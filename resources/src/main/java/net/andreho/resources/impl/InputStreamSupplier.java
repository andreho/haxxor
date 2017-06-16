package net.andreho.resources.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 03:20.
 */
public final class InputStreamSupplier
    implements Supplier<InputStream>,
               Closeable {

  private final Callable<InputStream> inputStreamSupplier;
  private volatile boolean closed;
  private volatile InputStream inputStream;

  public InputStreamSupplier(final Callable<InputStream> inputStreamSupplier) {
    this.inputStreamSupplier = inputStreamSupplier;
  }

  @Override
  public void close()
  throws IOException {
    if (!closed) {
      closed = true;
      if (inputStream != null) {
        inputStream.close();
        inputStream = null;
      }
    }
  }

  @Override
  public InputStream get() {
    if (closed) {
      throw new IllegalStateException("Stream was already closed.");
    }
    try {
      return this.inputStream = inputStreamSupplier.call();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}