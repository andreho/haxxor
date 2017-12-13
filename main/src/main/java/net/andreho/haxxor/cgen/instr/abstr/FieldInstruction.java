package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class FieldInstruction
    extends AbstractInstruction {

  protected final String owner;
  protected final String name;
  protected final String desc;

  public FieldInstruction(String owner,
                          String name,
                          String desc) {
    super();
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
  public int getStackPushSize() {
    return getInstructionType().getPushSize(desc);
  }

  @Override
  public int getStackPopSize() {
    return getInstructionType().getPopSize(desc);
  }

  public abstract <INST extends FieldInstruction> INST clone(String owner, String name, String desc);

  @Override
  protected String print() {
    return getName() + " " + this.owner + "." + this.name + " " + this.desc;
  }
}
