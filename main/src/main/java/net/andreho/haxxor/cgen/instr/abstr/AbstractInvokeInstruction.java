package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.Context;

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

  public AbstractInvokeInstruction(int opcode, String owner, String name, String desc) {
    this(opcode, owner, name, desc, false);
  }

  public AbstractInvokeInstruction(int opcode, String owner, String name, String desc, boolean isInterface) {
    super(opcode);
    this.owner = owner;
    this.name = name;
    this.desc = desc;
    this.isInterface = isInterface;
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public List<Object> apply(final Context context) {
    return Utils.retrieveType(context, this.desc);
  }

  @Override
  public int getStackPopCount() {
    return (Type.getArgumentsAndReturnSizes(this.desc) >> 2) - ((getOpcode() == Opcodes.INVOKESTATIC) ? 1 : 0);
  }

  //----------------------------------------------------------------------------------------------------------------

  public String getOwner() {
    return this.owner;
  }

  public String getName() {
    return this.name;
  }

  public String getDesc() {
    return this.desc;
  }

  public boolean isInterface() {
    return this.isInterface;
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.owner + "." + this.name + " " + this.desc;
  }
}
