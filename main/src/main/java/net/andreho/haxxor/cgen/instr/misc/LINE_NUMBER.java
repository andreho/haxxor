package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructions;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class LINE_NUMBER
    extends AbstractInstruction {

  private final int line;
  private final LABEL start;

  public LINE_NUMBER(int line, LABEL start) {
    super(-1);
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
    return HxInstructions.Special.LINE_NUMBER;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LINE_NUMBER(line, start);
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return NO_STACK_PUSH;
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
    return super.toString() + " (" + this.line + ", " + this.start + ")";
  }

}
