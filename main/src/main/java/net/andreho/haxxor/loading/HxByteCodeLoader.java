package net.andreho.haxxor.loading;

import net.andreho.haxxor.Haxxor;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public interface HxByteCodeLoader {
   /**
    * @return the associated haxxor instance
    */
   Haxxor getHaxxor();

   /**
    * @param className of a class being loaded
    * @return content of the requested class as a bytearray
    */
   byte[] load(String className);
}
