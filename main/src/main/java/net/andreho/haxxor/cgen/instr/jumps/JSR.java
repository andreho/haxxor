package net.andreho.haxxor.cgen.instr.jumps;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class JSR extends AbstractJumpInstruction {
   public JSR(LABEL label) {
      super(Opcodes.JSR, label);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.JSR(this.label);
   }

   @Override
   public List<Object> apply(final Context context) {
      super.apply(context);
      return PUSH_INT;
   }

   @Override
   public int getStackPopCount() {
      return 0;
   }
}
