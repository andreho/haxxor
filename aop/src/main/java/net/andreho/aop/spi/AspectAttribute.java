package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 02:50.
 */
public interface AspectAttribute {

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
   * @param index
   */
  void setIndex(int index);

  /**
   * @param delta
   */
  void shift(int delta);

}
