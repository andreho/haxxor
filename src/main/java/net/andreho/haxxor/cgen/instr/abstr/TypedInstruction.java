package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 15:00.
 */
public abstract class TypedInstruction extends StringOperandInstruction {
  public TypedInstruction(final String operand) {
    super(operand);
  }
}
