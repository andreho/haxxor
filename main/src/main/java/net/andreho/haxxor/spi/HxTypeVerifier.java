package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 04.06.2017 at 00:44.
 */
@FunctionalInterface
public interface HxTypeVerifier {

  /**
   * @param type to verify
   * @return a result
   * @see HxVerificationResult
   */
  HxVerificationResult verify(HxType type);
}
