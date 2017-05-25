package net.andreho.aop.service;

import net.andreho.aop.internal.Scanner;
import net.andreho.aop.transform.ClassTransformer;
import net.andreho.aop.transform.Transformer;
import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public class DelegatingClassFileTransformer implements ClassFileTransformer {
   private static final Logger LOG = Logger.getLogger(DelegatingClassFileTransformer.class.getName());

   private static final int DEFAULT_VISITOR_FLAGS = ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG;
   private static final ClassTransformer[] EMPTY_CLASS_TRANSFORMERS = {};
   private static final String TRANSFORMER_DESC =
      "L" + Transformer.class.getName().replace('.', '/') + ";";

   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   private final WeakHashMap<ClassLoader, ClassTransformer[]> transformers = new WeakHashMap<>();

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

      ClassTransformer[] transformers = fetchTransformers(loader);

      if(transformers == EMPTY_CLASS_TRANSFORMERS || (transformers != null && transformers.length == 0)) {
         return null;
      }

      //DO WE NEED TO SEARCH FOR TRANSFORMERS?
      if (transformers == null) {
         transformers = searchTransformers(loader);
      }

      if (transformers.length > 0) {
         return delegate(loader, className, classBeingRedefined, protectionDomain, classfileBuffer, transformers);
      }

      return null;
   }

   private ClassTransformer[] searchTransformers(final ClassLoader loader) {
      ClassTransformer[] transformers;
      final Lock writeLock = getWriteLock();
      writeLock.lock();
      try {
         transformers = fetchTransformers(loader);
         if(transformers == null) {
            transformers = locateClassfileTransformers(loader);
            this.transformers.putIfAbsent(loader, transformers);
         }
      } finally {
         writeLock.unlock();
      }
      return transformers;
   }

   private ClassTransformer[] fetchTransformers(final ClassLoader loader) {
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
      final ClassTransformer[] transformers;
      Set<String> foundTransformers = scanForTransformers(loader);
      transformers = instantiateTransformers(loader, foundTransformers);
      Arrays.sort(transformers);
      return transformers;
   }

   private boolean isNotSupportedPackage(final String className) {
      return className.startsWith("java/") ||
             className.startsWith("sun/") ||
             className.startsWith("javax/") ||
             className.startsWith("com/sun/") ||
             className.startsWith("com/intellij/") ||
             className.startsWith("net/andreho/aop/") ||
             className.startsWith("net/andreho/haxxor/");
   }

   private byte[] delegate(final ClassLoader loader,
                           final String className,
                           final Class<?> classBeingRedefined,
                           final ProtectionDomain protectionDomain,
                           final byte[] classfileBuffer,
                           final ClassTransformer[] transformers) {

      byte[] transformedClassfileBuffer = classfileBuffer;

      for (ClassTransformer transformer : transformers) {
         try {
            transformedClassfileBuffer = transformer.transform(
               loader,
               className,
               classBeingRedefined,
               protectionDomain,
               transformedClassfileBuffer
            );
         } catch (Throwable t) {
            t.printStackTrace();
            LOG.severe("DelegatingClassFileTransformer failed at: " +
                       transformer.getName() + ", because of: " + t.getMessage());
            return classfileBuffer;
         }
      }

      return transformedClassfileBuffer;
   }

   private ClassTransformer[] instantiateTransformers(ClassLoader loader, Set<String> foundTransformers) {
      ClassTransformer[] transformers = EMPTY_CLASS_TRANSFORMERS;

      if (!foundTransformers.isEmpty()) {
         Set<ClassTransformer> transformerSet = new LinkedHashSet<>();

         for (String transformer : foundTransformers) {
            try {
               final Class<? extends ClassTransformer> transformerClass =
                  (Class<? extends ClassTransformer>) loader.loadClass(transformer.replace('/', '.'));

               if (ClassTransformer.class.isAssignableFrom(transformerClass)) {
                  transformerSet.add(transformerClass.newInstance());
                  LOG.fine("Added transformer: " + transformerClass.getName());
               }
            } catch (Exception e) {
               e.printStackTrace();
               LOG.severe("Unable to instantiate new class transformer: " + e.getMessage());
            }
         }

         transformers = transformerSet.toArray(new ClassTransformer[transformerSet.size()]);
      }

      return transformers;
   }

   Set<String> scanForTransformers(ClassLoader loader) {
      final Scanner scanner = new Scanner(loader);
      final Set<String> foundTransformers = new HashSet<>();
      final List<Future<Collection<String>>> pendingTasks = new ArrayList<>();

      try {
         scanner.scan((scn, rootUrl, name, file, isJar) -> {
            ForkJoinTask<Collection<String>> pendingTask =
               ForkJoinPool.commonPool().submit(() -> {
                  final Collection<String> result = new LinkedHashSet<>();
                  if (isJar) {
                     findTransformersFromJar(file, result);
                  } else if (isClassFile(file.getName())) {
                     findTransformerInClass(file, result);
                  }
                  return result;
               });
            pendingTasks.add(pendingTask);
         });

         try {
            for (Future<Collection<String>> pending : pendingTasks) {
               Collection<String> transformers = pending.get();
               foundTransformers.addAll(transformers);
            }
         } catch (InterruptedException | ExecutionException e) {
            LOG.severe(e.getMessage());
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      return foundTransformers;
   }

   void findTransformerInClass(final File file, final Collection<String> result) {
      try (final FileInputStream inputStream = new FileInputStream(file)) {
         final ClassReader classReader = new ClassReader(inputStream);
         if(isNotSupportedPackage(classReader.getClassName())) {
            return;
         }
         classReader.accept(new SearchForTransformers(result), DEFAULT_VISITOR_FLAGS);
      } catch (Exception e) {
         LOG.severe(e.getMessage());
      }
   }

   void findTransformersFromJar(File file, final Collection<String> result) {
      try (final JarFile jarFile = new JarFile(file)) {
         final Enumeration<? extends JarEntry> entries = jarFile.entries();

         while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();

            if (isNotSupportedPackage(entry.getName())) {
               continue;
            }

            if (!entry.isDirectory() && isClassFile(entry.getName())) {
               final InputStream inputStream = jarFile.getInputStream(entry);

               final ClassReader classReader = new ClassReader(inputStream);

               classReader.accept(new SearchForTransformers(result), DEFAULT_VISITOR_FLAGS);
            }
         }

      } catch (IOException e) {
         LOG.severe(e.getMessage());
      }
   }

   private boolean isClassFile(final String name) {
      return name.toLowerCase(Locale.getDefault()).endsWith(".class");
   }

   static class SearchForTransformers extends ClassVisitor {
      private static final int UNSUPPORTED_ACCESS =
         Opcodes.ACC_ABSTRACT
         | Opcodes.ACC_INTERFACE
         | Opcodes.ACC_ANNOTATION
         | Opcodes.ACC_SYNTHETIC
         | Opcodes.ACC_ENUM;

      final Collection<String> result;
      String className;

      public SearchForTransformers(final Collection<String> result) {
         super(Opcodes.ASM5);
         this.result = result;
      }

      @Override
      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         super.visit(version, access, name, signature, superName, interfaces);

         if ((access & UNSUPPORTED_ACCESS) == 0 &&
             (access & Opcodes.ACC_PUBLIC) != 0) {
            this.className = name;
         }
      }

      @Override
      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (className != null &&
             visible &&
             TRANSFORMER_DESC.equals(desc)) {

            this.result.add(this.className);
         }

         return super.visitAnnotation(desc, visible);
      }
   }
}
