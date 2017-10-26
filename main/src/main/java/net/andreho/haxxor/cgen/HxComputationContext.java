package net.andreho.haxxor.cgen;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public interface HxComputationContext {
  Object TOP = new Integer(0);
  Object INTEGER = new Integer(1);
  Object FLOAT = new Integer(2);
  Object DOUBLE = new Integer(3);
  Object LONG = new Integer(4);
  Object NULL = new Integer(5);
  Object UNINITIALIZED_THIS = new Integer(6);

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
