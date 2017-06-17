package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:44.
 */
public class AnnotatedFragment<A extends HxAnnotated<A> & HxNamed>
    extends AbstractFragment<A> {

  private final AbstractFragment<HxNamed> named;
  private final MatchFragment value;
  private final AbstractFragment<HxAnnotation> where;

  public AnnotatedFragment(final AbstractFragment<HxNamed> named,
                           final String value,
                           final AbstractFragment<HxAnnotation> where) {
    this.named = named;
    this.value = new MatchFragment(value);
    this.where = where;
  }

  @Override
  public boolean test(final A annotated) {
    for(HxAnnotation annotation : annotated.getAnnotations()) {
      final HxType hxType = annotation.getType();

      if(named.test(hxType) &&
         value.test(hxType.getName()) &&
         where.test(annotation)) {
        return true;
      }
    }
    return false;
  }
}
