package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxPrimitiveTypeImpl
    extends HxAbstractType
    implements HxType {

  private volatile HxTypeReference reference;

  public HxPrimitiveTypeImpl(final Haxxor haxxor,
                             final String name) {
    super(haxxor, name);
    this.modifiers = 0x80000000 | //special bit for primitive types
                     Modifiers.PUBLIC.toBit() |
                     Modifiers.FINAL.toBit() |
                     Modifiers.SUPER.toBit();
  }

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

  @Override
  public Appendable toDescriptor(final Appendable builder) {
    try {
      switch (getName()) {
        case "void":
          builder.append('V');
          break;
        case "boolean":
          builder.append('Z');
          break;
        case "byte":
          builder.append('B');
          break;
        case "char":
          builder.append('C');
          break;
        case "short":
          builder.append('S');
          break;
        case "int":
          builder.append('I');
          break;
        case "float":
          builder.append('F');
          break;
        case "long":
          builder.append('J');
          break;
        case "double":
          builder.append('D');
          break;
        default:
          throw new IllegalStateException("Not a primitive type: " + getName());
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return builder;
  }

  @Override
  public HxTypeReference toReference() {
    if (reference == null) {
      reference = super.toReference();
    }
    return reference;
  }
}
