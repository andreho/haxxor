package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.ArrayType;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Frames;
import net.andreho.haxxor.cgen.instr.LABEL;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 07:09.
 */
public class PrintingCodeStream extends DelegatingCodeStream {
  private final Appendable output;
  private final StringBuilder labels;
  private final StringBuilder code;
  private final Map<LABEL, String> labelMap;

  public PrintingCodeStream(final CodeStream codeStream,
                            final Appendable output) {
    super(codeStream);
    this.output = Objects.requireNonNull(output, "Printing output can't be null.");
    this.code = new StringBuilder();
    this.labels = new StringBuilder();
    this.labelMap = new IdentityHashMap<>(16);
  }

  protected String newLabelAlias() {
    return "L"+labelMap.size();
  }

  private String createLabelAlias(final LABEL label) {
    String alias = labelMap.get(label);
    if(alias == null) {
      labelMap.put(label, alias = newLabelAlias());
      labels.append("LABEL ").append(alias).append(";\n");
    }
    return alias;
  }

  private PrintingCodeStream append(char c) {
    code.append(c);
    return this;
  }

  private PrintingCodeStream append(String s) {
    code.append(s);
    return this;
  }

  @Override
  public CodeStream BEGIN() {
    super.BEGIN();
    append(".BEGIN()\n");
    return this;
  }

  @Override
  public CodeStream NOP() {
    super.NOP();
    append(".NOP()\n");
    return this;
  }

  @Override
  public CodeStream ACONST_NULL() {
    super.ACONST_NULL();
    append(".ACONST_NULL()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_M1() {
    super.ICONST_M1();
    append(".ICONST_M1()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_0() {
    super.ICONST_0();
    append(".ICONST_0()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_1() {
    super.ICONST_1();
    append(".ICONST_1()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_2() {
    super.ICONST_2();
    append(".ICONST_2()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_3() {
    super.ICONST_3();
    append(".ICONST_3()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_4() {
    super.ICONST_4();
    append(".ICONST_4()\n");
    return this;
  }

  @Override
  public CodeStream ICONST_5() {
    super.ICONST_5();
    append(".ICONST_5()\n");
    return this;
  }

  @Override
  public CodeStream LCONST_0() {
    super.LCONST_0();
    append(".LCONST_0()\n");
    return this;
  }

  @Override
  public CodeStream LCONST_1() {
    super.LCONST_1();
    append(".LCONST_1()\n");
    return this;
  }

  @Override
  public CodeStream FCONST_0() {
    super.FCONST_0();
    append(".FCONST_0()\n");
    return this;
  }

  @Override
  public CodeStream FCONST_1() {
    super.FCONST_1();
    append(".FCONST_1()\n");
    return this;
  }

  @Override
  public CodeStream FCONST_2() {
    super.FCONST_2();
    append(".FCONST_2()\n");
    return this;
  }

  @Override
  public CodeStream DCONST_0() {
    super.DCONST_0();
    append(".DCONST_0()\n");
    return this;
  }

  @Override
  public CodeStream DCONST_1() {
    super.DCONST_1();
    append(".DCONST_1()\n");
    return this;
  }

  @Override
  public CodeStream BIPUSH(final byte value) {
    super.BIPUSH(value);
    append(".BIPUSH((byte) ").append(Byte.toString(value)).append(")\n");
    return this;
  }

  @Override
  public CodeStream SIPUSH(final short value) {
    super.SIPUSH(value);
    append(".SIPUSH((short) ").append(Short.toString(value)).append(")\n");
    return this;
  }

  @Override
  public CodeStream LDC(final int value) {
    super.LDC(value);
    append(".LDC(").append(Integer.toString(value)).append(")\n");
    return this;
  }

  @Override
  public CodeStream LDC(final float value) {
    super.LDC(value);
    append(".LDC(").append(Float.toString(value)).append("f)\n");
    return this;
  }

  @Override
  public CodeStream LDC(final long value) {
    super.LDC(value);
    append(".LDC(").append(Long.toString(value)).append("L)\n");
    return this;
  }

  @Override
  public CodeStream LDC(final double value) {
    super.LDC(value);
    append(".LDC(").append(Double.toString(value)).append("d)\n");
    return this;
  }

  @Override
  public CodeStream LDC(final String value) {
    super.LDC(value);
    append(".LDC(\"").append(value).append("\")\n");
    return this;
  }

  private String toHandleTag(int tag) {
    switch (tag) {
      case Opcodes.H_GETFIELD: return "Opcodes.H_GETFIELD";
      case Opcodes.H_GETSTATIC: return "Opcodes.H_GETSTATIC";
      case Opcodes.H_PUTFIELD: return "Opcodes.H_PUTFIELD";
      case Opcodes.H_PUTSTATIC: return "Opcodes.H_PUTSTATIC";
      case Opcodes.H_INVOKEVIRTUAL: return "Opcodes.H_INVOKEVIRTUAL";
      case Opcodes.H_INVOKESTATIC: return "Opcodes.H_INVOKESTATIC";
      case Opcodes.H_INVOKESPECIAL: return "Opcodes.H_INVOKESPECIAL";
      case Opcodes.H_NEWINVOKESPECIAL: return "Opcodes.H_NEWINVOKESPECIAL";
      case Opcodes.H_INVOKEINTERFACE: return "Opcodes.H_INVOKEINTERFACE";
      default:
        throw new IllegalStateException("Unknown tag: "+tag);
    }
  }

  @Override
  public CodeStream HANDLE(final Handle handle) {
    super.HANDLE(handle);
    append(".HANDLE(new Handle(")
        .append("\"").append(toHandleTag(handle.getTag())).append("\",")
        .append("\"").append(handle.getOwner()).append("\",")
        .append("\"").append(handle.getName()).append("\",")
        .append("\"").append(handle.getDesc()).append("\",")
        .append(handle.isInterface()? "true" : "false").append("))\n");
    return this;
  }

  @Override
  public CodeStream METHOD(final String methodDescriptor) {
    super.METHOD(methodDescriptor);
    append(".METHOD(").append("\"").append(methodDescriptor).append("\")\n");
    return this;
  }

  @Override
  public CodeStream TYPE(final String internalType) {
    super.TYPE(internalType);
    append(".TYPE(").append("\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public CodeStream ILOAD(final int idx) {
    super.ILOAD(idx);
    append(".ILOAD(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream LLOAD(final int idx) {
    super.LLOAD(idx);
    append(".LLOAD(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream FLOAD(final int idx) {
    super.FLOAD(idx);
    append(".FLOAD(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream DLOAD(final int idx) {
    super.DLOAD(idx);
    append(".DLOAD(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream ALOAD(final int idx) {
    super.ALOAD(idx);
    append(".ALOAD(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream THIS() {
    super.THIS();
    append(".THIS()\n");
    return this;
  }

  @Override
  public CodeStream IALOAD() {
    super.IALOAD();
    append(".IALOAD()\n");
    return this;
  }

  @Override
  public CodeStream LALOAD() {
    super.LALOAD();
    append(".LALOAD()\n");
    return this;
  }

  @Override
  public CodeStream FALOAD() {
    super.FALOAD();
    append(".FALOAD()\n");
    return this;
  }

  @Override
  public CodeStream DALOAD() {
    super.DALOAD();
    append(".DALOAD()\n");
    return this;
  }

  @Override
  public CodeStream AALOAD() {
    super.AALOAD();
    append(".AALOAD()\n");
    return this;
  }

  @Override
  public CodeStream BALOAD() {
    super.BALOAD();
    append(".BALOAD()\n");
    return this;
  }

  @Override
  public CodeStream CALOAD() {
    super.CALOAD();
    append(".CALOAD()\n");
    return this;
  }

  @Override
  public CodeStream SALOAD() {
    super.SALOAD();
    append(".SALOAD()\n");
    return this;
  }

  @Override
  public CodeStream ISTORE(final int idx) {
    super.ISTORE(idx);
    append(".ISTORE(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream LSTORE(final int idx) {
    super.LSTORE(idx);
    append(".LSTORE(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream FSTORE(final int idx) {
    super.FSTORE(idx);
    append(".FSTORE(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream DSTORE(final int idx) {
    super.DSTORE(idx);
    append(".DSTORE(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream ASTORE(final int idx) {
    super.ASTORE(idx);
    append(".ASTORE(").append(Integer.toString(idx)).append(")\n");
    return this;
  }

  @Override
  public CodeStream IASTORE() {
    super.IASTORE();
    append(".IASTORE()\n");
    return this;
  }

  @Override
  public CodeStream LASTORE() {
    super.LASTORE();
    append(".LASTORE()\n");
    return this;
  }

  @Override
  public CodeStream FASTORE() {
    super.FASTORE();
    append(".FASTORE()\n");
    return this;
  }

  @Override
  public CodeStream DASTORE() {
    super.DASTORE();
    append(".DASTORE()\n");
    return this;
  }

  @Override
  public CodeStream AASTORE() {
    super.AASTORE();
    append(".AASTORE()\n");
    return this;
  }

  @Override
  public CodeStream BASTORE() {
    super.BASTORE();
    append(".BASTORE()\n");
    return this;
  }

  @Override
  public CodeStream CASTORE() {
    super.CASTORE();
    append(".CASTORE()\n");
    return this;
  }

  @Override
  public CodeStream SASTORE() {
    super.SASTORE();
    append(".SASTORE()\n");
    return this;
  }

  @Override
  public CodeStream POP() {
    super.POP();
    append(".POP()\n");
    return this;
  }

  @Override
  public CodeStream POP2() {
    super.POP2();
    append(".POP2()\n");
    return this;
  }

  @Override
  public CodeStream DUP() {
    super.DUP();
    append(".DUP()\n");
    return this;
  }

  @Override
  public CodeStream DUP_X1() {
    super.DUP_X1();
    append(".DUP_X1()\n");
    return this;
  }

  @Override
  public CodeStream DUP_X2() {
    super.DUP_X2();
    append(".DUP_X2()\n");
    return this;
  }

  @Override
  public CodeStream DUP2() {
    super.DUP2();
    append(".DUP2()\n");
    return this;
  }

  @Override
  public CodeStream DUP2_X1() {
    super.DUP2_X1();
    append(".DUP2_X1()\n");
    return this;
  }

  @Override
  public CodeStream DUP2_X2() {
    super.DUP2_X2();
    append(".DUP2_X2()\n");
    return this;
  }

  @Override
  public CodeStream SWAP() {
    super.SWAP();
    append(".SWAP()\n");
    return this;
  }

  @Override
  public CodeStream IADD() {
    super.IADD();
    append(".IADD()\n");
    return this;
  }

  @Override
  public CodeStream LADD() {
    super.LADD();
    append(".LADD()\n");
    return this;
  }

  @Override
  public CodeStream FADD() {
    super.FADD();
    append(".FADD()\n");
    return this;
  }

  @Override
  public CodeStream DADD() {
    super.DADD();
    append(".DADD()\n");
    return this;
  }

  @Override
  public CodeStream ISUB() {
    super.ISUB();
    append(".ISUB()\n");
    return this;
  }

  @Override
  public CodeStream LSUB() {
    super.LSUB();
    append(".LSUB()\n");
    return this;
  }

  @Override
  public CodeStream FSUB() {
    super.FSUB();
    append(".FSUB()\n");
    return this;
  }

  @Override
  public CodeStream DSUB() {
    super.DSUB();
    append(".DSUB()\n");
    return this;
  }

  @Override
  public CodeStream IMUL() {
    super.IMUL();
    append(".IMUL()\n");
    return this;
  }

  @Override
  public CodeStream LMUL() {
    super.LMUL();
    append(".LMUL()\n");
    return this;
  }

  @Override
  public CodeStream FMUL() {
    super.FMUL();
    append(".FMUL()\n");
    return this;
  }

  @Override
  public CodeStream DMUL() {
    super.DMUL();
    append(".DMUL()\n");
    return this;
  }

  @Override
  public CodeStream IDIV() {
    super.IDIV();
    append(".IDIV()\n");
    return this;
  }

  @Override
  public CodeStream LDIV() {
    super.LDIV();
    append(".LDIV()\n");
    return this;
  }

  @Override
  public CodeStream FDIV() {
    super.FDIV();
    append(".FDIV()\n");
    return this;
  }

  @Override
  public CodeStream DDIV() {
    super.DDIV();
    append(".DDIV()\n");
    return this;
  }

  @Override
  public CodeStream IREM() {
    super.IREM();
    append(".IREM()\n");
    return this;
  }

  @Override
  public CodeStream LREM() {
    super.LREM();
    append(".LREM()\n");
    return this;
  }

  @Override
  public CodeStream FREM() {
    super.FREM();
    append(".FREM()\n");
    return this;
  }

  @Override
  public CodeStream DREM() {
    super.DREM();
    append(".DREM()\n");
    return this;
  }

  @Override
  public CodeStream INEG() {
    super.INEG();
    append(".INEG()\n");
    return this;
  }

  @Override
  public CodeStream LNEG() {
    super.LNEG();
    append(".LNEG()\n");
    return this;
  }

  @Override
  public CodeStream FNEG() {
    super.FNEG();
    append(".FNEG()\n");
    return this;
  }

  @Override
  public CodeStream DNEG() {
    super.DNEG();
    append(".DNEG()\n");
    return this;
  }

  @Override
  public CodeStream ISHL() {
    super.ISHL();
    append(".ISHL()\n");
    return this;
  }

  @Override
  public CodeStream LSHL() {
    super.LSHL();
    append(".LSHL()\n");
    return this;
  }

  @Override
  public CodeStream ISHR() {
    super.ISHR();
    append(".ISHR()\n");
    return this;
  }

  @Override
  public CodeStream LSHR() {
    super.LSHR();
    append(".LSHR()\n");
    return this;
  }

  @Override
  public CodeStream IUSHR() {
    super.IUSHR();
    append(".IUSHR()\n");
    return this;
  }

  @Override
  public CodeStream LUSHR() {
    super.LUSHR();
    append(".LUSHR()\n");
    return this;
  }

  @Override
  public CodeStream IAND() {
    super.IAND();
    append(".IAND()\n");
    return this;
  }

  @Override
  public CodeStream LAND() {
    super.LAND();
    append(".LAND()\n");
    return this;
  }

  @Override
  public CodeStream IOR() {
    super.IOR();
    append(".IOR()\n");
    return this;
  }

  @Override
  public CodeStream LOR() {
    super.LOR();
    append(".LOR()\n");
    return this;
  }

  @Override
  public CodeStream IXOR() {
    super.IXOR();
    append(".IXOR()\n");
    return this;
  }

  @Override
  public CodeStream LXOR() {
    super.LXOR();
    append(".LXOR()\n");
    return this;
  }

  @Override
  public CodeStream IINC(final int var,
                         final int increment) {
    super.IINC(var, increment);
    append(".IINC(").append(Integer.toString(var)).append(',').append(Integer.toString(increment)).append(")\n");
    return this;
  }

  @Override
  public CodeStream I2L() {
    super.I2L();
    append(".I2L()\n");
    return this;
  }

  @Override
  public CodeStream I2F() {
    super.I2F();
    append(".I2F()\n");
    return this;
  }

  @Override
  public CodeStream I2D() {
    super.I2D();
    append(".I2D()\n");
    return this;
  }

  @Override
  public CodeStream L2I() {
    super.L2I();
    append(".L2I()\n");
    return this;
  }

  @Override
  public CodeStream L2F() {
    super.L2F();
    append(".L2F()\n");
    return this;
  }

  @Override
  public CodeStream L2D() {
    super.L2D();
    append(".L2D()\n");
    return this;
  }

  @Override
  public CodeStream F2I() {
    super.F2I();
    append(".F2I()\n");
    return this;
  }

  @Override
  public CodeStream F2L() {
    super.F2L();
    append(".F2L()\n");
    return this;
  }

  @Override
  public CodeStream F2D() {
    super.F2D();
    append(".F2D()\n");
    return this;
  }

  @Override
  public CodeStream D2I() {
    super.D2I();
    append(".D2I()\n");
    return this;
  }

  @Override
  public CodeStream D2L() {
    super.D2L();
    append(".D2L()\n");
    return this;
  }

  @Override
  public CodeStream D2F() {
    super.D2F();
    append(".D2F()\n");
    return this;
  }

  @Override
  public CodeStream I2B() {
    super.I2B();
    append(".I2B()\n");
    return this;
  }

  @Override
  public CodeStream I2C() {
    super.I2C();
    append(".I2C()\n");
    return this;
  }

  @Override
  public CodeStream I2S() {
    super.I2S();
    append(".I2S()\n");
    return this;
  }

  @Override
  public CodeStream LCMP() {
    super.LCMP();
    append(".LCMP()\n");
    return this;
  }

  @Override
  public CodeStream FCMPL() {
    super.FCMPL();
    append(".FCMPL()\n");
    return this;
  }

  @Override
  public CodeStream FCMPG() {
    super.FCMPG();
    append(".FCMPG()\n");
    return this;
  }

  @Override
  public CodeStream DCMPL() {
    super.DCMPL();
    append(".DCMPL()\n");
    return this;
  }

  @Override
  public CodeStream DCMPG() {
    super.DCMPG();
    append(".DCMPG()\n");
    return this;
  }

  @Override
  public CodeStream IFEQ(final LABEL label) {
    super.IFEQ(label);
    append(".IFEQ()\n");
    return this;
  }

  @Override
  public CodeStream IFNE(final LABEL label) {
    super.IFNE(label);
    append(".IFNE()\n");
    return this;
  }

  @Override
  public CodeStream IFLT(final LABEL label) {
    super.IFLT(label);
    append(".IFLT()\n");
    return this;
  }

  @Override
  public CodeStream IFGE(final LABEL label) {
    super.IFGE(label);
    append(".IFGE()");
    return this;
  }

  @Override
  public CodeStream IFGT(final LABEL label) {
    super.IFGT(label);
    append(".IFGT()");
    return this;
  }

  @Override
  public CodeStream IFLE(final LABEL label) {
    super.IFLE(label);
    append(".IFLE()");
    return this;
  }

  @Override
  public CodeStream IF_ICMPEQ(final LABEL label) {
    super.IF_ICMPEQ(label);
    append(".IF_ICMPEQ()");
    return this;
  }

  @Override
  public CodeStream IF_ICMPNE(final LABEL label) {
    super.IF_ICMPNE(label);
    append(".IF_ICMPNE()");
    return this;
  }

  @Override
  public CodeStream IF_ICMPLT(final LABEL label) {
    super.IF_ICMPLT(label);
    append(".IF_ICMPLT()");
    return this;
  }

  @Override
  public CodeStream IF_ICMPGE(final LABEL label) {
    super.IF_ICMPGE(label);
    append(".IF_ICMPGE()");
    return this;
  }

  @Override
  public CodeStream IF_ICMPGT(final LABEL label) {
    super.IF_ICMPGT(label);
    append(".IF_ICMPGT()");
    return this;
  }

  @Override
  public CodeStream IF_ICMPLE(final LABEL label) {
    super.IF_ICMPLE(label);
    append(".IF_ICMPLE()");
    return this;
  }

  @Override
  public CodeStream IF_ACMPEQ(final LABEL label) {
    super.IF_ACMPEQ(label);
    append(".IF_ACMPEQ()");
    return this;
  }

  @Override
  public CodeStream IF_ACMPNE(final LABEL label) {
    super.IF_ACMPNE(label);
    append(".IF_ACMPNE()");
    return this;
  }

  @Override
  public CodeStream GOTO(final LABEL label) {
    super.GOTO(label);
    append(".GOTO()");
    return this;
  }

  @Override
  public CodeStream JSR(final LABEL label) {
    super.JSR(label);
    append(".JSR()");
    return this;
  }

  @Override
  public CodeStream RET(final int var) {
    super.RET(var);
    append(".RET()");
    return this;
  }

  @Override
  public CodeStream TABLESWITCH(final int min,
                                final int max,
                                final LABEL defaultLabel,
                                final LABEL... labels) {
    super.TABLESWITCH(min, max, defaultLabel, labels);
    append(".TABLESWITCH()");
    return this;
  }

  @Override
  public CodeStream LOOKUPSWITCH(final LABEL defaultLabel,
                                 final int[] keys,
                                 final LABEL[] labels) {
    super.LOOKUPSWITCH(defaultLabel, keys, labels);
    append(".LOOKUPSWITCH()");
    return this;
  }

  @Override
  public CodeStream IRETURN() {
    super.IRETURN();
    append(".IRETURN()\n");
    return this;
  }

  @Override
  public CodeStream LRETURN() {
    super.LRETURN();
    append(".LRETURN()\n");
    return this;
  }

  @Override
  public CodeStream FRETURN() {
    super.FRETURN();
    append(".FRETURN()\n");
    return this;
  }

  @Override
  public CodeStream DRETURN() {
    super.DRETURN();
    append(".DRETURN()\n");
    return this;
  }

  @Override
  public CodeStream ARETURN() {
    super.ARETURN();
    append(".ARETURN()\n");
    return this;
  }

  @Override
  public CodeStream RETURN() {
    super.RETURN();
    append(".RETURN()\n");
    return this;
  }

  @Override
  public CodeStream GETSTATIC(final String owner,
                              final String name,
                              final String desc) {
    super.GETSTATIC(owner, name, desc);
    append(".GETSTATIC()");
    return this;
  }

  @Override
  public CodeStream PUTSTATIC(final String owner,
                              final String name,
                              final String desc) {
    super.PUTSTATIC(owner, name, desc);
    append(".PUTSTATIC()");
    return this;
  }

  @Override
  public CodeStream GETFIELD(final String owner,
                             final String name,
                             final String desc) {
    super.GETFIELD(owner, name, desc);
    append(".GETFIELD()");
    return this;
  }

  @Override
  public CodeStream PUTFIELD(final String owner,
                             final String name,
                             final String desc) {
    super.PUTFIELD(owner, name, desc);
    append(".PUTFIELD()");
    return this;
  }

  @Override
  public CodeStream INVOKEVIRTUAL(final String owner,
                                  final String name,
                                  final String desc) {
    super.INVOKEVIRTUAL(owner, name, desc);
    append(".INVOKEVIRTUAL()");
    return this;
  }

  @Override
  public CodeStream INVOKESPECIAL(final String owner,
                                  final String name,
                                  final String desc) {
    super.INVOKESPECIAL(owner, name, desc);
    append(".INVOKESPECIAL()");
    return this;
  }

  @Override
  public CodeStream INVOKESTATIC(final String owner,
                                 final String name,
                                 final String desc,
                                 final boolean isInterface) {
    super.INVOKESTATIC(owner, name, desc, isInterface);
    append(".INVOKESTATIC()");
    return this;
  }

  @Override
  public CodeStream INVOKEINTERFACE(final String owner,
                                    final String name,
                                    final String desc) {
    super.INVOKEINTERFACE(owner, name, desc);
    append(".INVOKEINTERFACE()");
    return this;
  }

  @Override
  public CodeStream INVOKEDYNAMIC(final String name,
                                  final String desc,
                                  final Handle bsm,
                                  final Object... bsmArgs) {
    super.INVOKEDYNAMIC(name, desc, bsm, bsmArgs);
    append(".INVOKEDYNAMIC()");
    return this;
  }

  @Override
  public CodeStream NEW(final String internalType) {
    super.NEW(internalType);
    append(".NEW(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public CodeStream NEWARRAY(final ArrayType type) {
    super.NEWARRAY(type);
    append(".NEWARRAY(ArrayType.").append(type.name()).append(")\n");
    return this;
  }

  @Override
  public CodeStream ANEWARRAY(final String internalType) {
    super.ANEWARRAY(internalType);
    append(".ANEWARRAY(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public CodeStream ARRAYLENGTH() {
    super.ARRAYLENGTH();
    append(".ARRAYLENGTH()\n");
    return this;
  }

  @Override
  public CodeStream ATHROW() {
    super.ATHROW();
    append(".ATHROW()\n");
    return this;
  }

  @Override
  public CodeStream CHECKCAST(final String internalType) {
    super.CHECKCAST(internalType);
    append(".CHECKCAST(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public CodeStream INSTANCEOF(final String internalType) {
    super.INSTANCEOF(internalType);
    append(".INSTANCEOF(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public CodeStream MONITORENTER() {
    super.MONITORENTER();
    append(".MONITORENTER()\n");
    return this;
  }

  @Override
  public CodeStream MONITOREXIT() {
    super.MONITOREXIT();
    append(".MONITOREXIT()\n");
    return this;
  }

  @Override
  public CodeStream MULTIANEWARRAY(final String internalType,
                                   final int dims) {
    super.MULTIANEWARRAY(internalType, dims);
    append(".MULTIANEWARRAY(\"").append(internalType).append("\",").append(Integer.toString(dims)).append(")\n");
    return this;
  }

  @Override
  public CodeStream IFNULL(final LABEL label) {
    super.IFNULL(label);
    append(".IFNULL()");
    return this;
  }

  @Override
  public CodeStream IFNONNULL(final LABEL label) {
    super.IFNONNULL(label);
    append(".IFNONNULL()\n");
    return this;
  }



  @Override
  public CodeStream LABEL(final LABEL label) {
    super.LABEL(label);
    final String alias = createLabelAlias(label);
    append(".LABEL(").append(alias).append(" = new LABEL())\n");
    return this;
  }


  @Override
  public CodeStream TRY_CATCH(final LABEL startLabel,
                              final LABEL endLabel,
                              final LABEL handler,
                              final String type) {
    super.TRY_CATCH(startLabel, endLabel, handler, type);
    append(".TRY_CATCH()\n");
    return this;
  }

  @Override
  public CodeStream FRAME(final Frames type,
                          final int nLocal,
                          final Object[] local,
                          final int nStack,
                          final Object[] stack) {
    super.FRAME(type, nLocal, local, nStack, stack);
    append(".FRAME()\n");
    return this;
  }

  @Override
  public CodeStream LOCAL_VARIABLE(final String name,
                                   final String desc,
                                   final String signature,
                                   final LABEL start,
                                   final LABEL end,
                                   final int index) {
    super.LOCAL_VARIABLE(name, desc, signature, start, end, index);
    append(".LOCAL_VARIABLE()\n");
    return this;
  }

  @Override
  public CodeStream LINE_NUMBER(final int line,
                                final LABEL start) {
    super.LINE_NUMBER(line, start);
    final String alias = createLabelAlias(start);
    append(".LINE_NUMBER(").append(Integer.toString(line)).append(", ").append(alias).append(")\n");
    return this;
  }

  @Override
  public CodeStream MAXS(final int maxStack,
                         final int maxLocals) {
    super.MAXS(maxStack, maxLocals);
    append(".MAXS(").append(Integer.toString(maxStack)).append(",").append(Integer.toString(maxLocals)).append(")\n");
    return this;
  }

  @Override
  public void END() {
    super.END();
    append(".END();\n");

    try {
      output.append(labels.toString())
            .append('\n')
            .append(code.toString());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
