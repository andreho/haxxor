package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxArrayTypeImpl
    extends HxAbstractType
    implements HxType {

  private static final Class<Serializable> SERIALIZABLE_CLASS = Serializable.class;
  private static final Class<Cloneable> CLONEABLE_CLASS = Cloneable.class;

  private final Collection<HxType> interfaces;

  public HxArrayTypeImpl(final Haxxor haxxor, final String arrayTypeName) {
    super(haxxor, arrayTypeName);

    if (!arrayTypeName.startsWith("[")) {
      throw new IllegalArgumentException("Invalid array type name: " + arrayTypeName);
    }

    this.interfaces = Collections.unmodifiableList(
        Arrays.asList(
            haxxor.createReference(SERIALIZABLE_CLASS.getName()),
            haxxor.createReference(CLONEABLE_CLASS.getName())
        )
    );
  }

  @Override
  public Collection<HxType> getInterfaces() {
    return this.interfaces;
  }

  @Override
  public HxType getSuperType() {
    return getHaxxor().resolve("java/lang/Object");
  }

  @Override
  public Version getVersion() {
    return getComponentType().getVersion();
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
  public HxType setAnnotations(Collection<HxAnnotation> annotations) {
    return this;
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return Collections.emptySet();
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
}
