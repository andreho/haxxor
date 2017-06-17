package net.andreho.aop.impl.search;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 19:19.
 */
public class MatchFragment
    extends AbstractFragment<String> {

  private final String value;

  public MatchFragment(final String value) {
    this.value = value == null ? "" : value;
  }

  @Override
  public boolean test(final String s) {
    return value.isEmpty() || value.equals(s);
  }
}
