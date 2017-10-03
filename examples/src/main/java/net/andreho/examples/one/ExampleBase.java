package net.andreho.examples.one;

import javassist.CannotCompileException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 28.09.2017 at 03:25.
 */
public class ExampleBase {

  // used by injectClassIntoClassLoader().
  private static java.lang.reflect.Method defineClass1, defineClass2;

  static {
    try {
      AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Object run()
        throws Exception {
          Class cl = Class.forName("java.lang.ClassLoader");
          defineClass1 = cl.getDeclaredMethod("defineClass",
                                              new Class[]{String.class, byte[].class,
                                                          int.class, int.class});

          defineClass2 = cl.getDeclaredMethod("defineClass",
                                              new Class[]{String.class, byte[].class,
                                                          int.class, int.class, ProtectionDomain.class});
          return null;
        }
      });
    } catch (PrivilegedActionException pae) {
      throw new RuntimeException("cannot initialize ClassPool", pae.getException());
    }
  }

  public static Class injectClassIntoClassLoader(String name,
                                                 byte[] bytecode)
  throws CannotCompileException {
    return injectClassIntoClassLoader(name, bytecode, Thread.currentThread().getContextClassLoader());
  }

  public static Class injectClassIntoClassLoader(String name,
                                                 byte[] bytecode,
                                                 ClassLoader loader)
  throws CannotCompileException {
    return injectClassIntoClassLoader(name, bytecode, loader, null);
  }

  public static Class injectClassIntoClassLoader(String name,
                                                 byte[] bytecode,
                                                 ClassLoader loader,
                                                 ProtectionDomain domain)
  throws CannotCompileException {
    try {
      java.lang.reflect.Method method;
      Object[] args;
      if (domain == null) {
        method = defineClass1;
        args = new Object[]{name, bytecode, 0, bytecode.length};
      } else {
        method = defineClass2;
        args = new Object[]{name, bytecode, 0, bytecode.length, domain};
      }
      return (Class) injectClassIntoClassLoader(method, loader, args);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static synchronized Object injectClassIntoClassLoader(Method method,
                                                                ClassLoader loader,
                                                                Object[] args)
  throws Exception {
    method.setAccessible(true);

    try {
      return method.invoke(loader, args);
    } finally {
      method.setAccessible(false);
    }
  }

  public static Object invokeMainMethod(final String[] args,
                                        final Class<?> aClass)
  throws IllegalAccessException,
         InvocationTargetException,
         NoSuchMethodException {
    return aClass.getMethod("main", String[].class).invoke(null, new Object[]{args});
  }
}
