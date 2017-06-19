package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 07:05.
 */
public class DelegatingCodeStream
    implements HxCodeStream {

  private final HxCodeStream codeStream;

  public DelegatingCodeStream() {
    this(null);
  }

  public DelegatingCodeStream(final HxCodeStream codeStream) {
    this.codeStream = codeStream;
  }

  @Override
  public HxCodeStream BEGIN() {
    if (codeStream != null) {
      codeStream.BEGIN();
    }
    return this;
  }

  @Override
  public HxCodeStream NOP() {
    if (codeStream != null) {
      codeStream.NOP();
    }
    return this;
  }

  @Override
  public HxCodeStream ACONST_NULL() {
    if (codeStream != null) {
      codeStream.ACONST_NULL();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_M1() {
    if (codeStream != null) {
      codeStream.ICONST_M1();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_0() {
    if (codeStream != null) {
      codeStream.ICONST_0();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_1() {
    if (codeStream != null) {
      codeStream.ICONST_1();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_2() {
    if (codeStream != null) {
      codeStream.ICONST_2();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_3() {
    if (codeStream != null) {
      codeStream.ICONST_3();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_4() {
    if (codeStream != null) {
      codeStream.ICONST_4();
    }
    return this;
  }

  @Override
  public HxCodeStream ICONST_5() {
    if (codeStream != null) {
      codeStream.ICONST_5();
    }
    return this;
  }

  @Override
  public HxCodeStream LCONST_0() {
    if (codeStream != null) {
      codeStream.LCONST_0();
    }
    return this;
  }

  @Override
  public HxCodeStream LCONST_1() {
    if (codeStream != null) {
      codeStream.LCONST_1();
    }
    return this;
  }

  @Override
  public HxCodeStream FCONST_0() {
    if (codeStream != null) {
      codeStream.FCONST_0();
    }
    return this;
  }

  @Override
  public HxCodeStream FCONST_1() {
    if (codeStream != null) {
      codeStream.FCONST_1();
    }
    return this;
  }

  @Override
  public HxCodeStream FCONST_2() {
    if (codeStream != null) {
      codeStream.FCONST_2();
    }
    return this;
  }

  @Override
  public HxCodeStream DCONST_0() {
    if (codeStream != null) {
      codeStream.DCONST_0();
    }
    return this;
  }

  @Override
  public HxCodeStream DCONST_1() {
    if (codeStream != null) {
      codeStream.DCONST_1();
    }
    return this;
  }

  @Override
  public HxCodeStream BIPUSH(final byte value) {
    if (codeStream != null) {
      codeStream.BIPUSH(value);
    }
    return this;
  }

  @Override
  public HxCodeStream SIPUSH(final short value) {
    if (codeStream != null) {
      codeStream.SIPUSH(value);
    }
    return this;
  }

  @Override
  public HxCodeStream LDC(final int value) {
    if (codeStream != null) {
      codeStream.LDC(value);
    }
    return this;
  }

  @Override
  public HxCodeStream LDC(final float value) {
    if (codeStream != null) {
      codeStream.LDC(value);
    }
    return this;
  }

  @Override
  public HxCodeStream LDC(final long value) {
    if (codeStream != null) {
      codeStream.LDC(value);
    }
    return this;
  }

  @Override
  public HxCodeStream LDC(final double value) {
    if (codeStream != null) {
      codeStream.LDC(value);
    }
    return this;
  }

  @Override
  public HxCodeStream LDC(final String value) {
    if (codeStream != null) {
      codeStream.LDC(value);
    }
    return this;
  }

  @Override
  public HxCodeStream HANDLE(final HxMethodHandle handle) {
    if (codeStream != null) {
      codeStream.HANDLE(handle);
    }
    return this;
  }

  @Override
  public HxCodeStream METHOD(final HxMethodType methodType) {
    if (codeStream != null) {
      codeStream.METHOD(methodType);
    }
    return this;
  }

  @Override
  public HxCodeStream TYPE(final String internalType) {
    if (codeStream != null) {
      codeStream.TYPE(internalType);
    }
    return this;
  }

  @Override
  public HxCodeStream ILOAD(final int idx) {
    if (codeStream != null) {
      codeStream.ILOAD(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream LLOAD(final int idx) {
    if (codeStream != null) {
      codeStream.LLOAD(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream FLOAD(final int idx) {
    if (codeStream != null) {
      codeStream.FLOAD(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream DLOAD(final int idx) {
    if (codeStream != null) {
      codeStream.DLOAD(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream ALOAD(final int idx) {
    if (codeStream != null) {
      codeStream.ALOAD(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream THIS() {
    if (codeStream != null) {
      codeStream.THIS();
    }
    return this;
  }

  @Override
  public HxCodeStream IALOAD() {
    if (codeStream != null) {
      codeStream.IALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream LALOAD() {
    if (codeStream != null) {
      codeStream.LALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream FALOAD() {
    if (codeStream != null) {
      codeStream.FALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream DALOAD() {
    if (codeStream != null) {
      codeStream.DALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream AALOAD() {
    if (codeStream != null) {
      codeStream.AALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream BALOAD() {
    if (codeStream != null) {
      codeStream.BALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream CALOAD() {
    if (codeStream != null) {
      codeStream.CALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream SALOAD() {
    if (codeStream != null) {
      codeStream.SALOAD();
    }
    return this;
  }

  @Override
  public HxCodeStream ISTORE(final int idx) {
    if (codeStream != null) {
      codeStream.ISTORE(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream LSTORE(final int idx) {
    if (codeStream != null) {
      codeStream.LSTORE(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream FSTORE(final int idx) {
    if (codeStream != null) {
      codeStream.FSTORE(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream DSTORE(final int idx) {
    if (codeStream != null) {
      codeStream.DSTORE(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream ASTORE(final int idx) {
    if (codeStream != null) {
      codeStream.ASTORE(idx);
    }
    return this;
  }

  @Override
  public HxCodeStream IASTORE() {
    if (codeStream != null) {
      codeStream.IASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream LASTORE() {
    if (codeStream != null) {
      codeStream.LASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream FASTORE() {
    if (codeStream != null) {
      codeStream.FASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream DASTORE() {
    if (codeStream != null) {
      codeStream.DASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream AASTORE() {
    if (codeStream != null) {
      codeStream.AASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream BASTORE() {
    if (codeStream != null) {
      codeStream.BASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream CASTORE() {
    if (codeStream != null) {
      codeStream.CASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream SASTORE() {
    if (codeStream != null) {
      codeStream.SASTORE();
    }
    return this;
  }

  @Override
  public HxCodeStream POP() {
    if (codeStream != null) {
      codeStream.POP();
    }
    return this;
  }

  @Override
  public HxCodeStream POP2() {
    if (codeStream != null) {
      codeStream.POP2();
    }
    return this;
  }

  @Override
  public HxCodeStream DUP() {
    if (codeStream != null) {
      codeStream.DUP();
    }
    return this;
  }

  @Override
  public HxCodeStream DUP_X1() {
    if (codeStream != null) {
      codeStream.DUP_X1();
    }
    return this;
  }

  @Override
  public HxCodeStream DUP_X2() {
    if (codeStream != null) {
      codeStream.DUP_X2();
    }
    return this;
  }

  @Override
  public HxCodeStream DUP2() {
    if (codeStream != null) {
      codeStream.DUP2();
    }
    return this;
  }

  @Override
  public HxCodeStream DUP2_X1() {
    if (codeStream != null) {
      codeStream.DUP2_X1();
    }
    return this;
  }

  @Override
  public HxCodeStream DUP2_X2() {
    if (codeStream != null) {
      codeStream.DUP2_X2();
    }
    return this;
  }

  @Override
  public HxCodeStream SWAP() {
    if (codeStream != null) {
      codeStream.SWAP();
    }
    return this;
  }

  @Override
  public HxCodeStream IADD() {
    if (codeStream != null) {
      codeStream.IADD();
    }
    return this;
  }

  @Override
  public HxCodeStream LADD() {
    if (codeStream != null) {
      codeStream.LADD();
    }
    return this;
  }

  @Override
  public HxCodeStream FADD() {
    if (codeStream != null) {
      codeStream.FADD();
    }
    return this;
  }

  @Override
  public HxCodeStream DADD() {
    if (codeStream != null) {
      codeStream.DADD();
    }
    return this;
  }

  @Override
  public HxCodeStream ISUB() {
    if (codeStream != null) {
      codeStream.ISUB();
    }
    return this;
  }

  @Override
  public HxCodeStream LSUB() {
    if (codeStream != null) {
      codeStream.LSUB();
    }
    return this;
  }

  @Override
  public HxCodeStream FSUB() {
    if (codeStream != null) {
      codeStream.FSUB();
    }
    return this;
  }

  @Override
  public HxCodeStream DSUB() {
    if (codeStream != null) {
      codeStream.DSUB();
    }
    return this;
  }

  @Override
  public HxCodeStream IMUL() {
    if (codeStream != null) {
      codeStream.IMUL();
    }
    return this;
  }

  @Override
  public HxCodeStream LMUL() {
    if (codeStream != null) {
      codeStream.LMUL();
    }
    return this;
  }

  @Override
  public HxCodeStream FMUL() {
    if (codeStream != null) {
      codeStream.FMUL();
    }
    return this;
  }

  @Override
  public HxCodeStream DMUL() {
    if (codeStream != null) {
      codeStream.DMUL();
    }
    return this;
  }

  @Override
  public HxCodeStream IDIV() {
    if (codeStream != null) {
      codeStream.IDIV();
    }
    return this;
  }

  @Override
  public HxCodeStream LDIV() {
    if (codeStream != null) {
      codeStream.LDIV();
    }
    return this;
  }

  @Override
  public HxCodeStream FDIV() {
    if (codeStream != null) {
      codeStream.FDIV();
    }
    return this;
  }

  @Override
  public HxCodeStream DDIV() {
    if (codeStream != null) {
      codeStream.DDIV();
    }
    return this;
  }

  @Override
  public HxCodeStream IREM() {
    if (codeStream != null) {
      codeStream.IREM();
    }
    return this;
  }

  @Override
  public HxCodeStream LREM() {
    if (codeStream != null) {
      codeStream.LREM();
    }
    return this;
  }

  @Override
  public HxCodeStream FREM() {
    if (codeStream != null) {
      codeStream.FREM();
    }
    return this;
  }

  @Override
  public HxCodeStream DREM() {
    if (codeStream != null) {
      codeStream.DREM();
    }
    return this;
  }

  @Override
  public HxCodeStream INEG() {
    if (codeStream != null) {
      codeStream.INEG();
    }
    return this;
  }

  @Override
  public HxCodeStream LNEG() {
    if (codeStream != null) {
      codeStream.LNEG();
    }
    return this;
  }

  @Override
  public HxCodeStream FNEG() {
    if (codeStream != null) {
      codeStream.FNEG();
    }
    return this;
  }

  @Override
  public HxCodeStream DNEG() {
    if (codeStream != null) {
      codeStream.DNEG();
    }
    return this;
  }

  @Override
  public HxCodeStream ISHL() {
    if (codeStream != null) {
      codeStream.ISHL();
    }
    return this;
  }

  @Override
  public HxCodeStream LSHL() {
    if (codeStream != null) {
      codeStream.LSHL();
    }
    return this;
  }

  @Override
  public HxCodeStream ISHR() {
    if (codeStream != null) {
      codeStream.ISHR();
    }
    return this;
  }

  @Override
  public HxCodeStream LSHR() {
    if (codeStream != null) {
      codeStream.LSHR();
    }
    return this;
  }

  @Override
  public HxCodeStream IUSHR() {
    if (codeStream != null) {
      codeStream.IUSHR();
    }
    return this;
  }

  @Override
  public HxCodeStream LUSHR() {
    if (codeStream != null) {
      codeStream.LUSHR();
    }
    return this;
  }

  @Override
  public HxCodeStream IAND() {
    if (codeStream != null) {
      codeStream.IAND();
    }
    return this;
  }

  @Override
  public HxCodeStream LAND() {
    if (codeStream != null) {
      codeStream.LAND();
    }
    return this;
  }

  @Override
  public HxCodeStream IOR() {
    if (codeStream != null) {
      codeStream.IOR();
    }
    return this;
  }

  @Override
  public HxCodeStream LOR() {
    if (codeStream != null) {
      codeStream.LOR();
    }
    return this;
  }

  @Override
  public HxCodeStream IXOR() {
    if (codeStream != null) {
      codeStream.IXOR();
    }
    return this;
  }

  @Override
  public HxCodeStream LXOR() {
    if (codeStream != null) {
      codeStream.LXOR();
    }
    return this;
  }

  @Override
  public HxCodeStream IINC(final int var,
                           final int increment) {
    if (codeStream != null) {
      codeStream.IINC(var, increment);
    }
    return this;
  }

  @Override
  public HxCodeStream I2L() {
    if (codeStream != null) {
      codeStream.I2L();
    }
    return this;
  }

  @Override
  public HxCodeStream I2F() {
    if (codeStream != null) {
      codeStream.I2F();
    }
    return this;
  }

  @Override
  public HxCodeStream I2D() {
    if (codeStream != null) {
      codeStream.I2D();
    }
    return this;
  }

  @Override
  public HxCodeStream L2I() {
    if (codeStream != null) {
      codeStream.L2I();
    }
    return this;
  }

  @Override
  public HxCodeStream L2F() {
    if (codeStream != null) {
      codeStream.L2F();
    }
    return this;
  }

  @Override
  public HxCodeStream L2D() {
    if (codeStream != null) {
      codeStream.L2D();
    }
    return this;
  }

  @Override
  public HxCodeStream F2I() {
    if (codeStream != null) {
      codeStream.F2I();
    }
    return this;
  }

  @Override
  public HxCodeStream F2L() {
    if (codeStream != null) {
      codeStream.F2L();
    }
    return this;
  }

  @Override
  public HxCodeStream F2D() {
    if (codeStream != null) {
      codeStream.F2D();
    }
    return this;
  }

  @Override
  public HxCodeStream D2I() {
    if (codeStream != null) {
      codeStream.D2I();
    }
    return this;
  }

  @Override
  public HxCodeStream D2L() {
    if (codeStream != null) {
      codeStream.D2L();
    }
    return this;
  }

  @Override
  public HxCodeStream D2F() {
    if (codeStream != null) {
      codeStream.D2F();
    }
    return this;
  }

  @Override
  public HxCodeStream I2B() {
    if (codeStream != null) {
      codeStream.I2B();
    }
    return this;
  }

  @Override
  public HxCodeStream I2C() {
    if (codeStream != null) {
      codeStream.I2C();
    }
    return this;
  }

  @Override
  public HxCodeStream I2S() {
    if (codeStream != null) {
      codeStream.I2S();
    }
    return this;
  }

  @Override
  public HxCodeStream LCMP() {
    if (codeStream != null) {
      codeStream.LCMP();
    }
    return this;
  }

  @Override
  public HxCodeStream FCMPL() {
    if (codeStream != null) {
      codeStream.FCMPL();
    }
    return this;
  }

  @Override
  public HxCodeStream FCMPG() {
    if (codeStream != null) {
      codeStream.FCMPG();
    }
    return this;
  }

  @Override
  public HxCodeStream DCMPL() {
    if (codeStream != null) {
      codeStream.DCMPL();
    }
    return this;
  }

  @Override
  public HxCodeStream DCMPG() {
    if (codeStream != null) {
      codeStream.DCMPG();
    }
    return this;
  }

  @Override
  public HxCodeStream IFEQ(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFEQ(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IFNE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFNE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IFLT(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFLT(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IFGE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFGE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IFGT(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFGT(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IFLE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFLE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPEQ(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ICMPEQ(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPNE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ICMPNE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLT(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ICMPLT(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ICMPGE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGT(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ICMPGT(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ICMPLE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPEQ(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ACMPEQ(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPNE(final LABEL label) {
    if (codeStream != null) {
      codeStream.IF_ACMPNE(label);
    }
    return this;
  }

  @Override
  public HxCodeStream GOTO(final LABEL label) {
    if (codeStream != null) {
      codeStream.GOTO(label);
    }
    return this;
  }

  @Override
  public HxCodeStream JSR(final LABEL label) {
    if (codeStream != null) {
      codeStream.JSR(label);
    }
    return this;
  }

  @Override
  public HxCodeStream RET(final int var) {
    if (codeStream != null) {
      codeStream.RET(var);
    }
    return this;
  }

  @Override
  public HxCodeStream TABLESWITCH(final int min,
                                  final int max,
                                  final LABEL defaultLabel,
                                  final LABEL... labels) {
    if (codeStream != null) {
      codeStream.TABLESWITCH(min, max, defaultLabel, labels);
    }
    return this;
  }

  @Override
  public HxCodeStream LOOKUPSWITCH(final LABEL defaultLabel,
                                   final int[] keys,
                                   final LABEL[] labels) {
    if (codeStream != null) {
      codeStream.LOOKUPSWITCH(defaultLabel, keys, labels);
    }
    return this;
  }

  @Override
  public HxCodeStream IRETURN() {
    if (codeStream != null) {
      codeStream.IRETURN();
    }
    return this;
  }

  @Override
  public HxCodeStream LRETURN() {
    if (codeStream != null) {
      codeStream.LRETURN();
    }
    return this;
  }

  @Override
  public HxCodeStream FRETURN() {
    if (codeStream != null) {
      codeStream.FRETURN();
    }
    return this;
  }

  @Override
  public HxCodeStream DRETURN() {
    if (codeStream != null) {
      codeStream.DRETURN();
    }
    return this;
  }

  @Override
  public HxCodeStream ARETURN() {
    if (codeStream != null) {
      codeStream.ARETURN();
    }
    return this;
  }

  @Override
  public HxCodeStream RETURN() {
    if (codeStream != null) {
      codeStream.RETURN();
    }
    return this;
  }

  @Override
  public HxCodeStream GETSTATIC(final String owner,
                                final String name,
                                final String desc) {
    if (codeStream != null) {
      codeStream.GETSTATIC(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream PUTSTATIC(final String owner,
                                final String name,
                                final String desc) {
    if (codeStream != null) {
      codeStream.PUTSTATIC(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream GETFIELD(final String owner,
                               final String name,
                               final String desc) {
    if (codeStream != null) {
      codeStream.GETFIELD(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream PUTFIELD(final String owner,
                               final String name,
                               final String desc) {
    if (codeStream != null) {
      codeStream.PUTFIELD(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream INVOKEVIRTUAL(final String owner,
                                    final String name,
                                    final String desc) {
    if (codeStream != null) {
      codeStream.INVOKEVIRTUAL(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream INVOKESPECIAL(final String owner,
                                    final String name,
                                    final String desc) {
    if (codeStream != null) {
      codeStream.INVOKESPECIAL(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream INVOKESTATIC(final String owner,
                                   final String name,
                                   final String desc,
                                   final boolean isInterface) {
    if (codeStream != null) {
      codeStream.INVOKESTATIC(owner, name, desc, isInterface);
    }
    return this;
  }

  @Override
  public HxCodeStream INVOKEINTERFACE(final String owner,
                                      final String name,
                                      final String desc) {
    if (codeStream != null) {
      codeStream.INVOKEINTERFACE(owner, name, desc);
    }
    return this;
  }

  @Override
  public HxCodeStream INVOKEDYNAMIC(final String name,
                                    final String desc,
                                    final HxMethodHandle bsm,
                                    final HxArguments bsmArgs) {
    if (codeStream != null) {
      codeStream.INVOKEDYNAMIC(name, desc, bsm, bsmArgs);
    }
    return this;
  }

  @Override
  public HxCodeStream NEW(final String internalType) {
    if (codeStream != null) {
      codeStream.NEW(internalType);
    }
    return this;
  }

  @Override
  public HxCodeStream NEWARRAY(final HxArrayType type) {
    if (codeStream != null) {
      codeStream.NEWARRAY(type);
    }
    return this;
  }

  @Override
  public HxCodeStream ANEWARRAY(final String internalType) {
    if (codeStream != null) {
      codeStream.ANEWARRAY(internalType);
    }
    return this;
  }

  @Override
  public HxCodeStream ARRAYLENGTH() {
    if (codeStream != null) {
      codeStream.ARRAYLENGTH();
    }
    return this;
  }

  @Override
  public HxCodeStream ATHROW() {
    if (codeStream != null) {
      codeStream.ATHROW();
    }
    return this;
  }

  @Override
  public HxCodeStream CHECKCAST(final String internalType) {
    if (codeStream != null) {
      codeStream.CHECKCAST(internalType);
    }
    return this;
  }

  @Override
  public HxCodeStream INSTANCEOF(final String internalType) {
    if (codeStream != null) {
      codeStream.INSTANCEOF(internalType);
    }
    return this;
  }

  @Override
  public HxCodeStream MONITORENTER() {
    if (codeStream != null) {
      codeStream.MONITORENTER();
    }
    return this;
  }

  @Override
  public HxCodeStream MONITOREXIT() {
    if (codeStream != null) {
      codeStream.MONITOREXIT();
    }
    return this;
  }

  @Override
  public HxCodeStream MULTIANEWARRAY(final String internalType,
                                     final int dims) {
    if (codeStream != null) {
      codeStream.MULTIANEWARRAY(internalType, dims);
    }
    return this;
  }

  @Override
  public HxCodeStream IFNULL(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFNULL(label);
    }
    return this;
  }

  @Override
  public HxCodeStream IFNONNULL(final LABEL label) {
    if (codeStream != null) {
      codeStream.IFNONNULL(label);
    }
    return this;
  }

  @Override
  public HxCodeStream LABEL(final LABEL label) {
    if (codeStream != null) {
      codeStream.LABEL(label);
    }
    return this;
  }

  @Override
  public HxCodeStream TRY_CATCH(final LABEL startLabel,
                                final LABEL endLabel,
                                final LABEL handler,
                                final String type) {
    if (codeStream != null) {
      codeStream.TRY_CATCH(startLabel, endLabel, handler, type);
    }
    return this;
  }

  @Override
  public HxCodeStream FRAME(final HxFrames type,
                            final int nLocal,
                            final Object[] local,
                            final int nStack,
                            final Object[] stack) {
    if (codeStream != null) {
      codeStream.FRAME(type, nLocal, local, nStack, stack);
    }
    return this;
  }

  @Override
  public HxCodeStream LOCAL_VARIABLE(final String name,
                                     final String desc,
                                     final String signature,
                                     final LABEL start,
                                     final LABEL end,
                                     final int index) {
    if (codeStream != null) {
      codeStream.LOCAL_VARIABLE(name, desc, signature, start, end, index);
    }
    return this;
  }

  @Override
  public HxCodeStream LINE_NUMBER(final int line,
                                  final LABEL start) {
    if (codeStream != null) {
      codeStream.LINE_NUMBER(line, start);
    }
    return this;
  }

  @Override
  public HxCodeStream MAXS(final int maxStack,
                           final int maxLocals) {
    if (codeStream != null) {
      codeStream.MAXS(maxStack, maxLocals);
    }
    return this;
  }

  @Override
  public void END() {
    if (codeStream != null) {
      codeStream.END();
    }
  }
}
