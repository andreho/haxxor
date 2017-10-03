package net.andreho.examples.one.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyExample {
  public static void main(String[] args)
  throws IllegalAccessException, InstantiationException {
    Class<?> dynamicType = new ByteBuddy()
      .subclass(Object.class)
      .name("examples.Main")
      .method(ElementMatchers.named("toString"))
      .intercept(FixedValue.value("Hello World!"))
      .make()
      .load(ByteBuddyExample.class.getClassLoader())
      .getLoaded();
    System.out.println(dynamicType.newInstance().toString());
  }
}
