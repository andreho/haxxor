package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.haxxor.cgen.ArrayType;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Frames;
import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 07:05.
 */
public class DelegatingCodeStream
    implements CodeStream {

  private final CodeStream codeStream;

  public DelegatingCodeStream(final CodeStream codeStream) {
    this.codeStream = codeStream;
  }

  @Override
  public CodeStream BEGIN() {
    codeStream.BEGIN();
    return this;
  }

  @Override
  public CodeStream NOP() {
    codeStream.NOP();
    return this;
  }

  @Override
  public CodeStream ACONST_NULL() {
    codeStream.ACONST_NULL();
    return this;
  }

  @Override
  public CodeStream ICONST_M1() {
    codeStream.ICONST_M1();
    return this;
  }

  @Override
  public CodeStream ICONST_0() {
    codeStream.ICONST_0();
    return this;
  }

  @Override
  public CodeStream ICONST_1() {
    codeStream.ICONST_1();
    return this;
  }

  @Override
  public CodeStream ICONST_2() {
    codeStream.ICONST_2();
    return this;
  }

  @Override
  public CodeStream ICONST_3() {
    codeStream.ICONST_3();
    return this;
  }

  @Override
  public CodeStream ICONST_4() {
    codeStream.ICONST_4();
    return this;
  }

  @Override
  public CodeStream ICONST_5() {
    codeStream.ICONST_5();
    return this;
  }

  @Override
  public CodeStream LCONST_0() {
    codeStream.LCONST_0();
    return this;
  }

  @Override
  public CodeStream LCONST_1() {
    codeStream.LCONST_1();
    return this;
  }

  @Override
  public CodeStream FCONST_0() {
    codeStream.FCONST_0();
    return this;
  }

  @Override
  public CodeStream FCONST_1() {
    codeStream.FCONST_1();
    return this;
  }

  @Override
  public CodeStream FCONST_2() {
    codeStream.FCONST_2();
    return this;
  }

  @Override
  public CodeStream DCONST_0() {
    codeStream.DCONST_0();
    return this;
  }

  @Override
  public CodeStream DCONST_1() {
    codeStream.DCONST_1();
    return this;
  }

  @Override
  public CodeStream BIPUSH(final byte value) {
    codeStream.BIPUSH(value);
    return this;
  }

  @Override
  public CodeStream SIPUSH(final short value) {
    codeStream.SIPUSH(value);
    return this;
  }

  @Override
  public CodeStream LDC(final int value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public CodeStream LDC(final float value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public CodeStream LDC(final long value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public CodeStream LDC(final double value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public CodeStream LDC(final String value) {
    codeStream.LDC(value);
    return this;
  }

  @Override
  public CodeStream HANDLE(final Handle handle) {
    codeStream.HANDLE(handle);
    return this;
  }

  @Override
  public CodeStream METHOD(final String methodDescriptor) {
    codeStream.METHOD(methodDescriptor);
    return this;
  }

  @Override
  public CodeStream TYPE(final String internalType) {
    codeStream.TYPE(internalType);
    return this;
  }

  @Override
  public CodeStream ILOAD(final int idx) {
    codeStream.ILOAD(idx);
    return this;
  }

  @Override
  public CodeStream LLOAD(final int idx) {
    codeStream.LLOAD(idx);
    return this;
  }

  @Override
  public CodeStream FLOAD(final int idx) {
    codeStream.FLOAD(idx);
    return this;
  }

  @Override
  public CodeStream DLOAD(final int idx) {
    codeStream.DLOAD(idx);
    return this;
  }

  @Override
  public CodeStream ALOAD(final int idx) {
    codeStream.ALOAD(idx);
    return this;
  }

  @Override
  public CodeStream THIS() {
    codeStream.THIS();
    return this;
  }

  @Override
  public CodeStream IALOAD() {
    codeStream.IALOAD();
    return this;
  }

  @Override
  public CodeStream LALOAD() {
    codeStream.LALOAD();
    return this;
  }

  @Override
  public CodeStream FALOAD() {
    codeStream.FALOAD();
    return this;
  }

  @Override
  public CodeStream DALOAD() {
    codeStream.DALOAD();
    return this;
  }

  @Override
  public CodeStream AALOAD() {
    codeStream.AALOAD();
    return this;
  }

  @Override
  public CodeStream BALOAD() {
    codeStream.BALOAD();
    return this;
  }

  @Override
  public CodeStream CALOAD() {
    codeStream.CALOAD();
    return this;
  }

  @Override
  public CodeStream SALOAD() {
    codeStream.SALOAD();
    return this;
  }

  @Override
  public CodeStream ISTORE(final int idx) {
    codeStream.ISTORE(idx);
    return this;
  }

  @Override
  public CodeStream LSTORE(final int idx) {
    codeStream.LSTORE(idx);
    return this;
  }

  @Override
  public CodeStream FSTORE(final int idx) {
    codeStream.FSTORE(idx);
    return this;
  }

  @Override
  public CodeStream DSTORE(final int idx) {
    codeStream.DSTORE(idx);
    return this;
  }

  @Override
  public CodeStream ASTORE(final int idx) {
    codeStream.ASTORE(idx);
    return this;
  }

  @Override
  public CodeStream IASTORE() {
    codeStream.IASTORE();
    return this;
  }

  @Override
  public CodeStream LASTORE() {
    codeStream.LASTORE();
    return this;
  }

  @Override
  public CodeStream FASTORE() {
    codeStream.FASTORE();
    return this;
  }

  @Override
  public CodeStream DASTORE() {
    codeStream.DASTORE();
    return this;
  }

  @Override
  public CodeStream AASTORE() {
    codeStream.AASTORE();
    return this;
  }

  @Override
  public CodeStream BASTORE() {
    codeStream.BASTORE();
    return this;
  }

  @Override
  public CodeStream CASTORE() {
    codeStream.CASTORE();
    return this;
  }

  @Override
  public CodeStream SASTORE() {
    codeStream.SASTORE();
    return this;
  }

  @Override
  public CodeStream POP() {
    codeStream.POP();
    return this;
  }

  @Override
  public CodeStream POP2() {
    codeStream.POP2();
    return this;
  }

  @Override
  public CodeStream DUP() {
    codeStream.DUP();
    return this;
  }

  @Override
  public CodeStream DUP_X1() {
    codeStream.DUP_X1();
    return this;
  }

  @Override
  public CodeStream DUP_X2() {
    codeStream.DUP_X2();
    return this;
  }

  @Override
  public CodeStream DUP2() {
    codeStream.DUP2();
    return this;
  }

  @Override
  public CodeStream DUP2_X1() {
    codeStream.DUP2_X1();
    return this;
  }

  @Override
  public CodeStream DUP2_X2() {
    codeStream.DUP2_X2();
    return this;
  }

  @Override
  public CodeStream SWAP() {
    codeStream.SWAP();
    return this;
  }

  @Override
  public CodeStream IADD() {
    codeStream.IADD();
    return this;
  }

  @Override
  public CodeStream LADD() {
    codeStream.LADD();
    return this;
  }

  @Override
  public CodeStream FADD() {
    codeStream.FADD();
    return this;
  }

  @Override
  public CodeStream DADD() {
    codeStream.DADD();
    return this;
  }

  @Override
  public CodeStream ISUB() {
    codeStream.ISUB();
    return this;
  }

  @Override
  public CodeStream LSUB() {
    codeStream.LSUB();
    return this;
  }

  @Override
  public CodeStream FSUB() {
    codeStream.FSUB();
    return this;
  }

  @Override
  public CodeStream DSUB() {
    codeStream.DSUB();
    return this;
  }

  @Override
  public CodeStream IMUL() {
    codeStream.IMUL();
    return this;
  }

  @Override
  public CodeStream LMUL() {
    codeStream.LMUL();
    return this;
  }

  @Override
  public CodeStream FMUL() {
    codeStream.FMUL();
    return this;
  }

  @Override
  public CodeStream DMUL() {
    codeStream.DMUL();
    return this;
  }

  @Override
  public CodeStream IDIV() {
    codeStream.IDIV();
    return this;
  }

  @Override
  public CodeStream LDIV() {
    codeStream.LDIV();
    return this;
  }

  @Override
  public CodeStream FDIV() {
    codeStream.FDIV();
    return this;
  }

  @Override
  public CodeStream DDIV() {
    codeStream.DDIV();
    return this;
  }

  @Override
  public CodeStream IREM() {
    codeStream.IREM();
    return this;
  }

  @Override
  public CodeStream LREM() {
    codeStream.LREM();
    return this;
  }

  @Override
  public CodeStream FREM() {
    codeStream.FREM();
    return this;
  }

  @Override
  public CodeStream DREM() {
    codeStream.DREM();
    return this;
  }

  @Override
  public CodeStream INEG() {
    codeStream.INEG();
    return this;
  }

  @Override
  public CodeStream LNEG() {
    codeStream.LNEG();
    return this;
  }

  @Override
  public CodeStream FNEG() {
    codeStream.FNEG();
    return this;
  }

  @Override
  public CodeStream DNEG() {
    codeStream.DNEG();
    return this;
  }

  @Override
  public CodeStream ISHL() {
    codeStream.ISHL();
    return this;
  }

  @Override
  public CodeStream LSHL() {
    codeStream.LSHL();
    return this;
  }

  @Override
  public CodeStream ISHR() {
    codeStream.ISHR();
    return this;
  }

  @Override
  public CodeStream LSHR() {
    codeStream.LSHR();
    return this;
  }

  @Override
  public CodeStream IUSHR() {
    codeStream.IUSHR();
    return this;
  }

  @Override
  public CodeStream LUSHR() {
    codeStream.LUSHR();
    return this;
  }

  @Override
  public CodeStream IAND() {
    codeStream.IAND();
    return this;
  }

  @Override
  public CodeStream LAND() {
    codeStream.LAND();
    return this;
  }

  @Override
  public CodeStream IOR() {
    codeStream.IOR();
    return this;
  }

  @Override
  public CodeStream LOR() {
    codeStream.LOR();
    return this;
  }

  @Override
  public CodeStream IXOR() {
    codeStream.IXOR();
    return this;
  }

  @Override
  public CodeStream LXOR() {
    codeStream.LXOR();
    return this;
  }

  @Override
  public CodeStream IINC(final int var,
                         final int increment) {
    codeStream.IINC(var, increment);
    return this;
  }

  @Override
  public CodeStream I2L() {
    codeStream.I2L();
    return this;
  }

  @Override
  public CodeStream I2F() {
    codeStream.I2F();
    return this;
  }

  @Override
  public CodeStream I2D() {
    codeStream.I2D();
    return this;
  }

  @Override
  public CodeStream L2I() {
    codeStream.L2I();
    return this;
  }

  @Override
  public CodeStream L2F() {
    codeStream.L2F();
    return this;
  }

  @Override
  public CodeStream L2D() {
    codeStream.L2D();
    return this;
  }

  @Override
  public CodeStream F2I() {
    codeStream.F2I();
    return this;
  }

  @Override
  public CodeStream F2L() {
    codeStream.F2L();
    return this;
  }

  @Override
  public CodeStream F2D() {
    codeStream.F2D();
    return this;
  }

  @Override
  public CodeStream D2I() {
    codeStream.D2I();
    return this;
  }

  @Override
  public CodeStream D2L() {
    codeStream.D2L();
    return this;
  }

  @Override
  public CodeStream D2F() {
    codeStream.D2F();
    return this;
  }

  @Override
  public CodeStream I2B() {
    codeStream.I2B();
    return this;
  }

  @Override
  public CodeStream I2C() {
    codeStream.I2C();
    return this;
  }

  @Override
  public CodeStream I2S() {
    codeStream.I2S();
    return this;
  }

  @Override
  public CodeStream LCMP() {
    codeStream.LCMP();
    return this;
  }

  @Override
  public CodeStream FCMPL() {
    codeStream.FCMPL();
    return this;
  }

  @Override
  public CodeStream FCMPG() {
    codeStream.FCMPG();
    return this;
  }

  @Override
  public CodeStream DCMPL() {
    codeStream.DCMPL();
    return this;
  }

  @Override
  public CodeStream DCMPG() {
    codeStream.DCMPG();
    return this;
  }

  @Override
  public CodeStream IFEQ(final LABEL label) {
    codeStream.IFEQ(label);
    return this;
  }

  @Override
  public CodeStream IFNE(final LABEL label) {
    codeStream.IFNE(label);
    return this;
  }

  @Override
  public CodeStream IFLT(final LABEL label) {
    codeStream.IFLT(label);
    return this;
  }

  @Override
  public CodeStream IFGE(final LABEL label) {
    codeStream.IFGE(label);
    return this;
  }

  @Override
  public CodeStream IFGT(final LABEL label) {
    codeStream.IFGT(label);
    return this;
  }

  @Override
  public CodeStream IFLE(final LABEL label) {
    codeStream.IFLE(label);
    return this;
  }

  @Override
  public CodeStream IF_ICMPEQ(final LABEL label) {
    codeStream.IF_ICMPEQ(label);
    return this;
  }

  @Override
  public CodeStream IF_ICMPNE(final LABEL label) {
    codeStream.IF_ICMPNE(label);
    return this;
  }

  @Override
  public CodeStream IF_ICMPLT(final LABEL label) {
    codeStream.IF_ICMPLT(label);
    return this;
  }

  @Override
  public CodeStream IF_ICMPGE(final LABEL label) {
    codeStream.IF_ICMPGE(label);
    return this;
  }

  @Override
  public CodeStream IF_ICMPGT(final LABEL label) {
    codeStream.IF_ICMPGT(label);
    return this;
  }

  @Override
  public CodeStream IF_ICMPLE(final LABEL label) {
    codeStream.IF_ICMPLE(label);
    return this;
  }

  @Override
  public CodeStream IF_ACMPEQ(final LABEL label) {
    codeStream.IF_ACMPEQ(label);
    return this;
  }

  @Override
  public CodeStream IF_ACMPNE(final LABEL label) {
    codeStream.IF_ACMPNE(label);
    return this;
  }

  @Override
  public CodeStream GOTO(final LABEL label) {
    codeStream.GOTO(label);
    return this;
  }

  @Override
  public CodeStream JSR(final LABEL label) {
    codeStream.JSR(label);
    return this;
  }

  @Override
  public CodeStream RET(final int var) {
    codeStream.RET(var);
    return this;
  }

  @Override
  public CodeStream TABLESWITCH(final int min,
                                final int max,
                                final LABEL defaultLabel,
                                final LABEL... labels) {
    codeStream.TABLESWITCH(min, max, defaultLabel, labels);
    return this;
  }

  @Override
  public CodeStream LOOKUPSWITCH(final LABEL defaultLabel,
                                 final int[] keys,
                                 final LABEL[] labels) {
    codeStream.LOOKUPSWITCH(defaultLabel, keys, labels);
    return this;
  }

  @Override
  public CodeStream IRETURN() {
    codeStream.IRETURN();
    return this;
  }

  @Override
  public CodeStream LRETURN() {
    codeStream.LRETURN();
    return this;
  }

  @Override
  public CodeStream FRETURN() {
    codeStream.FRETURN();
    return this;
  }

  @Override
  public CodeStream DRETURN() {
    codeStream.DRETURN();
    return this;
  }

  @Override
  public CodeStream ARETURN() {
    codeStream.ARETURN();
    return this;
  }

  @Override
  public CodeStream RETURN() {
    codeStream.RETURN();
    return this;
  }

  @Override
  public CodeStream GETSTATIC(final String owner,
                              final String name,
                              final String desc) {
    codeStream.GETSTATIC(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream PUTSTATIC(final String owner,
                              final String name,
                              final String desc) {
    codeStream.PUTSTATIC(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream GETFIELD(final String owner,
                             final String name,
                             final String desc) {
    codeStream.GETFIELD(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream PUTFIELD(final String owner,
                             final String name,
                             final String desc) {
    codeStream.PUTFIELD(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream INVOKEVIRTUAL(final String owner,
                                  final String name,
                                  final String desc) {
    codeStream.INVOKEVIRTUAL(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream INVOKESPECIAL(final String owner,
                                  final String name,
                                  final String desc) {
    codeStream.INVOKESPECIAL(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream INVOKESTATIC(final String owner,
                                 final String name,
                                 final String desc,
                                 final boolean isInterface) {
    codeStream.INVOKESTATIC(owner, name, desc, isInterface);
    return this;
  }

  @Override
  public CodeStream INVOKEINTERFACE(final String owner,
                                    final String name,
                                    final String desc) {
    codeStream.INVOKEINTERFACE(owner, name, desc);
    return this;
  }

  @Override
  public CodeStream INVOKEDYNAMIC(final String name,
                                  final String desc,
                                  final Handle bsm,
                                  final Object... bsmArgs) {
    codeStream.INVOKEDYNAMIC(name, desc, bsm, bsmArgs);
    return this;
  }

  @Override
  public CodeStream NEW(final String internalType) {
    codeStream.NEW(internalType);
    return this;
  }

  @Override
  public CodeStream NEWARRAY(final ArrayType type) {
    codeStream.NEWARRAY(type);
    return this;
  }

  @Override
  public CodeStream ANEWARRAY(final String internalType) {
    codeStream.ANEWARRAY(internalType);
    return this;
  }

  @Override
  public CodeStream ARRAYLENGTH() {
    codeStream.ARRAYLENGTH();
    return this;
  }

  @Override
  public CodeStream ATHROW() {
    codeStream.ATHROW();
    return this;
  }

  @Override
  public CodeStream CHECKCAST(final String internalType) {
    codeStream.CHECKCAST(internalType);
    return this;
  }

  @Override
  public CodeStream INSTANCEOF(final String internalType) {
    codeStream.INSTANCEOF(internalType);
    return this;
  }

  @Override
  public CodeStream MONITORENTER() {
    codeStream.MONITORENTER();
    return this;
  }

  @Override
  public CodeStream MONITOREXIT() {
    codeStream.MONITOREXIT();
    return this;
  }

  @Override
  public CodeStream MULTIANEWARRAY(final String internalType,
                                   final int dims) {
    codeStream.MULTIANEWARRAY(internalType, dims);
    return this;
  }

  @Override
  public CodeStream IFNULL(final LABEL label) {
    codeStream.IFNULL(label);
    return this;
  }

  @Override
  public CodeStream IFNONNULL(final LABEL label) {
    codeStream.IFNONNULL(label);
    return this;
  }

  @Override
  public CodeStream LABEL(final LABEL label) {
    codeStream.LABEL(label);
    return this;
  }

  @Override
  public CodeStream TRY_CATCH(final LABEL startLabel,
                              final LABEL endLabel,
                              final LABEL handler,
                              final String type) {
    codeStream.TRY_CATCH(startLabel, endLabel, handler, type);
    return this;
  }

  @Override
  public CodeStream FRAME(final Frames type,
                          final int nLocal,
                          final Object[] local,
                          final int nStack,
                          final Object[] stack) {
    codeStream.FRAME(type, nLocal, local, nStack, stack);
    return this;
  }

  @Override
  public CodeStream LOCAL_VARIABLE(final String name,
                                   final String desc,
                                   final String signature,
                                   final LABEL start,
                                   final LABEL end,
                                   final int index) {
    codeStream.LOCAL_VARIABLE(name, desc, signature, start, end, index);
    return this;
  }

  @Override
  public CodeStream LINE_NUMBER(final int line,
                                final LABEL start) {
    codeStream.LINE_NUMBER(line, start);
    return this;
  }

  @Override
  public CodeStream MAXS(final int maxStack,
                         final int maxLocals) {
    codeStream.MAXS(maxStack, maxLocals);
    return this;
  }

  @Override
  public void END() {
    codeStream.END();
  }
}
