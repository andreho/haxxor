package net.andreho.haxxor.spi;

import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 18:47.
 */
@FunctionalInterface
public interface HxClassResolver {
  /**
   * @param classLoader
   * @param className
   * @param bytecode
   * @return
   */
  default Class<?> resolveClass(ClassLoader classLoader, String className, byte[] bytecode) {
    return resolveClass(classLoader, null, className, bytecode);
  }

  /**
   * @param classLoader
   * @param protectionDomain
   * @param className
   * @param bytecode
   * @return
   */
  Class<?> resolveClass(ClassLoader classLoader, ProtectionDomain protectionDomain, String className, byte[] bytecode);
}
