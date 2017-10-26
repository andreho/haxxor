package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectAdvice<T>
    extends Comparable<AspectAdvice> {

  enum Target {
    TYPE,
    FIELD,
    METHOD,
    CONSTRUCTOR,
    PARAMETER
  }

  /**
   * @return
   */
  int getIndex();

  /**
   * @return
   */
  boolean hasTarget(Target target);

  /**
   * @return
   */
  AspectAdviceType getType();

  /**
   * @return
   */
  ElementMatcher<T> getMatcher();

  /**
   * @return
   */
  List<HxMethod> getInterceptors();

  /**
   * @return
   */
  default boolean needsAspectFactory() {
    return getInterceptors()
      .stream()
      .anyMatch(m -> !m.isStatic());
  }

  /**
   * @return
   */
  String getProfileName();

  /**
   * @param context
   * @param element
   * @return
   */
  boolean apply(final AspectContext context,
                final T element);

  /**
   * @return
   */
  default String getName() {
    return getClass().getSimpleName();
  }

  @Override
  default int compareTo(AspectAdvice o) {
    int result = getType().compareTo(o.getType());
    if(result == 0) {
      result = Integer.compare(getIndex(), o.getIndex());
    }
    return result;
  }
}
