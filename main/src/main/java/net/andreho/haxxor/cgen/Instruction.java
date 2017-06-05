package net.andreho.haxxor.cgen;

import net.andreho.haxxor.spec.api.HxAnnotated;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 17.11.2015.<br/>
 */
public interface Instruction
    extends Iterable<Instruction>,
            HxAnnotated<Instruction> {

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
   * @return opcode of this instruction
   */
  int getOpcode();

  /**
   * @return
   */
  default InstructionType getInstructionType() {
    return Instructions.of(getOpcode());
  }

  /**
   * @return
   */
  List<Object> apply(Context context);

  /**
   * @return
   */
  int getStackPopCount();

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
  Instruction getPrevious();

  /**
   * @param previous
   */
  void setPrevious(Instruction previous);

  /**
   * @return
   */
  Instruction getNext();

  /**
   * @param next
   */
  void setNext(Instruction next);

  /**
   * Shortcut for: <code>this.getPrevious().append(inst);</code>
   *
   * @param inst to prepend
   * @return given instruction
   */
  default Instruction prepend(Instruction inst) {
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
  default Instruction append(Instruction inst) {
    Objects.requireNonNull(inst);

    if (hasNext()) {
      Instruction oldNext = getNext();
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
  default Instruction remove() {
    if (!hasPrevious()) {
      throw new IllegalStateException("There isn't any previous operation.");
    }

    Instruction previous = getPrevious();
    Instruction next = getNext();

    previous.setNext(next);

    if (next != null) {
      next.setPrevious(previous);
    }

    return previous;
  }

  @Override
  default Iterator<Instruction> iterator() {
    return new Iterator<Instruction>() {
      Instruction visited;
      Instruction current = Instruction.this;

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
      public Instruction next() {
        Instruction instruction = this.visited = this.current;

        if (instruction == null) {
          throw new NoSuchElementException();
        }

        this.current = instruction.getNext();
        return instruction;
      }
    };
  }

  /**
   * Dumps this instruction to the given {@link CodeStream code stream} using the provided {@link Context context}
   *
   * @param context    to use
   * @param codeStream to dump this instruction to
   */
  void dumpTo(Context context, CodeStream codeStream);

  //----------------------------------------------------------------------------------------------------------------
}
