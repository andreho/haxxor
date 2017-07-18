package net.andreho.dyn.classpath;

import java.net.URL;
import java.util.Collection;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 11:22.
 */
public interface Manager {

  /**
   * @return
   */
  Collection<EntryInstaller<?>> getAvailableEntryInstallers();

  /**
   * @return
   */
  Collection<EntryFactory> getAvailableEntryFactories();

  /**
   * @param id
   * @param classLoader
   * @param classPathUrl
   * @return
   */
  InstallationResult install(String id, ClassLoader classLoader, URL classPathUrl);

  /**
   * @return
   */
  Iterable<Entry> getEntries();

  /**
   * @param id
   * @return
   */
  Optional<Entry> getEntryById(String id);
}
