package net.andreho.haxxor.stub;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 19:15.
 */
public class ClassInjector {
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
          defineClass1.setAccessible(true);
          defineClass2 = cl.getDeclaredMethod("defineClass",
                                              new Class[]{String.class, byte[].class,
                                                          int.class, int.class, ProtectionDomain.class});
          defineClass2.setAccessible(true);
          return null;
        }
      });
    } catch (PrivilegedActionException pae) {
      throw new RuntimeException("cannot initialize ClassPool", pae.getException());
    }
  }

  public static Class injectClassIntoClassLoader(String name,
                                                 byte[] bytecode) {
    return injectClassIntoClassLoader(name, bytecode, Thread.currentThread().getContextClassLoader());
  }

  public static Class injectClassIntoClassLoader(String name,
                                                 byte[] bytecode,
                                                 ClassLoader loader) {
    return injectClassIntoClassLoader(name, bytecode, loader, null);
  }

  public static Class injectClassIntoClassLoader(String name,
                                                 byte[] bytecode,
                                                 ClassLoader loader,
                                                 ProtectionDomain domain) {
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
    try {
      return method.invoke(loader, args);
    } finally {
      method.setAccessible(false);
    }
  }
}
