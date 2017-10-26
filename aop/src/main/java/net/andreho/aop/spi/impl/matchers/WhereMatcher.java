package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxAnnotation;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class WhereMatcher
    extends ConjunctionMatcher<HxAnnotation> {

  public WhereMatcher(final Collection<ElementMatcher<HxAnnotation>> collection) {
    super(collection);
  }

  public WhereMatcher(final ElementMatcher<HxAnnotation>... array) {
    super(array);
  }
}
