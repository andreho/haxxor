package net.andreho.resources.abstr;

import net.andreho.resources.ResourceType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.net.URL;

/**
 * Created by a.hofmann on 27.07.2016.
 */
public abstract class AbstractByteArrayResource
    extends AbstractResource<byte[]> {

  private static final int MAX_CACHEABLE_BYTES = Integer.MAX_VALUE - 16;
  private final Object lock = new Object();

  public AbstractByteArrayResource(final URL source,
                                   final String name,
                                   final ResourceType resourceType) {
    super(source, name, resourceType);
  }

  @Override
  public final InputStream getInputStream()
  throws IOException {
    if (isCached()) {
      Reference<byte[]> cachedReference = this.cachedReference;
      final byte[] content = cachedReference.get();
      if (content != null) {
        return new ByteArrayInputStream(content);
      }
    }
    return openInputStream();
  }

  protected byte[] readContentWithLength()
  throws IOException {
    if(length() >= MAX_CACHEABLE_BYTES) {
      throw new IllegalStateException("Not cacheable resource, because of length constraints: "+length());
    }

    final byte[] result = new byte[(int) length()];
    try (InputStream inputStream = getInputStream()) {
      int awaited = result.length;
      int count;
      while ((count = inputStream.read(result)) > -1) {
        awaited -= count;
        if (awaited <= 0) {
          break;
        }
      }
    }
    return result;
  }

  protected byte[] readContentWithOutLength()
  throws IOException {
    final byte[] result;
    try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      try (final InputStream inputStream = getInputStream()) {
        final byte[] buffer = new byte[2048];

        int count;
        while ((count = inputStream.read(buffer)) > -1) {
          outputStream.write(buffer, 0, count);
        }
      }
      result = outputStream.toByteArray();
    }
    return result;
  }

  @Override
  public boolean cache()
  throws IOException {
    if (!isCached()) {
      synchronized (this.lock) {
        if (isCached()) {
          return true;
        }
        byte[] result;

        if (hasLength()) {
          result = readContentWithLength();
        } else {
          result = readContentWithOutLength();
        }
        this.cachedReference = createCachedReference(result);
      }
      return true;
    }
    return false;
  }

  protected Reference<byte[]> createCachedReference(final byte[] result) {
    return new SoftReference<>(result);
  }
}
