package net.andreho.haxxor.cgen.instr.fields;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.FieldInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class PUTSTATIC
  extends FieldInstruction {

  public PUTSTATIC(String owner,
                   String name,
                   String desc) {
    super(owner, name, desc);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Fields.PUTSTATIC;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.PUTSTATIC(this.owner, this.name, this.desc);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public PUTSTATIC clone() {
    return clone(getOwner(), getName(), getDescriptor());
  }

  @Override
  public PUTSTATIC clone(final String owner,
                         final String name,
                         final String desc) {
    return new PUTSTATIC(owner, name, desc);
  }
}
