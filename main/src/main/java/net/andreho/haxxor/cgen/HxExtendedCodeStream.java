package net.andreho.haxxor.cgen;


import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxSort;
import net.andreho.haxxor.spec.api.HxType;

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
    if(!field.isStatic()) {
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
      throw new IllegalStateException("Invalid invoke operation of static method: "+method);
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
      throw new IllegalStateException("Invalid invoke operation of static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKESPECIAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INVOKEINTERFACE(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation of static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKEINTERFACE(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INSTANCEOF(HxType type) {
    return INSTANCEOF(type.toInternalName());
  }

  default HxExtendedCodeStream CHECKCAST(HxType type) {
    return CHECKCAST(type.toInternalName());
  }

  default HxExtendedCodeStream TYPE(HxType type) {
    return TYPE(type.toInternalName());
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

  default HxExtendedCodeStream GENERIC_ARRAY_STORE(HxType arrayType) {
    final HxType componentType =
       arrayType.getComponentType().orElseThrow(IllegalArgumentException::new);

    switch (componentType.getSort()) {
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
      GENERIC_LOAD(type, slotShift);
      slotShift += type.getSlotsCount();
    }
    return this;
  }

  default HxExtendedCodeStream GENERIC_LOAD(final HxType type, final int slotId) {
    switch (type.getSort()) {
      case VOID:
        throw new IllegalArgumentException("Invalid parameter: " + type);

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

  default HxExtendedCodeStream GENERIC_STORE(final HxSort sort, final int slotId) {
    switch (sort) {
      case VOID:
        throw new IllegalArgumentException("Invalid parameter: " + sort);

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
}
