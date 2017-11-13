package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:34.
 */
public class COMPOUND
  extends AbstractInstruction {

  protected HxInstruction first;
  protected HxInstruction last;

  public COMPOUND() {
    super();
  }

  public HxInstruction getFirst() {
    return this.first;
  }

  public void setFirst(final HxInstruction first) {
    this.first = first;
  }

  public HxInstruction getLast() {
    return this.last;
  }

  public void setLast(final HxInstruction last) {
    this.last = last;
  }

  public boolean isEmpty() {
    return this.first == null || this.last == null;
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
    return hasPrevious() && getPrevious().isBegin();
  }

  @Override
  public boolean isEnd() {
    return hasNext() && getNext().isEnd();
  }

  @Override
  public void compute(final HxComputationContext context,
                      final HxFrame frame) {
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    if(getFirst() != null && getLast() != null) {
      final HxCodeStream subStream = codeStream.subStream();
      HxInstruction current = getFirst(), last = getLast();

      for(; current != last; current = current.getNext()) {
        current.visit(subStream);
      }

      last.visit(subStream);
    }
  }
}
