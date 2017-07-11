package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class ShortRedefinition extends Redefinition<Short> {
   private static final Class<Short> TYPE = Short.class;
   private final short value;

   ShortRedefinition(final short value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Short.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public short toShort() {
      return this.value;
   }

   @Override
   public Short toObject() {
      return toShort();
   }
}
