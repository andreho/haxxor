package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKEINTERFACE
  extends AbstractInvokeInstruction {

  public INVOKEINTERFACE(String owner,
                         String name,
                         String desc) {
    super(Opcodes.INVOKEINTERFACE, owner, name, desc);
    Utils.checkMethodName(getOpcode(), name);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKEINTERFACE(this.owner, this.name, this.desc);
  }

  @Override
  public INVOKEINTERFACE clone(final String owner,
                               final String name,
                               final String desc,
                               final boolean isInterface) {
    return new INVOKEINTERFACE(owner, name, desc);
  }
}
