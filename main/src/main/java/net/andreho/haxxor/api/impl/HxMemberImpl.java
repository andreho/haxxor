package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxOwned;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxMemberImpl<M extends HxMember<M> & HxOwned<M>>
    extends HxOwnedImpl<M>
    implements HxMember<M> {

  protected int modifiers;

  public HxMemberImpl() {
  }

  @Override
  public int getModifiers() {
    return modifiers;
  }

  @Override
  public M setModifiers(int modifiers) {
    this.modifiers = modifiers;
    return (M) this;
  }
}
