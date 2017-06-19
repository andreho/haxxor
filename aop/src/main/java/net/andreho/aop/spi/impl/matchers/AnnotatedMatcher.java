package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:44.
 */
public class AnnotatedMatcher<A extends HxAnnotated<A> & HxNamed>
    extends AbstractMatcher<A> {

  private final ElementMatcher<HxNamed> value;
  private final ElementMatcher<HxNamed> named;
  private final ElementMatcher<HxAnnotation> where;

  public AnnotatedMatcher(final ElementMatcher<HxNamed> value,
                          final ElementMatcher<HxNamed> named,
                          final ElementMatcher<HxAnnotation> where) {
    this.named = named;
    this.value = value;
    this.where = where;
  }

  @Override
  public boolean match(final A annotated) {
    for(HxAnnotation annotation : annotated.getAnnotations().values()) {
      final HxType hxType = annotation.getType();

      if(value.match(hxType) &&
         named.match(hxType) &&
         where.match(annotation)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("ANNOTATED [");

    int added = 0;
    if(!value.isAny()) {
      builder.append("types: ").append(value);
      added++;
    }

    if(!named.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("named: ").append(named);
      added++;
    }

    if(!where.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("where: ").append(where);
      added++;
    }

    if(added == 0) {
      builder.append("ANY");
    }

    return builder.append("]").toString();
  }
}
