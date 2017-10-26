package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectTryCatch;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.impl.HxTryCatchImpl;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 09.07.2017 at 22:12.
 */
public class AspectTryCatchImpl implements AspectTryCatch {
  private HxLocalVariable hxLocalVariable;
  private HxTryCatch hxTryCatch;
  private HxType exceptionType;
  private LABEL handlerEnd;

  public AspectTryCatchImpl() {
  }

  public AspectTryCatchImpl(final HxType exceptionType,
                            final LABEL begin,
                            final LABEL end,
                            final LABEL handlerBegin,
                            final LABEL handlerEnd,
                            final HxLocalVariable exceptionVariable) {
    this(
      exceptionType,
       new HxTryCatchImpl(begin, end, handlerBegin, exceptionType.toInternalName()),
       exceptionVariable,
       handlerEnd
    );
  }

  public AspectTryCatchImpl(final HxType exceptionType,
                            final HxTryCatch hxTryCatch,
                            final HxLocalVariable localVariable,
                            final LABEL handlerEnd) {
    this.exceptionType = exceptionType;
    this.hxTryCatch = hxTryCatch;
    this.hxLocalVariable = localVariable;
    this.handlerEnd = handlerEnd;
  }

  @Override
  public HxType getExceptionType() {
    return exceptionType;
  }

  @Override
  public void setExceptionType(final HxType exceptionType) {
    this.exceptionType = exceptionType;
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
