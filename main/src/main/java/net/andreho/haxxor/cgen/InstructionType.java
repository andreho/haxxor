package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.Instructions.Kind;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public interface InstructionType {
   /**
    * @return
    */
   Kind getKind();

   /**
    * @return
    */
   int getOpcode();
}
