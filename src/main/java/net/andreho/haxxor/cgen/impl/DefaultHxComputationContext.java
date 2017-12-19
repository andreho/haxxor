package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxExecutor;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 02:46.
 */
public class DefaultHxComputationContext implements HxComputationContext {

  @Override
  public HxMethod getMethod() {
    return null;
  }

  @Override
  public HxFrame getInitialFrame() {
    return null;
  }

  @Override
  public HxFrame getCurrentFrame() {
    return null;
  }

  @Override
  public HxExecutor getExecutor() {
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
}
