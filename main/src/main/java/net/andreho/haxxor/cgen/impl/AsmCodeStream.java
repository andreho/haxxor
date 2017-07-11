package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxAsmUtils;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public class AsmCodeStream
    implements HxCodeStream {

  private static final int ZERO_FLOAT_RAW = Float.floatToRawIntBits(0.0f);
  private static final int ONE_FLOAT_RAW = Float.floatToRawIntBits(1.0f);
  private static final int TWO_FLOAT_RAW = Float.floatToRawIntBits(2.0f);
  private static final long ZERO_DOUBLE_RAW = Double.doubleToRawLongBits(0.0d);
  private static final long ONE_DOUBLE_RAW = Double.doubleToRawLongBits(1.0d);

  protected int lineNumber;
  private final MethodVisitor mv;
  private final HxMethod method;

  public AsmCodeStream(HxMethod method, MethodVisitor mv) {
    this.mv = mv;
    this.method = method;
  }

  protected String toInternalTypeName(String type) {
    return type;
  }

  protected Label toAsmLabel(LABEL label) {
    return HxAsmUtils.toAsmLabel(label);
  }

  protected Label[] toAsmLabels(LABEL... labels) {
    return HxAsmUtils.toAsmLabels(labels);
  }

  protected Type toAsmType(final HxType type) {
    return Type.getType(type.toDescriptor());
  }

  protected Handle toAsmHandle(final HxMethodHandle handle) {
    return HxAsmUtils.toAsmHandle(handle);
  }

  protected Type toAsmMethodType(final HxMethodType methodType) {
    return Type.getMethodType(methodType.getSignature());
  }

  protected Object[] toBootstrapArguments(HxArguments arguments) {
    final Object[] array = arguments.toArray();
    for (int i = 0; i < array.length; i++) {
      Object arg = array[i];
      Object val = arg;

      if(arg instanceof HxType) {
        val = Type.getType(((HxType) arg).toDescriptor());
      } else if(arg instanceof HxMethodHandle) {
        val = toAsmHandle((HxMethodHandle) arg);
      } else if(arg instanceof HxMethodType) {
        val = toAsmMethodType((HxMethodType) arg);
      }
      array[i] = val;
    }
    return array;
  }

  protected HxCodeStream visitCode() {
    this.mv.visitCode();
    return this;
  }

  protected HxCodeStream visitInsn(int opcode) {
    this.mv.visitInsn(opcode);
    return this;
  }

  protected HxCodeStream visitIntInsn(int opcode, int operand) {
    this.mv.visitIntInsn(opcode, operand);
    return this;
  }

  protected HxCodeStream visitVarInsn(int opcode, int var) {
    this.mv.visitVarInsn(opcode, var);
    return this;
  }

  protected HxCodeStream visitLdcInsn(Object cst) {
    if(cst instanceof HxMethodHandle) {
      cst = toAsmHandle((HxMethodHandle) cst);
    } else if(cst instanceof HxMethodType) {
      cst = toAsmMethodType((HxMethodType) cst);
    } else if(cst instanceof HxType) {
      cst = toAsmType((HxType) cst);
    }
    this.mv.visitLdcInsn(cst);
    return this;
  }

  protected HxCodeStream visitIincInsn(int var, int increment) {
    this.mv.visitIincInsn(var, increment);
    return this;
  }

  protected HxCodeStream visitJumpInsn(int opcode, LABEL label) {
    this.mv.visitJumpInsn(opcode, toAsmLabel(label));
    return this;
  }

  protected HxCodeStream visitTableSwitchInsn(int min, int max, LABEL defaultLabel, LABEL[] labels) {
    this.mv.visitTableSwitchInsn(min, max, toAsmLabel(defaultLabel), toAsmLabels(labels));
    return this;
  }

  protected HxCodeStream visitLookupSwitchInsn(LABEL defaultLabel, int[] keys, LABEL[] labels) {
    this.mv.visitLookupSwitchInsn(toAsmLabel(defaultLabel), keys, toAsmLabels(labels));
    return this;
  }

  protected HxCodeStream visitFieldInsn(int opcode, String owner, String name, String desc) {
    this.mv.visitFieldInsn(opcode, owner, name, desc);
    return this;
  }

  protected HxCodeStream visitMethodInsn(int opcode, String owner, String name, String desc, boolean isInterface) {
    this.mv.visitMethodInsn(opcode, owner, name, desc, isInterface);
    return this;
  }

  protected HxCodeStream visitInvokeDynamicInsn(String name, String desc, HxMethodHandle handle, HxArguments bsmArgs) {
    this.mv.visitInvokeDynamicInsn(name, desc, toAsmHandle(handle), toBootstrapArguments(bsmArgs));
    return this;
  }

  protected HxCodeStream visitTypeInsn(int opcode, String type) {
    this.mv.visitTypeInsn(opcode, type);
    return this;
  }

  protected HxCodeStream visitMultiANewArrayInsn(String desc, int dims) {
    this.mv.visitMultiANewArrayInsn(desc, dims);
    return this;
  }

  protected HxCodeStream visitLabel(LABEL label) {
    this.mv.visitLabel(toAsmLabel(label));
    return this;
  }

  protected HxCodeStream visitTryCatchBlock(LABEL start, LABEL end, LABEL handler, String type) {
    this.mv.visitTryCatchBlock(toAsmLabel(start), toAsmLabel(end), toAsmLabel(handler), type);
    return this;
  }

  protected HxCodeStream visitFrame(final HxFrames type,
                                    final int localsCount,
                                    final Object[] local,
                                    final int stackLength,
                                    final Object[] stack) {
    this.mv.visitFrame(type.getCode(), localsCount, local, stackLength, stack);
    return this;
  }

  protected HxCodeStream visitLocalVariable(String name, String desc, String signature, LABEL start, LABEL end, int index) {
    this.mv.visitLocalVariable(name, desc, signature, toAsmLabel(start), toAsmLabel(end), index);
    return this;
  }

  protected HxCodeStream visitLineNumber(final int line,
                                         final LABEL start) {
    this.mv.visitLineNumber(line, toAsmLabel(start));
    this.lineNumber = line;
    return this;
  }

  protected HxCodeStream visitMaxs(final int maxStack,
                                   final int maxLocals) {
    this.mv.visitMaxs(maxStack, maxLocals);
    return this;
  }

  protected void visitEnd() {
    this.mv.visitEnd();
  }

  @Override
  public HxCodeStream BEGIN() {
    return visitCode();
  }

  @Override
  public HxCodeStream NOP() {
    return visitInsn(Opcodes.NOP);
  }

  @Override
  public HxCodeStream THIS() {
    return ALOAD(0);
  }

  @Override
  public HxCodeStream ACONST_NULL() {
    return visitInsn(Opcodes.ACONST_NULL);
  }

  @Override
  public HxCodeStream ICONST_M1() {
    return visitInsn(Opcodes.ICONST_M1);
  }

  @Override
  public HxCodeStream ICONST_0() {
    return visitInsn(Opcodes.ICONST_0);
  }

  @Override
  public HxCodeStream ICONST_1() {
    return visitInsn(Opcodes.ICONST_1);
  }

  @Override
  public HxCodeStream ICONST_2() {
    return visitInsn(Opcodes.ICONST_2);
  }

  @Override
  public HxCodeStream ICONST_3() {
    return visitInsn(Opcodes.ICONST_3);
  }

  @Override
  public HxCodeStream ICONST_4() {
    return visitInsn(Opcodes.ICONST_4);
  }

  @Override
  public HxCodeStream ICONST_5() {
    return visitInsn(Opcodes.ICONST_5);
  }

  @Override
  public HxCodeStream LCONST_0() {
    return visitInsn(Opcodes.LCONST_0);
  }

  @Override
  public HxCodeStream LCONST_1() {
    return visitInsn(Opcodes.LCONST_1);
  }

  @Override
  public HxCodeStream FCONST_0() {
    return visitInsn(Opcodes.FCONST_0);
  }

  @Override
  public HxCodeStream FCONST_1() {
    return visitInsn(Opcodes.FCONST_1);
  }

  @Override
  public HxCodeStream FCONST_2() {
    return visitInsn(Opcodes.FCONST_2);
  }

  @Override
  public HxCodeStream DCONST_0() {
    return visitInsn(Opcodes.DCONST_0);
  }

  @Override
  public HxCodeStream DCONST_1() {
    return visitInsn(Opcodes.DCONST_1);
  }

  @Override
  public HxCodeStream BIPUSH(byte value) {
    return visitIntInsn(Opcodes.BIPUSH, value);
  }

  @Override
  public HxCodeStream SIPUSH(short value) {
    return visitIntInsn(Opcodes.SIPUSH, value);
  }

  @Override
  public HxCodeStream LDC(final int value) {
    switch (value) {
      case -1: {
        return ICONST_M1();
      }
      case 0: {
        return ICONST_0();
      }
      case 1: {
        return ICONST_1();
      }
      case 2: {
        return ICONST_2();
      }
      case 3: {
        return ICONST_3();
      }
      case 4: {
        return ICONST_4();
      }
      case 5: {
        return ICONST_5();
      }
      default: {
        if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
          return BIPUSH((byte) value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
          return SIPUSH((short) value);
        }
      }
    }
    return visitLdcInsn(value);
  }

  @Override
  public HxCodeStream LDC(final float value) {
    final int raw = Float.floatToRawIntBits(value);
    if (raw == ZERO_FLOAT_RAW) {
      return FCONST_0();
    } else if (raw == ONE_FLOAT_RAW) {
      return FCONST_1();
    } else if (raw == TWO_FLOAT_RAW) {
      return FCONST_2();
    }
    return visitLdcInsn(value);
  }

  @Override
  public HxCodeStream LDC(long value) {
    if (value == 0L) {
      return LCONST_0();
    } else if (value == 1L) {
      return LCONST_1();
    }
    return visitLdcInsn(value);
  }

  @Override
  public HxCodeStream LDC(final double value) {
    final long raw = Double.doubleToRawLongBits(value);
    if (raw == ZERO_DOUBLE_RAW) {
      return DCONST_0();
    } else if (raw == ONE_DOUBLE_RAW) {
      return DCONST_1();
    }
    return visitLdcInsn(value);
  }

  @Override
  public HxCodeStream LDC(String value) {
    return visitLdcInsn(value);
  }

  @Override
  public HxCodeStream HANDLE(HxMethodHandle handle) {
    return visitLdcInsn(handle);
  }

  @Override
  public HxCodeStream METHOD(HxMethodType methodType) {
    return visitLdcInsn(methodType);
  }

  @Override
  public HxCodeStream TYPE(String internalType) {
    return visitLdcInsn(Type.getType(internalType));
  }

  @Override
  public HxCodeStream ILOAD(int var) {
    return visitVarInsn(Opcodes.ILOAD, var);
  }

  @Override
  public HxCodeStream LLOAD(int var) {
    return visitVarInsn(Opcodes.LLOAD, var);
  }

  @Override
  public HxCodeStream FLOAD(int var) {
    return visitVarInsn(Opcodes.FLOAD, var);
  }

  @Override
  public HxCodeStream DLOAD(int var) {
    return visitVarInsn(Opcodes.DLOAD, var);
  }

  @Override
  public HxCodeStream ALOAD(int var) {
    return visitVarInsn(Opcodes.ALOAD, var);
  }

  @Override
  public HxCodeStream IALOAD() {
    return visitInsn(Opcodes.IALOAD);
  }

  @Override
  public HxCodeStream LALOAD() {
    return visitInsn(Opcodes.LALOAD);
  }

  @Override
  public HxCodeStream FALOAD() {
    return visitInsn(Opcodes.FALOAD);
  }

  @Override
  public HxCodeStream DALOAD() {
    return visitInsn(Opcodes.DALOAD);
  }

  @Override
  public HxCodeStream AALOAD() {
    return visitInsn(Opcodes.AALOAD);
  }

  @Override
  public HxCodeStream BALOAD() {
    return visitInsn(Opcodes.BALOAD);
  }

  @Override
  public HxCodeStream CALOAD() {
    return visitInsn(Opcodes.CALOAD);
  }

  @Override
  public HxCodeStream SALOAD() {
    return visitInsn(Opcodes.SALOAD);
  }

  @Override
  public HxCodeStream ISTORE(int var) {
    return visitVarInsn(Opcodes.ISTORE, var);
  }

  @Override
  public HxCodeStream LSTORE(int var) {
    return visitVarInsn(Opcodes.LSTORE, var);
  }

  @Override
  public HxCodeStream FSTORE(int var) {
    return visitVarInsn(Opcodes.FSTORE, var);
  }

  @Override
  public HxCodeStream DSTORE(int var) {
    return visitVarInsn(Opcodes.DSTORE, var);
  }

  @Override
  public HxCodeStream ASTORE(int var) {
    return visitVarInsn(Opcodes.ASTORE, var);
  }

  @Override
  public HxCodeStream IASTORE() {
    return visitInsn(Opcodes.IASTORE);
  }

  @Override
  public HxCodeStream LASTORE() {
    return visitInsn(Opcodes.LASTORE);
  }

  @Override
  public HxCodeStream FASTORE() {
    return visitInsn(Opcodes.FASTORE);
  }

  @Override
  public HxCodeStream DASTORE() {
    return visitInsn(Opcodes.DASTORE);
  }

  @Override
  public HxCodeStream AASTORE() {
    return visitInsn(Opcodes.AASTORE);
  }

  @Override
  public HxCodeStream BASTORE() {
    return visitInsn(Opcodes.BASTORE);
  }

  @Override
  public HxCodeStream CASTORE() {
    return visitInsn(Opcodes.CASTORE);
  }

  @Override
  public HxCodeStream SASTORE() {
    return visitInsn(Opcodes.SASTORE);
  }

  @Override
  public HxCodeStream POP() {
    return visitInsn(Opcodes.POP);
  }

  @Override
  public HxCodeStream POP2() {
    return visitInsn(Opcodes.POP2);
  }

  @Override
  public HxCodeStream DUP() {
    return visitInsn(Opcodes.DUP);
  }

  @Override
  public HxCodeStream DUP_X1() {
    return visitInsn(Opcodes.DUP_X1);
  }

  @Override
  public HxCodeStream DUP_X2() {
    return visitInsn(Opcodes.DUP_X2);
  }

  @Override
  public HxCodeStream DUP2() {
    return visitInsn(Opcodes.DUP2);
  }

  @Override
  public HxCodeStream DUP2_X1() {
    return visitInsn(Opcodes.DUP2_X1);
  }

  @Override
  public HxCodeStream DUP2_X2() {
    return visitInsn(Opcodes.DUP2_X2);
  }

  @Override
  public HxCodeStream SWAP() {
    return visitInsn(Opcodes.SWAP);
  }

  @Override
  public HxCodeStream IADD() {
    return visitInsn(Opcodes.IADD);
  }

  @Override
  public HxCodeStream LADD() {
    return visitInsn(Opcodes.LADD);
  }

  @Override
  public HxCodeStream FADD() {
    return visitInsn(Opcodes.FADD);
  }

  @Override
  public HxCodeStream DADD() {
    return visitInsn(Opcodes.DADD);
  }

  @Override
  public HxCodeStream ISUB() {
    return visitInsn(Opcodes.ISUB);
  }

  @Override
  public HxCodeStream LSUB() {
    return visitInsn(Opcodes.LSUB);
  }

  @Override
  public HxCodeStream FSUB() {
    return visitInsn(Opcodes.FSUB);
  }

  @Override
  public HxCodeStream DSUB() {
    return visitInsn(Opcodes.DSUB);
  }

  @Override
  public HxCodeStream IMUL() {
    return visitInsn(Opcodes.IMUL);
  }

  @Override
  public HxCodeStream LMUL() {
    return visitInsn(Opcodes.LMUL);
  }

  @Override
  public HxCodeStream FMUL() {
    return visitInsn(Opcodes.FMUL);
  }

  @Override
  public HxCodeStream DMUL() {
    return visitInsn(Opcodes.DMUL);
  }

  @Override
  public HxCodeStream IDIV() {
    return visitInsn(Opcodes.IDIV);
  }

  @Override
  public HxCodeStream LDIV() {
    return visitInsn(Opcodes.LDIV);
  }

  @Override
  public HxCodeStream FDIV() {
    return visitInsn(Opcodes.FDIV);
  }

  @Override
  public HxCodeStream DDIV() {
    return visitInsn(Opcodes.DDIV);
  }

  @Override
  public HxCodeStream IREM() {
    return visitInsn(Opcodes.IREM);
  }

  @Override
  public HxCodeStream LREM() {
    return visitInsn(Opcodes.LREM);
  }

  @Override
  public HxCodeStream FREM() {
    return visitInsn(Opcodes.FREM);
  }

  @Override
  public HxCodeStream DREM() {
    return visitInsn(Opcodes.DREM);
  }

  @Override
  public HxCodeStream INEG() {
    return visitInsn(Opcodes.INEG);
  }

  @Override
  public HxCodeStream LNEG() {
    return visitInsn(Opcodes.LNEG);
  }

  @Override
  public HxCodeStream FNEG() {
    return visitInsn(Opcodes.FNEG);
  }

  @Override
  public HxCodeStream DNEG() {
    return visitInsn(Opcodes.DNEG);
  }

  @Override
  public HxCodeStream ISHL() {
    return visitInsn(Opcodes.ISHL);
  }

  @Override
  public HxCodeStream LSHL() {
    return visitInsn(Opcodes.LSHL);
  }

  @Override
  public HxCodeStream ISHR() {
    return visitInsn(Opcodes.ISHR);
  }

  @Override
  public HxCodeStream LSHR() {
    return visitInsn(Opcodes.LSHR);
  }

  @Override
  public HxCodeStream IUSHR() {
    return visitInsn(Opcodes.IUSHR);
  }

  @Override
  public HxCodeStream LUSHR() {
    return visitInsn(Opcodes.LUSHR);
  }

  @Override
  public HxCodeStream IAND() {
    return visitInsn(Opcodes.IAND);
  }

  @Override
  public HxCodeStream LAND() {
    return visitInsn(Opcodes.LAND);
  }

  @Override
  public HxCodeStream IOR() {
    return visitInsn(Opcodes.IOR);
  }

  @Override
  public HxCodeStream LOR() {
    return visitInsn(Opcodes.LOR);
  }

  @Override
  public HxCodeStream IXOR() {
    return visitInsn(Opcodes.IXOR);
  }

  @Override
  public HxCodeStream LXOR() {
    return visitInsn(Opcodes.LXOR);
  }

  @Override
  public HxCodeStream IINC(int var, int increment) {
    return visitIincInsn(var, increment);
  }

  @Override
  public HxCodeStream I2L() {
    return visitInsn(Opcodes.I2L);
  }

  @Override
  public HxCodeStream I2F() {
    return visitInsn(Opcodes.I2F);
  }

  @Override
  public HxCodeStream I2D() {
    return visitInsn(Opcodes.I2D);
  }

  @Override
  public HxCodeStream L2I() {
    return visitInsn(Opcodes.L2I);
  }

  @Override
  public HxCodeStream L2F() {
    return visitInsn(Opcodes.L2F);
  }

  @Override
  public HxCodeStream L2D() {
    return visitInsn(Opcodes.L2D);
  }

  @Override
  public HxCodeStream F2I() {
    return visitInsn(Opcodes.F2I);
  }

  @Override
  public HxCodeStream F2L() {
    return visitInsn(Opcodes.F2L);
  }

  @Override
  public HxCodeStream F2D() {
    return visitInsn(Opcodes.F2D);
  }

  @Override
  public HxCodeStream D2I() {
    return visitInsn(Opcodes.D2I);
  }

  @Override
  public HxCodeStream D2L() {
    return visitInsn(Opcodes.D2L);
  }

  @Override
  public HxCodeStream D2F() {
    return visitInsn(Opcodes.D2F);
  }

  @Override
  public HxCodeStream I2B() {
    return visitInsn(Opcodes.I2B);
  }

  @Override
  public HxCodeStream I2C() {
    return visitInsn(Opcodes.I2C);
  }

  @Override
  public HxCodeStream I2S() {
    return visitInsn(Opcodes.I2S);
  }

  @Override
  public HxCodeStream LCMP() {
    return visitInsn(Opcodes.LCMP);
  }

  @Override
  public HxCodeStream FCMPL() {
    return visitInsn(Opcodes.FCMPL);
  }

  @Override
  public HxCodeStream FCMPG() {
    return visitInsn(Opcodes.FCMPG);
  }

  @Override
  public HxCodeStream DCMPL() {
    return visitInsn(Opcodes.DCMPL);
  }

  @Override
  public HxCodeStream DCMPG() {
    return visitInsn(Opcodes.DCMPG);
  }

  @Override
  public HxCodeStream IFEQ(LABEL label) {
    return visitJumpInsn(Opcodes.IFEQ, label);
  }

  @Override
  public HxCodeStream IFNE(LABEL label) {
    return visitJumpInsn(Opcodes.IFNE, label);
  }

  @Override
  public HxCodeStream IFLT(LABEL label) {
    return visitJumpInsn(Opcodes.IFLT, label);
  }

  @Override
  public HxCodeStream IFGE(LABEL label) {
    return visitJumpInsn(Opcodes.IFGE, label);
  }

  @Override
  public HxCodeStream IFGT(LABEL label) {
    return visitJumpInsn(Opcodes.IFGT, label);
  }

  @Override
  public HxCodeStream IFLE(LABEL label) {
    return visitJumpInsn(Opcodes.IFLE, label);
  }

  @Override
  public HxCodeStream IF_ICMPEQ(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ICMPEQ, label);
  }

  @Override
  public HxCodeStream IF_ICMPNE(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ICMPNE, label);
  }

  @Override
  public HxCodeStream IF_ICMPLT(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ICMPLT, label);
  }

  @Override
  public HxCodeStream IF_ICMPGE(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ICMPGE, label);
  }

  @Override
  public HxCodeStream IF_ICMPGT(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ICMPGT, label);
  }

  @Override
  public HxCodeStream IF_ICMPLE(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ICMPLE, label);
  }

  @Override
  public HxCodeStream IF_ACMPEQ(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ACMPEQ, label);
  }

  @Override
  public HxCodeStream IF_ACMPNE(LABEL label) {
    return visitJumpInsn(Opcodes.IF_ACMPNE, label);
  }

  @Override
  public HxCodeStream GOTO(LABEL label) {
    return visitJumpInsn(Opcodes.GOTO, label);
  }

  @Override
  public HxCodeStream JSR(LABEL label) {
    return visitJumpInsn(Opcodes.JSR, label);
  }

  @Override
  public HxCodeStream RET(int var) {
    return visitVarInsn(Opcodes.RET, var);
  }

  @Override
  public HxCodeStream TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels) {
    return visitTableSwitchInsn(min, max, defaultLabel, labels);
  }

  @Override
  public HxCodeStream LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL[] labels) {
    return visitLookupSwitchInsn(defaultLabel, keys, labels);
  }

  @Override
  public HxCodeStream IRETURN() {
    return visitInsn(Opcodes.IRETURN);
  }

  @Override
  public HxCodeStream LRETURN() {
    return visitInsn(Opcodes.LRETURN);
  }

  @Override
  public HxCodeStream FRETURN() {
    return visitInsn(Opcodes.FRETURN);
  }

  @Override
  public HxCodeStream DRETURN() {
    return visitInsn(Opcodes.DRETURN);
  }

  @Override
  public HxCodeStream ARETURN() {
    return visitInsn(Opcodes.ARETURN);
  }

  @Override
  public HxCodeStream RETURN() {
    return visitInsn(Opcodes.RETURN);
  }

  @Override
  public HxCodeStream GETSTATIC(String owner, String name, String desc) {
    return visitFieldInsn(Opcodes.GETSTATIC, owner, name, desc);
  }

  @Override
  public HxCodeStream PUTSTATIC(String owner, String name, String desc) {
    return visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
  }

  @Override
  public HxCodeStream GETFIELD(String owner, String name, String desc) {
    return visitFieldInsn(Opcodes.GETFIELD, owner, name, desc);
  }

  @Override
  public HxCodeStream PUTFIELD(String owner, String name, String desc) {
    return visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
  }

  @Override
  public HxCodeStream INVOKEVIRTUAL(String owner, String name, String desc) {
    return visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, desc, false);
  }

  @Override
  public HxCodeStream INVOKESPECIAL(String owner, String name, String desc) {
    return visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, desc, false);
  }

  @Override
  public HxCodeStream INVOKESTATIC(String owner, String name, String desc, boolean isInterface) {
    return visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, desc, isInterface);
  }

  @Override
  public HxCodeStream INVOKEINTERFACE(String owner, String name, String desc) {
    return visitMethodInsn(Opcodes.INVOKEINTERFACE, owner, name, desc, true);
  }

  @Override
  public HxCodeStream INVOKEDYNAMIC(String name, String desc, HxMethodHandle bsm, HxArguments bsmArgs) {
    return visitInvokeDynamicInsn(name, toInternalTypeName(desc), bsm, bsmArgs);
  }

  @Override
  public HxCodeStream NEW(String internalType) {
    return visitTypeInsn(Opcodes.NEW, toInternalTypeName(internalType));
  }

  @Override
  public HxCodeStream NEWARRAY(HxArrayType type) {
    return visitIntInsn(Opcodes.NEWARRAY, type.getCode());
  }

  @Override
  public HxCodeStream ANEWARRAY(String internalType) {
    return visitTypeInsn(Opcodes.ANEWARRAY, toInternalTypeName(internalType));
  }

  @Override
  public HxCodeStream MULTIANEWARRAY(String internalType, int dims) {
    return visitMultiANewArrayInsn(toInternalTypeName(internalType), dims);
  }

  @Override
  public HxCodeStream ARRAYLENGTH() {
    return visitInsn(Opcodes.ARRAYLENGTH);
  }

  @Override
  public HxCodeStream ATHROW() {
    return visitInsn(Opcodes.ATHROW);
  }

  @Override
  public HxCodeStream CHECKCAST(String internalType) {
    return visitTypeInsn(Opcodes.CHECKCAST, toInternalTypeName(internalType));
  }

  @Override
  public HxCodeStream INSTANCEOF(String internalType) {
    return visitTypeInsn(Opcodes.INSTANCEOF, toInternalTypeName(internalType));
  }

  @Override
  public HxCodeStream MONITORENTER() {
    return visitInsn(Opcodes.MONITORENTER);
  }

  @Override
  public HxCodeStream MONITOREXIT() {
    return visitInsn(Opcodes.MONITOREXIT);
  }

  @Override
  public HxCodeStream IFNULL(LABEL label) {
    return visitJumpInsn(Opcodes.IFNULL, label);
  }

  @Override
  public HxCodeStream IFNONNULL(LABEL label) {
    return visitJumpInsn(Opcodes.IFNONNULL, label);
  }

  @Override
  public HxCodeStream LABEL(LABEL label) {
    return visitLabel(label);
  }

  @Override
  public HxCodeStream TRY_CATCH(LABEL startLabel, LABEL endLabel, LABEL handler, String exceptionTypename) {
    return visitTryCatchBlock(startLabel, endLabel, handler, exceptionTypename);
  }

  @Override
  public HxCodeStream FRAME(HxFrames type, int localsCount, Object[] local, int stackLength, Object[] stack) {
    return visitFrame(type, localsCount, local, stackLength, stack);
  }

  @Override
  public HxCodeStream LOCAL_VARIABLE(String name,
                                     int index,
                                     String desc,
                                     String signature,
                                     LABEL start,
                                     LABEL end) {
    return visitLocalVariable(name, desc, signature, start, end, index);
  }

  @Override
  public HxCodeStream LINE_NUMBER(int line, LABEL start) {
    return visitLineNumber(line, start);
  }

  @Override
  public HxCodeStream MAXS(int maxStack, int maxLocals) {
    return visitMaxs(maxStack, maxLocals);
  }

  @Override
  public void END() {
    visitEnd();
  }
}
