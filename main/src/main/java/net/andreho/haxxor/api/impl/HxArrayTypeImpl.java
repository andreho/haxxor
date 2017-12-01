package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.Version;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxArrayTypeImpl
    extends HxAbstractType
    implements HxType {

  private static final int ARRAY_MODIFIERS =
      Modifiers.PUBLIC.toBit() | Modifiers.FINAL.toBit() | Modifiers.ABSTRACT.toBit();
  private static final Class<Serializable> SERIALIZABLE_CLASS = Serializable.class;
  private static final Class<Cloneable> CLONEABLE_CLASS = Cloneable.class;

  private final List<HxType> interfaces;

  public HxArrayTypeImpl(final Hx haxxor, final String arrayTypeName) {
    super(haxxor, arrayTypeName);

    this.modifiers = ARRAY_MODIFIERS;
    this.interfaces = Collections.unmodifiableList(
        Arrays.asList(
            haxxor.reference(CLONEABLE_CLASS.getName()),
            haxxor.reference(SERIALIZABLE_CLASS.getName())
        )
    );
  }

  @Override
  public List<HxType> getInterfaces() {
    return this.interfaces;
  }

  @Override
  public Optional<HxType> getSuperType() {
    return Optional.of(getHaxxor().reference("java.lang.Object"));
  }

  @Override
  public Version getVersion() {
    return getComponentType().get().getVersion();
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
  public HxType setAnnotations(Collection<HxAnnotation> annotations) {
    return this;
  }

  @Override
  public Map<String, HxAnnotation> getAnnotations() {
    return DEFAULT_ANNOTATION_MAP;
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
}
