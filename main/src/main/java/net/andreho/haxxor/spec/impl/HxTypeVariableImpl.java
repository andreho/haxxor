package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by andreho on 3/26/16 at 10:16 PM.<br/>
 */
public class HxTypeVariableImpl
    extends HxAbstractGeneric
    implements HxTypeVariable {

  private String name;
  private HxMember declaring;
  private List<HxGeneric> bounds = Collections.emptyList();

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxTypeVariable setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public List<HxGeneric> getBounds() {
    return bounds;
  }

  @Override
  public HxTypeVariable setBounds(List<HxGeneric> bounds) {
    this.bounds = bounds;
    return this;
  }

  @Override
  public HxMember getGenericDeclaration() {
    return declaring;
  }

  @Override
  public HxTypeVariable setGenericDeclaration(HxMember genericDeclaration) {
    this.declaring = genericDeclaration;
    return this;
  }

  @Override
  public HxTypeVariable attach(final HxGeneric generic) {
    getBounds().add(generic);
    return this;
  }
}
