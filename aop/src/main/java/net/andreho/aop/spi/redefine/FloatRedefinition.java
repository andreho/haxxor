package net.andreho.aop.spi.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class FloatRedefinition extends Redefinition<Float> {
   private static final Class<Float> TYPE = Float.class;
   private final float value;

   public FloatRedefinition(final float value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Float.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public float toFloat() {
      return this.value;
   }

   @Override
   public Float toObject() {
      return toFloat();
   }
}
