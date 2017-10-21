package net.andreho.aop.api.injectable.locals;

import net.andreho.aop.api.injectable.locals.impl.KeyRegistry;

/**
 * This class defines an access-key interface to a property of an {@link ElementLocal element-local}.
 * The instances of this interface must be defined as static constants and reused for further access.
 * <br/>Created by a.hofmann on 11.10.2017 at 18:34.
 */
public interface Key<V>
  extends Comparable<Key<?>> {

  /**
   * @param value
   * @param <X>
   * @return
   */
  static <X> Key<X> forValue(X value) {
    return KeyRegistry.register(value);
  }

  /**
   * @return
   */
  int getId();

  /**
   * @return
   */
  Object getRepresentative();
}
