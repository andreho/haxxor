package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFNULL extends AbstractJumpInstruction {
   public IFNULL(LABEL label) {
      super(Opcodes.IFNULL, label);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.IFNULL(this.label);
   }

   @Override
   public int getStackPopCount() {
      return 1;
   }
}
