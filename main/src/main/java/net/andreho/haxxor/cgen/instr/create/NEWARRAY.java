package net.andreho.haxxor.cgen.instr.create;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.ArrayType;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class NEWARRAY
    extends AbstractSingleOperandInstruction {

  public NEWARRAY(ArrayType type) {
    super(Opcodes.NEWARRAY, type.getCode());
  }

  public NEWARRAY(int operand) {
    super(Opcodes.NEWARRAY, operand);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.NEWARRAY(ArrayType.fromCode(this.operand));
  }

  @Override
  public List<Object> apply(final Context context) {
    return ArrayType.fromCode(this.operand)
                    .getStackOperands();
  }

  @Override
  public int getStackPopCount() {
    return 1;
  }

  @Override
  public String toString() {
    return super.toString() + " " + ArrayType.fromCode(this.operand)
                                             .getClassName();
  }
}
