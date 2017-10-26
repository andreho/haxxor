package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.Utils;
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
   * Moves the content of this method's body into the given method and resets this method's body to empty state.
   * @param newOwner of the cloned method's body
   * @return current method's body of the given method
   */
  HxMethodBody moveTo(HxMethod newOwner);

  /**
   * @return owning method or constructor of this body
   */
  HxMethod getMethod();

  /**
   * @return the getFirst instruction of associated code
   */
  HxInstruction getFirst();

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
   * @param localVariables of this method body
   */
  void setLocalVariables(final List<HxLocalVariable> localVariables);

  /**
   * @return <b>true</b> if there is some information about local variables, <b>false</b> otherwise.
   */
  default boolean hasLocalVariables() {
    return !getLocalVariables().isEmpty();
  }

  /**
   * @param name
   * @return
   */
  default boolean hasLocalVariable(String name) {
    for (HxLocalVariable variable : getLocalVariables()) {
      if(name.equals(variable.getName())) {
        return true;
      }
    }
    return false;
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
   * @param tryCatches of this method's body
   */
  void setTryCatches(final List<HxTryCatch> tryCatches);

  /**
   * @return
   */
  default boolean hasTryCatches() {
    return !getTryCatches().isEmpty();
  }

  /**
   * @return
   */
  default HxTryCatch getLastTryCatch() {
    return getTryCatches().get(getTryCatches().size() - 1);
  }

  /**
   * @param tryCatch block to add
   * @return this
   */
  HxMethodBody addTryCatch(HxTryCatch tryCatch);

  /**
   * @param exceptionType
   * @return
   */
  List<HxTryCatch> findTryCatch(String exceptionType);

  /**
   * @param exceptionType
   * @return
   */
  default List<HxTryCatch> findTryCatch(HxType exceptionType) {
    return findTryCatch(exceptionType.getName());
  }

  /**
   * @param exceptionType
   * @return
   */
  default List<HxTryCatch> findTryCatch(Class<? extends Throwable> exceptionType) {
    return findTryCatch(Utils.toClassName(exceptionType));
  }

  /**
   * @return factory for instructions
   */
  HxInstructionFactory getInstructionFactory();

  /**
   * @return
   */
  String toString();
}
