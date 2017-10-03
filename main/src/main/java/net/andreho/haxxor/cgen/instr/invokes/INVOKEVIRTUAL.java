package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKEVIRTUAL
  extends AbstractInvokeInstruction {

  public INVOKEVIRTUAL(String owner,
                       String name,
                       String desc) {
    super(Opcodes.INVOKEVIRTUAL, owner, name, desc);
    Utils.checkMethodName(getOpcode(), name);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INVOKEVIRTUAL(this.owner, this.name, this.desc);
  }

  @Override
  public INVOKEVIRTUAL clone(final String owner,
                             final String name,
                             final String desc,
                             final boolean isInterface) {
    return new INVOKEVIRTUAL(owner, name, desc);
  }
}
