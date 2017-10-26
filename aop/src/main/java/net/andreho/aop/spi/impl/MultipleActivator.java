package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.Activator;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.haxxor.api.HxType;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 21:19.
 */
public class MultipleActivator
  implements Activator {
  private final Activator[] activators;

  public MultipleActivator(final Collection<Activator> activators) {
    this(activators.toArray(new Activator[0]));
  }

  public MultipleActivator(final Activator[] activators) {
    this.activators = activators;
  }

  @Override
  public Collection<AspectAdviceType> getAspectStepTypes() {
    final Set<AspectAdviceType> aspectAdviceTypes = new LinkedHashSet<>();
    for(Activator activator : activators) {
      aspectAdviceTypes.addAll(activator.getAspectStepTypes());
    }
    return aspectAdviceTypes;
  }

  @Override
  public boolean wasActivated() {
    for(Activator activator : activators) {
      if(!activator.wasActivated()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void activate(final Collection<String> aspects) {
    for(Activator activator : activators) {
      activator.activate(aspects);
    }
  }

  @Override
  public Optional<HxType> transform(final HxType hxType) {
    HxType type = hxType;
    Optional<HxType> current = Optional.empty();
    for(Activator activator : activators) {
      Optional<HxType> result = activator.transform(type);
      if(result.isPresent()) {
        type = result.get();
        current = result;
      }
    }
    return current;
  }
}
