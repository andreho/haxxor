package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 09.07.2017 at 21:21.
 */
public interface AspectTryCatch {
  HxLocalVariable getHxLocalVariable();

  HxTryCatch getHxTryCatch();

  LABEL getBegin();

  LABEL getEnd();

  LABEL getCatchBegin();

  LABEL getCatchEnd();
}
