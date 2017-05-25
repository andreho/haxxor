package net.andreho.aop.internal;

import java.io.File;
import java.net.URL;

/**
 * <br/>Created by a.hofmann on 26.09.2015.<br/>
 */
@FunctionalInterface
public interface ScannerVisitor {
   /**
    * Used to visit a resource that is available through the given class loader
    *
    * @param scanner that used this visitor
    * @param rootUrl is URL of the main entry point (root folder with classes or a lib folder containing jar files)
    * @param name    is the name of corresponding resource (of a class, properties file, etc.)
    * @param value   corresponding file source (typically either a direct file or a file representing a jar-file)
    * @param isJar
    */
   void visit(Scanner scanner, URL rootUrl, String name, File value, boolean isJar);
}
