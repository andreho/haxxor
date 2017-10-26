package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class LINE_NUMBER
    extends AbstractInstruction {

  private final int line;
  private final LABEL start;

  public LINE_NUMBER(int line, LABEL start) {
    super();
    this.line = line;
    this.start = start;
  }

  public int getLine() {
    return line;
  }

  public LABEL getStart() {
    return start;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Special.LINE_NUMBER;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LINE_NUMBER(line, start);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
  }

  @Override
  public int getStackPushSize() {
    return 0;
  }

  @Override
  public int getStackPopSize() {
    return 0;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.line + ", " + this.start.print() + ")";
  }
}
