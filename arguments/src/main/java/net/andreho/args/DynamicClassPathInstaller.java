package net.andreho.args;

import net.andreho.dyn.classpath.DynamicClassPath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 04:31.
 */
final class DynamicClassPathInstaller {

  private static final String ID = "net_andreho_args";
  static final Path INSTALLATION_FOLDER = createTempDirectory();
  static final URL INSTALLATION_URL = toUrl(INSTALLATION_FOLDER);


  static void installDynamicClassPathForArguments() {
    installDynamicClassPathForArguments(ClassLoader.getSystemClassLoader());
  }

  static void installDynamicClassPathForArguments(final ClassLoader classLoader) {
    DynamicClassPath.defaultManager().install(ID, classLoader, INSTALLATION_URL);
  }

  private static Path createTempDirectory() {
    try {
      return Files.createTempDirectory(ID);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to create temporary directory for: 'net.andreho.args'", e);
    }
  }

  private static URL toUrl(Path folder) {
    try {
      return folder.toUri().toURL();
    } catch (MalformedURLException e) {
      throw new IllegalStateException("Unable to obtain URL from: " +folder, e);
    }
  }
}
