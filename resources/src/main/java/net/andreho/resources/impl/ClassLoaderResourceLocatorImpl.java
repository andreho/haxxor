package net.andreho.resources.impl;

import net.andreho.resources.ResourceSourceLocator;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;

/**
 * Created by a.hofmann on 13.05.2016.
 */
public class ClassLoaderResourceLocatorImpl
    implements ResourceSourceLocator {

  public static final ResourceSourceLocator INSTANCE = new ClassLoaderResourceLocatorImpl();

  @Override
  public Collection<URL> locateResources(final ClassLoader classLoader) {
    Collection<URL> result = new LinkedHashSet<>();
    try {
      for (final Enumeration<URL> resources = classLoader.getResources(""); resources.hasMoreElements(); ) {
        URL url = resources.nextElement();
        result.add(url);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Unable to fetch resources from given class-loader:" + classLoader, e);
    }
    return result;
  }
}