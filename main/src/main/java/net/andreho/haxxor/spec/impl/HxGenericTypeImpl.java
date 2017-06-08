package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxGenericType;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 00:25.
 */
public class HxGenericTypeImpl implements HxGenericType {

  private List<HxTypeVariable> typeVariables = Collections.emptyList();
  private HxGeneric<?> superType;
  private List<HxGeneric<?>> interfaces = Collections.emptyList();

  @Override
  public List<HxTypeVariable> getTypeVariables() {
    return typeVariables;
  }

  public void setTypeVariables(final List<HxTypeVariable> typeVariables) {
    this.typeVariables = typeVariables;
  }

  @Override
  public HxGeneric<?> getSuperType() {
    return superType;
  }

  public void setSuperType(final HxGeneric<?> superType) {
    this.superType = superType;
  }

  @Override
  public List<HxGeneric<?>> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(final List<HxGeneric<?>> interfaces) {
    this.interfaces = interfaces;
  }

  public HxGenericTypeImpl initialize() {
    if(isUninitialized(typeVariables)) {
      typeVariables = new ArrayList<>();
    }
    if(isUninitialized(interfaces)) {
      interfaces = new ArrayList<>();
    }
    return this;
  }

  @Override
  public void interpret(final String signature) {

  }
}
