package net.andreho.aop.spi.impl.matchers;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 19:19.
 */
public class StringMatcher
    extends AbstractMatcher<String> {

  private final String value;

  public StringMatcher(final String value) {
    this.value = value == null ? "" : value;
  }

  @Override
  public boolean matches(final String s) {
    return isAny() || value.equals(s);
  }

  @Override
  public boolean isAny() {
    return value.isEmpty();
  }

  @Override
  public String toString() {
      return "EQUALS="+(isAny()? "ANY" : value);
  }
}
