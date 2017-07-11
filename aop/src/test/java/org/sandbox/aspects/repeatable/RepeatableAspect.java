package org.sandbox.aspects.repeatable;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Catch;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Named;

import java.lang.reflect.Executable;

import static org.sandbox.aspects.repeatable.RepeatableAspect.REPEATABLE_ASPECT_PROFILE;

/**
 * <br/>Created by a.hofmann on 10.07.2017 at 17:15.
 */
@Profile(
  name = REPEATABLE_ASPECT_PROFILE,
  classes = @Classes(
    @ClassWith(
      named = @Named("org.sandbox.repeatable.**")
    )
  )
)
@Aspect(REPEATABLE_ASPECT_PROFILE)
public class RepeatableAspect {
  public static final String REPEATABLE_ASPECT_PROFILE = "repeatable.aspect.profile";

  @Catch(value = REPEATABLE_ASPECT_PROFILE, exception = Throwable.class)
  public static <T extends Throwable> void catchAnyException(@Caught T error,
                                                             @This Object self,
                                                             @Intercepted Executable exec,
                                                             @Args Object[] args) throws T {
    System.out.println("Caught an exception: "+error.getMessage());
    throw error;
  }
}
