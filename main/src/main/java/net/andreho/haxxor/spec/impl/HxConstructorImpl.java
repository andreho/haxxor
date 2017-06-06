package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxConstructorImpl
    extends HxParameterizableImpl<HxConstructor>
    implements HxConstructor {

  private final Haxxor haxxor;

  public HxConstructorImpl(Haxxor haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor, "Associated haxxor instance can't be null.");
  }

  public HxConstructorImpl(Haxxor haxxor, HxType... parameterTypes) {
    this(haxxor);
    setModifiers(HxConstructor.Modifiers.PUBLIC.toBit());
    setParameterTypes(parameterTypes);
  }

  public HxConstructorImpl(HxConstructorImpl prototype) {
    this.declaringMember = null;
    this.haxxor = prototype.haxxor;
    this.genericSignature = prototype.genericSignature;

    this.exceptions = isUninitialized(prototype.exceptions) ? Collections.emptyList() : new ArrayList<>(exceptions);
    this.parameters = isUninitialized(prototype.parameters) ? Collections.emptyList() : new ArrayList<>();

    prototype.cloneParametersTo(this);
    prototype.cloneAnnotationsTo(this);
  }

  @Override
  public Haxxor getHaxxor() {
    return haxxor;
  }

  @Override
  public HxConstructor clone() {
    return new HxConstructorImpl(this);
  }

  @Override
  public String toString() {
    return ((getDeclaringMember() == null)? "undefined" :  getDeclaringMember()) + super.toString();
  }
}
