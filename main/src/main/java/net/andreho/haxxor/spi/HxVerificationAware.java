package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 26.10.2017 at 14:32.
 */
public interface HxVerificationAware {
  HxVerificationResult verify(HxType type);

  HxVerificationResult verify(HxField field);

  HxVerificationResult verify(HxMethod method);
}
