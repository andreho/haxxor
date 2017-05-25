package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.loading.HxByteCodeLoader;
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

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is <b>NOT THREAD-SAFE.</b>
 * <br/>Created by a.hofmann on 21.03.2015 at 01:17<br/>
 */
public class Haxxor {
   //-----------------------------------------------------------------------------------------------------------------

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

   /**
    * Flag to expand the stack map frames. By default stack map frames are
    * visited in their original format (i.e. "expanded" for classes whose
    * version is less than V1_6, and "compressed" for the other classes). If
    * this flag is set, stack map frames are always visited in expanded format
    * (this option adds a decompression/recompression step in ClassReader and
    * ClassWriter which degrades performances quite a lot).
    */
   public static final int EXPAND_FRAMES = 8;

   //----------------------------------------------------------------------------------------------------------------
   private final int opts;
   private final HxByteCodeLoader loader;
   private final Map<String, HxType> resolvedCache;
   private final Map<String, HxType> referenceCache;

   //----------------------------------------------------------------------------------------------------------------
   private final WeakReference<ClassLoader> classLoaderWeakReference;

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

      this.loader = builder.createLoader(this);
      this.resolvedCache = builder.createResolvedCache(this);
      this.referenceCache = builder.createReferenceCache(this);

      initialize();
   }

   //----------------------------------------------------------------------------------------------------------------

   protected void initialize() {
      getResolvedCache().put("V", new HxPrimitiveTypeImpl(this, "V"));
      getResolvedCache().put("Z", new HxPrimitiveTypeImpl(this, "Z"));
      getResolvedCache().put("B", new HxPrimitiveTypeImpl(this, "B"));
      getResolvedCache().put("C", new HxPrimitiveTypeImpl(this, "C"));
      getResolvedCache().put("S", new HxPrimitiveTypeImpl(this, "S"));
      getResolvedCache().put("I", new HxPrimitiveTypeImpl(this, "I"));
      getResolvedCache().put("F", new HxPrimitiveTypeImpl(this, "F"));
      getResolvedCache().put("J", new HxPrimitiveTypeImpl(this, "J"));
      getResolvedCache().put("D", new HxPrimitiveTypeImpl(this, "D"));

      getReferenceCache().put("java/lang/Object", createReference("java/lang/Object"));
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
      final byte[] content = getLoader().load(className);
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
   public String typeName(final String typeName) {
      int dim = 0;
      int off = 0;
      int len = typeName.length();

      if (typeName.charAt(off) == '[') {
         do {
            dim++;
         }
         while (typeName.charAt(++off) == '[');
      } else if (typeName.charAt(len - 1) == ']') {
         do {
            dim++;
            len -= 2;
         }
         while (typeName.charAt(len - 1) == ']');
      }

      //Do we have a descriptor form?
      if (typeName.charAt(len - 1) == ';') {
         off++; //destroy leading 'L'
         len--; //destroy trailing ';'
      }

      String type = typeName.substring(off, len);

      switch (type) {
         case "boolean":
            type = "Z";
            break;
         case "byte":
            type = "B";
            break;
         case "char":
            type = "C";
            break;
         case "short":
            type = "S";
            break;
         case "int":
            type = "I";
            break;
         case "float":
            type = "F";
            break;
         case "long":
            type = "J";
            break;
         case "double":
            type = "D";
            break;
         case "void":
            type = "V";
            break;
         default: {
            if (type.indexOf('/') == -1) {
               type = type.replace('.', '/');
            }
         }
      }
      if (dim > 0) {
         final StringBuilder builder = new StringBuilder(typeName.length());
         while (dim-- > 0) {
            builder.append('[');
         }
         return builder.append(type)
                       .toString();
      }
      return type;
   }

   /**
    * Transforms given array of typenames if needed to an internal name form
    * @param typeNames to transform
    * @return an array with typenames in the internal typename form
    */
   public String[] typeNames(final String... typeNames) {
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
   public HxByteCodeLoader getLoader() {
      return this.loader;
   }

   /**
    * Checks whether this haxxor instance and its type collection
    * are still available by associated class-loader.
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
    * @param typeName to check
    * @return
    */
   public boolean hasReference(String typeName) {
      typeName = typeName(typeName);
      return this.referenceCache.containsKey(typeName);
   }

   /**
    * Checks whether the given type name was already resolved or not
    *
    * @param typeName to check
    * @return
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
         System.out.println("Reference: " + typeName);
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
      if (!isActive()) {
         throw new IllegalStateException("Associated class-loader was collected by GC.");
      }

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
