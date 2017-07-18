package net.andreho.aop.spi.impl.matchers;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 00:12.
 */
public class NoneMatcher<T> extends AbstractMatcher<T> {
  public static final NoneMatcher INSTANCE = new NoneMatcher();

  @Override
  public boolean matches(final T element) {
    return false;
  }

  @Override
  public String toString() {
    return "NONE";
  }
}
