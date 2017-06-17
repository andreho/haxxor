package net.andreho.aop.spi;

import net.andreho.aop.AspectType;
import net.andreho.aop.utils.OrderUtils;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectStep
    extends TypeMatcher, Comparable<AspectStep> {

  /**
   * @return
   */
  AspectType getAspectType();

  /**
   * @return
   */
  TypeMatcher getTypeMatcher();

  boolean apply(AspectDefinition def,
                HxType hxType);
  /**
   * @return
   */
  default String getName() {
    return getClass().getSimpleName();
  }

  @Override
  default boolean match(HxType hxType) {
    return getTypeMatcher().match(hxType);
  }

  @Override
  default int compareTo(AspectStep o) {
    return OrderUtils.order(this, o);
  }

}
