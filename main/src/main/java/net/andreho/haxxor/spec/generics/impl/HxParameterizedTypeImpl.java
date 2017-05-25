package net.andreho.haxxor.spec.generics.impl;

import net.andreho.haxxor.spec.HxGeneric;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.generics.HxParameterizedType;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Created by andreho on 3/26/16 at 10:24 PM.<br/>
 */
public class HxParameterizedTypeImpl extends HxAbstractGeneric implements HxParameterizedType {
   //-----------------------------------------------------------------------------------------------------------------

   private HxType rawType;
   private HxGeneric ownerType;
   private List<HxGeneric> actualTypeArguments = new ArrayList<>();

   //-----------------------------------------------------------------------------------------------------------------

   @Override
   public HxType getRawType() {
      return rawType;
   }

   @Override
   public HxParameterizedType setRawType(HxType rawType) {
      this.rawType = rawType;
      return this;
   }

   @Override
   public HxGeneric getOwnerType() {
      return ownerType;
   }

   @Override
   public HxParameterizedType setOwnerType(HxGeneric ownerType) {
      this.ownerType = ownerType;
      return this;
   }

   @Override
   public List<HxGeneric> getActualTypeArguments() {
      return actualTypeArguments;
   }

   @Override
   public HxParameterizedType setActualTypeArguments(List<HxGeneric> actualTypeArguments) {
      this.actualTypeArguments = actualTypeArguments;
      return this;
   }

   @Override
   public HxParameterizedType attach(final HxGeneric generic) {
      this.actualTypeArguments.add(generic);
      return this;
   }
}
