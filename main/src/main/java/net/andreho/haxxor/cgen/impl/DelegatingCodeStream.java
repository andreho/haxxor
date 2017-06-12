package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxHandle;
import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 07:05.
 */
public class DelegatingCodeStream
    implements HxCodeStream {

  private final HxCodeStream codeStream;

  public DelegatingCodeStream(final HxCodeStream codeStream) {
    this.codeStream = codeStream;
  }

  @Override
  public HxCodeStream BEGIN() {
    codeStream.BEGIN();
    return this;
  }

  @Override
  public HxCodeStream NOP() {
    codeStream.NOP();
    return this;
  }

  @Override
  public HxCodeStream ACONST_NULL() {
    codeStream.ACONST_NULL();
    return this;
  }

  @Override
  public HxCodeStream ICONST_M1() {
    codeStream.ICONST_M1();
    return this;
  }

  @Override
  public HxCodeStream ICONST_0() {
    codeStream.ICONST_0();
    return this;
  }

  @Override
  public HxCodeStream ICONST_1() {
    codeStream.ICONST_1();
    return this;
  }

  @Override
  public HxCodeStream ICONST_2() {
    codeStream.ICONST_2();
    return this;
  }

  @Override
  public HxCodeStream ICONST_3() {
    codeStream.ICONST_3();
    return this;
  }

  @Override
  public HxCodeStream ICONST_4() {
    codeStream.ICONST_4();
    return this;
  }

  @Override
  public HxCodeStream ICONST_5() {
    codeStream.ICONST_5();
    return this;
  }

  @Override
  public HxCodeStream LCONST_0() {
    codeStream.LCONST_0();
    return this;
  }

  @Override
  public HxCodeStream LCONST_1() {
    codeStream.LCONST_1();
    return this;
  }

  @Override
  public HxCodeStream FCONST_0() {
    codeStream.FCONST_0();
    return this;
  }

  @Override
  public HxCodeStream FCONST_1() {
    codeStream.FCONST_1();
    return this;
  }

  @Override
  public HxCodeStream FCONST_2() {
    codeStream.FCONST_2();
    return this;
  }

  @Override
  public HxCodeStream DCONST_0() {
    codeStream.DCONST_0();
    return this;
  }

  @Override
  public HxCodeStream DCONST_1() {
    codeStream.DCONST_1();
    return this;
  }

  @Override
  public HxCodeStream BIPUSH(final byte value) {
    codeStream.BIPUSH(value);
    return this;
  }

  @Override
  public HxCodeStream SIPUSH(final short value) {
    codeStream.SIPUSH(value);
    return this;
  }

  @Override
  public HxCodeStream LDC(final int value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public HxCodeStream LDC(final float value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public HxCodeStream LDC(final long value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public HxCodeStream LDC(final double value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public HxCodeStream LDC(final String value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public HxCodeStream HANDLE(final HxHandle handle) {
    codeStream.HANDLE(handle);
    return this;
  }

  @Override
  public HxCodeStream METHOD(final String methodDescriptor) {
    codeStream.METHOD(methodDescriptor);
    return this;
  }

  @Override
  public HxCodeStream TYPE(final String internalType) {
    codeStream.TYPE(internalType);
    return this;
  }

  @Override
  public HxCodeStream ILOAD(final int idx) {
    codeStream.ILOAD(idx);
    return this;
  }

  @Override
  public HxCodeStream LLOAD(final int idx) {
    codeStream.LLOAD(idx);
    return this;
  }

  @Override
  public HxCodeStream FLOAD(final int idx) {
    codeStream.FLOAD(idx);
    return this;
  }

  @Override
  public HxCodeStream DLOAD(final int idx) {
    codeStream.DLOAD(idx);
    return this;
  }

  @Override
  public HxCodeStream ALOAD(final int idx) {
    codeStream.ALOAD(idx);
    return this;
  }

  @Override
  public HxCodeStream THIS() {
    codeStream.THIS();
    return this;
  }

  @Override
  public HxCodeStream IALOAD() {
    codeStream.IALOAD();
    return this;
  }

  @Override
  public HxCodeStream LALOAD() {
    codeStream.LALOAD();
    return this;
  }

  @Override
  public HxCodeStream FALOAD() {
    codeStream.FALOAD();
    return this;
  }

  @Override
  public HxCodeStream DALOAD() {
    codeStream.DALOAD();
    return this;
  }

  @Override
  public HxCodeStream AALOAD() {
    codeStream.AALOAD();
    return this;
  }

  @Override
  public HxCodeStream BALOAD() {
    codeStream.BALOAD();
    return this;
  }

  @Override
  public HxCodeStream CALOAD() {
    codeStream.CALOAD();
    return this;
  }

  @Override
  public HxCodeStream SALOAD() {
    codeStream.SALOAD();
    return this;
  }

  @Override
  public HxCodeStream ISTORE(final int idx) {
    codeStream.ISTORE(idx);
    return this;
  }

  @Override
  public HxCodeStream LSTORE(final int idx) {
    codeStream.LSTORE(idx);
    return this;
  }

  @Override
  public HxCodeStream FSTORE(final int idx) {
    codeStream.FSTORE(idx);
    return this;
  }

  @Override
  public HxCodeStream DSTORE(final int idx) {
    codeStream.DSTORE(idx);
    return this;
  }

  @Override
  public HxCodeStream ASTORE(final int idx) {
    codeStream.ASTORE(idx);
    return this;
  }

  @Override
  public HxCodeStream IASTORE() {
    codeStream.IASTORE();
    return this;
  }

  @Override
  public HxCodeStream LASTORE() {
    codeStream.LASTORE();
    return this;
  }

  @Override
  public HxCodeStream FASTORE() {
    codeStream.FASTORE();
    return this;
  }

  @Override
  public HxCodeStream DASTORE() {
    codeStream.DASTORE();
    return this;
  }

  @Override
  public HxCodeStream AASTORE() {
    codeStream.AASTORE();
    return this;
  }

  @Override
  public HxCodeStream BASTORE() {
    codeStream.BASTORE();
    return this;
  }

  @Override
  public HxCodeStream CASTORE() {
    codeStream.CASTORE();
    return this;
  }

  @Override
  public HxCodeStream SASTORE() {
    codeStream.SASTORE();
    return this;
  }

  @Override
  public HxCodeStream POP() {
    codeStream.POP();
    return this;
  }

  @Override
  public HxCodeStream POP2() {
    codeStream.POP2();
    return this;
  }

  @Override
  public HxCodeStream DUP() {
    codeStream.DUP();
    return this;
  }

  @Override
  public HxCodeStream DUP_X1() {
    codeStream.DUP_X1();
    return this;
  }

  @Override
  public HxCodeStream DUP_X2() {
    codeStream.DUP_X2();
    return this;
  }

  @Override
  public HxCodeStream DUP2() {
    codeStream.DUP2();
    return this;
  }

  @Override
  public HxCodeStream DUP2_X1() {
    codeStream.DUP2_X1();
    return this;
  }

  @Override
  public HxCodeStream DUP2_X2() {
    codeStream.DUP2_X2();
    return this;
  }

  @Override
  public HxCodeStream SWAP() {
    codeStream.SWAP();
    return this;
  }

  @Override
  public HxCodeStream IADD() {
    codeStream.IADD();
    return this;
  }

  @Override
  public HxCodeStream LADD() {
    codeStream.LADD();
    return this;
  }

  @Override
  public HxCodeStream FADD() {
    codeStream.FADD();
    return this;
  }

  @Override
  public HxCodeStream DADD() {
    codeStream.DADD();
    return this;
  }

  @Override
  public HxCodeStream ISUB() {
    codeStream.ISUB();
    return this;
  }

  @Override
  public HxCodeStream LSUB() {
    codeStream.LSUB();
    return this;
  }

  @Override
  public HxCodeStream FSUB() {
    codeStream.FSUB();
    return this;
  }

  @Override
  public HxCodeStream DSUB() {
    codeStream.DSUB();
    return this;
  }

  @Override
  public HxCodeStream IMUL() {
    codeStream.IMUL();
    return this;
  }

  @Override
  public HxCodeStream LMUL() {
    codeStream.LMUL();
    return this;
  }

  @Override
  public HxCodeStream FMUL() {
    codeStream.FMUL();
    return this;
  }

  @Override
  public HxCodeStream DMUL() {
    codeStream.DMUL();
    return this;
  }

  @Override
  public HxCodeStream IDIV() {
    codeStream.IDIV();
    return this;
  }

  @Override
  public HxCodeStream LDIV() {
    codeStream.LDIV();
    return this;
  }

  @Override
  public HxCodeStream FDIV() {
    codeStream.FDIV();
    return this;
  }

  @Override
  public HxCodeStream DDIV() {
    codeStream.DDIV();
    return this;
  }

  @Override
  public HxCodeStream IREM() {
    codeStream.IREM();
    return this;
  }

  @Override
  public HxCodeStream LREM() {
    codeStream.LREM();
    return this;
  }

  @Override
  public HxCodeStream FREM() {
    codeStream.FREM();
    return this;
  }

  @Override
  public HxCodeStream DREM() {
    codeStream.DREM();
    return this;
  }

  @Override
  public HxCodeStream INEG() {
    codeStream.INEG();
    return this;
  }

  @Override
  public HxCodeStream LNEG() {
    codeStream.LNEG();
    return this;
  }

  @Override
  public HxCodeStream FNEG() {
    codeStream.FNEG();
    return this;
  }

  @Override
  public HxCodeStream DNEG() {
    codeStream.DNEG();
    return this;
  }

  @Override
  public HxCodeStream ISHL() {
    codeStream.ISHL();
    return this;
  }

  @Override
  public HxCodeStream LSHL() {
    codeStream.LSHL();
    return this;
  }

  @Override
  public HxCodeStream ISHR() {
    codeStream.ISHR();
    return this;
  }

  @Override
  public HxCodeStream LSHR() {
    codeStream.LSHR();
    return this;
  }

  @Override
  public HxCodeStream IUSHR() {
    codeStream.IUSHR();
    return this;
  }

  @Override
  public HxCodeStream LUSHR() {
    codeStream.LUSHR();
    return this;
  }

  @Override
  public HxCodeStream IAND() {
    codeStream.IAND();
    return this;
  }

  @Override
  public HxCodeStream LAND() {
    codeStream.LAND();
    return this;
  }

  @Override
  public HxCodeStream IOR() {
    codeStream.IOR();
    return this;
  }

  @Override
  public HxCodeStream LOR() {
    codeStream.LOR();
    return this;
  }

  @Override
  public HxCodeStream IXOR() {
    codeStream.IXOR();
    return this;
  }

  @Override
  public HxCodeStream LXOR() {
    codeStream.LXOR();
    return this;
  }

  @Override
  public HxCodeStream IINC(final int var,
                           final int increment) {
    codeStream.IINC(var, increment);
    return this;
  }

  @Override
  public HxCodeStream I2L() {
    codeStream.I2L();
    return this;
  }

  @Override
  public HxCodeStream I2F() {
    codeStream.I2F();
    return this;
  }

  @Override
  public HxCodeStream I2D() {
    codeStream.I2D();
    return this;
  }

  @Override
  public HxCodeStream L2I() {
    codeStream.L2I();
    return this;
  }

  @Override
  public HxCodeStream L2F() {
    codeStream.L2F();
    return this;
  }

  @Override
  public HxCodeStream L2D() {
    codeStream.L2D();
    return this;
  }

  @Override
  public HxCodeStream F2I() {
    codeStream.F2I();
    return this;
  }

  @Override
  public HxCodeStream F2L() {
    codeStream.F2L();
    return this;
  }

  @Override
  public HxCodeStream F2D() {
    codeStream.F2D();
    return this;
  }

  @Override
  public HxCodeStream D2I() {
    codeStream.D2I();
    return this;
  }

  @Override
  public HxCodeStream D2L() {
    codeStream.D2L();
    return this;
  }

  @Override
  public HxCodeStream D2F() {
    codeStream.D2F();
    return this;
  }

  @Override
  public HxCodeStream I2B() {
    codeStream.I2B();
    return this;
  }

  @Override
  public HxCodeStream I2C() {
    codeStream.I2C();
    return this;
  }

  @Override
  public HxCodeStream I2S() {
    codeStream.I2S();
    return this;
  }

  @Override
  public HxCodeStream LCMP() {
    codeStream.LCMP();
    return this;
  }

  @Override
  public HxCodeStream FCMPL() {
    codeStream.FCMPL();
    return this;
  }

  @Override
  public HxCodeStream FCMPG() {
    codeStream.FCMPG();
    return this;
  }

  @Override
  public HxCodeStream DCMPL() {
    codeStream.DCMPL();
    return this;
  }

  @Override
  public HxCodeStream DCMPG() {
    codeStream.DCMPG();
    return this;
  }

  @Override
  public HxCodeStream IFEQ(final LABEL label) {
    codeStream.IFEQ(label);
    return this;
  }

  @Override
  public HxCodeStream IFNE(final LABEL label) {
    codeStream.IFNE(label);
    return this;
  }

  @Override
  public HxCodeStream IFLT(final LABEL label) {
    codeStream.IFLT(label);
    return this;
  }

  @Override
  public HxCodeStream IFGE(final LABEL label) {
    codeStream.IFGE(label);
    return this;
  }

  @Override
  public HxCodeStream IFGT(final LABEL label) {
    codeStream.IFGT(label);
    return this;
  }

  @Override
  public HxCodeStream IFLE(final LABEL label) {
    codeStream.IFLE(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPEQ(final LABEL label) {
    codeStream.IF_ICMPEQ(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPNE(final LABEL label) {
    codeStream.IF_ICMPNE(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLT(final LABEL label) {
    codeStream.IF_ICMPLT(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGE(final LABEL label) {
    codeStream.IF_ICMPGE(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGT(final LABEL label) {
    codeStream.IF_ICMPGT(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLE(final LABEL label) {
    codeStream.IF_ICMPLE(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPEQ(final LABEL label) {
    codeStream.IF_ACMPEQ(label);
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPNE(final LABEL label) {
    codeStream.IF_ACMPNE(label);
    return this;
  }

  @Override
  public HxCodeStream GOTO(final LABEL label) {
    codeStream.GOTO(label);
    return this;
  }

  @Override
  public HxCodeStream JSR(final LABEL label) {
    codeStream.JSR(label);
    return this;
  }

  @Override
  public HxCodeStream RET(final int var) {
    codeStream.RET(var);
    return this;
  }

  @Override
  public HxCodeStream TABLESWITCH(final int min,
                                  final int max,
                                  final LABEL defaultLabel,
                                  final LABEL... labels) {
    codeStream.TABLESWITCH(min, max, defaultLabel, labels);
    return this;
  }

  @Override
  public HxCodeStream LOOKUPSWITCH(final LABEL defaultLabel,
                                   final int[] keys,
                                   final LABEL[] labels) {
    codeStream.LOOKUPSWITCH(defaultLabel, keys, labels);
    return this;
  }

  @Override
  public HxCodeStream IRETURN() {
    codeStream.IRETURN();
    return this;
  }

  @Override
  public HxCodeStream LRETURN() {
    codeStream.LRETURN();
    return this;
  }

  @Override
  public HxCodeStream FRETURN() {
    codeStream.FRETURN();
    return this;
  }

  @Override
  public HxCodeStream DRETURN() {
    codeStream.DRETURN();
    return this;
  }

  @Override
  public HxCodeStream ARETURN() {
    codeStream.ARETURN();
    return this;
  }

  @Override
  public HxCodeStream RETURN() {
    codeStream.RETURN();
    return this;
  }

  @Override
  public HxCodeStream GETSTATIC(final String owner,
                                final String name,
                                final String desc) {
    codeStream.GETSTATIC(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream PUTSTATIC(final String owner,
                                final String name,
                                final String desc) {
    codeStream.PUTSTATIC(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream GETFIELD(final String owner,
                               final String name,
                               final String desc) {
    codeStream.GETFIELD(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream PUTFIELD(final String owner,
                               final String name,
                               final String desc) {
    codeStream.PUTFIELD(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream INVOKEVIRTUAL(final String owner,
                                    final String name,
                                    final String desc) {
    codeStream.INVOKEVIRTUAL(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream INVOKESPECIAL(final String owner,
                                    final String name,
                                    final String desc) {
    codeStream.INVOKESPECIAL(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream INVOKESTATIC(final String owner,
                                   final String name,
                                   final String desc,
                                   final boolean isInterface) {
    codeStream.INVOKESTATIC(owner, name, desc, isInterface);
    return this;
  }

  @Override
  public HxCodeStream INVOKEINTERFACE(final String owner,
                                      final String name,
                                      final String desc) {
    codeStream.INVOKEINTERFACE(owner, name, desc);
    return this;
  }

  @Override
  public HxCodeStream INVOKEDYNAMIC(final String name,
                                    final String desc,
                                    final HxHandle bsm,
                                    final Object... bsmArgs) {
    codeStream.INVOKEDYNAMIC(name, desc, bsm, bsmArgs);
    return this;
  }

  @Override
  public HxCodeStream NEW(final String internalType) {
    codeStream.NEW(internalType);
    return this;
  }

  @Override
  public HxCodeStream NEWARRAY(final HxArrayType type) {
    codeStream.NEWARRAY(type);
    return this;
  }

  @Override
  public HxCodeStream ANEWARRAY(final String internalType) {
    codeStream.ANEWARRAY(internalType);
    return this;
  }

  @Override
  public HxCodeStream ARRAYLENGTH() {
    codeStream.ARRAYLENGTH();
    return this;
  }

  @Override
  public HxCodeStream ATHROW() {
    codeStream.ATHROW();
    return this;
  }

  @Override
  public HxCodeStream CHECKCAST(final String internalType) {
    codeStream.CHECKCAST(internalType);
    return this;
  }

  @Override
  public HxCodeStream INSTANCEOF(final String internalType) {
    codeStream.INSTANCEOF(internalType);
    return this;
  }

  @Override
  public HxCodeStream MONITORENTER() {
    codeStream.MONITORENTER();
    return this;
  }

  @Override
  public HxCodeStream MONITOREXIT() {
    codeStream.MONITOREXIT();
    return this;
  }

  @Override
  public HxCodeStream MULTIANEWARRAY(final String internalType,
                                     final int dims) {
    codeStream.MULTIANEWARRAY(internalType, dims);
    return this;
  }

  @Override
  public HxCodeStream IFNULL(final LABEL label) {
    codeStream.IFNULL(label);
    return this;
  }

  @Override
  public HxCodeStream IFNONNULL(final LABEL label) {
    codeStream.IFNONNULL(label);
    return this;
  }

  @Override
  public HxCodeStream LABEL(final LABEL label) {
    codeStream.LABEL(label);
    return this;
  }

  @Override
  public HxCodeStream TRY_CATCH(final LABEL startLabel,
                                final LABEL endLabel,
                                final LABEL handler,
                                final String type) {
    codeStream.TRY_CATCH(startLabel, endLabel, handler, type);
    return this;
  }

  @Override
  public HxCodeStream FRAME(final HxFrames type,
                            final int nLocal,
                            final Object[] local,
                            final int nStack,
                            final Object[] stack) {
    codeStream.FRAME(type, nLocal, local, nStack, stack);
    return this;
  }

  @Override
  public HxCodeStream LOCAL_VARIABLE(final String name,
                                     final String desc,
                                     final String signature,
                                     final LABEL start,
                                     final LABEL end,
                                     final int index) {
    codeStream.LOCAL_VARIABLE(name, desc, signature, start, end, index);
    return this;
  }

  @Override
  public HxCodeStream LINE_NUMBER(final int line,
                                  final LABEL start) {
    codeStream.LINE_NUMBER(line, start);
    return this;
  }

  @Override
  public HxCodeStream MAXS(final int maxStack,
                           final int maxLocals) {
    codeStream.MAXS(maxStack, maxLocals);
    return this;
  }

  @Override
  public void END() {
    codeStream.END();
  }
}
