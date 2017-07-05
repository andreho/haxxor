package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ElementMatcher;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractAspectAdvice<T>
  implements AspectAdvice<T>, ElementMatcher<T> {

  private final int index;
  private final AspectAdviceType type;
  private final ElementMatcher<T> elementMatcher;
  private final String profileName;

  public AbstractAspectAdvice(final int index,
                              final AspectAdviceType type,
                              final ElementMatcher<T> elementMatcher,
                              final String profileName) {
    this.index = index;
    this.type = requireNonNull(type);
    this.profileName = requireNonNull(profileName);
    this.elementMatcher = requireNonNull(elementMatcher);
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public boolean hasTarget(final Target target) {
    return getType().hasTarget(target);
  }

  @Override
  public boolean needsAspectFactory() {
    return false;
  }

  @Override
  public String getProfileName() {
    return profileName;
  }

  @Override
  public boolean apply(final AspectContext context,
                       final T element) {
    return false;
  }

  @Override
  public boolean match(final T element) {
    return getMatcher().match(element);
  }

  @Override
  public AspectAdviceType getType() {
    return type;
  }

  @Override
  public ElementMatcher<T> getMatcher() {
    return elementMatcher;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AspectAdvice)) {
      return false;
    }

    final AspectAdvice<?> that = (AspectAdvice<?>) o;
    return Objects.equals(type, that.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(type);
  }
}
