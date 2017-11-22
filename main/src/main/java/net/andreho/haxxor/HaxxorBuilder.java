package net.andreho.haxxor;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeReference;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxClassLoadingHandler;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCache;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxFieldInitializer;
import net.andreho.haxxor.spi.HxFieldVerifier;
import net.andreho.haxxor.spi.HxMethodInitializer;
import net.andreho.haxxor.spi.HxMethodVerifier;
import net.andreho.haxxor.spi.HxStubHandler;
import net.andreho.haxxor.spi.HxTypeDeserializer;
import net.andreho.haxxor.spi.HxTypeInitializer;
import net.andreho.haxxor.spi.HxTypeSerializer;
import net.andreho.haxxor.spi.HxTypeVerifier;
import net.andreho.haxxor.spi.HxVerificationResult;
import net.andreho.haxxor.spi.impl.CachedHxByteCodeLoader;
import net.andreho.haxxor.spi.impl.CompoundHxStubHandler;
import net.andreho.haxxor.spi.impl.DefaultHxClassnameNormalizer;
import net.andreho.haxxor.spi.impl.DefaultHxElementFactory;
import net.andreho.haxxor.spi.impl.DefaultHxTypeDeserializer;
import net.andreho.haxxor.spi.impl.DefaultHxTypeInitializer;
import net.andreho.haxxor.spi.impl.DefaultHxTypeSerializer;
import net.andreho.haxxor.spi.impl.DefaultTrieBasedDeduplicationCache;
import net.andreho.haxxor.spi.impl.NoOpDeduplicationCache;
import net.andreho.haxxor.spi.impl.ReflectingClassLoadingHandler;
import net.andreho.haxxor.spi.impl.ServiceStubHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class HaxxorBuilder {

  private int flags;
  private boolean concurrent;
  private ClassLoader classLoader;

  /**
   * @return
   */
  static HaxxorBuilder newBuilder() {
    return new HaxxorBuilder();
  }

  protected HaxxorBuilder() {
    this(HaxxorBuilder.class.getClassLoader(), false);
  }

  protected HaxxorBuilder(final ClassLoader classLoader) {
    this(classLoader, false);
  }

  protected HaxxorBuilder(final ClassLoader classLoader,
                          final boolean concurrent) {
    this.classLoader = Objects.requireNonNull(classLoader);
    this.concurrent = concurrent;
  }

  /**
   * Informs builder to create a haxxor instance with given class-loader
   *
   * @param classLoader to use
   * @return this
   */
  public HaxxorBuilder withClassLoader(ClassLoader classLoader) {
    this.classLoader = Objects.requireNonNull(classLoader);
    return this;
  }

  /**
   * Informs builder to create a haxxor instance with given flags
   *
   * @param flags to use
   * @return this
   */
  public HaxxorBuilder withFlags(int flags) {
    this.flags = flags;
    return this;
  }

  /**
   * Informs builder to create a concurrent haxxor instance
   *
   * @return this
   */
  public HaxxorBuilder withConcurrency() {
    this.concurrent = true;
    return this;
  }

  /**
   * @return
   */
  boolean isConcurrent() {
    return concurrent;
  }

  /**
   * @param haxxor instance that will use provided classloader
   * @return the classloader to use for byte-code loading
   */
  public ClassLoader provideClassLoader(final Hx haxxor) {
    return classLoader;
  }

  /**
   * Creates a new deduplication cache for the given haxxor-instance
   *
   * @param haxxor instance that requires this component
   * @return new new deduplication cache associated with given haxxor instance
   */
  public HxDeduplicationCache createDeduplicationCache(final Hx haxxor) {
    if(concurrent) {
      return new NoOpDeduplicationCache();
    }
    return new DefaultTrieBasedDeduplicationCache();
  }

  /**
   * Creates a new type-initializer instance that must be involved into creation of new types via
   * {@link HxElementFactory#createType(String)}
   *
   * @param haxxor instance that requires this component
   * @return new type-initializer instance associated with given haxxor instance
   */
  public HxTypeInitializer createTypeInitializer(final Hx haxxor) {
    return new DefaultHxTypeInitializer();
  }

  /**
   * Creates a new field-initializer instance that must be involved into creation of new field via
   * {@link HxElementFactory#createField(HxType, String)}
   *
   * @param haxxor instance that requires this component
   * @return new field-initializer instance associated with given haxxor instance
   */
  public HxFieldInitializer createFieldInitializer(final Hx haxxor) {
    return (hxField) -> {
    };
  }

  /**
   * Creates a new method-initializer instance that must be involved into creation of new field via
   * {@link HxElementFactory#createMethod(HxType, String, HxType...)}
   *
   * @param haxxor instance that requires this component
   * @return new method-initializer instance associated with given haxxor instance
   */
  public HxMethodInitializer createMethodInitializer(final Hx haxxor) {
    return (hxMethod) -> {
    };
  }

  /**
   * Creates element factory instance that must be used with given haxxor instance
   *
   * @param haxxor is the requesting instance that receives created element factory
   * @return new element factory associated with given haxxor instance
   */
  public HxElementFactory createElementFactory(final Hx haxxor) {
    return new DefaultHxElementFactory(haxxor);
  }

  /**
   * @param haxxor is the requesting instance
   * @return new type deserializer instance
   */
  public HxTypeDeserializer createTypeDeserializer(final Hx haxxor) {
    return new DefaultHxTypeDeserializer(haxxor);
  }

  /**
   * @param haxxor is the requesting instance
   * @return new type serializer instance
   */
  public HxTypeSerializer createTypeSerializer(final Hx haxxor) {
    return new DefaultHxTypeSerializer(haxxor);
  }

  /**
   * Creates byte-code loader instance that must be used with given haxxor instance
   *
   * @param haxxor instance that receives created content loader
   * @return new byte-code loader associated with given haxxor instance
   */
  public HxByteCodeLoader createByteCodeLoader(final Hx haxxor) {
    return new CachedHxByteCodeLoader(haxxor);
  }

  /**
   * Creates a map for type references
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxTypeReference> createReferenceCache(final Hx haxxor) {
    return new HashMap<>();
  }

  /**
   * Creates a map for resolved types
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public Map<String, HxType> createResolvedCache(final Hx haxxor) {
    return new HashMap<>();
  }

  /**
   * Creates a new provider of default Java type-classnames
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxClassnameNormalizer createClassNameNormalizer(final Hx haxxor) {
    return new DefaultHxClassnameNormalizer();
  }

  /**
   * Creates a new type verifier for any type that is going to be serialized
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxTypeVerifier createTypeVerifier(final Hx haxxor) {
    return type -> HxVerificationResult.ok();
  }

  /**
   * Creates a new type verifier for any field that is going to be serialized
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxFieldVerifier createFieldVerifier(final Hx haxxor) {
    return hxField -> HxVerificationResult.ok();
  }

  /**
   * Creates a new type verifier for any method/constructor that is going to be serialized
   *
   * @param haxxor is the requesting instance
   * @return
   */
  public HxMethodVerifier createMethodVerifier(final Hx haxxor) {
    return hxMethod -> HxVerificationResult.ok();
  }

  /**
   * Creates a new stub-interpreter
   *
   * @param haxxor is the requesting instance
   * @return a
   */
  public HxStubHandler createStubHandler(final Hx haxxor) {
    return new CompoundHxStubHandler(new ServiceStubHandler());
  }

  public HxClassLoadingHandler createClassLoadingHandler(final Hx haxxor) {
    return new ReflectingClassLoadingHandler();
  }

  /**
   * @return
   */
  public Hx build() {
    return new Haxxor(this.flags, this);
  }
}
