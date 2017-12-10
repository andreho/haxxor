package net.andreho.haxxor.spi;

import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 18:47.
 */
@FunctionalInterface
public interface HxClassResolver {
  /**
   * @param classLoader for injection of the given bytecode
   * @param classname of the class represented in the bytecode
   * @param bytecode of the class to resolve
   * @return
   */
  default Class<?> resolveClass(ClassLoader classLoader, String classname, byte[] bytecode) {
    return resolveClass(classLoader, null, classname, bytecode);
  }

  /**
   * @param classLoader for injection of the given bytecode
   * @param protectionDomain for security checks
   * @param classname of the class represented in the bytecode
   * @param bytecode of the class to resolve
   * @return
   */
  Class<?> resolveClass(ClassLoader classLoader, ProtectionDomain protectionDomain, String classname, byte[] bytecode);
}
