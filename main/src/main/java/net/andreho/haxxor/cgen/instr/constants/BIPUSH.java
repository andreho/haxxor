package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class BIPUSH extends AbstractSingleOperandInstruction {
   public BIPUSH(int value) {
      this((byte) (0xFF & value));
   }

   public BIPUSH(byte value) {
      super(Opcodes.BIPUSH, value);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.BIPUSH((byte) this.operand);
   }

   @Override
   public List<Object> apply(final Context context) {
      return PUSH_INT;
   }

   @Override
   public int getStackPopCount() {
      return 0;
   }

   @Override
   public String toString() {
      return super.toString() + " " + this.operand;
   }
}
