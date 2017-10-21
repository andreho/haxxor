package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.HxComputingContext;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractFieldInstruction
    extends AbstractInstruction {

  protected final String owner;
  protected final String name;
  protected final String desc;

  public AbstractFieldInstruction(int opcode,
                                  String owner,
                                  String name,
                                  String desc) {
    super(opcode);
    this.owner = owner;
    this.name = name;
    this.desc = desc;
  }

  public String getOwner() {
    return owner;
  }

  public String getName() {
    return name;
  }

  public String getDescriptor() {
    return desc;
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

  public abstract <INST extends AbstractFieldInstruction> INST clone(String owner, String name, String desc);

  @Override
  public String toString() {
    return super.toString() + " " + this.owner + "." + this.name + " " + this.desc;
  }
}
