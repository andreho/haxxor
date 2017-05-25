package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

/**
 * IFEQ succeeds if and only if value = 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFEQ extends AbstractJumpInstruction {
   public IFEQ(LABEL label) {
      super(Opcodes.IFEQ, label);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.IFEQ(this.label);
   }

   @Override
   public int getStackPopCount() {
      return 1;
   }
}
