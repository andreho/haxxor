package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;

/**
 * Created by 666 on 25.06.2017.
 */
public interface HxTryCatch extends HxVisitable, HxAnnotated<HxTryCatch> {
   /**
    * @return the start label of try-catch-block
    */
   LABEL getStart();

   /**
    * @return the end label of try-catch-block
    */
   LABEL getEnd();

   /**
    * @return the label of the try-catch-handler
    */
   LABEL getHandler();

   /**
    * @return the internal classname of handled exception
    */
   String getType();
}
