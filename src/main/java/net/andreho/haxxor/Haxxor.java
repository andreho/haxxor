package net.andreho.haxxor;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.impl.HxArrayTypeImpl;
import net.andreho.haxxor.api.impl.HxPrimitiveTypeImpl;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxClassResolver;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCache;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxFieldInitializer;
import net.andreho.haxxor.spi.HxFieldVerifier;
import net.andreho.haxxor.spi.HxMethodInitializer;
import net.andreho.haxxor.spi.HxMethodVerifier;
import net.andreho.haxxor.spi.HxProvidable;
import net.andreho.haxxor.spi.HxStubInjector;
import net.andreho.haxxor.spi.HxTypeDeserializer;
import net.andreho.haxxor.spi.HxTypeInitializer;
import net.andreho.haxxor.spi.HxTypeSerializer;
import net.andreho.haxxor.spi.HxTypeVerifier;
import net.andreho.haxxor.spi.HxVerificationException;
import net.andreho.haxxor.spi.HxVerificationResult;
import net.andreho.haxxor.stub.errors.HxStubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * This class is <b>NOT THREAD-SAFE.</b>
 * <br/>Created by a.hofmann on 21.03.2015 at 01:17<br/>
 */
public class Haxxor
  implements Hx {

  private static final Logger LOG = LoggerFactory.getLogger(Haxxor.class.getName());

  private final int flags;
  private final ReadWriteLock readWriteLock;
  private final HxByteCodeLoader byteCodeLoader;
  private final Map<String, HxType> resolvedCache;
  private final Map<String, HxType> referenceCache;
  private final WeakReference<ClassLoader> classLoaderWeakReference;
  private final HxClassnameNormalizer classNameNormalizer;
  private final HxDeduplicationCache deduplicationCache;
  private final HxTypeDeserializer typeDeserializer;
  private final HxTypeSerializer typeSerializer;
  private final HxFieldInitializer fieldInitializer;
  private final HxMethodInitializer methodInitializer;
  private final HxElementFactory elementFactory;
  private final HxTypeVerifier typeVerifier;
  private final HxFieldVerifier fieldVerifier;
  private final HxMethodVerifier methodVerifier;
  private final HxTypeInitializer typeInitializer;
  private final HxStubInjector stubInjector;
  private final HxClassResolver classResolver;
  private final boolean concurrent;
  private final List<HxType> arrayInterfaces;

  private static <T> T notNull(T current,
                               T notNull) {
    return current == null ? notNull : current;
  }

  public Haxxor() {
    this(new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  public Haxxor(final HaxxorBuilder builder) {
    super();

    this.flags = builder.getFlags();
    this.concurrent = builder.isConcurrent();
    this.readWriteLock = new ReentrantReadWriteLock();
    this.classLoaderWeakReference = new WeakReference<>(builder.provideClassLoader(this));
    this.classResolver = builder.createClassResolver(this);

    this.resolvedCache = builder.createResolvedCache(this);
    this.referenceCache = builder.createReferenceCache(this);
    this.byteCodeLoader = builder.createByteCodeLoader(this);
    this.deduplicationCache = builder.createDeduplicationCache(this);
    this.typeInitializer = builder.createTypeInitializer(this);
    this.fieldInitializer = builder.createFieldInitializer(this);
    this.methodInitializer = builder.createMethodInitializer(this);
    this.elementFactory = builder.createElementFactory(this);
    this.typeDeserializer = builder.createTypeDeserializer(this);
    this.typeSerializer = builder.createTypeSerializer(this);
    this.typeVerifier = builder.createTypeVerifier(this);
    this.fieldVerifier = builder.createFieldVerifier(this);
    this.methodVerifier = builder.createMethodVerifier(this);
    this.classNameNormalizer = builder.createClassNameNormalizer(this);
    this.stubInjector = builder.createStubInjector(this);

    postInitialization();
    this.arrayInterfaces = createDefaultInterfacesOfArray();
  }

  protected void postInitialization() {
    String name = "void";
    HxPrimitiveTypeImpl type =
      new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "boolean";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "byte";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "short";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "char";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "int";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "float";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "long";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = "double";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    register(name, type);

    name = toNormalizedClassname("java.lang.Object");
    register(name, createReference(name));
  }

  protected List<HxType> createDefaultInterfacesOfArray() {
    return unmodifiableList(
      asList(
        reference(Cloneable.class.getName()),
        reference(Serializable.class.getName())
      )
    );
  }

  @Override
  public Haxxor getHaxxor() {
    return this;
  }

  public boolean isConcurrent() {
    return concurrent;
  }

  @Override
  public HxDeduplicationCache getDeduplicationCache() {
    return deduplicationCache;
  }

  /**
   * @return the associated byte-code-loader instance
   */
  public HxByteCodeLoader getByteCodeLoader() {
    return byteCodeLoader;
  }

  /**
   * @return the associated java classname provider
   */
  public HxClassnameNormalizer getClassNameNormalizer() {
    return classNameNormalizer;
  }

  /**
   * @return the associated element factory
   */
  public HxElementFactory getElementFactory() {
    return elementFactory;
  }

  /**
   * @return the associated type-verifier
   */
  public HxTypeVerifier getTypeVerifier() {
    return typeVerifier;
  }

  /**
   * @return the associated field-verifier
   */
  public HxFieldVerifier getFieldVerifier() {
    return fieldVerifier;
  }

  /**
   * @return the associated method-verifier
   */
  public HxMethodVerifier getMethodVerifier() {
    return methodVerifier;
  }

  /**
   * @return the associated type-deserializer
   */
  public HxTypeDeserializer getTypeDeserializer() {
    return typeDeserializer;
  }

  /**
   * @return the associated type-serializer
   */
  public HxTypeSerializer getTypeSerializer() {
    return typeSerializer;
  }

  /**
   * @return the associated type-initializer
   */
  public HxTypeInitializer getTypeInitializer() {
    return typeInitializer;
  }

  /**
   * @return the associated field-initializer
   */
  public HxFieldInitializer getFieldInitializer() {
    return fieldInitializer;
  }

  /**
   * @return the associated method-initializer
   */
  public HxMethodInitializer getMethodInitializer() {
    return methodInitializer;
  }

  /**
   * @return the associated stub-interpreter
   */
  public HxStubInjector getStubInjector() {
    return stubInjector;
  }

  public HxClassResolver getClassResolver() {
    return classResolver;
  }

  /**
   * @return a cache with resolved types
   */
  public Map<String, HxType> getResolvedCache() {
    return this.resolvedCache;
  }

  /**
   * @return a cache with references to a type
   */
  public Map<String, HxType> getReferenceCache() {
    return this.referenceCache;
  }

  /**
   * @param type to initialize and to prepare for further usage
   * @return the given type
   */
  @Override
  public HxType initialize(final HxType type) {
    getTypeInitializer().initialize(type);
    return type;
  }

  /**
   * @param field to initialize and to prepare for further usage
   * @return the given field
   */
  @Override
  public HxField initialize(final HxField field) {
    getFieldInitializer().initialize(field);
    return field;
  }

  /**
   * @param method to initialize and to prepare for further usage
   * @return the given method
   */
  @Override
  public HxMethod initialize(final HxMethod method) {
    getMethodInitializer().initialize(method);
    return method;
  }

  /**
   * @param type to verify for correctness and other rules
   * @return the given type
   */
  @Override
  public HxVerificationResult verify(final HxType type) {
    return getTypeVerifier().verify(type);
  }

  /**
   * @param field to verify for correctness and other rules
   * @return the given field
   */
  @Override
  public HxVerificationResult verify(final HxField field) {
    return getFieldVerifier().verify(field);
  }

  /**
   * @param method to verify for correctness and other rules
   * @return the given method
   */
  @Override
  public HxVerificationResult verify(final HxMethod method) {
    return getMethodVerifier().verify(method);
  }

  @Override
  public Class<?> resolveClass(final ClassLoader classLoader,
                               final ProtectionDomain protectionDomain,
                               final String className,
                               final byte[] bytecode) {
    return getClassResolver().resolveClass(classLoader, protectionDomain, className, bytecode);
  }

  /**
   * Checks whether this haxxor instance and its type collection
   * are still available to the associated class-loader.
   *
   * @return
   */
  public boolean isActive() {
    return getClassLoader() != null;
  }

  /**
   * @return class loader associated with this haxxor instance
   */
  @Override
  public ClassLoader getClassLoader() {
    return this.classLoaderWeakReference.get();
  }

  /**
   * Checks whether the given type name was already referenced or not
   *
   * @param classname to look for
   * @return <b>true</b> if there is a reference with given typename in this instance, <b>false</b> otherwise
   */
  @Override
  public boolean hasReference(String classname) {
    return fetchReferenceCache(toNormalizedClassname(classname)) != null;
  }

  /**
   * Checks whether the given type name was already resolved or not
   *
   * @param classname to look for
   * @return <b>true</b> if there is a resolved type with given typename in this instance, <b>false</b> otherwise
   */
  @Override
  public boolean hasResolved(String classname) {
    return fetchResolvedCache(toNormalizedClassname(classname)) != null;
  }

  /**
   * Tries to reference wanted type by its internal-name without to resolve it
   *
   * @param classname is the classname of the referenced type (like: int, java.lang.String or byte[])
   * @return a new or already cached type reference
   */
  @Override
  public HxType reference(final String classname) {
    return referenceInternally(toNormalizedClassname(classname));
  }

  /**
   * @param aClass as prototype for a reference
   * @return a new or cached reference to the given class
   */
  @Override
  public HxType reference(Class<?> aClass) {
    if (aClass.isArray()) {
      return reference(aClass.getName());
    }
    return referenceInternally(aClass.getName());
  }

  private HxType referenceInternally(final String classname) {
    checkClassLoaderAvailability();
    HxType reference = fetchReferenceCache(classname);
    if (reference == null) {
      reference = isArray(classname) ? new HxArrayTypeImpl(reference(minusArrayDimension(classname)))
                                     : createReference(classname);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Creating reference: {}", classname);
      }
      return storeReference(classname, reference);
    }
    return reference;
  }

  /**
   * Creates a collection with references to given type-names
   *
   * @param classnames to reference
   * @return a collection with possibly not-resolved references
   */
  @Override
  public List<HxType> referencesAsList(String... classnames) {
    if (classnames.length == 0) {
      return Collections.emptyList();
    }

    List<HxType> output = new ArrayList<>(classnames.length);
    for (String typeName : classnames) {
      output.add(reference(typeName));
    }
    return output;
  }

  /**
   * @param classnames for references to use (like: int, java.lang.String or byte[])
   * @return
   */
  @Override
  public HxType[] references(String... classnames) {
    if (classnames.length == 0) {
      return Constants.EMPTY_HX_TYPE_ARRAY;
    }
    HxType[] output = new HxType[classnames.length];

    for (int i = 0; i < classnames.length; i++) {
      String typeName = classnames[i];
      output[i] = reference(typeName);
    }

    return output;
  }

  @Override
  public HxType[] references(final Class<?>... classes) {
    if (classes.length == 0) {
      return Constants.EMPTY_HX_TYPE_ARRAY;
    }
    HxType[] output = new HxType[classes.length];

    for (int i = 0; i < classes.length; i++) {
      Class<?> cls = classes[i];
      output[i] = reference(cls);
    }

    return output;
  }

  /**
   * Resolves corresponding type by the given name to a {@link HxType haxxor type}
   *
   * @param classname of class to resolve (like: int, java.lang.String or byte[])
   * @return a resolved instance of {@link HxType}
   */
  @Override
  public HxType resolve(String classname) {
    return resolve(classname, this.flags);
  }

  /**
   * Resolves corresponding type by its name to a {@link HxType haxxor type}
   *
   * @param cls with a valid name
   * @return a resolved instance of {@link HxType}
   */
  @Override
  public HxType resolve(Class<?> cls) {
    if (cls.isPrimitive()) {
      return reference(cls);
    }
    if (cls.isArray()) {
      return resolve(cls.getName());
    }
    return resolveInternally(cls.getName(), this.flags);
  }

  /**
   * Resolves corresponding type by its name to a {@link HxType haxxor type}
   *
   * @param classname of class to resolve in binary format (like: int, java.lang.String or byte[])
   * @param flags     describes how to resolve desired type
   * @return a resolved instance of {@link HxType}
   * @implNote resolution means that the corresponding <code>*.class</code> file is loaded, parsed according to given
   * flags and represented as a {@link HxType}
   * @see Flags
   */
  @Override
  public HxType resolve(final String classname,
                        final int flags) {
    return resolveInternally(toNormalizedClassname(classname), flags);
  }

  @Override
  public HxType resolve(final String classname,
                        final byte[] byteCode) {
    return resolve(classname, byteCode, this.flags);
  }

  private HxType resolveInternally(final String classname, final int flags) {
    return resolveInternally(classname,
                             isPrimitive(classname) || isArray(classname)? null : safeLoadByteCode(classname),
                             flags);
  }

  /**
   * @param classname of class to resolve
   * @param byteCode  that describes the class itself
   * @param flags     describes how to resolve the desired type
   * @return a resolved instance of {@link HxType}
   * @implNote resolution means that the corresponding <code>*.class</code> file is loaded, parsed according to given
   * flags and represented as a {@link HxType}
   * @see Flags
   */
  @Override
  public HxType resolve(String classname,
                        final byte[] byteCode,
                        final int flags) {
    return resolveInternally(toNormalizedClassname(classname), byteCode, flags);
  }

  private byte[] safeLoadByteCode(final String classname) {
    return loadByteCode(classname).orElseThrow(
      () -> new IllegalStateException("Unable to load content of the class with name: " + classname));
  }

  private HxType resolveInternally(final String normalizedClassname,
                                   final byte[] byteCode,
                                   final int flags) {
    checkClassLoaderAvailability();
    HxType type;

    if (isArray(normalizedClassname)) {
      type = fetchReferenceCache(normalizedClassname);
      if (type != null) {
        return type;
      }
      return register(
        normalizedClassname,
        new HxArrayTypeImpl(resolveInternally(minusArrayDimension(normalizedClassname), flags))
      );
    } else if (isPrimitive(normalizedClassname)) {
      return fetchReferenceCache(normalizedClassname);
    }

    type = fetchResolvedCache(normalizedClassname);

    if (type == null) {
      type = readAndRegister(normalizedClassname, requireNonNull(byteCode, "Bytecode is null."), flags);

      if ((flags & Flags.SKIP_FIELDS) == 0) {
        type.addModifiers(HxType.Internals.FIELDS_LOADED);
      }
      if ((flags & Flags.SKIP_METHODS) == 0) {
        type.addModifiers(HxType.Internals.METHODS_LOADED);
      }
      if (LOG.isDebugEnabled()) {
        LOG.debug("Resolved: {}", normalizedClassname);
      }
    }
    return type;
  }

  /**
   * @param classname
   * @param byteCode
   * @param flags
   * @return
   */
  private HxType readAndRegister(final String classname,
                                 final byte[] byteCode,
                                 final int flags) {
    return register(classname, readClass(classname, byteCode, flags));
  }

  /**
   * @param classname
   * @param byteCode
   * @param flags
   * @return
   */
  protected HxType readClass(final String classname,
                             final byte[] byteCode,
                             final int flags) {

    return deserializeBytecodeTo(createType(classname), byteCode, flags);
  }

  /**
   * @param type
   * @param byteCode
   * @param flags
   * @return
   */
  protected HxType deserializeBytecodeTo(final HxType type,
                                         final byte[] byteCode,
                                         final int flags) {
    return getTypeDeserializer().deserialize(type, byteCode, flags);
  }

  protected boolean isArray(final String classname) {
    return classname.endsWith(HxConstants.ARRAY_DIMENSION);
  }

  protected boolean isPrimitive(final String classname) {
    return HxSort.fromName(classname).isPrimitive();
  }

  protected String minusArrayDimension(final String classname) {
    return classname.substring(0, classname.length() - HxConstants.ARRAY_DIMENSION.length());
  }

  protected Optional<byte[]> loadByteCode(final String classname) {
    return getByteCodeLoader().load(getClassLoader(), classname);
  }

  protected HxType fetchResolvedCache(final String classname) {
    if (!concurrent) {
      return readResolvedCache(classname);
    }

    return readResolvedCacheGuarded(classname);
  }

  private HxType readResolvedCacheGuarded(final String classname) {
    final Lock lock = readWriteLock.readLock();
    lock.lock();
    try {
      return readResolvedCache(classname);
    } finally {
      lock.unlock();
    }
  }

  private HxType readResolvedCache(final String classname) {
    return this.resolvedCache.get(classname);
  }

  protected HxType fetchReferenceCache(final String classname) {
    if (!concurrent) {
      return readReferenceCache(classname);
    }
    return readReferenceCacheGuarded(classname);
  }

  private HxType readReferenceCacheGuarded(final String classname) {
    final Lock lock = readWriteLock.readLock();
    lock.lock();
    try {
      return readReferenceCache(classname);
    } finally {
      lock.unlock();
    }
  }

  private HxType readReferenceCache(final String classname) {
    return this.referenceCache.get(classname);
  }

  protected void resolveElements(final HxType hxType,
                                 final int flags) {
    if (hxType.getHaxxor() != this) {
      throw new IllegalArgumentException("Given type must be managed by this instance.");
    }
    loadByteCode(hxType.getName())
      .ifPresent(byteCode -> deserializeBytecodeTo(hxType, byteCode, flags));
  }

  @Override
  public void resolveFields(final HxType hxType) {
    resolveElements(hxType, Flags.SKIP_CLASS_INTERNALS | Flags.SKIP_METHODS);
  }

  @Override
  public void resolveMethods(final HxType hxType) {
    resolveElements(hxType, Flags.SKIP_CLASS_INTERNALS | Flags.SKIP_FIELDS);
  }

  @Override
  public void resolveFieldsAndMethods(final HxType hxType) {

    resolveElements(hxType, Flags.SKIP_CLASS_INTERNALS);
  }

  /**
   * Registers the given type/reference with the provided typename
   *
   * @param classname       to use as key
   * @param typeOrReference to register
   * @return the actually registered type
   */
  @Override
  public HxType register(final String classname,
                         final HxType typeOrReference) {
    if (typeOrReference.isReference()) {
      return storeReference(classname, typeOrReference.toReference());
    }
    return storeResolved(classname, typeOrReference);
  }

  protected HxType storeReference(final String classname,
                                  final HxType reference) {
    if (concurrent) {
      return writeToReferenceCacheGuarded(classname, reference);
    }
    return writeToReferenceCache(classname, reference);
  }

  private HxType writeToReferenceCacheGuarded(final String classname,
                                              final HxType reference) {
    final Lock lock = readWriteLock.writeLock();
    lock.lock();
    try {
      return writeToReferenceCache(classname, reference);
    } finally {
      lock.unlock();
    }
  }

  private HxType writeToReferenceCache(final String classname,
                                       final HxType reference) {
    return notNull(this.referenceCache.putIfAbsent(classname, reference), reference);
  }

  protected HxType storeResolved(final String classname,
                                 final HxType resolved) {
    if (concurrent) {
      return writeToResolvedCacheGuarded(classname, resolved);
    }
    return writeToResolvedCache(classname, resolved);
  }

  private HxType writeToResolvedCacheGuarded(final String classname,
                                             final HxType resolved) {
    final Lock lock = readWriteLock.writeLock();
    lock.lock();
    try {
      return writeToResolvedCache(classname, resolved);
    } finally {
      lock.unlock();
    }
  }

  private HxType writeToResolvedCache(final String classname,
                                      final HxType resolved) {
    return notNull(this.resolvedCache.putIfAbsent(classname, resolved), resolved);
  }

  private void checkClassLoaderAvailability() {
    if (!isActive()) {
      throw new IllegalStateException("Associated class-loader was collected by GC.");
    }
  }

  @Override
  public String toNormalizedClassname(final String classname) {
    return classNameNormalizer.toNormalizedClassname(classname);
  }

  @Override
  public HxType createType(final String className) {
    return initialize(elementFactory.createType(toNormalizedClassname(className)));
  }

  @Override
  public HxType createReference(final String className) {
    return elementFactory.createReference(toNormalizedClassname(className));
  }

  @Override
  public HxType createReference(final HxType resolvedType) {
    return elementFactory.createReference(resolvedType);
  }

  @Override
  public HxField createField(final String fieldType,
                             final String fieldName) {
    return initialize(elementFactory.createField(toNormalizedClassname(fieldType), fieldName));
  }

  @Override
  public HxMethod createConstructor(final String... parameterTypes) {
    return initialize(elementFactory.createConstructor(toNormalizedClassnames(parameterTypes)));
  }

  @Override
  public HxMethod createConstructor(final HxType... parameterTypes) {
    return initialize(elementFactory.createConstructor(parameterTypes));
  }

  @Override
  public HxMethod createConstructorReference(final String declaringType,
                                             final String... parameterTypes) {
    return elementFactory.createConstructorReference(toNormalizedClassname(declaringType),
                                                     toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxMethod createMethod(final String returnType,
                               final String methodName,
                               final String... parameterTypes) {
    return initialize(elementFactory.createMethod(toNormalizedClassname(returnType),
                                                  methodName,
                                                  toNormalizedClassnames(parameterTypes)));
  }

  @Override
  public HxMethod createMethod(final HxType returnType,
                               final String methodName,
                               final HxType... parameterTypes) {
    return initialize(elementFactory.createMethod(returnType, methodName, parameterTypes));
  }

  @Override
  public HxMethod createMethodReference(final String declaringType,
                                        final String returnType,
                                        final String methodName,
                                        final String... parameterTypes) {
    return elementFactory.createMethodReference(toNormalizedClassname(declaringType),
                                                toNormalizedClassname(returnType),
                                                methodName,
                                                toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxParameter createParameter(final String classname) {
    return elementFactory.createParameter(toNormalizedClassname(classname));
  }

  @Override
  public HxAnnotation createAnnotation(final String classname,
                                       final boolean visible) {
    return elementFactory.createAnnotation(toNormalizedClassname(classname), visible);
  }

  @Override
  public HxAnnotated<?> createAnnotated() {
    return elementFactory.createAnnotated();
  }

  @Override
  public byte[] serialize(final HxType type,
                          final boolean computeFrames) {
    doFullVerification(type);
    return getTypeSerializer().serialize(type, computeFrames);
  }

  protected void doFullVerification(final HxType type) {
    //Check given type itself
    HxVerificationResult result = verify(type);
    HxVerificationResult current = result;

    //Check fields
    for (HxField field : type.getFields()) {
      HxVerificationResult subResult = verify(field);

      if (subResult.isFailed()) {
        if (result.isFailed()) {
          current = current.append(subResult);
        } else {
          result = current = subResult;
        }
      }
    }

    //Check methods
    for (HxMethod method : type.getMethods()) {
      HxVerificationResult subResult = verify(method);

      if (subResult.isFailed()) {
        if (result.isFailed()) {
          current = current.append(subResult);
        } else {
          result = current = subResult;
        }
      }
    }

    if (result.isFailed()) {
      throw new HxVerificationException(result);
    }
  }

  @Override
  public <T> T providePart(final HxProvidable<T> providable) {
    if (providable.equals(HxProvidable.Defaults.DEFAULT_INTERFACES_FOR_ARRAY)) {
      return (T) this.arrayInterfaces;
    }
    return null;
  }

  @Override
  public boolean injectStub(final HxType target,
                            final HxType stub)
  throws HxStubException {
    return getStubInjector().injectStub(target, stub);
  }

  @Override
  public String toString() {
//    final StringBuilder builder = new StringBuilder();
//    for (Entry<String, HxType> entry : getResolvedCache().entrySet()) {
//      builder.append(entry.getKey())
//             .append('\n');
//    }
//    return builder.toString();
    return "Hx{refs=" + getReferenceCache().size() + ", types=" + getResolvedCache().size() + ", active=" + isActive() +
           "}";
  }

}
