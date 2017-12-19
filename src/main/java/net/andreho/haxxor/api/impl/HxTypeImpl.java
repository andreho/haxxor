package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxGenericType;
import net.andreho.haxxor.api.HxInitializablePart;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxSourceInfo;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.Version;
import net.andreho.haxxor.spi.HxVerificationException;
import net.andreho.haxxor.spi.HxVerificationResult;
import net.andreho.haxxor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static net.andreho.haxxor.api.HxConstants.EMPTY_ANNOTATED_ARRAY;
import static net.andreho.haxxor.utils.CommonUtils.asArray;
import static net.andreho.haxxor.utils.CommonUtils.concat;
import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxTypeImpl
  extends HxAbstractType
  implements HxType {

  protected Version version = Version.V1_7;
  protected HxSourceInfo sourceInfo;
  protected HxType supertype;
  protected HxAnnotated<?> annotatedSupertype;
  protected List<HxType> interfaces = Collections.emptyList();
  protected HxAnnotated<?>[] annotatedInterfaces = EMPTY_ANNOTATED_ARRAY;
  protected List<HxType> declaredTypes = Collections.emptyList();
  protected String genericSignature = null;

  protected List<HxField> fields = Collections.emptyList();
  protected Map<String, Object> fieldMap = Collections.emptyMap();

  protected List<HxMethod> methods = Collections.emptyList();
  protected Map<String, Collection<HxMethod>> methodMap = Collections.emptyMap();
  protected int state;

  public HxTypeImpl(Hx haxxor,
                    String name) {
    super(haxxor, name);
    this.supertype = haxxor.reference("java.lang.Object");
  }

  @Override
  public HxType initialize(HxInitializablePart part) {
    if (part == HxInitializablePart.ANNOTATIONS) {
      if (isUninitialized(this.annotations)) {
        this.annotations = new ArrayList<>();
      }
    } else if (part == HxInitializablePart.FIELDS) {
      if (isUninitialized(this.fields)) {
        this.fields = new ArrayList<>();
      }
    } else if (part == HxInitializablePart.METHODS) {
      if (isUninitialized(this.methods)) {
        this.methods = new ArrayList<>();
      }
    } else if (part == HxInitializablePart.INTERFACES) {
      if (isUninitialized(this.interfaces)) {
        this.interfaces = new ArrayList<>();
      }
    } else if (part == HxInitializablePart.INNER_TYPES) {
      if (isUninitialized(this.declaredTypes)) {
        this.declaredTypes = new ArrayList<>();
      }
    } else {
      throw new IllegalStateException("Unsupported part: " + part);
    }
    return this;
  }

  @Override
  public Version getVersion() {
    return version;
  }

  @Override
  public HxType setVersion(Version version) {
    this.version = Objects.requireNonNull(version, "Given classfile's version is null.");
    return this;
  }

  @Override
  public Optional<HxSourceInfo> getSourceInfo() {
    return Optional.ofNullable(sourceInfo);
  }

  @Override
  public HxType setSourceInfo(final HxSourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public Optional<HxGenericType> getGenericType() {
    return Optional.empty();
  }

  @Override
  public Optional<String> getGenericSignature() {
    return Optional.ofNullable(genericSignature);
  }

  @Override
  public HxType setGenericSignature(String genericSignature) {
    this.genericSignature = genericSignature;
    return this;
  }

  @Override
  public Optional<HxType> getSupertype() {
    return Optional.ofNullable(supertype);
  }

  @Override
  public HxType setSupertype(HxType supertype) {
    if (supertype == null) {
      if(!hasName("java.lang.Object")) {
        throw new IllegalArgumentException("Super type can't be null.");
      }
    } else if(supertype.isPrimitive() ||
              supertype.isArray()) { //|| supertype.isInterface() -> skipped, bcs this would load the class
      throw new IllegalArgumentException("Given type can't be used a superclass: " + supertype);
    }
    this.supertype = supertype;
    return this;
  }

  @Override
  public Optional<HxAnnotated<?>> getAnnotatedSupertype() {
    return Optional.ofNullable(this.annotatedSupertype);
  }

  @Override
  public HxType setAnnotatedSupertype(final HxAnnotated<?> annotated) {
    this.annotatedSupertype = annotated;
    return this;
  }

  @Override
  public List<HxType> getInterfaces() {
    return interfaces;
  }

  @Override
  public HxType clearInterfaces() {
    this.interfaces = Collections.emptyList();
    this.annotatedInterfaces = EMPTY_ANNOTATED_ARRAY;
    return this;
  }

  @Override
  public HxType setInterfaces(List<HxType> interfaces) {
    if (isUninitialized(interfaces) || interfaces.isEmpty()) {
      return clearInterfaces();
    }
    this.interfaces = interfaces;
    return this;
  }

  @Override
  public HxType setAnnotatedInterface(final int index,
                                      final HxAnnotated<?> annotated) {
    checkAccessIndexOfInterfaces(index);
    HxAnnotated<?>[] annotatedInterfaces = this.annotatedInterfaces;
    if(annotatedInterfaces.length <= index) {
      if(annotated == null) {
        return this;
      }
      this.annotatedInterfaces = annotatedInterfaces =
        Arrays.copyOf(annotatedInterfaces, index + 1);
    }
    annotatedInterfaces[index] = annotated;
    return this;
  }

  private void checkAccessIndexOfInterfaces(final int index) {
    if (index < 0 || index >= getInterfaces().size()) {
      throw new IndexOutOfBoundsException(
        "Given index must reference one of defined interfaces and must be less then: " +
        getInterfaces().size());
    }
  }

  @Override
  public Optional<HxAnnotated<?>> getAnnotatedInterface(final int index) {
    checkAccessIndexOfInterfaces(index);
    if (this.annotatedInterfaces.length <= index) {
      return Optional.empty();
    }
    return Optional.ofNullable(this.annotatedInterfaces[index]);
  }

  @Override
  public List<HxType> getInnerTypes() {
    return this.declaredTypes;
  }

  @Override
  public HxType setInnerTypes(List<HxType> declaredTypes) {
    if (isUninitialized(declaredTypes) || declaredTypes.isEmpty()) {
      declaredTypes = Collections.emptyList();
    }
    this.declaredTypes = declaredTypes;
    return this;
  }

  @Override
  protected HxField findFieldInternally(final String name,
                                        final String type) {
    if (isFieldCacheActivated()) {
      return findCachedField(name, type);
    }
    return super.findFieldInternally(name, type);
  }

  private HxField findCachedField(final String name,
                                  final String type) {
    HxField found = null;
    Object entry = this.fieldMap.get(name);

    if (entry instanceof HxField) {
      found = (HxField) entry;
      if (type != null && !found.hasType(type)) {
        found = null;
      }
    } else if(entry instanceof HxField[]) {
      HxField[] array = (HxField[]) entry;
      for (HxField field : array) {
        if (type == null || field.hasType(type)) {
          found = field;
          break;
        }
      }
    }
    return found;
  }

  private boolean areFieldsLoaded() {
    return (this.state & Internals.FIELDS_LOADED) != 0;
  }

  private void markLoadedFields() {
    this.state |= Internals.FIELDS_LOADED;
  }

  private void removeMarkForLoadedFields() {
    this.state &= ~Internals.FIELDS_LOADED;
  }

  @Override
  public HxType loadFields() {
    if (!areFieldsLoaded()) {
        markLoadedFields();
        getHaxxor().resolveFields(this);
    }
    return this;
  }

  @Override
  public HxType cacheFields() {
    loadFields();
    if (!isFieldCacheActivated()) {
      this.fieldMap = createFieldCache();
      for (HxField field : this.fields) {
        if (!addFieldIntoCache(field)) {
          complainAboutDuplicateField(field);
        }
      }
    }
    return this;
  }

  protected Map<String, Object> createFieldCache() {
    return new HashMap<>(this.fields.size());
  }

  public List<HxField> getEditableFields() {
    return this.fields;
  }

  @Override
  public List<HxField> getFields() {
    return getLoadedFields();
  }

  private List<HxField> getLoadedFields() {
    if(!areFieldsLoaded()) {
      loadFields();
    }
    return this.fields;
  }

  @Override
  public HxType clearFields() {
    for(HxField field : getLoadedFields()) {
      field.setDeclaringMember(null);
    }

    this.fields = Collections.emptyList();
    this.fieldMap = Collections.emptyMap();

    return this;
  }

  @Override
  public HxType setFields(List<HxField> fields) {
    if (isUninitialized(fields) || fields.isEmpty()) {
      return clearFields();
    }

    for (int i = getLoadedFields().size() - 1; i >= 0; i--) {
      removeField(this.fields.get(i));
    }

    expectEmptyFields();

    for (HxField field : fields) {
      addField(field);
    }
    return this;
  }

  @Override
  public HxType addFieldAt(int index,
                           HxField field) {
    if (field.getDeclaringMember() != null) {
      if(equals(field.getDeclaringMember())) {
        complainAboutDuplicateField(field);
      }
      field = field.clone();
    }

    initialize(HxInitializablePart.FIELDS);

    if (!addFieldInternally(getLoadedFields(), index, field)) {
      complainAboutDuplicateField(field);
    }
    field.setDeclaringMember(this);
    return this;
  }

  protected boolean addFieldInternally(final List<HxField> fields,
                                       final int index,
                                       final HxField given) {
    if (isFieldCacheActivated() &&
        !addFieldIntoCache(given)) {
      return false;
    }
    fields.add(index, given);
    return true;
  }

  private boolean isFieldCacheActivated() {
    return !isUninitialized(this.fieldMap);
  }

  private boolean addFieldIntoCache(final HxField given) {
    final Map<String, Object> fieldMap = this.fieldMap;
    final Object entry = fieldMap.get(given.getName());

    if (entry instanceof HxField) {
      if (given.equals(entry)) {
        return false;
      }
      fieldMap.put(given.getName(), asArray(entry, given));
    } else if (entry instanceof HxField[]) {
      HxField[] array = (HxField[]) entry;
      for (HxField field : array) {
        if (given.equals(field)) {
          return false;
        }
        fieldMap.put(given.getName(), concat(array, given));
      }
    } else {
      return fieldMap.putIfAbsent(given.getName(), given) == null;
    }
    return true;
  }

  private void complainAboutDuplicateField(final HxField field) {
    throw new IllegalArgumentException("Current class '" + getName() + "' already contains the given field: " + field);
  }

  private void expectEmptyFields() {
    if (!this.fields.isEmpty() || !this.fieldMap.isEmpty()) {
      throw new IllegalStateException("Setting fields to given value lead to an inconsistent state.");
    }
  }

  private boolean removeFieldInternally(HxField field) {
    if (!isFieldCacheActivated() ||
        removedCachedField(field)) {
      int index = indexOfField(field);
      if (index < 0) {
        throw new IllegalStateException("Removal of the given field led to an inconsistent state: " + field);
      }
      getLoadedFields().remove(index);
      return true;
    }
    return false;
  }

  private boolean removedCachedField(final HxField given) {
    final Map<String, Object> fieldMap = this.fieldMap;
    final Object entry = fieldMap.get(given.getName());

    if (entry instanceof HxField) {
      if (given.equals(entry)) {
        fieldMap.remove(given.getName());
        return true;
      }
    } else if(entry instanceof HxField[]) {
      int found = -1;
      HxField[] array = (HxField[]) entry;

      for (int i = 0; i < array.length; i++) {
        if (given.equals(array[i])) {
          found = i;
          break;
        }
      }

      if (found > -1) {
        if (array.length > 1) {
          fieldMap.put(given.getName(), CommonUtils.copyWithout(array, found));
        } else {
          fieldMap.remove(given.getName());
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public HxType removeField(HxField field) {
    if (!equals(field.getDeclaringMember())) {
      throw new IllegalArgumentException("Given field must exist within this type: " + field);
    }
    if (!removeFieldInternally(field)) {
      throw new IllegalArgumentException("Given field must exist within this type: " + field);
    }
    field.setDeclaringMember(null);
    return this;
  }

  @Override
  public Optional<HxMethod> findMethod(final Optional<HxType> returnType,
                                       final String name,
                                       final List<HxType> parameters) {
    final Iterable<HxMethod> methods = isMethodCacheActivated() ?
                                       getNamedMethodsPartition(name) :
                                       getMethods();
    for (HxMethod method : methods) {
      if (method.hasName(name) &&
          method.hasParameters(parameters) &&
          (!returnType.isPresent() || Objects.equals(returnType.get(), method.getReturnType()))) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  private boolean areMethodsLoaded() {
    return (this.state & Internals.METHODS_LOADED) != 0;
  }

  private void markLoadedMethods() {
    this.state |= Internals.METHODS_LOADED;
  }

  private void removeMarkForLoadedMethods() {
    this.state &= ~Internals.METHODS_LOADED;
  }

  @Override
  public HxType loadFieldsAndMethods() {
    int bits = 0;
    if(!areFieldsLoaded()) {
      bits |= 1;
    }
    if(!areMethodsLoaded()) {
      bits |= 2;
    }
    switch (bits) {
      case 1: loadFields(); break;
      case 2: loadMethods(); break;
      case 3:
        markLoadedFields();
        markLoadedMethods();
        getHaxxor().resolveFieldsAndMethods(this);
      break;
    }
    return this;
  }

  @Override
  public HxType loadMethods() {
    if (!areMethodsLoaded()) {
        markLoadedMethods();
        getHaxxor().resolveMethods(this);
    }
    return this;
  }

  @Override
  public HxType cacheMethods() {
    loadMethods();
    if (!isMethodCacheActivated()) {
      this.methodMap = createMethodCache();

      for (HxMethod method : this.methods) {
        Collection<HxMethod> namedPartition = getNamedMethodsPartition(method.getName());
        //Only the small named partition must be analysed for the existence of the current method
        if (hasMethod(method.getReturnType(), method.getName(), method.getParameterTypes())) {
          complainAboutDuplicateMethod(method);
        }
        namedPartition.add(method);
      }
    }
    return this;
  }

  protected Map<String, Collection<HxMethod>> createMethodCache() {
    return new HashMap<>(this.methods.size());
  }

  private void complainAboutDuplicateMethod(final HxMethod method) {
    throw new IllegalArgumentException(
      "Current class '" + getName() + "' already contains the given method: " + method);
  }

  @Override
  public List<HxMethod> getMethods() {
    return getLoadedMethods();
  }

  private List<HxMethod> getLoadedMethods() {
    if(isUninitialized(this.methods)) {
      loadMethods();
    }
    return this.methods;
  }

  @Override
  public HxType clearMethods() {
    for(HxMethod method : getLoadedMethods()) {
      method.setDeclaringMember(null);
    }

    this.methods = Collections.emptyList();
    this.methodMap = Collections.emptyMap();

    return this;
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    if (isMethodCacheActivated()) {
      return Collections.unmodifiableCollection(getNamedMethodsPartition(name));
    }
    final List<HxMethod> foundMethods = new ArrayList<>();
    for (HxMethod method : getLoadedMethods()) {
      if (method.hasName(name)) {
        foundMethods.add(method);
      }
    }
    return foundMethods;
  }

  private Collection<HxMethod> getNamedMethodsPartition(final String name) {
    return this.methodMap.computeIfAbsent(name, (key) -> new ArrayList<>());
  }

  @Override
  public HxType addMethodAt(final int index,
                            HxMethod method) {

    if (method.getDeclaringMember() != null) {
      if (equals(method.getDeclaringMember())) {
        throw new IllegalStateException("Method was already added: " + method);
      }
      method = method.clone();
    }
    return addMethodInto(getLoadedMethods(), index, method);
  }

  private HxType addMethodInto(List<HxMethod> methods, int index, HxMethod method) {
    if (isMethodCacheActivated()) {
      //Duplicate detection is here
      Collection<HxMethod> partition = getNamedMethodsPartition(method.getName());
      if (partition.contains(method) || !partition.add(method)) {
        throw new IllegalStateException("Ambiguous method: " + method);
      }
    }
    methods.add(index, method);
    method.setDeclaringMember(this);
    return this;
  }

  private boolean isMethodCacheActivated() {
    return !isUninitialized(this.methodMap);
  }

  @Override
  public HxType setMethods(List<HxMethod> methods) {
    if (isUninitialized(methods) || methods.isEmpty()) {
      return clearMethods();
    }

    for (int i = getLoadedMethods().size() - 1; i >= 0; i--) {
      removeMethod(this.methods.get(i));
    }

    expectEmptyMethods();

    for (HxMethod method : methods) {
      addMethod(method);
    }

    return this;
  }

  private void expectEmptyMethods() {
    if (!this.methods.isEmpty() || !this.methodMap.isEmpty()) {
      throw new IllegalStateException("Setting methods to given value lead to an inconsistent state.");
    }
  }

  @Override
  public HxType removeMethod(HxMethod method) {
    if (!equals(method.getDeclaringMember())) {
      throw new IllegalArgumentException("Given method must exist within this type: " + method);
    }
    if(!removeCachedMethod(method)) {
      throw new IllegalArgumentException("Given method must exist within this type: " + method);
    }
    if(removeStoredMethod(method)) {
      throw new IllegalStateException("Removal of given method led to an inconsistent state: " + method);
    }
    method.setDeclaringMember(null);
    return this;
  }

  private boolean removeStoredMethod(final HxMethod method) {
    int index = indexOfMethod(method);
    if (index < 0) {
      return false;
    }
    this.methods.remove(index);
    return true;
  }

  private boolean removeCachedMethod(final HxMethod method) {
    if (isMethodCacheActivated()) {
      final Collection<HxMethod> namedPartition = this.methodMap.getOrDefault(method.getName(), Collections.emptyList());
      if (!hasMethod(method.getReturnType(), method.getName(), method.getParameterTypes()) ||
          !namedPartition.remove(method)) {
        return false;
      }
      if (namedPartition.isEmpty()) {
        this.methodMap.remove(method.getName());
      }
    }
    return true;
  }

  @Override
  public int indexOfMethod(final HxMethod method) {
    if (!equals(method.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxMethod hxMethod : getLoadedMethods()) {
      if (method == hxMethod ||
          method.equals(hxMethod)) {
        return idx;
      }
      idx++;
    }
    return -1;
  }

  @Override
  public int indexOfField(final HxField field) {
    if (!equals(field.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxField hxField : getLoadedFields()) {
      if (field == hxField ||
          field.equals(hxField)) {
        return idx;
      }
      idx++;
    }
    return -1;
  }

  @Override
  public List<HxField> fields(Predicate<HxField> predicate,
                              boolean recursive) {
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
      if (!current.hasSupertype()) {
        break;
      }
      current = current.getSupertype().orElse(null);
    }

    return result;
  }

  @Override
  public List<HxMethod> methods(final Predicate<HxMethod> predicate,
                                final boolean recursive) {
    final List<HxMethod> result = new ArrayList<>();
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
      if (!current.hasSupertype()) {
        break;
      }
      current = current.getSupertype().orElse(null);
    }

    current = this;

    if (!recursive) {
      return result;
    }

    //Any interface has java/lang/Object as supertype
    while (current != null && current.hasSupertype()) {
      for (HxType itf : current.getInterfaces()) {
        result.addAll(itf.methods(predicate, recursive));
      }
      current = current.getSupertype().orElse(null);
    }

    return result;
  }

  @Override
  public List<HxMethod> constructors(final Predicate<HxMethod> predicate,
                                     final boolean recursive) {
    final List<HxMethod> result = new ArrayList<>();
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
      if (!current.hasSupertype()) {
        break;
      }
      current = current.getSupertype().orElse(null);
    }

    return result;
  }

  @Override
  public byte[] toByteCode() {
    final Hx haxxor = getHaxxor();
    final HxVerificationResult result = haxxor.verify(this);
    if (result.isFailed()) {
      throw new HxVerificationException(result);
    }
    return haxxor.serialize(this);
  }
}
