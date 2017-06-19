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
public class InstructionCodeStream<Stream extends HxExtendedCodeStream<Stream>>
  implements HxExtendedCodeStream<Stream> {

  protected final HxInstructionFactory factory;
  protected HxInstruction current;

  public InstructionCodeStream(final HxInstruction current) {
    this(current, HxInstructionFactoryGlobal.FactoryHolder.instance());
  }

  public InstructionCodeStream(final HxInstruction current, final HxInstructionFactory factory) {
    this.current = Objects.requireNonNull(current);
    this.factory = Objects.requireNonNull(factory);
  }

  private Stream newCurrent(HxInstruction current) {
    this.current = current;
    return (Stream) this;
  }

  public HxInstruction getCurrent() {
    return current;
  }

  @Override
  public Stream BEGIN() {
    //NO OP
    return (Stream) this;
  }

  @Override
  public Stream NOP() {
    return newCurrent(this.current.append(factory.NOP()));

  }

  @Override
  public Stream ACONST_NULL() {
    return newCurrent(this.current.append(factory.ACONST_NULL()));
  }

  @Override
  public Stream ICONST_M1() {
    return newCurrent(this.current.append(factory.ICONST(-1)));
  }

  @Override
  public Stream ICONST_0() {
    return newCurrent(this.current.append(factory.ICONST(0)));
  }

  @Override
  public Stream ICONST_1() {
    return newCurrent(this.current.append(factory.ICONST(1)));
  }

  @Override
  public Stream ICONST_2() {
    return newCurrent(this.current.append(factory.ICONST(2)));
  }

  @Override
  public Stream ICONST_3() {
    return newCurrent(this.current.append(factory.ICONST(3)));
  }

  @Override
  public Stream ICONST_4() {
    return newCurrent(this.current.append(factory.ICONST(4)));
  }

  @Override
  public Stream ICONST_5() {
    return newCurrent(this.current.append(factory.ICONST(5)));
  }

  @Override
  public Stream LCONST_0() {
    return newCurrent(this.current.append(factory.LCONST(0L)));
  }

  @Override
  public Stream LCONST_1() {
    return newCurrent(this.current.append(factory.LCONST(1L)));
  }

  @Override
  public Stream FCONST_0() {
    return newCurrent(this.current.append(factory.FCONST(0f)));
  }

  @Override
  public Stream FCONST_1() {
    return newCurrent(this.current.append(factory.FCONST(1f)));
  }

  @Override
  public Stream FCONST_2() {
    return newCurrent(this.current.append(factory.FCONST(2f)));
  }

  @Override
  public Stream DCONST_0() {
    return newCurrent(this.current.append(factory.DCONST(0d)));
  }

  @Override
  public Stream DCONST_1() {
    return newCurrent(this.current.append(factory.DCONST(1d)));
  }

  @Override
  public Stream BIPUSH(final byte value) {
    return newCurrent(this.current.append(factory.BIPUSH(value)));
  }

  @Override
  public Stream SIPUSH(final short value) {
    return newCurrent(this.current.append(factory.SIPUSH(value)));
  }

  @Override
  public Stream LDC(final int value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public Stream LDC(final float value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public Stream LDC(final long value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public Stream LDC(final double value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public Stream LDC(final String value) {
    return newCurrent(this.current.append(factory.LDC(value)));
  }

  @Override
  public Stream HANDLE(final HxMethodHandle handle) {
    return newCurrent(this.current.append(factory.HANDLE(handle)));
  }

  @Override
  public Stream METHOD(final HxMethodType methodType) {
    return newCurrent(this.current.append(factory.METHOD(methodType)));
  }

  @Override
  public Stream TYPE(final String internalType) {
    return newCurrent(this.current.append(factory.TYPE(internalType)));
  }

  @Override
  public Stream TYPE(final HxType type) {
    return newCurrent(this.current.append(factory.TYPE(type)));
  }

  @Override
  public Stream ILOAD(final int idx) {
    return newCurrent(this.current.append(factory.ILOAD(idx)));
  }

  @Override
  public Stream LLOAD(final int idx) {
    return newCurrent(this.current.append(factory.LLOAD(idx)));
  }

  @Override
  public Stream FLOAD(final int idx) {
    return newCurrent(this.current.append(factory.FLOAD(idx)));
  }

  @Override
  public Stream DLOAD(final int idx) {
    return newCurrent(this.current.append(factory.DLOAD(idx)));
  }

  @Override
  public Stream ALOAD(final int idx) {
    return newCurrent(this.current.append(factory.ALOAD(idx)));
  }

  @Override
  public Stream THIS() {
    return newCurrent(this.current.append(factory.ALOAD(0)));
  }

  @Override
  public Stream IALOAD() {
    return newCurrent(this.current.append(factory.IALOAD()));
  }

  @Override
  public Stream LALOAD() {
    return newCurrent(this.current.append(factory.LALOAD()));
  }

  @Override
  public Stream FALOAD() {
    return newCurrent(this.current.append(factory.FALOAD()));
  }

  @Override
  public Stream DALOAD() {
    return newCurrent(this.current.append(factory.DALOAD()));
  }

  @Override
  public Stream AALOAD() {
    return newCurrent(this.current.append(factory.AALOAD()));
  }

  @Override
  public Stream BALOAD() {
    return newCurrent(this.current.append(factory.BALOAD()));
  }

  @Override
  public Stream CALOAD() {
    return newCurrent(this.current.append(factory.CALOAD()));
  }

  @Override
  public Stream SALOAD() {
    return newCurrent(this.current.append(factory.SALOAD()));
  }

  @Override
  public Stream ISTORE(final int idx) {
    return newCurrent(this.current.append(factory.ISTORE(idx)));
  }

  @Override
  public Stream LSTORE(final int idx) {
    return newCurrent(this.current.append(factory.LSTORE(idx)));
  }

  @Override
  public Stream FSTORE(final int idx) {
    return newCurrent(this.current.append(factory.FSTORE(idx)));
  }

  @Override
  public Stream DSTORE(final int idx) {
    return newCurrent(this.current.append(factory.DSTORE(idx)));
  }

  @Override
  public Stream ASTORE(final int idx) {
    return newCurrent(this.current.append(factory.ASTORE(idx)));
  }

  @Override
  public Stream IASTORE() {
    return newCurrent(this.current.append(factory.IASTORE()));
  }

  @Override
  public Stream LASTORE() {
    return newCurrent(this.current.append(factory.LASTORE()));
  }

  @Override
  public Stream FASTORE() {
    return newCurrent(this.current.append(factory.FASTORE()));
  }

  @Override
  public Stream DASTORE() {
    return newCurrent(this.current.append(factory.DASTORE()));
  }

  @Override
  public Stream AASTORE() {
    return newCurrent(this.current.append(factory.AASTORE()));
  }

  @Override
  public Stream BASTORE() {
    return newCurrent(this.current.append(factory.BASTORE()));
  }

  @Override
  public Stream CASTORE() {
    return newCurrent(this.current.append(factory.CASTORE()));
  }

  @Override
  public Stream SASTORE() {
    return newCurrent(this.current.append(factory.SASTORE()));
  }

  @Override
  public Stream POP() {
    return newCurrent(this.current.append(factory.POP()));
  }

  @Override
  public Stream POP2() {
    return newCurrent(this.current.append(factory.POP2()));
  }

  @Override
  public Stream DUP() {
    return newCurrent(this.current.append(factory.DUP()));
  }

  @Override
  public Stream DUP_X1() {
    return newCurrent(this.current.append(factory.DUP_X1()));
  }

  @Override
  public Stream DUP_X2() {
    return newCurrent(this.current.append(factory.DUP_X2()));
  }

  @Override
  public Stream DUP2() {
    return newCurrent(this.current.append(factory.DUP2()));
  }

  @Override
  public Stream DUP2_X1() {
    return newCurrent(this.current.append(factory.DUP2_X1()));
  }

  @Override
  public Stream DUP2_X2() {
    return newCurrent(this.current.append(factory.DUP2_X2()));
  }

  @Override
  public Stream SWAP() {
    return newCurrent(this.current.append(factory.SWAP()));
  }

  @Override
  public Stream IADD() {
    return newCurrent(this.current.append(factory.IADD()));
  }

  @Override
  public Stream LADD() {
    return newCurrent(this.current.append(factory.LADD()));
  }

  @Override
  public Stream FADD() {
    return newCurrent(this.current.append(factory.FADD()));
  }

  @Override
  public Stream DADD() {
    return newCurrent(this.current.append(factory.DADD()));
  }

  @Override
  public Stream ISUB() {
    return newCurrent(this.current.append(factory.ISUB()));
  }

  @Override
  public Stream LSUB() {
    return newCurrent(this.current.append(factory.LSUB()));
  }

  @Override
  public Stream FSUB() {
    return newCurrent(this.current.append(factory.FSUB()));
  }

  @Override
  public Stream DSUB() {
    return newCurrent(this.current.append(factory.DSUB()));
  }

  @Override
  public Stream IMUL() {
    return newCurrent(this.current.append(factory.IMUL()));
  }

  @Override
  public Stream LMUL() {
    return newCurrent(this.current.append(factory.LMUL()));
  }

  @Override
  public Stream FMUL() {
    return newCurrent(this.current.append(factory.FMUL()));
  }

  @Override
  public Stream DMUL() {
    return newCurrent(this.current.append(factory.DMUL()));
  }

  @Override
  public Stream IDIV() {
    return newCurrent(this.current.append(factory.IDIV()));
  }

  @Override
  public Stream LDIV() {
    return newCurrent(this.current.append(factory.LDIV()));
  }

  @Override
  public Stream FDIV() {
    return newCurrent(this.current.append(factory.FDIV()));
  }

  @Override
  public Stream DDIV() {
    return newCurrent(this.current.append(factory.DDIV()));
  }

  @Override
  public Stream IREM() {
    return newCurrent(this.current.append(factory.IREM()));
  }

  @Override
  public Stream LREM() {
    return newCurrent(this.current.append(factory.LREM()));
  }

  @Override
  public Stream FREM() {
    return newCurrent(this.current.append(factory.FREM()));
  }

  @Override
  public Stream DREM() {
    return newCurrent(this.current.append(factory.DREM()));
  }

  @Override
  public Stream INEG() {
    return newCurrent(this.current.append(factory.INEG()));
  }

  @Override
  public Stream LNEG() {
    return newCurrent(this.current.append(factory.LNEG()));
  }

  @Override
  public Stream FNEG() {
    return newCurrent(this.current.append(factory.FNEG()));
  }

  @Override
  public Stream DNEG() {
    return newCurrent(this.current.append(factory.DNEG()));
  }

  @Override
  public Stream ISHL() {
    return newCurrent(this.current.append(factory.ISHL()));
  }

  @Override
  public Stream LSHL() {
    return newCurrent(this.current.append(factory.LSHL()));
  }

  @Override
  public Stream ISHR() {
    return newCurrent(this.current.append(factory.ISHR()));
  }

  @Override
  public Stream LSHR() {
    return newCurrent(this.current.append(factory.LSHR()));
  }

  @Override
  public Stream IUSHR() {
    return newCurrent(this.current.append(factory.IUSHR()));
  }

  @Override
  public Stream LUSHR() {
    return newCurrent(this.current.append(factory.LUSHR()));
  }

  @Override
  public Stream IAND() {
    return newCurrent(this.current.append(factory.IADD()));
  }

  @Override
  public Stream LAND() {
    return newCurrent(this.current.append(factory.LADD()));
  }

  @Override
  public Stream IOR() {
    return newCurrent(this.current.append(factory.IOR()));
  }

  @Override
  public Stream LOR() {
    return newCurrent(this.current.append(factory.LOR()));
  }

  @Override
  public Stream IXOR() {
    return newCurrent(this.current.append(factory.IXOR()));
  }

  @Override
  public Stream LXOR() {
    return newCurrent(this.current.append(factory.LXOR()));
  }

  @Override
  public Stream IINC(final int var, final int increment) {
    return newCurrent(this.current.append(factory.IINC(var, increment)));
  }

  @Override
  public Stream I2L() {
    return newCurrent(this.current.append(factory.I2L()));
  }

  @Override
  public Stream I2F() {
    return newCurrent(this.current.append(factory.I2F()));
  }

  @Override
  public Stream I2D() {
    return newCurrent(this.current.append(factory.I2D()));
  }

  @Override
  public Stream L2I() {
    return newCurrent(this.current.append(factory.L2I()));
  }

  @Override
  public Stream L2F() {
    return newCurrent(this.current.append(factory.L2F()));
  }

  @Override
  public Stream L2D() {
    return newCurrent(this.current.append(factory.L2D()));
  }

  @Override
  public Stream F2I() {
    return newCurrent(this.current.append(factory.F2I()));
  }

  @Override
  public Stream F2L() {
    return newCurrent(this.current.append(factory.F2L()));
  }

  @Override
  public Stream F2D() {
    return newCurrent(this.current.append(factory.F2D()));
  }

  @Override
  public Stream D2I() {
    return newCurrent(this.current.append(factory.D2I()));
  }

  @Override
  public Stream D2L() {
    return newCurrent(this.current.append(factory.D2L()));
  }

  @Override
  public Stream D2F() {
    return newCurrent(this.current.append(factory.D2F()));
  }

  @Override
  public Stream I2B() {
    return newCurrent(this.current.append(factory.I2B()));
  }

  @Override
  public Stream I2C() {
    return newCurrent(this.current.append(factory.I2C()));
  }

  @Override
  public Stream I2S() {
    return newCurrent(this.current.append(factory.I2S()));
  }

  @Override
  public Stream LCMP() {
    return newCurrent(this.current.append(factory.LCMP()));
  }

  @Override
  public Stream FCMPL() {
    return newCurrent(this.current.append(factory.FCMPL()));
  }

  @Override
  public Stream FCMPG() {
    return newCurrent(this.current.append(factory.FCMPG()));
  }

  @Override
  public Stream DCMPL() {
    return newCurrent(this.current.append(factory.DCMPL()));
  }

  @Override
  public Stream DCMPG() {
    return newCurrent(this.current.append(factory.DCMPG()));
  }

  @Override
  public Stream IFEQ(final LABEL label) {
    return newCurrent(this.current.append(factory.IFEQ(label)));
  }

  @Override
  public Stream IFNE(final LABEL label) {
    return newCurrent(this.current.append(factory.IFNE(label)));
  }

  @Override
  public Stream IFLT(final LABEL label) {
    return newCurrent(this.current.append(factory.IFLT(label)));
  }

  @Override
  public Stream IFGE(final LABEL label) {
    return newCurrent(this.current.append(factory.IFGE(label)));
  }

  @Override
  public Stream IFGT(final LABEL label) {
    return newCurrent(this.current.append(factory.IFGT(label)));
  }

  @Override
  public Stream IFLE(final LABEL label) {
    return newCurrent(this.current.append(factory.IFLE(label)));
  }

  @Override
  public Stream IF_ICMPEQ(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPEQ(label)));
  }

  @Override
  public Stream IF_ICMPNE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPNE(label)));
  }

  @Override
  public Stream IF_ICMPLT(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPLT(label)));
  }

  @Override
  public Stream IF_ICMPGE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPGE(label)));
  }

  @Override
  public Stream IF_ICMPGT(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPGT(label)));
  }

  @Override
  public Stream IF_ICMPLE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ICMPLE(label)));
  }

  @Override
  public Stream IF_ACMPEQ(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ACMPEQ(label)));
  }

  @Override
  public Stream IF_ACMPNE(final LABEL label) {
    return newCurrent(this.current.append(factory.IF_ACMPNE(label)));
  }

  @Override
  public Stream GOTO(final LABEL label) {
    return newCurrent(this.current.append(factory.GOTO(label)));
  }

  @Override
  public Stream JSR(final LABEL label) {
    return newCurrent(this.current.append(factory.JSR(label)));
  }

  @Override
  public Stream RET(final int var) {
    return newCurrent(this.current.append(factory.RET(var)));
  }

  @Override
  public Stream TABLESWITCH(final int min, final int max, final LABEL defaultLabel, final LABEL... labels) {
    return newCurrent(this.current.append(factory.TABLESWITCH(min, max, defaultLabel, labels)));
  }

  @Override
  public Stream LOOKUPSWITCH(final LABEL label, final int[] keys, final LABEL[] labels) {
    return newCurrent(this.current.append(factory.LOOKUPSWITCH(label, keys, labels)));
  }

  @Override
  public Stream IRETURN() {
    return newCurrent(this.current.append(factory.IRETURN()));
  }

  @Override
  public Stream LRETURN() {
    return newCurrent(this.current.append(factory.LRETURN()));
  }

  @Override
  public Stream FRETURN() {
    return newCurrent(this.current.append(factory.FRETURN()));
  }

  @Override
  public Stream DRETURN() {
    return newCurrent(this.current.append(factory.DRETURN()));
  }

  @Override
  public Stream ARETURN() {
    return newCurrent(this.current.append(factory.ARETURN()));
  }

  @Override
  public Stream RETURN() {
    return newCurrent(this.current.append(factory.RETURN()));
  }

  @Override
  public Stream GETSTATIC(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.GETSTATIC(owner, name, desc)));
  }

  @Override
  public Stream PUTSTATIC(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.PUTSTATIC(owner, name, desc)));
  }

  @Override
  public Stream GETFIELD(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.GETFIELD(owner, name, desc)));
  }

  @Override
  public Stream PUTFIELD(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.PUTFIELD(owner, name, desc)));
  }

  @Override
  public Stream INVOKEVIRTUAL(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.INVOKEVIRTUAL(owner, name, desc)));
  }

  @Override
  public Stream INVOKESPECIAL(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.INVOKESPECIAL(owner, name, desc)));
  }

  @Override
  public Stream INVOKESTATIC(final String owner, final String name, final String desc, final boolean isInterface) {
    return newCurrent(this.current.append(factory.INVOKESTATIC(owner, name, desc, isInterface)));
  }

  @Override
  public Stream INVOKEINTERFACE(final String owner, final String name, final String desc) {
    return newCurrent(this.current.append(factory.INVOKEINTERFACE(owner, name, desc)));
  }

  @Override
  public Stream INVOKEDYNAMIC(final String name, final String desc, final HxMethodHandle bsm, final HxArguments bsmArgs) {
    return newCurrent(this.current.append(factory.INVOKEDYNAMIC(name, desc, bsm, bsmArgs)));
  }

  @Override
  public Stream NEW(final String internalType) {
    return newCurrent(this.current.append(factory.NEW(internalType)));
  }

  @Override
  public Stream NEWARRAY(final HxArrayType type) {
    return newCurrent(this.current.append(factory.NEWARRAY(type)));
  }

  @Override
  public Stream ANEWARRAY(final String internalType) {
    return newCurrent(this.current.append(factory.ANEWARRAY(internalType)));
  }

  @Override
  public Stream ARRAYLENGTH() {
    return newCurrent(this.current.append(factory.ARRAYLENGTH()));
  }

  @Override
  public Stream ATHROW() {
    return newCurrent(this.current.append(factory.ATHROW()));
  }

  @Override
  public Stream CHECKCAST(final String internalType) {
    return newCurrent(this.current.append(factory.CHECKCAST(internalType)));
  }

  @Override
  public Stream INSTANCEOF(final String internalType) {
    return newCurrent(this.current.append(factory.INSTANCEOF(internalType)));
  }

  @Override
  public Stream MONITORENTER() {
    return newCurrent(this.current.append(factory.MONITORENTER()));
  }

  @Override
  public Stream MONITOREXIT() {
    return newCurrent(this.current.append(factory.MONITOREXIT()));
  }

  @Override
  public Stream MULTIANEWARRAY(final String internalType, final int dims) {
    return newCurrent(this.current.append(factory.MULTIANEWARRAY(internalType, dims)));
  }

  @Override
  public Stream IFNULL(final LABEL label) {
    return newCurrent(this.current.append(factory.IFNULL(label)));
  }

  @Override
  public Stream IFNONNULL(final LABEL label) {
    return newCurrent(this.current.append(factory.IFNONNULL(label)));
  }

  @Override
  public Stream LABEL(final LABEL label) {
    return newCurrent(this.current.append(label));
  }

  @Override
  public Stream TRY_CATCH(final LABEL startLabel, final LABEL endLabel, final LABEL handler, final String type) {
    throw new UnsupportedOperationException("Use "+ExtendedInstructionCodeStream.class.getName());
  }

  @Override
  public Stream FRAME(final HxFrames type, final int nLocal, final Object[] local, final int nStack,
                            final Object[] stack) {
    return newCurrent(this.current.append(factory.FRAME(type, nLocal, local, nStack, stack)));
  }

  @Override
  public Stream LOCAL_VARIABLE(final String name, final String desc, final String signature, final LABEL start,
                                     final LABEL end, final int index) {
    throw new UnsupportedOperationException("Use "+ExtendedInstructionCodeStream.class.getName());
  }

  @Override
  public Stream LINE_NUMBER(final int line, final LABEL start) {
    return newCurrent(this.current.append(factory.LINE_NUMBER(line, start)));
  }

  @Override
  public Stream MAXS(final int maxStack, final int maxLocals) {
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
