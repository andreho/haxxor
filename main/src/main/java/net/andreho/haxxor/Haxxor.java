package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeReference;
import net.andreho.haxxor.api.impl.HxArrayTypeImpl;
import net.andreho.haxxor.api.impl.HxPrimitiveTypeImpl;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCache;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxFieldInitializer;
import net.andreho.haxxor.spi.HxFieldVerifier;
import net.andreho.haxxor.spi.HxMethodInitializer;
import net.andreho.haxxor.spi.HxMethodVerifier;
import net.andreho.haxxor.spi.HxStubInterpreter;
import net.andreho.haxxor.spi.HxTypeDeserializer;
import net.andreho.haxxor.spi.HxTypeInitializer;
import net.andreho.haxxor.spi.HxTypeSerializer;
import net.andreho.haxxor.spi.HxTypeVerifier;
import net.andreho.haxxor.spi.HxVerificationException;
import net.andreho.haxxor.spi.HxVerificationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
  private final Map<String, HxTypeReference> referenceCache;
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
  private final HxStubInterpreter stubInterpreter;
  private final boolean concurrent;

  /**
   * <br/>Created by a.hofmann on 30.05.2017 at 12:45.
   */
  public static abstract class Flags {

    /**
     * Flag to skip method code. If this class is set <code>CODE</code>
     * attribute won't be visited. This can be used, for example, to retrieve
     * annotations for methods and method parameters.
     */
    public static final int SKIP_CODE = 1;

    /**
     * Flag to skip the debug information in the class. If this flag is set the
     * debug information of the class is not visited, i.e. the
     * {@link MethodVisitor#visitLocalVariable visitLocalVariable} and
     * {@link MethodVisitor#visitLineNumber visitLineNumber} methods will not be
     * called.
     */
    public static final int SKIP_DEBUG = 2;

    /**
     * Flag to skip the stack map frames in the class. If this flag is set the
     * stack map frames of the class is not visited, i.e. the
     * {@link MethodVisitor#visitFrame visitFrame} method will not be called.
     * This flag is useful when the {@link ClassWriter#COMPUTE_FRAMES} option is
     * used: it avoids visiting frames that will be ignored and recomputed from
     * scratch in the class writer.
     */
    public static final int SKIP_FRAMES = 4;

//    /**
//     * Flag to expand the stack map frames. By default stack map frames are
//     * visited in their original format (i.e. "expanded" for classes whose
//     * version is less than V1_6, and "compressed" for the other classes). If
//     * this flag is set, stack map frames are always visited in expanded format
//     * (this option adds a decompression/recompression step in ClassReader and
//     * ClassWriter which degrades performances quite a lot).
//     */
//    public static final int EXPAND_FRAMES = 8;

    private Flags() {
    }
  }

  public Haxxor() {
    this(0, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  public Haxxor(final HaxxorBuilder builder) {
    this(0, builder);
  }

  /**
   * @param flags
   * @see Haxxor.Flags
   */
  public Haxxor(final int flags) {
    this(flags, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  /**
   * @param flags
   * @param classLoader
   * @see Haxxor.Flags
   */
  public Haxxor(final int flags,
                final ClassLoader classLoader) {
    this(flags, new HaxxorBuilder(classLoader));
  }

  public Haxxor(final int flags,
                final HaxxorBuilder builder) {
    super();

    this.flags = flags;
    this.classLoaderWeakReference = new WeakReference<>(builder.provideClassLoader(this));

    this.byteCodeLoader = builder.createByteCodeLoader(this);
    this.resolvedCache = builder.createResolvedCache(this);
    this.referenceCache = builder.createReferenceCache(this);
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
    this.stubInterpreter = builder.createStubInterpreter(this);
    this.readWriteLock = new ReentrantReadWriteLock();
    this.concurrent = builder.isConcurrent();

    postInitialization();
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

  /**
   * @return
   */
  protected HxType readClass(final String classname,
                             final byte[] byteCode,
                             final int flags) {
    return getTypeDeserializer().deserialize(classname, byteCode, flags);
  }

  protected boolean isArray(final String classname) {
    return classname.endsWith(HxConstants.ARRAY_DIMENSION);
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
  public HxStubInterpreter getStubInterpreter() {
    return stubInterpreter;
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
  public Map<String, HxTypeReference> getReferenceCache() {
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
    classname = toNormalizedClassname(classname);
    return this.referenceCache.containsKey(classname);
  }

  /**
   * Checks whether the given type name was already resolved or not
   *
   * @param classname to look for
   * @return <b>true</b> if there is a resolved type with given typename in this instance, <b>false</b> otherwise
   */
  @Override
  public boolean hasResolved(String classname) {
    classname = toNormalizedClassname(classname);
    return this.resolvedCache.containsKey(classname);
  }

  /**
   * Tries to reference wanted type by its internal-name without to resolve it
   *
   * @param classname is the classname of the referenced type
   * @return a new or already cached type reference
   */
  @Override
  public HxTypeReference reference(String classname) {
    checkClassLoaderAvailability();
    classname = toNormalizedClassname(classname);
    HxTypeReference reference = fetchReferenceCache(classname);

    if (reference == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("New reference: {}", classname);
      }
      return storeSynchronizedReference(classname, createReference(classname));
    }

    return reference;
  }

  /**
   * @param aClass as prototype for a reference
   * @return a new or cached reference to the given class
   */
  @Override
  public HxTypeReference reference(Class<?> aClass) {
    return reference(aClass.getName());
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
   * @param classnames
   * @return
   */
  @Override
  public HxType[] referencesAsArray(String... classnames) {
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

  /**
   * Resolves corresponding type by the given name to a {@link HxType haxxor type}
   *
   * @param classname of class to resolve
   * @return a resolved instance of {@link HxType}
   */
  @Override
  public HxType resolve(String classname) {
    return resolve(classname, this.flags);
  }

  /**
   * Resolves corresponding type by its name to a {@link HxType haxxor type}
   *
   * @param aClass with a valid name
   * @return a resolved instance of {@link HxType}
   */
  @Override
  public HxType resolve(Class<?> aClass) {
    return resolve(aClass.getName());
  }

  /**
   * Resolves corresponding type by its name to a {@link HxType haxxor type}
   *
   * @param classname of class to resolve
   * @param flags describes how to resolve desired type
   * @return a resolved instance of {@link HxType}
   * @implNote resolution means that the corresponding <code>*.class</code> file is loaded, parsed according to given
   * flags and represented as a {@link HxType}
   * @see Flags
   */
  @Override
  public HxType resolve(String classname,
                        int flags) {
    checkClassLoaderAvailability();
    classname = toNormalizedClassname(classname);
    HxType type = fetchResolvedCache(classname);

    if (type != null) {
      return type;
    }

    if (isArray(classname)) {
      return register(classname, new HxArrayTypeImpl(this, classname));
    }
    return resolveInternally(
      classname,
      getByteCodeLoader().load(getClassLoader(), classname), flags
    );
  }

  /**
   * @param classname of class to resolve
   * @param byteCode that describes the class itself
   * @param flags describes how to resolve the desired type
   * @return a resolved instance of {@link HxType}
   * @implNote resolution means that the corresponding <code>*.class</code> file is loaded, parsed according to given
   * flags and represented as a {@link HxType}
   * @see Flags
   */
  @Override
  public HxType resolve(String classname,
                        final byte[] byteCode,
                        final int flags) {
    checkClassLoaderAvailability();
    classname = toNormalizedClassname(classname);
    HxType type = fetchResolvedCache(classname);

    if (type != null) {
      return type;
    }

    return resolveInternally(classname, byteCode, flags);
  }

  protected HxType fetchResolvedCache(final String classname) {
    if(!concurrent) {
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

  protected HxTypeReference fetchReferenceCache(final String classname) {
    if(!concurrent) {
      return readReferenceCache(classname);
    }

    return readReferenceCacheGuarded(classname);
  }

  private HxTypeReference readReferenceCacheGuarded(final String classname) {
    final Lock lock = readWriteLock.readLock();
    lock.lock();
    try {
      return readReferenceCache(classname);
    } finally {
      lock.unlock();
    }
  }

  private HxTypeReference readReferenceCache(final String classname) {
    return this.referenceCache.get(classname);
  }

  private HxType resolveInternally(String classname,
                                   final byte[] byteCode,
                                   final int flags) {
    HxType type = readClass(classname, byteCode, flags);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Resolving: {}", classname);
    }
    return register(classname, type);
  }

  /**
   * Registers the given type/reference with the provided typename
   *
   * @param classname to use as key
   * @param typeOrReference     to register
   * @return the actually registered type
   */
  @Override
  public HxType register(final String classname,
                         final HxType typeOrReference) {
    if (typeOrReference.isReference()) {
      return storeSynchronizedReference(classname, typeOrReference.toReference());
    }
    return storeSynchronizedResolved(classname, typeOrReference);
  }

  protected HxTypeReference storeSynchronizedReference(final String classname,
                                                       final HxTypeReference reference) {
    if(!concurrent) {
      return writeToReferenceCache(classname, reference);
    }

    return writeToReferenceCacheGuarded(classname, reference);
  }

  private HxTypeReference writeToReferenceCacheGuarded(final String classname,
                                                       final HxTypeReference reference) {
    final Lock lock = readWriteLock.writeLock();
    lock.lock();
    try {
      return writeToReferenceCache(classname, reference);
    } finally {
      lock.unlock();
    }
  }

  private static  <T> T notNull(T current, T notNull) {
    return current == null? notNull : current;
  }

  private HxTypeReference writeToReferenceCache(final String classname,
                                                final HxTypeReference reference) {
    return notNull(this.referenceCache.putIfAbsent(classname, reference), reference);
  }

  protected HxType storeSynchronizedResolved(final String classname,
                                             final HxType resolved) {
    if(!concurrent) {
      return writeToResolvedCache(classname, resolved);
    }

    return writeToResolvedCacheGuarded(classname, resolved);
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
    return elementFactory.createType(toNormalizedClassname(className));
  }

  @Override
  public HxTypeReference createReference(final String className) {
    return elementFactory.createReference(toNormalizedClassname(className));
  }

  @Override
  public HxTypeReference createReference(final HxType resolvedType) {
    return elementFactory.createReference(resolvedType);
  }

  @Override
  public HxField createField(final String className,
                             final String fieldName) {
    return elementFactory.createField(toNormalizedClassname(className), fieldName);
  }

  @Override
  public HxMethod createConstructor(final String... parameterTypes) {
    return elementFactory.createConstructor(toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxMethod createConstructor(final HxType... parameterTypes) {
    return elementFactory.createConstructor(parameterTypes);
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
    return elementFactory.createMethod(toNormalizedClassname(returnType),
                                       methodName,
                                       toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxMethod createMethod(final HxType returnType,
                               final String methodName,
                               final HxType... parameterTypes) {
    return elementFactory.createMethod(returnType, methodName, parameterTypes);
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
  public byte[] serialize(final HxType type,
                          final boolean computeFrames) {
    doFullVerification(type);

    return getTypeSerializer().serialize(type, computeFrames);
  }

  protected void doFullVerification(final HxType type) {
    //Type itself
    HxVerificationResult result = verify(type);
    HxVerificationResult current = result;

    //Fields
    for(HxField field : type.getFields()) {
      HxVerificationResult subResult = verify(field);

      if(subResult.isFailed()) {
        if(result.isFailed()) {
          current = current.append(subResult);
        } else {
          result = current = subResult;
        }
      }
    }

    //Methods
    for(HxMethod method : type.getMethods()) {
      HxVerificationResult subResult = verify(method);

      if(subResult.isFailed()) {
        if(result.isFailed()) {
          current = current.append(subResult);
        } else {
          result = current = subResult;
        }
      }
    }

    if(result.isFailed()) {
      throw new HxVerificationException(result);
    }
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();

    for (Entry<String, HxType> entry : getResolvedCache().entrySet()) {
      builder.append(entry.getKey())
             .append('\n');
    }

    return builder.toString();
  }
}
