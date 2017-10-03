package net.andreho.examples.one.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <br/>Created by a.hofmann on 01.10.2017 at 11:44.
 */
//@Aspect
public class AroundAspectWithAnnotation {
//  @Around("execution(* examples.AspectJMain.getMessage())")
  public String aroundGetMessage(ProceedingJoinPoint thisJoinPoint) {
    System.out.println("Before");
    try {
      Object result = thisJoinPoint.proceed(thisJoinPoint.getArgs());
      System.out.println("After");
      return "AspectJ: "+ String.valueOf(result);
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }
}
