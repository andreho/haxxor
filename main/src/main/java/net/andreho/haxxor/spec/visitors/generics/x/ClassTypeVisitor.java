package net.andreho.haxxor.spec.visitors.generics.x;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxParameterizedType;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeVariable;
import net.andreho.haxxor.spec.impl.HxGenericArrayTypeImpl;
import net.andreho.haxxor.spec.impl.HxParameterizedTypeImpl;
import net.andreho.haxxor.spec.impl.HxWildcardTypeImpl;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 18:37.
 */
public class ClassTypeVisitor extends ConsumingVisitor {

  private boolean array;
  private int dimension;
  protected HxType type;
  protected HxGenericArrayTypeImpl arrayType;
  protected HxParameterizedTypeImpl parameterizedType;

  /**
   * @param haxxor
   * @param consumer
   * @param variableResolver
   */
  public ClassTypeVisitor(final Haxxor haxxor,
                          final Consumer<HxGeneric<?>> consumer,
                          final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, consumer, variableResolver);
  }

  private String asArray(final String name) {
    if(dimension == 0) {
      return name;
    }
    array = true;
    String newName = asArray(dimension, name);
    dimension = 0;
    return newName;
  }

  private String asArray(int dimension, final String name) {
    if(dimension == 0) {
      return name;
    }
    StringBuilder builder = new StringBuilder(name);
    while(dimension > 0) {
      builder.append("[]");
      dimension--;
    }
    return builder.toString();
  }

  private HxGenericArrayTypeImpl asGenericArray() {
    if(arrayType == null) {
      Objects.requireNonNull(parameterizedType);
      arrayType = new HxGenericArrayTypeImpl().setGenericComponentType(parameterizedType);
    }
    return arrayType;
  }

  private HxParameterizedTypeImpl asParameterizedType() {
    if(parameterizedType == null) {
      parameterizedType = new HxParameterizedTypeImpl().setRawType(type);
    }
    return parameterizedType;
  }

  @Override
  public void visitClassType(final String name) {
    System.out.println("visitClassType(\""+name+"\")");
    this.type = getHaxxor().reference(name);
  }

  @Override
  public void visitInnerClassType(final String name) {
    System.out.println("visitInnerClassType(\""+name+"\")");
    this.type = getHaxxor().reference(name);
  }

  @Override
  public SignatureVisitor visitArrayType() {
    System.out.println("visitArrayType()");
    dimension++;
    return this;
  }


  protected void visitPrimitiveClassType(String name) {
    HxType aType = getHaxxor().reference(asArray(name));
    if(parameterizedType != null) {
      parameterizedType.addActualTypeArgument(aType);
    } else {
      consume(aType);
    }
  }

  @Override
  public void visitBaseType(final char descriptor) {
    System.out.println("visitBaseType('"+descriptor+"')");
    switch (descriptor) {
      case 'V': visitPrimitiveClassType("void"); break;
      case 'Z': visitPrimitiveClassType("boolean"); break;
      case 'B': visitPrimitiveClassType("byte"); break;
      case 'S': visitPrimitiveClassType("short"); break;
      case 'C': visitPrimitiveClassType("char"); break;
      case 'I': visitPrimitiveClassType("int"); break;
      case 'F': visitPrimitiveClassType("float"); break;
      case 'J': visitPrimitiveClassType("long"); break;
      case 'D': visitPrimitiveClassType("double"); break;
      default:
        throw new IllegalStateException("Invalid descriptor for a primitive type: "+descriptor);
    }
  }

  @Override
  public SignatureVisitor visitTypeArgument(final char wildcard) {
    System.out.println("visitTypeArgument('"+wildcard+"')");
    HxParameterizedTypeImpl parameterizedType = asParameterizedType();

    if('+' == wildcard || '-' == wildcard) {
      System.out.println(wildcard);
      HxWildcardTypeImpl wildcardType = new HxWildcardTypeImpl();
      parameterizedType.addActualTypeArgument(wildcardType);
      if('+' == wildcard) {
        return new ClassTypeVisitor(getHaxxor(), wildcardType::addUpperBound, getVariableResolver());
      } //else if('-' == wildcard)
      wildcardType.addUpperBound(getHaxxor().reference("java.lang.Object"));
      return new ClassTypeVisitor(getHaxxor(), wildcardType::addLowerBound, getVariableResolver());
    } else {
      if(array) {
        asGenericArray();
      }
    }
    return new ClassTypeVisitor(getHaxxor(), parameterizedType::addActualTypeArgument, getVariableResolver());
  }

  @Override
  public void visitTypeArgument() {
    System.out.println("visitTypeArgument()");
    asParameterizedType().addActualTypeArgument(new HxWildcardTypeImpl().asUnboundType(getHaxxor()));
  }

  @Override
  public void visitTypeVariable(final String name) {
    System.out.println("visitTypeVariable(\""+name+"\")");
    HxGeneric<?> result = typeVariable(name);
    while(dimension-- > 0) {
      result = new HxGenericArrayTypeImpl().setGenericComponentType(result);
    }
    consume(result);
//    asParameterizedType().addActualTypeArgument(typeArgument);
//    asParameterizedType().addActualTypeArgument(typeVariable(name));
  }

  @Override
  public void visitEnd() {
    System.out.println("visitEnd()");
    HxGeneric<?> result;
     if(arrayType != null) {
      result = arrayType;
    } else if(parameterizedType != null) {
      result = parameterizedType.minimize();
    } else if(type != null) {
      result = type;
    } else {
      System.out.println("visitEnd(): NO TYPE");
      return;
    }
    if(dimension > 0) {
      if(result instanceof HxParameterizedType) {
        while(dimension-- > 0) {
          result = new HxGenericArrayTypeImpl().setGenericComponentType(result);
        }
      } else if(result instanceof HxType) {
        final String typeName = ((HxType) result).getName();
        result = getHaxxor().reference(asArray(dimension, typeName));
      }
    }
    consume(result);
  }
}
