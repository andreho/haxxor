package org.sandbox.aspects.security;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.MethodsType;
import net.andreho.aop.api.spec.Named;
import org.sandbox.secured.SubjectServiceImpl;

import static org.sandbox.aspects.security.SecurityAspect.SECURED_CLASSES;
import static org.sandbox.aspects.security.SecurityAspect.SECURED_EXECUTABLES;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:03.
 */
@Profile(
  name = SECURED_CLASSES,
  classes = @Classes(
    @ClassWith(named = @Named("org.sandbox.secured.**"))
  )
)
@Profile(
  name = SECURED_EXECUTABLES,
  methods = @Methods(
    type = MethodsType.ALL,
    annotated = @Annotated(Secured.class)
  )
)
@Aspect(SECURED_CLASSES)
public class SecurityAspect {
  public static final String SECURED_CLASSES = "secured_classes";
  public static final String SECURED_EXECUTABLES = "secured_executables";

  @Aspect.Factory
  public static SecurityManager securityManager() {
    return SubjectServiceImpl.securityManager();
  }
}
