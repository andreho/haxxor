package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxInstructionFactoryGlobal;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class InstructionCodeStream
  implements HxExtendedCodeStream {

  protected final HxInstructionFactory factory;
  protected HxInstruction current;

  public InstructionCodeStream(final HxInstruction current) {
    this(current, HxInstructionFactoryGlobal.FactoryHolder.instance());
  }

  public InstructionCodeStream(final HxInstruction current, final HxInstructionFactory factory) {
    this.current = Objects.requireNonNull(current);
    this.factory = Objects.requireNonNull(factory);
  }

  private HxExtendedCodeStream newCurrent(HxInstruction current) {
    this.current = current;
    return this;
  }

  public HxInstruction getCurrent() {
    return current;
  }

  @Override
  public HxExtendedCodeStream BEGIN() {
    //NO OP
    return this;
  }

  @Override
  public HxExtendedCodeStream NOP() {
    return newCurrent(this.current.append(factory.NOP()));

  }

  @Override
  public HxExtendedCodeStream ACONST_NULL() {
    return newCurrent(this.current.append(factory.ACONST_NULL()));
  }

  @Override
  public HxExtendedCodeStream ICONST_M1() {
    return newCurrent(this.current.append(factory.ICONST(-1)));
  }

  @Override
  public HxExtendedCodeStream ICONST_0() {
    return newCurrent(this.current.append(factory.ICONST(0)));
  }

  @Override
  public HxExtendedCodeStream ICONST_1() {
    return newCurrent(this.current.append(factory.ICONST(1)));
  }

  @Override
  public HxExtendedCodeStream ICONST_2() {
    return newCurrent(this.current.append(factory.ICONST(2)));
  }

  @Override
  public HxExtendedCodeStream ICONST_3() {
    return newCurrent(this.current.append(factory.ICONST(3)));
  }

  @Override
  public HxExtendedCodeStream ICONST_4() {
    return newCurrent(this.current.append(factory.ICONST(4)));
  }

  @Override
  public HxExtendedCodeStream ICONST_5() {
    return newCurrent(this.current.append(factory.ICONST(5)));
  }

  @Override
  public HxExtendedCodeStream LCONST_0() {
    return newCurrent(this.current.append(factory.LCONST(0L)));
  }

  @Override
  public HxExtendedCodeStream LCONST_1() {
    return newCurrent(this.current.append(factory.LCONST(1L)));
  }

  @Override
  public HxExtendedCodeStream FCONST_0() {
    return newCurrent(this.current.append(factory.FCONST(0f)));
  }

  @Override
  public HxExtendedCodeStream FCONST_1() {
    return newCurrent(this.current.append(factory.FCONST(1f)));
  }

  @Override
  public HxExtendedCodeStream FCONST_2() {
    return newCurrent(this.current.append(factory.FCONST(2f)));
  }

  @Override
  public HxExtendedCodeStream DCONST_0() {
    return newCurrent(this.current.append(factory.DCONST(0d)));
  }

  @Override
  public HxExtendedCodeStream DCONST_1() {
    return newCurrent(this.current.append(factory.DCONST(1d)));
  }

  @Override
  public HxExtendedCodeStream BIPUSH(final byte value) {
    return newCurrent(this.current.append(factory.BIPUSH(value)));
  }

  @Override
  public HxExtendedCodeStream SIPUSH(final short value) {
    return newCurrent(this.current.append(factory.SIPUSH(value)));
  }

  @Override
  public HxExtendedCodeStream LDC(final int value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public HxExtendedCodeStream LDC(final float value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public HxExtendedCodeStream LDC(final long value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public HxExtendedCodeStream LDC(final double value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public HxExtendedCodeStream LDC(final String value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public HxExtendedCodeStream HANDLE(final HxMethodHandle handle) {
    return newCurrent(this.current.append(factory.HANDLE(handle)));
  }

  @Override
  public HxExtendedCodeStream METHOD(final HxMethodType methodType) {
    return newCurrent(this.current.append(factory.METHOD(methodType)));
  }

  @Override
  public HxExtendedCodeStream TYPE(final String typeDescriptor) {
    return newCurrent(this.current.append(factory.TYPE(typeDescriptor)));
  }

  @Override
  public HxExtendedCodeStream TYPE(final HxType type) {
    return newCurrent(this.current.append(factory.TYPE(type)));
  }

  @Override
  public HxExtendedCodeStream ILOAD(final int idx) {
    return newCurrent(this.current.append(factory.ILOAD(idx)));
  }

  @Override
  public HxExtendedCodeStream LLOAD(final int idx) {
    return newCurrent(this.current.append(factory.LLOAD(idx)));
  }

  @Override
  public HxExtendedCodeStream FLOAD(final int idx) {
    return newCurrent(this.current.append(factory.FLOAD(idx)));
  }

  @Override
  public HxExtendedCodeStream DLOAD(final int idx) {
    return newCurrent(this.current.append(factory.DLOAD(idx)));
  }

  @Override
  public HxExtendedCodeStream ALOAD(final int idx) {
    return newCurrent(this.current.append(factory.ALOAD(idx)));
  }

  @Override
  public HxExtendedCodeStream THIS() {
    return newCurrent(this.current.append(factory.ALOAD(0)));
  }

  @Override
  public HxExtendedCodeStream IALOAD() {
    return newCurrent(this.current.append(factory.IALOAD()));
  }

  @Override
  public HxExtendedCodeStream LALOAD() {
    return newCurrent(this.current.append(factory.LALOAD()));
  }

  @Override
  public HxExtendedCodeStream FALOAD() {
    return newCurrent(this.current.append(factory.FALOAD()));
  }

  @Override
  public HxExtendedCodeStream DALOAD() {
    return newCurrent(this.current.append(factory.DALOAD()));
  }

  @Override
  public HxExtendedCodeStream AALOAD() {
    return newCurrent(this.current.append(factory.AALOAD()));
  }

  @Override
  public HxExtendedCodeStream BALOAD() {
    return newCurrent(this.current.append(factory.BALOAD()));
  }

  @Override
  public HxExtendedCodeStream CALOAD() {
    return newCurrent(this.current.append(factory.CALOAD()));
  }

  @Override
  public HxExtendedCodeStream SALOAD() {
    return newCurrent(this.current.append(factory.SALOAD()));
  }

  @Override
  public HxExtendedCodeStream ISTORE(final int idx) {
    return newCurrent(this.current.append(factory.ISTORE(idx)));
  }

  @Override
  public HxExtendedCodeStream LSTORE(final int idx) {
    return newCurrent(this.current.append(factory.LSTORE(idx)));
  }

  @Override
  public HxExtendedCodeStream FSTORE(final int idx) {
    return newCurrent(this.current.append(factory.FSTORE(idx)));
  }

  @Override
  public HxExtendedCodeStream DSTORE(final int idx) {
    return newCurrent(this.current.append(factory.DSTORE(idx)));
  }

  @Override
  public HxExtendedCodeStream ASTORE(final int idx) {
    return newCurrent(this.current.append(factory.ASTORE(idx)));
  }

  @Override
  public HxExtendedCodeStream IASTORE() {
    return newCurrent(this.current.append(factory.IASTORE()));
  }

  @Override
  public HxExtendedCodeStream LASTORE() {
    return newCurrent(this.current.append(factory.LASTORE()));
  }

  @Override
  public HxExtendedCodeStream FASTORE() {
    return newCurrent(this.current.append(factory.FASTORE()));
  }

  @Override
  public HxExtendedCodeStream DASTORE() {
    return newCurrent(this.current.append(factory.DASTORE()));
  }

  @Override
  public HxExtendedCodeStream AASTORE() {
    return newCurrent(this.current.append(factory.AASTORE()));
  }

  @Override
  public HxExtendedCodeStream BASTORE() {
    return newCurrent(this.current.append(factory.BASTORE()));
  }

  @Override
  public HxExtendedCodeStream CASTORE() {
    return newCurrent(this.current.append(factory.CASTORE()));
  }

  @Override
  public HxExtendedCodeStream SASTORE() {
    return newCurrent(this.current.append(factory.SASTORE()));
  }

  @Override
  public HxExtendedCodeStream POP() {
    return newCurrent(this.current.append(factory.POP()));
  }

  @Override
  public HxExtendedCodeStream POP2() {
    return newCurrent(this.current.append(factory.POP2()));
  }

  @Override
  public HxExtendedCodeStream DUP() {
    return newCurrent(this.current.append(factory.DUP()));
  }

  @Override
  public HxExtendedCodeStream DUP_X1() {
    return newCurrent(this.current.append(factory.DUP_X1()));
  }

  @Override
  public HxExtendedCodeStream DUP_X2() {
    return newCurrent(this.current.append(factory.DUP_X2()));
  }

  @Override
  public HxExtendedCodeStream DUP2() {
    return newCurrent(this.current.append(factory.DUP2()));
  }

  @Override
  public HxExtendedCodeStream DUP2_X1() {
    return newCurrent(this.current.append(factory.DUP2_X1()));
  }

  @Override
  public HxExtendedCodeStream DUP2_X2() {
    return newCurrent(this.current.append(factory.DUP2_X2()));
  }

  @Override
  public HxExtendedCodeStream SWAP() {
    return newCurrent(this.current.append(factory.SWAP()));
  }

  @Override
  public HxExtendedCodeStream IADD() {
    return newCurrent(this.current.append(factory.IADD()));
  }

  @Override
  public HxExtendedCodeStream LADD() {
    return newCurrent(this.current.append(factory.LADD()));
  }

  @Override
  public HxExtendedCodeStream FADD() {
    return newCurrent(this.current.append(factory.FADD()));
  }

  @Override
  public HxExtendedCodeStream DADD() {
    return newCurrent(this.current.append(factory.DADD()));
  }

  @Override
  public HxExtendedCodeStream ISUB() {
    return newCurrent(this.current.append(factory.ISUB()));
  }

  @Override
  public HxExtendedCodeStream LSUB() {
    return newCurrent(this.current.append(factory.LSUB()));
  }

  @Override
  public HxExtendedCodeStream FSUB() {
    return newCurrent(this.current.append(factory.FSUB()));
  }

  @Override
  public HxExtendedCodeStream DSUB() {
    return newCurrent(this.current.append(factory.DSUB()));
  }

  @Override
  public HxExtendedCodeStream IMUL() {
    return newCurrent(this.current.append(factory.IMUL()));
  }

  @Override
  public HxExtendedCodeStream LMUL() {
    return newCurrent(this.current.append(factory.LMUL()));
  }

  @Override
  public HxExtendedCodeStream FMUL() {
    return newCurrent(this.current.append(factory.FMUL()));
  }

  @Override
  public HxExtendedCodeStream DMUL() {
    return newCurrent(this.current.append(factory.DMUL()));
  }

  @Override
  public HxExtendedCodeStream IDIV() {
    return newCurrent(this.current.append(factory.IDIV()));
  }

  @Override
  public HxExtendedCodeStream LDIV() {
    return newCurrent(this.current.append(factory.LDIV()));
  }

  @Override
  public HxExtendedCodeStream FDIV() {
    return newCurrent(this.current.append(factory.FDIV()));
  }

  @Override
  public HxExtendedCodeStream DDIV() {
    return newCurrent(this.current.append(factory.DDIV()));
  }

  @Override
  public HxExtendedCodeStream IREM() {
    return newCurrent(this.current.append(factory.IREM()));
  }

  @Override
  public HxExtendedCodeStream LREM() {
    return newCurrent(this.current.append(factory.LREM()));
  }

  @Override
  public HxExtendedCodeStream FREM() {
    return newCurrent(this.current.append(factory.FREM()));
  }

  @Override
  public HxExtendedCodeStream DREM() {
    return newCurrent(this.current.append(factory.DREM()));
  }

  @Override
  public HxExtendedCodeStream INEG() {
    return newCurrent(this.current.append(factory.INEG()));
  }

  @Override
  public HxExtendedCodeStream LNEG() {
    return newCurrent(this.current.append(factory.LNEG()));
  }

  @Override
  public HxExtendedCodeStream FNEG() {
    return newCurrent(this.current.append(factory.FNEG()));
  }

  @Override
  public HxExtendedCodeStream DNEG() {
    return newCurrent(this.current.append(factory.DNEG()));
  }

  @Override
  public HxExtendedCodeStream ISHL() {
    return newCurrent(this.current.append(factory.ISHL()));
  }

  @Override
  public HxExtendedCodeStream LSHL() {
    return newCurrent(this.current.append(factory.LSHL()));
  }

  @Override
  public HxExtendedCodeStream ISHR() {
    return newCurrent(this.current.append(factory.ISHR()));
  }

  @Override
  public HxExtendedCodeStream LSHR() {
    return newCurrent(this.current.append(factory.LSHR()));
  }

  @Override
  public HxExtendedCodeStream IUSHR() {
    return newCurrent(this.current.append(factory.IUSHR()));
  }

  @Override
  public HxExtendedCodeStream LUSHR() {
    return newCurrent(this.current.append(factory.LUSHR()));
  }

  @Override
  public HxExtendedCodeStream IAND() {
    return newCurrent(this.current.append(factory.IAND()));
  }

  @Override
  public HxExtendedCodeStream LAND() {
    return newCurrent(this.current.append(factory.LADD()));
  }

  @Override
  public HxExtendedCodeStream IOR() {
    return newCurrent(this.current.append(factory.IOR()));
  }

  @Override
  public HxExtendedCodeStream LOR() {
    return newCurrent(this.current.append(factory.LOR()));
  }

  @Override
  public HxExtendedCodeStream IXOR() {
    return newCurrent(this.current.append(factory.IXOR()));
  }

  @Override
  public HxExtendedCodeStream LXOR() {
    return newCurrent(this.current.append(factory.LXOR()));
  }

  @Override
  public HxExtendedCodeStream IINC(final int var, final int increment) {
    return newCurrent(this.current.append(factory.IINC(var, increment)));
  }

  @Override
  public HxExtendedCodeStream I2L() {
    return newCurrent(this.current.append(factory.I2L()));
  }

  @Override
  public HxExtendedCodeStream I2F() {
    return newCurrent(this.current.append(factory.I2F()));
  }

  @Override
  public HxExtendedCodeStream I2D() {
    return newCurrent(this.current.append(factory.I2D()));
  }

  @Override
  public HxExtendedCodeStream L2I() {
    return newCurrent(this.current.append(factory.L2I()));
  }

  @Override
  public HxExtendedCodeStream L2F() {
    return newCurrent(this.current.append(factory.L2F()));
  }

  @Override
  public HxExtendedCodeStream L2D() {
    return newCurrent(this.current.append(factory.L2D()));
  }

  @Override
  public HxExtendedCodeStream F2I() {
    return newCurrent(this.current.append(factory.F2I()));
  }

  @Override
  public HxExtendedCodeStream F2L() {
    return newCurrent(this.current.append(factory.F2L()));
  }

  @Override
  public HxExtendedCodeStream F2D() {
    return newCurrent(this.current.append(factory.F2D()));
  }

  @Override
  public HxExtendedCodeStream D2I() {
    return newCurrent(this.current.append(factory.D2I()));
  }

  @Override
  public HxExtendedCodeStream D2L() {
    return newCurrent(this.current.append(factory.D2L()));
  }

  @Override
  public HxExtendedCodeStream D2F() {
    return newCurrent(this.current.append(factory.D2F()));
  }

  @Override
  public HxExtendedCodeStream I2B() {
    return newCurrent(this.current.append(factory.I2B()));
  }

  @Override
  public HxExtendedCodeStream I2C() {
    return newCurrent(this.current.append(factory.I2C()));
  }

  @Override
  public HxExtendedCodeStream I2S() {
    return newCurrent(this.current.append(factory.I2S()));
  }

  @Override
  public HxExtendedCodeStream LCMP() {
    return newCurrent(this.current.append(factory.LCMP()));
  }

  @Override
  public HxExtendedCodeStream FCMPL() {
    return newCurrent(this.current.append(factory.FCMPL()));
  }

  @Override
  public HxExtendedCodeStream FCMPG() {
    return newCurrent(this.current.append(factory.FCMPG()));
  }

  @Override
  public HxExtendedCodeStream DCMPL() {
    return newCurrent(this.current.append(factory.DCMPL()));
  }

  @Override
  public HxExtendedCodeStream DCMPG() {
    return newCurrent(this.current.append(factory.DCMPG()));
  }

  @Override
  public HxExtendedCodeStream IFEQ(final LABEL label) {
    return newCurrent(this.current.append(factory.IFEQ(label)));
  }

  @Override
  public HxExtendedCodeStream IFNE(final LABEL label) {
    return newCurrent(this.current.append(factory.IFNE(label)));
  }

  @Override
  public HxExtendedCodeStream IFLT(final LABEL label) {
    return newCurrent(this.current.append(factory.IFLT(label)));
  }

  @Override
  public HxExtendedCodeStream IFGE(final LABEL label) {
    return newCurrent(this.current.append(factory.IFGE(label)));
  }

  @Override
  public HxExtendedCodeStream IFGT(final LABEL label) {
    return newCurrent(this.current.append(factory.IFGT(label)));
  }

  @Override
  public HxExtendedCodeStream IFLE(final LABEL label) {
    return newCurrent(this.current.append(factory.IFLE(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ICMPEQ(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPEQ(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ICMPNE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPNE(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ICMPLT(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPLT(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ICMPGE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPGE(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ICMPGT(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPGT(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ICMPLE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPLE(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ACMPEQ(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ACMPEQ(label)));
  }

  @Override
  public HxExtendedCodeStream IF_ACMPNE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ACMPNE(label)));
  }

  @Override
  public HxExtendedCodeStream GOTO(final LABEL label) {
    return newCurrent(this.current.append(factory.GOTO(label)));
  }

  @Override
  public HxExtendedCodeStream JSR(final LABEL label) {
    return newCurrent(this.current.append(factory.JSR(label)));
  }

  @Override
  public HxExtendedCodeStream RET(final int var) {
    return newCurrent(this.current.append(factory.RET(var)));
  }

  @Override
  public HxExtendedCodeStream TABLESWITCH(final int min, final int max, final LABEL defaultLabel, final LABEL... labels) {
    return newCurrent(this.current.append(factory.TABLESWITCH(min, max, defaultLabel, labels)));
  }

  @Override
  public HxExtendedCodeStream LOOKUPSWITCH(final LABEL label, final int[] keys, final LABEL[] labels) {
    return newCurrent(this.current.append(factory.LOOKUPSWITCH(label, keys, labels)));
  }

  @Override
  public HxExtendedCodeStream IRETURN() {
    return newCurrent(this.current.append(factory.IRETURN()));
  }

  @Override
  public HxExtendedCodeStream LRETURN() {
    return newCurrent(this.current.append(factory.LRETURN()));
  }

  @Override
  public HxExtendedCodeStream FRETURN() {
    return newCurrent(this.current.append(factory.FRETURN()));
  }

  @Override
  public HxExtendedCodeStream DRETURN() {
    return newCurrent(this.current.append(factory.DRETURN()));
  }

  @Override
  public HxExtendedCodeStream ARETURN() {
    return newCurrent(this.current.append(factory.ARETURN()));
  }

  @Override
  public HxExtendedCodeStream RETURN() {
    return newCurrent(this.current.append(factory.RETURN()));
  }

  @Override
  public HxExtendedCodeStream GETSTATIC(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.GETSTATIC(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream PUTSTATIC(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.PUTSTATIC(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream GETFIELD(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.GETFIELD(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream PUTFIELD(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.PUTFIELD(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream INVOKEVIRTUAL(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.INVOKEVIRTUAL(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream INVOKESPECIAL(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.INVOKESPECIAL(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream INVOKESTATIC(final String owner, final String name, final String desc, final boolean isInterface) {
    return newCurrent(this.current.append(factory.INVOKESTATIC(owner, name, desc, isInterface)));
  }

  @Override
  public HxExtendedCodeStream INVOKEINTERFACE(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.INVOKEINTERFACE(owner, name, desc)));
  }

  @Override
  public HxExtendedCodeStream INVOKEDYNAMIC(final String name, final String desc, final HxMethodHandle bsm, final HxArguments bsmArgs) {
    return newCurrent(this.current.append(factory.INVOKEDYNAMIC(name, desc, bsm, bsmArgs)));
  }

  @Override
  public HxExtendedCodeStream NEW(final String internalType) {
    return newCurrent(this.current.append(factory.NEW(internalType)));
  }

  @Override
  public HxExtendedCodeStream NEWARRAY(final HxArrayType type) {
    return newCurrent(this.current.append(factory.NEWARRAY(type)));
  }

  @Override
  public HxExtendedCodeStream ANEWARRAY(final String internalType) {
    return newCurrent(this.current.append(factory.ANEWARRAY(internalType)));
  }

  @Override
  public HxExtendedCodeStream ARRAYLENGTH() {
    return newCurrent(this.current.append(factory.ARRAYLENGTH()));
  }

  @Override
  public HxExtendedCodeStream ATHROW() {
    return newCurrent(this.current.append(factory.ATHROW()));
  }

  @Override
  public HxExtendedCodeStream CHECKCAST(final String internalType) {
    return newCurrent(this.current.append(factory.CHECKCAST(internalType)));
  }

  @Override
  public HxExtendedCodeStream INSTANCEOF(final String internalType) {
    return newCurrent(this.current.append(factory.INSTANCEOF(internalType)));
  }

  @Override
  public HxExtendedCodeStream MONITORENTER() {
    return newCurrent(this.current.append(factory.MONITORENTER()));
  }

  @Override
  public HxExtendedCodeStream MONITOREXIT() {
    return newCurrent(this.current.append(factory.MONITOREXIT()));
  }

  @Override
  public HxExtendedCodeStream MULTIANEWARRAY(final String internalType, final int dims) {
    return newCurrent(this.current.append(factory.MULTIANEWARRAY(internalType, dims)));
  }

  @Override
  public HxExtendedCodeStream IFNULL(final LABEL label) {
    return newCurrent(this.current.append(factory.IFNULL(label)));
  }

  @Override
  public HxExtendedCodeStream IFNONNULL(final LABEL label) {
    return newCurrent(this.current.append(factory.IFNONNULL(label)));
  }

  @Override
  public HxExtendedCodeStream LABEL(final LABEL label) {
    return newCurrent(this.current.append(label));
  }

  @Override
  public HxExtendedCodeStream TRY_CATCH(final LABEL startLabel, final LABEL endLabel, final LABEL handler, final String exceptionTypename) {
    throw new UnsupportedOperationException("Use "+ExtendedInstructionCodeStream.class.getName());
  }

  @Override
  public HxExtendedCodeStream FRAME(final HxFrames type, final int nLocal, final Object[] local, final int nStack,
                            final Object[] stack) {
    return newCurrent(this.current.append(factory.FRAME(type, nLocal, local, nStack, stack)));
  }

  @Override
  public HxExtendedCodeStream LOCAL_VARIABLE(final String name,
                                             final int index,
                                             final String desc,
                                             final String signature,
                                             final LABEL start,
                                             final LABEL end) {
    throw new UnsupportedOperationException("Use "+ExtendedInstructionCodeStream.class.getName());
  }

  @Override
  public HxExtendedCodeStream LINE_NUMBER(final int line, final LABEL start) {
    return newCurrent(this.current.append(factory.LINE_NUMBER(line, start)));
  }

  @Override
  public HxExtendedCodeStream MAXS(final int maxStack, final int maxLocals) {
    throw new UnsupportedOperationException("Use "+ExtendedInstructionCodeStream.class.getName());
  }

  @Override
  public void END() {
    //NO OP
  }

  @Override
  public String toString() {
    return String.valueOf(this.current);
  }
}
