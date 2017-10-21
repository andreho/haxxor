package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.ConstructorLocal;

import java.lang.reflect.Constructor;

/**
 * <br/>Created by a.hofmann on 07.10.2017 at 10:14.
 */
public final class ConstructorLocalImpl<V> extends AbstractElementLocal<Constructor<?>> implements ConstructorLocal {
  public ConstructorLocalImpl(final Constructor<?> element) {
    super(element);
  }
}
