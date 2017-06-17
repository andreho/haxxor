package net.andreho.aop.spi;

import net.andreho.aop.utils.OrderUtils;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 06:17.
 */
public interface PackageFilter
    extends Comparable<PackageFilter> {

  /**
   * @param fullyQualifiedName
   * @return <b>true</b> to allow further transformation, <b>false</b> to skip
   */
  boolean filter(String fullyQualifiedName);

  @Override
  default int compareTo(PackageFilter o) {
    return OrderUtils.order(this, o);
  }

  /**
   * @param filters
   * @return
   */
  static PackageFilter with(final Collection<PackageFilter> filters) {
    return with(filters.toArray(new PackageFilter[0]));
  }

  /**
   * @param filters
   * @return
   */
  static PackageFilter with(final PackageFilter... filters) {
    return (typename) -> {
      for (PackageFilter filter : filters) {
        if (filter.filter(typename)) {
          return true;
        }
      }
      return false;
    };
  }
}
