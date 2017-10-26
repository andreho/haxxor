package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 16:18.
 */
public interface AspectFactory {
  /**
   * @return
   */
  boolean isReusable();

  /**
   * @return
   */
  HxMethod getMethod();

  /**
   * @return
   */
  String getAspectAttributeName();
}
