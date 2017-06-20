package net.andreho.aop.spi;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectStep<T>
    extends Comparable<AspectStep> {

  enum Kind {
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
  boolean hasKind(Kind kind);

  /**
   * @return
   */
  AspectStepType getType();

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
  boolean apply(final AspectApplicationContext ctx,
                final T element);

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
