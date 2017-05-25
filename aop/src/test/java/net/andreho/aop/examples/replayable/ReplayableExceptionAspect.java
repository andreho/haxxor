package net.andreho.aop.examples.replayable;

import net.andreho.aop.Aspect;
import net.andreho.aop.Catch;
import net.andreho.aop.Redefine;
import net.andreho.aop.injectable.Args;
import net.andreho.aop.injectable.Caught;
import net.andreho.aop.injectable.Intercepted;
import net.andreho.aop.injectable.Line;
import net.andreho.aop.injectable.This;
import net.andreho.aop.spec.Annotated;
import net.andreho.aop.spec.ClassRef;
import net.andreho.aop.spec.MethodRef;
import net.andreho.aop.spec.Scope;

import java.lang.reflect.Executable;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 22:48.
 */
@Aspect(
   value = Replayable.class,
   scope = {Scope.TYPE, Scope.CONSTRUCTOR, Scope.METHOD}
)
public class ReplayableExceptionAspect {

   @Catch(
      value = Throwable.class,
      methods = {
         @MethodRef(annotated = @Annotated(Replayable.class)),
         @MethodRef(declaredBy = @ClassRef(annotated = @Annotated(Replayable.class)))
      }
   )
   public static @Redefine(Caught.class) ReplayableException interceptException(
      @Caught Throwable error,
      @Intercepted Executable executable,
      @This Object host,
      @Args Object[] args,
      @Line int line) {

      return new ReplayableException(error, executable, host, args, line);
   }
}
