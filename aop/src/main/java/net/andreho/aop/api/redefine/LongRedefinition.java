package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class LongRedefinition extends Redefinition<Long> {
   private static final Class<Long> TYPE = Long.class;
   private final long value;

   LongRedefinition(final long value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Long.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public long toLong() {
      return this.value;
   }

   @Override
   public Long toObject() {
      return toLong();
   }
}
