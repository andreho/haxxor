package net.andreho.aop.spi;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:40.
 */
public interface AspectDefinitionFactory {

  /**
   * @param haxxor
   * @param type
   * @param aspectProfiles
   * @param aspectStepTypes
   * @return
   */
  AspectDefinition create(Haxxor haxxor,
                          HxType type,
                          Collection<AspectProfile> aspectProfiles,
                          Collection<AspectStepType> aspectStepTypes
  );
}
