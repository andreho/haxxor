package org.sandbox.examples.exec_flow;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Modifier;
import net.andreho.aop.api.spec.Modifiers;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 08:36.
 */
@Aspect(
  classes = @Classes(
    @ClassWith(
      modifiers = @Modifier(value = Modifiers.ABSTRACT | Modifiers.INTERFACE, negate = true)
    )
  )
)
public class ExecFlowAspect {

  @Before(
    methods = @Methods(
      annotated = @Annotated(Control.class)
    )
  )
  public static void beforeMethodExecution() {
    System.out.println("Before ...");
  }

  @After(
    methods = @Methods(
      annotated = @Annotated(Control.class)
    )
  )
  public static void afterMethodExecution() {
    System.out.println("After ...");
  }
}
