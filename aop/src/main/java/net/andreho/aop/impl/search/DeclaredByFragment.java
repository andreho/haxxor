package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxOwned;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:34.
 */
public class DeclaredByFragment<O extends HxOwned<O>>
    extends DisjunctionFragment<O> {

  public DeclaredByFragment(final Collection<AbstractFragment<O>> collection) {
    super(collection);
  }

  public DeclaredByFragment(final AbstractFragment<O> ... array) {
    super(array);
  }

  @Override
  protected boolean check(final AbstractFragment<O> fragment,
                          final O element) {
    return fragment.test((O) element.getDeclaringMember());
  }
}
