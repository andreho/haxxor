package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:34.
 */
public class COMPOSITE extends AbstractInstruction {

  protected HxInstruction first;
  protected HxInstruction last;

  public COMPOSITE() {
    super();
  }

  public HxInstruction getFirst() {
    return this.first;
  }

  public void setFirst(final HxInstruction first) {
    this.first = first;
  }

  public HxInstruction getCurrent() {
    return this.last.getPrevious();
  }

  public HxInstruction getLast() {
    return this.last;
  }

  public void setLast(final HxInstruction last) {
    this.last = last;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Special.COMPOSITE;
  }

  @Override
  public boolean isPseudoInstruction() {
    return true;
  }

  @Override
  public boolean isBegin() {
    return getPrevious() != null && getPrevious().isBegin();
  }

  @Override
  public boolean isEnd() {
    return getNext() != null && getNext().isEnd();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return Collections.emptyList();
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    if(getFirst() != null && getLast() != null) {
      HxInstruction instr = getFirst(), end = getLast();
      for(; instr != end; instr = instr.getNext()) {
        instr.visit(codeStream);
      }
      end.visit(codeStream);
    }
  }
}
