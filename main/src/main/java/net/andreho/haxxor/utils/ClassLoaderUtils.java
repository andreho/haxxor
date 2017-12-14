package net.andreho.haxxor.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 20:47.
 */
public class ClassLoaderUtils {
  private static final Method defineClassMethod;
  private static final Field classesField;

  static {
    try {
      defineClassMethod = AccessController.doPrivileged((PrivilegedExceptionAction<Method>) () ->
        ClassLoader.class.getDeclaredMethod(
          "defineClass", String.class, byte[].class, int.class, int.class, ProtectionDomain.class));
      defineClassMethod.setAccessible(true);

      classesField = AccessController.doPrivileged((PrivilegedExceptionAction<Field>) () ->
        ClassLoader.class.getDeclaredField("classes"));
      classesField.setAccessible(true);
    } catch (PrivilegedActionException pae) {
      throw new RuntimeException(
        "Cannot access 'ClassLoader.defineClass'-Method over Reflection.", pae.getException());
    }
  }

  private static Iterable<Class<?>> fetchFields(final ClassLoader classLoader) {
    try {
      return (Iterable<Class<?>>) classesField.get(classLoader);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }

  public static boolean wasAlreadyLoadedWith(final ClassLoader classLoader, final String classname) {
    ClassLoader current = classLoader;

    while (current != null) {
      for(Class<?> cls : fetchFields(current)) {
        if(classname.equals(cls.getName())) {
          return true;
        }
      }
      current = current.getParent();
    }
    return false;
  }

  public static Class<?> defineClass(final ClassLoader classLoader,
                               final ProtectionDomain protectionDomain,
                               final String classname,
                               final byte[] bytecode) {
    try {
      if(wasAlreadyLoadedWith(classLoader, classname) || classname.startsWith("java.")) {
        return Class.forName(classname, false, classLoader);
      }
      return (Class) defineClassMethod
        .invoke(classLoader, classname, bytecode, 0, bytecode.length, protectionDomain);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
