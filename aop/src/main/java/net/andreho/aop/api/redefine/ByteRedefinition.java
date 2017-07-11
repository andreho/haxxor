package net.andreho.aop.api.redefine;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 17:50.
 */
final class ByteRedefinition extends Redefinition<Byte> {
   private static final Class<Byte> TYPE = Byte.class;
   private final byte value;

   ByteRedefinition(final byte value) {
      this.value = value;
   }

   @Override
   public boolean isCompatibleWith(final Class<?> type) {
      return Byte.TYPE == type ||
             type.isAssignableFrom(TYPE);
   }

   @Override
   public byte toByte() {
      return this.value;
   }

   @Override
   public Byte toObject() {
      return toByte();
   }
}
