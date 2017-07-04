package org.sandbox.aspects.security;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.This;

import java.lang.reflect.Executable;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 15:29.
 */
public interface SecurityManager {
  @Before(SecurityAspect.METHODS)
  @After(SecurityAspect.CONSTRUCTORS)
  void checkAccessTo(@This Object target, @Intercepted Executable method);
}
