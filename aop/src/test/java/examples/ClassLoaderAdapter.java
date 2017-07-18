package examples;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 17.07.2017 at 19:24.
 */
public class ClassLoaderAdapter extends ClassLoader {

  public final Consumer<Class<?>> resolveClassFunction() {
    return this::resolveClass;
  }

  public final Function<String, Class<?>> findClassFunction() {
    return this::findLoadedClass;
  }

  public final BiFunction<String, byte[], Class<?>> defineClassFunction() {
    return (classname, byteCode) ->
      defineClass(classname, byteCode, 0, byteCode.length);
  }

  @Test
  void test() {
    System.out.println(getClass().getClassLoader());
//    Debugger.trace(getClass());
    Class<?> loadedClass = findLoadedClass("java.lang.ClassLoader");
  }

}
