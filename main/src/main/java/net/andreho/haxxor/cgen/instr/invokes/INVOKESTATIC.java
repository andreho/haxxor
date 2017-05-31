package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKESTATIC
    extends AbstractInvokeInstruction {

  public INVOKESTATIC(String owner, String name, String desc, boolean isInterface) {
    super(Opcodes.INVOKESTATIC, owner, name, desc, isInterface);
    Utils.checkMethodName(getOpcode(), name);
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.INVOKESTATIC(this.owner, this.name, this.desc, this.isInterface);
  }
}
