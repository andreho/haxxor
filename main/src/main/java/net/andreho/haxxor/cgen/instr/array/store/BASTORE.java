package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class BASTORE extends AbstractArrayStoreInstruction {
   public BASTORE() {
      super(Opcodes.BASTORE);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.BASTORE();
   }

   @Override
   protected void checkArrayType(final Object arrayType, final int depth) {
      super.checkArrayType(arrayType, depth);
      String array = arrayType.toString();
      if (!"[Z".equals(array) && !"[B".equals(array)) {
         throw new IllegalArgumentException("Expected an boolean[] or byte[] array type, but got: " + arrayType);
      }
   }

}
