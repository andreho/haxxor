package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxExecutable;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 08:12.
 */
public class ParametersMatcher<E extends HxExecutable<E>> extends AbstractMatcher<E> {

  private final AspectMatcher<E> positioned;
  private final AspectMatcher<E> modifiers;
  private final AspectMatcher<E> typed;
  private final AspectMatcher<E> annotated;
  private final AspectMatcher<E> named;

  public ParametersMatcher(final AspectMatcher<E> positioned,
                           final AspectMatcher<E> modifiers,
                           final AspectMatcher<E> typed,
                           final AspectMatcher<E> annotated,
                           final AspectMatcher<E> named) {
    this.positioned = positioned;
    this.modifiers = modifiers;
    this.typed = typed;
    this.annotated = annotated;
    this.named = named;
  }

  @Override
  public boolean isAny() {
    return positioned.isAny() &&
           modifiers.isAny() &&
           typed.isAny() &&
           annotated.isAny() &&
           named.isAny();
  }

  @Override
  public boolean match(final E element) {
    return positioned.match(element) &&
           modifiers.match(element) &&
           typed.match(element) &&
           annotated.match(element) &&
           named.match(element);
  }
}
