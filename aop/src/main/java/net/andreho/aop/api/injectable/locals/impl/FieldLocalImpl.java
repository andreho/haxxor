package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.FieldLocal;

import java.lang.reflect.Field;

/**
 * <br/>Created by a.hofmann on 07.10.2017 at 10:14.
 */
public final class FieldLocalImpl extends AbstractElementLocal<Field> implements FieldLocal {
  public FieldLocalImpl(final Field element) {
    super(element);
  }
}
