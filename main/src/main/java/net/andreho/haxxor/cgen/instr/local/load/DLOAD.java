package net.andreho.haxxor.cgen.instr.local.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DLOAD
    extends AbstractLocalAccessInstruction {

  public DLOAD(int var) {
    super(Opcodes.DLOAD, var);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.DLOAD(getLocalIndex());
  }

  @Override
  public List<Object> apply(final Context context) {
    Object type = context.getLocals()
                         .get(getLocalIndex());
    if (Opcodes.DOUBLE != type) {
      throw new IllegalArgumentException(
          "A double operand is expected at slot index [" + getLocalIndex() + "], but got: " + type);
    }
    return context.getStackPush()
                  .prepare()
                  .push(type, Opcodes.TOP)
                  .get();
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }
}
