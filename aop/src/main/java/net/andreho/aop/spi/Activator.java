package net.andreho.aop.spi;

import net.andreho.aop.utils.OrderUtils;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:16.
 */
public interface Activator extends Comparable<Activator> {
  /**
   * @param aspects
   * @return
   */
  Activator activate(Collection<String> aspects);

  /**
   * @param hxType
   * @return
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
    return new Activator() {
      @Override
      public Activator activate(final Collection<String> aspects) {
        for(Activator activator : activators) {
          activator.activate(aspects);
        }
        return this;
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
    };
  }
}
