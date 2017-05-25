package net.andreho.haxxor.cgen.instr.sync;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class MONITOREXIT extends AbstractZeroOperandInstruction {
   public MONITOREXIT() {
      super(Opcodes.MONITOREXIT);
   }

   @Override
   public List<Object> apply(final Context context) {
      return NO_STACK_PUSH;
   }

   @Override
   public int getStackPopCount() {
      return 1;
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.MONITOREXIT();
   }
}
