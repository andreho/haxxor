package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class CASTORE extends AbstractArrayStoreInstruction {
   public CASTORE() {
      super(Opcodes.CASTORE);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.CASTORE();
   }

   @Override
   protected void checkArrayType(final Object arrayType, final int depth) {
      super.checkArrayType(arrayType, depth);
      String array = arrayType.toString();
      if (!"[C".equals(array)) {
         throw new IllegalArgumentException("Expected an char[] array type, but got: " + arrayType);
      }
   }
}
