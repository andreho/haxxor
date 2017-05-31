package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.cgen.Code;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.InstructionFactory;
import net.andreho.haxxor.cgen.LocalVariable;
import net.andreho.haxxor.cgen.TryCatch;
import net.andreho.haxxor.cgen.impl.InstructionCodeStream;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxParameterizable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class HxCodeImpl
    extends Code
    implements HxCode {

  private static final InstructionFactory DEFAULT_INSTRUCTION_FACTORY = new InstructionFactory() {
  };

  private final HxParameterizable owner;

  public HxCodeImpl(final HxParameterizable owner) {
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
  public HxCode addLocalVariable(final LocalVariable localVariable) {
    if (localVariables == Collections.EMPTY_LIST) {
      localVariables = new ArrayList<>();
    }
    localVariables.add(localVariable);
    return this;
  }

  @Override
  public HxCode addTryCatch(final TryCatch tryCatch) {
    if (tryCatches == Collections.EMPTY_LIST) {
      tryCatches = new ArrayList<>();
    }
    tryCatches.add(tryCatch);
    return this;
  }

  @Override
  public HxParameterizable getOwner() {
    return this.owner;
  }


  @Override
  public InstructionFactory getInstructionFactory() {
    return DEFAULT_INSTRUCTION_FACTORY;
  }

  @Override
  public CodeStream build(final boolean rebuild) {
    if (rebuild) {
      this.getFirst()
          .append(this.getLast());
    }
    return new InstructionCodeStream(this);
  }
}
