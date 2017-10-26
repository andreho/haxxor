package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 08:01.
 */
public interface HxComputable {
  /**
   * @return
   */
  int getStackPopSize();

  /**
   * @return
   */
  int getStackPushSize();

  /**
   * @param context
   * @param frame
   * @return
   */
  void compute(HxComputationContext context, HxFrame frame);
}
