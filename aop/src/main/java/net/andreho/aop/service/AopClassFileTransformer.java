package net.andreho.aop.service;

import net.andreho.aop.transform.ClassTransformer;
import net.andreho.aop.transform.Transformer;
import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.resources.ResourceScanner;
import net.andreho.resources.ResourceType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URISyntaxException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.andreho.resources.ResourceResolver.newFileResourceResolver;
import static net.andreho.resources.ResourceResolver.newJarResourceResolver;
import static net.andreho.resources.ResourceResolver.with;
import static net.andreho.resources.ResourceSourceLocator.usingClassLoader;
import static net.andreho.resources.ResourceSourceLocator.usingClassPath;
import static net.andreho.resources.ResourceSourceLocator.with;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public class AopClassFileTransformer
    implements ClassFileTransformer,
               Comparable<ClassFileTransformer> {

  private static final Logger LOG = Logger.getLogger(AopClassFileTransformer.class.getName());

  private static final int DEFAULT_VISITOR_FLAGS = ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG;
  private static final ClassTransformer[] EMPTY_CLASS_TRANSFORMERS = {};
  private static final String TRANSFORMER_DESC =
      "L" + Transformer.class.getName().replace('.', '/') + ";";

  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final WeakHashMap<ClassLoader, ClassTransformer[]> transformers = new WeakHashMap<>();

  static final class SearchForTransformers
      extends ClassVisitor {

    private static final int UNSUPPORTED_MODIFIERS =
        Opcodes.ACC_ABSTRACT
        | Opcodes.ACC_INTERFACE
        | Opcodes.ACC_ANNOTATION
        | Opcodes.ACC_SYNTHETIC
        | Opcodes.ACC_ENUM;

    String className;
    volatile boolean result;

    public SearchForTransformers() {
      super(Opcodes.ASM5);
    }

    @Override
    public void visit(final int version,
                      final int access,
                      final String name,
                      final String signature,
                      final String superName,
                      final String[] interfaces) {
      super.visit(version, access, name, signature, superName, interfaces);

      if ((access & UNSUPPORTED_MODIFIERS) == 0 &&
          (access & Opcodes.ACC_PUBLIC) != 0) {
        this.className = name;
      }
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc,
                                             final boolean visible) {
      if (visible &&
          !result &&
          className != null &&
          TRANSFORMER_DESC.equals(desc)) {
        this.result = true;
      }
      return super.visitAnnotation(desc, visible);
    }
  }

  @Override
  public byte[] transform(final ClassLoader loader,
                          final String className,
                          final Class<?> classBeingRedefined,
                          final ProtectionDomain protectionDomain,
                          final byte[] classfileBuffer)

  throws IllegalClassFormatException {

    if (isNotSupportedPackage(className)) {
      return null;
    }

    ClassTransformer[] transformers = getTransformers(loader);

    if (transformers == EMPTY_CLASS_TRANSFORMERS ||
        (transformers != null && transformers.length == 0)) {
      return null;
    }

    //DO WE NEED TO SEARCH FOR TRANSFORMERS?
    if (transformers == null) {
      transformers = findTransformers(loader);
    }

    if (transformers.length > 0) {
      return delegate(loader, className, classBeingRedefined, protectionDomain, classfileBuffer, transformers);
    }

    return null;
  }

  private ClassTransformer[] findTransformers(final ClassLoader loader) {
    ClassTransformer[] transformers;
    final Lock writeLock = getWriteLock();
    writeLock.lock();
    try {
      transformers = getTransformers(loader);
      if (transformers == null) {
        transformers = locateClassfileTransformers(loader);
        this.transformers.putIfAbsent(loader, transformers);
      }
    } finally {
      writeLock.unlock();
    }
    return transformers;
  }

  private ClassTransformer[] getTransformers(final ClassLoader loader) {
    final Lock readLock = getReadLock();
    readLock.lock();
    try {
      return this.transformers.get(loader);
    } finally {
      readLock.unlock();
    }
  }

  private Lock getReadLock() {
    return this.lock.readLock();
  }

  private Lock getWriteLock() {
    return this.lock.writeLock();
  }

  protected ClassTransformer[] locateClassfileTransformers(final ClassLoader loader) {
    final Set<String> foundTransformers = scanForTransformers(loader);
    final ClassTransformer[] transformers = instantiateTransformers(loader, foundTransformers);
    Arrays.sort(transformers);
    return transformers;
  }

  private byte[] delegate(final ClassLoader loader,
                          final String className,
                          final Class<?> classBeingRedefined,
                          final ProtectionDomain protectionDomain,
                          final byte[] classfileBuffer,
                          final ClassTransformer[] transformers) {

    byte[] currentByteCode = classfileBuffer;

    for (ClassTransformer transformer : transformers) {
      try {
        byte[] transformedByteCode = transformer.transform(
            loader,
            className,
            classBeingRedefined,
            protectionDomain,
            currentByteCode
        );

        if (transformedByteCode != null && transformedByteCode != currentByteCode) {
          currentByteCode = transformedByteCode;
        }
      } catch (Throwable t) {
        LOG.log(Level.SEVERE, "Failed at: " + transformer.getName() + ", because of: " + t.getMessage(), t);
        return null;
      }
    }

    return currentByteCode;
  }

  private ClassTransformer[] instantiateTransformers(ClassLoader loader,
                                                     Set<String> foundTransformers) {
    ClassTransformer[] transformers = EMPTY_CLASS_TRANSFORMERS;

    if (!foundTransformers.isEmpty()) {
      Set<ClassTransformer> transformerSet = new LinkedHashSet<>();

      for (String transformer : foundTransformers) {
        try {
          final Class<? extends ClassTransformer> transformerClass =
              (Class<? extends ClassTransformer>) loader.loadClass(transformer.replace('/', '.'));

          if (ClassTransformer.class.isAssignableFrom(transformerClass)) {
            transformerSet.add(transformerClass.newInstance());
            LOG.config("Added transformer: " + transformerClass.getName());
          }
        } catch (Exception e) {
          LOG.severe("Unable to instantiate new class transformer: " + e.getMessage());
        }
      }
      transformers = transformerSet.toArray(new ClassTransformer[transformerSet.size()]);
    }

    return transformers;
  }

  private Set<String> scanForTransformers(ClassLoader loader) {
    try {
      ResourceScanner scanner = ResourceScanner.newScanner(
          with(usingClassLoader(), usingClassPath()),
          with(newFileResourceResolver(), newJarResourceResolver()),
          this::checkResource,
          ResourceType.CLASS_TYPE);

      return scanner.scan(loader).keySet();
    } catch (IOException | URISyntaxException | ExecutionException | InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

  private boolean checkResource(final String name,
                                final Supplier<InputStream> supplier) {
    return name.endsWith(".class") &&
           isSupportedPackage(name) &&
           filter(supplier.get());
  }

  private boolean filter(final InputStream inputStream) {
    try {

      final ClassReader classReader = new ClassReader(inputStream);
      if (!isNotSupportedPackage(classReader.getClassName())) {
        return false;
      }

      final SearchForTransformers visitor = new SearchForTransformers();
      classReader.accept(visitor, DEFAULT_VISITOR_FLAGS);
      return visitor.result;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public int compareTo(final ClassFileTransformer o) {
    return getClass().getName().compareTo(o.getClass().getName());
  }

  private static boolean isSupportedPackage(final String className) {
    return !isNotSupportedPackage(className);
  }

  private static boolean isNotSupportedPackage(final String className) {
    return className.startsWith("java/") ||
           className.startsWith("sun/") ||
           className.startsWith("javax/") ||
           className.startsWith("com/sun/") ||
           className.startsWith("com/intellij/") ||
           className.startsWith("net/andreho/aop/") ||
           className.startsWith("net/andreho/agent/") ||
           className.startsWith("net/andreho/haxxor/");
  }
}
