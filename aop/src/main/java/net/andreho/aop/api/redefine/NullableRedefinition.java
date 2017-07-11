package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class NullableRedefinition<T> extends Redefinition<T> {
   static NullableRedefinition INSTANCE = new NullableRedefinition();

   private NullableRedefinition() {
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return !type.isPrimitive();
   }

   @Override
   public T toObject() {
      return null;
   }
}
