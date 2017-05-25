package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class FASTORE extends AbstractArrayStoreInstruction {
   public FASTORE() {
      super(Opcodes.FASTORE);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.FASTORE();
   }

   @Override
   protected void checkArrayType(final Object arrayType, final int depth) {
      super.checkArrayType(arrayType, depth);
      String array = arrayType.toString();
      if (!"[F".equals(array)) {
         throw new IllegalArgumentException("Expected an float[] array type, but got: " + arrayType);
      }
   }

}
