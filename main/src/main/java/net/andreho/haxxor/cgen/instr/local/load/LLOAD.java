package net.andreho.haxxor.cgen.instr.local.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class LLOAD
    extends AbstractLocalAccessInstruction {

  public LLOAD(int var) {
    super(Opcodes.LLOAD, var);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LLOAD(getLocalIndex());
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    Object type = context.getLocals()
                         .get(getLocalIndex());
    if (Opcodes.LONG != type) {
      throw new IllegalArgumentException(
          "A long operand is expected for the local-variable [" + getLocalIndex() + "], but got: " + type);
    }
    return context.getStackPush()
                  .prepare()
                  .push(type, Opcodes.TOP)
                  .get();
  }
}
