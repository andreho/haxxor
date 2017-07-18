package net.andreho.aop.spi.impl.matchers;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 00:12.
 */
public class AnyMatcher<T> extends AbstractMatcher<T> {
  public static final AnyMatcher INSTANCE = new AnyMatcher();

  @Override
  public boolean matches(final T element) {
    return true;
  }

  @Override
  public String toString() {
    return "ANY";
  }
}
