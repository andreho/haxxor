package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ACMPNE extends AbstractJumpInstruction {
   public IF_ACMPNE(LABEL label) {
      super(Opcodes.IF_ACMPNE, label);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.IF_ACMPNE(this.label);
   }

   @Override
   public int getStackPopCount() {
      return 1 + 1;
   }
}
