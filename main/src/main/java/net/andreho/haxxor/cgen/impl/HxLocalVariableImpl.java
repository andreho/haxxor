package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.impl.HxAnnotatedDelegate;

/**
 * <br/>Created by a.hofmann on 21.06.2015.<br/>
 */
public class HxLocalVariableImpl
   extends HxAnnotatedDelegate<HxLocalVariable>
    implements HxLocalVariable {

  private int index;
  private final String name;
  private final String descriptor;
  private final String signature;

  private final LABEL start;
  private final LABEL end;

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

  public int getIndex() {
    return this.index;
  }

  public void setIndex(final int index) {
    this.index = index;
  }

  public String getDescriptor() {
    return this.descriptor;
  }

  public String getName() {
    return this.name;
  }

  public String getSignature() {
    return this.signature;
  }

  public LABEL getStart() {
    return this.start;
  }

  public LABEL getEnd() {
    return this.end;
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
