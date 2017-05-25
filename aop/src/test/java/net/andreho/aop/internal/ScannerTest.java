package net.andreho.aop.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <br/>Created by a.hofmann on 26.09.2015.<br/>
 */
@DisplayName("Functional test of Scanner")
class ScannerTest {

   @Test
   @DisplayName("Scanner must find all *.class files")
   void testScan() throws Exception {
      final Scanner classLoaderScanner = new Scanner(this.getClass().getClassLoader(), true);
      final Set<String> set = new LinkedHashSet<>();

      classLoaderScanner.scan((scanner, rootUrl, name, value, isJar) -> {
         if (!isJar) {
            final String rootUrlFile = rootUrl.toString();
            //int offset = rootUrlFile.endsWith("/classes")? "/classes".length() + 1 : rootUrlFile.endsWith
            // ("/lib")? "/lib".length() + 1 : 0;
            //if(rootUrlFile.endsWith("/WEB-INF/classes"))

            final String substring = name.substring(
                  rootUrlFile.length() - (rootUrl.getProtocol().length() + ":/".length())).replace('\\', '/');
            set.add(substring);
         } else {
            set.add(name);
         }
      });

      set.contains(ScannerTest.class.getName().replace('.', '/'));
   }
}