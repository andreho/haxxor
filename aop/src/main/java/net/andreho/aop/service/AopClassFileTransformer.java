package net.andreho.aop.service;

import net.andreho.agent.ClassFileTransformerService;
import net.andreho.aop.utils.OrderUtils;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public class AopClassFileTransformer
    implements ClassFileTransformerService {
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final WeakHashMap<ClassLoader, AopTypeTransformerEntry> entries = new WeakHashMap<>();

  public AopClassFileTransformer() {
  }

  @Override
  public byte[] transform(final ClassLoader loader,
                          final String className,
                          final Class<?> classBeingRedefined,
                          final ProtectionDomain protectionDomain,
                          final byte[] classfileBuffer)
  throws IllegalClassFormatException {

    if(loader != null) {
      AopTypeTransformerEntry entry = initEntry(loader); //.installAspects(loader)
      if (className != null && isSupportedPackage(className)) {
        return entry.execute(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
      }
    }
    return null;
  }

  private AopTypeTransformerEntry initEntry(final ClassLoader classLoader) {
    AopTypeTransformerEntry entry = getEntry(classLoader);
    if (entry != null) {
      return entry;
    }

    boolean newEntry = false;
    final Lock writeLock = getWriteLock();
    writeLock.lock();

    try {
      entry = getEntry(classLoader);
      if (entry == null) {
        entry = new AopTypeTransformerEntry();
        newEntry = null == this.entries.putIfAbsent(classLoader, entry);
      }
    } finally {
      writeLock.unlock();
    }

    if(newEntry) {
      entry.installAspects(classLoader);
    }

    return entry;
  }

  private AopTypeTransformerEntry getEntry(final ClassLoader loader) {
    final Lock readLock = getReadLock();
    readLock.lock();
    try {
      return this.entries.get(loader);
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

  @Override
  public int compareTo(final ClassFileTransformerService o) {
    return OrderUtils.order(this, o);
  }

  private static boolean isSupportedPackage(final String className) {
    return !isNotSupportedPackage(className);
  }

  private static boolean isNotSupportedPackage(final String className) {
    return isSealedPackage(className) ||
           className.startsWith("net/andreho/agent/") ||
           className.startsWith("net/andreho/haxxor/");
  }

  private static boolean isSealedPackage(final String className) {
    return className.startsWith("java/") ||
           className.startsWith("sun/") ||
           className.startsWith("javax/") ||
           className.startsWith("com/sun/") ||
           className.startsWith("com/intellij/");
  }
}
