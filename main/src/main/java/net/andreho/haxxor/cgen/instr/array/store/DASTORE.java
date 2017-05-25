package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DASTORE extends AbstractArrayStoreInstruction {
   public DASTORE() {
      super(Opcodes.DASTORE);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.DASTORE();
   }

   @Override
   protected void checkArrayType(final Object arrayType, final int depth) {
      super.checkArrayType(arrayType, depth);
      String array = arrayType.toString();
      if (!"[D".equals(array)) {
         throw new IllegalArgumentException("Expected an double[] array type, but got: " + arrayType);
      }
   }
}
