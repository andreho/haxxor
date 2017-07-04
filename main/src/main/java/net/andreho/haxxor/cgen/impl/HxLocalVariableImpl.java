package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.impl.HxAnnotatedDelegate;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 21.06.2015.<br/>
 */
public class HxLocalVariableImpl
  extends HxAnnotatedDelegate<HxLocalVariable>
  implements HxLocalVariable {

  private int index;
  private String name;
  private String descriptor;
  private String signature;

  private LABEL start;
  private LABEL end;

  public HxLocalVariableImpl() {
  }

  public HxLocalVariableImpl(final int index,
                             final String name,
                             final String descriptor,
                             final String signature,
                             final LABEL start,
                             final LABEL end) {
    this.index = index;
    this.name = name;
    this.descriptor = descriptor;
    this.signature = signature;
    this.start = start;
    this.end = end;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LOCAL_VARIABLE(getName(), getDescriptor(), getSignature(), getStart(), getEnd(), getIndex());
  }

  @Override
  public int getIndex() {
    return this.index;
  }

  @Override
  public HxLocalVariable setIndex(final int index) {
    this.index = index;
    return this;
  }

  @Override
  public String getDescriptor() {
    return this.descriptor;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getSignature() {
    return this.signature;
  }

  @Override
  public LABEL getStart() {
    return this.start;
  }

  @Override
  public LABEL getEnd() {
    return this.end;
  }

  @Override
  public HxLocalVariable setDescriptor(final String descriptor) {
    this.descriptor = descriptor;
    return this;
  }

  @Override
  public HxLocalVariable setName(final String name) {
    this.name = name;
    return this;
  }

  @Override
  public HxLocalVariable setSignature(final String signature) {
    this.signature = signature;
    return this;
  }

  @Override
  public HxLocalVariable setStart(final LABEL start) {
    this.start = Objects.requireNonNull(start);
    return this;
  }

  @Override
  public HxLocalVariable setEnd(final LABEL end) {
    this.end = Objects.requireNonNull(end);
    return this;
  }

  public boolean isVisible(HxInstruction instruction) {
    return getStart().getIndex() < instruction.getIndex() && instruction.getIndex() < getEnd().getIndex();
  }

  public int size() {
    final Object type = getDescriptor();
    if ("D".equals(type) || "J".equals(type)) {
      return 2;
    }
    return 1;
  }
}