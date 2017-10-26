package net.andreho.aop.spi;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.api.HxMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:01.
 */
public abstract class Helpers {

  private static final ClassValue<Map<String, Method>> CACHED_METHODS = new ClassValue<Map<String, Method>>() {
    @Override
    protected Map<String, Method> computeValue(final Class<?> type) {
      final Map<String, Method> cache = new HashMap<>();
      for (Method method : type.getDeclaredMethods()) {
        cache.put(method.getName() + Type.getMethodDescriptor(method), method);
      }
      return unmodifiableMap(cache);
    }
  };

  private static final ClassValue<Map<String, Constructor<?>>> CACHED_CONSTRUCTORS =
    new ClassValue<Map<String, Constructor<?>>>() {
      @Override
      protected Map<String, Constructor<?>> computeValue(final Class<?> type) {
        final Map<String, Constructor<?>> cache = new HashMap<>();
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
          cache.put(Type.getConstructorDescriptor(constructor), constructor);
        }
        return unmodifiableMap(cache);
      }
    };

  private static final ClassValue<Map<String, Field>> CACHED_FIELDS = new ClassValue<Map<String, Field>>() {
    @Override
    protected Map<String, Field> computeValue(final Class<?> type) {
      final Map<String, Field> cache = new HashMap<>();
      for (Field field : type.getDeclaredFields()) {
        cache.put(field.getName(), field);
      }
      return unmodifiableMap(cache);
    }
  };

  public static String buildKeyFor(HxMethod method) {
    return method.isConstructor() ?
           method.toDescriptor() :
           method.toDescriptor(new StringBuilder(method.getName())).toString();
  }

  /**
   * @param o
   * @return
   */
  public static Class<?> getClassOf(Object o) {
    return o.getClass();
  }

  /**
   * @param cls
   * @param nameWithSignature
   * @return
   */
  public static Method getMethodOf(Class<?> cls,
                                   String nameWithSignature) {
    return CACHED_METHODS.get(cls).get(nameWithSignature);
  }

  /**
   * @param cls
   * @param signature
   * @return
   */
  public static Constructor<?> getConstructorOf(Class<?> cls,
                                                String signature) {
    return CACHED_CONSTRUCTORS.get(cls).get(signature);
  }

  /**
   * @param cls
   * @param name
   * @return
   */
  public static Field getFieldOf(Class<?> cls,
                                 String name) {
    return CACHED_FIELDS.get(cls).get(name);
  }

  /**
   * @param cls
   * @param name
   * @param type
   * @return
   */
  public static Field getFieldOf(Class<?> cls,
                                 String name,
                                 String type) {
    return CACHED_FIELDS.get(cls).get(name);
  }

  /**
   * @param executable
   * @param index
   * @return
   */
  public static Parameter getParameterOf(Executable executable,
                                         int index) {
    return executable.getParameters()[index];
  }

  /**
   * @param annotatedElement
   * @param annotationsType
   * @return
   */
  public static <T extends Annotation> T getAnnotationOf(AnnotatedElement annotatedElement,
                                                         Class<T> annotationsType) {
    return annotatedElement.getAnnotation(annotationsType);
  }

  private Helpers() {
  }
}
