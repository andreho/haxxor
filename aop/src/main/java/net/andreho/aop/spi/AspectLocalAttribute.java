package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxLocalVariable;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 02:50.
 */
public interface AspectLocalAttribute {

  /**
   * @return
   */
  HxLocalVariable getHxLocalVariable();

  /**
   * @return
   */
  HxType getType();

  /**
   * @return
   */
  String getName();

  /**
   * @return
   */
  int getIndex();

  /**
   * @return
   */
  boolean wasHandled();

  /**
   */
  void markAsHandled();
}
