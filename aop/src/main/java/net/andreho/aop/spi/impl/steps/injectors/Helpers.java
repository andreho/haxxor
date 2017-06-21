package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.asm.org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:01.
 */
public abstract class Helpers {
  private static final ClassValue<Map<String, Method>> CACHED_METHODS = new ClassValue<Map<String, Method>>() {
    @Override
    protected Map<String, Method> computeValue(final Class<?> type) {
      final Map<String, Method> cache = new HashMap<>();
      for(Method method : type.getDeclaredMethods()) {
        cache.put(method.getName() + Type.getMethodDescriptor(method), method);
      }
      return cache;
    }
  };

  private static final ClassValue<Map<String, Constructor<?>>> CACHED_CONSTRUCTORS = new ClassValue<Map<String, Constructor<?>>>() {
    @Override
    protected Map<String, Constructor<?>> computeValue(final Class<?> type) {
      final Map<String, Constructor<?>> cache = new HashMap<>();
      for(Constructor<?> constructor : type.getDeclaredConstructors()) {
        cache.put(constructor.getName() + Type.getConstructorDescriptor(constructor), constructor);
      }
      return cache;
    }
  };

  private static final ClassValue<Map<String, Field>> CACHED_FIELDS = new ClassValue<Map<String, Field>>() {
    @Override
    protected Map<String, Field> computeValue(final Class<?> type) {
      final Map<String, Field> cache = new HashMap<>();
      for(Field field : type.getDeclaredFields()) {
        cache.put(field.getName(), field);
      }
      return cache;
    }
  };

  private Helpers() {
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
  public static Method getMethodOf(Class<?> cls, String nameWithSignature) {
    return CACHED_METHODS.get(cls).get(nameWithSignature);
  }

  /**
   * @param cls
   * @param nameWithSignature
   * @return
   */
  public static Constructor<?> getConstructorOf(Class<?> cls, String nameWithSignature) {
    return CACHED_CONSTRUCTORS.get(cls).get(nameWithSignature);
  }

  /**
   * @param cls
   * @param name
   * @return
   */
  public static Field getFieldOf(Class<?> cls, String name) {
    return CACHED_FIELDS.get(cls).get(name);
  }
}
