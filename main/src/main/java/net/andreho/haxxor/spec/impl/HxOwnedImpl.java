package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.HxMember;
import net.andreho.haxxor.spec.HxOwned;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxOwnedImpl<O extends HxOwned<O>> implements HxOwned<O> {
   protected HxMember declaringMember;

   public HxOwnedImpl() {
   }

   public HxOwnedImpl(HxMember declaringMember) {
      this.declaringMember = declaringMember;
   }

   @Override
   public <M extends HxMember> M getDeclaringMember() {
      return (M) declaringMember;
   }

   @Override
   public O setDeclaringMember(HxMember declaringMember) {
      this.declaringMember = declaringMember;
      return (O) this;
   }
}
