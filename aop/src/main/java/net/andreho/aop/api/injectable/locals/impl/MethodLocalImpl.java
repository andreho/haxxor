package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.MethodLocal;

import java.lang.reflect.Method;

/**
 * <br/>Created by a.hofmann on 07.10.2017 at 10:14.
 */
public final class MethodLocalImpl extends AbstractElementLocal<Method> implements MethodLocal {
  public MethodLocalImpl(final Method element) {
    super(element);
  }
}
