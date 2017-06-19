package net.andreho.aop.spi.impl.activation;

import net.andreho.aop.api.Order;
import net.andreho.aop.spi.Activator;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectDefinitionFactory;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.impl.AspectDefinitionFactoryImpl;
import net.andreho.aop.spi.impl.ElementMatcherFactoryImpl;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 19:03.
 */
@Order(500)
public class AspectActivator
    implements Activator {

  private final AtomicBoolean activated = new AtomicBoolean();
  private final AspectDefinitionFactory aspectDefinitionFactory;
  private volatile List<AspectDefinition> aspectDefinitions;

  public AspectActivator() {
    this(new AspectDefinitionFactoryImpl(new ElementMatcherFactoryImpl()));
  }

  public AspectActivator(final AspectDefinitionFactory aspectDefinitionFactory) {
    this.aspectDefinitionFactory = aspectDefinitionFactory;
  }

  public List<AspectDefinition> getAspectDefinitions() {
    return aspectDefinitions;
  }

  @Override
  public boolean wasActivated() {
    return activated.get();
  }

  @Override
  public void activate(final Collection<String> aspects) {
    if(!needsActivation()) {
      return;
    }

    final Collection<AspectStepType> aspectStepTypes = Collections.unmodifiableCollection(getAspectStepTypes());
    final Haxxor haxxor = new Haxxor(Haxxor.Flags.SKIP_CODE);
    final List<AspectDefinition> aspectDefinitions = new ArrayList<>(aspects.size());

    for(String aspectClassname : aspects) {
      AspectDefinition aspectDefinition = doActivation(haxxor, aspectClassname, aspectStepTypes);
      aspectDefinitions.add(aspectDefinition);
    }
    this.aspectDefinitions = aspectDefinitions;
  }

  protected AspectDefinition doActivation(final Haxxor haxxor,
                                          final String aspectClassname,
                                          final Collection<AspectStepType> aspectStepTypes) {

    System.out.println("Activating: " + aspectClassname);
    return aspectDefinitionFactory.create(haxxor, haxxor.resolve(aspectClassname), aspectStepTypes);
  }

  private boolean needsActivation() {
    return !activated.get() && activated.compareAndSet(false, true);
  }

  @Override
  public Optional<HxType> transform(final HxType hxType) {
    System.out.println("Reached activator: " + hxType);

    boolean modified = false;
    for(AspectDefinition aspectDefinition : getAspectDefinitions()) {
      if(aspectDefinition.getTypeMatcher().match(hxType)) {
        System.out.println("Matched class '"+hxType+"' with "+aspectDefinition);
        if(aspectDefinition.apply(hxType)) {
          modified = true;
        }
      }
    }
    if(modified) {
      return Optional.of(hxType);
    }
    return Optional.empty();
  }
}
