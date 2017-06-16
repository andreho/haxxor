package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spec.impl.HxArrayTypeImpl;
import net.andreho.haxxor.spec.impl.HxPrimitiveTypeImpl;
import net.andreho.haxxor.spec.visitors.HxTypeVisitor;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxTypeInitializer;
import net.andreho.haxxor.spi.HxTypeInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is <b>NOT THREAD-SAFE.</b>
 * <br/>Created by a.hofmann on 21.03.2015 at 01:17<br/>
 */
public class Haxxor
    implements Hx,
               HxElementFactory,
               HxClassnameNormalizer {

  private static final Logger LOG = LoggerFactory.getLogger(Haxxor.class.getName());

  private final int flags;
  private final HxByteCodeLoader byteCodeLoader;
  private final Map<String, HxType> resolvedCache;
  private final Map<String, HxTypeReference> referenceCache;
  private final WeakReference<ClassLoader> classLoaderWeakReference;
  private final HxClassnameNormalizer classNameNormalizer;
  private final HxTypeInterpreter typeInterpreter;
  private final HxElementFactory elementFactory;
  private final HxTypeInitializer typeInitializer;

  public Haxxor() {
    this(0, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  /**
   * @param flags
   * @see Haxxor.Flags
   */
  public Haxxor(int flags) {
    this(flags, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  /**
   *
   * @param flags
   * @param classLoader
   * @see Haxxor.Flags
   */
  public Haxxor(int flags,
                ClassLoader classLoader) {
    this(flags, new HaxxorBuilder(classLoader));
  }

  public Haxxor(int flags,
                HaxxorBuilder builder) {

    this.flags = flags;
    this.classLoaderWeakReference = new WeakReference<>(builder.provideClassLoader(this));

    this.byteCodeLoader = builder.createByteCodeLoader(this);
    this.resolvedCache = builder.createResolvedCache(this);
    this.referenceCache = builder.createReferenceCache(this);
    this.typeInitializer = builder.createTypeInitializer(this);
    this.elementFactory = builder.createElementFactory(this);
    this.typeInterpreter = builder.createTypeInterpreter(this);
    this.classNameNormalizer = builder.createClassNameNormalizer(this);

    initialize();
  }

  protected void initialize() {
    String name = "void";
    HxPrimitiveTypeImpl type =
        new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "boolean";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "byte";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "short";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "char";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "int";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "float";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "long";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = "double";
    type = new HxPrimitiveTypeImpl(this, toNormalizedClassname(name));
    resolvedCache.put(name, type);

    name = toNormalizedClassname("java.lang.Object");
    referenceCache.put(name, createReference(name));
  }

  /**
   * @return
   */
  protected HxTypeVisitor createTypeVisitor() {
    return new HxTypeVisitor(this);
  }

  /**
   * @param className
   * @return
   */
  protected HxType readClass(String className,
                             int opts) {
    final byte[] content = getByteCodeLoader().load(className);
    final HxTypeVisitor visitor = createTypeVisitor();
    final ClassReader classReader = new ClassReader(content);
    classReader.accept(visitor, opts);
    return visitor.getType();
  }

  protected boolean isArray(final String typeName) {
    return typeName.endsWith("[]");
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
   * @return the associated java element factory
   */
  public HxElementFactory getElementFactory() {
    return elementFactory;
  }

  /**
   * @return the associated type-interpreter
   */
  public HxTypeInterpreter getTypeInterpreter() {
    return typeInterpreter;
  }

  /**
   * @return the associated type-initializer
   */
  public HxTypeInitializer getTypeInitializer() {
    return typeInitializer;
  }

  @Override
  public Haxxor getHaxxor() {
    return this;
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
   * @return class loader associated with this haxxor instance
   */
  public ClassLoader getClassLoader() {
    return this.classLoaderWeakReference.get();
  }

  /**
   * Checks whether the given type name was already referenced or not
   *
   * @param typeName to look for
   * @return <b>true</b> if there is a reference with given typename in this instance, <b>false</b> otherwise
   */
  public boolean hasReference(String typeName) {
    typeName = toNormalizedClassname(typeName);
    return this.referenceCache.containsKey(typeName);
  }

  /**
   * Checks whether the given type name was already resolved or not
   *
   * @param typeName to look for
   * @return <b>true</b> if there is a resolved type with given typename in this instance, <b>false</b> otherwise
   */
  public boolean hasResolved(String typeName) {
    typeName = toNormalizedClassname(typeName);
    return this.resolvedCache.containsKey(typeName);
  }

  /**
   * Tries to reference wanted type by its internal-name without to resolve it
   *
   * @param typeName is the internal classname of the referenced type
   * @return a new or already existing type reference
   */
  public HxTypeReference reference(String typeName) {
    checkClassLoaderAvailability();

    typeName = toNormalizedClassname(typeName);
    HxTypeReference reference = this.referenceCache.get(typeName);

    if (reference == null) {
      reference = createReference(typeName);
      this.referenceCache.put(typeName, reference);

      if (LOG.isDebugEnabled()) {
        LOG.debug("New reference: {}", typeName);
      }
    }

    return reference;
  }

  /**
   * Creates a collection with references to given type-names
   *
   * @param typeNames to reference
   * @return a collection with possibly not-resolved references
   */
  public List<HxType> referencesAsList(String... typeNames) {
    if (typeNames.length == 0) {
      return Collections.emptyList();
    }

    List<HxType> output = new ArrayList<>(typeNames.length);
    for (String typeName : typeNames) {
      output.add(reference(typeName));
    }
    return output;
  }

  /**
   * @param typeNames
   * @return
   */
  public HxType[] referencesAsArray(String... typeNames) {
    if (typeNames.length == 0) {
      return Constants.EMPTY_HX_TYPE_ARRAY;
    }
    HxType[] output = new HxType[typeNames.length];

    for (int i = 0; i < typeNames.length; i++) {
      String typeName = typeNames[i];
      output[i] = reference(typeName);
    }

    return output;
  }

  /**
   * Resolves corresponding type by its name to a {@link HxType haxxor type}
   *
   * @param typeName to resolve
   * @return a real resolved instance of {@link HxType}
   */
  public HxType resolve(String typeName) {
    return resolve(typeName, this.flags);
  }

  /**
   * Resolves corresponding type by its name to a {@link HxType haxxor type}
   *
   * @param typeName to resolve
   * @param options  describes how to resolve wanted type
   * @return a real resolved instance of {@link HxType}
   * @implNote resolution means that the corresponding <code>*.class</code> file is loaded, parsed according to given
   * options and represented as a {@link HxType}
   */
  public HxType resolve(String typeName,
                        int options) {
    checkClassLoaderAvailability();
    typeName = toNormalizedClassname(typeName);
    HxType type = this.resolvedCache.get(typeName);

    if (type == null) {
      if (isArray(typeName)) {
        type = new HxArrayTypeImpl(this, typeName);
      } else {
        type = readClass(typeName, options);
      }

      this.resolvedCache.put(typeName, type);

      if (LOG.isDebugEnabled()) {
        LOG.debug("New type: {}", typeName);
      }
    }
    return type;
  }

  private void checkClassLoaderAvailability() {
    if (!isActive()) {
      throw new IllegalStateException("Associated class-loader was collected by GC.");
    }
  }

  @Override
  public String toNormalizedClassname(final String typeName) {
    return classNameNormalizer.toNormalizedClassname(typeName);
  }

  @Override
  public HxType createType(final String className) {
    HxType type = elementFactory.createType(toNormalizedClassname(className));
    getTypeInitializer().initialize(type);
    return type;
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
  public HxConstructor createConstructor(final String... parameterTypes) {
    return elementFactory.createConstructor(toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxConstructor createConstructorReference(final String declaringType,
                                                  final String... parameterTypes) {
    return elementFactory.createConstructorReference(toNormalizedClassname(declaringType),
                                                     toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxMethod createMethod(final String returnType,
                               final String methodName,
                               final String... parameterTypes) {
    return elementFactory.createMethod(toNormalizedClassname(returnType), methodName,
                                       toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxMethod createMethodReference(final String declaringType,
                                        final String returnType,
                                        final String methodName,
                                        final String... parameterTypes) {
    return elementFactory.createMethodReference(toNormalizedClassname(declaringType),
                                                toNormalizedClassname(returnType), methodName,
                                                toNormalizedClassnames(parameterTypes));
  }

  @Override
  public HxParameter createParameter(final String className) {
    return elementFactory.createParameter(toNormalizedClassname(className));
  }

  @Override
  public HxAnnotation createAnnotation(final String className,
                                       final boolean visible) {
    return elementFactory.createAnnotation(toNormalizedClassname(className), visible);
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
}
