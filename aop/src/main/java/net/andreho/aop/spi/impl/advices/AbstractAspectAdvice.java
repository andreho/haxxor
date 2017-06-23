package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ElementMatcher;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractAspectAdvice<T>
  implements AspectAdvice<T>, ElementMatcher<T> {

  private final int index;
  private final AspectAdviceType type;
  private final ElementMatcher<T> elementMatcher;
  private final Optional<String> profileName;

  public AbstractAspectAdvice(final int index,
                              final AspectAdviceType type,
                              final ElementMatcher<T> elementMatcher,
                              final Optional<String> profileName) {
    this.index = index;
    this.type = type;
    this.elementMatcher = elementMatcher;
    this.profileName = profileName;
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
  public Optional<String> getProfileName() {
    return profileName;
  }

  @Override
  public boolean apply(final AspectContext ctx,
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
}
