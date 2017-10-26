package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 03:18.
 */
public interface AspectProfileFactory {

  /**
   * @param aspectType
   * @return
   */
  Collection<AspectProfile> create(HxType aspectType);
}
