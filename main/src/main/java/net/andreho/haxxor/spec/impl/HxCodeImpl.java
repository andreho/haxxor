package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxLinkedCode;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.impl.ExtendedInstructionCodeStream;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxExecutable;

import java.util.ArrayList;
import java.util.Objects;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class HxCodeImpl
    extends HxLinkedCode
    implements HxCode {

  private static final HxInstructionFactory DEFAULT_INSTRUCTION_FACTORY = new HxInstructionFactory() {
  };

  private final HxExecutable owner;

  public HxCodeImpl(final HxExecutable owner) {
    super();
    this.owner = Objects.requireNonNull(owner);
  }

  @Override
  public boolean store() {
    return false;
  }

  @Override
  public boolean load() {
    return false;
  }

  @Override
  public boolean isLoaded() {
    return true;
  }

  @Override
  public HxCode addLocalVariable(final HxLocalVariable localVariable) {
    if (isUninitialized(localVariables)) {
      localVariables = new ArrayList<>(2);
    }
    localVariables.add(localVariable);
    return this;
  }

  @Override
  public HxCode addTryCatch(final HxTryCatch tryCatch) {
    if (isUninitialized(tryCatches)) {
      tryCatches = new ArrayList<>(2);
    }
    tryCatches.add(tryCatch);
    return this;
  }

  @Override
  public HxExecutable getOwner() {
    return this.owner;
  }

  @Override
  public int computeIndex() {
    int i = 0;
    for(HxInstruction inst = getFirst().getNext(), end = getLast(); inst != end; inst = inst.getNext()) {
      inst.setIndex(i++);
    }
    return i;
  }

  @Override
  public HxInstructionFactory getInstructionFactory() {
    return DEFAULT_INSTRUCTION_FACTORY;
  }

  @Override
  public HxCodeStream build(final boolean rebuild) {
    if (rebuild) {
      this.getFirst()
          .append(this.getLast());
    }
    return new ExtendedInstructionCodeStream(this);
  }
}
