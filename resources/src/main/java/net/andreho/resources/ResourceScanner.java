package net.andreho.resources;

import net.andreho.resources.impl.ResourceScannerImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by a.hofmann on 09.05.2016.
 */
public interface ResourceScanner {

  /**
   * @return locator of possible resource sources
   */
  ResourceSourceLocator getResourceSourceLocator();

  /**
   * @return associated strategy-pipeline for resource resolution
   */
  ResourceResolver getResourceResolver();

  /**
   * @return associated strategy for a selection of suitable resource types
   */
  ResourceTypeSelector getResourceTypeSelector();

  /**
   * @return associated resource's name filter
   */
  ResourceFilter getResourceFilter();

  /**
   * @param classLoader to scan
   */
  Map<String, Resource> scan(ClassLoader classLoader)
  throws IOException, URISyntaxException, ExecutionException, InterruptedException;

  /**
   * Creates default resource type implementation
   *
   * @param sourceLocator to use
   * @param resolver      to use
   * @param filter        to use
   * @param types         to use
   * @return a resource scanner ready for use.
   */
  static ResourceScanner newScanner(ResourceSourceLocator sourceLocator,
                                    ResourceResolver resolver,
                                    ResourceFilter filter,
                                    ResourceType... types) {
    return new ResourceScannerImpl(sourceLocator, resolver, filter, types);
  }

  /**
   * Creates default resource type implementation
   *
   * @param sourceLocator to use
   * @param resolver      to use
   * @param filter        to use
   * @param typeSelector  to use
   * @return a resource scanner ready for use.
   */
  static ResourceScanner newScanner(ResourceSourceLocator sourceLocator,
                                    ResourceResolver resolver,
                                    ResourceFilter filter,
                                    ResourceTypeSelector typeSelector) {
    return new ResourceScannerImpl(sourceLocator, resolver, filter, typeSelector);
  }
}
