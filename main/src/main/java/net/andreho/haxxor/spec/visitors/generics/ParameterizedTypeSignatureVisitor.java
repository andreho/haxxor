package net.andreho.haxxor.spec.visitors.generics;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxParameterizedType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spec.impl.HxParameterizedTypeImpl;
import net.andreho.haxxor.spec.impl.HxWildcardTypeImpl;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 01:08.
 */
public class ParameterizedTypeSignatureVisitor
    extends TargetedSignatureVisitor<HxGeneric> {

  private int dimension;
  /**
   * @param haxxor
   * @param consumer
   */
  public ParameterizedTypeSignatureVisitor(final Haxxor haxxor,
                                           final Consumer<HxGeneric> consumer,
                                           final Function<String, HxGeneric<?>> variableResolver) {
    super(haxxor, consumer, variableResolver);
    this.target = new HxParameterizedTypeImpl();
  }

  private ParameterizedTypeSignatureVisitor asParameterizedType() {
    HxGeneric target = getTarget();
    if(!(target instanceof HxParameterizedType)) {
      HxParameterizedTypeImpl parameterizedType = new HxParameterizedTypeImpl();
      this.target = parameterizedType;
      if(target != null) {
        parameterizedType.addActualTypeArgument(target);
      }
    }
    return this;
  }

  @Override
  public void visitClassType(final String name) {
    String classname = asArray(name);
    HxTypeReference reference = getHaxxor().reference(classname);
    HxGeneric target = getTarget();
    if(target instanceof HxParameterizedType) {
      HxParameterizedTypeImpl parameterizedType = (HxParameterizedTypeImpl) target;
      parameterizedType.setRawType(reference);
    } else {
      this.target = reference;
    }
  }

  private String asArray(final String name) {
    String classname = name;
    while(dimension > 0) {
      classname += "[]";
      dimension--;
    }
    return classname;
  }

  @Override
  public void visitInnerClassType(final String name) {
    String classname = asArray(name);
    HxParameterizedTypeImpl parameterizedType = getTarget();
    parameterizedType.setRawType(getHaxxor().reference(classname));
  }

  @Override
  public SignatureVisitor visitArrayType() {
    dimension++;
    return this;
  }

  @Override
  public void visitBaseType(final char descriptor) {
    switch (descriptor) {
      case 'V':
      case 'Z':
      case 'B':
      case 'S':
      case 'C':
      case 'I':
      case 'F':
      case 'J':
      case 'D':
        default:
          throw new IllegalStateException();
    }
  }

  @Override
  public void visitTypeArgument() {
    HxParameterizedTypeImpl parameterizedType = asParameterizedType().getTarget();
    parameterizedType.addActualTypeArgument(new HxWildcardTypeImpl().asUnboundType(getHaxxor()));
  }

  @Override
  public SignatureVisitor visitTypeArgument(final char wildcard) {
    HxParameterizedTypeImpl parameterizedType = asParameterizedType().getTarget();
    return new ParameterizedTypeSignatureVisitor(getHaxxor(), parameterizedType::addActualTypeArgument, getVariableResolver());
  }

  @Override
  public void visitTypeVariable(final String name) {
    HxGeneric<?> variable = variable(name);
    if(target instanceof HxParameterizedType) {
      HxParameterizedTypeImpl parameterizedType = (HxParameterizedTypeImpl) target;
      parameterizedType.addActualTypeArgument(variable);
    } else {
      this.target = variable;
    }
  }
}
