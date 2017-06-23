package net.andreho.haxxor.spec.api;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 15:43.
 */
public final class HxSourceInfo {
  private final String source;
  private final String debug;

  public HxSourceInfo(final String source,
                      final String debug) {
    this.source = source;
    this.debug = debug;
  }

  public String getSource() {
    return source;
  }

  public String getDebug() {
    return debug;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final HxSourceInfo other = (HxSourceInfo) o;

    return Objects.equals(source, other.source);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(source);
  }

  @Override
  public String toString() {
    return String.valueOf(source) + (debug != null? ": " + debug : "");
  }
}
