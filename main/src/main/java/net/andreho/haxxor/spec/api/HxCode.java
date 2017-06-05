package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.cgen.InstructionFactory;
import net.andreho.haxxor.cgen.LocalVariable;
import net.andreho.haxxor.cgen.TryCatch;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public interface HxCode {

  /**
   * Stores this bytecode into a persistent storage and so frees used memory
   */
  boolean store();

  /**
   * Loads the previously stored bytecode from a persistent storage and
   * so makes this method code available for processing
   */
  boolean load();

  /**
   * @return <b>true</b> if this code is loaded and available for modifications, <b>false</b> otherwise
   */
  boolean isLoaded();

  /**
   * @return owning method or constructor of this code block
   */
  HxParameterizable getOwner();

  /**
   * @return
   */
  default boolean isAvailable() {
    return getFirst() != null &&
           getFirst().getNext() != getLast();
  }

  /**
   * @return a code stream that collects the visited instructions into this {@link HxCode code instance}
   */
  default CodeStream build() {
    return build(false);
  }

  /**
   * @param rebuild defines whether already collected instructions may be replaced or not
   * @return a code stream that collects the visited instructions into this {@link HxCode code instance}
   */
  CodeStream build(boolean rebuild);

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
  List<LocalVariable> getLocalVariables();

  /**
   * @return <b>true</b> if there is some information about local variables, <b>false</b> otherwise.
   */
  default boolean hasLocalVariables() {
    return getLocalVariables().isEmpty();
  }

  /**
   * @return
   */
  default LocalVariable getLastLocalVariable() {
    return getLocalVariables().get(getLocalVariables().size() - 1);
  }

  /**
   * @param localVariable
   * @return
   */
  HxCode addLocalVariable(LocalVariable localVariable);

  /**
   * @return
   */
  List<TryCatch> getTryCatches();

  /**
   * @return
   */
  default boolean hasTryCatch() {
    return getTryCatches().isEmpty();
  }

  /**
   * @return
   */
  default TryCatch getLastTryCatch() {
    return getTryCatches().get(getTryCatches().size() - 1);
  }

  /**
   * @param tryCatch block to add
   * @return
   */
  HxCode addTryCatch(TryCatch tryCatch);

  /**
   * @return factory for instructions
   */
  InstructionFactory getInstructionFactory();

  /**
   * Recomputes the {@link Instruction#getIndex() index} for each instruction of this code
   * @return total count of instructions in this code
   */
  int computeIndex();

  /**
   * @return the getFirst instruction of associated code
   */
  Instruction getFirst();

  /**
   * @return the last added instruction or {@link HxCode#getFirst()} if there isn't any instructions
   */
  Instruction getCurrent();

  /**
   * @return the end instruction of associated code
   */
  Instruction getLast();
}
