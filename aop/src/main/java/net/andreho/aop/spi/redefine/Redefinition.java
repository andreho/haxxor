package net.andreho.aop.spi.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:49.
 */
public abstract class Redefinition<T> {
   private static final Redefinition NONE = new Redefinition() {
      @Override
      public boolean isNeeded() {
         return false;
      }

      @Override
      public boolean isCompatibleWith(final Class type) {
         return type != null && !type.isPrimitive();
      }
   };

   /**
    * @param <T>
    * @return
    */
   public static <T> Redefinition<T> none() {
      return NONE;
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Boolean> as(boolean value) {
      return new BooleanRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Byte> as(byte value) {
      return new ByteRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Short> as(short value) {
      return new ShortRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Character> as(char value) {
      return new CharRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Integer> as(int value) {
      return new IntRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Float> as(float value) {
      return new FloatRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Long> as(long value) {
      return new LongRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Double> as(double value) {
      return new DoubleRedefinition(value);
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Boolean> as(Boolean value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.booleanValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Byte> as(Byte value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.byteValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Short> as(Short value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.shortValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Character> as(Character value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.charValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Integer> as(Integer value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.intValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Float> as(Float value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.floatValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Long> as(Long value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.longValue());
   }

   /**
    * @param value
    * @return
    */
   public static Redefinition<Double> as(Double value) {
      return value == null? new ObjectRedefinition<>(value) : as(value.doubleValue());
   }

   /**
    * @param value
    * @return
    */
   public static <T> Redefinition<T> as(T value) {
      return new ObjectRedefinition<>(value);
   }

   public boolean isNeeded() {
      return true;
   }

   public boolean toBoolean() {
      throw new UnsupportedOperationException();
   }

   public byte toByte() {
      throw new UnsupportedOperationException();
   }

   public short toShort() {
      throw new UnsupportedOperationException();
   }

   public char toChar() {
      throw new UnsupportedOperationException();
   }

   public int toInt() {
      throw new UnsupportedOperationException();
   }

   public float toFloat() {
      throw new UnsupportedOperationException();
   }

   public long toLong() {
      throw new UnsupportedOperationException();
   }

   public double toDouble() {
      throw new UnsupportedOperationException();
   }

   public T toObject() {
      throw new UnsupportedOperationException();
   }

   public abstract boolean isCompatibleWith(Class<?> type);

   @Override
   public String toString() {
      return "Redefinition {" + String.valueOf(toObject()) + "}";
   }
}
