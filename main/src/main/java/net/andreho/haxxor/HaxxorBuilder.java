package net.andreho.haxxor;

import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxTypeNamingStrategy;
import net.andreho.haxxor.spi.impl.DefaultHxByteCodeLoader;
import net.andreho.haxxor.spi.impl.DefaultHxTypeNamingStrategy;

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
  public HxByteCodeLoader createCodeLoader(Haxxor haxxor) {
    return new DefaultHxByteCodeLoader();
  }

  /**
   * Creates a map for type references
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxTypeReference> createReferenceCache(Haxxor haxxor) {
    return new TreeMap<>();
  }

  /**
   * Creates a map for resolved types
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxType> createResolvedCache(Haxxor haxxor) {
    return new TreeMap<>();
  }

  /**
   * Creates a new type-naming strategy for further usage
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxTypeNamingStrategy createTypeNamingStrategy(Haxxor haxxor) {
    return new DefaultHxTypeNamingStrategy();
  }
}
