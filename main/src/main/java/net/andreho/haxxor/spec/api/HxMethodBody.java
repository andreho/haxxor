package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;

import java.util.List;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public interface HxMethodBody
  extends Iterable<HxInstruction>,
          Cloneable {

  /**
   * @param owner of the cloned method's body
   * @return makes a deep-copy of this method's body
   */
  HxMethodBody clone(HxMethod owner);

  /**
   * Moves the content of this method's bode into the given method and resets this method's body to empty state.
   * @param newOwner of the cloned method's body
   * @return current method's body of the given method
   */
  HxMethodBody moveTo(HxMethod newOwner);

  /**
   * @return owning method or constructor of this code block
   */
  HxMethod getMethod();

  /**
   * @return the getFirst instruction of associated code
   */
  HxInstruction getFirst();

  /**
   * @return the last added instruction or {@link HxMethodBody#getFirst()} if there isn't any instructions
   */
  HxInstruction getCurrent();

  /**
   * @return the end instruction of associated code
   */
  HxInstruction getLast();

  /**
   * @return
   */
  default boolean isAvailable() {
    return getFirst() != null &&
           getFirst().getNext() != getLast();
  }

  /**
   * @return a code stream that collects the visited instructions into this {@link HxMethodBody code instance}
   */
  default <S extends HxCodeStream<S>> S build() {
    return build(false);
  }

  /**
   * @param rebuild defines whether already collected instructions may be replaced or not
   * @return a code stream that collects the visited instructions into this {@link HxMethodBody code instance}
   */
  <S extends HxCodeStream<S>> S build(boolean rebuild);

  /**
   * @param rebuild defines whether already collected instructions may be replaced or not
   * @param streamFactory to use to build a new stream
   * @return a code stream that collects the visited instructions into this {@link HxMethodBody code instance}
   */
  <S extends HxCodeStream<S>> S build(boolean rebuild, Function<HxMethodBody, S> streamFactory);

  /**
   * @return count of maximal used stack slots
   */
  int getMaxStack();

  /**
   * @param maxStack count of maximal used stack slots
   */
  void setMaxStack(int maxStack);

  /**
   * @return count of maximal used local slots
   */
  int getMaxLocals();

  /**
   * @param maxLocals count of maximal used local slots
   */
  void setMaxLocals(int maxLocals);

  /**
   * @return a list with available local variables (OPTIONAL and depends on resolution flags of resolving Haxxor
   * instance)
   */
  List<HxLocalVariable> getLocalVariables();

  /**
   * @return <b>true</b> if there is some information about local variables, <b>false</b> otherwise.
   */
  default boolean hasLocalVariables() {
    return getLocalVariables().isEmpty();
  }

  /**
   * @return
   */
  default HxLocalVariable getLastLocalVariable() {
    return getLocalVariables().get(getLocalVariables().size() - 1);
  }

  /**
   * @param localVariable
   * @return
   */
  HxMethodBody addLocalVariable(HxLocalVariable localVariable);

  /**
   * @return
   */
  List<HxTryCatch> getTryCatches();

  /**
   * @return
   */
  default boolean hasTryCatch() {
    return getTryCatches().isEmpty();
  }

  /**
   * @return
   */
  default HxTryCatch getLastTryCatch() {
    return getTryCatches().get(getTryCatches().size() - 1);
  }

  /**
   * @param tryCatch block to add
   * @return
   */
  HxMethodBody addTryCatch(HxTryCatch tryCatch);

  /**
   * @return factory for instructions
   */
  HxInstructionFactory getInstructionFactory();

  /**
   * Recomputes the {@link HxInstruction#getIndex() index} for each instruction of this code
   * @return total count of instructions in this code
   */
  int computeIndex();

}
