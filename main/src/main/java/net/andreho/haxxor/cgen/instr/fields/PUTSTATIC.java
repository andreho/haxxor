package net.andreho.haxxor.cgen.instr.fields;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractFieldInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class PUTSTATIC
    extends AbstractFieldInstruction {

  public PUTSTATIC(String owner, String name, String desc) {
    super(Opcodes.PUTSTATIC, owner, name, desc);
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.PUTSTATIC(this.owner, this.name, this.desc);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}
