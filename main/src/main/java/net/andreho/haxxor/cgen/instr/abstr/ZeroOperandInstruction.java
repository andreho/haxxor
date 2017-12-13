package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class ZeroOperandInstruction
    extends AbstractInstruction {

  public ZeroOperandInstruction() {
    super();
  }

  @Override
  protected String print() {
    return getInstructionName();
  }
}
