package net.andreho.aop.spi.impl.activation;

import net.andreho.aop.api.Order;
import net.andreho.aop.spi.Activator;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectDefinitionFactory;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectProfileFactory;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.DefaultAspectStepTypes;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.aop.spi.impl.AspectDefinitionFactoryImpl;
import net.andreho.aop.spi.impl.AspectProfileFactoryImpl;
import net.andreho.aop.spi.impl.ElementMatcherFactoryImpl;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 19:03.
 */
@Order(Activator.DEFAULT_ORDER)
public class AspectActivator
    implements Activator {
  private static final Logger LOG = LoggerFactory.getLogger(AspectActivator.class);

  private final AtomicBoolean activated = new AtomicBoolean();

  private final ElementMatcherFactory elementMatcherFactory;
  private final AspectDefinitionFactory aspectDefinitionFactory;
  private final AspectProfileFactory aspectProfileFactory;

  private volatile List<AspectDefinition> aspectDefinitions;

  public AspectActivator() {
    this(new ElementMatcherFactoryImpl());
  }

  public AspectActivator(final ElementMatcherFactory elementMatcherFactory) {
    this.elementMatcherFactory = elementMatcherFactory;
    this.aspectDefinitionFactory = createDefinitionFactory(elementMatcherFactory);
    this.aspectProfileFactory = createAspectProfileFactory(elementMatcherFactory);
  }

  protected AspectDefinitionFactoryImpl createDefinitionFactory(final ElementMatcherFactory elementMatcherFactory) {
    return new AspectDefinitionFactoryImpl(elementMatcherFactory);
  }

  protected AspectProfileFactory createAspectProfileFactory(final ElementMatcherFactory elementMatcherFactory) {
    return new AspectProfileFactoryImpl(elementMatcherFactory);
  }

  public List<AspectDefinition> getAspectDefinitions() {
    return aspectDefinitions;
  }

  public ElementMatcherFactory getElementMatcherFactory() {
    return elementMatcherFactory;
  }

  public AspectDefinitionFactory getAspectDefinitionFactory() {
    return aspectDefinitionFactory;
  }

  public AspectProfileFactory getAspectProfileFactory() {
    return aspectProfileFactory;
  }

  @Override
  public Collection<AspectStepType> getAspectStepTypes() {
    return Stream.of(DefaultAspectStepTypes.values())
                 .map(DefaultAspectStepTypes::getStepType)
                 .collect(Collectors.toSet());
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
    final AspectProfileFactory aspectProfileFactory = getAspectProfileFactory();

    final Haxxor haxxor = new Haxxor(Haxxor.Flags.SKIP_CODE);
    final Collection<HxType> aspectTypes = new ArrayList<>(aspects.size());
    final Collection<AspectProfile> aspectProfiles = new LinkedHashSet<>();

    for(String aspectClassname : aspects) {
      final HxType aspectType = haxxor.resolve(aspectClassname);
      aspectProfiles.addAll(aspectProfileFactory.create(aspectType));
      aspectTypes.add(aspectType);
    }

    final List<AspectDefinition> aspectDefinitions = new ArrayList<>(aspects.size());

    for(HxType aspectType : aspectTypes) {
      final AspectDefinition aspectDefinition = doActivation(aspectType, aspectProfiles, aspectStepTypes);
      if(aspectDefinition != null) {
        aspectDefinitions.add(aspectDefinition);
        if(LOG.isDebugEnabled()) {
          LOG.debug("Aspect was activated: {}", aspectType.getName());
        }
      } else {
        if(LOG.isDebugEnabled()) {
          LOG.debug("Aspect couldn't be activated: {}", aspectType.getName());
        }
      }
    }

    this.aspectDefinitions = aspectDefinitions;
  }

  protected AspectDefinition doActivation(final HxType aspectType,
                                          final Collection<AspectProfile> aspectProfiles,
                                          final Collection<AspectStepType> aspectStepTypes) {

    try {
      return aspectDefinitionFactory.create(
        aspectType.getHaxxor(),
        aspectType,
        aspectProfiles,
        aspectStepTypes
      );
    } catch (Throwable t) {
      LOG.error("Aspect's activation ends up in an error: " + aspectType.getName(), t);
    }
    return null;
  }

  private boolean needsActivation() {
    return !activated.get() && activated.compareAndSet(false, true);
  }

  @Override
  public Optional<HxType> transform(final HxType hxType) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Trying to transform type: {}", hxType.getName());
    }

    boolean modified = false;
    for(AspectDefinition aspectDefinition : getAspectDefinitions()) {
      if(aspectDefinition.getTypeMatcher().match(hxType)) {
        if(LOG.isDebugEnabled()) {
          LOG.debug("Type's pre-matching was successful for: {}", hxType.getName());
        }

        if(aspectDefinition.apply(hxType)) {
          modified = true;

          if(LOG.isDebugEnabled()) {
            LOG.debug("Modifications of {} were applied to: {}", aspectDefinition.getType().getName(), hxType.getName());
          }
        }
      }
    }
    if(modified) {
      return Optional.of(hxType);
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("Type wasn't modified: {}", hxType.getName());
    }

    return Optional.empty();
  }
}
