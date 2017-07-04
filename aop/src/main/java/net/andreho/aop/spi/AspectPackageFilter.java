package net.andreho.aop.spi;

import net.andreho.aop.utils.OrderUtils;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 06:17.
 */
public interface AspectPackageFilter
    extends Comparable<AspectPackageFilter> {

  /**
   * @param name is either a internal fully-qualified name of a class or
   *             a fully-qualified filename of a class with <code>.class</code> as ending
   * @return <b>true</b> to allow further transformation, <b>false</b> to ignore
   */
  boolean filter(String name);

  @Override
  default int compareTo(AspectPackageFilter o) {
    return OrderUtils.order(this, o);
  }

  /**
   * @param filters
   * @return
   */
  static AspectPackageFilter with(final Collection<AspectPackageFilter> filters) {
    return with(filters.toArray(new AspectPackageFilter[0]));
  }

  /**
   * @param filters
   * @return
   */
  static AspectPackageFilter with(final AspectPackageFilter... filters) {
    return (typename) -> {
      for (AspectPackageFilter filter : filters) {
        if (filter.filter(typename)) {
          return true;
        }
      }
      return false;
    };
  }
}
