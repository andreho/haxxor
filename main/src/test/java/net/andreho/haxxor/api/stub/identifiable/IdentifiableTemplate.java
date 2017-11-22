package net.andreho.haxxor.api.stub.identifiable;

import net.andreho.haxxor.api.stub.Stub;

import java.util.concurrent.atomic.AtomicLong;

interface Identifiable {
  long getId();
}
public class IdentifiableTemplate implements Identifiable {
  private static final AtomicLong COUNTER = new AtomicLong();
  private final long id;

  public IdentifiableTemplate() {
    this.id = COUNTER.getAndIncrement();
  }
  public IdentifiableTemplate(@Stub.Named long id) {
    this.id = id;
  }
  @Override
  public long getId() {
    return id;
  }
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Identifiable)) {
      return false;
    }
    final Identifiable that = (Identifiable) o;
    return id == that.getId();
  }
  @Override
  public int hashCode() {
    return Long.hashCode(id);
  }
}
