package net.andreho.haxxor.cgen.instr.array;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ARRAYLENGTH
    extends AbstractZeroOperandInstruction {

  public ARRAYLENGTH() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.ARRAYLENGTH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ARRAYLENGTH();
  }

  @Override
  public List<Object> compute(HxComputingContext context) {
    Object arrayType = context.getStack().peek();

    if (arrayType.getClass() != String.class ||
        arrayType.toString()
                 .charAt(0) != '[') {
      throw new IllegalStateException("An array type is expected at stack[stack.length - 1]: " + arrayType);
    }

    return PUSH_INT;
  }
}
