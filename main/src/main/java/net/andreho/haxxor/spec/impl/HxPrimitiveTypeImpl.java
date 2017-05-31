package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxPrimitiveTypeImpl
    extends HxAbstractType
    implements HxType {
  //----------------------------------------------------------------------------------------------------------------

  public HxPrimitiveTypeImpl(final Haxxor haxxor, final String name) {
    super(haxxor, name);
    this.modifiers = 0x80000000 | //special bit for primitive types
                     Modifiers.PUBLIC.toBit() |
                     Modifiers.FINAL.toBit() |
                     Modifiers.SUPER.toBit();
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public boolean isPrimitive() {
    return true;
  }

  @Override
  public HxType getSuperType() {
    return null;
  }

  @Override
  public HxType setDeclaringMember(HxMember declaringMember) {
    return this;
  }

  @Override
  public HxType setModifiers(HxModifier... modifiers) {
    return this;
  }

  @Override
  public HxType setModifiers(int modifiers) {
    return this;
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return Collections.emptySet();
  }

  @Override
  public HxType setAnnotations(Collection<HxAnnotation> annotations) {
    return this;
  }

  @Override
  public HxType addAnnotation(HxAnnotation annotation) {
    return this;
  }

  @Override
  public HxType replaceAnnotation(HxAnnotation annotation) {
    return this;
  }

  @Override
  public HxType setAnnotations(HxAnnotation... annotations) {
    return this;
  }

  @Override
  public HxType removeAnnotation(HxAnnotation annotation) {
    return this;
  }

  //----------------------------------------------------------------------------------------------------------------
}
