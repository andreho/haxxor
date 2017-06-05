package net.andreho.haxxor;


import net.andreho.haxxor.model.AbstractBean;
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
    System.out.println(HxType.Modifiers.toModifiers(int[].class.getModifiers()));
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
    Debugger.trace(DebugCode.class);
  }
}