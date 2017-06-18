package net.andreho.aop.spi;

import net.andreho.aop.api.AspectType;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectStep
    extends AspectMatcher,
            Comparable<AspectStep> {

  /**
   * @return
   */
  int getIndex();

  /**
   * @return
   */
  AspectType getType();

  /**
   * @return
   */
  AspectMatcher getMatcher();


  boolean apply(AspectDefinition def,
                HxType hxType);

  /**
   * @return
   */
  default String getName() {
    return getClass().getSimpleName();
  }

  @Override
  default int compareTo(AspectStep o) {
    return Integer.compare(getIndex(), o.getIndex());
  }

}
