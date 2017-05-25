package net.andreho.haxxor.spec.entries;

import net.andreho.haxxor.spec.HxEnum;

import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class EnumAnnotationEntry extends AbstractAnnotationEntry<HxEnum, Enum> {
   public EnumAnnotationEntry(final String name, final HxEnum value) {
      super(name, Objects.requireNonNull(value, "Value is null."));
   }

   @Override
   public Enum original() {
      return get().get();
   }
}
