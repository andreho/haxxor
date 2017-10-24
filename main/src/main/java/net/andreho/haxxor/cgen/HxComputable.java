package net.andreho.haxxor.cgen;

import java.util.List;

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
   * @return
   */
  List<Object> compute(HxComputingContext context);
}
