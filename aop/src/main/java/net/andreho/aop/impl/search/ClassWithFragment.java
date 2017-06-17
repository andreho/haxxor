package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassWithFragment
    extends AbstractFragment<HxType> {

  private final AbstractFragment<HxType> modifiers;
  private final AbstractFragment<String> value;
  private final AbstractFragment<HxType> named;
  private final AbstractFragment<HxType> annotated;

  public ClassWithFragment(final AbstractFragment<HxType> modifiers,
                           final AbstractFragment<String> value,
                           final AbstractFragment<HxType> named,
                           final AbstractFragment<HxType> annotated) {
    this.modifiers = modifiers;
    this.annotated = annotated;
    this.named = named;
    this.value = value;
  }

  @Override
  public boolean test(final HxType type) {
    return modifiers.test(type) &&
           value.test(type.getName()) &&
           named.test(type) &&
           annotated.test(type);
  }
}
