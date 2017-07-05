package org.sandbox.aspects.security;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Marker;
import net.andreho.aop.api.injectable.This;
import org.sandbox.secured.Subject;

import java.lang.reflect.Executable;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 15:29.
 */
public interface SecurityManager {
  @Before(SecurityAspect.SECURED_METHODS)
  @After(SecurityAspect.SECURED_CONSTRUCTORS)
  void verifyAccessTo(
    @This Subject target,
    @Intercepted Executable method,
    @Marker Secured secured
  )
  throws IllegalAccessException;
}
