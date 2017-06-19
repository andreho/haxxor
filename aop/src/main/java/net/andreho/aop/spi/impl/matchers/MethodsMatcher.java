package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:40.
 */
public class MethodsMatcher
  extends AbstractMatcher<HxMethod> {

  private final ElementMatcher<HxMethod> declaredBy;
  private final ElementMatcher<HxMethod> modifiers;
  private final ElementMatcher<HxNamed> named;
  private final ElementMatcher<HxMethod> returning;
  private final ElementMatcher<HxAnnotated> annotated;
  private final ElementMatcher<HxType> throwing;
  private final ElementMatcher<HxMethod> signatures;
  private final ElementMatcher<HxMethod> parameters;

  public MethodsMatcher(final ElementMatcher<HxType> declaredBy,
                        final ElementMatcher<HxMethod> modifiers,
                        final ElementMatcher<HxNamed> named,
                        final ElementMatcher<HxType> returning,
                        final ElementMatcher<HxAnnotated> annotated,
                        final ElementMatcher<HxType> throwing,
                        final ElementMatcher<HxMethod> signatures,
                        final ElementMatcher<HxMethod> parameters) {
    this.declaredBy = declaredBy.isAny() ? ElementMatcher.any() : new DeclaredByMatcher<>(declaredBy);
    this.modifiers = modifiers;
    this.named = named;
    this.returning = returning.isAny() ? ElementMatcher.any() : new ReturningMatcher(returning);
    this.annotated = annotated;
    this.throwing = throwing;
    this.signatures = signatures;
    this.parameters = parameters;
  }

  @Override
  public boolean match(final HxMethod element) {
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

  private boolean matchThrowing(final HxMethod element) {
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
