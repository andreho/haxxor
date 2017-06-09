package net.andreho.haxxor;


import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.model.AbstractBean;
import net.andreho.haxxor.model.AnnotatedBeanWithJava8Features;
import net.andreho.haxxor.model.EnumC;
import net.andreho.haxxor.spec.api.HxType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
class SandBoxTest {
  public static final Class<AbstractBean> ABSTRACT_ITEM_CLASS = AbstractBean.class;

  public static class DebugCode {
    public String[] debugStringArray(Object o) {
      if(!(o instanceof String[])) {
        throw new IllegalArgumentException();
      }
      return (String[]) o;
    }

    public int[] debugIntArray(Object o) {
      if(!(o instanceof int[])) {
        throw new IllegalArgumentException();
      }
      return (int[]) o;
    }

    public String debugString(Object o) {
      if(!(o instanceof String)) {
        throw new IllegalArgumentException();
      }
      return (String) o;
    }
  }

  @Test
  @Disabled
  void checkSomeConditions() {
    System.out.println(HxType.Modifiers.toSet(int.class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(String[].class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(int[].class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(int[][].class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(EnumC.class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(Instruction.class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(AbstractBean.class.getModifiers()));
    System.out.println(HxType.Modifiers.toSet(SandBoxTest.class.getModifiers()));

    System.out.println(int.class.getName());
    System.out.println(String[].class.getName());
    System.out.println(int[].class.getName());
    System.out.println(int[][].class.getName());
  }

  @Test
  @Disabled
  void printByteCodeOfObject() {
    HxType type = new Haxxor().resolve(ABSTRACT_ITEM_CLASS.getName());
    assertEquals(ABSTRACT_ITEM_CLASS.getName(), type.getName());
  }

  @Test
  @Disabled
  void printDebugCode() {
    Debugger.trace(AnnotatedBeanWithJava8Features.class.getName());
  }


  @Test
  @Disabled
  void cornerCases() {
//    String string = "hello";
//    StringBuilder builder = new StringBuilder("hello");
//    StringBuilder builder2 = new StringBuilder("hello");
//
//    System.out.println(((Object) string) == builder);
//    System.out.println(string.equals(builder));
//    System.out.println(builder.equals(builder2));

//    System.out.print("Foo.Bar:");
//    http://www.javapoint.ru
//    return;

//    work();
  }

  private static void work() {
    try {
      work();
    } finally {
      work();
    }
  }
}