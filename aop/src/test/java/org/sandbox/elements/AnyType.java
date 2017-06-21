package org.sandbox.elements;

import org.sandbox.examples.exec_flow.Control;

import java.util.Arrays;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:10.
 */
@Control
//@TransparentLog
public class AnyType {
  public static void staticMethod(int a) {
    System.out.println(">>> staticMethod(...)");
  }

  public String manyArgumentsMethod(String name, boolean bool, byte b, short s, char c, int i, float f, long l, double d, int ... rest) {
    System.out.println(">>> test(...)");

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
    //info("test");
    return builder.toString();
  }

  public AnyType self() {
    System.out.println(">>> self(...)");
    return this;
  }
}
