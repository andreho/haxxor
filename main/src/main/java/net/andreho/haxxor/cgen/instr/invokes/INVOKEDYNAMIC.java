package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKEDYNAMIC
    extends AbstractInstruction {

  private final String name;
  private final String desc;
  private final HxMethodHandle bootstrapMethod;
  private final HxArguments bootstrapMethodArguments;

  public INVOKEDYNAMIC(String name, String desc, HxMethodHandle bsm, HxArguments bsmArgs) {
    super();
    this.name = name;
    this.desc = desc;
    this.bootstrapMethod = bsm;
    this.bootstrapMethodArguments = bsmArgs;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Invocation.INVOKEDYNAMIC;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKEDYNAMIC(this.name, this.desc, this.bootstrapMethod, this.bootstrapMethodArguments);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public int getStackPushSize() {
    return getInstructionType().getPushSize(desc);
  }

  @Override
  public int getStackPopSize() {
    return getInstructionType().getPopSize(desc);
  }

  public String getName() {
    return name;
  }

  public String getDescriptor() {
    return desc;
  }

  public HxMethodHandle getBootstrapMethod() {
    return bootstrapMethod;
  }

  public HxArguments getBootstrapMethodArguments() {
    return bootstrapMethodArguments;
  }

  @Override
  public INVOKEDYNAMIC clone() {
    return clone(getName(), getDescriptor(), getBootstrapMethod(), getBootstrapMethodArguments());
  }

  public INVOKEDYNAMIC clone(String name, String desc, HxMethodHandle bsm, HxArguments bsmArgs) {
    return new INVOKEDYNAMIC(name, desc, bsm, HxArguments.createArguments(bsmArgs).freeze());
  }

  @Override
  protected String print() {
    return getInstructionName() + " " +
           this.name + " " + this.desc + " : " + this.bootstrapMethod + bootstrapMethodArguments;
  }
}
