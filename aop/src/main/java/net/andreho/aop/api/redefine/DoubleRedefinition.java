package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class DoubleRedefinition extends Redefinition<Double> {
   private static final Class<Double> TYPE = Double.class;
   private final double value;

   DoubleRedefinition(final double value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Double.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public double toDouble() {
      return this.value;
   }

   @Override
   public Double toObject() {
      return toDouble();
   }
}
