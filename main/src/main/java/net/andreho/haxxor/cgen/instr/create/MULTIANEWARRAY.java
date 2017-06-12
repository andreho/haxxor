package net.andreho.haxxor.cgen.instr.create;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class MULTIANEWARRAY
    extends AbstractStringOperandInstruction {

  private final int dimension;

  public MULTIANEWARRAY(String className,
                        int dims) {
    super(Opcodes.MULTIANEWARRAY, className);
    this.dimension = dims;
  }

  @Override
  public void dumpTo(HxComputingContext context,
                     HxCodeStream codeStream) {
    codeStream.MULTIANEWARRAY(getOperand(), this.dimension);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return context.getStackPush()
                  .prepare()
                  .push(multiply(getOperand(), this.dimension))
                  .get();
  }

  @Override
  public int getPopSize() {
    return this.dimension;
  }

  @Override
  public String toString() {
    return super.toString() + " " + multiply("[", this.dimension) + getOperand();
  }

  private static String multiply(String str,
                                 int times) {
    StringBuilder builder = new StringBuilder();
    while (times-- > 0) {
      builder.append(str);
    }
    return builder.toString();
  }
}
