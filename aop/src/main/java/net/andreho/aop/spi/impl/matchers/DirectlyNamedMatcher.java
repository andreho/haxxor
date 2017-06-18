package net.andreho.aop.spi.impl.matchers;

import net.andreho.haxxor.spec.api.HxNamed;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class DirectlyNamedMatcher<T extends HxNamed>
    extends AbstractMatcher<T> {

  private final String name;

  public DirectlyNamedMatcher(final String name) {
    this.name = name == null? "" : name;
  }
  @Override
  public boolean match(final T named) {
    return isAny() || name.equals(named.getName());
  }

  @Override
  public boolean isAny() {
    return name.isEmpty();
  }

  @Override
  public String toString() {
    return "NAME="+(isAny()? "ANY" : name);
  }
}
