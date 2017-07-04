package org.sandbox.examples.exec_flow;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Result;
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
import static org.sandbox.examples.exec_flow.ExecFlowAspect.TARGET_CLASSES;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 08:36.
 */
@Profile(name = TARGET_CLASSES,
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
//@Aspect(TARGET_CLASSES)
public class ExecFlowAspect {
  public static final String ALL_PUBLIC_METHODS = "all.public.methods";
  public static final String ANNOTATED_WITH_CONTROL = "@Control";
  public static final String TARGET_CLASSES = "target_classes";

  private static final String STATE = "start.time.ms";

  public static class State {
    private final long time;
    public State(final long time) {
      this.time = time;
    }
    long diff() {
      return System.currentTimeMillis() - time;
    }
  }

//  @Aspect.Factory
//  public static ExecFlowAspect create() {
//    return new ExecFlowAspect();
//  }

  @Before(ANNOTATED_WITH_CONTROL)
  public static
  @Attribute(STATE) State
  beforeMethodExecution(
    @Intercepted Method method,
    @Args Object[] args) {

    System.out.printf("Before: %s with arguments (%s)\n", method, Arrays.toString(args));
    return new State(System.currentTimeMillis());
  }

  @After(ANNOTATED_WITH_CONTROL)
  public static void
  afterMethodExecution(@Intercepted Method method, @Attribute(STATE) State state, @Result Object result) {
    System.out.printf("After: %s with time %d and result %s\n", method, state.diff(), result);
  }
}
