package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxField;

/**
 * <br/>Created by a.hofmann on 04.06.2017 at 00:44.
 */
@FunctionalInterface
public interface HxFieldVerifier extends HxVerificator<HxField> {
  /**
   * @param field to verify
   * @return either {@link HxVerificationResult#ok()} or a chain of discovered verification problems
   */
  HxVerificationResult verify(HxField field);
}
