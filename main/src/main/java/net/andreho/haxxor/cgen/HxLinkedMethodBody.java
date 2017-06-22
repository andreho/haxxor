package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class HxLinkedMethodBody
    implements Iterable<HxInstruction> {

  protected HxInstruction first;
  protected HxInstruction last;

  protected List<HxLocalVariable> localVariables;
  protected List<HxTryCatch> tryCatches;

  protected int maxStack;
  protected int maxLocals;

  public HxLinkedMethodBody() {
    reset();
  }

  public HxLinkedMethodBody(boolean manual) {
  }

  protected void reset() {
    reset(new BEGIN(), new END());
  }

  protected void reset(final HxInstruction first,
                       final HxInstruction last) {
    this.maxStack = -1;
    this.maxLocals = -1;

    this.localVariables = Collections.emptyList();
    this.tryCatches = Collections.emptyList();

    first.append(last);

    setFirst(first);
    setLast(last);
  }

  public HxLinkedMethodBody append(HxInstruction inst) {
    getCurrent().append(inst);
    return this;
  }

  public HxInstruction getFirst() {
    return this.first;
  }

  protected void setFirst(final HxInstruction first) {
    this.first = first;
  }

  public HxInstruction getCurrent() {
    return this.last.getPrevious();
  }

  public HxInstruction getLast() {
    return this.last;
  }

  protected void setLast(final HxInstruction last) {
    this.last = last;
  }

  public List<HxLocalVariable> getLocalVariables() {
    return this.localVariables;
  }

  public List<HxTryCatch> getTryCatches() {
    return this.tryCatches;
  }

  public int getMaxStack() {
    return this.maxStack;
  }

  public void setMaxStack(final int maxStack) {
    this.maxStack = maxStack;
  }

  public int getMaxLocals() {
    return this.maxLocals;
  }

  public void setMaxLocals(final int maxLocals) {
    this.maxLocals = maxLocals;
  }

  @Override
  public Iterator<HxInstruction> iterator() {
    return getFirst().iterator();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (HxTryCatch tryCatch : getTryCatches()) {
      builder.append(tryCatch)
             .append('\n');
    }

    for (HxInstruction inst : getFirst()) {
      builder.append(inst.toString())
             .append('\n');
    }

    return builder.toString();
  }
}
