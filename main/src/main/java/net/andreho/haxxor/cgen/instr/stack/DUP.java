package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP
    extends AbstractZeroOperandInstruction {

  public DUP() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Stack.DUP;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DUP();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    Object value = context.getStack()
                          .peek();
    return context.getStackPush()
                  .prepare()
                  .push(value, value)
                  .get();
  }
}
