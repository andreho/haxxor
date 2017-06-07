package net.andreho.haxxor.spec.impl;

import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxTypeImpl
    extends HxAbstractType
    implements HxType {

  protected Version version = Version.V1_8;
  protected HxType superType;
  protected String genericSignature = "";
  protected List<HxType> interfaces = Collections.emptyList();
  protected List<HxType> declaredTypes = Collections.emptyList();

  protected List<HxField> fields = Collections.emptyList();
  protected Map<String, HxField> fieldMap = Collections.emptyMap();

  protected List<HxConstructor> constructors = Collections.emptyList();
  protected List<HxMethod> methods = Collections.emptyList();
  protected Map<String, Collection<HxMethod>> methodMap = Collections.emptyMap();

  public HxTypeImpl(Haxxor haxxor, String name) {
    super(haxxor, name);
    this.superType = haxxor.reference("java.lang.Object");
  }

  @Override
  public HxType initialize(Part part) {
    switch (part) {
      case DEFAULTS: {
        getHaxxor().getTypeInitializer().initialize(this);
      }
      break;
      case ANNOTATIONS: {
        if (isUninitialized(getAnnotations())) {
          this.annotations = new LinkedHashSet<>();
        }
      }
      break;
      case CONSTRUCTORS: {
        if (isUninitialized(getConstructors())) {
          this.constructors = new ArrayList<>();
        }
      }
      break;
      case FIELDS: {
        if (isUninitialized(getFields())) {
          this.fields = new ArrayList<>();
          this.fieldMap = new LinkedHashMap<>();
        }
      }
      break;
      case INTERFACES: {
        if (isUninitialized(getInterfaces())) {
          this.interfaces = new ArrayList<>();
        }
      }
      break;
      case METHODS: {
        if (isUninitialized(getMethods())) {
          this.methods = new ArrayList<>();
          this.methodMap = new LinkedHashMap<>();
        }
      }
      break;
      case DECLARED_TYPES: {
        if (isUninitialized(getDeclaredTypes())) {
          this.declaredTypes = new ArrayList<>();
        }
      }
      break;
      default:
        throw new IllegalStateException("Invalid state.");
    }

    return this;
  }

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
    if (superType == null && !"java.lang.Object".equals(getName())) {
      throw new IllegalArgumentException("Super type can't be null.");
    }

    this.superType = superType;
    return this;
  }

  @Override
  public List<HxType> getInterfaces() {
    return interfaces;
  }

  @Override
  public HxType setInterfaces(List<HxType> interfaces) {
    if (isUninitialized(interfaces)) {
      interfaces = Collections.emptyList();
    }

    this.interfaces = interfaces;
    return this;
  }

  @Override
  public List<HxType> getDeclaredTypes() {
    return declaredTypes;
  }

  @Override
  public HxType setDeclaredTypes(List<HxType> declaredTypes) {
    if (isUninitialized(declaredTypes)) {
      declaredTypes = Collections.emptyList();
    }

    this.declaredTypes = declaredTypes;
    return this;
  }

  @Override
  public Optional<HxField> findField(String name) {
    HxField hxField = fieldMap.get(name);
    if(hxField != null) {
      return Optional.of(hxField);
    }
    return Optional.empty();
  }

  @Override
  public List<HxField> getFields() {
    return fields;
  }

  @Override
  public HxType addFieldAt(int index,
                           HxField field) {
    if (field.getDeclaringMember() != null && !equals(field.getDeclaringMember())) {
      field = field.clone();
    }

    initialize(Part.FIELDS);

    if (field.getDeclaringMember() != null ||
        hasField(field.getName()) ||
        fieldMap.put(field.getName(), field) != null) {

      throw new IllegalArgumentException("Given field was already associated with this type: " + field);
    }

    fields.add(index, field);
    field.setDeclaringMember(this);

    return this;
  }

  @Override
  public HxType setFields(List<HxField> fields) {
    if (isUninitialized(fields)) {
      this.fields = Collections.emptyList();
      this.fieldMap = Collections.emptyMap();
      return this;
    }

    initialize(Part.FIELDS);

    for (int i = this.fields.size() - 1; i >= 0; i--) {
      removeField(this.fields.get(i));
    }

    if(!this.fields.isEmpty() || !this.fieldMap.isEmpty()) {
      throw new IllegalStateException("Setting fields to given value lead to an inconsistent state.");
    }

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
  public HxType removeField(HxField field) {
    if (!equals(field.getDeclaringMember())) {
      throw new IllegalArgumentException("Given field must exist within this type: " + field);
    }

    if (!hasField(field.getName()) || fieldMap.remove(field.getName()) != field) {
      throw new IllegalArgumentException("Given field must exist within this type: " + field);
    }

    int index = indexOf(field);
    if(index > -1) {
      fields.remove(index);
    } else {
      throw new IllegalStateException("Removal of given field led to an inconsistent state: " + field);
    }
    field.setDeclaringMember(null);

    return this;
  }

  @Override
  public Optional<HxMethod> findMethod(String name) {
    final Collection<HxMethod> collection = methodMap.get(name);

    if (collection != null && !collection.isEmpty()) {
      if (collection.size() != 1) {
        throw new IllegalStateException("There are more than one method with given name: " + name);
      } else {
        HxMethod hxMethod = collection
            .iterator()
            .next();
        return Optional.of(hxMethod);
      }
    }
    return Optional.empty();
  }

  @Override
  public List<HxMethod> getMethods() {
    return methods;
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    return methodMap.computeIfAbsent(name, (key) -> new LinkedHashSet<>());
  }

  @Override
  public HxType addMethodAt(final int index,
                            HxMethod method) {
    initialize(Part.METHODS);

    if (equals(method.getDeclaringMember())) {
      throw new IllegalStateException("Method was already added: " + method);
    } else if (method.getDeclaringMember() != null) {
      method = method.clone();
    }

    if (method.getDeclaringMember() != null) {
      throw new IllegalStateException("Ambiguous method: " + method);
    }
    if (!getMethods(method.getName()).add(method)) {
      throw new IllegalStateException("Ambiguous method: " + method);
    }
    if (!getMethods().add(method)) {
      throw new IllegalStateException("Ambiguous method: " + method);
    }

    method.setDeclaringMember(this);
    return this;
  }

  @Override
  public HxType setMethods(List<HxMethod> methods) {
    if (isUninitialized(methods)) {
      this.methods = Collections.emptyList();
      this.methodMap = Collections.emptyMap();
      return this;
    }

    initialize(Part.METHODS);

    for (int i = this.methods.size() - 1; i >= 0; i--) {
      removeMethod(this.methods.get(i));
    }

    if(!this.methods.isEmpty() || !this.methodMap.isEmpty()) {
      throw new IllegalStateException("Setting methods to given value lead to an inconsistent state.");
    }

    for (HxMethod method : methods) {
      addMethod(method);
    }

    return this;
  }

  @Override
  public HxType removeMethod(HxMethod method) {
    if (!equals(method.getDeclaringMember())) {
      throw new IllegalArgumentException("Given method must exist within this type: " + method);
    }

    Collection<HxMethod> hxMethods = methodMap.get(method.getName());
    if (!hasMethod(method.getReturnType(), method.getName(), method.getParameterTypes()) ||
        !hxMethods.remove(method)) {
      throw new IllegalArgumentException("Given method must exist within this type: " + method);
    }

    if(hxMethods.isEmpty()) {
      methodMap.remove(method.getName());
    }

    int index = indexOf(method);
    if(index > -1) {
      methods.remove(index);
    } else {
      throw new IllegalStateException("Removal of given method led to an inconsistent state: " + method);
    }
    method.setDeclaringMember(null);

    return this;
  }

  @Override
  public List<HxConstructor> getConstructors() {
    return constructors;
  }

  @Override
  public HxType addConstructorAt(final int index,
                                 HxConstructor constructor) {
    initialize(Part.CONSTRUCTORS);

    if (equals(constructor.getDeclaringMember())) {
      throw new IllegalStateException("Ambiguous constructor: " + constructor);
    } else if (constructor.getDeclaringMember() != null) {
      constructor = constructor.clone();
    }
    if (constructor.getDeclaringMember() != null || hasConstructor(constructor.getParameterTypes())) {
      throw new IllegalStateException("Ambiguous constructor: " + constructor);
    }

    constructors.add(index, constructor);
    constructor.setDeclaringMember(this);

    return super.addConstructorAt(index, constructor);
  }

  @Override
  public HxType setConstructors(List<HxConstructor> constructors) {
    if (isUninitialized(constructors)) {
      this.constructors = Collections.emptyList();
      return this;
    }

    initialize(Part.CONSTRUCTORS);

    for (int i = this.constructors.size() - 1; i >= 0; i--) {
      removeConstructor(this.constructors.get(i));
    }

    if(!this.constructors.isEmpty()) {
      throw new IllegalStateException("Setting constructors to given value lead to an inconsistent state.");
    }

    for (HxConstructor constructor : constructors) {
      addConstructor(constructor);
    }
    return this;
  }

  @Override
  public HxType removeConstructor(HxConstructor constructor) {
    if (!equals(constructor.getDeclaringMember())) {
      throw new IllegalArgumentException("Given constructor must exist within this type: " + constructor);
    }

    int index = indexOf(constructor);
    if(index > -1) {
      constructors.remove(index);
    } else {
      throw new IllegalStateException("Removal of given constructor led to an inconsistent state: " + constructor);
    }
    constructor.setDeclaringMember(null);

    return this;
  }

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
      if (!recursive) {
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
