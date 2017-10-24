package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKESTATIC
  extends AbstractInvokeInstruction {

  public INVOKESTATIC(String owner,
                      String name,
                      String desc,
                      boolean isInterface) {
    super(owner, name, desc, isInterface);
    Utils.checkMethodName(getOpcode(), name);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Invocation.INVOKESTATIC;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKESTATIC(this.owner, this.name, this.desc, this.isInterface);
  }

  @Override
  public INVOKESTATIC clone(final String owner,
                            final String name,
                            final String desc,
                            final boolean isInterface) {
    return new INVOKESTATIC(owner, name, desc, isInterface);
  }
}
