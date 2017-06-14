package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxOwned;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxOwnedImpl<O extends HxOwned<O>>
    implements HxOwned<O> {

  protected HxMember declaringMember;

  public HxOwnedImpl() {
  }

  public HxOwnedImpl(HxMember declaringMember) {
    this.declaringMember = declaringMember;
  }

  @Override
  public HxMember getDeclaringMember() {
    return declaringMember;
  }

  @Override
  public O setDeclaringMember(HxMember declaringMember) {
    this.declaringMember = declaringMember;
    return (O) this;
  }
}
