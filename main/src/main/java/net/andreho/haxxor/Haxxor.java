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

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is <b>NOT THREAD-SAFE.</b>
 * <br/>Created by a.hofmann on 21.03.2015 at 01:17<br/>
 */
public class Haxxor implements HxElementFactory, HxByteCodeLoader,
                               HxInternalClassNameProvider {

  private static final Logger LOG = Logger.getLogger(Haxxor.class.getName());

  private final int opts;
  private final HxByteCodeLoader byteCodeLoader;
  private final Map<String, HxType> resolvedCache;
  private final Map<String, HxTypeReference> referenceCache;
  private final WeakReference<ClassLoader> classLoaderWeakReference;
  private final HxInternalClassNameProvider internalClassNameProvider;
  private final HxElementFactory elementFactory;
  private final Object lock = new Object();

  public Haxxor() {
    this(Flags.SKIP_DEBUG, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  public Haxxor(int opts) {
    this(opts, new HaxxorBuilder(Haxxor.class.getClassLoader()));
  }

  public Haxxor(int opts, ClassLoader classLoader) {
    this(opts, new HaxxorBuilder(classLoader));
  }

  public Haxxor(int opts, HaxxorBuilder builder) {

    this.opts = opts;
    this.classLoaderWeakReference = new WeakReference<>(builder.provideClassLoader(this));

    this.byteCodeLoader = builder.createCodeLoader(this);
    this.resolvedCache = builder.createResolvedCache(this);
    this.referenceCache = builder.createReferenceCache(this);
    this.elementFactory = builder.createElementFactory(this);
    this.internalClassNameProvider = builder.createInternalClassNameProvider(this);

    initialize();
  }

  protected void initialize() {
    String type;
    getResolvedCache().put(type = toInternalClassName("V"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("Z"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("B"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("C"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("S"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("I"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("F"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("J"), new HxPrimitiveTypeImpl(this, type));
    getResolvedCache().put(type = toInternalClassName("D"), new HxPrimitiveTypeImpl(this, type));

    getReferenceCache().put(type = toInternalClassName("java/lang/Object"), createReference(type));
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
    final byte[] content = load(className);
    final HxTypeVisitor visitor = createTypeVisitor();
    final ClassReader classReader = new ClassReader(content);
    classReader.accept(visitor, opts);
    return visitor.getType();
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
    typeName = toInternalClassName(typeName);
    return this.referenceCache.containsKey(typeName);
  }

  /**
   * Checks whether the given type name was already resolved or not
   *
   * @param typeName to look for
   * @return <b>true</b> if there is a resolved type with given typename in this instance, <b>false</b> otherwise
   */
  public boolean hasResolved(String typeName) {
    typeName = toInternalClassName(typeName);
    return this.resolvedCache.containsKey(typeName);
  }

  /**
   * Tries to reference wanted type by its name without to resolve it
   *
   * @param typeName
   * @return
   */
  public HxTypeReference reference(String typeName) {
    checkClassLoaderAvailability();
    typeName = toInternalClassName(typeName);

    HxTypeReference reference = this.referenceCache.get(typeName);

    if (reference == null) {
      if (hasResolved(typeName)) {
        return resolve(typeName).toReference();
      }

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
  public Collection<HxType> referencesAsCollection(String... typeNames) {
    if (typeNames.length == 0) {
      return Collections.emptySet();
    }

    Collection<HxType> output = new LinkedHashSet<>(typeNames.length);
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
    return resolve(typeName, this.opts);
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

    typeName = toInternalClassName(typeName);
    HxType type = this.resolvedCache.get(typeName);

    if (type == null) {
      if (typeName.charAt(0) == '[') {
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
  public byte[] load(final String className) {
    return byteCodeLoader.load(className);
  }

  @Override
  public String toInternalClassName(final String typeName) {
    return internalClassNameProvider.toInternalClassName(typeName);
  }

  @Override
  public HxType createType(final String internalTypeName) {
    return elementFactory.createType(internalTypeName);
  }

  @Override
  public HxTypeReference createReference(final String internalTypeName) {
    return elementFactory.createReference(internalTypeName);
  }

  @Override
  public HxField createField(final String internalTypeName, final String fieldName) {
    return elementFactory.createField(internalTypeName, fieldName);
  }

  @Override
  public HxConstructor createConstructor(final String... parameterTypes) {
    return elementFactory.createConstructor(parameterTypes);
  }

  @Override
  public HxConstructor createConstructorReference(final String declaringType, final String... parameterTypes) {
    return elementFactory.createConstructorReference(declaringType, parameterTypes);
  }

  @Override
  public HxMethod createMethod(final String methodName, final String returnType, final String... parameterTypes) {
    return elementFactory.createMethod(methodName, returnType, parameterTypes);
  }

  @Override
  public HxMethod createMethodReference(final String declaringType,
                                        final String methodName,
                                        final String returnType,
                                        final String... parameterTypes) {
    return elementFactory.createMethodReference(declaringType, methodName, returnType, parameterTypes);
  }

  @Override
  public HxParameter createParameter(final String internalTypeName) {
    return elementFactory.createParameter(internalTypeName);
  }

  @Override
  public HxAnnotation createAnnotation(final String internalTypeName, final boolean visible) {
    return elementFactory.createAnnotation(internalTypeName, visible);
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
