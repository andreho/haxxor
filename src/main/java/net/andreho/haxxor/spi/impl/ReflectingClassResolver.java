package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassResolver;
import net.andreho.haxxor.utils.ClassLoaderUtils;

import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 18:52.
 */
public class ReflectingClassResolver
  implements HxClassResolver {

  @Override
  public Class<?> resolveClass(final ClassLoader classLoader,
                               final ProtectionDomain protectionDomain,
                               final String classname,
                               final byte[] bytecode) {
    return ClassLoaderUtils.defineClass(classLoader, protectionDomain, classname, bytecode);
  }
}
