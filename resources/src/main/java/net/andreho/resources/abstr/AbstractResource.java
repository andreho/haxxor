package net.andreho.resources.abstr;

import net.andreho.resources.Resource;
import net.andreho.resources.ResourceType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by a.hofmann on 09.05.2016.
 */
public abstract class AbstractResource<T>
    implements Resource {

  private final URL source;
  private final String name;
  private final ResourceType resourceType;

  protected volatile Object attachment;
  protected volatile Optional<Resource> next;
  protected volatile Reference<byte[]> cachedReference;

  public AbstractResource(final URL source,
                          final String name,
                          final ResourceType resourceType) {
    this.source = Objects.requireNonNull(source, "source");
    this.name = Objects.requireNonNull(name, "name");
    this.resourceType = Objects.requireNonNull(resourceType, "resourceType");
  }

  @Override
  public Optional<Resource> getNext() {
    return this.next;
  }

  @Override
  public void setNext(final Resource resource) {
    this.next = Optional.ofNullable(resource);
  }

  @Override
  public boolean hasNext() {
    return this.next != null;
  }

  @Override
  public boolean cache()
  throws IOException {
    return false;
  }

  @Override
  public URL getSource() {
    return this.source;
  }

  @Override
  public <V> Optional<V> getAttachment() {
    return Optional.ofNullable((V) attachment);
  }

  @Override
  public void setAttachment(final Object attachment) {
    this.attachment = attachment;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Resource evict()
  throws IOException {
    this.cachedReference = null;
    return this;
  }

  @Override
  public boolean isCached() {
    Reference<byte[]> cachedReference = this.cachedReference;
    return cachedReference != null && cachedReference.get() != null;
  }

  @Override
  public ResourceType getResourceType() {
    return this.resourceType;
  }

  @Override
  public Optional<ReadableByteChannel> getReadableByteChannel()
  throws IOException {
    return openReadableByteChannel();
  }

  @Override
  public InputStream getInputStream()
  throws IOException {
    return openInputStream();
  }

  @Override
  public long length() {
    return -1;
  }

  @Override
  public boolean hasLength() {
    return false;
  }

  @Override
  public Iterator<Resource> iterator() {
    final Resource resource = this;
    return new Iterator<Resource>() {
      Optional<Resource> current = Optional.of(resource);

      @Override
      public boolean hasNext() {
        return this.current.isPresent();
      }

      @Override
      public Resource next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        Resource current = this.current.get();
        this.current = current.getNext();
        return current;
      }
    };
  }

  /**
   * Used to open an instance of {@link ReadableByteChannel}
   *
   * @return an instance (not null)
   * @throws IOException
   */
  protected Optional<ReadableByteChannel> openReadableByteChannel()
  throws IOException {
    return Optional.empty();
  }

  /**
   * Opens the underlying stream to the referenced resource
   *
   * @return an opened stream that <u>must</u> be closed by user again.
   * @throws IOException
   */
  protected abstract InputStream openInputStream()
  throws IOException;

  @Override
  public String toString() {
    return getName();
  }
}
