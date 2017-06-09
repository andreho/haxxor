package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static net.andreho.haxxor.Utils.isUninitialized;


/**
 * <br/>Created by andreho on 3/26/16 at 10:16 PM.<br/>
 */
public class HxTypeVariableImpl
    extends HxAbstractGeneric<HxTypeVariable>
    implements HxTypeVariable {

  private String name;
  private HxMember declaring;
  private HxGeneric<?> classBound;
  private List<HxGeneric<?>> interfaceBounds = Collections.emptyList();

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxTypeVariableImpl setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public HxGeneric<?> getClassBound() {
    return classBound;
  }

  @Override
  public HxTypeVariableImpl setClassBound(final HxGeneric<?> classBound) {
    this.classBound = minimize(classBound);
    return this;
  }

  @Override
  public List<HxGeneric<?>> getInterfaceBounds() {
    return interfaceBounds;
  }

  @Override
  public HxTypeVariableImpl setInterfaceBounds(List<HxGeneric<?>> interfaceBounds) {
    this.interfaceBounds = interfaceBounds;
    return this;
  }

  @Override
  public HxTypeVariable addInterfaceBound(final HxGeneric<?> interfaceBound) {
    if(isUninitialized(interfaceBounds)) {
      setInterfaceBounds(new ArrayList<>(1));
    }
    getInterfaceBounds().add(minimize(interfaceBound));
    return this;
  }

  @Override
  public HxMember getGenericDeclaration() {
    return declaring;
  }

  @Override
  public HxTypeVariableImpl setGenericDeclaration(HxMember genericDeclaration) {
    this.declaring = genericDeclaration;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(getName());
    final Iterator<HxGeneric<?>> iterator = getInterfaceBounds().iterator();
    final boolean hasClassBound = getClassBound() != null;
    final boolean hasInterfaceBounds = iterator.hasNext();

    if(hasClassBound) {
      builder.append(" extends ").append(getClassBound());
    }
    if(hasInterfaceBounds) {
      if(!hasClassBound) {
        builder.append(" extends ");
      } else {
        builder.append(" & ");
      }
      builder.append(iterator.next());
      while(iterator.hasNext()) {
        builder.append(" & ").append(iterator.next());
      }
    }
    return builder.toString();
  }
}
