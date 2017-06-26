package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.impl.HxAnnotatedDelegate;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class HxTryCatchImpl extends HxAnnotatedDelegate<HxTryCatch>
    implements HxTryCatch {

  private final LABEL start;
  private final LABEL end;
  private final LABEL handler;
  private final String type;

  public HxTryCatchImpl(LABEL start,
                        LABEL end,
                        LABEL handler,
                        String type) {
    this.start = Objects.requireNonNull(start);
    this.end = Objects.requireNonNull(end);
    this.handler = Objects.requireNonNull(handler);
    this.type = type;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.TRY_CATCH(this.start, this.end, this.handler, this.type);
  }

  public LABEL getStart() {
    return this.start;
  }

  public LABEL getEnd() {
    return this.end;
  }

  public LABEL getHandler() {
    return this.handler;
  }

  public String getType() {
    return this.type;
  }
}
