package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxLocals;
import net.andreho.haxxor.cgen.HxStack;
import net.andreho.haxxor.cgen.instr.arithmetic.DADD;
import net.andreho.haxxor.cgen.instr.compare.FCMPG;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 02:46.
 */
public class DefaultHxComputationContext implements HxComputationContext {

  @Override
  public HxMethod getMethod() {
    return null;
  }

  @Override
  public HxLocals getLocals() {
    return null;
  }

  @Override
  public HxStack getStack() {
    return null;
  }

  @Override
  public boolean wasVisited(final LABEL label) {
    return false;
  }

  @Override
  public boolean visit(final LABEL label) {
    return false;
  }

  @Override
  public void visit(final FCMPG fcmpg,
                    final HxStack stack,
                    final HxLocals locals) {

  }

  @Override
  public void visit(final DADD dadd,
                    final HxStack stack,
                    final HxLocals locals) {
    stack
      .pop(dadd.getStackPopSize())
      .push(HxComputationContext.DOUBLE);
  }
}
