package org.sandbox.aspects.security;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Marker;
import net.andreho.aop.api.injectable.This;
import org.sandbox.secured.Subject;

import java.lang.reflect.Executable;

import static org.sandbox.aspects.security.SecurityAspect.SECURED_EXECUTABLES;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 15:29.
 */
public interface SecurityManager {
  @Before(SECURED_EXECUTABLES)
  @After(SECURED_EXECUTABLES)
  void verifyAccessTo(
    @This Subject target,
    @Marker Secured secured,
    @Intercepted Executable method
  )
  throws IllegalAccessException;
}
