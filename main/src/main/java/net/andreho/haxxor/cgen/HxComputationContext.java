package net.andreho.haxxor.cgen;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public interface HxComputationContext {
  /**
   * @return
   */
  HxMethod getMethod();

  /**
   * @return
   */
  HxFrame getInitialFrame();

  /**
   * @return
   */
  HxFrame getCurrentFrame();

  /**
   * @return
   */
  HxExecutor getExecutor();

  /**
   * @param label
   * @return
   */
  boolean wasVisited(LABEL label);

  /**
   * @param label
   * @return
   */
  boolean visit(LABEL label);
}
