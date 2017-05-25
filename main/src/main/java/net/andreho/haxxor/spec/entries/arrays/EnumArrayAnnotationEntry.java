package net.andreho.haxxor.spec.entries.arrays;

import net.andreho.haxxor.spec.HxEnum;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.entries.AbstractAnnotationEntry;

import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class EnumArrayAnnotationEntry extends AbstractAnnotationEntry<HxEnum[], Enum[]> {
   private final HxType enumType;
   private volatile Enum[] enumArray;

   public EnumArrayAnnotationEntry(final String name, final HxType enumType, final HxEnum... values) {
      super(name, Objects.requireNonNull(values, "Value is null."));
      this.enumType = Objects.requireNonNull(enumType, "Enum-type is null.");
   }

   @Override
   public Enum[] original() {
      if (this.enumArray == null) {
         this.enumArray = HxEnum.toEnumArray(this.enumType, get());
      }
      return this.enumArray;
   }

   @Override
   public void set(final HxEnum[] value) {
      super.set(value);
      this.enumArray = null;
   }
}
