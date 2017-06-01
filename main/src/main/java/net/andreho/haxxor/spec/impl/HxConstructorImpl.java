package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collections;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxConstructorImpl
    extends HxParameterizableImpl<HxConstructor>
    implements HxConstructor {

  public HxConstructorImpl() {
    super();
  }

  public HxConstructorImpl(HxType... parameterTypes) {
    this(null, (HxType[]) parameterTypes);
  }

  public HxConstructorImpl(HxType declaringType, HxType... parameterTypes) {
    super();
    setModifiers(HxConstructor.Modifiers.PUBLIC.toBit());
    setDeclaringMember(declaringType);
    setParameterTypes(parameterTypes);
  }

  public HxConstructorImpl(HxConstructorImpl prototype) {
    this.declaringMember = null;

    this.genericSignature = prototype.genericSignature;
    this.exceptions = isUninitialized(prototype.exceptions) ? Collections.emptyList() : new ArrayList<>(exceptions);
    this.parameters = isUninitialized(prototype.parameters) ? Collections.emptyList() : new ArrayList<>();

    prototype.cloneParametersTo(this);
    prototype.cloneAnnotationsTo(this);
  }

  @Override
  public HxConstructor clone() {
    return new HxConstructorImpl(this);
  }

  @Override
  public String toString() {
    return getDeclaringMember() + super.toString() + "void";
  }
}
