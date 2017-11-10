package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class StringOperandInstruction
    extends AbstractInstruction {

  protected final String operand;

  public StringOperandInstruction(String operand) {
    super();
    this.operand = operand;
  }

  public String getOperand() {
    return operand;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + getOperand() + ")";
  }
}
