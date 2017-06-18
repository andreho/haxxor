package net.andreho.aop.spi;

import net.andreho.aop.spi.impl.ChainedActivator;
import net.andreho.aop.utils.OrderUtils;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:16.
 */
public interface Activator extends Comparable<Activator> {

  /**
   * @return
   */
  boolean wasActivated();

  /**
   * @param aspects
   * @return
   */
  void activate(Collection<String> aspects);

  /**
   * @param hxType
   * @return {@link Optional#empty() empty} or
   * an optional containing either a new or the same hx-type instance
   */
  Optional<HxType> transform(final HxType hxType);

  @Override
  default int compareTo(Activator o) {
    return OrderUtils.order(this, o);
  }

  /**
   * @param activators
   * @return
   */
  static Activator with(final Collection<Activator> activators) {
    return with(activators.toArray(new Activator[0]));
  }

  /**
   * @param activators
   * @return
   */
  static Activator with(final Activator ... activators) {
    return new ChainedActivator(activators);
  }
}
