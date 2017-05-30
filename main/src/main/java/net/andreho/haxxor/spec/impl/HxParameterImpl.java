package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.HxParameter;
import net.andreho.haxxor.spec.HxParameterizable;
import net.andreho.haxxor.spec.HxType;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxParameterImpl extends HxAnnotatedImpl<HxParameter> implements HxParameter {
   private final int index;
   private String name;
   private HxType type;

   public HxParameterImpl(int index) {
      if(index < 0) {
         throw new IllegalArgumentException("Invalid parameter's index: "+index);
      }
      this.index = index;
   }

   public HxParameterImpl(HxParameterizable owner, int index) {
      this(index);
      this.setDeclaringMember(owner);
   }

   public HxParameterImpl(HxParameterizable owner, int index, HxType type) {
      this(index);
      this.setDeclaringMember(owner);
      this.setType(type);
   }

   @Override
   public int getIndex() {
      return index;
   }

   @Override
   public String getName() {
      final String name = this.name;
      return name != null ?  name : "arg" + index;
   }

   @Override
   public HxParameter setName(final String name) {
      this.name = name;
      return this;
   }

   @Override
   public HxType getType() {
      return getDeclaringMember().getParameterTypeAt(getIndex());
   }

   @Override
   public HxParameter setType(final HxType type) {
      this.type = type;
      return this;
   }

   @Override
   public HxParameterizable getDeclaringMember() {
      return (HxParameterizable) super.getDeclaringMember();
   }
}
