package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 06:47.
 */
public interface AspectContext {
  /**
   * @param type
   */
  void enterType(HxType type);

  /**
   * @param field
   */
  void enterField(HxField field);

  /**
   * @param method
   */
  void enterMethod(HxMethod method);

  /**
   * @param constructor
   */
  void enterConstructor(HxMethod constructor);

  /**
   * @return
   */
  AspectDefinition getAspectDefinition();

  /**
   * @return
   */
  AspectMethodContext getAspectMethodContext();
}
