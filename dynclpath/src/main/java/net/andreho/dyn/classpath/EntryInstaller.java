package net.andreho.dyn.classpath;

/**
 * @param <C>
 */
@FunctionalInterface
public interface EntryInstaller<C extends ClassLoader> {

  /**
   * @param classLoader
   * @return
   */
  default boolean isCompatibleWith(ClassLoader classLoader) {
    return true;
  }

  /**
   * @param classLoader to use for installation
   * @param entry       to install
   * @return value signalising status of this installation attempt.
   */
  InstallationResult installOn(C classLoader, Entry entry);
}
