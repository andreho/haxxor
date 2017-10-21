package net.andreho.haxxor.cgen.instr.create;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class NEWARRAY
    extends AbstractSingleOperandInstruction {

  public NEWARRAY(HxArrayType type) {
    super(Opcodes.NEWARRAY, type.getCode());
  }

  public NEWARRAY(int operand) {
    super(Opcodes.NEWARRAY, operand);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.NEWARRAY(HxArrayType.fromCode(this.operand));
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return HxArrayType.fromCode(this.operand)
                      .getStackOperands();
  }

  @Override
  public String toString() {
    return "NEWARRAY (" + HxArrayType.fromCode(this.operand).getClassName() + ")";
  }
}
