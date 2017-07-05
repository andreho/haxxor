package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.LABEL;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractJumpInstruction
    extends AbstractInstruction {

  protected final LABEL label;

  public AbstractJumpInstruction(int opcode, LABEL label) {
    super(opcode);
    this.label = label;
  }

  protected LABEL getLabel() {
    return label;
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    this.label.addReference(this);
    return NO_STACK_PUSH;
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.label.print();
  }
}
