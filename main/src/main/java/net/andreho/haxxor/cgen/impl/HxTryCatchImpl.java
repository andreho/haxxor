package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.spec.impl.HxAnnotatedDelegate;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class HxTryCatchImpl extends HxAnnotatedDelegate<HxTryCatch>
    implements HxTryCatch {

  private LABEL begin;
  private LABEL end;
  private LABEL handler;
  private String handledException;

  public HxTryCatchImpl() {
  }

  public HxTryCatchImpl(LABEL begin,
                        LABEL end,
                        LABEL handler,
                        String handledException) {
    this.begin = requireNonNull(begin);
    this.end = requireNonNull(end);
    this.handler = requireNonNull(handler);
    this.handledException = handledException;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.TRY_CATCH(requireNonNull(this.begin),
                         requireNonNull(this.end),
                         requireNonNull(this.handler),
                         this.handledException);
  }

  @Override
  public LABEL getBegin() {
    return this.begin;
  }

  @Override
  public LABEL getEnd() {
    return this.end;
  }

  @Override
  public LABEL getCatch() {
    return this.handler;
  }

  @Override
  public String getExceptionType() {
    return this.handledException;
  }

  @Override
  public HxTryCatch setBegin(final LABEL label) {
    this.begin = requireNonNull(label);
    return this;
  }

  @Override
  public HxTryCatch setEnd(final LABEL label) {
    this.end = requireNonNull(label);
    return this;
  }

  @Override
  public HxTryCatch setCatch(final LABEL label) {
    this.handler = requireNonNull(label);
    return this;
  }

  @Override
  public HxTryCatch setExceptionType(final String exceptionType) {
    this.handledException = exceptionType;
    return this;
  }
}
