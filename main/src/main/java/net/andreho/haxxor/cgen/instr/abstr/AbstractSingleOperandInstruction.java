package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractSingleOperandInstruction
    extends AbstractInstruction {

  protected final int operand;

  public AbstractSingleOperandInstruction(int opcode, int operand) {
    super(opcode);
    this.operand = operand;
  }
}
