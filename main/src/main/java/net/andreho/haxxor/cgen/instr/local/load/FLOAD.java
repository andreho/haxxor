package net.andreho.haxxor.cgen.instr.local.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class FLOAD
    extends AbstractLocalAccessInstruction {

  public FLOAD(int var) {
    super(Opcodes.FLOAD, var);
  }

  @Override
  public void dumpTo(HxComputingContext context, HxCodeStream codeStream) {
    codeStream.FLOAD(getLocalIndex());
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    Object type = context.getLocals()
                         .get(getLocalIndex());
    if (Opcodes.FLOAT != type) {
      throw new IllegalArgumentException(
          "A float operand is expected at slot index [" + getLocalIndex() + "], but got: " + type);
    }
    return context.getStackPush()
                  .prepare()
                  .push(type)
                  .get();
  }
}
