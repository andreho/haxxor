package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.HxComputingContext;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractInvokeInstruction
    extends AbstractInstruction {

  protected final String owner;
  protected final String name;
  protected final String desc;
  protected final boolean isInterface;

  public AbstractInvokeInstruction(String owner, String name, String desc) {
    this(owner, name, desc, false);
  }

  public AbstractInvokeInstruction(String owner, String name, String desc, boolean isInterface) {
    super();
    this.owner = owner;
    this.name = name;
    this.desc = desc;
    this.isInterface = isInterface;
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return Utils.retrieveType(context, this.desc);
  }

  @Override
  public int getStackPushSize() {
    return getInstructionType().getPushSize(desc);
  }

  @Override
  public int getStackPopSize() {
    return getInstructionType().getPopSize(desc);
  }

  public String getOwner() {
    return this.owner;
  }

  public String getName() {
    return this.name;
  }

  public String getDescriptor() {
    return this.desc;
  }

  public boolean isInterface() {
    return this.isInterface;
  }

  public abstract <INST extends AbstractInvokeInstruction> INST clone(String owner, String name, String desc, boolean isInterface);

  @Override
  public String toString() {
    return super.toString() + " (" + this.owner + ", " + this.name + ", " + this.desc + ")";
  }
}
