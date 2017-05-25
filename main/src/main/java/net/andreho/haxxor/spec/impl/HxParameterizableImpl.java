package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxCode;
import net.andreho.haxxor.spec.HxMethod.Modifiers;
import net.andreho.haxxor.spec.HxParameter;
import net.andreho.haxxor.spec.HxParameterizable;
import net.andreho.haxxor.spec.HxType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public abstract class HxParameterizableImpl<P extends HxParameterizable<P>> extends HxAnnotatedImpl<P> implements HxParameterizable<P> {
   protected List<HxParameter> parameters;
   protected List<HxType> exceptions;
   protected String genericSignature;
   protected HxCode code;

   public HxParameterizableImpl() {
      super();
      this.setModifiers(Modifiers.PUBLIC.toBit());
   }

   @Override
   public Haxxor getHaxxor() {
      return ((HxType) getDeclaringMember()).getHaxxor();
   }

   @Override
   public boolean hasCode() {
      return this.code != null && this.code.isAvailable();
   }

   @Override
   public HxCode getCode() {
      if (this.code == null) {
         this.code = new HxCodeImpl(this);
      }

      return this.code;
   }

   @Override
   public String toDescriptor() {
      return toDescriptor(new StringBuilder()).toString();
   }

   @Override
   public List<HxParameter> getParameters() {
      return this.parameters;
   }

   @Override
   public P setParameters(final List<HxParameter> parameters) {
      this.parameters = parameters;
      return (P) this;
   }

   @Override
   public P setExceptionTypes(List<HxType> exceptionTypes) {
      this.exceptions = exceptionTypes;
      return (P) this;
   }

   @Override
   public HxParameter getParameterAt(final int index) {
      return parameters.get(index);
   }

   @Override
   public P setParameterAt(final int index, final HxParameter parameter) {
      this.parameters.set(index, parameter);
      parameter.setDeclaringMember(this);
      return (P) this;
   }

   @Override
   public List<HxType> getExceptionTypes() {
      return exceptions;
   }

   @Override
   public String getGenericSignature() {
      return genericSignature;
   }

   @Override
   public P setGenericSignature(String genericSignature) {
      this.genericSignature = Objects.requireNonNull(genericSignature);
      return (P) this;
   }

   @Override
   public Collection<HxParameterizable> getOverriddenMembers() {
      return Collections.emptySet();
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HxParameterizable)) {
         return false;
      }

      final HxParameterizable other = (HxParameterizable) o;
      return getParameters().equals(other.getParameters());
   }

   @Override
   public int hashCode() {
      return getParameters().hashCode();
   }

   @Override
   public String toString() {
      final StringBuilder builder = new StringBuilder("(");

      for (int i = 0, l = getArity() - 1; i <= l; i++) {
         HxType type = getParameterTypeAt(i);
         builder.append(type.getJavaName());

         if (i < l) {
            builder.append(',');
         }
      }

      return builder.append(')').toString();
   }
}
