package net.andreho.aop.api.spec;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 05:06.
 */
public enum MethodsType
  implements ElementMatcher<HxMethod> {
  ANY() {
    @Override
    public boolean matches(final HxMethod element) {
      return true;
    }
    @Override
    public boolean isAny() {
      return true;
    }
  },
  /**
   * All methods and constructors except the static class-initializer
   */
  ALL() {
    @Override
    public boolean matches(final HxMethod element) {
      return !element.isClassInitializer();
    }
  },
  /**
   * All methods without any constructors and without the static class-initializer
   */
  METHODS() {
    @Override
    public boolean matches(final HxMethod element) {
      return !element.isClassInitializer() &&
             !element.isConstructor();
    }
  },
  /**
   * All constructors without any methods and the static class-initializer
   */
  CONSTRUCTORS() {
    @Override
    public boolean matches(final HxMethod element) {
      return element.isConstructor();
    }
  }
}
