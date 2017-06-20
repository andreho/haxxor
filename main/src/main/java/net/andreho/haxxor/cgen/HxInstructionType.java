package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public interface HxInstructionType {

  /**
   * @return
   */
  HxInstructionSort getSort();

  /**
   * @return opcode of the instruction's type
   */
  int getOpcode();

  /**
   * @return number of consumed operands from stack
   */
  int getPopSize();

  /**
   * @return number of pushed operands onto stack
   */
  int getPushSize();

  /**
   * @param desc
   * @return number of consumed operands from stack considering given descriptor
   */
  default int getPopSize(String desc) {
    return getPopSize();
  }

  /**
   * @param desc
   * @return number of pushed operands onto stack considering given descriptor
   */
  default int getPushSize(String desc) {
    return getPushSize();
  }
}
