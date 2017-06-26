package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;
import net.andreho.haxxor.spec.api.HxSort;
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

  public static <S extends HxCodeStream<S>> S genericReturnValue(final HxType returnType,
                                                                 final S stream) {
    switch (returnType.getSort()) {
      case VOID:
        return stream.RETURN();
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
        return stream.IRETURN();
      case FLOAT:
        return stream.FRETURN();
      case LONG:
        return stream.LRETURN();
      case DOUBLE:
        return stream.DRETURN();
      default: {
        return stream.ARETURN();
      }
    }
  }

  public static <S extends HxCodeStream<S>> S packArguments(final List<HxType> signature,
                                                            final int slotOffset,
                                                            final S stream) {
    stream
      .LDC(signature.size())
      .ANEWARRAY("java/lang/Object");

    int slot = slotOffset;
    for (int i = 0; i < signature.size(); i++) {
      final HxType type = signature.get(i);
      stream
        .DUP()
        .LDC(i);

      genericLoadSlot(type, slot, stream);
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
                                                                final S stream) {
    if (!arrayType.isArray()) {
      throw new IllegalArgumentException("Not an array-type: " + arrayType);
    }

    //STACK: ..., arrayref, index, value → ...
    switch (arrayType.getComponentType().get().getSort()) {
      case BOOLEAN:
      case BYTE:
        return stream.BASTORE();
      case CHAR:
        return stream.CASTORE();
      case SHORT:
        return stream.SASTORE();
      case INT:
        return stream.IASTORE();
      case FLOAT:
        return stream.FASTORE();
      case LONG:
        return stream.LASTORE();
      case DOUBLE:
        return stream.DASTORE();
      default: {
        return stream.AASTORE();
      }
    }
  }

  public static <S extends HxCodeStream<S>> S genericConvertInteger(final HxType toType,
                                                                    final S stream) {
    if (toType.isArray()) {
      throw new IllegalArgumentException("Cannot convert an int to an array: " + toType);
    }

    switch (toType.getSort()) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return stream
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return stream.I2B();
      case CHAR:
        return stream.I2C();
      case SHORT:
        return stream.I2S();
      case INT:
        return stream;
      case FLOAT:
        return stream.I2F();
      case LONG:
        return stream.I2L();
      case DOUBLE:
        return stream.I2D();
      default: {
        return wrapIfNeeded(HxSort.INT, stream);
      }
    }
  }

  public static <S extends HxCodeStream<S>> S genericConvertFloat(final HxType toType,
                                                                  final S stream) {
    if (toType.isArray()) {
      throw new IllegalArgumentException("Cannot convert an int to an array: " + toType);
    }

    switch (toType.getSort()) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return stream
          .FCONST_0()
          .FCMPG()
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return stream.F2I().I2B();
      case CHAR:
        return stream.F2I().I2C();
      case SHORT:
        return stream.F2I().I2S();
      case INT:
        return stream.F2I();
      case FLOAT:
        return stream;
      case LONG:
        return stream.F2L();
      case DOUBLE:
        return stream.F2D();
      default: {
        return wrapIfNeeded(HxSort.FLOAT, stream);
      }
    }
  }

  public static <S extends HxCodeStream<S>> S genericConvertLong(final HxType toType,
                                                                  final S stream) {
    if (toType.isArray()) {
      throw new IllegalArgumentException("Cannot convert an int to an array: " + toType);
    }

    switch (toType.getSort()) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return stream
          .LCONST_0()
          .LCMP()
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return stream.L2I().I2B();
      case CHAR:
        return stream.L2I().I2C();
      case SHORT:
        return stream.L2I().I2S();
      case INT:
        return stream.L2I();
      case FLOAT:
        return stream.L2F();
      case LONG:
        return stream;
      case DOUBLE:
        return stream.L2D();
      default: {
        return wrapIfNeeded(HxSort.LONG, stream);
      }
    }
  }

  public static <S extends HxCodeStream<S>> S genericConvertDouble(final HxType toType,
                                                                 final S stream) {
    if (toType.isArray()) {
      throw new IllegalArgumentException("Cannot convert an int to an array: " + toType);
    }

    switch (toType.getSort()) {
      case BOOLEAN:
        LABEL notZero = new LABEL();
        LABEL endif = new LABEL();
        return stream
          .DCONST_0()
          .DCMPG()
          .IFNE(notZero)
          .ICONST_0()
          .GOTO(endif)
          .LABEL(notZero)
          .ICONST_1()
          .LABEL(endif);
      case BYTE:
        return stream.D2I().I2B();
      case CHAR:
        return stream.D2I().I2C();
      case SHORT:
        return stream.D2I().I2S();
      case INT:
        return stream.D2I();
      case FLOAT:
        return stream.D2F();
      case LONG:
        return stream.D2L();
      case DOUBLE:
        return stream;
      default: {
        return wrapIfNeeded(HxSort.DOUBLE, stream);
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

  public static <S extends HxCodeStream<S>> S genericPopStack(final HxType lastValue,
                                                              final S stream) {
    switch (lastValue.getSort()) {
      case VOID:
        return stream;
      case LONG:
      case DOUBLE:
        return stream.POP2();
      default:
        return stream.POP();
    }
  }

  public static <S extends HxCodeStream<S>> S genericLoadTypes(final List<HxType> types,
                                                               int slotShift,
                                                               final S stream) {
    for (HxType type : types) {
      genericLoadSlot(type, slotShift, stream);
      slotShift += type.getSlotsCount();
    }
    return stream;
  }

  public static <S extends HxCodeStream<S>> S genericLoadSlot(final HxType type,
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

  public static <S extends HxCodeStream<S>> S genericStoreSlot(final HxType type,
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

  public static <S extends HxCodeStream<S>> S genericDuplicate(final HxType type,
                                                               final S stream) {
    switch (type.getSort()) {
      case VOID:
        throw new IllegalArgumentException("Invalid parameter: " + type);
      case LONG:
      case DOUBLE:
        return stream.DUP2();
      default:
        return stream.DUP();
    }
  }

  /**
   * @param instruction
   * @param slotOffset
   * @param variableSize
   */
  public static void shiftAccessToLocalVariable(final HxInstruction instruction,
                                                final int slotOffset,
                                                final int variableSize) {
    instruction.forEachNext(
      ins -> ins.hasSort(HxInstructionSort.Load) || ins.hasSort(HxInstructionSort.Store),
      ins -> shiftAccessToLocalVariables(ins, slotOffset, variableSize)
    );
  }

  private static void shiftAccessToLocalVariables(final HxInstruction instruction,
                                                  final int parametersOffset,
                                                  final int offsetAddition) {
    final AbstractLocalAccessInstruction accessInstruction = (AbstractLocalAccessInstruction) instruction;
    int slotIndex = accessInstruction.getOperand();
    if (slotIndex >= parametersOffset) {
      accessInstruction.setOperand(slotIndex + offsetAddition);
    }
  }


  public static <S extends HxCodeStream<S>> S wrapIfNeeded(final HxType possiblyPrimitiveType,
                                                           final S stream) {
    return wrapIfNeeded (possiblyPrimitiveType.getSort(), stream);
  }

  public static <S extends HxCodeStream<S>> S wrapIfNeeded(final HxSort sortOfType,
                                                           final S stream) {
    switch (sortOfType) {
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
        throw new IllegalArgumentException("Invalid type: " + sortOfType);
    }
    return stream;
  }
}
