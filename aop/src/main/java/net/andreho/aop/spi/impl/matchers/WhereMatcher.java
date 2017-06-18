package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxAnnotation;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class WhereMatcher
    extends ConjunctionMatcher<HxAnnotation> {

  public WhereMatcher(final Collection<AspectMatcher<HxAnnotation>> collection) {
    super(collection);
  }

  public WhereMatcher(final AspectMatcher<HxAnnotation> ... array) {
    super(array);
  }
}
