package net.andreho.haxxor;

import net.andreho.haxxor.loading.HxByteCodeLoader;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.impl.HxByteCodeLoaderImpl;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class HaxxorBuilder {
   /**
    * Creates content loader instance that must be used for given
    *
    * @param haxxor instance that receives created content loader
    * @return new content loader
    */
   public HxByteCodeLoader createLoader(Haxxor haxxor) {
      return new HxByteCodeLoaderImpl(haxxor);
   }

   /**
    * Creates a map for type references
    * @param haxxor
    * @return
    */
   public Map<String, HxType> createReferenceCache(Haxxor haxxor) {
      return new TreeMap<>();
   }

   /**
    * Creates a map for resolved types
    * @param haxxor
    * @return
    */
   public Map<String, HxType> createResolvedCache(Haxxor haxxor) {
      return new TreeMap<>();
   }
}
