package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKEVIRTUAL extends AbstractInvokeInstruction {
   public INVOKEVIRTUAL(String owner, String name, String desc) {
      super(Opcodes.INVOKEVIRTUAL, owner, name, desc);
      Utils.checkMethodName(getOpcode(), name);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.INVOKEVIRTUAL(this.owner, this.name, this.desc);
   }
}
