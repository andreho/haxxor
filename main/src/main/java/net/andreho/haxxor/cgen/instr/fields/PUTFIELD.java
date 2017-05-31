package net.andreho.haxxor.cgen.instr.fields;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractFieldInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class PUTFIELD
    extends AbstractFieldInstruction {

  public PUTFIELD(String owner, String name, String desc) {
    super(Opcodes.PUTFIELD, owner, name, desc);
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.PUTFIELD(this.owner, this.name, this.desc);
  }

  @Override
  public List<Object> apply(final Context context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    if ("J".equals(this.desc) || "D".equals(this.desc)) {
      return 1 + 2;
    }
    return 1 + 1;
  }
}
