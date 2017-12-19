package net.andreho.haxxor.cgen;


import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public interface HxExtendedCodeStream
  extends HxCodeStream<HxExtendedCodeStream> {

  default HxExtendedCodeStream GETSTATIC(HxField field) {
    if(!field.isStatic()) {
      throw new IllegalStateException("Invalid GETSTATIC operation on not-static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return GETSTATIC(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream GETFIELD(HxField field) {
    if(field.isStatic()) {
      throw new IllegalStateException("Invalid GETFIELD operation on static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return GETFIELD(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream PUTSTATIC(HxField field) {
    if(!field.isStatic()) {
      throw new IllegalStateException("Invalid PUTSTATIC operation on not-static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return PUTSTATIC(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream PUTFIELD(HxField field) {
    if(field.isStatic()) {
      throw new IllegalStateException("Invalid PUTFIELD operation on static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return PUTFIELD(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream INVOKEVIRTUAL(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation on a static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKEVIRTUAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INVOKESTATIC(HxMethod method) {
    if(!method.isStatic()) {
      throw new IllegalStateException("Not a static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKESTATIC(declaring.toInternalName(), method.getName(), method.toDescriptor(), declaring.isInterface());
  }

  default HxExtendedCodeStream INVOKESPECIAL(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation on a static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKESPECIAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INVOKEINTERFACE(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation on a static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKEINTERFACE(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INVOKE(HxMethod method) {
    final HxType declaring = method.getDeclaringType();
    if(method.isStatic()) {
      return INVOKESTATIC(method);
    }
    if(method.isPrivate() ||
       method.isConstructor()) {
      return INVOKESPECIAL(method);
    }
    if(declaring.isInterface()) {
      return INVOKEINTERFACE(method);
    }
    return INVOKEVIRTUAL(method);
  }

  default HxExtendedCodeStream INSTANCEOF(HxType type) {
    return INSTANCEOF(type.toInternalName());
  }

  default HxExtendedCodeStream CHECKCAST(HxType type) {
    return CHECKCAST(type.toInternalName());
  }

  default HxExtendedCodeStream TYPE(HxType type) {
    return TYPE(type.toDescriptor());
  }

  /**
   * <b>ATTENTION:</b> this method can't correctly treat multi-dimensional arrays.
   * @param type
   * @return
   */
  default HxExtendedCodeStream NEW(HxType type) {
    if(!type.isInstantiable()) {
      throw new IllegalArgumentException("Not instantiable type: "+type);
    }
    if(!type.isArray()) {
      return NEW(type.toInternalName());
    }
    final HxType componentType = type.getComponentType().get();
    if(componentType.isPrimitive()) {
      return NEWARRAY(HxArrayType.fromSort(componentType.getSort()));
    }
    return ANEWARRAY(componentType.toInternalName());
  }

  default HxExtendedCodeStream GENERIC_DEFAULT_VALUE(HxType type) {
    return GENERIC_DEFAULT_VALUE(type.getSort());
  }

  default HxExtendedCodeStream GENERIC_DEFAULT_VALUE(HxSort sort) {
    switch (sort) {
      case VOID:
        throw new IllegalArgumentException("Type hasn't any default value: "+sort);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return ICONST_0();
      case FLOAT:
        return FCONST_0();
      case LONG:
        return LCONST_0();
      case DOUBLE:
        return DCONST_0();
      default: {
        return ACONST_NULL();
      }
    }
  }

  default HxExtendedCodeStream GENERIC_RETURN(HxType returnType) {
    return GENERIC_RETURN(returnType.getSort());
  }

  default HxExtendedCodeStream GENERIC_RETURN(HxSort returnValueSort) { {
      switch (returnValueSort) {
        case VOID:
          return RETURN();
        case BOOLEAN:
        case BYTE:
        case CHAR:
        case SHORT:
        case INT:
          return IRETURN();
        case FLOAT:
          return FRETURN();
        case LONG:
          return LRETURN();
        case DOUBLE:
          return DRETURN();
        default: {
          return ARETURN();
        }
      }
    }
  }

  default HxExtendedCodeStream GENERIC_ARRAY_STORE(HxType arrayComponentType) {
    return GENERIC_ARRAY_STORE(arrayComponentType.getSort());
  }

  default HxExtendedCodeStream GENERIC_ARRAY_STORE(HxSort arrayComponentSort) {
    switch (arrayComponentSort) {
      case BOOLEAN:
      case BYTE:
        return BASTORE();
      case CHAR:
        return CASTORE();
      case SHORT:
        return SASTORE();
      case INT:
        return IASTORE();
      case FLOAT:
        return FASTORE();
      case LONG:
        return LASTORE();
      case DOUBLE:
        return DASTORE();
      default: {
        return AASTORE();
      }
    }
  }

  default HxExtendedCodeStream GENERIC_LOAD(final List<HxType> types, int slotShift) {
    for (HxType type : types) {
      GENERIC_LOAD(type.getSort(), slotShift);
      slotShift += type.getSlotSize();
    }
    return this;
  }

  default HxExtendedCodeStream GENERIC_LOAD(final HxType type, final int slotId) {
    return GENERIC_LOAD(type.getSort(), slotId);
  }

  default HxExtendedCodeStream GENERIC_LOAD(final HxSort sort, final int slotId) {
    switch (sort) {
      case VOID:
        return this;

      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return ILOAD(slotId);
      case FLOAT:
        return FLOAD(slotId);
      case LONG:
        return LLOAD(slotId);
      case DOUBLE:
        return DLOAD(slotId);
      default:
        return ALOAD(slotId);
    }
  }

  default HxExtendedCodeStream GENERIC_STORE(final HxType type, final int slotId) {
    return GENERIC_STORE(type.getSort(), slotId);
  }

  default HxExtendedCodeStream GENERIC_STORE(final HxSort sort, final int slotId) {
    switch (sort) {
      case VOID:
        return this;

      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return ISTORE(slotId);
      case FLOAT:
        return FSTORE(slotId);
      case LONG:
        return LSTORE(slotId);
      case DOUBLE:
        return DSTORE(slotId);
      default:
        return ASTORE(slotId);
    }
  }

  default HxExtendedCodeStream GENERIC_POP(final HxType type) {
    return GENERIC_POP(type.getSort());
  }

  default HxExtendedCodeStream GENERIC_POP(final HxSort sort) {
    switch (sort) {
      case VOID:
        return this;
      case LONG:
      case DOUBLE:
        return POP2();
      default:
        return POP();
    }
  }

  default HxExtendedCodeStream GENERIC_DUP(final HxType type) {
    return GENERIC_DUP(type.getSort());
  }

  default HxExtendedCodeStream GENERIC_DUP(final HxSort sort) {
    switch (sort) {
      case VOID:
        return this;
      case LONG:
      case DOUBLE:
        return DUP2();
      default:
        return DUP();
    }
  }

  default HxExtendedCodeStream AUTOBOXING(final HxType type) {
    return AUTOBOXING(type.getSort());
  }

  /**
   * @param sort
   * @return
   * @see <a href="https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html">Documentation</a>
   */
  default HxExtendedCodeStream AUTOBOXING(final HxSort sort) {
    switch (sort) {
      case BOOLEAN:
        return INVOKESTATIC("java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
      case BYTE:
        return INVOKESTATIC("java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
      case CHAR:
        return INVOKESTATIC("java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
      case SHORT:
        return INVOKESTATIC("java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
      case INT:
        return INVOKESTATIC("java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
      case FLOAT:
        return INVOKESTATIC("java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
      case LONG:
        return INVOKESTATIC("java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
      case DOUBLE:
        return INVOKESTATIC("java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
      case VOID:
        throw new IllegalArgumentException("Invalid type: " + sort);
    }
    return this;
  }

  default HxExtendedCodeStream UNBOXING(final HxType type, final boolean castBefore) {
    return UNBOXING(type.getSort(), castBefore);
  }

  /**
   *
   * @param sort
   * @param castBefore
   * @return
   * @see <a href="https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html">Documentation</a>
   */
  default HxExtendedCodeStream UNBOXING(final HxSort sort, final boolean castBefore) {
    if(castBefore) {
      switch (sort) {
        case BOOLEAN:
          CHECKCAST("java/lang/Boolean");
          break;
        case CHAR:
          CHECKCAST("java/lang/Character");
          break;
        case BYTE:
        case SHORT:
        case INT:
        case FLOAT:
        case LONG:
        case DOUBLE:
          CHECKCAST("java/lang/Number");
          break;
        case VOID:
          throw new IllegalArgumentException("Invalid type: " + sort);
      }
    }

    String methodName = null;
    String descriptor = null;

    switch (sort) {
      case BOOLEAN:
        return INVOKEVIRTUAL("java/lang/Boolean", "booleanValue", "()Z");
      case CHAR:
        return INVOKEVIRTUAL("java/lang/Character", "charValue", "()C");
      case BYTE: methodName = "byteValue"; descriptor = "()B"; break;
      case SHORT: methodName = "shortValue"; descriptor = "()S"; break;
      case INT: methodName = "intValue"; descriptor = "()I"; break;
      case FLOAT: methodName = "floatValue"; descriptor = "()F"; break;
      case LONG: methodName = "longValue"; descriptor = "()J"; break;
      case DOUBLE: methodName = "doubleValue"; descriptor = "()D"; break;
      case VOID:
        throw new IllegalArgumentException("Invalid type: " + sort);
    }
    if(methodName != null) {
      return INVOKEVIRTUAL("java/lang/Number", methodName, descriptor);
    }
    return this;
  }

  default HxExtendedCodeStream CONVERT(final HxType fromType, final HxType toType) {
    final HxSort toTypeSort = toType.getSort();
    if(fromType.equals(toType)) {
      return this;
    }

    switch (fromType.getSort()) {
      case BOOLEAN:
        return CONVERT_BOOLEAN(toTypeSort);
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return CONVERT_INT(toTypeSort);
      case FLOAT:
        return CONVERT_FLOAT(toTypeSort);
      case LONG:
        return CONVERT_LONG(toTypeSort);
      case DOUBLE:
        return CONVERT_DOUBLE(toTypeSort);
      case OBJECT:
        if(toType.isPrimitive()) {
          return UNBOXING(toType, true);
        }
        if(!fromType.equals(toType)) {
          return CHECKCAST(toType);
        }
        return this;
      case ARRAY:
        if(toType.hasName("java.lang.Object") ||
           toType.hasName("java.io.Serializable")) {
          return this;
        } else if(toType.isArray() &&
                  fromType.getDimension() == toType.getDimension()) {
          return CHECKCAST(toType);
        }
    }
    throw new IllegalStateException("Not convertible: "+toType);
  }

  default HxExtendedCodeStream CONVERT(final HxSort fromType, final HxSort toType) {
    switch (fromType) {
      case BOOLEAN:
        return CONVERT_BOOLEAN(toType);
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return CONVERT_INT(toType);
      case FLOAT:
        return CONVERT_FLOAT(toType);
      case LONG:
        return CONVERT_LONG(toType);
      case DOUBLE:
        return CONVERT_DOUBLE(toType);
      case OBJECT:
        if(toType.isPrimitive()) {
          return UNBOXING(toType, true);
        }
        return this;
//      case ARRAY:
//        if(toType.isArray() || toType.isObject()) {
//          return this;
//        }
    }
    throw new IllegalStateException("Not convertible: "+toType);
  }

  /**
   * <code>value == true? 1 : 0</code>
   * @param toType
   * @return
   */
  default HxExtendedCodeStream CONVERT_BOOLEAN(final HxSort toType) {
    if(toType == HxSort.BOOLEAN) {
      return this;
    } else if(toType == HxSort.OBJECT) {
      return AUTOBOXING(toType);
    }

    LABEL isTrue = new LABEL();
    LABEL endif = new LABEL();
    IFNE(isTrue);

    switch (toType) {
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return
          ICONST_0()
          .GOTO(endif)
          .LABEL(isTrue)
          .ICONST_1()
          .LABEL(endif);
      case FLOAT:
        return
          FCONST_0()
          .GOTO(endif)
          .LABEL(isTrue)
          .FCONST_1()
          .LABEL(endif);
      case LONG:
        return
          LCONST_0()
          .GOTO(endif)
          .LABEL(isTrue)
          .LCONST_1()
          .LABEL(endif);
      case DOUBLE:
        return
          DCONST_0()
          .GOTO(endif)
          .LABEL(isTrue)
          .DCONST_1()
          .LABEL(endif);
      default: {
        throw new IllegalStateException("Not convertible: "+toType);
      }
    }
  }

  default HxExtendedCodeStream CONVERT_INT(final HxSort toType) {
    switch (toType) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return
          IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return I2B();
      case CHAR:
        return I2C();
      case SHORT:
        return I2S();
      case INT:
        return this;
      case FLOAT:
        return I2F();
      case LONG:
        return I2L();
      case DOUBLE:
        return I2D();
      case OBJECT:
        return AUTOBOXING(toType);
      default: {
        throw new IllegalStateException("Not convertible: "+toType);
      }
    }
  }

  default HxExtendedCodeStream CONVERT_FLOAT(final HxSort toType) {
    switch (toType) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return
          FCONST_0()
          .FCMPG()
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return F2I().I2B();
      case CHAR:
        return F2I().I2C();
      case SHORT:
        return F2I().I2S();
      case INT:
        return F2I();
      case FLOAT:
        return this;
      case LONG:
        return F2L();
      case DOUBLE:
        return F2D();
      case OBJECT:
        return AUTOBOXING(toType);
      default: {
        throw new IllegalStateException("Not convertible: "+toType);
      }
    }
  }

  default HxExtendedCodeStream CONVERT_LONG(final HxSort toType) {
    switch (toType) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return
          LCONST_0()
          .LCMP()
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return L2I().I2B();
      case CHAR:
        return L2I().I2C();
      case SHORT:
        return L2I().I2S();
      case INT:
        return L2I();
      case FLOAT:
        return L2F();
      case LONG:
        return this;
      case DOUBLE:
        return L2D();
      case OBJECT:
        return AUTOBOXING(toType);
      default: {
        throw new IllegalStateException("Not convertible: "+toType);
      }
    }
  }

  default HxExtendedCodeStream CONVERT_DOUBLE(final HxSort toType) {
    switch (toType) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return
          DCONST_0()
          .DCMPG()
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return D2I().I2B();
      case CHAR:
        return D2I().I2C();
      case SHORT:
        return D2I().I2S();
      case INT:
        return D2I();
      case FLOAT:
        return D2F();
      case LONG:
        return D2L();
      case DOUBLE:
        return this;
      case OBJECT:
        return AUTOBOXING(toType);
      default: {
        throw new IllegalStateException("Not convertible: "+toType);
      }
    }
  }

  default HxExtendedCodeStream PACK_ARGUMENTS(final List<HxType> signature,
                                              final int firstSlotOffset) {
    LDC(signature.size())
    .ANEWARRAY("java/lang/Object");

    int slotIndex = firstSlotOffset;

    for (int i = 0; i < signature.size(); i++) {
      final HxType type = signature.get(i);

      DUP()
      .LDC(i)
      .GENERIC_LOAD(type, slotIndex)
      .AUTOBOXING(type)
      .GENERIC_ARRAY_STORE(HxSort.OBJECT);

      slotIndex += type.getSlotSize();
    }

    return this;
  }
}
