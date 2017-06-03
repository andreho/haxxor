package net.andreho.haxxor;

import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxInternalClassNameProvider;
import net.andreho.haxxor.spi.HxJavaClassNameProvider;
import net.andreho.haxxor.spi.HxTypeInitializer;
import net.andreho.haxxor.spi.impl.DefaultHxByteCodeLoader;
import net.andreho.haxxor.spi.impl.DefaultHxElementFactory;
import net.andreho.haxxor.spi.impl.DefaultHxInternalClassNameProvider;
import net.andreho.haxxor.spi.impl.DefaultHxJavaClassNameProvider;
import net.andreho.haxxor.spi.impl.DefaultHxTypeInitializer;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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
   * @param haxxor instance that will use provided classloader
   * @return the classloader to use for byte-code loading and resource location
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
   * Creates byte-code loader instance that must be used with given haxxor instance
   *
   * @param haxxor instance that receives created content loader
   * @return new byte-code loader associated with given haxxor instance
   */
  public HxByteCodeLoader createByteCodeLoader(Haxxor haxxor) {
    return new DefaultHxByteCodeLoader(haxxor);
  }

  /**
   * Creates a map for type references
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxTypeReference> createReferenceCache(Haxxor haxxor) {
    return new TreeMap<>();
  }

  /**
   * Creates a map for resolved types
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxType> createResolvedCache(Haxxor haxxor) {
    return new TreeMap<>();
  }

  /**
   * Creates a new provider of internal classnames
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxInternalClassNameProvider createInternalClassNameProvider(Haxxor haxxor) {
    return new DefaultHxInternalClassNameProvider();
  }

  /**
   * Creates a new provider of default Java type-classnames
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxJavaClassNameProvider createJavaClassNameProvider(Haxxor haxxor) {
    return new DefaultHxJavaClassNameProvider();
  }
}
