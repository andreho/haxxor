package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
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
import net.andreho.haxxor.spec.impl.annotation.arrays.EnumArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.FloatArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.IntegerArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.LongArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.ShortArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.StringArrayAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.arrays.SubAnnotationArrayAttribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotationImpl
    extends HxOwnedImpl<HxAnnotation>
    implements HxAnnotation {

  protected final HxType type;
  protected final Map<String, HxAnnotationAttribute<?, ?>> values;
  protected boolean visible;
//  protected volatile Annotation view;

  public HxAnnotationImpl(HxType type,
                          boolean visible) {
    this(type, visible, new HashMap<>());
  }

  public HxAnnotationImpl(HxType type,
                          boolean visible,
                          Map<String, HxAnnotationAttribute<?, ?>> values) {
    this.type = type;
    this.values = values;
    this.visible = visible;
  }

  public HxAnnotationImpl(HxAnnotationImpl prototype) {
    this.declaringMember = null;

    this.type = prototype.type;
    this.visible = prototype.visible;
    this.values = new HashMap<>(prototype.values.size());

    for (HxAnnotationAttribute<?, ?> entry : prototype.values.values()) {
      set(entry.getName(), entry.clone());
    }
  }

//  @Override
//  public <A extends Annotation> A getView(final ClassLoader classLoader) {
//    if (this.view == null) {
//      //TODO
//    }
//    return (A) view;
//  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public Map<String, HxAnnotationAttribute<?, ?>> getValues() {
    return values;
  }

  @Override
  public HxAnnotation clone() {
    return new HxAnnotationImpl(this);
  }

  @Override
  public <V> V attribute(String name) {
    final HxAnnotationAttribute<?, ?> value = values.get(name);
    if(value == null) {
      HxMethod method = getType().getMethod(name);
      if(method == null) {
        throw new IllegalArgumentException("Annotation's method not found: "+name);
      }
      return (V) method.getDefaultValue();
    }
    return (V) value.getValue();
  }

  private HxAnnotation set(String name,
                           HxAnnotationAttribute<?, ?> entry) {
    values.put(name, entry);
    return this;
  }

  private HxTypeReference reference(String classname) {
    return getHaxxor().reference(classname);
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxAnnotation)) {
      return false;
    }

    HxAnnotation that = (HxAnnotation) o;
    return type.equals(that.getType());
  }

  @Override
  public int hashCode() {
    return type.hashCode();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder(type.getName()).append(" {\n");

    for (Iterator<Map.Entry<String, HxAnnotationAttribute<?, ?>>> iterator =
         getValues().entrySet()
                    .iterator(); iterator.hasNext(); ) {

      final Map.Entry<String, HxAnnotationAttribute<?, ?>> entry = iterator.next();
      final String key = entry.getKey();
      final HxAnnotationAttribute<?, ?> value = entry.getValue();

      builder.append('"')
             .append(key)
             .append("\": ")
             .append(value.getValue());
      if (iterator.hasNext()) {
        builder.append('\n');
      }
    }

    return builder.append("\n}")
                  .toString();
  }
}
