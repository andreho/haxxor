package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.Key;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br/>Created by a.hofmann on 11.10.2017 at 18:15.
 */
public class KeyRegistry {
  private static final KeyRegistry INSTANCE = new KeyRegistry();

  private final Lock lock;
  private final Map<Object, Key> keys;

  /**
   * Creates a new key for given value or uses already existing one
   * @param value
   * @param <V>
   * @return
   */
  public static <V> Key<V> register(V value) {
    return INSTANCE.registerKey(value);
  }

  private KeyRegistry() {
    this.lock = new ReentrantLock();
    this.keys = new WeakHashMap<>();
  }

  private <V> Key<V> registerKey(V value) {
    final Lock lock = this.lock;
    lock.lock();
    try {
      final int id = keys.size();
      return keys.computeIfAbsent(value, (key) -> new KeyImpl<>(key, id));
    } finally {
      lock.unlock();
    }
  }
}
