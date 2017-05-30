package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxConstructor;
import net.andreho.haxxor.spec.HxField;
import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxParameter;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spi.HxElementFactory;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 20:47.
 */
public class DefaultHxElementFactory implements HxElementFactory {
   @Override
   public HxType createType(final Haxxor haxxor, final String typeName) {
      return null;
   }

   @Override
   public HxType createReference(final Haxxor haxxor, final String typeName) {
      return null;
   }

   @Override
   public HxField createField(final Haxxor haxxor, final String typeName, final String name) {
      return null;
   }

   @Override
   public HxConstructor createConstructor(final Haxxor haxxor, final String... parameterTypes) {
      return null;
   }

   @Override
   public HxConstructor createConstructorReference(final Haxxor haxxor, final String... parameterTypes) {
      return null;
   }

   @Override
   public HxMethod createMethod(final Haxxor haxxor, final String name, final String returnType,
                                final String... parameterTypes) {
      return null;
   }

   @Override
   public HxMethod createMethodReference(final Haxxor haxxor, final String name, final String returnType,
                                         final String... parameterTypes) {
      return null;
   }

   @Override
   public HxParameter createParameter(final Haxxor haxxor, final String typeName) {
      return null;
   }

   @Override
   public HxAnnotation createAnnotation(final Haxxor haxxor, final String typeName, final boolean visible) {
      return null;
   }
}
