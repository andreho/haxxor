package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 04.06.2017 at 00:44.
 */
@FunctionalInterface
public interface HxTypeVerifier extends HxVerificator<HxType> {
  /**
   * @param type to verify
   * @return either {@link HxVerificationResult#ok()} or a chain of discovered verification problems
   */
  HxVerificationResult verify(HxType type);
}
