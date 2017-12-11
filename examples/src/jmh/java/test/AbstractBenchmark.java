package test;

import net.andreho.resources.Resource;
import net.andreho.resources.ResourceResolver;
import net.andreho.resources.ResourceScanner;
import net.andreho.resources.ResourceSourceLocator;
import net.andreho.resources.ResourceType;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 23.11.2017 at 17:20.
 */
public abstract class AbstractBenchmark {
  private static volatile Blackhole blackhole;
  private static final Map<String, Resource> RESOURCES;
  static {
    RESOURCES = new LinkedHashMap<>();
  }
  protected static void loadResources() {
    final ResourceScanner scanner = ResourceScanner.newScanner(
      ResourceScanner.Parallelism.SINGLE,
      ResourceSourceLocator.usingClassPath(),
      ResourceResolver.newJarResourceResolver(),
      (resourceName, streamSupplier) ->
        resourceName.startsWith("org/hibernate/") && resourceName.endsWith(".class"),
      ResourceType.CLASS_TYPE
    );
    try {
      RESOURCES.putAll(scanner.scan(AbstractBenchmark.class.getClassLoader()));
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }

    for(Resource resource : RESOURCES.values()) {
      try {
        resource.cache();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }

    System.out.println("Size: "+getResources().size());
  }

  protected static void done(Blackhole blackhole) {
    blackhole.consume("done");
    if((blackhole.b1+
        blackhole.b2+
        blackhole.c1+
        blackhole.c2+
        blackhole.s1+
        blackhole.s2+
        blackhole.i1+
        blackhole.i2+
        blackhole.f1+
        blackhole.f2+
        blackhole.l1+
        blackhole.l2+
        blackhole.d1+
        blackhole.d2+
        blackhole.tlr) == Long.MIN_VALUE) {
      System.out.println("ok");
    }
  }

  protected static Blackhole createBlackhole() {
    if(blackhole == null) {
      return blackhole =
        new Blackhole(
          "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous."
        );
    }
    return blackhole;
  }

  protected static long getTotalMemory() {
    return
      ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getCommitted() +
      ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted();
  }

  protected static long getCurrentlyUsedMemory() {
    return
      ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() +
      ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed();
  }

  static Map<String, Resource> getResources() {
    return RESOURCES;
  }

  protected static Collection<Resource> getResourceCollection() {
    return RESOURCES.values();
  }

  protected static Resource getResource(String resource) {
    return getResources().get(resource);
  }

  public abstract void read(Blackhole bh) throws Exception;

  public abstract void write(Blackhole bh) throws Exception;
}
