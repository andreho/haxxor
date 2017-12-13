package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 26.10.2017 at 14:32.
 */
public interface HxVerificationAware {
  /**
   * @param type to verify
   * @return the result of type's verification
   */
  HxVerificationResult verify(HxType type);
  /**
   * @param field to verify
   * @return the result of field's verification
   */
  HxVerificationResult verify(HxField field);
  /**
   * @param method to verify
   * @return the result of method's verification
   */
  HxVerificationResult verify(HxMethod method);
}
