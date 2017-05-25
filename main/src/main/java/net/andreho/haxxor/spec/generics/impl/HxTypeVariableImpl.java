package net.andreho.haxxor.spec.generics.impl;

import net.andreho.haxxor.spec.HxGeneric;
import net.andreho.haxxor.spec.HxMember;
import net.andreho.haxxor.spec.generics.HxTypeVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Created by andreho on 3/26/16 at 10:16 PM.<br/>
 */
public class HxTypeVariableImpl extends HxAbstractGeneric implements HxTypeVariable {
   private String name;
   private List<HxGeneric> bounds = new ArrayList<>();

   @Override
   public String getName() {
      return name;
   }

   @Override
   public HxTypeVariable setName(String name) {
      this.name = name;
      return this;
   }

   @Override
   public List<HxGeneric> getBounds() {
      return bounds;
   }

   @Override
   public HxTypeVariable setBounds(List<HxGeneric> bounds) {
      this.bounds = bounds;
      return this;
   }

   @Override
   public HxMember getGenericDeclaration() {
      return getDeclaringMember();
   }

   @Override
   public HxTypeVariable setGenericDeclaration(HxMember genericDeclaration) {
      setDeclaringMember(genericDeclaration);
      return this;
   }

   @Override
   public HxTypeVariable attach(final HxGeneric generic) {
      getBounds().add(generic);
      return this;
   }
}
