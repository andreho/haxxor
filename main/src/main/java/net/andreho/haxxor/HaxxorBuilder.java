package net.andreho.haxxor;

import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxTypeInitializer;
import net.andreho.haxxor.spi.HxTypeInterpreter;
import net.andreho.haxxor.spi.impl.CachedHxByteCodeLoader;
import net.andreho.haxxor.spi.impl.DefaultHxClassnameNormalizer;
import net.andreho.haxxor.spi.impl.DefaultHxElementFactory;
import net.andreho.haxxor.spi.impl.DefaultHxTypeInitializer;
import net.andreho.haxxor.spi.impl.DefaultHxTypeInterpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class HaxxorBuilder {
  private final ClassLoader classLoader;

  public HaxxorBuilder() {
    this(HaxxorBuilder.class.getClassLoader());
  }

  public HaxxorBuilder(final ClassLoader classLoader) {
    this.classLoader = Objects.requireNonNull(classLoader);
  }

  /**
   * Creates new instance of builder with given classloader
   * @param classLoader to use
   * @return new builder instance
   */
  public static HaxxorBuilder with(ClassLoader classLoader) {
    return new HaxxorBuilder(classLoader);
  }

  /**
   * @param haxxor instance that will use provided classloader
   * @return the classloader to use for byte-code loading
   */
  public ClassLoader provideClassLoader(Haxxor haxxor) {
    return classLoader;
  }

  /**
   * Creates a new type-initializer instance that must be involved into creation of new types via
   * {@link HxElementFactory#createType(String)}
   * @return new type-initializer instance associated with given haxxor instance
   */
  public HxTypeInitializer createTypeInitializer(Haxxor haxxor) {
    return new DefaultHxTypeInitializer();
  }

  /**
   * Creates element factory instance that must be used with given haxxor instance
   *
   * @param haxxor instance that receives created element factory
   * @return new element factory associated with given haxxor instance
   */
  public HxElementFactory createElementFactory(Haxxor haxxor) {
    return new DefaultHxElementFactory(haxxor);
  }

  /**
   * @param haxxor instance
   * @return new type interpreter instance
   */
  public HxTypeInterpreter createTypeInterpreter(Haxxor haxxor) {
    return new DefaultHxTypeInterpreter();
  }

  /**
   * Creates byte-code loader instance that must be used with given haxxor instance
   *
   * @param haxxor instance that receives created content loader
   * @return new byte-code loader associated with given haxxor instance
   */
  public HxByteCodeLoader createByteCodeLoader(Haxxor haxxor) {
    return new CachedHxByteCodeLoader(haxxor);
  }

  /**
   * Creates a map for type references
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxTypeReference> createReferenceCache(Haxxor haxxor) {
    return new HashMap<>();
  }

  /**
   * Creates a map for resolved types
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxType> createResolvedCache(Haxxor haxxor) {
    return new HashMap<>();
  }

  /**
   * Creates a new provider of default Java type-classnames
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxClassnameNormalizer createClassNameNormalizer(Haxxor haxxor) {
    return new DefaultHxClassnameNormalizer();
  }
}
