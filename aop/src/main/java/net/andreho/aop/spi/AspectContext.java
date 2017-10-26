package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 06:47.
 */
public interface AspectContext {
  /**
   * @param aspectAdvice
   */
  void enterAspect(AspectAdvice<?> aspectAdvice);
  /**
   * @param aspectAdvice
   */
  default void leaveAspect(AspectAdvice<?> aspectAdvice) {}

  /**
   * @param type
   */
  void enterType(HxType type);
  /**
   * @param type
   */
  default void leaveType(HxType type) {}

  /**
   * @param field
   */
  void enterField(HxField field);

  /**
   * @param field
   */
  default void leaveField(HxField field) {}

  /**
   * @param method
   */
  void enterMethod(HxMethod method);

  /**
   * @param method
   */
  default void leaveMethod(HxMethod method) {}

  /**
   * @param constructor
   */
  void enterConstructor(HxMethod constructor);

  /**
   * @param constructor
   */
  default void leaveConstructor(HxMethod constructor) {}

  /**
   * @param parameter
   */
  void enterParameter(HxParameter parameter);

  /**
   * @param parameter
   */
  default void leaveParameter(HxParameter parameter) {}

  /**
   * @return
   */
  AspectAdvice<?> getAspectAdvice();

  /**
   * @return
   */
  AspectDefinition getAspectDefinition();

  /**
   * @return
   */
  AspectMethodContext getAspectMethodContext();

}
