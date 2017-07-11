package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class ObjectRedefinition<T> extends Redefinition<T> {
   private final T value;

   ObjectRedefinition(final T value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      final T value = this.value;
      if(value == null) {
         return true;
      }
      return type.isAssignableFrom(value.getClass());
   }

   @Override
   public T toObject() {
      return this.value;
   }
}
