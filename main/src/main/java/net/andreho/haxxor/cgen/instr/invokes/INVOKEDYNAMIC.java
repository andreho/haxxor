package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxHandle;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKEDYNAMIC
    extends AbstractInstruction {

  private final String name;
  private final String desc;
  private final HxHandle bootstrapMethod;
  private final HxArguments bootstrapMethodArguments;

  public INVOKEDYNAMIC(String name, String desc, HxHandle bsm, HxArguments bsmArgs) {
    super(Opcodes.INVOKEDYNAMIC);
    Utils.checkMethodName(getOpcode(), name);
    this.name = name;
    this.desc = desc;
    this.bootstrapMethod = bsm;
    this.bootstrapMethodArguments = bsmArgs;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKEDYNAMIC(this.name, this.desc, this.bootstrapMethod, this.bootstrapMethodArguments);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return Utils.retrieveType(context, this.desc);
  }

  @Override
  public int getPushSize() {
    return getInstructionType().getPushSize(desc);
  }

  @Override
  public int getPopSize() {
    return getInstructionType().getPopSize(desc);
  }

  public String getName() {
    return name;
  }

  public String getDescriptor() {
    return desc;
  }

  public HxHandle getBootstrapMethod() {
    return bootstrapMethod;
  }

  public HxArguments getBootstrapMethodArguments() {
    return bootstrapMethodArguments;
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.name + " " + this.desc + " - > " + this.bootstrapMethod + bootstrapMethodArguments;
  }
}
