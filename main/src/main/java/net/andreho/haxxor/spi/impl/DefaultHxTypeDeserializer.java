package net.andreho.haxxor.spi.impl;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxTypeDeserializer;
import net.andreho.haxxor.spi.impl.visitors.HxTypeVisitor;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 16:43.
 */
public class DefaultHxTypeDeserializer implements HxTypeDeserializer {
  private final Hx haxxor;
//  private final Map<String, Reference<ClassReader>> cache;
//  private final Queue<String> cacheQueue;
//  private final int cacheBound;

  public DefaultHxTypeDeserializer(final Hx haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor);
//    this.cache = new ConcurrentHashMap<>();
//    this.cacheQueue = new ConcurrentLinkedQueue<>();
//    this.cacheBound = 4096;
  }

  /**
   * @return
   */
  protected HxTypeVisitor createTypeVisitor(final HxType type, boolean resolveClass, boolean resolveFields, boolean resolveMethods) {
    return new HxTypeVisitor(haxxor, resolveClass, resolveFields, resolveMethods).setType(type);
  }

  @Override
  public HxType deserialize(final HxType type,
                            final byte[] byteCode,
                            final int flags) {
//    Reference<ClassReader> reference = cache.get(type.getName());
//    ClassReader classReader;
//    if(reference == null || (classReader = reference.get()) == null) {
//      classReader = new ClassReader(byteCode);
//      {
//        @Override
//        protected String createString(final char[] buf,
//                                      final int strLen) {
//          return haxxor.getDeduplicationCache().deduplicate(buf, strLen);
//        }
//      };
//      if(!cache.isEmpty() && cache.size() > cacheBound) {
//        cache.remove(cacheQueue.poll());
//      }
//      cache.putIfAbsent(type.getName(), new SoftReference<>(classReader));
//      cacheQueue.offer(type.getName());
//    }

    ClassReader classReader = new ClassReader(byteCode);
    final HxTypeVisitor visitor = createTypeVisitor(
      type,
      (flags & Haxxor.Flags.SKIP_CLASS_INTERNALS) == 0,
      (flags & Haxxor.Flags.SKIP_FIELDS) == 0,
      (flags & Haxxor.Flags.SKIP_METHODS) == 0
    );
    classReader.accept(visitor, flags & 0xFFFF);
    return visitor.getType();
  }
}
