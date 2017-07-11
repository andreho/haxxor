package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class IntRedefinition extends Redefinition<Integer> {
   private static final Class<Integer> TYPE = Integer.class;
   private final int value;

   IntRedefinition(final int value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Integer.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public int toInt() {
      return this.value;
   }

   @Override
   public Integer toObject() {
      return toInt();
   }
}
