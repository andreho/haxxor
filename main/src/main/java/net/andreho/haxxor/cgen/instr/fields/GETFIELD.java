package net.andreho.haxxor.cgen.instr.fields;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractFieldInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class GETFIELD
  extends AbstractFieldInstruction {

  public GETFIELD(String owner,
                  String name,
                  String desc) {
    super(Opcodes.GETFIELD, owner, name, desc);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.GETFIELD(this.owner, this.name, this.desc);
  }

  @Override
  public GETFIELD clone(final String owner,
                        final String name,
                        final String desc) {
    return new GETFIELD(owner, name, desc);
  }
}
