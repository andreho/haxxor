package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;

/**
 * Created by 666 on 25.06.2017.
 */
public interface HxTryCatch
  extends HxVisitable,
          HxAnnotated<HxTryCatch> {

  /**
   * @return the start label of try-catch-block
   */
  LABEL getBegin();

  /**
   * @param label
   * @return this
   */
  HxTryCatch setBegin(LABEL label);

  /**
   * @return the end label of try-catch-block
   */
  LABEL getEnd();

  /**
   * @param label
   * @return this
   */
  HxTryCatch setEnd(LABEL label);

  /**
   * @return the label of the try-catch-handler
   */
  LABEL getCatch();

  /**
   * @param label
   * @return this
   */
  HxTryCatch setCatch(LABEL label);

  /**
   * @return the internal classname of handled exception
   */
  String getExceptionType();

  /**
   * @param exceptionType is the internal classname of handled exception
   * @return this
   */
  HxTryCatch setExceptionType(String exceptionType);
}
