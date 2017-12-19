package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class LocalAccessInstruction
  extends SingleOperandInstruction {

  public LocalAccessInstruction(int operand) {
    super(operand);
  }

  public int getLocalIndex() {
    return this.operand;
  }

  @Override
  protected String print() {
    return getInstructionName() + " (" + this.operand + ")";
  }

  /**
   * @param var
   * @return
   */
  public abstract LocalAccessInstruction clone(int var);
}
