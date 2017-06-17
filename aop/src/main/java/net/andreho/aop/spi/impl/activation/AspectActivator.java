package net.andreho.aop.spi.impl.activation;

import net.andreho.aop.Order;
import net.andreho.aop.spi.Activator;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 19:03.
 */
@Order(500)
public class AspectActivator
    implements Activator {

  private final AtomicBoolean initialized = new AtomicBoolean();

  public AspectActivator() {
  }

  @Override
  public Activator activate(final Collection<String> aspects) {
    if(isInitializable()) {

    }
    return this;
  }

  private boolean isInitializable() {
    return !initialized.get() && initialized.compareAndSet(false, true);
  }

  @Override
  public Optional<HxType> transform(final HxType hxType) {
    System.out.println(hxType);
    return Optional.empty();
  }
}
