package net.andreho.aop.spi.impl.matchers;

import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxModifier;
import net.andreho.haxxor.api.HxModifiers;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ModifiersMatcher<M extends HxMember<M>>
    extends AbstractMatcher<M> {

  private final int mods;

  public ModifiersMatcher(final int mods) {
    this.mods = mods;
  }

  @Override
  public boolean matches(final M hxMember) {
    final int mods = this.mods;
    return isAny(mods) ||
           (hxMember.getModifiers() & mods) == mods;
  }

  private boolean isAny(final int mods) {
    return mods == -1;
  }

  @Override
  public String toString() {
    return "WITH_MODIFIERS " + (isAny(mods)? "[ANY]" : HxModifier.toSet(HxModifiers.class, mods));
  }
}
