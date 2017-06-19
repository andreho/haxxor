package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.spec.api.HxType;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 03:14.
 */
public abstract class HxCgenUtils {

  private static final String VALUE_OF_METHOD = "valueOf";

  private static final String BOOLEAN_VALUE_METHOD = "booleanValue";
  private static final String BYTE_VALUE_METHOD = "byteValue";
  private static final String CHAR_VALUE_METHOD = "charValue";
  private static final String SHORT_VALUE_METHOD = "shortValue";
  private static final String INT_VALUE_METHOD = "intValue";
  private static final String FLOAT_VALUE_METHOD = "floatValue";
  private static final String LONG_VALUE_METHOD = "longValue";
  private static final String DOUBLE_VALUE_METHOD = "doubleValue";

  //###############################################################################################

  private static final Type OBJECT_TYPE = Type.getType(Object.class);
  private static final Type NUMBER_TYPE = Type.getType(Number.class);
  public static final String NUMBER_TYPE_INTERNAL_NAME = NUMBER_TYPE.getInternalName();

  //###############################################################################################

  private static final Type BOOLEAN_WRAPPER = Type.getType(Boolean.class);
  public static final String BOOLEAN_WRAPPER_INTERNAL_NAME = BOOLEAN_WRAPPER.getInternalName();

  private static final Type BYTE_WRAPPER = Type.getType(Byte.class);
  public static final String BYTE_WRAPPER_INTERNAL_NAME = BYTE_WRAPPER.getInternalName();

  private static final Type CHARACTER_WRAPPER = Type.getType(Character.class);
  public static final String CHARACTER_WRAPPER_INTERNAL_NAME = CHARACTER_WRAPPER.getInternalName();

  private static final Type SHORT_WRAPPER = Type.getType(Short.class);
  public static final String SHORT_WRAPPER_INTERNAL_NAME = SHORT_WRAPPER.getInternalName();

  private static final Type INTEGER_WRAPPER = Type.getType(Integer.class);
  public static final String INTEGER_WRAPPER_INTERNAL_NAME = INTEGER_WRAPPER.getInternalName();

  private static final Type FLOAT_WRAPPER = Type.getType(Float.class);
  public static final String FLOAT_WRAPPER_INTERNAL_NAME = FLOAT_WRAPPER.getInternalName();

  private static final Type LONG_WRAPPER = Type.getType(Long.class);
  public static final String LONG_WRAPPER_INTERNAL_NAME = LONG_WRAPPER.getInternalName();

  private static final Type DOUBLE_WRAPPER = Type.getType(Double.class);
  public static final String DOUBLE_WRAPPER_INTERNAL_NAME = DOUBLE_WRAPPER.getInternalName();

  //###############################################################################################

  private static final String BOOLEAN_WRAP_DESCRIPTOR =
    "(" + Type.BOOLEAN_TYPE.getDescriptor() + ")" + BOOLEAN_WRAPPER.getDescriptor();

  private static final String BYTE_WRAP_DESCRIPTOR =
    "(" + Type.BYTE_TYPE.getDescriptor() + ")" + BYTE_WRAPPER.getDescriptor();

  private static final String CHARACTER_WRAP_DESCRIPTOR =
    "(" + Type.CHAR_TYPE.getDescriptor() + ")" + CHARACTER_WRAPPER.getDescriptor();

  private static final String SHORT_WRAP_DESCRIPTOR =
    "(" + Type.SHORT_TYPE.getDescriptor() + ")" + SHORT_WRAPPER.getDescriptor();

  private static final String INTEGER_WRAP_DESCRIPTOR =
    "(" + Type.INT_TYPE.getDescriptor() + ")" + INTEGER_WRAPPER.getDescriptor();

  private static final String FLOAT_WRAP_DESCRIPTOR =
    "(" + Type.FLOAT_TYPE.getDescriptor() + ")" + FLOAT_WRAPPER.getDescriptor();

  private static final String LONG_WRAP_DESCRIPTOR =
    "(" + Type.LONG_TYPE.getDescriptor() + ")" + LONG_WRAPPER.getDescriptor();

  private static final String DOUBLE_WRAP_DESCRIPTOR =
    "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" + DOUBLE_WRAPPER.getDescriptor();

  private HxCgenUtils() {
  }

  public static <S extends HxCodeStream<S>> S packArguments(final List<HxType> argumentTypes,
                                                            final int slotOffset,
                                                            final S stream) {
    stream
      .LDC(argumentTypes.size())
      .ANEWARRAY("java/lang/Object");

    int slot = slotOffset;
    for (int i = 0; i < argumentTypes.size(); i++) {
      final HxType type = argumentTypes.get(i);
      stream
        .DUP()
        .LDC(i);

      loadSlot(type, slot, stream);
      wrapIfNeeded(type, stream);
      objectArrayStore(stream);

      slot += type.getSlotsCount();
    }

    return stream;
  }

  public static <S extends HxCodeStream<S>> S objectArrayStore(final S codeStream) {
    return codeStream.AASTORE();
  }

  public static <S extends HxCodeStream<S>> S genericArrayStore(final HxType arrayType,
                                                                final S codeStream) {
    if (!arrayType.isArray()) {
      throw new IllegalArgumentException("Not an array-type: " + arrayType);
    }

    //STACK: ..., arrayref, index, value → ...
    switch (arrayType.getComponentType().get().getSort()) {
      case BOOLEAN:
      case BYTE:
        return codeStream.BASTORE();
      case CHAR:
        return codeStream.CASTORE();
      case SHORT:
        return codeStream.SASTORE();
      case INT:
        return codeStream.IASTORE();
      case FLOAT:
        return codeStream.FASTORE();
      case LONG:
        return codeStream.LASTORE();
      case DOUBLE:
        return codeStream.DASTORE();
      default: {
        return codeStream.AASTORE();
      }
    }
  }

  public static <S extends HxCodeStream<S>> S genericLoadDefault(final HxType type,
                                                                 final S codeStream) {
    switch (type.getSort()) {
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return codeStream.ICONST_0();
      case FLOAT:
        return codeStream.FCONST_0();
      case LONG:
        return codeStream.LCONST_0();
      case DOUBLE:
        return codeStream.DCONST_0();
      default: {
        return codeStream.ACONST_NULL();
      }
    }
  }

  public static <S extends HxCodeStream<S>> S loadSlot(final HxType type,
                                                       final int slotId,
                                                       final S stream) {
    switch (type.getSort()) {
      case VOID:
        throw new IllegalArgumentException("Invalid parameter: " + type);

      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return stream.ILOAD(slotId);
      case FLOAT:
        return stream.FLOAD(slotId);
      case LONG:
        return stream.LLOAD(slotId);
      case DOUBLE:
        return stream.DLOAD(slotId);
      default:
        return stream.ALOAD(slotId);
    }
  }

  public static <S extends HxCodeStream<S>> S storeSlot(final HxType type,
                                                       final int slotId,
                                                       final S stream) {
    switch (type.getSort()) {
      case VOID:
        throw new IllegalArgumentException("Invalid parameter: " + type);

      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return stream.ISTORE(slotId);
      case FLOAT:
        return stream.FSTORE(slotId);
      case LONG:
        return stream.LSTORE(slotId);
      case DOUBLE:
        return stream.DSTORE(slotId);
      default:
        return stream.ASTORE(slotId);
    }
  }

  public static <S extends HxCodeStream<S>> S wrapIfNeeded(final HxType possiblyPrimitiveType,
                                                           final S stream) {
    switch (possiblyPrimitiveType.getSort()) {
      case BOOLEAN:
        return stream.
                       INVOKESTATIC(BOOLEAN_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, BOOLEAN_WRAP_DESCRIPTOR, false);
      case BYTE:
        return stream.
                       INVOKESTATIC(BYTE_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, BYTE_WRAP_DESCRIPTOR, false);
      case CHAR:
        return stream.
                       INVOKESTATIC(CHARACTER_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, CHARACTER_WRAP_DESCRIPTOR, false);
      case SHORT:
        return stream.
                       INVOKESTATIC(SHORT_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, SHORT_WRAP_DESCRIPTOR, false);
      case INT:
        return stream.
                       INVOKESTATIC(INTEGER_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, INTEGER_WRAP_DESCRIPTOR, false);
      case FLOAT:
        return stream.
                       INVOKESTATIC(FLOAT_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, FLOAT_WRAP_DESCRIPTOR, false);
      case LONG:
        return stream.
                       INVOKESTATIC(LONG_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, LONG_WRAP_DESCRIPTOR, false);
      case DOUBLE:
        return stream.
                       INVOKESTATIC(DOUBLE_WRAPPER_INTERNAL_NAME, VALUE_OF_METHOD, DOUBLE_WRAP_DESCRIPTOR, false);
      case VOID:
        throw new IllegalArgumentException("Invalid type: " + possiblyPrimitiveType);
    }
    return stream;
  }
}