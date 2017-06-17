package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
@FunctionalInterface
public interface TypeMatcher {

  /**
   * @param hxType
   * @return
   */
  boolean match(HxType hxType);
}
