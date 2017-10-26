package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 04.06.2017 at 00:44.
 */
@FunctionalInterface
public interface HxMethodVerifier extends HxVerificator<HxMethod> {
  /**
   * @param method to verify
   * @return either {@link HxVerificationResult#ok()} or a chain of discovered verification problems
   */
  HxVerificationResult verify(HxMethod method);
}
