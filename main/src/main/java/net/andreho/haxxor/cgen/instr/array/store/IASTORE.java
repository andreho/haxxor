package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class IASTORE extends AbstractArrayStoreInstruction {
   public IASTORE() {
      super(Opcodes.IASTORE);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.IASTORE();
   }

   @Override
   protected void checkArrayType(final Object arrayType, final int depth) {
      super.checkArrayType(arrayType, depth);
      String array = arrayType.toString();
      if (!"[I".equals(array)) {
         throw new IllegalArgumentException("Expected an int[] array type, but got: " + arrayType);
      }
   }
}
