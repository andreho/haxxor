package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:40.
 */
public class MethodsMatcher<E extends HxExecutable<E>>
  extends AbstractMatcher<E> {

  private final AspectMatcher<E> declaredBy;
  private final AspectMatcher<E> modifiers;
  private final AspectMatcher<HxNamed> named;
  private final AspectMatcher<E> returning;
  private final AspectMatcher<HxAnnotated> annotated;
  private final AspectMatcher<HxType> throwing;
  private final AspectMatcher<E> signatures;
  private final AspectMatcher<E> parameters;

  public MethodsMatcher(final AspectMatcher<HxType> declaredBy,
                        final AspectMatcher<E> modifiers,
                        final AspectMatcher<HxNamed> named,
                        final AspectMatcher<HxType> returning,
                        final AspectMatcher<HxAnnotated> annotated,
                        final AspectMatcher<HxType> throwing,
                        final AspectMatcher<E> signatures,
                        final AspectMatcher<E> parameters) {
    this.declaredBy = declaredBy.isAny() ? AspectMatcher.any() : new DeclaredByMatcher<>(declaredBy);
    this.modifiers = modifiers;
    this.named = named;
    this.returning = returning.isAny() ? AspectMatcher.any() : new ReturningMatcher<>(returning);
    this.annotated = annotated;
    this.throwing = throwing;
    this.signatures = signatures;
    this.parameters = parameters;
  }

  @Override
  public boolean match(final E element) {
    return
      declaredBy.match(element) &&
      modifiers.match(element) &&
      named.match(element) &&
      returning.match(element) &&
      annotated.match(element) &&
      matchThrowing(element) &&
      signatures.match(element) &&
      parameters.match(element);
  }

  private boolean matchThrowing(final E element) {
    if (throwing.isAny()) {
      return true;
    }
    final List<HxType> exceptionTypes = element.getExceptionTypes();
    for (HxType throwableType : exceptionTypes) {
      if (throwing.match(throwableType)) {
        return true;
      }
    }
    return false;
  }
}
