package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectTryCatch;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.impl.HxTryCatchImpl;
import net.andreho.haxxor.cgen.instr.LABEL;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 09.07.2017 at 22:12.
 */
public class AspectTryCatchImpl implements AspectTryCatch {
  private HxLocalVariable hxLocalVariable;
  private HxTryCatch hxTryCatch;
  private LABEL handlerEnd;

  public AspectTryCatchImpl() {
  }

  public AspectTryCatchImpl(final LABEL begin,
                            final LABEL end,
                            final LABEL handlerBegin,
                            final LABEL handlerEnd,
                            final String exceptionType,
                            final HxLocalVariable exceptionVariable) {
    this(new HxTryCatchImpl(begin, end, handlerBegin, exceptionType), exceptionVariable, handlerEnd);

  }

  public AspectTryCatchImpl(final HxTryCatch hxTryCatch,
                            final HxLocalVariable localVariable,
                            final LABEL handlerEnd) {
    this.hxTryCatch = hxTryCatch;
    this.hxLocalVariable = localVariable;
    this.handlerEnd = handlerEnd;
  }

  @Override
  public HxLocalVariable getHxLocalVariable() {
    return hxLocalVariable;
  }

  @Override
  public HxTryCatch getHxTryCatch() {
    return hxTryCatch;
  }

  @Override
  public LABEL getBegin() {
    return getHxTryCatch().getBegin();
  }

  @Override
  public LABEL getEnd() {
    return getHxTryCatch().getEnd();
  }

  @Override
  public LABEL getCatchBegin() {
    return getHxTryCatch().getCatch();
  }

  @Override
  public LABEL getCatchEnd() {
    return handlerEnd;
  }

  public void setHxLocalVariable(final HxLocalVariable hxLocalVariable) {
    this.hxLocalVariable = hxLocalVariable;
  }

  public void setHxTryCatch(final HxTryCatch tryCatch) {
    this.hxTryCatch = requireNonNull(tryCatch);
  }

  public void setHandlerEnd(final LABEL handlerEnd) {
    this.handlerEnd = requireNonNull(handlerEnd);
  }
}
