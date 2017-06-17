package net.andreho.aop.service;

import net.andreho.aop.spi.Activator;
import net.andreho.aop.spi.PackageFilter;
import net.andreho.aop.spi.impl.AspectClassVisitor;
import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.HaxxorBuilder;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.resources.Resource;
import net.andreho.resources.ResourceResolver;
import net.andreho.resources.ResourceScanner;
import net.andreho.resources.ResourceSourceLocator;
import net.andreho.resources.ResourceType;
import net.andreho.resources.ResourceTypeSelector;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 20:48.
 */
public final class AopTypeTransformerEntry {

  private static final PackageFilter[] EMPTY_PACKAGE_FILTERS = {};
  private static final Activator[] EMPTY_ACTIVATORS = {};
  private static final String SERVICE_LOADER_NAME = "java.util.ServiceLoader";

  private final AtomicBoolean installingAspects = new AtomicBoolean();
  private volatile PackageFilter filter;
  private volatile Activator activator;
  private volatile Collection<String> aspects;

  public AopTypeTransformerEntry() {
  }

  private Activator fetchActivator(ClassLoader classLoader) {
    Activator activator = this.activator;
    if (activator == null) {
      this.activator = activator = locateActivator(classLoader);
    }
    return activator;
  }

  private PackageFilter fetchTypeTransformerFilter(ClassLoader classLoader) {
    PackageFilter filter = this.filter;
    if (filter == null) {
      this.filter = filter = locateFilter(classLoader);
    }
    return filter;
  }

  private boolean filter(final ClassLoader classLoader,
                         final String className) {
    return fetchTypeTransformerFilter(classLoader).filter(className);
  }

  public byte[] execute(final ClassLoader loader,
                        final String className,
                        final Class<?> classBeingRedefined,
                        final ProtectionDomain protectionDomain,
                        final byte[] classfileBuffer) {

    Activator activator;

    try {
      if (!filter(loader, className)) {
        return null;
      }
      activator = fetchActivator(loader);
    } catch (Exception e) {
      throw new IllegalStateException("Failed at: " + className, e);
    }

    if (activator == null) {
      return null;
    }

    long start = System.currentTimeMillis();
    long used = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();

    final Haxxor haxxor = new Haxxor(HaxxorBuilder.with(loader));
    final HxType hxType = haxxor.resolve(className, 0, classfileBuffer);

    activator.activate(installAspects(loader));
    Optional<HxType> optional = activator.transform(hxType);

    if (optional.isPresent()) {
      byte[] bytes = optional.get().toByteCode();
      printInfo(className, true, start, used);
      return bytes;
    }
    printInfo(className, false, start, used);
    return null;
  }

  private void printInfo(final String className,
                         final boolean changed,
                         final long start,
                         final long used) {
    System.out.printf("%s %s transformed in: %6d ms and used memory: %15.2f Kb \n",
                      className,
                      changed ? "WAS" : "NOT",
                      (System.currentTimeMillis() - start),
                      ((ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() - used) / 1024d)
    );
  }

  private Activator locateActivator(final ClassLoader loader) {
    Iterable<Activator> iterable = perClassLoaderLookUp(loader, Activator.class);
    return Activator.with(postProcess(iterable, EMPTY_ACTIVATORS));
  }

  private PackageFilter locateFilter(final ClassLoader loader) {
    Iterable<PackageFilter> iterable = perClassLoaderLookUp(loader, PackageFilter.class);
    return PackageFilter.with(postProcess(iterable, EMPTY_PACKAGE_FILTERS));
  }

  private <T> T[] postProcess(final Iterable<T> iterable,
                              final T[] arrayPrototype) {
    List<T> foundFilters = new ArrayList<>();
    for (T element : iterable) {
      foundFilters.add(element);
    }

    final T[] transformers = foundFilters.toArray(arrayPrototype);
    Arrays.sort(transformers);
    return transformers;
  }

  private <T> Iterable<T> perClassLoaderLookUp(final ClassLoader loader,
                                               final Class<T> elementType) {
    Iterable<T> iterable;
    try {
      iterable =
          (Iterable<T>) loader.loadClass(SERVICE_LOADER_NAME)
                              .getMethod("load", Class.class, ClassLoader.class)
                              .invoke(null, elementType, loader);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return iterable;
  }

  public Collection<String> installAspects(final ClassLoader classLoader) {
    Collection<String> aspects = this.aspects;
    if(aspects == null) {
      this.aspects = aspects = locateAspects(classLoader);
    }
    return aspects;
  }

  private boolean findAspect(String filename,
                             Supplier<InputStream> supplier) {
    try {
      ClassReader reader = new ClassReader(supplier.get());
      AspectClassVisitor classVisitor = new AspectClassVisitor();
      reader.accept(classVisitor, ClassReader.SKIP_CODE);
      return classVisitor.isAspect();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to introspect: " + filename, e);
    }
  }

  private Collection<String> locateAspects(final ClassLoader classLoader) {
    ResourceScanner scanner = ResourceScanner.newScanner(
        ResourceScanner.Parallelism.CONCURRENT,
        ResourceSourceLocator.with(ResourceSourceLocator.usingClassLoader()),
        ResourceResolver.with(ResourceResolver.newFileResourceResolver(), ResourceResolver.newJarResourceResolver()),
        (name, stream) ->
            filter(classLoader, name) &&
            findAspect(name, stream),
        ResourceTypeSelector.with(ResourceType.CLASS_TYPE)
    );

    List<String> aspectClasses = Collections.emptyList();

    try {
      Map<String, Resource> result = scanner.scan(classLoader);
      if (!result.isEmpty()) {
        aspectClasses = new ArrayList<>();
      }

      for (Resource resource : result.values()) {
        if (resource.getResourceType() == ResourceType.CLASS_TYPE) {
          String filename = resource.getName();
          String classname = filename.substring(0, filename.length() - ".class".length());
          classname = classname.replace('/', '.');
          aspectClasses.add(classname);
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException("Unable to locate defined aspects.", e);
    }
    return aspectClasses;
  }
}
