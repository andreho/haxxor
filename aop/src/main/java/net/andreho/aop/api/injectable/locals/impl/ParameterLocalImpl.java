package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.ParameterLocal;

import java.lang.reflect.Parameter;

/**
 * <br/>Created by a.hofmann on 07.10.2017 at 10:14.
 */
public final class ParameterLocalImpl extends AbstractElementLocal<Parameter> implements ParameterLocal {
  public ParameterLocalImpl(final Parameter element) {
    super(element);
  }
}
