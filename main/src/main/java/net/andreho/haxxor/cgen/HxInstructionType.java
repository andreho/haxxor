package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public interface HxInstructionType {

  /**
   * @return
   */
  HxInstructionKind getKind();

  /**
   * @return
   */
  int getOpcode();

  /**
   * @return
   */
  int getPopSize();

  /**
   * @return
   */
  int getPushSize();

  /**
   * @return
   */
  default int getPopSize(String desc) {
    return getPopSize();
  }

  /**
   * @return
   */
  default int getPushSize(String desc) {
    return getPushSize();
  }
}
