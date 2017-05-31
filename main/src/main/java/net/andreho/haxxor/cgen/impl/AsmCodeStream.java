package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxParameterizable;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public class AsmCodeStream
    implements CodeStream {

  private static final int ZERO_FLOAT_RAW = Float.floatToRawIntBits(0.0f);
  private static final int ONE_FLOAT_RAW = Float.floatToRawIntBits(1.0f);
  private static final int TWO_FLOAT_RAW = Float.floatToRawIntBits(2.0f);
  private static final long ZERO_DOUBLE_RAW = Double.doubleToRawLongBits(0.0d);
  private static final long ONE_DOUBLE_RAW = Double.doubleToRawLongBits(1.0d);

  protected int lineNumber;
  private final MethodVisitor mv;
  private final HxParameterizable element;

  public AsmCodeStream(HxParameterizable element, MethodVisitor mv) {
    this.mv = mv;
    this.element = element;
  }

  protected String asType(String type) {
    return type;
  }

  protected Label label(LABEL label) {
    return label.getAsmLabel();
  }

  protected Label[] labels(LABEL... labels) {
    Label[] result = new Label[labels.length];

    for (int i = 0; i < labels.length; i++) {
      result[i] = label(labels[i]);
    }
    return result;
  }

  @Override
  public CodeStream BEGIN() {
    this.mv.visitCode();
    return this;
  }

  @Override
  public CodeStream NOP() {
    this.mv.visitInsn(Opcodes.NOP);
    return this;
  }

  @Override
  public CodeStream THIS() {
    ALOAD(0);
    return this;
  }

  @Override
  public CodeStream ACONST_NULL() {
    this.mv.visitInsn(Opcodes.ACONST_NULL);
    return this;
  }

  @Override
  public CodeStream ICONST_M1() {
    this.mv.visitInsn(Opcodes.ICONST_M1);
    return this;
  }

  @Override
  public CodeStream ICONST_0() {
    this.mv.visitInsn(Opcodes.ICONST_0);
    return this;
  }

  @Override
  public CodeStream ICONST_1() {
    this.mv.visitInsn(Opcodes.ICONST_1);
    return this;
  }

  @Override
  public CodeStream ICONST_2() {
    this.mv.visitInsn(Opcodes.ICONST_2);
    return this;
  }

  @Override
  public CodeStream ICONST_3() {
    this.mv.visitInsn(Opcodes.ICONST_3);
    return this;
  }

  @Override
  public CodeStream ICONST_4() {
    this.mv.visitInsn(Opcodes.ICONST_4);
    return this;
  }

  @Override
  public CodeStream ICONST_5() {
    this.mv.visitInsn(Opcodes.ICONST_5);
    return this;
  }

  @Override
  public CodeStream LCONST_0() {
    this.mv.visitInsn(Opcodes.LCONST_0);
    return this;
  }

  @Override
  public CodeStream LCONST_1() {
    this.mv.visitInsn(Opcodes.LCONST_1);
    return this;
  }

  @Override
  public CodeStream FCONST_0() {
    this.mv.visitInsn(Opcodes.FCONST_0);
    return this;
  }

  @Override
  public CodeStream FCONST_1() {
    this.mv.visitInsn(Opcodes.FCONST_1);
    return this;
  }

  @Override
  public CodeStream FCONST_2() {
    this.mv.visitInsn(Opcodes.FCONST_2);
    return this;
  }

  @Override
  public CodeStream DCONST_0() {
    this.mv.visitInsn(Opcodes.DCONST_0);
    return this;
  }

  @Override
  public CodeStream DCONST_1() {
    this.mv.visitInsn(Opcodes.DCONST_1);
    return this;
  }

  @Override
  public CodeStream BIPUSH(byte value) {
    this.mv.visitIntInsn(Opcodes.BIPUSH, value);
    return this;
  }

  @Override
  public CodeStream SIPUSH(short value) {
    this.mv.visitIntInsn(Opcodes.SIPUSH, value);
    return this;
  }

  @Override
  public CodeStream LDC(final int value) {
    switch (value) {
      case -1: {
        ICONST_M1();
      }
      break;
      case 0: {
        ICONST_0();
      }
      break;
      case 1: {
        ICONST_1();
      }
      break;
      case 2: {
        ICONST_2();
      }
      break;
      case 3: {
        ICONST_3();
      }
      break;
      case 4: {
        ICONST_4();
      }
      break;
      case 5: {
        ICONST_5();
      }
      break;
      default: {
        if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
          BIPUSH((byte) value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
          SIPUSH((short) value);
        } else {
          this.mv.visitLdcInsn(value);
        }
      }
    }
    return this;
  }

  @Override
  public CodeStream LDC(final float value) {
    final int raw = Float.floatToRawIntBits(value);
    if (raw == ZERO_FLOAT_RAW) {
      FCONST_0();
    } else if (raw == ONE_FLOAT_RAW) {
      FCONST_1();
    } else if (raw == TWO_FLOAT_RAW) {
      FCONST_2();
    } else {
      this.mv.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public CodeStream LDC(long value) {
    if (value == 0L) {
      LCONST_0();
    } else if (value == 1L) {
      LCONST_1();
    } else {
      this.mv.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public CodeStream LDC(final double value) {
    final long raw = Double.doubleToRawLongBits(value);
    if (raw == ZERO_DOUBLE_RAW) {
      DCONST_0();
    } else if (raw == ONE_DOUBLE_RAW) {
      DCONST_1();
    } else {
      this.mv.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public CodeStream LDC(String value) {
    this.mv.visitLdcInsn(value);
    return this;
  }

  @Override
  public CodeStream HANDLE(Handle handle) {
    this.mv.visitLdcInsn(handle);
    return this;
  }

  @Override
  public CodeStream METHOD(String methodDescriptor) {
    this.mv.visitLdcInsn(Type.getMethodType(methodDescriptor));
    return this;
  }

  @Override
  public CodeStream TYPE(String internalType) {
    this.mv.visitLdcInsn(Type.getType(internalType));
    return this;
  }

  @Override
  public CodeStream ILOAD(int var) {
    this.mv.visitVarInsn(Opcodes.ILOAD, var);
    return this;
  }

  @Override
  public CodeStream LLOAD(int var) {
    this.mv.visitVarInsn(Opcodes.LLOAD, var);
    return this;
  }

  @Override
  public CodeStream FLOAD(int var) {
    this.mv.visitVarInsn(Opcodes.FLOAD, var);
    return this;
  }

  @Override
  public CodeStream DLOAD(int var) {
    this.mv.visitVarInsn(Opcodes.DLOAD, var);
    return this;
  }

  @Override
  public CodeStream ALOAD(int var) {
    this.mv.visitVarInsn(Opcodes.ALOAD, var);
    return this;
  }

  @Override
  public CodeStream IALOAD() {
    this.mv.visitInsn(Opcodes.IALOAD);
    return this;
  }

  @Override
  public CodeStream LALOAD() {
    this.mv.visitInsn(Opcodes.LALOAD);
    return this;
  }

  @Override
  public CodeStream FALOAD() {
    this.mv.visitInsn(Opcodes.FALOAD);
    return this;
  }

  @Override
  public CodeStream DALOAD() {
    this.mv.visitInsn(Opcodes.DALOAD);
    return this;
  }

  @Override
  public CodeStream AALOAD() {
    this.mv.visitInsn(Opcodes.AALOAD);
    return this;
  }

  @Override
  public CodeStream BALOAD() {
    this.mv.visitInsn(Opcodes.BALOAD);
    return this;
  }

  @Override
  public CodeStream CALOAD() {
    this.mv.visitInsn(Opcodes.CALOAD);
    return this;
  }

  @Override
  public CodeStream SALOAD() {
    this.mv.visitInsn(Opcodes.SALOAD);
    return this;
  }

  @Override
  public CodeStream ISTORE(int var) {
    this.mv.visitVarInsn(Opcodes.ISTORE, var);
    return this;
  }

  @Override
  public CodeStream LSTORE(int var) {
    this.mv.visitVarInsn(Opcodes.LSTORE, var);
    return this;
  }

  @Override
  public CodeStream FSTORE(int var) {
    this.mv.visitVarInsn(Opcodes.FSTORE, var);
    return this;
  }

  @Override
  public CodeStream DSTORE(int var) {
    this.mv.visitVarInsn(Opcodes.DSTORE, var);
    return this;
  }

  @Override
  public CodeStream ASTORE(int var) {
    this.mv.visitVarInsn(Opcodes.ASTORE, var);
    return this;
  }

  @Override
  public CodeStream IASTORE() {
    this.mv.visitInsn(Opcodes.IASTORE);
    return this;
  }

  @Override
  public CodeStream LASTORE() {
    this.mv.visitInsn(Opcodes.LASTORE);
    return this;
  }

  @Override
  public CodeStream FASTORE() {
    this.mv.visitInsn(Opcodes.FASTORE);
    return this;
  }

  @Override
  public CodeStream DASTORE() {
    this.mv.visitInsn(Opcodes.DASTORE);
    return this;
  }

  @Override
  public CodeStream AASTORE() {
    this.mv.visitInsn(Opcodes.AASTORE);
    return this;
  }

  @Override
  public CodeStream BASTORE() {
    this.mv.visitInsn(Opcodes.BASTORE);
    return this;
  }

  @Override
  public CodeStream CASTORE() {
    this.mv.visitInsn(Opcodes.CASTORE);
    return this;
  }

  @Override
  public CodeStream SASTORE() {
    this.mv.visitInsn(Opcodes.SASTORE);
    return this;
  }

  @Override
  public CodeStream POP() {
    this.mv.visitInsn(Opcodes.POP);
    return this;
  }

  @Override
  public CodeStream POP2() {
    this.mv.visitInsn(Opcodes.POP2);
    return this;
  }

  @Override
  public CodeStream DUP() {
    this.mv.visitInsn(Opcodes.DUP);
    return this;
  }

  @Override
  public CodeStream DUP_X1() {
    this.mv.visitInsn(Opcodes.DUP_X1);
    return this;
  }

  @Override
  public CodeStream DUP_X2() {
    this.mv.visitInsn(Opcodes.DUP_X2);
    return this;
  }

  @Override
  public CodeStream DUP2() {
    this.mv.visitInsn(Opcodes.DUP2);
    return this;
  }

  @Override
  public CodeStream DUP2_X1() {
    this.mv.visitInsn(Opcodes.DUP2_X1);
    return this;
  }

  @Override
  public CodeStream DUP2_X2() {
    this.mv.visitInsn(Opcodes.DUP2_X2);
    return this;
  }

  @Override
  public CodeStream SWAP() {
    this.mv.visitInsn(Opcodes.SWAP);
    return this;
  }

  @Override
  public CodeStream IADD() {
    this.mv.visitInsn(Opcodes.IADD);
    return this;
  }

  @Override
  public CodeStream LADD() {
    this.mv.visitInsn(Opcodes.LADD);
    return this;
  }

  @Override
  public CodeStream FADD() {
    this.mv.visitInsn(Opcodes.FADD);
    return this;
  }

  @Override
  public CodeStream DADD() {
    this.mv.visitInsn(Opcodes.DADD);
    return this;
  }

  @Override
  public CodeStream ISUB() {
    this.mv.visitInsn(Opcodes.ISUB);
    return this;
  }

  @Override
  public CodeStream LSUB() {
    this.mv.visitInsn(Opcodes.LSUB);
    return this;
  }

  @Override
  public CodeStream FSUB() {
    this.mv.visitInsn(Opcodes.FSUB);
    return this;
  }

  @Override
  public CodeStream DSUB() {
    this.mv.visitInsn(Opcodes.DSUB);
    return this;
  }

  @Override
  public CodeStream IMUL() {
    this.mv.visitInsn(Opcodes.IMUL);
    return this;
  }

  @Override
  public CodeStream LMUL() {
    this.mv.visitInsn(Opcodes.LMUL);
    return this;
  }

  @Override
  public CodeStream FMUL() {
    this.mv.visitInsn(Opcodes.FMUL);
    return this;
  }

  @Override
  public CodeStream DMUL() {
    this.mv.visitInsn(Opcodes.DMUL);
    return this;
  }

  @Override
  public CodeStream IDIV() {
    this.mv.visitInsn(Opcodes.IDIV);
    return this;
  }

  @Override
  public CodeStream LDIV() {
    this.mv.visitInsn(Opcodes.LDIV);
    return this;
  }

  @Override
  public CodeStream FDIV() {
    this.mv.visitInsn(Opcodes.FDIV);
    return this;
  }

  @Override
  public CodeStream DDIV() {
    this.mv.visitInsn(Opcodes.DDIV);
    return this;
  }

  @Override
  public CodeStream IREM() {
    this.mv.visitInsn(Opcodes.IREM);
    return this;
  }

  @Override
  public CodeStream LREM() {
    this.mv.visitInsn(Opcodes.LREM);
    return this;
  }

  @Override
  public CodeStream FREM() {
    this.mv.visitInsn(Opcodes.FREM);
    return this;
  }

  @Override
  public CodeStream DREM() {
    this.mv.visitInsn(Opcodes.DREM);
    return this;
  }

  @Override
  public CodeStream INEG() {
    this.mv.visitInsn(Opcodes.INEG);
    return this;
  }

  @Override
  public CodeStream LNEG() {
    this.mv.visitInsn(Opcodes.LNEG);
    return this;
  }

  @Override
  public CodeStream FNEG() {
    this.mv.visitInsn(Opcodes.FNEG);
    return this;
  }

  @Override
  public CodeStream DNEG() {
    this.mv.visitInsn(Opcodes.DNEG);
    return this;
  }

  @Override
  public CodeStream ISHL() {
    this.mv.visitInsn(Opcodes.ISHL);
    return this;
  }

  @Override
  public CodeStream LSHL() {
    this.mv.visitInsn(Opcodes.LSHL);
    return this;
  }

  @Override
  public CodeStream ISHR() {
    this.mv.visitInsn(Opcodes.ISHR);
    return this;
  }

  @Override
  public CodeStream LSHR() {
    this.mv.visitInsn(Opcodes.LSHR);
    return this;
  }

  @Override
  public CodeStream IUSHR() {
    this.mv.visitInsn(Opcodes.IUSHR);
    return this;
  }

  @Override
  public CodeStream LUSHR() {
    this.mv.visitInsn(Opcodes.LUSHR);
    return this;
  }

  @Override
  public CodeStream IAND() {
    this.mv.visitInsn(Opcodes.IAND);
    return this;
  }

  @Override
  public CodeStream LAND() {
    this.mv.visitInsn(Opcodes.LAND);
    return this;
  }

  @Override
  public CodeStream IOR() {
    this.mv.visitInsn(Opcodes.IOR);
    return this;
  }

  @Override
  public CodeStream LOR() {
    this.mv.visitInsn(Opcodes.LOR);
    return this;
  }

  @Override
  public CodeStream IXOR() {
    this.mv.visitInsn(Opcodes.IXOR);
    return this;
  }

  @Override
  public CodeStream LXOR() {
    this.mv.visitInsn(Opcodes.LXOR);
    return this;
  }

  @Override
  public CodeStream IINC(int var, int increment) {
    this.mv.visitIincInsn(var, increment);
    return this;
  }

  @Override
  public CodeStream I2L() {
    this.mv.visitInsn(Opcodes.I2L);
    return this;
  }

  @Override
  public CodeStream I2F() {
    this.mv.visitInsn(Opcodes.I2F);
    return this;
  }

  @Override
  public CodeStream I2D() {
    this.mv.visitInsn(Opcodes.I2D);
    return this;
  }

  @Override
  public CodeStream L2I() {
    this.mv.visitInsn(Opcodes.L2I);
    return this;
  }

  @Override
  public CodeStream L2F() {
    this.mv.visitInsn(Opcodes.L2F);
    return this;
  }

  @Override
  public CodeStream L2D() {
    this.mv.visitInsn(Opcodes.L2D);
    return this;
  }

  @Override
  public CodeStream F2I() {
    this.mv.visitInsn(Opcodes.F2I);
    return this;
  }

  @Override
  public CodeStream F2L() {
    this.mv.visitInsn(Opcodes.F2L);
    return this;
  }

  @Override
  public CodeStream F2D() {
    this.mv.visitInsn(Opcodes.F2D);
    return this;
  }

  @Override
  public CodeStream D2I() {
    this.mv.visitInsn(Opcodes.D2I);
    return this;
  }

  @Override
  public CodeStream D2L() {
    this.mv.visitInsn(Opcodes.D2L);
    return this;
  }

  @Override
  public CodeStream D2F() {
    this.mv.visitInsn(Opcodes.D2F);
    return this;
  }

  @Override
  public CodeStream I2B() {
    this.mv.visitInsn(Opcodes.I2B);
    return this;
  }

  @Override
  public CodeStream I2C() {
    this.mv.visitInsn(Opcodes.I2C);
    return this;
  }

  @Override
  public CodeStream I2S() {
    this.mv.visitInsn(Opcodes.I2S);
    return this;
  }

  @Override
  public CodeStream LCMP() {
    this.mv.visitInsn(Opcodes.LCMP);
    return this;
  }

  @Override
  public CodeStream FCMPL() {
    this.mv.visitInsn(Opcodes.FCMPL);
    return this;
  }

  @Override
  public CodeStream FCMPG() {
    this.mv.visitInsn(Opcodes.FCMPG);
    return this;
  }

  @Override
  public CodeStream DCMPL() {
    this.mv.visitInsn(Opcodes.DCMPL);
    return this;
  }

  @Override
  public CodeStream DCMPG() {
    this.mv.visitInsn(Opcodes.DCMPG);
    return this;
  }

  @Override
  public CodeStream IFEQ(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFEQ, label(label));
    return this;
  }

  @Override
  public CodeStream IFNE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFNE, label(label));
    return this;
  }

  @Override
  public CodeStream IFLT(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFLT, label(label));
    return this;
  }

  @Override
  public CodeStream IFGE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFGE, label(label));
    return this;
  }

  @Override
  public CodeStream IFGT(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFGT, label(label));
    return this;
  }

  @Override
  public CodeStream IFLE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFLE, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ICMPEQ(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ICMPEQ, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ICMPNE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ICMPNE, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ICMPLT(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ICMPLT, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ICMPGE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ICMPGE, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ICMPGT(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ICMPGT, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ICMPLE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ICMPLE, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ACMPEQ(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ACMPEQ, label(label));
    return this;
  }

  @Override
  public CodeStream IF_ACMPNE(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IF_ACMPNE, label(label));
    return this;
  }

  @Override
  public CodeStream GOTO(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.GOTO, label(label));
    return this;
  }

  @Override
  public CodeStream JSR(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.JSR, label(label));
    return this;
  }

  @Override
  public CodeStream RET(int var) {
    this.mv.visitVarInsn(Opcodes.RET, var);
    return this;
  }

  @Override
  public CodeStream TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels) {
    this.mv.visitTableSwitchInsn(min, max, label(defaultLabel), labels(labels));
    return this;
  }

  @Override
  public CodeStream LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL[] labels) {
    this.mv.visitLookupSwitchInsn(label(defaultLabel), keys, labels(labels));
    return this;
  }

  @Override
  public CodeStream IRETURN() {
    this.mv.visitInsn(Opcodes.IRETURN);
    return this;
  }

  @Override
  public CodeStream LRETURN() {
    this.mv.visitInsn(Opcodes.LRETURN);
    return this;
  }

  @Override
  public CodeStream FRETURN() {
    this.mv.visitInsn(Opcodes.FRETURN);
    return this;
  }

  @Override
  public CodeStream DRETURN() {
    this.mv.visitInsn(Opcodes.DRETURN);
    return this;
  }

  @Override
  public CodeStream ARETURN() {
    this.mv.visitInsn(Opcodes.ARETURN);
    return this;
  }

  @Override
  public CodeStream RETURN() {
    this.mv.visitInsn(Opcodes.RETURN);
    return this;
  }

  protected void access(int opcode, String owner, String name, String desc) {
    final String ownerClass = owner;
    this.mv.visitFieldInsn(opcode, ownerClass, name, desc);
  }

  @Override
  public CodeStream GETSTATIC(String owner, String name, String desc) {
    access(Opcodes.GETSTATIC, owner, name, desc);
    return this;
  }

  @Override
  public CodeStream PUTSTATIC(String owner, String name, String desc) {
    access(Opcodes.PUTSTATIC, owner, name, desc);
    return this;
  }

  @Override
  public CodeStream GETFIELD(String owner, String name, String desc) {
    access(Opcodes.GETFIELD, owner, name, desc);
    return this;
  }

  @Override
  public CodeStream PUTFIELD(String owner, String name, String desc) {
    access(Opcodes.PUTFIELD, owner, name, desc);
    return this;
  }

  protected void invoke(int opcode, String owner, String name, String desc, boolean isInterface) {
    this.mv.visitMethodInsn(opcode, owner, name, desc, isInterface);
  }

  @Override
  public CodeStream INVOKEVIRTUAL(String owner, String name, String desc) {
    invoke(Opcodes.INVOKEVIRTUAL, owner, name, desc, false);
    return this;
  }


  @Override
  public CodeStream INVOKESPECIAL(String owner, String name, String desc) {
    invoke(Opcodes.INVOKESPECIAL, owner, name, desc, false);
    return this;
  }

  @Override
  public CodeStream INVOKESTATIC(String owner, String name, String desc, boolean isInterface) {
    invoke(Opcodes.INVOKESTATIC, owner, name, desc, isInterface);
    return this;
  }

  @Override
  public CodeStream INVOKEINTERFACE(String owner, String name, String desc) {
    invoke(Opcodes.INVOKEINTERFACE, owner, name, desc, true);
    return this;
  }

  @Override
  public CodeStream INVOKEDYNAMIC(String name, String desc, Handle bsm, Object... bsmArgs) {
    this.mv.visitInvokeDynamicInsn(name, asType(desc), bsm, bsmArgs);
    return this;
  }

  @Override
  public CodeStream NEW(String internalType) {
    this.mv.visitTypeInsn(Opcodes.NEW, asType(internalType));
    return this;
  }

  @Override
  public CodeStream NEWARRAY(ArrayType type) {
    this.mv.visitIntInsn(Opcodes.NEWARRAY, type.getCode());
    return this;
  }

  @Override
  public CodeStream ANEWARRAY(String internalType) {
    this.mv.visitTypeInsn(Opcodes.ANEWARRAY, asType(internalType));
    return this;
  }

  @Override
  public CodeStream MULTIANEWARRAY(String internalType, int dims) {
    this.mv.visitMultiANewArrayInsn(asType(internalType), dims);
    return this;
  }

  @Override
  public CodeStream ARRAYLENGTH() {
    this.mv.visitInsn(Opcodes.ARRAYLENGTH);
    return this;
  }

  @Override
  public CodeStream ATHROW() {
    this.mv.visitInsn(Opcodes.ATHROW);
    return this;
  }

  @Override
  public CodeStream CHECKCAST(String internalType) {
    this.mv.visitTypeInsn(Opcodes.CHECKCAST, asType(internalType));
    return this;
  }

  @Override
  public CodeStream INSTANCEOF(String internalType) {
    this.mv.visitTypeInsn(Opcodes.INSTANCEOF, asType(internalType));
    return this;
  }

  @Override
  public CodeStream MONITORENTER() {
    this.mv.visitInsn(Opcodes.MONITORENTER);
    return this;
  }

  @Override
  public CodeStream MONITOREXIT() {
    this.mv.visitInsn(Opcodes.MONITOREXIT);
    return this;
  }

  @Override
  public CodeStream IFNULL(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFNULL, label(label));
    return this;
  }

  @Override
  public CodeStream IFNONNULL(LABEL label) {
    this.mv.visitJumpInsn(Opcodes.IFNONNULL, label(label));
    return this;
  }

  @Override
  public CodeStream LABEL(LABEL label) {
    this.mv.visitLabel(label(label));
    return this;
  }

  @Override
  public CodeStream TRY_CATCH(LABEL startLabel, LABEL endLabel, LABEL handler, String type) {
    this.mv.visitTryCatchBlock(label(startLabel), label(endLabel), label(handler), type);
    return this;
  }

  @Override
  public CodeStream FRAME(Frames type, int localsCount, Object[] local, int stackLength, Object[] stack) {
    this.mv.visitFrame(type.getCode(), localsCount, local, stackLength, stack);
    return this;
  }

  @Override
  public CodeStream LOCAL_VARIABLE(String name, String desc, String signature, LABEL start, LABEL end, int index) {
    this.mv.visitLocalVariable(name, desc, signature, label(start), label(end), index);
    return this;
  }

  @Override
  public CodeStream LINE_NUMBER(int line, LABEL start) {
    this.mv.visitLineNumber(line, label(start));
    this.lineNumber = line;
    return this;
  }

  @Override
  public CodeStream MAXS(int maxStack, int maxLocals) {
    this.mv.visitMaxs(maxStack, maxLocals);
    return this;
  }

  @Override
  public void END() {
    this.mv.visitEnd();
  }
}
