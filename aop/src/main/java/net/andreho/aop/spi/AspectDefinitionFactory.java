package net.andreho.aop.spi;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;

import java.util.Collection;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:40.
 */
public interface AspectDefinitionFactory {

  /**
   * @param haxxor
   * @param type
   * @param aspectProfiles
   * @param aspectAdviceTypes
   * @return
   */
  AspectDefinition create(Hx haxxor,
                          HxType type,
                          Map<String, AspectProfile> aspectProfiles,
                          Collection<AspectAdviceType> aspectAdviceTypes
  );
}
