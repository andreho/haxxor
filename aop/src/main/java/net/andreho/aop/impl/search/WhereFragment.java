package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxAnnotation;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class WhereFragment
    extends AbstractFragment<HxAnnotation> {

  private static final ConditionFragment[] EMPTY_QUERY_ARRAY = new ConditionFragment[0];
  private final ConditionFragment[] queries;

  public WhereFragment(final ConditionFragment ... queries) {
    this.queries = queries;
  }

  public WhereFragment(Collection<ConditionFragment> queries) {
    this(queries.toArray(EMPTY_QUERY_ARRAY));
  }

  @Override
  public boolean test(final HxAnnotation annotation) {
    for(ConditionFragment query : queries) {
      if(!query.test(annotation)) {
        return false;
      }
    }
    return true;
  }
}
