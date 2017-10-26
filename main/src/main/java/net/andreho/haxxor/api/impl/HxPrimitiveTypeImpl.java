package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeReference;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxPrimitiveTypeImpl
    extends HxAbstractType
    implements HxType {

  private static final int PRIMITIVE_MODIFIERS = Modifiers.PUBLIC.toBit() |
                                                Modifiers.FINAL.toBit() |
                                                Modifiers.ABSTRACT.toBit();
  private volatile HxTypeReference reference;

  public HxPrimitiveTypeImpl(final Haxxor haxxor,
                             final String name) {
    super(haxxor, name);
    this.modifiers = PRIMITIVE_MODIFIERS;
  }

  @Override
  public boolean isPrimitive() {
    return true;
  }

  @Override
  public HxType setDeclaringMember(HxMember declaringMember) {
    return this;
  }

  @Override
  public HxType setModifiers(int modifiers) {
    return this;
  }

  @Override
  public Map<String, HxAnnotation> getAnnotations() {
    return HxAnnotated.DEFAULT_ANNOTATION_MAP;
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

  @Override
  public Class<?> loadClass(final ClassLoader classLoader)
  throws ClassNotFoundException {
    switch (getName()) {
      case "void": return Void.TYPE;
      case "boolean": return Boolean.TYPE;
      case "byte": return Byte.TYPE;
      case "short": return Short.TYPE;
      case "char": return Character.TYPE;
      case "int": return Integer.TYPE;
      case "float": return Float.TYPE;
      case "long": return Long.TYPE;
      case "double": return Double.TYPE;
      default:
        throw new IllegalStateException();
    }
  }
}
