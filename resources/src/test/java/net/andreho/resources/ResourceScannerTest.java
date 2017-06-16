package net.andreho.resources;

import net.andreho.resources.impl.ClassLoaderResourceLocatorImpl;
import net.andreho.resources.impl.ClassPathResourceLocatorImpl;
import net.andreho.resources.impl.FileResourceResolverImpl;
import net.andreho.resources.impl.JarResourceResolverImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Created by a.hofmann on 20.05.2016.
 */
public class ResourceScannerTest {
   @Test
   public void resources_will_be_located() throws Exception {
      ResourceScanner resourceScanner = createResourceScanner();
      Map<String, Resource> result = resourceScanner.scan(Thread.currentThread().getContextClassLoader());
      System.out.println("Count: " + result.size());
      assertTrue(result.size() > 0);
   }

   @Test
   public void each_resource_has_fetchable_content() throws Exception {
      ResourceScanner resourceScanner = createResourceScanner();
      Map<String, Resource> result = resourceScanner.scan(Thread.currentThread().getContextClassLoader());
      long used = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();

      result.forEach((k, v) -> {
         assertTrue(v.length() > 0);
         try {
            v.cache();
         } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
         }
      });

      System.out.printf("Used memory: %15.2f Kb\n",
                        ((ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() - used) / 1024d));
   }

   private ResourceScanner createResourceScanner() {
      return ResourceScanner.newScanner(
          ResourceSourceLocator.with(
              ClassLoaderResourceLocatorImpl.INSTANCE,
              ClassPathResourceLocatorImpl.INSTANCE
          ),
          ResourceResolver.with(new FileResourceResolverImpl(), new JarResourceResolverImpl()),
          (name, supplier) -> name.startsWith("java/"),
          ResourceType.CLASS_TYPE,
          ResourceType.XML_TYPE,
          ResourceType.PROPERTIES_TYPE,
          ResourceType.SERVICE_DECLARATION_TYPE);
   }
}