package net.andreho.dyn.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 11:01.
 */
public interface Entry {

  /**
   * @return id of the associated classpath
   */
  String getId();

  /**
   * @return the associated classloaders (stored as WeakHashMap internally)
   */
  Set<ClassLoader> getClassLoaders();

  /**
   * @return
   */
  URL getClassPathUrl();

  /**
   * @param other
   * @return
   */
  Entry merge(Entry other);

  /**
   * @param inputStream is the content of the element to add
   * @param first is either the name or first segment in the path of the element to add
   * @param rest if present, is the rest path, where last element must be the name of element to add
   * @return <b>true</b> if successful, <b>false</b> otherwise.
   */
  boolean add(InputStream inputStream,
              String first,
              String... rest)
  throws IOException;

  /**
   * @param bytes is the byte content of the element to add
   * @param first is either the name or first segment in the path of the element to add
   * @param rest if present, is the rest path, where last element must be the name of element to add
   * @return <b>true</b> if successful, <b>false</b> otherwise.
   */
  boolean add(byte[] bytes,
              String first,
              String... rest)
  throws IOException;

  /**
   * @return
   */
  Stream<String> stream()
  throws IOException;
}
