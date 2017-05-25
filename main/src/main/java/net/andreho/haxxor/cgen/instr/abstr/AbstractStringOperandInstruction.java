package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractStringOperandInstruction extends AbstractInstruction {
   protected final String operand;

   public AbstractStringOperandInstruction(int opcode, String operand) {
      super(opcode);
      this.operand = operand;
   }

   public String getOperand() {
      return operand;
   }
}
