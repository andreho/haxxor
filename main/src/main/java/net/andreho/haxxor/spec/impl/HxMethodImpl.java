package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxType;

import java.util.Objects;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxMethodImpl extends HxParameterizableImpl<HxMethod> implements HxMethod {
   protected final String name;
   protected Object defaultValue;
   protected HxType returnType;

   public HxMethodImpl(final HxType owner,
                       final String name,
                       final HxType returnType,
                       final HxType... parameters) {
      this(name);
      setDeclaringMember(owner);
      setReturnType(returnType);

   }

   protected HxMethodImpl(final String name) {
      this.name = name;
      if (name == null || name.isEmpty()) {
         throw new IllegalArgumentException("Given method name is either null or empty.");
      }
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public HxType getReturnType() {
      return returnType;
   }

   @Override
   public Object getDefaultValue() {
      return defaultValue;
   }

   @Override
   public HxMethod setReturnType(HxType returnType) {
      this.returnType = returnType;
      return this;
   }

   @Override
   public HxMethod setDefaultValue(Object value) {
      this.defaultValue = value;
      return this;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HxMethod)) {
         return false;
      }

      final HxMethod other = (HxMethod) o;

      if (!Objects.equals(this.getName(), other.getName())) {
         return false;
      }

      return super.equals(o);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(getName()) * 31 + super.hashCode();
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public String toString() {
      return getDeclaringMember() + "." + getName() + super.toString() + getReturnType().getJavaName();
   }
}
