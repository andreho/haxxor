package net.andreho.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.ServiceLoader;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 23:54.
 */
final class DelegatingClassFileTransformer
    implements ClassFileTransformer {

  private static final Logger LOG = Logger.getLogger(DelegatingClassFileTransformer.class.getName());
  private static final ClassFileTransformer[] CLASS_FILE_TRANSFORMERS = new ClassFileTransformer[0];
  private static final ServiceLoader<ClassFileTransformer> TRANSFORMERS = ServiceLoader.load(
      ClassFileTransformer.class);

  private final ClassFileTransformer[] transformers;

  public DelegatingClassFileTransformer() {
    this(locateClassFileTransformers().toArray(CLASS_FILE_TRANSFORMERS));
  }

  private DelegatingClassFileTransformer(final ClassFileTransformer... transformers) {
    this.transformers = transformers;
  }

  private static Collection<ClassFileTransformer> locateClassFileTransformers() {
    Collection<ClassFileTransformer> transformers = new LinkedHashSet<>();
    int comparableCount = 0;

    for (final ClassFileTransformer transformer : TRANSFORMERS) {
      if (transformer instanceof Comparable) {
        comparableCount++;
      }

      if (transformers.add(transformer)) {
        LOG.config("ClassFileTransformer located and added: " + transformer.getClass()
                                                                           .getName());
      }
    }

    if (!transformers.isEmpty()) {
      if (comparableCount == transformers.size()) {
        transformers = new TreeSet<>(transformers);
      }
    }

    return transformers;
  }

  @Override
  public byte[] transform(final ClassLoader loader,
                          final String className,
                          final Class<?> classBeingRedefined,
                          final ProtectionDomain protectionDomain,
                          final byte[] classfileBuffer)
  throws IllegalClassFormatException {

    byte[] byteCode = classfileBuffer;

    for (ClassFileTransformer transformer : transformers) {
      try {
        byte[] transformedByteCode = transformer.transform(loader,
                                                           className,
                                                           classBeingRedefined,
                                                           protectionDomain,
                                                           classfileBuffer);

        if (transformedByteCode != null && transformedByteCode != byteCode) {
          byteCode = transformedByteCode;
        }
      } catch (Throwable t) {
        LOG.severe("Failed to apply transformation with: " + transformer.getClass()
                                                                        .getName() + ", because of: " +
                   t.getMessage());
        return null;
      }
    }

    if (byteCode != classfileBuffer) {
      return byteCode;
    }
    //there isn't any transformations
    return null;
  }
}
