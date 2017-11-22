package net.andreho.haxxor;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeReference;
import net.andreho.haxxor.spi.HxClassLoaderHolder;
import net.andreho.haxxor.spi.HxClassLoadingHandler;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCacheAware;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxInitializationAware;
import net.andreho.haxxor.spi.HxTypeSerializer;
import net.andreho.haxxor.spi.HxVerificationAware;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 22:31.
 */
public interface Hx extends HxClassnameNormalizer,
                            HxElementFactory,
                            HxClassLoaderHolder,
                            HxInitializationAware,
                            HxTypeSerializer,
                            HxVerificationAware,
                            HxDeduplicationCacheAware,
                            HxClassLoadingHandler {

  static HaxxorBuilder builder() {
    return HaxxorBuilder.newBuilder();
  }

  /**
   * @param classname
   * @return
   */
  boolean hasReference(String classname);

  /**
   * @param classname
   * @return
   */
  HxTypeReference reference(String classname);

  /**
   * @param aClass
   * @return
   */
  HxTypeReference reference(Class<?> aClass);

  /**
   * @param classnames
   * @return
   */
  List<HxType> referencesAsList(String... classnames);

  /**
   * @param classnames
   * @return
   */
  HxType[] referencesAsArray(String... classnames);

  /**
   * @param classname
   * @return
   */
  boolean hasResolved(String classname);

  /**
   * @param classname
   * @return
   */
  HxType resolve(String classname);

  /**
   * @param aClass
   * @return
   */
  HxType resolve(Class<?> aClass);

  /**
   * @param classname
   * @param flags
   * @return
   */
  HxType resolve(String classname,
                 int flags);

  /**
   * @param classname
   * @param byteCode
   * @param flags
   * @return
   */
  HxType resolve(String classname,
                 byte[] byteCode,
                 int flags);

  /**
   * @param classname
   * @param typeOrReference
   * @return
   */
  HxType register(String classname,
                  HxType typeOrReference);
}
