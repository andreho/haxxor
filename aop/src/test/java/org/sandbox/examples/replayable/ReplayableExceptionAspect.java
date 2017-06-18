package org.sandbox.examples.replayable;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Catch;
import net.andreho.aop.api.Redefine;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Methods;

import java.lang.reflect.Executable;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 22:48.
 */
//@Aspect(
//    value = Replayable.class,
//    scope = {Scope.TYPE, Scope.CONSTRUCTOR, Scope.METHOD}
//)
public class ReplayableExceptionAspect {

  @Aspect.Factory
  public ReplayableExceptionAspect() {

  }

  @Catch(
      methods = {
          @Methods(
              annotated = @Annotated(value = Replayable.class)
          ),
          @Methods(
              declaredBy = @Classes(
                  @ClassWith(annotated =
                             @Annotated(value = Replayable.class))
              )
          )
      }
  )
  public @Redefine(Caught.class)
  ReplayableException interceptException(
      @Caught Throwable error,
      @Intercepted Executable executable,
      @This Object host,
      @Args Object[] args,
      @Line int line) {

    return new ReplayableException(error, executable, host, args, line);
  }
}
