package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractLocalAccessInstruction
    extends AbstractSingleOperandInstruction {

  public AbstractLocalAccessInstruction(int opcode, int operand) {
    super(opcode, operand);
  }

  protected int getLocalIndex() {
    return this.operand;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.operand + ")";
  }
}
