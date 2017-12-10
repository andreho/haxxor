package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.spi.HxParameters;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class CachedHxByteCodeLoader
    extends DefaultHxByteCodeLoader {

  public static final String MAX_CACHE_SIZE_PARAMETER =
    HxParameters.HX_BYTECODE_LOADER_MAX_CACHE_SIZE;
  public static final int MAXIMAL_CACHE_SIZE =
      Math.max(1, Integer.parseInt(System.getProperty(MAX_CACHE_SIZE_PARAMETER, "1000")));

  private static final Map<String, Reference<byte[]>> GLOBAL_CACHE =
      new LinkedHashMap<String, Reference<byte[]>>() {
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest) {
          return size() >= MAXIMAL_CACHE_SIZE;
        }
      };

  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Map<String, Reference<byte[]>> cache;

  public CachedHxByteCodeLoader(final Hx haxxor) {
    this(haxxor, GLOBAL_CACHE);
  }

  public CachedHxByteCodeLoader(final Hx haxxor,
                                final Map<String, Reference<byte[]>> cache) {
    super(haxxor);
    this.cache = Objects.requireNonNull(cache, "Cache can't be null.");
  }

  @Override
  @SuppressWarnings("Duplicates")
  public Optional<byte[]> load(final ClassLoader classLoader, final String className) {
    byte[] bytes = getCached(className);
    if (bytes != null) {
      return Optional.of(bytes);
    }
    return loadContent(classLoader, className);
  }

  private Optional<byte[]> loadContent(final ClassLoader classLoader, final String className) {
    byte[] content;
    final Lock lock = this.lock.writeLock();
    lock.lock();
    try {
      content = getCached(className);

      if (content == null) {
        Optional<byte[]> bytes = super.load(classLoader, className);
        if(bytes.isPresent()) {
          cache.put(className, new SoftReference<>(content = bytes.get()));
        }
      }
    } finally {
      lock.unlock();
    }
    return Optional.ofNullable(content);
  }

  private byte[] getCached(final String className) {
    final Lock lock = this.lock.readLock();
    lock.lock();
    try {
      byte[] bytes;
      final Reference<byte[]> reference = this.cache.get(className);
      if (reference != null && (bytes = reference.get()) != null) {
        return bytes;
      }
    } finally {
      lock.unlock();
    }
    return null;
  }
}
