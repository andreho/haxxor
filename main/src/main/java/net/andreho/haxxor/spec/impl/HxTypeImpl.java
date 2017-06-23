package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxSourceInfo;
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
  protected HxSourceInfo sourceInfo;

  protected HxType superType;
  protected List<HxType> interfaces = Collections.emptyList();
  protected List<HxType> declaredTypes = Collections.emptyList();

  protected Optional<String> genericSignature = Optional.empty();

  protected List<HxField> fields = Collections.emptyList();
  protected Map<String, HxField> fieldMap = Collections.emptyMap();

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
          this.annotations = new LinkedHashMap<>();
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
      case INNER_TYPES: {
        if (isUninitialized(getInnerTypes())) {
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
  public Optional<HxSourceInfo> getSourceInfo() {
    HxSourceInfo sourceInfo = this.sourceInfo;
    return sourceInfo == null? Optional.empty() : Optional.of(sourceInfo);
  }

  @Override
  public HxType setSourceInfo(final HxSourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public Optional<String> getGenericSignature() {
    return genericSignature;
  }

  @Override
  public HxType setGenericSignature(String genericSignature) {
    if(genericSignature == null || genericSignature.isEmpty()) {
      this.genericSignature = Optional.empty();
    } else {
      this.genericSignature = Optional.of(genericSignature);
    }
    return this;
  }

  @Override
  public Optional<HxType> getSuperType() {
    return Optional.ofNullable(superType);
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
  public List<HxType> getInnerTypes() {
    return declaredTypes;
  }

  @Override
  public HxType setInnerTypes(List<HxType> declaredTypes) {
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

    final Collection<HxMethod> hxMethods = methodMap.get(method.getName());

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
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
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
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    current = this;

    if(!recursive) {
      return result;
    }

    while (current != null) {
      for (HxType itf : current.getInterfaces()) {
        result.addAll(itf.methods(predicate, recursive));
      }
      if (!recursive) {
        break;
      }
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    return result;
  }

  @Override
  public Collection<HxMethod> constructors(Predicate<HxMethod> predicate, boolean recursive) {
    List<HxMethod> result = new ArrayList<>();

    HxType current = this;

    while (current != null) {
      for (HxMethod constructor : current.getConstructors()) {
        if (predicate.test(constructor)) {
          result.add(constructor);
        }
      }
      if (!recursive) {
        break;
      }
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    return result;
  }

  @Override
  public Collection<HxType> interfaces(Predicate<HxType> predicate, boolean recursive) {
    List<HxType> result = new ArrayList<>();
    HxType current = this;
    while (current != null) {
      for (HxType itf : current.getInterfaces()) {
        if (predicate.test(itf)) {
          result.add(current);
        }
      }
      if (!recursive) {
        break;
      }

      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
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
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }
    return result;
  }

  @Override
  public byte[] toByteCode() {
    return getHaxxor().getTypeInterpreter().interpret(this);
  }
}
