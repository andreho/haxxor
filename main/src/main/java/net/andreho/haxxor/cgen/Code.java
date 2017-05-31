package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class Code
    implements Iterable<Instruction> {

  protected Instruction first;
  protected Instruction last;

  protected List<LocalVariable> localVariables;
  protected List<TryCatch> tryCatches;

  protected int maxStack = -1;
  protected int maxLocals = -1;

  public Code() {
    this(new BEGIN(), new END());
  }

  protected Code(final Instruction first, final Instruction last) {
    this.localVariables = Collections.emptyList();
    this.tryCatches = Collections.emptyList();

    setFirst(first);
    setLast(last);
    getFirst().append(getLast());
  }

  public Code append(Instruction inst) {
    getCurrent().append(inst);
    return this;
  }

  public Instruction getFirst() {
    return this.first;
  }

  protected void setFirst(final Instruction first) {
    this.first = first;
  }

  public Instruction getCurrent() {
    return this.last.getPrevious();
  }

  public Instruction getLast() {
    return this.last;
  }

  protected void setLast(final Instruction last) {
    this.last = last;
  }

  public List<LocalVariable> getLocalVariables() {
    return this.localVariables;
  }

  public List<TryCatch> getTryCatches() {
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
  public Iterator<Instruction> iterator() {
    return getFirst().iterator();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (TryCatch tryCatch : getTryCatches()) {
      builder.append(tryCatch)
             .append('\n');
    }

    for (Instruction inst : getFirst()) {
      builder.append(inst.toString())
             .append('\n');
    }

    for (TryCatch tryCatch : getTryCatches()) {

    }

    return builder.toString();
  }
}
