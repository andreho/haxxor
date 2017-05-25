package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxField;
import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxType;

import java.util.Objects;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxFieldImpl extends HxAnnotatedImpl<HxField> implements HxField {
   protected String name;
   protected HxType type;
   protected Object defaultValue;
   protected String genericSignature;

   public HxFieldImpl(HxType declaringType, HxType type, String name) {
      super();
      this.setModifiers(HxMethod.Modifiers.PUBLIC.toBit());

      if (declaringType == null) {
         throw new IllegalArgumentException("Declaring owner is null.");
      }

      if (type == null) {
         throw new IllegalArgumentException("Type is null.");
      }

      if (name == null || name.isEmpty()) {
         throw new IllegalArgumentException("Name is either null or empty.");
      }

      this.declaringMember = declaringType;
      this.type = type;
      this.name = name;
   }

   @Override
   public Haxxor getHaxxor() {
      return this.type.getHaxxor();
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public HxField setName(String name) {
      if (name == null || name.isEmpty()) {
         throw new IllegalArgumentException("Field name can't be neither null nor empty.");
      }

      if(getDeclaringMember() != null) {
         HxType type = getDeclaringMember();
         type.removeField(this);
      }

      this.name = name;
      return this;
   }

   @Override
   public HxType getType() {
      return type;
   }

   @Override
   public HxField setType(HxType type) {
      if (type == null) {
         throw new IllegalArgumentException("Type can't be null");
      }

      this.type = type;
      return this;
   }

   @Override
   public Object getDefaultValue() {
      return defaultValue;
   }

   @Override
   public HxField setDefaultValue(Object value) {
      this.defaultValue = value;
      return this;
   }

   @Override
   public String getGenericSignature() {
      return genericSignature;
   }

   @Override
   public HxField setGenericSignature(String genericSignature) {
      this.genericSignature = genericSignature;
      return this;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HxField)) {
         return false;
      }

      HxField other = (HxField) o;

      return Objects.equals(getName(), other.getName());
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(getName());
   }

   @Override
   public String toString() {
      return getDeclaringMember() + "." + getName();
   }
}
