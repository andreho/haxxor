package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxConstants;
import net.andreho.haxxor.spec.api.HxEnum;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spec.impl.annotation.BooleanAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.ByteAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.CharacterAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.ClassAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.DoubleAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.EnumAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.FloatAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.IntegerAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.LongAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.ShortAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.StringAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.SubAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.BooleanArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.ByteArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.CharacterArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.ClassArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.DoubleArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.EmptyArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.EnumArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.FloatArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.IntegerArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.LongArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.ShortArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.StringArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.SubAnnotationArrayAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotationImpl
    extends HxOwnedImpl<HxAnnotation>
    implements HxAnnotation {

  private static final Class<Target> TARGET_ANNOTATION_TYPE = Target.class;

  protected final HxType type;
  protected final boolean visible;
  protected Map<String, HxAnnotationAttribute<?, ?>> attributeMap;

  public HxAnnotationImpl(HxType type,
                          boolean visible) {
    this(type, visible, Collections.emptyMap());
  }

  public HxAnnotationImpl(HxType type,
                          boolean visible,
                          Map<String, HxAnnotationAttribute<?, ?>> attributeMap) {
    this.type = type;
    this.attributeMap = attributeMap;
    this.visible = visible;
  }

  protected HxAnnotationImpl(HxAnnotationImpl prototype) {
    this.declaringMember = null;

    this.type = prototype.type;
    this.visible = prototype.visible;
    if(prototype.attributeMap.isEmpty()) {
      this.attributeMap = Collections.emptyMap();
    } else {
      this.attributeMap = new LinkedHashMap<>(prototype.attributeMap.size());
      for (HxAnnotationAttribute<?, ?> entry : prototype.attributeMap.values()) {
        set(entry.getName(), entry.clone());
      }
    }
  }

  @Override
  public RetentionPolicy getRetention() {
    if(visible) {
      return RetentionPolicy.RUNTIME;
    }
    return RetentionPolicy.CLASS;
  }

  @Override
  public ElementType[] getElementTypes() {
    Optional<HxAnnotation> targetAnnotation = getType().getAnnotation(TARGET_ANNOTATION_TYPE);
    if(targetAnnotation.isPresent()) {
      HxAnnotation annotation = targetAnnotation.get();
      HxEnum[] hxEnums = annotation.getAttribute("value");
      return HxEnum.toEnumArray(ElementType.class, hxEnums);
    }
    return new ElementType[0];
  }

  @Override
  public boolean isVisible() {
    return visible;
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public Map<String, HxAnnotationAttribute<?, ?>> getAttributeMap() {
    return attributeMap;
  }

  @Override
  public HxAnnotation clone() {
    return new HxAnnotationImpl(this);
  }

  @Override
  public <V> V getAttribute(String name) {
    final HxAnnotationAttribute<?, ?> attribute = attributeMap.get(name);

    if (attribute != null) {
      return (V) attribute.getValue();
    }

    return getDefaultAttribute(name);
  }

  @Override
  public <V> V getAttribute(final String name,
                            final V defaultValue) {
    Object attribute = getAttribute(name);
    if(attribute == null || attribute == HxConstants.EMPTY_ARRAY) {
      return defaultValue;
    }
    return (V) attribute;
  }

  @Override
  public <V> V getAttribute(final String name,
                            final Class<V> type) {
    Object attribute = getAttribute(name);
    //Sometimes possible
    if(type.isArray() && attribute == HxConstants.EMPTY_ARRAY) {
      attribute = Array.newInstance(type.getComponentType(), 0);
    }
    return type.cast(attribute);
  }

  @Override
  public <V> V getDefaultAttribute(final String name) {
    HxMethod method =
        getType().findMethod(name)
                 .orElseThrow(() ->
                                  new IllegalArgumentException(
                                      "Method for annotation's attribute not found: " +name
                                  )
                 );
    return (V) method.getDefaultValue();
  }

  private HxAnnotation set(String name,
                           HxAnnotationAttribute<?, ?> entry) {
    Map<String, HxAnnotationAttribute<?, ?>> attributeMap = this.attributeMap;
    if(attributeMap == Collections.EMPTY_MAP) {
      this.attributeMap = attributeMap = new LinkedHashMap<>();
    }
    attributeMap.put(name, entry);
    return this;
  }

  private HxTypeReference reference(String classname) {
    return getHaxxor().reference(classname);
  }

  @Override
  public int size() {
    return this.attributeMap.size();
  }

  @Override
  public boolean hasAttribute(final String name) {
    return this.attributeMap.containsKey(name);
  }

  @Override
  public boolean hasDefaultAttribute(final String name) {
    return getType().hasMethod(name);
  }

  @Override
  public HxAnnotation attribute(String name,
                                boolean value) {
    return set(name, new BooleanAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                byte value) {
    return set(name, new ByteAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                char value) {
    return set(name, new CharacterAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                short value) {
    return set(name, new ShortAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                int value) {
    return set(name, new IntegerAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                float value) {
    return set(name, new FloatAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                long value) {
    return set(name, new LongAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                double value) {
    return set(name, new DoubleAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                String value) {
    return set(name, new StringAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                HxEnum value) {
    return set(name, new EnumAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(final String name,
                                final Class<?> value) {
    return attribute(name, reference(value.getName()));
  }

  @Override
  public <E extends Enum<E>> HxAnnotation attribute(String name,
                                                    E value) {
    return set(name, new EnumAnnotationAttribute(
        name,
        HxEnum.toHxEnum(getType().getHaxxor(), value))
    );
  }

  @Override
  public HxAnnotation attribute(String name,
                                HxType value) {
    return set(name, new ClassAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                HxAnnotation value) {
    return set(name, new SubAnnotationAttribute(name, value));
  }

  @Override
  public HxAnnotation attribute(String name,
                                boolean[] values) {
    return set(name, new BooleanArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                byte[] values) {
    return set(name, new ByteArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                char[] values) {
    return set(name, new CharacterArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                short[] values) {
    return set(name, new ShortArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                int[] values) {
    return set(name, new IntegerArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                float[] values) {
    return set(name, new FloatArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                long[] values) {
    return set(name, new LongArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                double[] values) {
    return set(name, new DoubleArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                String[] values) {
    return set(name, new StringArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                HxEnum[] values) {
    return set(name, new EnumArrayAnnotationAttribute(name, values));
  }

  @Override
  public <E extends Enum<E>> HxAnnotation attribute(String name,
                                                    E[] values) {
    return set(
        name,
        new EnumArrayAnnotationAttribute(
            name,
            HxEnum.toHxEnumArray(getType().getHaxxor(), values)
        )
    );
  }

  @Override
  public HxAnnotation attribute(final String name,
                                final Class<?>[] values) {
    final Haxxor haxxor = type.getHaxxor();
    final HxType[] hxTypes =
        Arrays.stream(values)
              .map(cls -> haxxor.reference(cls.getName()))
              .toArray(HxType[]::new);
    return attribute(name, hxTypes);
  }

  @Override
  public HxAnnotation attribute(String name,
                                HxType[] values) {
    return set(name, new ClassArrayAnnotationAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(String name,
                                HxAnnotation[] values) {
    return set(name, new SubAnnotationArrayAttribute(name, values));
  }

  @Override
  public HxAnnotation attribute(final String name,
                                final HxConstants.EmptyArray[] value) {
    return set(name, new EmptyArrayAnnotationAttribute(name, value));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxAnnotation)) {
      return false;
    }

    final HxAnnotation that = (HxAnnotation) o;
    return type.equals(that.getType()) && hasEqualAttributes(that);
  }

  private boolean hasEqualAttributes(HxAnnotation other) {
    if(this.attributeMap.size() != other.getAttributeMap().size()) {
      return false;
    }
    for (Map.Entry<String, HxAnnotationAttribute<?, ?>> entry : getAttributeMap().entrySet()) {
      final HxAnnotationAttribute<?, ?> attribute = entry.getValue();
      if(!other.hasAttribute(attribute.getName()) ||
         !attribute.hasValue(other.getAttribute(attribute.getName()))) {

        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return type.hashCode();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("{\"annotationType\": ").append(getType().getName());

    for (Map.Entry<String, HxAnnotationAttribute<?, ?>> entry : getAttributeMap().entrySet()) {
      final HxAnnotationAttribute<?, ?> attribute = entry.getValue();
      builder.append(",\"").append(attribute.getName()).append("\": ").append(attribute);
    }

    return builder.append("\n}").toString();
  }
}
