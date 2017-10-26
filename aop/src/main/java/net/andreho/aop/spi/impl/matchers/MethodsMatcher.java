package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxNamed;
import net.andreho.haxxor.api.HxType;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:40.
 */
public class MethodsMatcher
  extends AbstractMatcher<HxMethod> {

  private final ElementMatcher<HxMethod> type;
  private final ElementMatcher<HxMethod> declaredBy;
  private final ElementMatcher<HxMethod> modifiers;
  private final ElementMatcher<HxNamed> named;
  private final ElementMatcher<HxMethod> returning;
  private final ElementMatcher<HxAnnotated> annotated;
  private final ElementMatcher<HxType> throwing;
  private final ElementMatcher<HxMethod> signatures;
  private final ElementMatcher<HxMethod> parameters;

  public MethodsMatcher(final ElementMatcher<HxMethod> type,
                        final ElementMatcher<HxType> declaredBy,
                        final ElementMatcher<HxMethod> modifiers,
                        final ElementMatcher<HxNamed> named,
                        final ElementMatcher<HxType> returning,
                        final ElementMatcher<HxAnnotated> annotated,
                        final ElementMatcher<HxType> throwing,
                        final ElementMatcher<HxMethod> signatures,
                        final ElementMatcher<HxMethod> parameters) {
    this.type = type.minimize();
    this.declaredBy = new DeclaredByMatcher<HxMethod>(declaredBy).minimize();
    this.modifiers = modifiers.minimize();
    this.named = named.minimize();
    this.returning = new ReturningMatcher(returning).minimize();
    this.annotated = annotated.minimize();
    this.throwing = throwing.minimize();
    this.signatures = signatures.minimize();
    this.parameters = parameters.minimize();
  }

  @Override
  public boolean matches(final HxMethod element) {
    return
      type.matches(element) &&
      declaredBy.matches(element) &&
      modifiers.matches(element) &&
      named.matches(element) &&
      returning.matches(element) &&
      annotated.matches(element) &&
      signatures.matches(element) &&
      parameters.matches(element) &&
      matchThrowing(element);
  }

  private boolean matchThrowing(final HxMethod element) {
    if (throwing.isAny()) {
      return true;
    }
    final List<HxType> exceptionTypes = element.getExceptionTypes();
    for (HxType throwableType : exceptionTypes) {
      if (throwing.matches(throwableType)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("METHODS [");

    int added = 0;
    if(!type.isAny()) {
      builder.append("type: ").append(type);
      added++;
    }

    if(!declaredBy.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("declaredBy: ").append(declaredBy);
      added++;
    }

    if(!modifiers.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("modifiers: ").append(modifiers);
      added++;
    }

    if(!named.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("named: ").append(modifiers);
      added++;
    }

    if(!returning.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("returning: ").append(returning);
      added++;
    }

    if(!annotated.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("annotated: ").append(annotated);
      added++;
    }

    if(!signatures.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("signatures: ").append(signatures);
      added++;
    }

    if(!parameters.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("parameters: ").append(parameters);
      added++;
    }

    if(!throwing.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("throwing: ").append(throwing);
      added++;
    }

    if(added == 0) {
      builder.append("ANY");
    }

    return builder.append("]").toString();
  }
}
