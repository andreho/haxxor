package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractSingleOperandInstruction
    extends AbstractInstruction {

  protected int operand;

  public AbstractSingleOperandInstruction(int operand) {
    super();
    this.operand = operand;
  }

  public int getOperand() {
    return operand;
  }

  public void setOperand(final int operand) {
    this.operand = operand;
  }
}
