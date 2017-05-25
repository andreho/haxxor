package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.cgen.InstructionFactory;
import net.andreho.haxxor.cgen.LocalVariable;
import net.andreho.haxxor.cgen.TryCatch;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.HxCode;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class InstructionCodeStream implements CodeStream {
   private final HxCode code;
   private final InstructionFactory factory;

   //----------------------------------------------------------------------------------------------------------------

   public InstructionCodeStream(final HxCode code) {
      this(code, code.getInstructionFactory());
   }

   public InstructionCodeStream(final HxCode code, final InstructionFactory factory) {
      this.code = code;
      this.factory = factory;
   }

   //----------------------------------------------------------------------------------------------------------------

   private Instruction current() {
      return code.getCurrent();
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public CodeStream BEGIN() {
      //NO OP
      return this;
   }

   @Override
   public CodeStream NOP() {
      current().append(factory.NOP());
      return this;
   }

   @Override
   public CodeStream ACONST_NULL() {
      current().append(factory.ACONST_NULL());
      return this;
   }

   @Override
   public CodeStream ICONST_M1() {
      current().append(factory.ICONST(-1));
      return this;
   }

   @Override
   public CodeStream ICONST_0() {
      current().append(factory.ICONST(0));
      return this;
   }

   @Override
   public CodeStream ICONST_1() {
      current().append(factory.ICONST(1));
      return this;
   }

   @Override
   public CodeStream ICONST_2() {
      current().append(factory.ICONST(2));
      return this;
   }

   @Override
   public CodeStream ICONST_3() {
      current().append(factory.ICONST(3));
      return this;
   }

   @Override
   public CodeStream ICONST_4() {
      current().append(factory.ICONST(4));
      return this;
   }

   @Override
   public CodeStream ICONST_5() {
      current().append(factory.ICONST(5));
      return this;
   }

   @Override
   public CodeStream LCONST_0() {
      current().append(factory.LCONST(0L));
      return this;
   }

   @Override
   public CodeStream LCONST_1() {
      current().append(factory.LCONST(1L));
      return this;
   }

   @Override
   public CodeStream FCONST_0() {
      current().append(factory.FCONST(0f));
      return this;
   }

   @Override
   public CodeStream FCONST_1() {
      current().append(factory.FCONST(1f));
      return this;
   }

   @Override
   public CodeStream FCONST_2() {
      current().append(factory.FCONST(2f));
      return this;
   }

   @Override
   public CodeStream DCONST_0() {
      current().append(factory.DCONST(0d));
      return this;
   }

   @Override
   public CodeStream DCONST_1() {
      current().append(factory.DCONST(1d));
      return this;
   }

   @Override
   public CodeStream BIPUSH(final byte value) {
      current().append(factory.BIPUSH(value));
      return this;
   }

   @Override
   public CodeStream SIPUSH(final short value) {
      current().append(factory.SIPUSH(value));
      return this;
   }

   @Override
   public CodeStream LDC(final int value) {
      current().append(factory.LDC(value));
      return this;
   }

   @Override
   public CodeStream LDC(final float value) {
      current().append(factory.LDC(value));
      return this;
   }

   @Override
   public CodeStream LDC(final long value) {
      current().append(factory.LDC(value));
      return this;
   }

   @Override
   public CodeStream LDC(final double value) {
      current().append(factory.LDC(value));
      return this;
   }

   @Override
   public CodeStream LDC(final String value) {
      current().append(factory.LDC(value));
      return this;
   }

   @Override
   public CodeStream HANDLE(final Handle handle) {
      current().append(factory.HANDLE(handle));
      return this;
   }

   @Override
   public CodeStream METHOD(final String methodDescriptor) {
      current().append(factory.METHOD(methodDescriptor));
      return this;
   }

   @Override
   public CodeStream TYPE(final String internalType) {
      current().append(factory.TYPE(internalType));
      return this;
   }

   @Override
   public CodeStream ILOAD(final int idx) {
      current().append(factory.ILOAD(idx));
      return this;
   }

   @Override
   public CodeStream LLOAD(final int idx) {
      current().append(factory.LLOAD(idx));
      return this;
   }

   @Override
   public CodeStream FLOAD(final int idx) {
      current().append(factory.FLOAD(idx));
      return this;
   }

   @Override
   public CodeStream DLOAD(final int idx) {
      current().append(factory.DLOAD(idx));
      return this;
   }

   @Override
   public CodeStream ALOAD(final int idx) {
      current().append(factory.ALOAD(idx));
      return this;
   }

   @Override
   public CodeStream THIS() {
      current().append(factory.ALOAD(0));
      return this;
   }

   @Override
   public CodeStream IALOAD() {
      current().append(factory.IALOAD());
      return this;
   }

   @Override
   public CodeStream LALOAD() {
      current().append(factory.LALOAD());
      return this;
   }

   @Override
   public CodeStream FALOAD() {
      current().append(factory.FALOAD());
      return this;
   }

   @Override
   public CodeStream DALOAD() {
      current().append(factory.DALOAD());
      return this;
   }

   @Override
   public CodeStream AALOAD() {
      current().append(factory.AALOAD());
      return this;
   }

   @Override
   public CodeStream BALOAD() {
      current().append(factory.BALOAD());
      return this;
   }

   @Override
   public CodeStream CALOAD() {
      current().append(factory.CALOAD());
      return this;
   }

   @Override
   public CodeStream SALOAD() {
      current().append(factory.SALOAD());
      return this;
   }

   @Override
   public CodeStream ISTORE(final int idx) {
      current().append(factory.ISTORE(idx));
      return this;
   }

   @Override
   public CodeStream LSTORE(final int idx) {
      current().append(factory.LSTORE(idx));
      return this;
   }

   @Override
   public CodeStream FSTORE(final int idx) {
      current().append(factory.FSTORE(idx));
      return this;
   }

   @Override
   public CodeStream DSTORE(final int idx) {
      current().append(factory.DSTORE(idx));
      return this;
   }

   @Override
   public CodeStream ASTORE(final int idx) {
      current().append(factory.ASTORE(idx));
      return this;
   }

   @Override
   public CodeStream IASTORE() {
      current().append(factory.IASTORE());
      return this;
   }

   @Override
   public CodeStream LASTORE() {
      current().append(factory.LASTORE());
      return this;
   }

   @Override
   public CodeStream FASTORE() {
      current().append(factory.FASTORE());
      return this;
   }

   @Override
   public CodeStream DASTORE() {
      current().append(factory.DASTORE());
      return this;
   }

   @Override
   public CodeStream AASTORE() {
      current().append(factory.AASTORE());
      return this;
   }

   @Override
   public CodeStream BASTORE() {
      current().append(factory.BASTORE());
      return this;
   }

   @Override
   public CodeStream CASTORE() {
      current().append(factory.CASTORE());
      return this;
   }

   @Override
   public CodeStream SASTORE() {
      current().append(factory.SASTORE());
      return this;
   }

   @Override
   public CodeStream POP() {
      current().append(factory.POP());
      return this;
   }

   @Override
   public CodeStream POP2() {
      current().append(factory.POP2());
      return this;
   }

   @Override
   public CodeStream DUP() {
      current().append(factory.DUP());
      return this;
   }

   @Override
   public CodeStream DUP_X1() {
      current().append(factory.DUP_X1());
      return this;
   }

   @Override
   public CodeStream DUP_X2() {
      current().append(factory.DUP_X2());
      return this;
   }

   @Override
   public CodeStream DUP2() {
      current().append(factory.DUP2());
      return this;
   }

   @Override
   public CodeStream DUP2_X1() {
      current().append(factory.DUP2_X1());
      return this;
   }

   @Override
   public CodeStream DUP2_X2() {
      current().append(factory.DUP2_X2());
      return this;
   }

   @Override
   public CodeStream SWAP() {
      current().append(factory.SWAP());
      return this;
   }

   @Override
   public CodeStream IADD() {
      current().append(factory.IADD());
      return this;
   }

   @Override
   public CodeStream LADD() {
      current().append(factory.LADD());
      return this;
   }

   @Override
   public CodeStream FADD() {
      current().append(factory.FADD());
      return this;
   }

   @Override
   public CodeStream DADD() {
      current().append(factory.DADD());
      return this;
   }

   @Override
   public CodeStream ISUB() {
      current().append(factory.ISUB());
      return this;
   }

   @Override
   public CodeStream LSUB() {
      current().append(factory.LSUB());
      return this;
   }

   @Override
   public CodeStream FSUB() {
      current().append(factory.FSUB());
      return this;
   }

   @Override
   public CodeStream DSUB() {
      current().append(factory.DSUB());
      return this;
   }

   @Override
   public CodeStream IMUL() {
      current().append(factory.IMUL());
      return this;
   }

   @Override
   public CodeStream LMUL() {
      current().append(factory.LMUL());
      return this;
   }

   @Override
   public CodeStream FMUL() {
      current().append(factory.FMUL());
      return this;
   }

   @Override
   public CodeStream DMUL() {
      current().append(factory.DMUL());
      return this;
   }

   @Override
   public CodeStream IDIV() {
      current().append(factory.IDIV());
      return this;
   }

   @Override
   public CodeStream LDIV() {
      current().append(factory.LDIV());
      return this;
   }

   @Override
   public CodeStream FDIV() {
      current().append(factory.FDIV());
      return this;
   }

   @Override
   public CodeStream DDIV() {
      current().append(factory.DDIV());
      return this;
   }

   @Override
   public CodeStream IREM() {
      current().append(factory.IREM());
      return this;
   }

   @Override
   public CodeStream LREM() {
      current().append(factory.LREM());
      return this;
   }

   @Override
   public CodeStream FREM() {
      current().append(factory.FREM());
      return this;
   }

   @Override
   public CodeStream DREM() {
      current().append(factory.DREM());
      return this;
   }

   @Override
   public CodeStream INEG() {
      current().append(factory.INEG());
      return this;
   }

   @Override
   public CodeStream LNEG() {
      current().append(factory.LNEG());
      return this;
   }

   @Override
   public CodeStream FNEG() {
      current().append(factory.FNEG());
      return this;
   }

   @Override
   public CodeStream DNEG() {
      current().append(factory.DNEG());
      return this;
   }

   @Override
   public CodeStream ISHL() {
      current().append(factory.ISHL());
      return this;
   }

   @Override
   public CodeStream LSHL() {
      current().append(factory.LSHL());
      return this;
   }

   @Override
   public CodeStream ISHR() {
      current().append(factory.ISHR());
      return this;
   }

   @Override
   public CodeStream LSHR() {
      current().append(factory.LSHR());
      return this;
   }

   @Override
   public CodeStream IUSHR() {
      current().append(factory.IUSHR());
      return this;
   }

   @Override
   public CodeStream LUSHR() {
      current().append(factory.LUSHR());
      return this;
   }

   @Override
   public CodeStream IAND() {
      current().append(factory.IADD());
      return this;
   }

   @Override
   public CodeStream LAND() {
      current().append(factory.LADD());
      return this;
   }

   @Override
   public CodeStream IOR() {
      current().append(factory.IOR());
      return this;
   }

   @Override
   public CodeStream LOR() {
      current().append(factory.LOR());
      return this;
   }

   @Override
   public CodeStream IXOR() {
      current().append(factory.IXOR());
      return this;
   }

   @Override
   public CodeStream LXOR() {
      current().append(factory.LXOR());
      return this;
   }

   @Override
   public CodeStream IINC(final int var, final int increment) {
      current().append(factory.IINC(var, increment));
      return this;
   }

   @Override
   public CodeStream I2L() {
      current().append(factory.I2L());
      return this;
   }

   @Override
   public CodeStream I2F() {
      current().append(factory.I2F());
      return this;
   }

   @Override
   public CodeStream I2D() {
      current().append(factory.I2D());
      return this;
   }

   @Override
   public CodeStream L2I() {
      current().append(factory.L2I());
      return this;
   }

   @Override
   public CodeStream L2F() {
      current().append(factory.L2F());
      return this;
   }

   @Override
   public CodeStream L2D() {
      current().append(factory.L2D());
      return this;
   }

   @Override
   public CodeStream F2I() {
      current().append(factory.F2I());
      return this;
   }

   @Override
   public CodeStream F2L() {
      current().append(factory.F2L());
      return this;
   }

   @Override
   public CodeStream F2D() {
      current().append(factory.F2D());
      return this;
   }

   @Override
   public CodeStream D2I() {
      current().append(factory.D2I());
      return this;
   }

   @Override
   public CodeStream D2L() {
      current().append(factory.D2L());
      return this;
   }

   @Override
   public CodeStream D2F() {
      current().append(factory.D2F());
      return this;
   }

   @Override
   public CodeStream I2B() {
      current().append(factory.I2B());
      return this;
   }

   @Override
   public CodeStream I2C() {
      current().append(factory.I2C());
      return this;
   }

   @Override
   public CodeStream I2S() {
      current().append(factory.I2S());
      return this;
   }

   @Override
   public CodeStream LCMP() {
      current().append(factory.LCMP());
      return this;
   }

   @Override
   public CodeStream FCMPL() {
      current().append(factory.FCMPL());
      return this;
   }

   @Override
   public CodeStream FCMPG() {
      current().append(factory.FCMPG());
      return this;
   }

   @Override
   public CodeStream DCMPL() {
      current().append(factory.DCMPL());
      return this;
   }

   @Override
   public CodeStream DCMPG() {
      current().append(factory.DCMPG());
      return this;
   }

   @Override
   public CodeStream IFEQ(final LABEL label) {
      current().append(factory.IFEQ(label));
      return this;
   }

   @Override
   public CodeStream IFNE(final LABEL label) {
      current().append(factory.IFNE(label));
      return this;
   }

   @Override
   public CodeStream IFLT(final LABEL label) {
      current().append(factory.IFLT(label));
      return this;
   }

   @Override
   public CodeStream IFGE(final LABEL label) {
      current().append(factory.IFGE(label));
      return this;
   }

   @Override
   public CodeStream IFGT(final LABEL label) {
      current().append(factory.IFGT(label));
      return this;
   }

   @Override
   public CodeStream IFLE(final LABEL label) {
      current().append(factory.IFLE(label));
      return this;
   }

   @Override
   public CodeStream IF_ICMPEQ(final LABEL label) {
      current().append(factory.IF_ICMPEQ(label));
      return this;
   }

   @Override
   public CodeStream IF_ICMPNE(final LABEL label) {
      current().append(factory.IF_ICMPNE(label));
      return this;
   }

   @Override
   public CodeStream IF_ICMPLT(final LABEL label) {
      current().append(factory.IF_ICMPLT(label));
      return this;
   }

   @Override
   public CodeStream IF_ICMPGE(final LABEL label) {
      current().append(factory.IF_ICMPGE(label));
      return this;
   }

   @Override
   public CodeStream IF_ICMPGT(final LABEL label) {
      current().append(factory.IF_ICMPGT(label));
      return this;
   }

   @Override
   public CodeStream IF_ICMPLE(final LABEL label) {
      current().append(factory.IF_ICMPLE(label));
      return this;
   }

   @Override
   public CodeStream IF_ACMPEQ(final LABEL label) {
      current().append(factory.IF_ACMPEQ(label));
      return this;
   }

   @Override
   public CodeStream IF_ACMPNE(final LABEL label) {
      current().append(factory.IF_ACMPNE(label));
      return this;
   }

   @Override
   public CodeStream GOTO(final LABEL label) {
      current().append(factory.GOTO(label));
      return this;
   }

   @Override
   public CodeStream JSR(final LABEL label) {
      current().append(factory.JSR(label));
      return this;
   }

   @Override
   public CodeStream RET(final int var) {
      current().append(factory.RET(var));
      return this;
   }

   @Override
   public CodeStream TABLESWITCH(final int min, final int max, final LABEL defaultLabel, final LABEL... labels) {
      current().append(factory.TABLESWITCH(min, max, defaultLabel, labels));
      return this;
   }

   @Override
   public CodeStream LOOKUPSWITCH(final LABEL label, final int[] keys, final LABEL[] labels) {
      current().append(factory.LOOKUPSWITCH(label, keys, labels));
      return this;
   }

   @Override
   public CodeStream IRETURN() {
      current().append(factory.IRETURN());
      return this;
   }

   @Override
   public CodeStream LRETURN() {
      current().append(factory.LRETURN());
      return this;
   }

   @Override
   public CodeStream FRETURN() {
      current().append(factory.FRETURN());
      return this;
   }

   @Override
   public CodeStream DRETURN() {
      current().append(factory.DRETURN());
      return this;
   }

   @Override
   public CodeStream ARETURN() {
      current().append(factory.ARETURN());
      return this;
   }

   @Override
   public CodeStream RETURN() {
      current().append(factory.RETURN());
      return this;
   }

   @Override
   public CodeStream GETSTATIC(final String owner, final String name, final String desc) {
      current().append(factory.GETSTATIC(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream PUTSTATIC(final String owner, final String name, final String desc) {
      current().append(factory.PUTSTATIC(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream GETFIELD(final String owner, final String name, final String desc) {
      current().append(factory.GETFIELD(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream PUTFIELD(final String owner, final String name, final String desc) {
      current().append(factory.PUTFIELD(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream INVOKEVIRTUAL(final String owner, final String name, final String desc) {
      current().append(factory.INVOKEVIRTUAL(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream INVOKESPECIAL(final String owner, final String name, final String desc) {
      current().append(factory.INVOKESPECIAL(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream INVOKESTATIC(final String owner, final String name, final String desc, final boolean isInterface) {
      current().append(factory.INVOKESTATIC(owner, name, desc, isInterface));
      return this;
   }

   @Override
   public CodeStream INVOKEINTERFACE(final String owner, final String name, final String desc) {
      current().append(factory.INVOKEINTERFACE(owner, name, desc));
      return this;
   }

   @Override
   public CodeStream INVOKEDYNAMIC(final String name, final String desc, final Handle bsm, final Object... bsmArgs) {
      current().append(factory.INVOKEDYNAMIC(name, desc, bsm, bsmArgs));
      return this;
   }

   @Override
   public CodeStream NEW(final String internalType) {
      current().append(factory.NEW(internalType));
      return this;
   }

   @Override
   public CodeStream NEWARRAY(final ArrayType type) {
      current().append(factory.NEWARRAY(type));
      return this;
   }

   @Override
   public CodeStream ANEWARRAY(final String internalType) {
      current().append(factory.ANEWARRAY(internalType));
      return this;
   }

   @Override
   public CodeStream ARRAYLENGTH() {
      current().append(factory.ARRAYLENGTH());
      return this;
   }

   @Override
   public CodeStream ATHROW() {
      current().append(factory.ATHROW());
      return this;
   }

   @Override
   public CodeStream CHECKCAST(final String internalType) {
      current().append(factory.CHECKCAST(internalType));
      return this;
   }

   @Override
   public CodeStream INSTANCEOF(final String internalType) {
      current().append(factory.INSTANCEOF(internalType));
      return this;
   }

   @Override
   public CodeStream MONITORENTER() {
      current().append(factory.MONITORENTER());
      return this;
   }

   @Override
   public CodeStream MONITOREXIT() {
      current().append(factory.MONITOREXIT());
      return this;
   }

   @Override
   public CodeStream MULTIANEWARRAY(final String internalType, final int dims) {
      current().append(factory.MULTIANEWARRAY(internalType, dims));
      return this;
   }

   @Override
   public CodeStream IFNULL(final LABEL label) {
      current().append(factory.IFNULL(label));
      return this;
   }

   @Override
   public CodeStream IFNONNULL(final LABEL label) {
      current().append(factory.IFNONNULL(label));
      return this;
   }

   @Override
   public CodeStream LABEL(final LABEL label) {
      current().append(factory.LABEL(label));
      return this;
   }

   @Override
   public CodeStream TRY_CATCH(final LABEL startLabel, final LABEL endLabel, final LABEL handler, final String type) {
      this.code.getTryCatches().add(new TryCatch(startLabel, endLabel, handler, type));
      return this;
   }

   @Override
   public CodeStream FRAME(final Frames type, final int nLocal, final Object[] local, final int nStack,
                           final Object[] stack) {
      current().append(factory.FRAME(type, nLocal, local, nStack, stack));
      return this;
   }

   @Override
   public CodeStream LOCAL_VARIABLE(final String name, final String desc, final String signature, final LABEL start,
                                    final LABEL end, final int index) {
      this.code.getLocalVariables().add(new LocalVariable(index, name, desc, signature, start, end));
      return this;
   }

   @Override
   public CodeStream LINE_NUMBER(final int line, final LABEL start) {
      current().append(factory.LINE_NUMBER(line, start));
      return this;
   }

   @Override
   public CodeStream MAXS(final int maxStack, final int maxLocals) {
      this.code.setMaxStack(maxStack);
      this.code.setMaxLocals(maxLocals);
      return this;
   }

   @Override
   public void END() {
      //NO OP
   }
}
