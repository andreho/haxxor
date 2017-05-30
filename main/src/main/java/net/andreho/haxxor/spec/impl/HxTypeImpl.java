package net.andreho.haxxor.spec.impl;

import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxConstructor;
import net.andreho.haxxor.spec.HxField;
import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxTypeImpl extends HxAbstractType implements HxType {
   protected Version version = Version.V1_8;
   protected HxType superType;
   protected String genericSignature = "";
   protected Collection<HxType> interfaces = Collections.emptySet();
   protected Collection<HxType> declaredTypes = Collections.emptySet();

   protected List<HxField> fields = Collections.emptyList();
   protected Map<String, HxField> fieldMap = Collections.emptyMap();

   protected List<HxMethod> methods = Collections.emptyList();
   protected Map<String, Collection<HxMethod>> methodMap = Collections.emptyMap();
   protected List<HxConstructor> constructors = Collections.emptyList();

   public HxTypeImpl(Haxxor haxxor, String name) {
      super(haxxor, name);
      this.superType = haxxor.reference("java/lang/Object");
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public Version getVersion() {
      return version;
   }

   @Override
   public HxType setVersion(Version version) {
      this.version = version;
      return this;
   }

   @Override
   public String getGenericSignature() {
      return genericSignature;
   }

   @Override
   public HxType setGenericSignature(String genericSignature) {
      this.genericSignature = genericSignature;
      return this;
   }

   @Override
   public HxType getSuperType() {
      return superType;
   }

   @Override
   public HxType setSuperType(HxType superType) {
      if (superType == null) {
         throw new IllegalArgumentException("Super type is null");
      }

      this.superType = superType;
      return this;
   }

   @Override
   public Collection<HxType> getInterfaces() {
      return interfaces;
   }

   @Override
   public HxType setInterfaces(Collection<HxType> interfaces) {
      if (interfaces == null) {
         interfaces = Collections.emptySet();
      }

      this.interfaces = interfaces;

      return this;
   }

   @Override
   public Collection<HxType> getDeclaredTypes() {
      return declaredTypes;
   }

   @Override
   public HxType setDeclaredTypes(Collection<HxType> declaredTypes) {
      if (declaredTypes == null) {
         declaredTypes = Collections.emptySet();
      }

      this.declaredTypes = declaredTypes;

      return this;
   }

   @Override
   public HxField getField(String name) {
      return fieldMap.get(name);
   }

   @Override
   public List<HxField> getFields() {
      return fields;
   }


   @Override
   public HxType addField(HxField field) {
      if (field.getDeclaringMember() != null ||
         initialize(Part.FIELDS).hasField(field.getName()) ||
         fieldMap.put(field.getName(), field) != null) {

         throw new IllegalArgumentException("Field already exists: " + field);
      }

      field.setDeclaringMember(this);
      fields.add(field);

      return this;
   }

   private int indexOf(HxField field) {
      for (int i = 0; i < fields.size(); i++) {
         if(field == fields.get(i)) {
            return i;
         }
      }
      return -1;
   }

   @Override
   public HxType updateField(HxField field) {
      if(!equals(field.getDeclaringMember())) {
         throw new IllegalArgumentException("Invalid field argument: " + field);
      }
      HxField current = this.fieldMap.get(field.getName());
      if(current == null) {
         int index = indexOf(field);
         if(index < 0) {
            throw new IllegalArgumentException("Invalid field argument: " + field);
         }
         this.fieldMap.put(field.getName(), fields.get(index));
      }

      return this;
   }

   @Override
   public HxType removeField(HxField field) {
      if (!initialize(Part.FIELDS).hasField(field.getName()) ||
          fieldMap.remove(field.getName()) != field) {

         throw new IllegalArgumentException("Invalid field argument: " + field);
      }

      fields.remove(field);
      field.setDeclaringMember(null);

      return this;
   }

   @Override
   public HxType setFields(List<HxField> fields) {
      if (isUninitialized(fields)) {
         this.fields = Collections.emptyList();
         this.fieldMap = Collections.emptyMap();
         return this;
      }

      this.fields = new ArrayList<>(fields.size());
      this.fieldMap = new LinkedHashMap<>(fields.size());

      for (HxField field : fields) {
         if (this.fieldMap.put(field.getName(), field) != null) {
            throw new IllegalStateException("Ambiguous field: " + field);
         }

         field.setDeclaringMember(this);
         this.fields.add(field);
      }

      return this;
   }

   @Override
   public List<HxMethod> getMethods() {
      return methods;
   }

   @Override
   public HxType setMethods(List<HxMethod> methods) {
      if (isUninitialized(methods)) {
         this.methods = Collections.emptyList();
         this.methodMap = Collections.emptyMap();
         return this;
      }

      this.methods = new ArrayList<>(methods.size());
      this.methodMap = new LinkedHashMap<>(methods.size());

      for (HxMethod method : methods) {
         final Collection<HxMethod> methodsByName =
            this.methodMap.computeIfAbsent(
               method.getName(),
               (key) -> new LinkedHashSet<>()
            );

         if (!methodsByName.add(method)) {
            throw new IllegalStateException("Ambiguous method: " + method);
         }

         method.setDeclaringMember(this);
         this.methods.add(method);
      }
      return this;
   }

   @Override
   public List<HxConstructor> getConstructors() {
      return constructors;
   }

   @Override
   public HxType setConstructors(List<HxConstructor> constructors) {
      if (isUninitialized(constructors)) {
         this.constructors = Collections.emptyList();
         return this;
      }

      this.constructors = new ArrayList<>(constructors.size());

      for (HxConstructor constructor : constructors) {
         if (!this.constructors.add(constructor)) {
            throw new IllegalStateException("Ambiguous constructor: " + constructor);
         }
         constructor.setDeclaringMember(this);
      }
      return this;
   }


   @Override
   public HxMethod getMethod(String name) {
      final Collection<HxMethod> collection = methodMap.get(name);

      if (collection != null) {
         if (collection.size() > 1) {
            throw new IllegalStateException("There are more than one method with name: " + name);
         }

         return collection
            .iterator()
            .next();
      }

      return null;
   }

   @Override
   public Collection<HxMethod> getMethods(String name) {
      return methodMap.computeIfAbsent(name, (key) -> new LinkedHashSet<>());
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public Collection<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
      List<HxField> result = new ArrayList<>();

      HxType current = this;

      while (current != null) {
         for (HxField field : current.getFields()) {
            if (predicate.test(field)) {
               result.add(field);
            }
         }

         if (!recursive) {
            break;
         }

         current = current.getSuperType();
      }

      return result;
   }

   @Override
   public Collection<HxMethod> methods(Predicate<HxMethod> predicate, boolean recursive) {
      List<HxMethod> result = new ArrayList<>();

      HxType current = this;

      while (current != null) {
         for (HxMethod method : current.getMethods()) {
            if (predicate.test(method)) {
               result.add(method);
            }
         }
         if (!recursive) {
            break;
         }
         current = current.getSuperType();
      }

      current = this;

      while (current != null) {
         for (HxType itf : current.getInterfaces()) {
            result.addAll(itf.methods(predicate, recursive));
         }
         if(!recursive) {
            break;
         }
         current = current.getSuperType();
      }

      return result;
   }

   @Override
   public Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate, boolean recursive) {
      List<HxConstructor> result = new ArrayList<>();

      HxType current = this;

      while (current != null) {
         for (HxConstructor constructor : current.getConstructors()) {
            if (predicate.test(constructor)) {
               result.add(constructor);
            }
         }

         if (!recursive) {
            break;
         }

         current = current.getSuperType();
      }

      return result;
   }

   @Override
   public Collection<HxType> interfaces(Predicate<HxType> predicate, boolean recursive) {
      List<HxType> result = new ArrayList<>();

      HxType current = this;

      while (current != null) {
         for (HxType interfaze : current.getInterfaces()) {
            if (predicate.test(interfaze)) {
               result.add(current);
            }
         }

         if (!recursive) {
            break;
         }

         current = current.getSuperType();
      }

      return result;
   }

   @Override
   public Collection<HxType> types(Predicate<HxType> predicate, boolean recursive) {
      List<HxType> result = new ArrayList<>();

      HxType current = this;

      while (current != null) {
         if (predicate.test(current)) {
            result.add(current);
         }

         if (!recursive) {
            break;
         }

         current = current.getSuperType();
      }

      return result;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public byte[] toByteArray() {
      final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

      writer.visit(getVersion().getCode(),
                   getModifiers(),
                   getName(),
                   getGenericSignature(),
                   getSuperType().getName(),
                   getInterfaces().toArray(new String[0]));

      for (HxAnnotation annotation : getAnnotations()) {
         //writer.visitAnnotation()
      }

      //Outer type
      if (getDeclaringMember() instanceof HxType) {
         HxType owner = getDeclaringMember();
         writer.visitOuterClass(owner.getName(), null, null);
      } else {
         if (getDeclaringMember() instanceof HxMethod) {
            HxMethod owner = getDeclaringMember();
            writer.visitOuterClass(((HxType) owner.getDeclaringMember()).getName(), owner.getName(),
                                   owner.toDescriptor());
         } else {
            HxConstructor owner = getDeclaringMember();
            writer.visitOuterClass(((HxType) owner.getDeclaringMember()).getName(), "<init>", owner.toDescriptor());
         }
      }

      //Inner types
      for (HxType innerType : getDeclaredTypes()) {
         HxType owner = innerType.getDeclaringMember();
         if (isMemberType() && owner != null) {
            writer.visitInnerClass(innerType.getName(), owner.getName(), innerType.getSimpleName(),
                                   innerType.getModifiers());
         }
         writer.visitInnerClass(innerType.getName(), null, innerType.getSimpleName(), innerType.getModifiers());
      }

      return writer.toByteArray();
   }
}
