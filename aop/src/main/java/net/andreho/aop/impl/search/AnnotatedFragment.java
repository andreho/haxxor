package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;

import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:44.
 */
public class AnnotatedFragment
    implements Predicate<HxAnnotated<?>> {

  private final NamedFragment named;
  private final MatchFragment className;
  private final WhereFragment where;

  public AnnotatedFragment(final NamedFragment named,
                           final String className,
                           final WhereFragment where) {
    this.named = named;
    this.className = new MatchFragment(className);
    this.where = where;
  }

  @Override
  public boolean test(final HxAnnotated<?> annotated) {
    for(HxAnnotation annotation : annotated.getAnnotations()) {
      if(named.test(annotation.getType()) &&
         className.test(annotation.getType().getName()) &&
         where.test(annotation)) {
        return true;
      }
    }
    return false;
  }
}
