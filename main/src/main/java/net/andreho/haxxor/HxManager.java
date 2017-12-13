package net.andreho.haxxor;

import net.andreho.haxxor.spi.HxClassResolver;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCacheAware;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxInitializationAware;
import net.andreho.haxxor.spi.HxTypeDeserializer;
import net.andreho.haxxor.spi.HxTypeSerializer;
import net.andreho.haxxor.spi.HxVerificationAware;

/**
 * <br/>Created by a.hofmann on 01.12.2017 at 18:05.
 */
public interface HxManager extends HxInitializationAware,
                                   HxVerificationAware,
                                   HxDeduplicationCacheAware {

  /**
   * @return the associated classname normalizer instance
   */
  HxClassnameNormalizer getClassnameNormalizer();
  /**
   * @return the associated element factory
   */
  HxElementFactory getElementFactory();
  /**
   * @return the associated element serializer
   */
  HxTypeSerializer getTypeSerializer();
  /**
   * @return the associated type deserializer
   */
  HxTypeDeserializer getTypeDeserializer();
  /**
   * @return the associated class resolved
   */
  HxClassResolver getClassResolver();
}
