package net.andreho.haxxor.spec;

import net.andreho.haxxor.Haxxor;

import java.lang.reflect.Array;

/**
 * <br/>Created by a.hofmann on 04.06.2015.<br/>
 */
public class HxEnum {

   private final HxType type;
   private final String name;
   private volatile Enum<?> fetchedEnum;

   //----------------------------------------------------------------------------------------------------------------

   public HxEnum(Haxxor haxxor, Enum e) {
      this(haxxor.reference(e.getClass().getName()), e.name());
   }

   public HxEnum(HxType type, String name) {
      this.type = type;
      this.name = name;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param haxxor
    * @param enumConstant
    * @param <E>
    * @return
    */
   public static <E extends Enum<E>> HxEnum toHxEnum(Haxxor haxxor, E enumConstant) {
      return new HxEnum(haxxor, enumConstant);
   }

   /**
    * Constructs an array with requested enum instances
    *
    * @param enumType that is expected
    * @param array    of enum-refs
    * @return array with requested enum instances
    */
   public static Enum[] toEnumArray(HxType enumType, HxEnum... array) {
      final Class<Enum> enumClass = (Class<Enum>) enumType.loadClass();
      Enum[] result = (Enum[]) Array.newInstance(enumClass, array.length);

      for (int i = 0; i < array.length; i++) {
         result[i] = array[i].get();
      }

      return result;
   }

   /**
    * Transforms given enum array to an analogous hx-enum array
    * @param haxxor to use
    * @param enums to transform
    * @return a hx-enum array that is equal in meaning to the given one
    */
   public static HxEnum[] toHxEnumArray(Haxxor haxxor, Enum... enums) {
      HxEnum[] result = new HxEnum[enums.length];

      for (int i = 0; i < enums.length; i++) {
         result[i] = new HxEnum(haxxor, enums[i]);
      }

      return result;
   }

   //----------------------------------------------------------------------------------------------------------------

   public HxType getType() {
      return type;
   }

   public String getName() {
      return name;
   }

   public Enum get() {
      Enum<?> e = this.fetchedEnum;
      if (e != null) {
         return e;
      }

      final Class<Enum> enumClass = (Class<Enum>) type.loadClass();
      e = Enum.valueOf(enumClass, name);
      this.fetchedEnum = e;
      return e;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public String toString() {
      return type + "." + name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      HxEnum hxEnum = (HxEnum) o;

      if (type != null ? !type.equals(hxEnum.type) : hxEnum.type != null) {
         return false;
      }
      return !(name != null ? !name.equals(hxEnum.name) : hxEnum.name != null);
   }

   @Override
   public int hashCode() {
      int result = type != null ? type.hashCode() : 0;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      return result;
   }
}
