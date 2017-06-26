package org.sandbox.elements;

import net.andreho.aop.api.spec.Disable;
import org.sandbox.examples.exec_flow.Control;
import org.sandbox.examples.exec_flow.ExecFlowAspect;

import java.util.Arrays;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:10.
 */
//@Log
@Control
public class AnyType {
  public static void staticMethod(int a) {
    System.out.println(">>> staticMethod(...)");
  }

  public static void staticFailingMethod(String message) {
    System.out.println(">>> staticFailingMethod(...)");
    //throw new RuntimeException(message);
  }

  public String complexMethod(String name, boolean bool, byte b, short s, char c, int i, float f, long l, double d, int ... rest) {
    System.out.println(">>> complexMethod(...)");

    final StringBuilder builder = new StringBuilder();
    builder
      .append("Name: ").append(name)
      .append(",bool: ").append(bool)
      .append(",b: ").append(b)
      .append(",s: ").append(s)
      .append(",c: ").append(c)
      .append(",i: ").append(i)
      .append(",f: ").append(f)
      .append(",l: ").append(l)
      .append(",d: ").append(d)
      .append(",rest: ").append(Arrays.toString(rest))
      .append(";\n");

    double d1 = 0;
    for (int j = 0; j < 10; j++) {
      d1 += Math.max(1, j + d1);
      String str = String.valueOf(s);
      builder.append(j).append("=").append(str).append('\n');
    }
    multiply(builder, "\n", 2).toString();
    return name;
  }

  @Disable(ExecFlowAspect.class)
  private static StringBuilder multiply(StringBuilder builder, String fragment, int times) {
    System.out.println(">>> multiply(...)");
    while(times-- > 0) {
      builder.append(fragment);
    }
    return builder;
  }

  public AnyType self() {
    System.out.println(">>> self(...)");
    return this;
  }

  public AnyType failingMethod() {
    System.out.println(">>> failingMethod(...)");
    //throw new RuntimeException();
    return this;
  }
}
