package org.sandbox.examples.exec_flow;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Modifier;
import net.andreho.aop.api.spec.Modifiers;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.sandbox.examples.exec_flow.ExecFlowAspect.ALL_PUBLIC_METHODS;
import static org.sandbox.examples.exec_flow.ExecFlowAspect.ANNOTATED_WITH_CONTROL;

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
@Profile(
  name = ANNOTATED_WITH_CONTROL,
  methods = {
    @Methods(
      declaredBy = @Classes(@ClassWith(annotated = @Annotated(Control.class)))
    ), //OR
    @Methods(
      annotated = @Annotated(Control.class)
    ),
  }
)
@Profile(
  name = ALL_PUBLIC_METHODS,
  methods = {
    @Methods(
      modifiers = @Modifier(Modifiers.PUBLIC)
    )
  }
)
public class ExecFlowAspect {
  public static final String ALL_PUBLIC_METHODS = "All.public.Methods";
  public static final String ANNOTATED_WITH_CONTROL = "Annotated.with.Control";
  private static final String START_TIME_ALIAS = "start.time.ms";

  public static class State {
    private final long time;

    public State(final long time) {
      this.time = time;
    }
  }

  @Before(profile = ALL_PUBLIC_METHODS)
  public static
  @Attribute(START_TIME_ALIAS) State
  beforeMethodExecution(
    @Declaring Class<?> cls,
    @Intercepted Method method,
    @Args Object[] args,
    @This Object self) {

    System.out.println("BEFORE: "+method.getName()+" (" + Arrays.toString(args)+")");
    return new State(System.currentTimeMillis());
  }

  @After(profile = ANNOTATED_WITH_CONTROL)
  public static void
  afterMethodExecution(@Intercepted Method method, @Attribute(START_TIME_ALIAS) State state) {
    System.out.println("AFTER["+(System.currentTimeMillis() - state.time)+"]: "+method.getName());
  }
}
