package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Map;

/**
 * <br/>Created by a.hofmann on 03.07.2017 at 14:10.
 */
public interface AspectMethodContext {

  /**
   * @return
   */
  LABEL getBegin();

  /**
   * @param begin
   */
  void setBegin(LABEL begin);

  /**
   * @return
   */
  LABEL getDelegationBegin();

  /**
   * @param delegationBegin
   */
  void setDelegationBegin(LABEL delegationBegin);

  /**
   * @return
   */
  LABEL getDelegationEnd();

  /**
   * @param delegationEnd
   */
  void setDelegationEnd(LABEL delegationEnd);

  /**
   * @return
   */
  LABEL getCatchInjection();

  /**
   * @param catchInjection
   */
  void setCatchInjection(LABEL catchInjection);

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
   * @return
   */
  AspectLocalAttribute getResultLocalAttribute();

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
   * @param type
   * @param name
   * @param index
   * @param begin
   * @param end
   * @return
   */
  AspectLocalAttribute createLocalAttribute(HxType type, String name, int index, LABEL begin, LABEL end);

  /**
   * @return
   */
  Map<String, AspectLocalAttribute> getLocalAttributes();

  /**
   * @param exceptionType
   */
  boolean hasTryCatchBlock(String exceptionType);

  /**
   * @param tryCatch
   */
  void createTryCatchBlock(AspectTryCatch tryCatch);

  /**
   * @param exceptionType is the Java classname of an exception type
   */
  AspectTryCatch getTryCatchBlock(String exceptionType);

  /**
   * @return
   */
  int getNextSlotIndex();

  /**
   * Resets this method's state to an initial state
   */
  void reset();

}
