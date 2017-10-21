package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:34.
 */
public class COMPOSITE extends AbstractInstruction {

  public COMPOSITE() {
    super(-1);
    throw new UnsupportedOperationException();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Special.COMPOSITE;
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return Collections.emptyList();
  }

  @Override
  public void visit(final HxCodeStream codeStream) {

  }
}
