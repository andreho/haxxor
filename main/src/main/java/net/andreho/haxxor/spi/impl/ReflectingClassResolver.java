package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassResolver;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 18:52.
 */
public class ReflectingClassResolver
  implements HxClassResolver {
  private static final Method defineClassMethod;

  static {
    try {
      defineClassMethod = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() {
        public Method run()
        throws Exception {
          Class cl = Class.forName("java.lang.ClassLoader");
          return cl.getDeclaredMethod("defineClass",
                                              new Class[]{String.class, byte[].class, int.class, int.class, ProtectionDomain.class});
        }
      });
      defineClassMethod.setAccessible(true);
    } catch (PrivilegedActionException pae) {
      throw new RuntimeException("Cannot access 'defineClass'-Method over Reflection.", pae.getException());
    }
  }

  @Override
  public Class<?> resolveClass(final ClassLoader classLoader,
                               final ProtectionDomain protectionDomain,
                               final String className,
                               final byte[] bytecode) {
    try {
      return (Class) defineClassMethod.invoke(classLoader, new Object[]{className, bytecode, 0, bytecode.length, protectionDomain});
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
