package net.andreho.haxxor.cgen.instr.conversion;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class CHECKCAST extends AbstractStringOperandInstruction {
   public CHECKCAST(String internalType) {
      super(Opcodes.CHECKCAST, internalType);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.CHECKCAST(getOperand());
   }

   @Override
   public List<Object> apply(final Context context) {
      return context.getPush().prepare().push(getOperand()).get();
   }

   @Override
   public int getStackPopCount() {
      return 1;
   }

   @Override
   public String toString() {
      return super.toString() + " " + getOperand();
   }
}
