package net.andreho.aop.utils;

import sun.reflect.CallerSensitive;

import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 17:32.
 */
public abstract class ClassLoadingUtils
  //extends sun.reflect.MagicAccessorImpl
{
  @CallerSensitive
  public static Class<?> loadClass(ClassLoader classLoader, ProtectionDomain protectionDomain, byte[] byteCode) {
    throw new UnsupportedOperationException();
  }

  private ClassLoadingUtils() {
  }
}
