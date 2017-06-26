package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;

/**
 * Created by 666 on 25.06.2017.
 */
public interface HxLocalVariable extends HxVisitable, HxAnnotated<HxLocalVariable> {
   /**
    * @return index of this local variable
    */
   int getIndex();

   /**
    * @param index of this variable
    */
   void setIndex(final int index);

   /**
    * @return descriptor of this local variable
    */
   String getDescriptor();

   /**
    * @return name of this local variable
    */
   String getName();

   /**
    * @return full signature of this local variable
    */
   String getSignature();

   /**
    * @return start asmLabel of this local variable
    */
   LABEL getStart();

   /**
    * @return end asmLabel of this local variable
    */
   LABEL getEnd();

   /**
    * Checks whether this local variable is visible for given asmLabel or not.
    *
    * @return <b>true</b> if it's visible and accessible, <bfalse></b> otherwise.
    */
   boolean isVisible(HxInstruction instruction);

   /**
    * @return count of slots that reserved by this local variable (2 for long and double, otherwise 1 always)
    */
   int size();
}
