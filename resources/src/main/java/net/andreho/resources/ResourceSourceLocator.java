package net.andreho.resources;

import net.andreho.resources.impl.ClassLoaderResourceLocatorImpl;
import net.andreho.resources.impl.ClassPathResourceLocatorImpl;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by a.hofmann on 13.05.2016.
 */
@FunctionalInterface
public interface ResourceSourceLocator {

  /**
   * @param classLoader to inspect for possible resource sources
   */
  Collection<URL> locateResources(ClassLoader classLoader);

  static ResourceSourceLocator with(final ResourceSourceLocator... resourceSourceLocators) {
    return with(Arrays.asList(resourceSourceLocators));
  }

  static ResourceSourceLocator with(final Collection<ResourceSourceLocator> resourceSourceLocators) {
    return (classLoader) -> {
      Collection<URL> urls = new LinkedHashSet<>();
      for (ResourceSourceLocator locator : resourceSourceLocators) {
        urls.addAll(locator.locateResources(classLoader));
      }
      return urls;
    };
  }

  /**
   * @return evaluates resources given by class-path JVM parameter
   */
  static ResourceSourceLocator usingClassPath() {
    return new ClassPathResourceLocatorImpl();
  }

  /**
   * @return evaluates resources given by provided class-loader
   */
  static ResourceSourceLocator usingClassLoader() {
    return new ClassLoaderResourceLocatorImpl();
  }
}
