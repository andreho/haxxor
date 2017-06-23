package net.andreho.aop.spi;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectAdvice<T>
    extends Comparable<AspectAdvice> {

  enum Target {
    TYPE,
    FIELD,
    METHOD,
    CONSTRUCTOR
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
  Object getInterceptor();

  /**
   * @return
   */
  boolean needsAspectFactory();

  /**
   * @return
   */
  Optional<String> getProfileName();

  /**
   * @param ctx
   * @param element
   * @return
   */
  boolean apply(final AspectContext ctx,
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