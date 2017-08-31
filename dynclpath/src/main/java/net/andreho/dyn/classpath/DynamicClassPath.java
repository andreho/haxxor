package net.andreho.dyn.classpath;

import net.andreho.dyn.classpath.impl.ManagerImpl;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 13:20.
 */
public abstract class DynamicClassPath {
  volatile static Manager INSTANCE = new ManagerImpl();

  /**
   * Allows to override default manager instance
   * @param manager ot use
   */
  static void overrideDefaultManager(Manager manager) {
    INSTANCE = Objects.requireNonNull(manager);
  }

  /**
   * @return the default manager
   */
  public static Manager defaultManager() {
    return INSTANCE;
  }

  private DynamicClassPath() {
    throw new UnsupportedOperationException();
  }
}
