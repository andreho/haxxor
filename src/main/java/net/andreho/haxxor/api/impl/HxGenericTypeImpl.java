package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxGenericElement;
import net.andreho.haxxor.api.HxGenericType;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeVariable;
import net.andreho.haxxor.spi.impl.visitors.HxGenericSignatureReader;
import net.andreho.haxxor.spi.impl.visitors.HxGenericSignatureVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static net.andreho.haxxor.api.HxConstants.JAVA_LANG_OBJECT;
import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 00:25.
 */
public class HxGenericTypeImpl extends HxAbstractInterpretable implements HxGenericType {

  private final HxType declaringType;
  private HxGenericElement<?> superType;
  private List<HxTypeVariable> typeVariables = Collections.emptyList();
  private List<HxGenericElement<?>> interfaces = Collections.emptyList();

  public HxGenericTypeImpl(HxType owner, String signature) {
    this.declaringType = requireNonNull(owner);
    interpret(signature);
  }

  public HxGenericTypeImpl initialize() {
    if(isUninitialized(typeVariables)) {
      this.typeVariables = new ArrayList<>();
    }
    if(isUninitialized(interfaces)) {
      this.interfaces = new ArrayList<>();
    }
    return this;
  }

  @Override
  protected void interpret(final String signature) {
    final HxGenericSignatureReader reader = new HxGenericSignatureReader(signature);
    final HxGenericSignatureVisitor visitor = new HxGenericSignatureVisitor(this);
    reader.accept(visitor);
  }

  @Override
  public Hx getHaxxor() {
    return getDeclaringType().getHaxxor();
  }

  @Override
  public HxType getDeclaringType() {
    return declaringType;
  }

  @Override
  public Optional<HxTypeVariable> getVariable(final String name) {
    for(HxTypeVariable variable : getTypeVariables()) {
      if(name.equals(variable.getName())) {
        return Optional.of(variable);
      }
    }
    return Optional.empty();
  }

  @Override
  public List<HxTypeVariable> getTypeVariables() {
    return typeVariables;
  }

  public HxGenericTypeImpl setTypeVariables(final List<HxTypeVariable> typeVariables) {
    this.typeVariables = requireNonNull(typeVariables);
    return this;
  }

  @Override
  public HxGenericElement<?> getSuperType() {
    return superType;
  }

  public HxGenericTypeImpl setSuperType(final HxGenericElement<?> superType) {
    this.superType = superType;
    return this;
  }

  @Override
  public List<HxGenericElement<?>> getInterfaces() {
    return interfaces;
  }

  public HxGenericTypeImpl setInterfaces(final List<HxGenericElement<?>> interfaces) {
    this.interfaces = requireNonNull(interfaces);
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    printTypeVariables(builder);
    printExtends(builder);
    printImplements(builder);
    return builder.toString();
  }

  private void printTypeVariables(final StringBuilder builder) {
    Iterator<HxTypeVariable> variableIterator = getTypeVariables().iterator();
    if(variableIterator.hasNext()) {
      builder.append('<').append(variableIterator.next());
      while(variableIterator.hasNext()) {
        builder.append(',').append(variableIterator.next());
      }
      builder.append('>');
    }
  }
  private void printExtends(final StringBuilder builder) {
    if(getSuperType() != null && !JAVA_LANG_OBJECT.equals(getSuperType().toString())) {
      builder.append(" extends ").append(getSuperType());
    }
  }
  private void printImplements(final StringBuilder builder) {
    Iterator<HxGenericElement<?>> interfaceIterator = getInterfaces().iterator();
    if(interfaceIterator.hasNext()) {
      builder.append(" implements ").append(interfaceIterator.next());
      while(interfaceIterator.hasNext()) {
        builder.append(',').append(interfaceIterator.next());
      }
      builder.append('>');
    }
  }
}
