package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxProvidable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxArrayTypeImpl
    implements HxType {

  private static final HxProvidable<List<HxType>> DEFAULT_INTERFACES_FOR_ARRAY =
    HxProvidable.Defaults.DEFAULT_INTERFACES_FOR_ARRAY;

  private static final int ARRAY_MODIFIERS =
      Modifiers.PUBLIC.toBit() | Modifiers.FINAL.toBit() | Modifiers.ABSTRACT.toBit();

  private final HxType componentType;
  private volatile String name;

  public HxArrayTypeImpl(final HxType componentType) {
    this.componentType = Objects.requireNonNull(componentType, "Component type of an array can't be null.");
  }

  @Override
  public Hx getHaxxor() {
    return componentType.getHaxxor();
  }

  @Override
  public String getName() {
    String name = this.name;
    if(name != null) {
      return name;
    }
    return this.name = createArrayName();
  }

  private String createArrayName() {
    final StringBuilder builder = new StringBuilder();
    HxType component = this.componentType;
    int dims = 1; //this is already one array dimension

    while(component.isArray()) {
      Optional<HxType> optional = component.getComponentType();
      if(optional.isPresent()) {
        component = optional.get();
      }
      dims++;
    }
    builder.append(component.getName());

    while(dims-- > 0) {
      builder.append("[]");
    }
    return builder.toString();
  }

  @Override
  public Optional<HxType> getComponentType() {
    return Optional.of(this.componentType);
  }

  @Override
  public Optional<HxType> getSupertype() {
    return Optional.of(getHaxxor().reference("java.lang.Object"));
  }

  @Override
  public List<HxType> getInterfaces() {
    return DEFAULT_INTERFACES_FOR_ARRAY.fetch(getHaxxor());
  }

  @Override
  public int getModifiers() {
    return ARRAY_MODIFIERS;
  }

  @Override
  public HxSort getSort() {
    return HxSort.ARRAY;
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public boolean isVoid() {
    return false;
  }

  @Override
  public boolean isReference() {
    return true;
  }

  @Override
  public HxType toReference() {
    return this;
  }

  @Override
  public int distanceTo(final HxType otherType, final int arrayCost, final int extendsCost, final int interfaceCost) {
    return HxImplTools.estimateDistance(otherType, this, 0, arrayCost, extendsCost, interfaceCost);
  }

  @Override
  public boolean hasDescriptor(final String descriptor) {
    if(!descriptor.startsWith(HxConstants.DESC_ARRAY_PREFIX_STR)) {
      return false;
    }
    int offset = 1;
    HxType component = this.componentType;
    while(component.isArray()) {
      Optional<HxType> optional = component.getComponentType();
      if(descriptor.charAt(offset++) != HxConstants.DESC_ARRAY_PREFIX && !optional.isPresent()) {
        break;
      }
      component = optional.get();
    }

    if(descriptor.charAt(offset) != HxConstants.DESC_ARRAY_PREFIX) {
      return component.hasDescriptor(descriptor.substring(offset));
    }
    return false;
  }

  @Override
  public Class<?> toClass(final ClassLoader classLoader)
  throws ClassNotFoundException {
    //[I or [Ljava.lang.String;
    final String classname = toDescriptor().replace('/', '.');
    return Class.forName(classname, false, classLoader);
  }

  @Override
  public int hashCode() {
    int hash = 0;
    HxType component = this.componentType;
    while(component.isArray()) {
      Optional<HxType> optional = component.getComponentType();
      if(optional.isPresent()) {
        component = optional.get();
      }
      hash += 7;
    }
    return hash + component.hashCode();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxType)) {
      return false;
    }
    final HxType that = (HxType) o;
    return that.isArray() &&
           Objects.equals(this.getComponentType().orElse(null),
                          that.getComponentType().orElse(null));
  }

  @Override
  public String toString() {
    return getName();
  }
}
