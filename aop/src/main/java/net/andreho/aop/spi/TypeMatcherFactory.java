package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
public interface TypeMatcherFactory {

  /**
   * @param type
   * @return
   */
  TypeMatcher create(HxType type);

  /**
   * @param method
   * @return
   */
  TypeMatcher create(HxMethod method);

  /**
   * @param constructor
   * @return
   */
  TypeMatcher create(HxConstructor constructor);

}
