package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static net.andreho.haxxor.cgen.HxLocalVariable.createLocalVariable;

/**
 * <br/>Created by a.hofmann on 03.07.2017 at 14:15.
 */
public class AspectMethodContextImpl implements AspectMethodContext {

  private LABEL start;
  private LABEL end;
  private LABEL delegation;
  private HxMethod originalMethod;
  private HxMethod shadowMethod;
  private int nextSlotIndex;
  private Map<String, AspectLocalAttribute> localAttributes = Collections.emptyMap();

  @Override
  public LABEL getStart() {
    return start;
  }

  @Override
  public void setStart(final LABEL start) {
    this.start = requireNonNull(start);
  }

  @Override
  public LABEL getDelegation() {
    return delegation;
  }

  @Override
  public void setDelegation(final LABEL delegation) {
    this.delegation = requireNonNull(delegation);
  }

  @Override
  public LABEL getEnd() {
    return end;
  }

  @Override
  public void setEnd(final LABEL end) {
    this.end = requireNonNull(end);
  }

  @Override
  public HxMethod getOriginalMethod() {
    return originalMethod;
  }

  @Override
  public void setOriginalMethod(final HxMethod originalMethod) {
    this.originalMethod = originalMethod;
    this.nextSlotIndex =
      originalMethod.getParametersSlotSize() + (originalMethod.isStatic()? 0 : 1);
  }

  @Override
  public HxMethod getShadowMethod() {
    return shadowMethod;
  }

  @Override
  public void setShadowMethod(final HxMethod shadowMethod) {
    this.shadowMethod = requireNonNull(shadowMethod);
  }

  @Override
  public boolean hasLocalAttribute(final String name) {
    return getLocalAttribute(name) != null;
  }

  @Override
  public AspectLocalAttribute getLocalAttribute(final String name) {
    return getLocalAttributes().get(name);
  }

  @Override
  public AspectLocalAttribute createLocalAttribute(final HxType type,
                                                   final HxLocalVariable variable) {
    return addLocalAttribute(new AspectLocalAttributeImpl(variable, type));
  }

  @Override
  public AspectLocalAttribute createLocalAttribute(final HxType type,
                                                   final String name,
                                                   final int index) {
    return createLocalAttribute(type,
                                createLocalVariable(
                                  index, name, getStart(), getEnd(), type.toDescriptor(), null));
  }

  private AspectLocalAttribute addLocalAttribute(AspectLocalAttribute variable) {
    Map<String, AspectLocalAttribute> localAttributes = this.localAttributes;

    if(localAttributes == Collections.EMPTY_MAP) {
      this.localAttributes = localAttributes = new LinkedHashMap<>();
    }

    this.nextSlotIndex =
      shiftHigherSlots(variable.getIndex(), variable.getType().getSlotSize());

    if(null != localAttributes.putIfAbsent(variable.getName(), variable)) {
      throw new IllegalStateException("Local-Aspect-Attribute was already defined: " + variable.getName());
    }
    return variable;
  }

  @Override
  public Map<String, AspectLocalAttribute> getLocalAttributes() {
    return localAttributes;
  }

  private int shiftHigherSlots(final int from,
                               final int delta) {
    int maxSlotIndex = this.nextSlotIndex;
    for(AspectLocalAttribute attribute : getLocalAttributes().values()) {
      final HxLocalVariable localVariable = attribute.getLocalVariable();

      if(localVariable.getIndex() >= from) {
        localVariable.shift(delta);

        int reachedMax = localVariable.getIndex() + attribute.getType().getSlotSize();
        if(reachedMax > maxSlotIndex) {
          maxSlotIndex = reachedMax;
        }
      }
    }
    return maxSlotIndex;
  }

  @Override
  public int getNextSlotIndex() {
    return nextSlotIndex;
  }

  @Override
  public void reset() {
    this.nextSlotIndex = 0;
    this.start = this.end = null;
    this.shadowMethod = this.originalMethod = null;

    if(!this.localAttributes.isEmpty()) {
      this.localAttributes.clear();
    }
  }
}
