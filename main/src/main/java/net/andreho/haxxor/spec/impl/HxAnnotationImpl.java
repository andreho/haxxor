package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxEnum;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.annotation.AnnotationEntry;
import net.andreho.haxxor.spec.annotation.BooleanAnnotationEntry;
import net.andreho.haxxor.spec.annotation.ByteAnnotationEntry;
import net.andreho.haxxor.spec.annotation.CharacterAnnotationEntry;
import net.andreho.haxxor.spec.annotation.ClassAnnotationEntry;
import net.andreho.haxxor.spec.annotation.DoubleAnnotationEntry;
import net.andreho.haxxor.spec.annotation.EnumAnnotationEntry;
import net.andreho.haxxor.spec.annotation.FloatAnnotationEntry;
import net.andreho.haxxor.spec.annotation.IntegerAnnotationEntry;
import net.andreho.haxxor.spec.annotation.LongAnnotationEntry;
import net.andreho.haxxor.spec.annotation.ShortAnnotationEntry;
import net.andreho.haxxor.spec.annotation.StringAnnotationEntry;
import net.andreho.haxxor.spec.annotation.SubAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.BooleanArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.ByteArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.CharacterArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.ClassArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.DoubleArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.EnumArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.FloatArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.IntegerArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.LongArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.ShortArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.StringArrayAnnotationEntry;
import net.andreho.haxxor.spec.annotation.arrays.SubAnnotationArrayEntry;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotationImpl extends HxOwnedImpl implements HxAnnotation {
   protected final HxType type;
   protected final Map<String, AnnotationEntry<?, ?>> values;
   protected volatile Annotation view;
   protected boolean visible;

   //-----------------------------------------------------------------------------------------------------------------

   public HxAnnotationImpl(HxType type, boolean visible) {
      this(type, visible, new HashMap<>());
   }

   public HxAnnotationImpl(HxType type, boolean visible, Map<String, AnnotationEntry<?, ?>> values) {
      this.type = type;
      this.values = values;
      this.visible = visible;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public <A extends Annotation> A getView(final ClassLoader classLoader) {
      if (this.view == null) {
         //TODO
      }
      return (A) view;
   }

   @Override
   public HxType getType() {
      return type;
   }

   @Override
   public Map<String, AnnotationEntry<?, ?>> getValues() {
      return values;
   }

   @Override
   public <V> V attribute(String name) {
      final AnnotationEntry<?, ?> value = values.get(name);
      return (V) value.get();
   }

   //----------------------------------------------------------------------------------------------------------------

   private HxAnnotation set(String name, AnnotationEntry<?, ?> entry) {
      values.put(name, entry);
      return this;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public HxAnnotation attribute(String name, boolean value) {
      return set(name, new BooleanAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, byte value) {
      return set(name, new ByteAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, char value) {
      return set(name, new CharacterAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, short value) {
      return set(name, new ShortAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, int value) {
      return set(name, new IntegerAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, float value) {
      return set(name, new FloatAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, long value) {
      return set(name, new LongAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, double value) {
      return set(name, new DoubleAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, String value) {
      return set(name, new StringAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, HxEnum value) {
      return set(name, new EnumAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(final String name, final Class<?> value) {
      return attribute(name, this.type.getHaxxor().resolve(value.getName()));
   }


   @Override
   public HxAnnotation attribute(String name, Enum value) {
      return set(name, new EnumAnnotationEntry(name, HxEnum.toHxEnum(getType().getHaxxor(), value)));
   }

   @Override
   public HxAnnotation attribute(String name, HxType value) {
      return set(name, new ClassAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, HxAnnotation value) {
      return set(name, new SubAnnotationEntry(name, value));
   }

   @Override
   public HxAnnotation attribute(String name, boolean[] values) {
      return set(name, new BooleanArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, byte[] values) {
      return set(name, new ByteArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, char[] values) {
      return set(name, new CharacterArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, short[] values) {
      return set(name, new ShortArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, int[] values) {
      return set(name, new IntegerArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, float[] values) {
      return set(name, new FloatArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, long[] values) {
      return set(name, new LongArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, double[] values) {
      return set(name, new DoubleArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, String[] values) {
      return set(name, new StringArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, HxEnum[] values) {
      return set(name, new EnumArrayAnnotationEntry(name, values));
   }

   @Override
   public <E extends Enum<E>> HxAnnotation attribute(String name, E[] values) {
      return set(name,
                 new EnumArrayAnnotationEntry(name, HxEnum.toHxEnumArray(getType().getHaxxor(), values)));
   }

   @Override
   public HxAnnotation attribute(final String name, final Class<?>[] values) {
      final Haxxor haxxor = type.getHaxxor();
      final HxType[] hxTypes =
         Arrays.stream(values)
               .map(cls -> haxxor.reference(cls.getName()))
               .toArray(HxType[]::new);
      return attribute(name, hxTypes);
   }

   @Override
   public HxAnnotation attribute(String name, HxType[] values) {
      return set(name, new ClassArrayAnnotationEntry(name, values));
   }

   @Override
   public HxAnnotation attribute(String name, HxAnnotation[] values) {
      return set(name, new SubAnnotationArrayEntry(name, values));
   }

   //----------------------------------------------------------------------------------------------------------------

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

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public String toString() {
      final StringBuilder builder = new StringBuilder(type.getName()).append(" {\n");

      for (Iterator<Map.Entry<String, AnnotationEntry<?, ?>>> iterator =
           getValues().entrySet().iterator(); iterator.hasNext(); ) {

         final Map.Entry<String, AnnotationEntry<?, ?>> entry = iterator.next();
         final String key = entry.getKey();
         final AnnotationEntry<?, ?> value = entry.getValue();

         builder.append('"').append(key).append("\": ").append(value.get());
         if (iterator.hasNext()) {
            builder.append('\n');
         }
      }

      return builder.append("\n}").toString();
   }
}
