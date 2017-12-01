package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;
import net.andreho.haxxor.cgen.instr.misc.COMPOUND;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class HxLinkedMethodBody extends COMPOUND
    implements Iterable<HxInstruction> {

  protected List<HxLocalVariable> localVariables;
  protected List<HxTryCatch> tryCatches;

  protected int maxStack;
  protected int maxLocals;

  public HxLinkedMethodBody() {
    reset();
  }

  protected void reset() {
    this.maxStack = -1;
    this.maxLocals = -1;

    this.localVariables = Collections.emptyList();
    this.tryCatches = Collections.emptyList();

    this.first = new BEGIN();
    this.last = new END();
    first.append(this.last);
  }

  public List<HxLocalVariable> getLocalVariables() {
    return this.localVariables;
  }

  public List<HxTryCatch> getTryCatches() {
    return this.tryCatches;
  }

  public void setLocalVariables(final List<HxLocalVariable> localVariables) {
    this.localVariables = localVariables;
  }

  public void setTryCatches(final List<HxTryCatch> tryCatches) {
    this.tryCatches = tryCatches;
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
}
