package net.andreho.aop.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <br/>Created by a.hofmann on 26.09.2015.<br/>
 */
public class Scanner {
   /**
    * For some reason an URL that reference a JAR file
    * (in some cases (tomcat setup): ...aop.jar!/)
    * had some special format with an exclamation mark at the end.
    * This leads to an invalid file resolution and the target jar being unprocessed.
    */
   private static final Pattern JAR_PATTERN =
         Pattern.compile("(?:(?:jar:)?file:?[/\\\\])?(.*\\.jar)(?:[!/]*)?", Pattern.CASE_INSENSITIVE);

   private final String defaultEncoding;
   private final ClassLoader classLoader;
   private final boolean visitJar;

   public Scanner(ClassLoader classLoader) {
      this(classLoader, false);
   }

   public Scanner(ClassLoader classLoader, boolean visitJar) {
      this(classLoader, visitJar, System.getProperty("file.encoding", "ISO-8859-1"));
   }

   public Scanner(ClassLoader classLoader, boolean visitJar, String fileEncoding) {
      if (classLoader == null) {
         throw new NullPointerException("ClassLoader is null.");
      }

      this.visitJar = visitJar;
      this.classLoader = classLoader;
      this.defaultEncoding = fileEncoding;
   }

   /**
    * Helper method to extract right file name from given absolute path
    *
    * @param rootUrl
    * @param name
    * @return
    */
   public static String extractFilename(URL rootUrl, String name) {
      final String rootUrlFile = rootUrl.toString();
      return name.substring(rootUrlFile.length() - (rootUrl.getProtocol().length() + ":/".length())).replace('\\', '/');
   }

   //##################################################################################################

   public boolean mayVisitJar() {
      return visitJar;
   }

   /**
    * @return classloader to be used as source
    */
   public ClassLoader getClassLoader() {
      return classLoader;
   }

   /**
    * Scans all available classes and jars using given visitor instance.
    *
    * @param visitor
    * @throws IOException
    */
   public void scan(final ScannerVisitor visitor) throws IOException {
      final Enumeration<URL> resources = classLoader.getResources("");

      ClassLoader current = classLoader;

      final Set<URL> visitedUrls = new HashSet<>();

      boolean servletContainerPresent = false;

      while (current != null) {
         while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            if (url != null &&
                visitedUrls.add(url)) {
               if (!servletContainerPresent &&
                   "file".equalsIgnoreCase(url.getProtocol()) &&
                   (url.toString().endsWith("/WEB-INF/classes/") ||
                    url.toString().endsWith("/WEB-INF/lib/"))) {
                  servletContainerPresent = true;
               }

               scanUrl(url, visitor);
            }
         }

         if (servletContainerPresent) {
            break;
         }

         current = current.getParent();
      }

      if (!servletContainerPresent) {
         final String[] classpath = System.getProperty("java.class.path").split(File.pathSeparator);

         for (String path : classpath) {
            final URL url = new File(path).toURI().toURL();

            if (visitedUrls.add(url)) {
               scanUrl(url, visitor);
            }
         }
      }
   }

   //##################################################################################################

   protected void scanUrl(final URL url, final ScannerVisitor visitor) throws IOException {
      final String protocol = url.getProtocol();
      String path = URLDecoder.decode(url.getPath(), defaultEncoding);

      if ("file".equalsIgnoreCase(protocol) ||
          "jar".equalsIgnoreCase(protocol)) {
         Matcher matcher;

         if ("jar".equalsIgnoreCase(protocol) &&
             (matcher = JAR_PATTERN.matcher(path)).matches()) {
            path = matcher.group(1);
         }

         final File target = new File(path);
         scanFile(url, target, visitor);
      } else {
         System.err.println("Unknown protocol: " + protocol);
      }
   }

   //##################################################################################################

   protected void scanFile(final URL rootUrl, final File file, final ScannerVisitor visitor) throws IOException {
      if (file.exists()) {
         final boolean isJar =
            file.isFile() && (file.getName().endsWith(".jar") || file.getName().endsWith(".JAR"));
         if (visitJar && isJar) {
            visitJar(rootUrl, file, visitor);
         } else if (!file.isDirectory()) {
            visitFile(rootUrl, file, visitor, isJar);
         } else {
            for (File child : file.listFiles()) {
               scanFile(rootUrl, child, visitor);
            }
         }
      }
   }

   protected void visitFile(final URL rootUrl, final File file, final ScannerVisitor visitor, boolean isJar)
         throws IOException {
      visitor.visit(this, rootUrl, file.getPath(), file, isJar);
   }

   protected void visitJar(final URL rootUrl, final File file, final ScannerVisitor visitor) throws IOException {
      final JarFile jarFile = new JarFile(file);

      try {
         final Enumeration<? extends JarEntry> entries = jarFile.entries();

         while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();

            if (!entry.isDirectory()) {
               visitor.visit(this, rootUrl, entry.getName(), file, true);
            }
         }
      } finally {
         jarFile.close();
      }
   }
}
