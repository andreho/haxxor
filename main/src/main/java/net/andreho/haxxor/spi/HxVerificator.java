package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxMember;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 16:28.
 */
public interface HxVerificator<E extends HxMember<E>> {
  /**
   * @param element to check
   * @return result of this verification
   * @see HxVerificationResult
   */
  HxVerificationResult verify(E element);
}
