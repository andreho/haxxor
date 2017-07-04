package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Map;

/**
 * <br/>Created by a.hofmann on 03.07.2017 at 14:10.
 */
public interface AspectMethodContext {

  /**
   * @return
   */
  LABEL getStart();

  /**
   * @param start
   */
  void setStart(LABEL start);

  /**
   * @return
   */
  LABEL getDelegation();

  /**
   * @param delegation
   */
  void setDelegation(LABEL delegation);

  /**
   * @return
   */
  LABEL getEnd();

  /**
   * @param end
   */
  void setEnd(LABEL end);

  /**
   * @return
   */
  HxMethod getOriginalMethod();

  /**
   * @param originalMethod
   */
  void setOriginalMethod(HxMethod originalMethod);

  /**
   * @return
   */
  HxMethod getShadowMethod();

  /**
   * @param shadowMethod
   */
  void setShadowMethod(HxMethod shadowMethod);

  /**
   * @param name
   * @return
   */
  boolean hasLocalAttribute(String name);

  /**
   * @return
   */
  AspectLocalAttribute getLocalAttribute(String name);

  /**
   * @param type
   * @param variable
   * @return
   */
  AspectLocalAttribute createLocalAttribute(HxType type, HxLocalVariable variable);

  /**
   * @param type
   * @param name
   * @param index
   * @return
   */
  AspectLocalAttribute createLocalAttribute(HxType type, String name, int index);

  /**
   * @return
   */
  Map<String, AspectLocalAttribute> getLocalAttributes();

  /**
   * @return
   */
  int getNextSlotIndex();

  void reset();
}
