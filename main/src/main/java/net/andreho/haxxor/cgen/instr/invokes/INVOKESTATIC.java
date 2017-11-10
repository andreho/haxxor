package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.InvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKESTATIC
  extends InvokeInstruction {

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
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public INVOKESTATIC clone(final String owner,
                            final String name,
                            final String desc,
                            final boolean isInterface) {
    return new INVOKESTATIC(owner, name, desc, isInterface);
  }
}
