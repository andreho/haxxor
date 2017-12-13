package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 22:37.
 */
public final class InstructionIterator
  implements Iterator<HxInstruction> {

  private HxInstruction last;
  private HxInstruction current;

  public InstructionIterator(final HxInstruction start) {
    current = start;
  }

  @Override
  public boolean hasNext() {
    return this.current != null;
  }

  @Override
  public void remove() {
    if (this.last == null) {
      throw new IllegalStateException();
    }

    this.last.remove();
    this.last = null;
  }

  @Override
  public HxInstruction next() {
    HxInstruction instruction = this.last = this.current;

    if (instruction == null) {
      throw new NoSuchElementException();
    }

    this.current = instruction.getNext();
    return instruction;
  }
}
