package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

/**
 * IFGE succeeds if and only if value >= 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFGE extends AbstractJumpInstruction {
   public IFGE(LABEL label) {
      super(Opcodes.IFGE, label);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.IFGE(this.label);
   }

   @Override
   public int getStackPopCount() {
      return 1;
   }
}
