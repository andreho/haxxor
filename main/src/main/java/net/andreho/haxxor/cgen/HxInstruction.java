package net.andreho.haxxor.cgen;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.cgen.impl.InstructionCodeStream;
import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;
import net.andreho.haxxor.cgen.instr.misc.COMPOUND;
import net.andreho.haxxor.cgen.instr.misc.FRAME;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 17.11.2015.<br/>
 */
public interface HxInstruction
  extends HxVisitable,
          HxComputable,
          HxAnnotated<HxInstruction>,
          Cloneable,
          Iterable<HxInstruction> {

  /**
   * Interface for all dynamic property accesses on instructions
   *
   * @param <V>
   * @see HxInstructionProperties
   */
  interface Property<V> {

    /**
     * @return name of this property
     */
    String name();

    /**
     * Checks whether this property may extracted from the given instruction or not
     *
     * @param instruction to check
     * @return <b>true</b> if this property can be read from the given instruction, <b>false</b> otherwise
     */
    boolean isReadable(HxInstruction instruction);

    /**
     * Tries to read the value of the referenced property from the given instruction
     *
     * @param instruction to extract the referenced property's value
     * @return the value of the given instruction
     * @throws ClassCastException if the given instruction has wrong type
     */
    V read(HxInstruction instruction);
  }

  /**
   * @param instructionType
   * @param <I>
   * @return
   */
  default <I extends HxInstruction> I as(Class<I> instructionType) {
    return instructionType.cast(this);
  }

  /**
   * @return
   */
  boolean hasAnnotations();

  /**
   * @return
   */
  default HxInstruction clone() {
    throw new IllegalStateException("Cloning not supported: " + getClass().getName());
  }

  /**
   * @return
   */
  default String getInstructionName() {
    return getInstructionType().name();
  }

  /**
   * @return opcode of this instruction
   */
  default int getOpcode() {
    return getInstructionType().getOpcode();
  }

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
    return HxInstructionTypes.fromOpcode(getOpcode());
  }

  /**
   * @return
   */
  default HxInstructionSort getInstructionSort() {
    return getInstructionType().getSort();
  }

  /**
   * @param sort
   * @return
   */
  default boolean hasSort(HxInstructionSort sort) {
    return getInstructionSort() == sort;
  }

  /**
   * @param sort
   * @return
   */
  default boolean hasNotSort(HxInstructionSort sort) {
    return getInstructionSort() != sort;
  }

  /**
   * @param type
   * @return
   */
  default boolean hasType(HxInstructionType type) {
    return getInstructionType() == type;
  }

  /**
   * @param type
   * @return
   */
  default boolean hasNotType(HxInstructionType type) {
    return getInstructionType() != type;
  }

  /**
   * @param property
   * @param withValue
   * @param <V>
   * @return
   */
  default <V> boolean hasPropertyEqual(HxInstruction.Property<V> property,
                                       V withValue) {
    return Objects.equals(withValue, property(property, null));
  }

  /**
   * @param property
   * @param withValue
   * @param <V>
   * @return
   */
  default <V> boolean hasPropertyNotEqual(HxInstruction.Property<V> property,
                                          V withValue) {
    return !Objects.equals(withValue, property(property, null));
  }

  /**
   * @param property
   * @param <V>
   * @return
   */
  default <V> V property(HxInstruction.Property<V> property) {
    return property(property, null);
  }

  /**
   * @param property
   * @param defaultValue
   * @param <V>
   * @return
   */
  default <V> V property(HxInstruction.Property<V> property,
                         V defaultValue) {
    if (!property.isReadable(this)) {
      return defaultValue;
    }
    return property.read(this);
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

    if (!hasPrevious()) {
      throw new IllegalStateException("There isn't any previous instruction.");
    }
    return getPrevious().append(inst);
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
      throw new IllegalStateException("There isn't any previous instruction.");
    }

    HxInstruction previous = getPrevious();
    HxInstruction next = getNext();

    this.setNext(null);
    this.setPrevious(null);

    if (next != null) {
      next.setPrevious(previous);
    }
    previous.setNext(next);

    return previous;
  }

  /**
   * @param replacement
   * @return
   */
  default HxInstruction replaceWith(HxInstruction replacement) {
    prepend(replacement);
    remove();
    return replacement;
  }

  /**
   * @return
   */
  default HxExtendedCodeStream asStream() {
    return new InstructionCodeStream(this);
  }

  /**
   * @param factory
   * @param <S>
   * @return
   */
  default <S extends HxCodeStream<S>> S asStream(Function<HxInstruction, S> factory) {
    return factory.apply(this);
  }

  /**
   * @return
   */
  default HxExtendedCodeStream asAnchoredStream() {
    return new InstructionCodeStream(this.getPrevious());
  }

  /**
   * @param factory
   * @param <S>
   * @return
   */
  default <S extends HxCodeStream<S>> S asAnchoredStream(Function<HxInstruction, S> factory) {
    return factory.apply(this.getPrevious());
  }

  /**
   * @return <b>true</b> if this instruction's type has one or more references to label(s), <b>false</b> otherwise
   */
  default boolean hasLabelReference() {
    return getInstructionSort() == HxInstructionSort.Jump ||
           getInstructionSort() == HxInstructionSort.Switch ||
           hasType(HxInstructionTypes.SubRoutine.JSR) ||
           hasType(HxInstructionTypes.Special.LINE_NUMBER);
  }

  /**
   * @return
   */
  default boolean isAnnotable() {
    return true;
  }

  /**
   * @return <b>true</b> if this instruction doesn't exist and serves for internal use-cases, <b>false</b> otherwise.
   * @see END
   * @see BEGIN
   * @see LABEL
   * @see FRAME
   * @see LINE_NUMBER
   * @see COMPOUND
   */
  default boolean isPseudoInstruction() {
    return getOpcode() < 0;
  }


  /**
   * @return <b>true</b> if there isn't any further instructions in backward iteration's direction, <b>false</b>
   * otherwise.
   */
  default boolean isBegin() {
    return false;
  }

  /**
   * @return <b>true</b> if there isn't any further instructions in forward iteration's direction, <b>false</b>
   * otherwise.
   */
  default boolean isEnd() {
    return false;
  }

  /**
   * @return <b>true</b> if this instruction is a label, <b>false</b> otherwise.
   */
  default boolean isLabel() {
    return getInstructionType() == HxInstructionTypes.Special.LABEL;
  }

  /**
   * @param consumer
   */
  default void forEachFollowing(Consumer<HxInstruction> consumer) {
    for (HxInstruction instruction : this) {
      consumer.accept(instruction);
    }
  }

  /**
   * @param predicate
   * @return
   */
  default boolean isFollowedBy(Predicate<HxInstruction> predicate) {
    if (hasNext()) {
      for (HxInstruction inst : getNext()) {
        if (predicate.test(inst)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @param predicate
   * @return
   */
  default boolean isFollowedTillEndBy(Predicate<HxInstruction> predicate) {
    if (!isEnd() || hasNext()) {
      for (HxInstruction inst : getNext()) {
        if (inst.isEnd()) {
          break;
        }
        if (!predicate.test(inst)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * @param instructionType
   * @return
   */
  Optional<HxInstruction> findFirstWithType(final HxInstructionType instructionType);

  /**
   * @param instructionType
   * @return
   */
  Optional<HxInstruction> findLastWithType(final HxInstructionType instructionType);

  /**
   * @param instructionSort
   * @return
   */
  Optional<HxInstruction> findFirstWithSort(final HxInstructionSort instructionSort);

  /**
   * @param instructionSort
   * @return
   */
  Optional<HxInstruction> findLastWithSort(final HxInstructionSort instructionSort);

  /**
   * @param predicate
   * @return
   */
  Optional<HxInstruction> findFirst(final Predicate<HxInstruction> predicate);

  /**
   * @param predicate
   * @return
   */
  Optional<HxInstruction> findLast(final Predicate<HxInstruction> predicate);

  /**
   * @param consumer
   */
  void forEachNext(final Consumer<HxInstruction> consumer);

  /**
   * @param predicate
   * @param consumer
   */
  void forEachNext(final Predicate<HxInstruction> predicate,
                   final Consumer<HxInstruction> consumer);

  /**
   * @param consumer
   */
  void forEachPrevious(final Consumer<HxInstruction> consumer);

  /**
   * @param predicate
   * @param consumer
   */
  void forEachPrevious(final Predicate<HxInstruction> predicate,
                       final Consumer<HxInstruction> consumer);

  /**
   * @param consumer
   * @return
   */
  boolean forNext(final Consumer<HxInstruction> consumer);

  /**
   * @param predicate
   * @param consumer
   * @return
   */
  boolean forNext(final Predicate<HxInstruction> predicate,
                  final Consumer<HxInstruction> consumer);

  /**
   * @param consumer
   * @return
   */
  boolean forPrevious(final Consumer<HxInstruction> consumer);

  /**
   * @param predicate
   * @param consumer
   * @return
   */
  boolean forPrevious(final Predicate<HxInstruction> predicate,
                      final Consumer<HxInstruction> consumer);
}
