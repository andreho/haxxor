package net.andreho.haxxor.model;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 17:40.
 */
public class EmbeddedClassesBean
    extends AbstractBean
    implements InterfaceD {

  public EmbeddedClassesBean(final String name) {
    super(name);

    class LocalClassFromConstructor {
      final String name;
      public LocalClassFromConstructor(final String name) {
        this.name = name;
      }
    }

    System.out.println(new LocalClassFromConstructor(name));
  }

  public void someMethod() {
    class LocalClassFromMethod {
      final int value;
      public LocalClassFromMethod(final int value) {
        this.value = value;
      }
    }

    System.out.println(new LocalClassFromMethod(new Random().nextInt()));
  }

  public Callable<String> createAnonymousClass() {
    return new Callable<String>() {
      @Override
      public String call()
      throws Exception {
        return "foo bar";
      }
    };
  }

  public class InnerClass {
    public final int value;

    public InnerClass(final int value) {
      this.value = value;
      EmbeddedClassesBean.this.someMethod();
    }
  }

  public static class StaticNestedClass {
    public final int value;

    public StaticNestedClass(final int value) {
      this.value = value;
    }
  }
}
