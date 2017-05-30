package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.HxConstructor;
import net.andreho.haxxor.spec.HxType;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxConstructorImpl extends HxParameterizableImpl<HxConstructor> implements HxConstructor {
   public HxConstructorImpl() {
      super();
   }

   public HxConstructorImpl(HxType... parameterTypes) {
      this(null, (HxType[]) parameterTypes);
   }

   public HxConstructorImpl(HxType declaringType, HxType... parameterTypes) {
      super();
      setModifiers(HxConstructor.Modifiers.PUBLIC.toBit());
      setDeclaringMember(declaringType);
      setParameterTypes(parameterTypes);
   }

   @Override
   public String toString() {
      return getDeclaringMember() + super.toString() + "void";
   }
}
