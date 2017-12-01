package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxSourceInfo;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.InitializablePart;
import net.andreho.haxxor.api.Version;
import net.andreho.haxxor.spi.HxVerificationException;
import net.andreho.haxxor.spi.HxVerificationResult;
import net.andreho.haxxor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static net.andreho.haxxor.utils.CommonUtils.asArray;
import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxTypeImpl
    extends HxAbstractType
    implements HxType {

  protected Version version = Version.V1_7;
  protected HxSourceInfo sourceInfo;

  protected HxType superType;
  protected List<HxType> interfaces = Collections.emptyList();
  protected List<HxType> declaredTypes = Collections.emptyList();

  protected Optional<String> genericSignature = Optional.empty();

  protected List<HxField> fields = Collections.emptyList();
  protected Map<String, Object> fieldMap = Collections.emptyMap();

  protected List<HxMethod> methods = Collections.emptyList();
  protected Map<String, Collection<HxMethod>> methodMap = Collections.emptyMap();

  public HxTypeImpl(Hx haxxor, String name) {
    super(haxxor, name);
    this.superType = haxxor.reference("java.lang.Object");
  }

  @Override
  public HxType initialize(InitializablePart part) {
    switch (part) {
      case ANNOTATIONS: {
        if (isUninitialized(getAnnotations())) {
          this.annotations = new LinkedHashMap<>();
        }
      }
      break;
      case FIELDS: {
        if (isUninitialized(getFields())) {
          this.fields = new ArrayList<>();
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
    return Optional.ofNullable(sourceInfo);
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
  protected HxField findFieldInternally(final String name, final HxType fieldType) {
    if(isUninitialized(this.fieldMap)) {
      return findCachedField(name, fieldType);
    }
    return super.findFieldInternally(name, fieldType);
  }

  private HxField findCachedField(final String name,
                                  final HxType fieldType) {
    HxField found = null;
    Object entry = this.fieldMap.get(name);

    if(entry instanceof HxField) {
      found = (HxField) entry;
      if(fieldType != null && !found.hasType(fieldType)) {
        found = null;
      }
    } else {
      HxField[] array = (HxField[]) entry;
      for(HxField field : array) {
        if(fieldType == null || field.hasType(fieldType)) {
          found = field;
          break;
        }
      }
    }
    return found;
  }

  @Override
  public HxType cacheFields() {
    HxType type = loadFields();
    if(isUninitialized(this.fieldMap)) {
      setUpFieldCache();

      for(HxField field : getFields()) {
        if(!addFieldIntoCache(field)) {
          complainAboutDuplicateField(field);
        }
      }
    }
    return type;
  }

  protected void setUpFieldCache() {
    this.fieldMap = new LinkedHashMap<>(getFields().size());
  }

  @Override
  public List<HxField> getFields() {
    return fields;
  }

  @Override
  public HxType setFields(final List<HxField> fields) {
    if (isUninitialized(fields) || fields.isEmpty()) {
      this.fields = Collections.emptyList();
      this.fieldMap = Collections.emptyMap();
      return this;
    }

    initialize(InitializablePart.FIELDS);

    for (int i = this.fields.size() - 1; i >= 0; i--) {
      removeField(this.fields.get(i));
    }

    expectEmptyFields();

    for (HxField field : fields) {
      addField(field);
    }

    return this;
  }

  protected boolean addFieldInternally(final int index,
                                       final HxField given) {
    if(!isUninitialized(this.fieldMap) &&
       !addFieldIntoCache(given)) {
      return false;
    }
    fields.add(index, given);
    return true;
  }

  private boolean addFieldIntoCache(final HxField given) {
    final Map<String, Object> fieldMap = this.fieldMap;
    final Object entry = fieldMap.get(given.getName());

    if(entry instanceof HxField) {
      if(given.equals(entry)) {
        return false;
      }

      fieldMap.put(given.getName(), asArray(entry, given));
    } else if(entry instanceof HxField[]) {

      HxField[] array = (HxField[]) entry;
      for(int i = 0; i < array.length; i++) {
        if(given.equals(array[i])) {
          return false;
        }
        fieldMap.put(given.getName(), CommonUtils.concat(array, given));
      }
    }
    return fieldMap.putIfAbsent(given.getName(), given) == null;
  }

  @Override
  public HxType addFieldAt(int index,
                           HxField field) {
    if (field.getDeclaringMember() != null && !equals(field.getDeclaringMember())) {
      field = field.clone();
    }

    initialize(InitializablePart.FIELDS);

    if (field.getDeclaringMember() != null ||
        !addFieldInternally(index, field)) {

      complainAboutDuplicateField(field);
    }
    field.setDeclaringMember(this);
    return this;
  }

  private void complainAboutDuplicateField(final HxField field) {
    throw new IllegalArgumentException("Current class '"+getName()+"' already contains the given field: " + field);
  }

  private void expectEmptyFields() {
    if(!this.fields.isEmpty() || !this.fieldMap.isEmpty()) {
      throw new IllegalStateException("Setting fields to given value lead to an inconsistent state.");
    }
  }

  private boolean removeFieldInternally(HxField field) {
    if(isUninitialized(this.fieldMap) ||
       removedCachedField(field)) {
      int index = indexOf(field);
      if(index > -1) {
        this.fields.remove(index);
      } else {
        throw new IllegalStateException("Removal of given field led to an inconsistent state: " + field);
      }
      return true;
    }
    return false;
  }

  private boolean removedCachedField(final HxField given) {
    final Map<String, Object> fieldMap = this.fieldMap;
    final Object entry = fieldMap.get(given.getName());

    if(entry instanceof HxField) {
      if(given.equals(entry)) {
        fieldMap.remove(given.getName());
        return true;
      }
    } else {
      int found = -1;
      HxField[] array = (HxField[]) entry;

      for(int i = 0; i < array.length; i++) {
        if(given.equals(array[i])) {
          found = i;
          break;
        }
      }

      if(found > -1) {
        if(array.length > 1) {
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
    initialize(InitializablePart.METHODS);
    return
      methodMap.computeIfAbsent(name, (key) -> new ArrayList<>());
  }

  @Override
  public HxType addMethodAt(final int index,
                            HxMethod method) {
    initialize(InitializablePart.METHODS);

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

    initialize(InitializablePart.METHODS);

    for (int i = this.methods.size() - 1; i >= 0; i--) {
      removeMethod(this.methods.get(i));
    }

    expectEmptyMethods();

    for (HxMethod method : methods) {
      addMethod(method);
    }

    return this;
  }

  private void expectEmptyMethods() {
    if(!this.methods.isEmpty() || !this.methodMap.isEmpty()) {
      throw new IllegalStateException("Setting methods to given value lead to an inconsistent state.");
    }
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
  public List<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
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
  public List<HxMethod> methods(final Predicate<HxMethod> predicate, final boolean recursive) {
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
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    current = this;

    if(!recursive) {
      return result;
    }

    //Any interface has java/lang/Object as supertype
    while (current.hasSuperType()) {
      for (HxType itf : current.getInterfaces()) {
        result.addAll(itf.methods(predicate, recursive));
      }
      if (!recursive) {
        break;
      }
      current = current.getSuperType().get();
    }

    return result;
  }

  @Override
  public List<HxMethod> constructors(final Predicate<HxMethod> predicate, final boolean recursive) {
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
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    return result;
  }

  @Override
  public List<HxType> interfaces(final Predicate<HxType> predicate, final boolean recursive) {
    final List<HxType> result = new ArrayList<>();
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
  public List<HxType> types(final Predicate<HxType> predicate, final boolean recursive) {
    final List<HxType> result = new ArrayList<>();
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
    final Hx haxxor = getHaxxor();
    final HxVerificationResult result = haxxor.verify(this);
    if(result.isFailed()) {
      throw new HxVerificationException(result);
    }
    return haxxor.serialize(this);
  }
}
