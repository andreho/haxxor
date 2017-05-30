package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.HxEnum;
import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;

import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class EnumArrayAnnotationEntry<E extends Enum<E>> extends AbstractAnnotationEntry<HxEnum[], E[]> {
   private volatile E[] enumArray;

   public EnumArrayAnnotationEntry(final String name, final HxEnum... values) {
      super(name, Objects.requireNonNull(values, "Value is null."));
   }

   @Override
   public E[] original(Class<?> type) {
      return HxEnum.toEnumArray((Class<E>) type, get());
   }

   @Override
   public void set(final HxEnum[] value) {
      super.set(value);
      this.enumArray = null;
   }
}
