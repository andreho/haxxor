package net.andreho.examples.one.aspectj;

/**
 * <br/>Created by a.hofmann on 01.10.2017 at 10:36.
 */
public aspect AroundAspect {
  pointcut AroundMain(): execution(* examples.Main.main(..));

  void around() : AroundMain() {
    System.out.print("Before: ");
    try {
      proceed();
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
    System.out.println("Done.");
  }
}
