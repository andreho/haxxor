package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.AspectTryCatch;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static net.andreho.haxxor.cgen.HxLocalVariable.createLocalVariable;

/**
 * <br/>Created by a.hofmann on 03.07.2017 at 14:15.
 */
public class AspectMethodContextImpl implements AspectMethodContext {

  private LABEL begin;
  private LABEL end;
  private LABEL delegationBegin;
  private LABEL delegationEnd;
  private LABEL catchInjection;
  private HxMethod originalMethod;
  private HxMethod shadowMethod;
  private int nextSlotIndex;
  private Map<String, AspectLocalAttribute> localAttributes = Collections.emptyMap();
  private Map<String, AspectTryCatch> tryCatchBlockMap = Collections.emptyMap();

  @Override
  public LABEL getBegin() {
    return begin;
  }

  @Override
  public void setBegin(final LABEL begin) {
    this.begin = requireNonNull(begin);
  }

  @Override
  public LABEL getDelegationBegin() {
    return delegationBegin;
  }

  @Override
  public void setDelegationBegin(final LABEL delegationBegin) {
    this.delegationBegin = requireNonNull(delegationBegin);
  }

  @Override
  public LABEL getDelegationEnd() {
    return delegationEnd;
  }

  @Override
  public void setDelegationEnd(final LABEL delegationEnd) {
    this.delegationEnd = requireNonNull(delegationEnd);
  }

  @Override
  public LABEL getCatchInjection() {
    return catchInjection;
  }

  @Override
  public void setCatchInjection(final LABEL catchInjection) {
    this.catchInjection = requireNonNull(catchInjection);
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
  public AspectLocalAttribute getResultLocalAttribute() {
    return getLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME);
  }

  @Override
  public boolean hasLocalAttribute(final String name) {
    return getLocalAttributes().get(name) != null;
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
                                  index, name, getBegin(), getEnd(), type.toDescriptor(), null));
  }

  @Override
  public AspectLocalAttribute createLocalAttribute(final HxType type,
                                                   final String name,
                                                   final int index,
                                                   final LABEL begin,
                                                   final LABEL end) {
    return createLocalAttribute(type,
                                createLocalVariable(
                                  index, name, begin, end, type.toDescriptor(), null));
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
      final HxLocalVariable localVariable = attribute.getHxLocalVariable();

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
  public boolean hasTryCatchBlock(final String exceptionType) {
    return tryCatchBlockMap.containsKey(exceptionType);
  }

  @Override
  public void createTryCatchBlock(final AspectTryCatch tryCatch) {
    Map<String, AspectTryCatch> tryCatchBlockMap = this.tryCatchBlockMap;
    if(tryCatchBlockMap == Collections.EMPTY_MAP) {
      this.tryCatchBlockMap = tryCatchBlockMap = new LinkedHashMap<>();
    }

    final String handledException = tryCatch.getExceptionType().getName();

    if(null != tryCatchBlockMap.putIfAbsent(handledException, tryCatch)) {
      throw new IllegalStateException("Try-Catch block was already added: "+handledException);
    }
  }

  @Override
  public AspectTryCatch getTryCatchBlock(final String exceptionType) {
    AspectTryCatch tryCatch = tryCatchBlockMap.get(exceptionType);
    if(tryCatch == null) {
      throw new IllegalStateException("Try-Catch block isn't available: "+exceptionType);
    }
    return tryCatch;
  }

  @Override
  public void reset() {
    this.nextSlotIndex = 0;
    this.begin = this.end = null;
    this.shadowMethod = this.originalMethod = null;
    this.begin = this.end = this.delegationBegin = this.delegationEnd = null;

    if(!this.localAttributes.isEmpty()) {
      this.localAttributes.clear();
    }
    if(!this.tryCatchBlockMap.isEmpty()) {
      this.tryCatchBlockMap.clear();
    }
  }
}
