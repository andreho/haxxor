package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 09.07.2017 at 21:21.
 */
public interface AspectTryCatch {

  HxType getExceptionType();

  void setExceptionType(HxType exceptionType);

  HxLocalVariable getHxLocalVariable();

  HxTryCatch getHxTryCatch();

  LABEL getBegin();

  LABEL getEnd();

  LABEL getCatchBegin();

  LABEL getCatchEnd();
}
