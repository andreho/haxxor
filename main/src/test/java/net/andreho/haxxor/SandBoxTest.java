package net.andreho.haxxor;


import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.model.AbstractBean;
import net.andreho.haxxor.model.CodeAnnotationBean;
import net.andreho.haxxor.model.EnumC;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
public class SandBoxTest {

  public static final Class<AbstractBean> ABSTRACT_ITEM_CLASS = AbstractBean.class;

  public SandBoxTest() {
    super();
    try {

    } catch (Throwable t) {
      System.out.println(t);
    } finally {
      System.out.println("finally");
    }
  }

  @FunctionalInterface
  interface VOSJ<R, A, B, C> {

    default R invoke(Object... args) {
      execute((A) args[0], ((Number) args[1]).shortValue(), ((Number) args[2]).longValue());
      return null;
    }

    default R execute(A a,
                      B b,
                      C c) {
      execute(a, ((Number) b).shortValue(), ((Number) c).longValue());
      return null;
    }

    void execute(A o,
                 short i,
                 long l);

    default Callable<R> toCallable(final Object... args) {
      return () -> invoke(args);
    }

    default Runnable toRunnable(final Object... args) {
      return () -> invoke(args);
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
    Debugger.trace(CodeAnnotationBean.class, Debugger.SKIP_DEBUG);
  }

  @Test
  @Disabled
  void arrayToString() {
    System.out.println(Arrays.toString(new String[]{"123", "1234"}));
    System.out.println(Arrays.toString(new int[]{123, 1234}));
  }

  @Test
  @Disabled
  void cornerCases() {
    Debugger.trace(this.getClass());
  }

  void lambda()
  throws Exception {
    final Object[] args = new Object[]{"a", 1, 2L};
    final VOSJ<Void, String, Short, Long> adapter = this::__lambda__;
    Callable<Void> callable = adapter.toCallable(args);
    callable.call();
  }

  private void __lambda__(String a,
                          short b,
                          long c) {

  }

  @Test
  @Disabled
  void syntheticException() {
    Debugger.trace(SyntheticException.class);
    new SyntheticException().accept("test");
  }

  public static class DebugCode {

    public String[] debugStringArray(Object o) {
      if (!(o instanceof String[])) {
        throw new IllegalArgumentException();
      }
      return (String[]) o;
    }

    public int[] debugIntArray(Object o) {
      if (!(o instanceof int[])) {
        throw new IllegalArgumentException();
      }
      return (int[]) o;
    }

    public String debugString(Object o) {
      if (!(o instanceof String)) {
        throw new IllegalArgumentException();
      }
      return (String) o;
    }
  }

  static class SyntheticException
    implements Consumer<String> {

    @Override
    public void accept(final String s) {
      throw new RuntimeException(s);
    }
  }
}