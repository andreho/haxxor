package net.andreho.haxxor.model;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 17:40.
 */
public class EmbeddingClassesBean
    extends AbstractBean
    implements InterfaceD {

  final Serializable localClassFromConstructor;

  public EmbeddingClassesBean() {
    this("");
  }
  public EmbeddingClassesBean(final String name) {
    super(name);

    class LocalClassFromConstructor implements Serializable {
      final String name;
      public LocalClassFromConstructor(final String name) {
        this.name = name;
      }
    }
    this.localClassFromConstructor = new LocalClassFromConstructor(name);
  }

  @Override
  void someAbstractMethod() {

  }

  public Class<?> getLocalClassFromConstructor() {
    return localClassFromConstructor.getClass();
  }

  public Class<?> getLocalClassFromMethod() {
    return someMethod().getClass();
  }

  public Class<?> getAnonymousClass() {
    return createAnonymousClass().getClass();
  }

  private Object someMethod() {
    class LocalClassFromMethod {
      final int value;
      public LocalClassFromMethod(final int value) {
        this.value = value;
      }
    }
    return new LocalClassFromMethod(new Random().nextInt());
  }

  private Callable<String> createAnonymousClass() {
    return new Callable<String>() {
      @Override
      public String call()
      throws Exception {
        return "foo bar";
      }
    };
  }

  public final class InnerClass {
    public final int value;

    public InnerClass(final int value) {
      this.value = value;
      EmbeddingClassesBean.this.someMethod();
    }
  }

  public static class StaticNestedClass {
    public final int value;

    public StaticNestedClass(final int value) {
      this.value = value;
    }
  }
}
