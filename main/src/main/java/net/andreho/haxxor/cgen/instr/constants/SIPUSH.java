package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class SIPUSH
    extends AbstractSingleOperandInstruction {

  public SIPUSH(int value) {
    this((short) (0xFFFF & value));
  }

  public SIPUSH(short value) {
    super(Opcodes.SIPUSH, value);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.SIPUSH((short) this.operand);
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
