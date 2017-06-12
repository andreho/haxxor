package net.andreho.haxxor;


import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.model.AbstractBean;
import net.andreho.haxxor.model.AnnotatedBeanWithJava8Features;
import net.andreho.haxxor.model.AnnotationC;
import net.andreho.haxxor.model.EnumC;
import net.andreho.haxxor.spec.api.HxType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    System.out.println(HxType.Modifiers.toSet(HxInstruction.class.getModifiers()));
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

  static class Testing {
    static void foo()
    throws FileNotFoundException {
      @AnnotationC("x") String name = "";
      try(@AnnotationC("y") InputStream inputStream = new FileInputStream(new File("."))){

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  @Test
  @Disabled
  void cornerCases() {
    Debugger.trace(Testing.class);
  }
}