package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class InstructionCodeStream
    implements HxCodeStream {

  private final HxCode code;
  private final HxInstructionFactory factory;


  public InstructionCodeStream(final HxCode code) {
    this(code, code.getInstructionFactory());
  }

  public InstructionCodeStream(final HxCode code, final HxInstructionFactory factory) {
    this.code = code;
    this.factory = factory;
  }

  private HxInstruction current() {
    return code.getCurrent();
  }

  @Override
  public HxCodeStream BEGIN() {
    //NO OP
    return this;
  }

  @Override
  public HxCodeStream NOP() {
    current().append(factory.NOP());
    return this;
  }

  @Override
  public HxCodeStream ACONST_NULL() {
    current().append(factory.ACONST_NULL());
    return this;
  }

  @Override
  public HxCodeStream ICONST_M1() {
    current().append(factory.ICONST(-1));
    return this;
  }

  @Override
  public HxCodeStream ICONST_0() {
    current().append(factory.ICONST(0));
    return this;
  }

  @Override
  public HxCodeStream ICONST_1() {
    current().append(factory.ICONST(1));
    return this;
  }

  @Override
  public HxCodeStream ICONST_2() {
    current().append(factory.ICONST(2));
    return this;
  }

  @Override
  public HxCodeStream ICONST_3() {
    current().append(factory.ICONST(3));
    return this;
  }

  @Override
  public HxCodeStream ICONST_4() {
    current().append(factory.ICONST(4));
    return this;
  }

  @Override
  public HxCodeStream ICONST_5() {
    current().append(factory.ICONST(5));
    return this;
  }

  @Override
  public HxCodeStream LCONST_0() {
    current().append(factory.LCONST(0L));
    return this;
  }

  @Override
  public HxCodeStream LCONST_1() {
    current().append(factory.LCONST(1L));
    return this;
  }

  @Override
  public HxCodeStream FCONST_0() {
    current().append(factory.FCONST(0f));
    return this;
  }

  @Override
  public HxCodeStream FCONST_1() {
    current().append(factory.FCONST(1f));
    return this;
  }

  @Override
  public HxCodeStream FCONST_2() {
    current().append(factory.FCONST(2f));
    return this;
  }

  @Override
  public HxCodeStream DCONST_0() {
    current().append(factory.DCONST(0d));
    return this;
  }

  @Override
  public HxCodeStream DCONST_1() {
    current().append(factory.DCONST(1d));
    return this;
  }

  @Override
  public HxCodeStream BIPUSH(final byte value) {
    current().append(factory.BIPUSH(value));
    return this;
  }

  @Override
  public HxCodeStream SIPUSH(final short value) {
    current().append(factory.SIPUSH(value));
    return this;
  }

  @Override
  public HxCodeStream LDC(final int value) {
    current().append(factory.LDC(value));
    return this;
  }

  @Override
  public HxCodeStream LDC(final float value) {
    current().append(factory.LDC(value));
    return this;
  }

  @Override
  public HxCodeStream LDC(final long value) {
    current().append(factory.LDC(value));
    return this;
  }

  @Override
  public HxCodeStream LDC(final double value) {
    current().append(factory.LDC(value));
    return this;
  }

  @Override
  public HxCodeStream LDC(final String value) {
    current().append(factory.LDC(value));
    return this;
  }

  @Override
  public HxCodeStream HANDLE(final HxMethodHandle handle) {
    current().append(factory.HANDLE(handle));
    return this;
  }

  @Override
  public HxCodeStream METHOD(final HxMethodType methodType) {
    current().append(factory.METHOD(methodType));
    return this;
  }

  @Override
  public HxCodeStream TYPE(final String internalType) {
    current().append(factory.TYPE(internalType));
    return this;
  }

  @Override
  public HxCodeStream TYPE(final HxType type) {
    current().append(factory.TYPE(type));
    return this;
  }

  @Override
  public HxCodeStream ILOAD(final int idx) {
    current().append(factory.ILOAD(idx));
    return this;
  }

  @Override
  public HxCodeStream LLOAD(final int idx) {
    current().append(factory.LLOAD(idx));
    return this;
  }

  @Override
  public HxCodeStream FLOAD(final int idx) {
    current().append(factory.FLOAD(idx));
    return this;
  }

  @Override
  public HxCodeStream DLOAD(final int idx) {
    current().append(factory.DLOAD(idx));
    return this;
  }

  @Override
  public HxCodeStream ALOAD(final int idx) {
    current().append(factory.ALOAD(idx));
    return this;
  }

  @Override
  public HxCodeStream THIS() {
    current().append(factory.ALOAD(0));
    return this;
  }

  @Override
  public HxCodeStream IALOAD() {
    current().append(factory.IALOAD());
    return this;
  }

  @Override
  public HxCodeStream LALOAD() {
    current().append(factory.LALOAD());
    return this;
  }

  @Override
  public HxCodeStream FALOAD() {
    current().append(factory.FALOAD());
    return this;
  }

  @Override
  public HxCodeStream DALOAD() {
    current().append(factory.DALOAD());
    return this;
  }

  @Override
  public HxCodeStream AALOAD() {
    current().append(factory.AALOAD());
    return this;
  }

  @Override
  public HxCodeStream BALOAD() {
    current().append(factory.BALOAD());
    return this;
  }

  @Override
  public HxCodeStream CALOAD() {
    current().append(factory.CALOAD());
    return this;
  }

  @Override
  public HxCodeStream SALOAD() {
    current().append(factory.SALOAD());
    return this;
  }

  @Override
  public HxCodeStream ISTORE(final int idx) {
    current().append(factory.ISTORE(idx));
    return this;
  }

  @Override
  public HxCodeStream LSTORE(final int idx) {
    current().append(factory.LSTORE(idx));
    return this;
  }

  @Override
  public HxCodeStream FSTORE(final int idx) {
    current().append(factory.FSTORE(idx));
    return this;
  }

  @Override
  public HxCodeStream DSTORE(final int idx) {
    current().append(factory.DSTORE(idx));
    return this;
  }

  @Override
  public HxCodeStream ASTORE(final int idx) {
    current().append(factory.ASTORE(idx));
    return this;
  }

  @Override
  public HxCodeStream IASTORE() {
    current().append(factory.IASTORE());
    return this;
  }

  @Override
  public HxCodeStream LASTORE() {
    current().append(factory.LASTORE());
    return this;
  }

  @Override
  public HxCodeStream FASTORE() {
    current().append(factory.FASTORE());
    return this;
  }

  @Override
  public HxCodeStream DASTORE() {
    current().append(factory.DASTORE());
    return this;
  }

  @Override
  public HxCodeStream AASTORE() {
    current().append(factory.AASTORE());
    return this;
  }

  @Override
  public HxCodeStream BASTORE() {
    current().append(factory.BASTORE());
    return this;
  }

  @Override
  public HxCodeStream CASTORE() {
    current().append(factory.CASTORE());
    return this;
  }

  @Override
  public HxCodeStream SASTORE() {
    current().append(factory.SASTORE());
    return this;
  }

  @Override
  public HxCodeStream POP() {
    current().append(factory.POP());
    return this;
  }

  @Override
  public HxCodeStream POP2() {
    current().append(factory.POP2());
    return this;
  }

  @Override
  public HxCodeStream DUP() {
    current().append(factory.DUP());
    return this;
  }

  @Override
  public HxCodeStream DUP_X1() {
    current().append(factory.DUP_X1());
    return this;
  }

  @Override
  public HxCodeStream DUP_X2() {
    current().append(factory.DUP_X2());
    return this;
  }

  @Override
  public HxCodeStream DUP2() {
    current().append(factory.DUP2());
    return this;
  }

  @Override
  public HxCodeStream DUP2_X1() {
    current().append(factory.DUP2_X1());
    return this;
  }

  @Override
  public HxCodeStream DUP2_X2() {
    current().append(factory.DUP2_X2());
    return this;
  }

  @Override
  public HxCodeStream SWAP() {
    current().append(factory.SWAP());
    return this;
  }

  @Override
  public HxCodeStream IADD() {
    current().append(factory.IADD());
    return this;
  }

  @Override
  public HxCodeStream LADD() {
    current().append(factory.LADD());
    return this;
  }

  @Override
  public HxCodeStream FADD() {
    current().append(factory.FADD());
    return this;
  }

  @Override
  public HxCodeStream DADD() {
    current().append(factory.DADD());
    return this;
  }

  @Override
  public HxCodeStream ISUB() {
    current().append(factory.ISUB());
    return this;
  }

  @Override
  public HxCodeStream LSUB() {
    current().append(factory.LSUB());
    return this;
  }

  @Override
  public HxCodeStream FSUB() {
    current().append(factory.FSUB());
    return this;
  }

  @Override
  public HxCodeStream DSUB() {
    current().append(factory.DSUB());
    return this;
  }

  @Override
  public HxCodeStream IMUL() {
    current().append(factory.IMUL());
    return this;
  }

  @Override
  public HxCodeStream LMUL() {
    current().append(factory.LMUL());
    return this;
  }

  @Override
  public HxCodeStream FMUL() {
    current().append(factory.FMUL());
    return this;
  }

  @Override
  public HxCodeStream DMUL() {
    current().append(factory.DMUL());
    return this;
  }

  @Override
  public HxCodeStream IDIV() {
    current().append(factory.IDIV());
    return this;
  }

  @Override
  public HxCodeStream LDIV() {
    current().append(factory.LDIV());
    return this;
  }

  @Override
  public HxCodeStream FDIV() {
    current().append(factory.FDIV());
    return this;
  }

  @Override
  public HxCodeStream DDIV() {
    current().append(factory.DDIV());
    return this;
  }

  @Override
  public HxCodeStream IREM() {
    current().append(factory.IREM());
    return this;
  }

  @Override
  public HxCodeStream LREM() {
    current().append(factory.LREM());
    return this;
  }

  @Override
  public HxCodeStream FREM() {
    current().append(factory.FREM());
    return this;
  }

  @Override
  public HxCodeStream DREM() {
    current().append(factory.DREM());
    return this;
  }

  @Override
  public HxCodeStream INEG() {
    current().append(factory.INEG());
    return this;
  }

  @Override
  public HxCodeStream LNEG() {
    current().append(factory.LNEG());
    return this;
  }

  @Override
  public HxCodeStream FNEG() {
    current().append(factory.FNEG());
    return this;
  }

  @Override
  public HxCodeStream DNEG() {
    current().append(factory.DNEG());
    return this;
  }

  @Override
  public HxCodeStream ISHL() {
    current().append(factory.ISHL());
    return this;
  }

  @Override
  public HxCodeStream LSHL() {
    current().append(factory.LSHL());
    return this;
  }

  @Override
  public HxCodeStream ISHR() {
    current().append(factory.ISHR());
    return this;
  }

  @Override
  public HxCodeStream LSHR() {
    current().append(factory.LSHR());
    return this;
  }

  @Override
  public HxCodeStream IUSHR() {
    current().append(factory.IUSHR());
    return this;
  }

  @Override
  public HxCodeStream LUSHR() {
    current().append(factory.LUSHR());
    return this;
  }

  @Override
  public HxCodeStream IAND() {
    current().append(factory.IADD());
    return this;
  }

  @Override
  public HxCodeStream LAND() {
    current().append(factory.LADD());
    return this;
  }

  @Override
  public HxCodeStream IOR() {
    current().append(factory.IOR());
    return this;
  }

  @Override
  public HxCodeStream LOR() {
    current().append(factory.LOR());
    return this;
  }

  @Override
  public HxCodeStream IXOR() {
    current().append(factory.IXOR());
    return this;
  }

  @Override
  public HxCodeStream LXOR() {
    current().append(factory.LXOR());
    return this;
  }

  @Override
  public HxCodeStream IINC(final int var, final int increment) {
    current().append(factory.IINC(var, increment));
    return this;
  }

  @Override
  public HxCodeStream I2L() {
    current().append(factory.I2L());
    return this;
  }

  @Override
  public HxCodeStream I2F() {
    current().append(factory.I2F());
    return this;
  }

  @Override
  public HxCodeStream I2D() {
    current().append(factory.I2D());
    return this;
  }

  @Override
  public HxCodeStream L2I() {
    current().append(factory.L2I());
    return this;
  }

  @Override
  public HxCodeStream L2F() {
    current().append(factory.L2F());
    return this;
  }

  @Override
  public HxCodeStream L2D() {
    current().append(factory.L2D());
    return this;
  }

  @Override
  public HxCodeStream F2I() {
    current().append(factory.F2I());
    return this;
  }

  @Override
  public HxCodeStream F2L() {
    current().append(factory.F2L());
    return this;
  }

  @Override
  public HxCodeStream F2D() {
    current().append(factory.F2D());
    return this;
  }

  @Override
  public HxCodeStream D2I() {
    current().append(factory.D2I());
    return this;
  }

  @Override
  public HxCodeStream D2L() {
    current().append(factory.D2L());
    return this;
  }

  @Override
  public HxCodeStream D2F() {
    current().append(factory.D2F());
    return this;
  }

  @Override
  public HxCodeStream I2B() {
    current().append(factory.I2B());
    return this;
  }

  @Override
  public HxCodeStream I2C() {
    current().append(factory.I2C());
    return this;
  }

  @Override
  public HxCodeStream I2S() {
    current().append(factory.I2S());
    return this;
  }

  @Override
  public HxCodeStream LCMP() {
    current().append(factory.LCMP());
    return this;
  }

  @Override
  public HxCodeStream FCMPL() {
    current().append(factory.FCMPL());
    return this;
  }

  @Override
  public HxCodeStream FCMPG() {
    current().append(factory.FCMPG());
    return this;
  }

  @Override
  public HxCodeStream DCMPL() {
    current().append(factory.DCMPL());
    return this;
  }

  @Override
  public HxCodeStream DCMPG() {
    current().append(factory.DCMPG());
    return this;
  }

  @Override
  public HxCodeStream IFEQ(final LABEL label) {
    current().append(factory.IFEQ(label));
    return this;
  }

  @Override
  public HxCodeStream IFNE(final LABEL label) {
    current().append(factory.IFNE(label));
    return this;
  }

  @Override
  public HxCodeStream IFLT(final LABEL label) {
    current().append(factory.IFLT(label));
    return this;
  }

  @Override
  public HxCodeStream IFGE(final LABEL label) {
    current().append(factory.IFGE(label));
    return this;
  }

  @Override
  public HxCodeStream IFGT(final LABEL label) {
    current().append(factory.IFGT(label));
    return this;
  }

  @Override
  public HxCodeStream IFLE(final LABEL label) {
    current().append(factory.IFLE(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPEQ(final LABEL label) {
    current().append(factory.IF_ICMPEQ(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPNE(final LABEL label) {
    current().append(factory.IF_ICMPNE(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLT(final LABEL label) {
    current().append(factory.IF_ICMPLT(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGE(final LABEL label) {
    current().append(factory.IF_ICMPGE(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGT(final LABEL label) {
    current().append(factory.IF_ICMPGT(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLE(final LABEL label) {
    current().append(factory.IF_ICMPLE(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPEQ(final LABEL label) {
    current().append(factory.IF_ACMPEQ(label));
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPNE(final LABEL label) {
    current().append(factory.IF_ACMPNE(label));
    return this;
  }

  @Override
  public HxCodeStream GOTO(final LABEL label) {
    current().append(factory.GOTO(label));
    return this;
  }

  @Override
  public HxCodeStream JSR(final LABEL label) {
    current().append(factory.JSR(label));
    return this;
  }

  @Override
  public HxCodeStream RET(final int var) {
    current().append(factory.RET(var));
    return this;
  }

  @Override
  public HxCodeStream TABLESWITCH(final int min, final int max, final LABEL defaultLabel, final LABEL... labels) {
    current().append(factory.TABLESWITCH(min, max, defaultLabel, labels));
    return this;
  }

  @Override
  public HxCodeStream LOOKUPSWITCH(final LABEL label, final int[] keys, final LABEL[] labels) {
    current().append(factory.LOOKUPSWITCH(label, keys, labels));
    return this;
  }

  @Override
  public HxCodeStream IRETURN() {
    current().append(factory.IRETURN());
    return this;
  }

  @Override
  public HxCodeStream LRETURN() {
    current().append(factory.LRETURN());
    return this;
  }

  @Override
  public HxCodeStream FRETURN() {
    current().append(factory.FRETURN());
    return this;
  }

  @Override
  public HxCodeStream DRETURN() {
    current().append(factory.DRETURN());
    return this;
  }

  @Override
  public HxCodeStream ARETURN() {
    current().append(factory.ARETURN());
    return this;
  }

  @Override
  public HxCodeStream RETURN() {
    current().append(factory.RETURN());
    return this;
  }

  @Override
  public HxCodeStream GETSTATIC(final String owner, final String name, final String desc) {
    current().append(factory.GETSTATIC(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream PUTSTATIC(final String owner, final String name, final String desc) {
    current().append(factory.PUTSTATIC(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream GETFIELD(final String owner, final String name, final String desc) {
    current().append(factory.GETFIELD(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream PUTFIELD(final String owner, final String name, final String desc) {
    current().append(factory.PUTFIELD(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream INVOKEVIRTUAL(final String owner, final String name, final String desc) {
    current().append(factory.INVOKEVIRTUAL(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream INVOKESPECIAL(final String owner, final String name, final String desc) {
    current().append(factory.INVOKESPECIAL(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream INVOKESTATIC(final String owner, final String name, final String desc, final boolean isInterface) {
    current().append(factory.INVOKESTATIC(owner, name, desc, isInterface));
    return this;
  }

  @Override
  public HxCodeStream INVOKEINTERFACE(final String owner, final String name, final String desc) {
    current().append(factory.INVOKEINTERFACE(owner, name, desc));
    return this;
  }

  @Override
  public HxCodeStream INVOKEDYNAMIC(final String name, final String desc, final HxMethodHandle bsm, final HxArguments bsmArgs) {
    current().append(factory.INVOKEDYNAMIC(name, desc, bsm, bsmArgs));
    return this;
  }

  @Override
  public HxCodeStream NEW(final String internalType) {
    current().append(factory.NEW(internalType));
    return this;
  }

  @Override
  public HxCodeStream NEWARRAY(final HxArrayType type) {
    current().append(factory.NEWARRAY(type));
    return this;
  }

  @Override
  public HxCodeStream ANEWARRAY(final String internalType) {
    current().append(factory.ANEWARRAY(internalType));
    return this;
  }

  @Override
  public HxCodeStream ARRAYLENGTH() {
    current().append(factory.ARRAYLENGTH());
    return this;
  }

  @Override
  public HxCodeStream ATHROW() {
    current().append(factory.ATHROW());
    return this;
  }

  @Override
  public HxCodeStream CHECKCAST(final String internalType) {
    current().append(factory.CHECKCAST(internalType));
    return this;
  }

  @Override
  public HxCodeStream INSTANCEOF(final String internalType) {
    current().append(factory.INSTANCEOF(internalType));
    return this;
  }

  @Override
  public HxCodeStream MONITORENTER() {
    current().append(factory.MONITORENTER());
    return this;
  }

  @Override
  public HxCodeStream MONITOREXIT() {
    current().append(factory.MONITOREXIT());
    return this;
  }

  @Override
  public HxCodeStream MULTIANEWARRAY(final String internalType, final int dims) {
    current().append(factory.MULTIANEWARRAY(internalType, dims));
    return this;
  }

  @Override
  public HxCodeStream IFNULL(final LABEL label) {
    current().append(factory.IFNULL(label));
    return this;
  }

  @Override
  public HxCodeStream IFNONNULL(final LABEL label) {
    current().append(factory.IFNONNULL(label));
    return this;
  }

  @Override
  public HxCodeStream LABEL(final LABEL label) {
    current().append(label);
    return this;
  }

  @Override
  public HxCodeStream TRY_CATCH(final LABEL startLabel, final LABEL endLabel, final LABEL handler, final String type) {
    this.code.addTryCatch(new HxTryCatch(startLabel, endLabel, handler, type));
    return this;
  }

  @Override
  public HxCodeStream FRAME(final HxFrames type, final int nLocal, final Object[] local, final int nStack,
                            final Object[] stack) {
    current().append(factory.FRAME(type, nLocal, local, nStack, stack));
    return this;
  }

  @Override
  public HxCodeStream LOCAL_VARIABLE(final String name, final String desc, final String signature, final LABEL start,
                                     final LABEL end, final int index) {
    this.code.addLocalVariable(new HxLocalVariable(name, desc, signature, start, end, index));
    return this;
  }

  @Override
  public HxCodeStream LINE_NUMBER(final int line, final LABEL start) {
    current().append(factory.LINE_NUMBER(line, start));
    return this;
  }

  @Override
  public HxCodeStream MAXS(final int maxStack, final int maxLocals) {
    this.code.setMaxStack(maxStack);
    this.code.setMaxLocals(maxLocals);
    return this;
  }

  @Override
  public void END() {
    //NO OP
  }
}
