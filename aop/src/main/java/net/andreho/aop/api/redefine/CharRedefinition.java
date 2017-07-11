package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class CharRedefinition extends Redefinition<Character> {
   private static final Class<Character> TYPE = Character.class;
   private final char value;

   CharRedefinition(final char value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Character.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public char toChar() {
      return this.value;
   }

   @Override
   public Character toObject() {
      return toChar();
   }
}
