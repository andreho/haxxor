package net.andreho.aop.spi.impl.activation;

import net.andreho.aop.api.Order;
import net.andreho.aop.spi.Activator;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectDefinitionFactory;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectProfileFactory;
import net.andreho.aop.spi.DefaultAspectStepTypes;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.aop.spi.impl.AspectDefinitionFactoryImpl;
import net.andreho.aop.spi.impl.AspectProfileFactoryImpl;
import net.andreho.aop.spi.impl.ElementMatcherFactoryImpl;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

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
  public Collection<AspectAdviceType> getAspectStepTypes() {
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

    final Collection<AspectAdviceType> aspectAdviceTypes =
      unmodifiableCollection(getAspectStepTypes());

    final AspectProfileFactory aspectProfileFactory = getAspectProfileFactory();

    final Hx haxxor = Hx.builder().withFlags(Hx.Flags.SKIP_CODE).build();
    final Collection<HxType> aspectTypes = new ArrayList<>(aspects.size());
    final Collection<AspectProfile> aspectProfiles = new LinkedHashSet<>();

    for(String aspectClassname : aspects) {
      final HxType aspectType = haxxor.resolve(aspectClassname);
      aspectTypes.add(aspectType);
      aspectProfiles.addAll(aspectProfileFactory.create(aspectType));
    }

    final Map<String, AspectProfile> aspectProfileMap = unmodifiableMap(
        aspectProfiles.stream().collect(Collectors.toMap(AspectProfile::getName, Function.identity()))
      );
    final List<AspectDefinition> aspectDefinitions = new ArrayList<>(aspects.size());

    for(HxType aspectType : aspectTypes) {
      final AspectDefinition aspectDefinition =
        doActivation(aspectType, aspectProfileMap, aspectAdviceTypes);

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

    aspectDefinitions.sort(Comparator.naturalOrder());
    this.aspectDefinitions = unmodifiableList(aspectDefinitions);
  }

  protected AspectDefinition doActivation(final HxType aspectType,
                                          final Map<String, AspectProfile> aspectProfiles,
                                          final Collection<AspectAdviceType> aspectAdviceTypes) {

    try {
      return aspectDefinitionFactory.create(
        aspectType.getHaxxor(),
        aspectType,
        aspectProfiles,
        aspectAdviceTypes
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
      if(aspectDefinition.getTypeMatcher().matches(hxType)) {
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
