package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxMember;

import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ModifiersFragment
    implements Predicate<HxMember<?>> {

  private final int mods;

  public ModifiersFragment(final int mods) {
    this.mods = mods;
  }

  @Override
  public boolean test(final HxMember<?> hxMember) {
    final int mods = this.mods;
    return mods == -1 || (hxMember.getModifiers() & mods) == mods;
  }
}
