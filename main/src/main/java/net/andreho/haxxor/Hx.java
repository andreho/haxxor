package net.andreho.haxxor;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxClassLoaderHolder;
import net.andreho.haxxor.spi.HxClassResolver;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCacheAware;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxInitializationAware;
import net.andreho.haxxor.spi.HxProvidable;
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
                            HxClassResolver {

  /**
   * @return
   */
  static Hx create() {
    return builder().build();
  }

  /**
   * @return
   */
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
  HxType reference(String classname);

  /**
   * @param aClass
   * @return
   */
  HxType reference(Class<?> aClass);

  /**
   * @param classnames
   * @return
   */
  HxType[] references(String... classnames);

  /**
   * @param classes
   * @return
   */
  HxType[] references(Class<?>... classes);

  /**
   * @param classnames
   * @return
   */
  List<HxType> referencesAsList(String... classnames);

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
   * @param cls
   * @return
   */
  HxType resolve(Class<?> cls);

  /**
   * @param classname
   * @param flags
   * @return
   */
  HxType resolve(String classname, int flags);

  /**
   * @param classname
   * @param byteCode
   * @return
   */
  default HxType resolve(String classname, byte[] byteCode) {
    return resolve(classname, byteCode, 0);
  }

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

  /**
   * @param hxType
   */
  void resolveFields(HxType hxType);

  /**
   * @param hxType
   */
  void resolveMethods(HxType hxType);

  /**
   * @param type
   */
  void resolveFieldsAndMethods(HxType type);

  /**
   * @param providable
   * @param <T>
   * @return
   */
  <T> T providePart(HxProvidable<T> providable);
}
