package net.andreho.dyn.classpath.impl;

import net.andreho.dyn.classpath.Entry;
import net.andreho.dyn.classpath.EntryInstaller;
import net.andreho.dyn.classpath.InstallationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 11:21.
 */
public class URLClassLoaderEntryInstaller
  implements EntryInstaller<URLClassLoader> {

  private static final Logger LOG = LoggerFactory.getLogger(URLClassLoaderEntryInstaller.class);
  private static final Method ADD_URL_METHOD;

  static {
    try {
      ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
        "Unable to get the required method of: " + URLClassLoader.class.getName(), e);
    }
    ADD_URL_METHOD.setAccessible(true);
  }

  @Override
  public boolean isCompatibleWith(final ClassLoader classLoader) {
    return classLoader instanceof URLClassLoader;
  }

  @Override
  public InstallationResult installOn(final URLClassLoader classLoader,
                                      final Entry entry) {
    final URL classPathUrl = entry.getClassPathUrl();
    for (URL url : classLoader.getURLs()) {
      if (Objects.equals(url, classPathUrl)) {
        return InstallationResult.EXISTS;
      }
    }

    return installUsingReflection(classLoader, entry, classPathUrl);
  }

  protected InstallationResult installUsingReflection(final URLClassLoader classLoader,
                                                      final Entry entry,
                                                      final URL classPathUrl) {
    try {
      //called methods are synchronized
      ADD_URL_METHOD.invoke(classLoader, classPathUrl);
    } catch (IllegalAccessException | InvocationTargetException e) {
      LOG.error("Unable to install dynamic classpath: " + entry, e);
      return InstallationResult.FAILED;
    }

    return InstallationResult.SUCCESSFUL;
  }
}
