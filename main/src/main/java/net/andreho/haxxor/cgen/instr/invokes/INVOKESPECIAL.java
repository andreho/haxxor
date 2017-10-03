package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKESPECIAL
  extends AbstractInvokeInstruction {

  public INVOKESPECIAL(String owner,
                       String name,
                       String desc) {
    super(Opcodes.INVOKESPECIAL, owner, name, desc);
    Utils.checkMethodName(getOpcode(), name);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKESPECIAL(this.owner, this.name, this.desc);
  }

  @Override
  public INVOKESPECIAL clone(final String owner,
                             final String name,
                             final String desc,
                             final boolean isInterface) {
    return new INVOKESPECIAL(owner, name, desc);
  }
}
