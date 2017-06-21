package net.andreho.aop.api.spec;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 05:06.
 */
public enum MethodsType
  implements ElementMatcher<HxMethod> {
  ANY() {
    @Override
    public boolean match(final HxMethod element) {
      return true;
    }
    @Override
    public boolean isAny() {
      return true;
    }
  },
  ALL() {
    @Override
    public boolean match(final HxMethod element) {
      return !"<clinit>".equals(element.getName());
    }
  },
  METHODS() {
    @Override
    public boolean match(final HxMethod element) {
      return !"<clinit>".equals(element.getName()) &&
             !"<init>".equals(element.getName());
    }
  },
  CONSTRUCTORS() {
    @Override
    public boolean match(final HxMethod element) {
      return "<init>".equals(element.getName());
    }
  }
}
