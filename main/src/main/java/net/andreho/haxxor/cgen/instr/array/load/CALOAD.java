package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class CALOAD extends AbstractArrayLoadInstruction {
   public CALOAD() {
      super(Opcodes.CALOAD);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.CALOAD();
   }

   @Override
   protected void checkArrayType(final Object arrayType) {
      super.checkArrayType(arrayType);
      if (!"[C".equals(arrayType)) {
         throw new IllegalArgumentException("Expected an array of current type: char[]");
      }
   }
}
