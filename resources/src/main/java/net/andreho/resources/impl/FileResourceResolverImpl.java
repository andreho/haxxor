package net.andreho.resources.impl;

import net.andreho.resources.Resource;
import net.andreho.resources.ResourceFilter;
import net.andreho.resources.ResourceType;
import net.andreho.resources.ResourceTypeSelector;
import net.andreho.resources.abstr.AbstractResourceResolver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.andreho.resources.impl.Utils.mergeResults;

/**
 * Created by a.hofmann on 13.05.2016.
 */
public class FileResourceResolverImpl
    extends AbstractResourceResolver {

  private static final String FILE_PROTOCOL_NAME = "file";

  private class PathFileVisitor
      implements FileVisitor<Path> {

    private final URL url;
    private final Path target;
    private final ResourceFilter resourceFilter;
    private final ResourceTypeSelector selector;
    private final Map<String, Resource> result;
    private final StringBuilder stack;

    public PathFileVisitor(final URL url,
                           final Path target,
                           final ResourceFilter resourceFilter,
                           final ResourceTypeSelector selector,
                           final Map<String, Resource> result) {
      this.target = target;
      this.resourceFilter = resourceFilter;
      this.result = result;
      this.url = url;
      this.selector = selector;
      this.stack = new StringBuilder(128);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir,
                                             BasicFileAttributes attrs)
    throws IOException {
      if (!target.equals(dir)) {
        stack.append(dir.getFileName().toString()).append('/');
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir,
                                              IOException exc)
    throws IOException {
      if (!target.equals(dir)) {
        stack.setLength(stack.length() - (dir.getFileName().toString().length() + 1));
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs)
    throws IOException {
      final String fileName = file.getFileName().toString();
      final StringBuilder stack = this.stack;

      try {
        final String resourceName = stack.append(fileName).toString();
        if (filterFile(file, resourceName, resourceFilter)) {
          result.put(resourceName, createResource(url, resourceName, file.toFile(), selector));
        }
      } finally {
        stack.setLength(stack.length() - fileName.length());
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc)
    throws IOException {
      throw exc;
    }
  }

  @Override
  public Optional<Map<String, Resource>> resolve(final URL url,
                                                 final ResourceFilter resourceFilter,
                                                 final ResourceTypeSelector typeSelector) {
    if (FILE_PROTOCOL_NAME.equalsIgnoreCase(url.getProtocol())) {
      try {
        final File directory = new File(url.toURI());
        return scan(url, directory, resourceFilter, typeSelector);
      } catch (URISyntaxException e) {
        throw new IllegalStateException("Unable to resolve given URL: " + url, e);
      } catch (IOException e) {
        throw new IllegalStateException("Unable to process directory, that is referenced by: " + url, e);
      }
    }

    return Optional.empty();
  }

  protected Optional<Map<String, Resource>> scan(final URL url,
                                                 final File file,
                                                 final ResourceFilter resourceFilter,
                                                 final ResourceTypeSelector typeSelector)
  throws IOException {
    if (!file.exists()) {
      return Optional.empty();
    }

    final Path targetPath = file.toPath();
    final Map<String, Resource> result = new HashMap<>();

    Files.walkFileTree(targetPath,
                       new PathFileVisitor(url, targetPath, resourceFilter, typeSelector, result)
    );

    if(isSubJar(url, file.getName())) {
      mergeResults(result, scanSubJar(url, file, resourceFilter, typeSelector));
    }

    return Optional.of(result);
  }

  protected boolean filterFile(final Path file,
                               final String resourceName,
                               final ResourceFilter resourceFilter) {

    try (final InputStreamSupplier streamSupplier =
             new InputStreamSupplier(() -> Files.newInputStream(file, StandardOpenOption.READ))) {
      return resourceFilter.filter(resourceName, streamSupplier);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }


  protected Resource createResource(final URL url,
                                    final String resourceName,
                                    final File target,
                                    final ResourceTypeSelector typeSelector) {
    final ResourceType resourceType = typeSelector.select(resourceName);
    return new FileResourceImpl(url, resourceName, resourceType, target);
  }
}