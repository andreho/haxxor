package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassReader;
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
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxInternalClassNameProvider;
import net.andreho.haxxor.spi.HxJavaClassNameProvider;
import net.andreho.haxxor.spi.HxTypeInitializer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is <b>NOT THREAD-SAFE.</b>
 * <br/>Created by a.hofmann on 21.03.2015 at 01:17<br/>
 */
public class Haxxor
    implements HxElementFactory,
               HxJavaClassNameProvider {

  private static final Logger LOG = Logger.getLogger(Haxxor.class.getName());

  private final int flags;
  private final HxByteCodeLoader byteCodeLoader;
  private final Map<String, HxType> resolvedCache;
  private final Map<String, HxTypeReference> referenceCache;
  private final WeakReference<ClassLoader> classLoaderWeakReference;
  private final HxInternalClassNameProvider internalClassNameProvider;
  private final HxJavaClassNameProvider javaClassNameProvider;
  private final HxElementFactory elementFactory;
  private final HxTypeInitializer typeInitializer;

  public Haxxor() {
    this(0, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  public Haxxor(int flags) {
    this(flags, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  public Haxxor(int flags, ClassLoader classLoader) {
    this(flags, new HaxxorBuilder(classLoader));
  }

  public Haxxor(int flags, HaxxorBuilder builder) {

    this.flags = flags;
    this.classLoaderWeakReference = new WeakReference<>(builder.provideClassLoader(this));

    this.byteCodeLoader = builder.createByteCodeLoader(this);
    this.resolvedCache = builder.createResolvedCache(this);
    this.referenceCache = builder.createReferenceCache(this);
    this.typeInitializer = builder.createTypeInitializer(this);
    this.elementFactory = builder.createElementFactory(this);
    this.javaClassNameProvider = builder.createJavaClassNameProvider(this);
    this.internalClassNameProvider = builder.createInternalClassNameProvider(this);

    initialize();
  }

  protected void initialize() {
    String name = "void";
    HxPrimitiveTypeImpl type =
        new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "boolean";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "byte";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "short";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "char";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "int";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "float";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "long";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = "double";
    type = new HxPrimitiveTypeImpl(this, toJavaClassName(name));
    resolvedCache.put(name, type);

    name = toJavaClassName("java.lang.Object");
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
  protected HxType readClass(String className, int opts) {
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
   * @return the associated internal classname provider instance
   */
  public HxInternalClassNameProvider getInternalClassNameProvider() {
    return internalClassNameProvider;
  }

  /**
   * @return the associated java classname provider
   */
  public HxJavaClassNameProvider getJavaClassNameProvider() {
    return javaClassNameProvider;
  }

  /**
   * @return the associated java element factory
   */
  public HxElementFactory getElementFactory() {
    return elementFactory;
  }

  /**
   * @return the associated type-initializer
   */
  public HxTypeInitializer getTypeInitializer() {
    return typeInitializer;
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
    typeName = toJavaClassName(typeName);
    return this.referenceCache.containsKey(typeName);
  }

  /**
   * Checks whether the given type name was already resolved or not
   *
   * @param typeName to look for
   * @return <b>true</b> if there is a resolved type with given typename in this instance, <b>false</b> otherwise
   */
  public boolean hasResolved(String typeName) {
    typeName = toJavaClassName(typeName);
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

    typeName = toJavaClassName(typeName);
    HxTypeReference reference = this.referenceCache.get(typeName);

    if (reference == null) {
      reference = createReference(typeName);
      this.referenceCache.put(typeName, reference);

      if (LOG.isLoggable(Level.CONFIG)) {
        LOG.config("New reference: " + typeName);
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
   * @param opts     describes how to resolve wanted type
   * @return a real resolved instance of {@link HxType}
   */
  public HxType resolve(String typeName, int opts) {
    checkClassLoaderAvailability();
    typeName = toJavaClassName(typeName);
    HxType type = this.resolvedCache.get(typeName);

    if (type == null) {
      if (isArray(typeName)) {
        type = new HxArrayTypeImpl(this, typeName);
      } else {
        type = readClass(typeName, opts);
      }

      this.resolvedCache.put(typeName, type);

      if (LOG.isLoggable(Level.CONFIG)) {
        LOG.config("New type: " + typeName);
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
  public String toJavaClassName(final String typeName) {
    return javaClassNameProvider.toJavaClassName(typeName);
  }

  private String[] toJavaClassNames(final String... typeNames) {
    String[] array = new String[typeNames.length];
    for (int i = 0; i < typeNames.length; i++) {
      array[i] = toJavaClassName(typeNames[i]);
    }
    return array;
  }

  @Override
  public HxType createType(final String className) {
    HxType type = elementFactory.createType(toJavaClassName(className));
    getTypeInitializer().initialize(type);
    return type;
  }

  @Override
  public HxTypeReference createReference(final String className) {
    return elementFactory.createReference(toJavaClassName(className));
  }

  @Override
  public HxTypeReference createReference(final HxType resolvedType) {
    return elementFactory.createReference(resolvedType);
  }

  @Override
  public HxField createField(final String className,
                             final String fieldName) {
    return elementFactory.createField(toJavaClassName(className), fieldName);
  }

  @Override
  public HxConstructor createConstructor(final String... parameterTypes) {
    return elementFactory.createConstructor(toJavaClassNames(parameterTypes));
  }

  @Override
  public HxConstructor createConstructorReference(final String declaringType, final String... parameterTypes) {
    return elementFactory.createConstructorReference(toJavaClassName(declaringType),
                                                     toJavaClassNames (parameterTypes));
  }

  @Override
  public HxMethod createMethod(final String returnType,
                               final String methodName,
                               final String... parameterTypes) {
    return elementFactory.createMethod(toJavaClassName(returnType), methodName,
                                       toJavaClassNames(parameterTypes));
  }

  @Override
  public HxMethod createMethodReference(final String declaringType,
                                        final String returnType,
                                        final String methodName,
                                        final String... parameterTypes) {
    return elementFactory.createMethodReference(toJavaClassName(declaringType),
                                                toJavaClassName(returnType), methodName,
                                                toJavaClassNames(parameterTypes));
  }

  @Override
  public HxParameter createParameter(final String className) {
    return elementFactory.createParameter(toJavaClassName(className));
  }

  @Override
  public HxAnnotation createAnnotation(final String className, final boolean visible) {
    return elementFactory.createAnnotation(toJavaClassName(className), visible);
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
