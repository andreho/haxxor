package org.sandbox.examples.exec_flow;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.DefineVariable;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Modifier;
import net.andreho.aop.api.spec.Modifiers;

import java.util.Arrays;

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
    methods = {
      @Methods(declaredBy = @Classes(@ClassWith(annotated = @Annotated(Control.class)))),
      @Methods(annotated = @Annotated(Control.class)),
    }
  )
  public static
  @DefineVariable("time") long
  beforeMethodExecution(@Declaring Class<?> cls, @Args Object[] args, @Arity int arity, @Line int line, @This Object self) {
    System.out.println("BEFORE: beforeMethodExecution(" + Arrays.toString(args)+") at "+line);
    return System.currentTimeMillis();
  }

  @After(
    methods = {
      @Methods(declaredBy = @Classes(@ClassWith(annotated = @Annotated(Control.class)))),
      @Methods(annotated = @Annotated(Control.class)),
    }
  )
  public static void afterMethodExecution() {
    System.out.println("AFTER: afterMethodExecution(...)");
  }
}
