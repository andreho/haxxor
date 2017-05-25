package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.HxConstructor;
import net.andreho.haxxor.spec.HxType;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxConstructorImpl extends HxParameterizableImpl<HxConstructor> implements HxConstructor {
   public HxConstructorImpl(HxType declaringType, HxType... parameterTypes) {
      super();
      setDeclaringMember(declaringType);
      setParameterTypes(parameterTypes);
   }

   protected HxConstructorImpl() {
      super();
   }

   @Override
   public String toString() {
      return getDeclaringMember() + super.toString() + "void";
   }
}
