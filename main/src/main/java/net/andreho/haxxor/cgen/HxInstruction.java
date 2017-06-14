package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.misc.FRAME;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.spec.api.HxAnnotated;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 17.11.2015.<br/>
 */
public interface HxInstruction
    extends HxVisitable,
            HxStackChangeProvider,
            HxAnnotated<HxInstruction>,
            Iterable<HxInstruction> {

  /**
   * @return
   */
  boolean hasAnnotations();

  /**
   * @return index of this instruction
   */
  int getIndex();

  /**
   * @param index of this instruction
   */
  void setIndex(int index);

  /**
   * @return <b>true</b> if there isn't any further instructions in backward iteration's direction, <b>false</b> otherwise.
   */
  default boolean isBegin() {
    return false;
  }

  /**
   * @return <b>true</b> if this instruction doesn't exist and serves for internal use-cases, <b>false</b> otherwise.
   * @see END
   * @see BEGIN
   * @see LABEL
   * @see FRAME
   * @see LINE_NUMBER
   */
  default boolean isPseudoInstruction() {
    return getOpcode() < 0;
  }

  /**
   * @return <b>true</b> if there isn't any further instructions in forward iteration's direction, <b>false</b> otherwise.
   */
  default boolean isEnd() {
    return false;
  }

  /**
   * @return opcode of this instruction
   */
  int getOpcode();

  /**
   * @param opcode
   * @return
   */
  default boolean hasOpcode(int opcode) {
    return getOpcode() == opcode;
  }

  /**
   * @return
   */
  default HxInstructionType getInstructionType() {
    return HxInstructions.fromOpcode(getOpcode());
  }

  /**
   * @param type
   * @return
   */
  default boolean hasInstructionType(HxInstructionType type) {
    return getInstructionType() == type;
  }

  /**
   * @param kind
   * @return
   */
  default boolean hasInstructionKind(HxInstructionKind kind) {
    return getInstructionType().getKind() == kind;
  }

  /**
   * @return
   */
  default boolean hasPrevious() {
    return getPrevious() != null;
  }

  /**
   * @return
   */
  default boolean hasNext() {
    return getNext() != null;
  }

  /**
   * @return
   */
  HxInstruction getPrevious();

  /**
   * @param previous
   */
  void setPrevious(HxInstruction previous);

  /**
   * @return
   */
  HxInstruction getNext();

  /**
   * @param next
   */
  void setNext(HxInstruction next);

  /**
   * Shortcut for: <code>this.getPrevious().append(inst);</code>
   *
   * @param inst to prepend
   * @return given instruction
   */
  default HxInstruction prepend(HxInstruction inst) {
    Objects.requireNonNull(inst);

    if (hasPrevious()) {
      return getPrevious().append(inst);
    }
    throw new IllegalStateException("There isn't any previous operation.");
  }

  /**
   * @param inst to append
   * @return the given instruction
   */
  default HxInstruction append(HxInstruction inst) {
    Objects.requireNonNull(inst);

    if (hasNext()) {
      HxInstruction oldNext = getNext();
      inst.setNext(oldNext);
      oldNext.setPrevious(inst);
    }

    this.setNext(inst);
    inst.setPrevious(this);

    return inst;
  }

  /**
   * Removes this instruction and returns the previous one
   *
   * @return previous instruction
   */
  default HxInstruction remove() {
    if (!hasPrevious()) {
      throw new IllegalStateException("There isn't any previous operation.");
    }

    HxInstruction previous = getPrevious();
    HxInstruction next = getNext();

    previous.setNext(next);

    if (next != null) {
      next.setPrevious(previous);
    }

    return previous;
  }

  @Override
  default Iterator<HxInstruction> iterator() {
    return new Iterator<HxInstruction>() {
      HxInstruction visited;
      HxInstruction current = HxInstruction.this;

      @Override
      public boolean hasNext() {
        return this.current != null;
      }

      @Override
      public void remove() {
        if (this.visited == null) {
          throw new IllegalStateException();
        }

        this.visited.remove();
        this.visited = null;
      }

      @Override
      public HxInstruction next() {
        HxInstruction instruction = this.visited = this.current;

        if (instruction == null) {
          throw new NoSuchElementException();
        }

        this.current = instruction.getNext();
        return instruction;
      }
    };
  }
}
