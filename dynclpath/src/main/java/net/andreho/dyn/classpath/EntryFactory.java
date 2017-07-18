package net.andreho.dyn.classpath;

import java.net.URL;

/**
 *
 */
@FunctionalInterface
public interface EntryFactory {

  /**
   * Creates an entry according to given parameters
   *
   * @param id           of new entry
   * @param classLoader  that should be associated with the new entry
   * @param classPathUrl that should be associated with the new entry
   * @return a new entry or <b>null</b> if not supported
   */
  Entry createFor(String id,
                  ClassLoader classLoader,
                  URL classPathUrl);
}
