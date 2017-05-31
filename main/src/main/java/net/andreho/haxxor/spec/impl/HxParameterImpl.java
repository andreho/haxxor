package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxParameterizable;
import net.andreho.haxxor.spec.api.HxType;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxParameterImpl<P extends HxParameterizable<P>>
    extends HxAnnotatedImpl<HxParameter<P>>
    implements
    HxParameter<P> {

  private String name;
  private HxType type;

  public HxParameterImpl() {
  }

  public HxParameterImpl(HxParameterizable owner) {
    this.setDeclaringMember(owner);
  }

  public HxParameterImpl(HxParameterizable owner, HxType type) {
    this.setDeclaringMember(owner);
    this.setType(type);
  }

  @Override
  public Haxxor getHaxxor() {
    HxType type = getType();
    if (type != null) {
      return type.getHaxxor();
    }
    return null;
  }

  @Override
  public int getIndex() {
    P declaringMember = getDeclaringMember();
    if (declaringMember == null) {
      return -1;
    }
    return declaringMember.getParameters()
                          .indexOf(this);
  }

  @Override
  public String getName() {
    final String name = this.name;
    if (name != null) {
      return name;
    }
    int index = getIndex();
    return "arg" + (index > -1 ? index : "X");
  }

  @Override
  public HxParameter setName(final String name) {
    this.name = name;
    return this;
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public HxParameter setType(final HxType type) {
    this.type = type;
    return this;
  }

  @Override
  public P getDeclaringMember() {
    return super.getDeclaringMember();
  }
}
