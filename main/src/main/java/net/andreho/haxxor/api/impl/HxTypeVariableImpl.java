package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxGenericElement;
import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxTypeVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;


/**
 * <br/>Created by andreho on 3/26/16 at 10:16 PM.<br/>
 */
public class HxTypeVariableImpl
    extends HxAbstractGeneric<HxTypeVariable>
    implements HxTypeVariable {

  private String name;
  private HxMember declaring;
  private HxGenericElement<?> classBound;
  private List<HxGenericElement<?>> interfaceBounds = Collections.emptyList();

  public HxTypeVariableImpl() {
  }

  @Override
  public String getName() {
    return name;
  }

  public HxTypeVariableImpl setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public HxGenericElement<?> getClassBound() {
    return classBound;
  }

  public HxTypeVariableImpl setClassBound(final HxGenericElement<?> classBound) {
    this.classBound = minimize(classBound);
    return this;
  }

  @Override
  public List<HxGenericElement<?>> getInterfaceBounds() {
    return interfaceBounds;
  }

  public HxTypeVariableImpl setInterfaceBounds(List<HxGenericElement<?>> interfaceBounds) {
    this.interfaceBounds = interfaceBounds;
    return this;
  }

  public HxTypeVariable addInterfaceBound(final HxGenericElement<?> interfaceBound) {
    if (isUninitialized(interfaceBounds)) {
      setInterfaceBounds(new ArrayList<>(1));
    }
    getInterfaceBounds().add(minimize(interfaceBound));
    return this;
  }

  @Override
  public HxMember getGenericDeclaration() {
    return declaring;
  }

  public HxTypeVariableImpl setGenericDeclaration(HxMember genericDeclaration) {
    this.declaring = genericDeclaration;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(getName());
    final Iterator<HxGenericElement<?>> iterator = getInterfaceBounds().iterator();
    final boolean hasClassBound = getClassBound() != null && !"java.lang.Object".equals(getClassBound().toString());
    final boolean hasInterfaceBounds = iterator.hasNext();

    if (hasClassBound) {
      builder.append(" extends ")
             .append(getClassBound());
    }
    if (hasInterfaceBounds) {
      if (!hasClassBound) {
        builder.append(" extends ");
      } else {
        builder.append(" & ");
      }
      builder.append(iterator.next());
      while (iterator.hasNext()) {
        builder.append(" & ")
               .append(iterator.next());
      }
    }
    return builder.toString();
  }
}
