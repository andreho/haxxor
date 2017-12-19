package net.andreho.haxxor.spi;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.impl.DefaultProvidable;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 01:04.
 */
public interface HxProvidable<T> {
  class Defaults {
    public static final HxProvidable<List<HxType>> DEFAULT_INTERFACES_FOR_ARRAY =
      HxProvidable.create("default_interfaces_for_array");
  }

  /**
   * @return
   */
  String name();

  /**
   * @param hx
   * @return
   */
  T fetch(Hx hx);

  /**
   * @param name
   * @param <T>
   * @return
   */
  static <T> HxProvidable<T> create(String name) {
    return new DefaultProvidable<>(name);
  }
}
