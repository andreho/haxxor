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
public class INVOKEVIRTUAL
  extends InvokeInstruction {

  public INVOKEVIRTUAL(String owner,
                       String name,
                       String desc) {
    super(owner, name, desc);
//    Utils.checkMethodName(getOpcode(), name);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Invocation.INVOKEVIRTUAL;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKEVIRTUAL(this.owner, this.name, this.desc);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public INVOKEVIRTUAL clone() {
    return clone(getOwner(), getName(), getDescriptor(), isInterface());
  }

  @Override
  public INVOKEVIRTUAL clone(final String owner,
                             final String name,
                             final String desc,
                             final boolean isInterface) {
    return new INVOKEVIRTUAL(owner, name, desc);
  }
}
