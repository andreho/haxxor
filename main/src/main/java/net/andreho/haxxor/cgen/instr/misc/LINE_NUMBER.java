package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
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

  @Override
  public void dumpTo(final Context context, final CodeStream codeStream) {
    codeStream.LINE_NUMBER(line, start);
  }

  @Override
  public List<Object> apply(final Context context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.line + ", " + this.start + ")";
  }

}
