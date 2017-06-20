package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectApplicationContext;
import net.andreho.aop.spi.AspectAttribute;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 07:15.
 */
public class AspectApplicationContextImpl implements AspectApplicationContext {

  private final AspectDefinition aspectDefinition;
  private final Map<String, AspectAttribute> localAttributes;

  public AspectApplicationContextImpl(final AspectDefinition aspectDefinition) {
    this.aspectDefinition = aspectDefinition;
    this.localAttributes = new LinkedHashMap<>(0);
  }


  private int shiftBy(final int from,
                     final int delta) {
    int count = 0;
    for(AspectAttribute attribute : getLocalAttributes().values()) {
      if(attribute.getIndex() >= from) {
        attribute.shift(delta);
        count++;
      }
    }
    return count;
  }

  @Override
  public void enterType(final HxType type) {

  }

  @Override
  public void enterField(final HxField field) {

  }

  @Override
  public void enterMethod(final HxMethod method) {
    getLocalAttributes().clear();
  }

  @Override
  public void enterConstructor(final HxMethod constructor) {
    getLocalAttributes().clear();
  }

  @Override
  public AspectAttribute createLocalAttribute(final String name,
                                              final HxType type,
                                              final int index) {
    shiftBy(index, type.getSlotsCount());
    final AspectAttribute aspectAttribute = new AspectAttributeImpl(name, type, index);

    if(null != getLocalAttributes().putIfAbsent(name, aspectAttribute)) {
      throw new IllegalStateException("Aspect-Attribute was already defined: " + name);
    }
    return aspectAttribute;
  }

  @Override
  public boolean hasLocalAttribute(final String name) {
    return getLocalAttributes().containsKey(name);
  }

  @Override
  public AspectAttribute getLocalAttribute(final String name) {
    return getLocalAttributes().get(name);
  }

  @Override
  public Map<String, AspectAttribute> getLocalAttributes() {
    return localAttributes;
  }

  @Override
  public AspectDefinition getAspectDefinition() {
    return aspectDefinition;
  }
}
