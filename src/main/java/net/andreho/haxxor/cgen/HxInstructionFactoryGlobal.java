package net.andreho.haxxor.cgen;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public interface HxInstructionFactoryGlobal {
  class FactoryHolder {
    static volatile HxInstructionFactory instance = new HxInstructionFactory() {};
    public static HxInstructionFactory instance() {
      return instance;
    }
    public static void overrideDefault(HxInstructionFactory instructionFactory) {
      instance = Objects.requireNonNull(instructionFactory);
    }
  }
  static HxInstruction BEGIN() {
    return FactoryHolder.instance.BEGIN();
  }

  static HxInstruction END() {
    return FactoryHolder.instance.END();
  }

  static HxInstruction NOP() {
    return FactoryHolder.instance.NOP();
  }

  static HxInstruction ACONST_NULL() {
    return FactoryHolder.instance.ACONST_NULL();
  }

  static HxInstruction ICONST(int value) {
    return FactoryHolder.instance.ICONST(value);
  }

  static HxInstruction LCONST(long value) {
    return FactoryHolder.instance.LCONST(value);
  }

  static HxInstruction FCONST(float value) {
    return FactoryHolder.instance.FCONST(value);
  }

  static HxInstruction DCONST(double value) {
    return FactoryHolder.instance.DCONST(value);
  }

  static HxInstruction BIPUSH(byte value) {
    return FactoryHolder.instance.BIPUSH(value);
  }

  static HxInstruction SIPUSH(short value) {
    return FactoryHolder.instance.SIPUSH(value);
  }

  static HxInstruction LDC(int value) {
    return FactoryHolder.instance.LDC(value);
  }

  static HxInstruction LDC(float value) {
    return FactoryHolder.instance.LDC(value);
  }

  static HxInstruction LDC(long value) {
    return FactoryHolder.instance.LDC(value);
  }

  static HxInstruction LDC(double value) {
    return FactoryHolder.instance.LDC(value);
  }

  static HxInstruction LDC(String value) {
    return FactoryHolder.instance.LDC(value);
  }

  static HxInstruction HANDLE(HxMethodHandle handle) {
    return FactoryHolder.instance.HANDLE(handle);
  }

  static HxInstruction METHOD(HxMethodType methodType) {
    return FactoryHolder.instance.METHOD(methodType);
  }

  static HxInstruction TYPE(String value) {
    return FactoryHolder.instance.TYPE(value);
  }

  static HxInstruction TYPE(HxType value) {
    return FactoryHolder.instance.TYPE(value);
  }

  static HxInstruction ILOAD(int var) {
    return FactoryHolder.instance.ILOAD(var);
  }

  static HxInstruction LLOAD(int var) {
    return FactoryHolder.instance.LLOAD(var);
  }

  static HxInstruction FLOAD(int var) {
    return FactoryHolder.instance.FLOAD(var);
  }

  static HxInstruction DLOAD(int var) {
    return FactoryHolder.instance.DLOAD(var);
  }

  static HxInstruction ALOAD(int var) {
    return FactoryHolder.instance.ALOAD(var);
  }

  static HxInstruction IALOAD() {
    return FactoryHolder.instance.IALOAD();
  }

  static HxInstruction LALOAD() {
    return FactoryHolder.instance.LALOAD();
  }

  static HxInstruction FALOAD() {
    return FactoryHolder.instance.FALOAD();
  }

  static HxInstruction DALOAD() {
    return FactoryHolder.instance.DALOAD();
  }

  static HxInstruction AALOAD() {
    return FactoryHolder.instance.AALOAD();
  }

  static HxInstruction BALOAD() {
    return FactoryHolder.instance.BALOAD();
  }

  static HxInstruction CALOAD() {
    return FactoryHolder.instance.CALOAD();
  }

  static HxInstruction SALOAD() {
    return FactoryHolder.instance.SALOAD();
  }

  static HxInstruction ISTORE(int var) {
    return FactoryHolder.instance.ISTORE(var);
  }

  static HxInstruction LSTORE(int var) {
    return FactoryHolder.instance.LSTORE(var);
  }

  static HxInstruction FSTORE(int var) {
    return FactoryHolder.instance.FSTORE(var);
  }

  static HxInstruction DSTORE(int var) {
    return FactoryHolder.instance.DSTORE(var);
  }

  static HxInstruction ASTORE(int var) {
    return FactoryHolder.instance.ASTORE(var);
  }

  static HxInstruction IASTORE() {
    return FactoryHolder.instance.IASTORE();
  }

  static HxInstruction LASTORE() {
    return FactoryHolder.instance.LASTORE();
  }

  static HxInstruction FASTORE() {
    return FactoryHolder.instance.FASTORE();
  }

  static HxInstruction DASTORE() {
    return FactoryHolder.instance.DASTORE();
  }

  static HxInstruction AASTORE() {
    return FactoryHolder.instance.AASTORE();
  }

  static HxInstruction BASTORE() {
    return FactoryHolder.instance.BASTORE();
  }

  static HxInstruction CASTORE() {
    return FactoryHolder.instance.CASTORE();
  }

  static HxInstruction SASTORE() {
    return FactoryHolder.instance.SASTORE();
  }

  static HxInstruction POP() {
    return FactoryHolder.instance.POP();
  }

  static HxInstruction POP2() {
    return FactoryHolder.instance.POP2();
  }

  static HxInstruction DUP() {
    return FactoryHolder.instance.DUP();
  }

  static HxInstruction DUP_X1() {
    return FactoryHolder.instance.DUP_X1();
  }

  static HxInstruction DUP_X2() {
    return FactoryHolder.instance.DUP_X2();
  }

  static HxInstruction DUP2() {
    return FactoryHolder.instance.DUP2();
  }

  static HxInstruction DUP2_X1() {
    return FactoryHolder.instance.DUP2_X1();
  }

  static HxInstruction DUP2_X2() {
    return FactoryHolder.instance.DUP2_X2();
  }

  static HxInstruction SWAP() {
    return FactoryHolder.instance.SWAP();
  }

  static HxInstruction IADD() {
    return FactoryHolder.instance.IADD();
  }

  static HxInstruction LADD() {
    return FactoryHolder.instance.LADD();
  }

  static HxInstruction FADD() {
    return FactoryHolder.instance.FADD();
  }

  static HxInstruction DADD() {
    return FactoryHolder.instance.DADD();
  }

  static HxInstruction ISUB() {
    return FactoryHolder.instance.ISUB();
  }

  static HxInstruction LSUB() {
    return FactoryHolder.instance.LSUB();
  }

  static HxInstruction FSUB() {
    return FactoryHolder.instance.FSUB();
  }

  static HxInstruction DSUB() {
    return FactoryHolder.instance.DSUB();
  }

  static HxInstruction IMUL() {
    return FactoryHolder.instance.IMUL();
  }

  static HxInstruction LMUL() {
    return FactoryHolder.instance.LMUL();
  }

  static HxInstruction FMUL() {
    return FactoryHolder.instance.FMUL();
  }

  static HxInstruction DMUL() {
    return FactoryHolder.instance.DMUL();
  }

  static HxInstruction IDIV() {
    return FactoryHolder.instance.IDIV();
  }

  static HxInstruction LDIV() {
    return FactoryHolder.instance.LDIV();
  }

  static HxInstruction FDIV() {
    return FactoryHolder.instance.FDIV();
  }

  static HxInstruction DDIV() {
    return FactoryHolder.instance.DDIV();
  }

  static HxInstruction IREM() {
    return FactoryHolder.instance.IREM();
  }

  static HxInstruction LREM() {
    return FactoryHolder.instance.LREM();
  }

  static HxInstruction FREM() {
    return FactoryHolder.instance.FREM();
  }

  static HxInstruction DREM() {
    return FactoryHolder.instance.DREM();
  }

  static HxInstruction INEG() {
    return FactoryHolder.instance.INEG();
  }

  static HxInstruction LNEG() {
    return FactoryHolder.instance.LNEG();
  }

  static HxInstruction FNEG() {
    return FactoryHolder.instance.FNEG();
  }

  static HxInstruction DNEG() {
    return FactoryHolder.instance.DNEG();
  }

  static HxInstruction ISHL() {
    return FactoryHolder.instance.ISHL();
  }

  static HxInstruction LSHL() {
    return FactoryHolder.instance.LSHL();
  }

  static HxInstruction ISHR() {
    return FactoryHolder.instance.ISHR();
  }

  static HxInstruction LSHR() {
    return FactoryHolder.instance.LSHR();
  }

  static HxInstruction IUSHR() {
    return FactoryHolder.instance.IUSHR();
  }

  static HxInstruction LUSHR() {
    return FactoryHolder.instance.LUSHR();
  }

  static HxInstruction IAND() {
    return FactoryHolder.instance.IAND();
  }

  static HxInstruction LAND() {
    return FactoryHolder.instance.LAND();
  }

  static HxInstruction IOR() {
    return FactoryHolder.instance.IOR();
  }

  static HxInstruction LOR() {
    return FactoryHolder.instance.LOR();
  }

  static HxInstruction IXOR() {
    return FactoryHolder.instance.IXOR();
  }

  static HxInstruction LXOR() {
    return FactoryHolder.instance.LXOR();
  }

  static HxInstruction IINC(int var,
                            int increment) {
    return FactoryHolder.instance.IINC(var, increment);
  }

  static HxInstruction I2L() {
    return FactoryHolder.instance.I2L();
  }

  static HxInstruction I2F() {
    return FactoryHolder.instance.I2F();
  }

  static HxInstruction I2D() {
    return FactoryHolder.instance.I2D();
  }

  static HxInstruction L2I() {
    return FactoryHolder.instance.L2I();
  }

  static HxInstruction L2F() {
    return FactoryHolder.instance.L2F();
  }

  static HxInstruction L2D() {
    return FactoryHolder.instance.L2D();
  }

  static HxInstruction F2I() {
    return FactoryHolder.instance.F2I();
  }

  static HxInstruction F2L() {
    return FactoryHolder.instance.F2L();
  }

  static HxInstruction F2D() {
    return FactoryHolder.instance.F2D();
  }

  static HxInstruction D2I() {
    return FactoryHolder.instance.D2I();
  }

  static HxInstruction D2L() {
    return FactoryHolder.instance.D2L();
  }

  static HxInstruction D2F() {
    return FactoryHolder.instance.D2F();
  }

  static HxInstruction I2B() {
    return FactoryHolder.instance.I2B();
  }

  static HxInstruction I2C() {
    return FactoryHolder.instance.I2C();
  }

  static HxInstruction I2S() {
    return FactoryHolder.instance.I2S();
  }

  static HxInstruction LCMP() {
    return FactoryHolder.instance.LCMP();
  }

  static HxInstruction FCMPL() {
    return FactoryHolder.instance.FCMPL();
  }

  static HxInstruction FCMPG() {
    return FactoryHolder.instance.FCMPG();
  }

  static HxInstruction DCMPL() {
    return FactoryHolder.instance.DCMPL();
  }

  static HxInstruction DCMPG() {
    return FactoryHolder.instance.DCMPG();
  }

  static HxInstruction IFEQ(LABEL label) {
    return  FactoryHolder.instance.IFEQ(label);
  }

  static HxInstruction IFNE(LABEL label) {
    return  FactoryHolder.instance.IFNE(label);
  }

  static HxInstruction IFLT(LABEL label) {
    return  FactoryHolder.instance.IFLT(label);
  }

  static HxInstruction IFGE(LABEL label) {
    return  FactoryHolder.instance.IFGE(label);
  }

  static HxInstruction IFGT(LABEL label) {
    return  FactoryHolder.instance.IFGT(label);
  }

  static HxInstruction IFLE(LABEL label) {
    return  FactoryHolder.instance.IFLE(label);
  }

  static HxInstruction IF_ICMPEQ(LABEL label) {
    return  FactoryHolder.instance.IF_ICMPEQ(label);
  }

  static HxInstruction IF_ICMPNE(LABEL label) {
    return  FactoryHolder.instance.IF_ICMPNE(label);
  }

  static HxInstruction IF_ICMPLT(LABEL label) {
    return  FactoryHolder.instance.IF_ICMPLT(label);
  }

  static HxInstruction IF_ICMPGE(LABEL label) {
    return  FactoryHolder.instance.IF_ICMPGE(label);
  }

  static HxInstruction IF_ICMPGT(LABEL label) {
    return  FactoryHolder.instance.IF_ICMPGT(label);
  }

  static HxInstruction IF_ICMPLE(LABEL label) {
    return  FactoryHolder.instance.IF_ICMPLE(label);
  }

  static HxInstruction IF_ACMPEQ(LABEL label) {
    return  FactoryHolder.instance.IF_ACMPEQ(label);
  }

  static HxInstruction IF_ACMPNE(LABEL label) {
    return  FactoryHolder.instance.IF_ACMPNE(label);
  }

  static HxInstruction GOTO(LABEL label) {
    return  FactoryHolder.instance.GOTO(label);
  }

  static HxInstruction JSR(LABEL label) {
    return  FactoryHolder.instance.JSR(label);
  }

  static HxInstruction RET(int var) {
    return FactoryHolder.instance.RET(var);
  }

  static HxInstruction TABLESWITCH(int min,
                                   int max,
                                   LABEL defaultLabel,
                                   LABEL... labels) {
    return FactoryHolder.instance.TABLESWITCH(min, max, defaultLabel, labels);
  }

  static HxInstruction LOOKUPSWITCH(LABEL defaultLabel,
                                    int[] keys,
                                    LABEL... labels) {
    return FactoryHolder.instance.LOOKUPSWITCH(defaultLabel, keys, labels);
  }

  static HxInstruction IRETURN() {
    return FactoryHolder.instance.IRETURN();
  }

  static HxInstruction LRETURN() {
    return FactoryHolder.instance.LRETURN();
  }

  static HxInstruction FRETURN() {
    return FactoryHolder.instance.FRETURN();
  }

  static HxInstruction DRETURN() {
    return FactoryHolder.instance.DRETURN();
  }

  static HxInstruction ARETURN() {
    return FactoryHolder.instance.ARETURN();
  }

  static HxInstruction RETURN() {
    return FactoryHolder.instance.RETURN();
  }

  static HxInstruction GETSTATIC(String type,
                                 String name,
                                 String fieldDesc) {
    return FactoryHolder.instance.GETSTATIC(type, name, fieldDesc);
  }

  static HxInstruction PUTSTATIC(String type,
                                 String name,
                                 String fieldDesc) {
    return FactoryHolder.instance.PUTSTATIC(type, name, fieldDesc);
  }

  static HxInstruction GETFIELD(String type,
                                String name,
                                String fieldDesc) {
    return FactoryHolder.instance.GETFIELD(type, name, fieldDesc);
  }

  static HxInstruction PUTFIELD(String type,
                                String name,
                                String fieldDesc) {
    return FactoryHolder.instance.PUTFIELD(type, name, fieldDesc);
  }


  static HxInstruction INVOKEVIRTUAL(String type,
                                     String name,
                                     String methodDesc) {
    return FactoryHolder.instance.INVOKEVIRTUAL(type, name, methodDesc);
  }

  static HxInstruction INVOKESPECIAL(String type,
                                     String name,
                                     String methodDesc) {
    return FactoryHolder.instance.INVOKESPECIAL(type, name, methodDesc);
  }

  static HxInstruction INVOKESTATIC(String type,
                                    String name,
                                    String methodDesc,
                                    boolean isInterface) {
    return FactoryHolder.instance.INVOKESTATIC(type, name, methodDesc, isInterface);
  }

  static HxInstruction INVOKEINTERFACE(String type,
                                       String name,
                                       String methodDesc) {
    return FactoryHolder.instance.INVOKEINTERFACE(type, name, methodDesc);
  }

  static HxInstruction INVOKEDYNAMIC(String name,
                                     String methodDesc,
                                     HxMethodHandle bootstrapMethod,
                                     HxArguments bsmArgs) {
    return FactoryHolder.instance.INVOKEDYNAMIC(name, methodDesc, bootstrapMethod, bsmArgs);
  }

  static HxInstruction NEW(String type) {
    return FactoryHolder.instance.NEW(type);
  }

  static HxInstruction NEWARRAY(HxArrayType type) {
    return FactoryHolder.instance.NEWARRAY(type);
  }

  static HxInstruction ANEWARRAY(String type) {
    return FactoryHolder.instance.ANEWARRAY(type);
  }

  static HxInstruction ARRAYLENGTH() {
    return FactoryHolder.instance.ARRAYLENGTH();
  }

  static HxInstruction ATHROW() {
    return FactoryHolder.instance.ATHROW();
  }

  static HxInstruction CHECKCAST(String type) {
    return FactoryHolder.instance.CHECKCAST(type);
  }

  static HxInstruction INSTANCEOF(String type) {
    return FactoryHolder.instance.INSTANCEOF(type);
  }

  static HxInstruction MONITORENTER() {
    return FactoryHolder.instance.MONITORENTER();
  }

  static HxInstruction MONITOREXIT() {
    return FactoryHolder.instance.MONITOREXIT();
  }

  static HxInstruction MULTIANEWARRAY(String type,
                                      int dims) {
    return FactoryHolder.instance.MULTIANEWARRAY(type, dims);
  }

  static HxInstruction IFNULL(LABEL label) {
    return FactoryHolder.instance.IFNULL(label);
  }

  static HxInstruction IFNONNULL(LABEL label) {
    return FactoryHolder.instance.IFNONNULL(label);
  }

  static HxInstruction FRAME(final HxFrames type,
                             final int nLocal,
                             final Object[] local,
                             final int nStack,
                             final Object[] stack) {
    return FactoryHolder.instance.FRAME(type, nLocal, local, nStack, stack);
  }

  static HxInstruction LINE_NUMBER(int line,
                                   LABEL label) {
    return FactoryHolder.instance.LINE_NUMBER(line, label);
  }

  static HxInstruction LABEL() {
    return FactoryHolder.instance.LABEL();
  }
}