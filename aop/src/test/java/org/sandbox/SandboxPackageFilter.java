package org.sandbox;

import net.andreho.aop.spi.PackageFilter;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 20:43.
 */
public class SandboxPackageFilter
    implements PackageFilter {

  @Override
  public boolean filter(final String fullyQualifiedName) {
    return fullyQualifiedName.startsWith("org/sandbox/");
  }
}
