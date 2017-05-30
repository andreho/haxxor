package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxConstructor;
import net.andreho.haxxor.spec.HxField;
import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxParameter;
import net.andreho.haxxor.spec.HxParameterizable;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.impl.HxAnnotationImpl;
import net.andreho.haxxor.spec.impl.HxConstructorImpl;
import net.andreho.haxxor.spec.impl.HxConstructorReferenceImpl;
import net.andreho.haxxor.spec.impl.HxFieldImpl;
import net.andreho.haxxor.spec.impl.HxMethodImpl;
import net.andreho.haxxor.spec.impl.HxMethodReferenceImpl;
import net.andreho.haxxor.spec.impl.HxParameterImpl;
import net.andreho.haxxor.spec.impl.HxPrimitiveTypeImpl;
import net.andreho.haxxor.spec.impl.HxTypeImpl;
import net.andreho.haxxor.spec.impl.HxTypeReferenceImpl;
import net.andreho.haxxor.spec.visitors.HxTypeVisitor;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.spi.HxTypeNamingStrategy;

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
public class Haxxor {
   private static final Logger LOG = Logger.getLogger(Haxxor.class.getName());


   private final int opts;
   private final HxByteCodeLoader codeLoader;
   private final Map<String, HxType> resolvedCache;
   private final Map<String, HxType> referenceCache;
   private final WeakReference<ClassLoader> classLoaderWeakReference;
   private final HxTypeNamingStrategy typeNamingStrategy;
   private final Object lock = new Object();

   public Haxxor(int opts) {
      this(opts, Haxxor.class.getClassLoader(), new HaxxorBuilder());
   }

   public Haxxor(int opts, ClassLoader classLoader) {
      this(opts, classLoader, new HaxxorBuilder());
   }

   public Haxxor(int opts, HaxxorBuilder builder) {
      this(opts, Haxxor.class.getClassLoader(), builder);
   }

   public Haxxor(
         int opts,
         ClassLoader classLoader,
         HaxxorBuilder builder) {

      this.opts = opts;
      this.classLoaderWeakReference = new WeakReference<>(classLoader);

      this.typeNamingStrategy = builder.createTypeNamingStrategy(this);
      this.codeLoader = builder.createCodeLoader(this);
      this.resolvedCache = builder.createResolvedCache(this);
      this.referenceCache = builder.createReferenceCache(this);

      initialize();
   }

   //----------------------------------------------------------------------------------------------------------------

   protected void initialize() {
      String type;
      getResolvedCache().put(type = typeName("V"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("Z"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("B"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("C"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("S"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("I"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("F"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("J"), new HxPrimitiveTypeImpl(this, type));
      getResolvedCache().put(type = typeName("D"), new HxPrimitiveTypeImpl(this, type));

      getReferenceCache().put(type = typeName("java/lang/Object"), createReference(type));
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
      final byte[] content = getCodeLoader().load(this, className);
      final HxTypeVisitor visitor = createTypeVisitor();
      final ClassReader classReader = new ClassReader(content);
      classReader.accept(visitor, opts);
      return visitor.getType();
   }

   //-----------------------------------------------------------------------------------------------------------------

   /**
    * Transforms given typename if needed to an internal name form
    *
    * @param typeName to transform
    * @return an internal name of the given typename
    */
   protected String typeName(final String typeName) {
      return getTypeNamingStrategy().toTypeName(typeName);
   }

   /**
    * Transforms given array of typenames if needed to an internal name form
    * @param typeNames to transform
    * @return an array with typenames in the internal typename form
    */
   protected String[] typeNames(final String... typeNames) {
      String[] result = new String[typeNames.length];
      for (int i = 0; i < typeNames.length; i++) {
         result[i] = typeName(typeNames[i]);
      }
      return result;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @return loader used to resolve and to load requested class as a byte-array
    */
   public HxByteCodeLoader getCodeLoader() {
      return this.codeLoader;
   }

   /**
    * @return associated type-naming strategy
    */
   public HxTypeNamingStrategy getTypeNamingStrategy() {
      return this.typeNamingStrategy;
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
   public Map<String, HxType> getReferenceCache() {
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
      typeName = typeName(typeName);
      return this.referenceCache.containsKey(typeName);
   }

   /**
    * Checks whether the given type name was already resolved or not
    *
    * @param typeName to look for
    * @return <b>true</b> if there is a resolved type with given typename in this instance, <b>false</b> otherwise
    */
   public boolean hasResolved(String typeName) {
      typeName = typeName(typeName);
      return this.resolvedCache.containsKey(typeName);
   }

   /**
    * Tries to reference wanted type by its name without to resolve it
    *
    * @param typeName
    * @return
    */
   public HxType reference(String typeName) {
      typeName = typeName(typeName);

      HxType reference = this.referenceCache.get(typeName);

      if (reference == null) {
         if (hasResolved(typeName)) {
            return resolve(typeName);
         }

         reference = createReference(typeName);
         this.referenceCache.put(typeName, reference);

         if(LOG.isLoggable(Level.CONFIG)) {
            LOG.config("Reference: " + typeName);
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
   public Collection<HxType> reference(String... typeNames) {
      if(typeNames.length == 0) {
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
   public HxType[] referenceArray(String... typeNames) {
      if(typeNames.length == 0) {
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

      typeName = typeName(typeName);

      HxType type = this.resolvedCache.get(typeName);

      if (type == null) {
         if (!typeName.startsWith("[")) {
            this.resolvedCache.put(typeName, createType(typeName));
         } else {
            type = readClass(typeName, opts);
         }
         this.resolvedCache.put(typeName, type);
      }
      return type;
   }

   private void checkClassLoaderAvailability() {
      if (!isActive()) {
         throw new IllegalStateException("Associated class-loader was collected by GC.");
      }
   }

   //----------------------------------------------------------------------------------------------------------------

   public HxType createType(final String internalTypeName) {
      return new HxTypeImpl(this, typeName(internalTypeName));
   }

   public HxType createReference(final String internalTypeName) {
      return new HxTypeReferenceImpl(this, typeName(internalTypeName));
   }

   public HxField createField(final HxType owner, final String name, final String internalType) {
      final HxType fieldType = createReference(internalType);
      return new HxFieldImpl(owner, fieldType, name);
   }

   public HxConstructor createConstructor(final HxType owner, final String... parametersAsInternalTypes) {
      return new HxConstructorImpl(owner, this.referenceArray(parametersAsInternalTypes));
   }

   public HxConstructor createConstructorReference(final HxType owner, final String... parametersAsInternalTypes) {
      return new HxConstructorReferenceImpl(owner, this.referenceArray(parametersAsInternalTypes));
   }

   public HxMethod createMethod(final HxType owner, final String name, final String internalReturnType,
                                final String... parametersAsInternalTypes) {
      return new HxMethodImpl(owner, name, this.reference(internalReturnType),
                              this.referenceArray(parametersAsInternalTypes));
   }

   public HxMethod createMethodReference(final HxType owner, final String name, final String internalReturnType,
                                         final String... parametersAsInternalTypes) {
      return new HxMethodReferenceImpl(owner, name, this.reference(internalReturnType),
                                       this.referenceArray(parametersAsInternalTypes));
   }

   public HxParameter createParameter(final HxParameterizable owner, int index) {
      return new HxParameterImpl(owner, index);
   }

   public HxAnnotation createAnnotation(final String internalTypeName, final boolean visible) {
      return new HxAnnotationImpl(createReference(internalTypeName), visible);
   }

   //----------------------------------------------------------------------------------------------------------------

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
