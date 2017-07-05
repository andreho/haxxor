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
import static org.sandbox.aspects.security.SecurityAspect.SECURED_CONSTRUCTORS;
import static org.sandbox.aspects.security.SecurityAspect.SECURED_METHODS;

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
  name = SECURED_METHODS,
  methods = @Methods(
    type = MethodsType.METHODS,
    annotated = @Annotated(Secured.class)
  )
)
@Profile(
  name = SECURED_CONSTRUCTORS,
  methods = @Methods(
    type = MethodsType.CONSTRUCTORS,
    annotated = @Annotated(Secured.class)
  )
)
@Aspect(SECURED_CLASSES)
public class SecurityAspect {
  public static final String SECURED_CLASSES = "secured_classes";
  public static final String SECURED_METHODS = "secured_methods";
  public static final String SECURED_CONSTRUCTORS = "secured_constructors";

  @Aspect.Factory
  public static SecurityManager securityManager() {
    return SubjectServiceImpl.securityManager();
  }
}
