package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class BIPUSH
    extends AbstractSingleOperandInstruction {

  public BIPUSH(int value) {
    this((byte) (0xFF & value));
  }

  public BIPUSH(byte value) {
    super(Opcodes.BIPUSH, value);
  }

  @Override
  public void dumpTo(HxComputingContext context, HxCodeStream codeStream) {
    codeStream.BIPUSH((byte) this.operand);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_INT;
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.operand;
  }
}
