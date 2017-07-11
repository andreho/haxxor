package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class BooleanRedefinition extends Redefinition<Boolean> {
   private static final Class<Boolean> TYPE = Boolean.class;
   private final boolean value;

   BooleanRedefinition(final boolean value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Boolean.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public boolean toBoolean() {
      return this.value;
   }

   @Override
   public Boolean toObject() {
      return toBoolean();
   }
}

