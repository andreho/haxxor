package net.andreho.haxxor.cgen.instr.conversion;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INSTANCEOF
    extends AbstractStringOperandInstruction {

  public INSTANCEOF(String operand) {
    super(Opcodes.INSTANCEOF, operand);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INSTANCEOF(getOperand());
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_INT;
  }

  @Override
  public String toString() {
    return super.toString() + " " + getOperand();
  }
}
