package net.andreho.haxxor.cgen.instr.fields;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractFieldInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class GETSTATIC
  extends AbstractFieldInstruction {

  public GETSTATIC(String owner,
                   String name,
                   String desc) {
    super(Opcodes.GETSTATIC, owner, name, desc);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.GETSTATIC(this.owner, this.name, this.desc);
  }

  @Override
  public GETSTATIC clone(final String owner,
                         final String name,
                         final String desc) {
    return new GETSTATIC(owner, name, desc);
  }
}
