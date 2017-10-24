package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxHandleTag;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.spec.api.HxType;

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

  public PrintingCodeStream(final Appendable output) {
    this(null, output);
  }

  public PrintingCodeStream(final HxCodeStream codeStream,
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

  private String getLabelAlias(final LABEL label) {
    String alias = labelMap.get(label);
    if(alias == null) {
      labelMap.put(label, alias = newLabelAlias());
      labels.append("final LABEL ").append(alias).append(" = new LABEL();\n");
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

  private PrintingCodeStream append(boolean b) {
    code.append(b);
    return this;
  }

  private PrintingCodeStream append(byte b) {
    code.append("(byte) ").append(b);
    return this;
  }

  private PrintingCodeStream append(short s) {
    code.append("(short) ").append(s);
    return this;
  }

  private PrintingCodeStream append(int i) {
    code.append(i);
    return this;
  }

  private PrintingCodeStream append(float f) {
    code.append(f).append('f');
    return this;
  }

  private PrintingCodeStream append(long l) {
    code.append(l).append('L');
    return this;
  }

  private PrintingCodeStream append(double d) {
    code.append(d).append('d');
    return this;
  }

  private PrintingCodeStream append(HxType type) {
    code.append("getHaxxor().reference(\"").append(type.getName()).append("\")");
    return this;
  }

  private PrintingCodeStream append(HxMethodType methodType) {
    code.append("new HxMethodType(\"")
        .append(methodType.getSignature())
        .append("\")");
    return this;
  }

  private PrintingCodeStream append(HxMethodHandle handle) {
    code.append("new HxMethodHandle(")
        .append(toHandleTag(handle.getTag()))
        .append(",\"").append(handle.getOwner())
        .append("\",\"").append(handle.getName())
        .append("\",\"").append(handle.getDescriptor())
        .append("\",").append(handle.isInterface()).append(")");
    return this;
  }

  private PrintingCodeStream append(HxArguments arguments) {
    append("HxArguments.createArguments(")
        .append(arguments.length()).append(")");

    for (int i = 0, len = arguments.length(); i < len; i++) {
      append(".add(").appendArgument(arguments.get(0)).append(")");
    }
    return this;
  }

  private PrintingCodeStream appendArgument(Object arg) {
    if(arg instanceof HxType) {
      append((HxType) arg);
    } else if(arg instanceof HxMethodHandle) {
      append((HxMethodHandle) arg);
    } else if(arg instanceof String) {
      append('"').append((String) arg).append('"');
    } else if(arg instanceof Number) {
      if(arg instanceof Integer) {
        append((Integer) arg);
      } else if(arg instanceof Float) {
        append((Float) arg);
      } else if(arg instanceof Long) {
        append((Long) arg);
      } else if(arg instanceof Double) {
        append((Double) arg);
      }
    }
    return this;
  }

  private PrintingCodeStream append(LABEL label) {
    code.append(getLabelAlias(label));
    return this;
  }

  private PrintingCodeStream append(LABEL[] labels) {
    code.append("new LABEL[]{");
    if(labels.length > 0) {
      append(labels[0]);
      for (int i = 1; i < labels.length; i++) {
        append(',').append(labels[i]);
      }
    }
    code.append('}');
    return this;
  }

  private PrintingCodeStream append(int[] ints) {
    code.append("new int[]{");
    if(ints.length > 0) {
      append(ints[0]);
      for (int i = 1; i < ints.length; i++) {
        append(',').append(ints[i]);
      }
    }
    code.append('}');
    return this;
  }

  private String toHandleTag(HxHandleTag tag) {
    switch (tag) {
      case GETFIELD: return "HxHandleTag.GETFIELD";
      case GETSTATIC: return "HxHandleTag.GETSTATIC";
      case PUTFIELD: return "HxHandleTag.PUTFIELD";
      case PUTSTATIC: return "HxHandleTag.PUTSTATIC";
      case INVOKEVIRTUAL: return "HxHandleTag.INVOKEVIRTUAL";
      case INVOKESTATIC: return "HxHandleTag.INVOKESTATIC";
      case INVOKESPECIAL: return "HxHandleTag.INVOKESPECIAL";
      case NEWINVOKESPECIAL: return "HxHandleTag.NEWINVOKESPECIAL";
      case INVOKEINTERFACE: return "HxHandleTag.INVOKEINTERFACE";
      default:
        throw new IllegalStateException("Unknown tag: "+tag);
    }
  }

  public void flush()
  throws IOException {
    output.append(labels);
    output.append(code);
  }

  public StringBuilder getLabels() {
    return labels;
  }

  public StringBuilder getCode() {
    return code;
  }

  @Override
  public HxCodeStream BEGIN() {
    super.BEGIN();
    append(".BEGIN()\n");
    return this;
  }

  @Override
  public HxCodeStream NOP() {
    super.NOP();
    append(".NOP()\n");
    return this;
  }

  @Override
  public HxCodeStream ACONST_NULL() {
    super.ACONST_NULL();
    append(".ACONST_NULL()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_M1() {
    super.ICONST_M1();
    append(".ICONST_M1()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_0() {
    super.ICONST_0();
    append(".ICONST_0()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_1() {
    super.ICONST_1();
    append(".ICONST_1()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_2() {
    super.ICONST_2();
    append(".ICONST_2()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_3() {
    super.ICONST_3();
    append(".ICONST_3()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_4() {
    super.ICONST_4();
    append(".ICONST_4()\n");
    return this;
  }

  @Override
  public HxCodeStream ICONST_5() {
    super.ICONST_5();
    append(".ICONST_5()\n");
    return this;
  }

  @Override
  public HxCodeStream LCONST_0() {
    super.LCONST_0();
    append(".LCONST_0()\n");
    return this;
  }

  @Override
  public HxCodeStream LCONST_1() {
    super.LCONST_1();
    append(".LCONST_1()\n");
    return this;
  }

  @Override
  public HxCodeStream FCONST_0() {
    super.FCONST_0();
    append(".FCONST_0()\n");
    return this;
  }

  @Override
  public HxCodeStream FCONST_1() {
    super.FCONST_1();
    append(".FCONST_1()\n");
    return this;
  }

  @Override
  public HxCodeStream FCONST_2() {
    super.FCONST_2();
    append(".FCONST_2()\n");
    return this;
  }

  @Override
  public HxCodeStream DCONST_0() {
    super.DCONST_0();
    append(".DCONST_0()\n");
    return this;
  }

  @Override
  public HxCodeStream DCONST_1() {
    super.DCONST_1();
    append(".DCONST_1()\n");
    return this;
  }

  @Override
  public HxCodeStream BIPUSH(final byte value) {
    super.BIPUSH(value);
    append(".BIPUSH(").append(value).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream SIPUSH(final short value) {
    super.SIPUSH(value);
    append(".SIPUSH(").append(value).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LDC(final int value) {
    super.LDC(value);
    append(".LDC(").append(value).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LDC(final float value) {
    super.LDC(value);
    append(".LDC(").append(value).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LDC(final long value) {
    super.LDC(value);
    append(".LDC(").append(value).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LDC(final double value) {
    super.LDC(value);
    append(".LDC(").append(value).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LDC(final String value) {
    super.LDC(value);
    append(".LDC(\"").append(value).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream HANDLE(final HxMethodHandle handle) {
    super.HANDLE(handle);
    append(".HANDLE(").append(handle).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream METHOD(final HxMethodType methodType) {
    super.METHOD(methodType);
    append(".METHOD(").append(methodType).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream TYPE(final String typeDescriptor) {
    super.TYPE(typeDescriptor);
    append(".TYPE(").append("\"").append(typeDescriptor).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream ILOAD(final int idx) {
    super.ILOAD(idx);
    append(".ILOAD(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LLOAD(final int idx) {
    super.LLOAD(idx);
    append(".LLOAD(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream FLOAD(final int idx) {
    super.FLOAD(idx);
    append(".FLOAD(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream DLOAD(final int idx) {
    super.DLOAD(idx);
    append(".DLOAD(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream ALOAD(final int idx) {
    super.ALOAD(idx);
    append(".ALOAD(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream THIS() {
    super.THIS();
    append(".THIS()\n");
    return this;
  }

  @Override
  public HxCodeStream IALOAD() {
    super.IALOAD();
    append(".IALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream LALOAD() {
    super.LALOAD();
    append(".LALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream FALOAD() {
    super.FALOAD();
    append(".FALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream DALOAD() {
    super.DALOAD();
    append(".DALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream AALOAD() {
    super.AALOAD();
    append(".AALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream BALOAD() {
    super.BALOAD();
    append(".BALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream CALOAD() {
    super.CALOAD();
    append(".CALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream SALOAD() {
    super.SALOAD();
    append(".SALOAD()\n");
    return this;
  }

  @Override
  public HxCodeStream ISTORE(final int idx) {
    super.ISTORE(idx);
    append(".ISTORE(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LSTORE(final int idx) {
    super.LSTORE(idx);
    append(".LSTORE(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream FSTORE(final int idx) {
    super.FSTORE(idx);
    append(".FSTORE(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream DSTORE(final int idx) {
    super.DSTORE(idx);
    append(".DSTORE(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream ASTORE(final int idx) {
    super.ASTORE(idx);
    append(".ASTORE(").append(idx).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IASTORE() {
    super.IASTORE();
    append(".IASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream LASTORE() {
    super.LASTORE();
    append(".LASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream FASTORE() {
    super.FASTORE();
    append(".FASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream DASTORE() {
    super.DASTORE();
    append(".DASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream AASTORE() {
    super.AASTORE();
    append(".AASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream BASTORE() {
    super.BASTORE();
    append(".BASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream CASTORE() {
    super.CASTORE();
    append(".CASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream SASTORE() {
    super.SASTORE();
    append(".SASTORE()\n");
    return this;
  }

  @Override
  public HxCodeStream POP() {
    super.POP();
    append(".POP()\n");
    return this;
  }

  @Override
  public HxCodeStream POP2() {
    super.POP2();
    append(".POP2()\n");
    return this;
  }

  @Override
  public HxCodeStream DUP() {
    super.DUP();
    append(".DUP()\n");
    return this;
  }

  @Override
  public HxCodeStream DUP_X1() {
    super.DUP_X1();
    append(".DUP_X1()\n");
    return this;
  }

  @Override
  public HxCodeStream DUP_X2() {
    super.DUP_X2();
    append(".DUP_X2()\n");
    return this;
  }

  @Override
  public HxCodeStream DUP2() {
    super.DUP2();
    append(".DUP2()\n");
    return this;
  }

  @Override
  public HxCodeStream DUP2_X1() {
    super.DUP2_X1();
    append(".DUP2_X1()\n");
    return this;
  }

  @Override
  public HxCodeStream DUP2_X2() {
    super.DUP2_X2();
    append(".DUP2_X2()\n");
    return this;
  }

  @Override
  public HxCodeStream SWAP() {
    super.SWAP();
    append(".SWAP()\n");
    return this;
  }

  @Override
  public HxCodeStream IADD() {
    super.IADD();
    append(".IADD()\n");
    return this;
  }

  @Override
  public HxCodeStream LADD() {
    super.LADD();
    append(".LADD()\n");
    return this;
  }

  @Override
  public HxCodeStream FADD() {
    super.FADD();
    append(".FADD()\n");
    return this;
  }

  @Override
  public HxCodeStream DADD() {
    super.DADD();
    append(".DADD()\n");
    return this;
  }

  @Override
  public HxCodeStream ISUB() {
    super.ISUB();
    append(".ISUB()\n");
    return this;
  }

  @Override
  public HxCodeStream LSUB() {
    super.LSUB();
    append(".LSUB()\n");
    return this;
  }

  @Override
  public HxCodeStream FSUB() {
    super.FSUB();
    append(".FSUB()\n");
    return this;
  }

  @Override
  public HxCodeStream DSUB() {
    super.DSUB();
    append(".DSUB()\n");
    return this;
  }

  @Override
  public HxCodeStream IMUL() {
    super.IMUL();
    append(".IMUL()\n");
    return this;
  }

  @Override
  public HxCodeStream LMUL() {
    super.LMUL();
    append(".LMUL()\n");
    return this;
  }

  @Override
  public HxCodeStream FMUL() {
    super.FMUL();
    append(".FMUL()\n");
    return this;
  }

  @Override
  public HxCodeStream DMUL() {
    super.DMUL();
    append(".DMUL()\n");
    return this;
  }

  @Override
  public HxCodeStream IDIV() {
    super.IDIV();
    append(".IDIV()\n");
    return this;
  }

  @Override
  public HxCodeStream LDIV() {
    super.LDIV();
    append(".LDIV()\n");
    return this;
  }

  @Override
  public HxCodeStream FDIV() {
    super.FDIV();
    append(".FDIV()\n");
    return this;
  }

  @Override
  public HxCodeStream DDIV() {
    super.DDIV();
    append(".DDIV()\n");
    return this;
  }

  @Override
  public HxCodeStream IREM() {
    super.IREM();
    append(".IREM()\n");
    return this;
  }

  @Override
  public HxCodeStream LREM() {
    super.LREM();
    append(".LREM()\n");
    return this;
  }

  @Override
  public HxCodeStream FREM() {
    super.FREM();
    append(".FREM()\n");
    return this;
  }

  @Override
  public HxCodeStream DREM() {
    super.DREM();
    append(".DREM()\n");
    return this;
  }

  @Override
  public HxCodeStream INEG() {
    super.INEG();
    append(".INEG()\n");
    return this;
  }

  @Override
  public HxCodeStream LNEG() {
    super.LNEG();
    append(".LNEG()\n");
    return this;
  }

  @Override
  public HxCodeStream FNEG() {
    super.FNEG();
    append(".FNEG()\n");
    return this;
  }

  @Override
  public HxCodeStream DNEG() {
    super.DNEG();
    append(".DNEG()\n");
    return this;
  }

  @Override
  public HxCodeStream ISHL() {
    super.ISHL();
    append(".ISHL()\n");
    return this;
  }

  @Override
  public HxCodeStream LSHL() {
    super.LSHL();
    append(".LSHL()\n");
    return this;
  }

  @Override
  public HxCodeStream ISHR() {
    super.ISHR();
    append(".ISHR()\n");
    return this;
  }

  @Override
  public HxCodeStream LSHR() {
    super.LSHR();
    append(".LSHR()\n");
    return this;
  }

  @Override
  public HxCodeStream IUSHR() {
    super.IUSHR();
    append(".IUSHR()\n");
    return this;
  }

  @Override
  public HxCodeStream LUSHR() {
    super.LUSHR();
    append(".LUSHR()\n");
    return this;
  }

  @Override
  public HxCodeStream IAND() {
    super.IAND();
    append(".IAND()\n");
    return this;
  }

  @Override
  public HxCodeStream LAND() {
    super.LAND();
    append(".LAND()\n");
    return this;
  }

  @Override
  public HxCodeStream IOR() {
    super.IOR();
    append(".IOR()\n");
    return this;
  }

  @Override
  public HxCodeStream LOR() {
    super.LOR();
    append(".LOR()\n");
    return this;
  }

  @Override
  public HxCodeStream IXOR() {
    super.IXOR();
    append(".IXOR()\n");
    return this;
  }

  @Override
  public HxCodeStream LXOR() {
    super.LXOR();
    append(".LXOR()\n");
    return this;
  }

  @Override
  public HxCodeStream IINC(final int var,
                           final int increment) {
    super.IINC(var, increment);
    append(".IINC(").append(var).append(',').append(increment).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream I2L() {
    super.I2L();
    append(".I2L()\n");
    return this;
  }

  @Override
  public HxCodeStream I2F() {
    super.I2F();
    append(".I2F()\n");
    return this;
  }

  @Override
  public HxCodeStream I2D() {
    super.I2D();
    append(".I2D()\n");
    return this;
  }

  @Override
  public HxCodeStream L2I() {
    super.L2I();
    append(".L2I()\n");
    return this;
  }

  @Override
  public HxCodeStream L2F() {
    super.L2F();
    append(".L2F()\n");
    return this;
  }

  @Override
  public HxCodeStream L2D() {
    super.L2D();
    append(".L2D()\n");
    return this;
  }

  @Override
  public HxCodeStream F2I() {
    super.F2I();
    append(".F2I()\n");
    return this;
  }

  @Override
  public HxCodeStream F2L() {
    super.F2L();
    append(".F2L()\n");
    return this;
  }

  @Override
  public HxCodeStream F2D() {
    super.F2D();
    append(".F2D()\n");
    return this;
  }

  @Override
  public HxCodeStream D2I() {
    super.D2I();
    append(".D2I()\n");
    return this;
  }

  @Override
  public HxCodeStream D2L() {
    super.D2L();
    append(".D2L()\n");
    return this;
  }

  @Override
  public HxCodeStream D2F() {
    super.D2F();
    append(".D2F()\n");
    return this;
  }

  @Override
  public HxCodeStream I2B() {
    super.I2B();
    append(".I2B()\n");
    return this;
  }

  @Override
  public HxCodeStream I2C() {
    super.I2C();
    append(".I2C()\n");
    return this;
  }

  @Override
  public HxCodeStream I2S() {
    super.I2S();
    append(".I2S()\n");
    return this;
  }

  @Override
  public HxCodeStream LCMP() {
    super.LCMP();
    append(".LCMP()\n");
    return this;
  }

  @Override
  public HxCodeStream FCMPL() {
    super.FCMPL();
    append(".FCMPL()\n");
    return this;
  }

  @Override
  public HxCodeStream FCMPG() {
    super.FCMPG();
    append(".FCMPG()\n");
    return this;
  }

  @Override
  public HxCodeStream DCMPL() {
    super.DCMPL();
    append(".DCMPL()\n");
    return this;
  }

  @Override
  public HxCodeStream DCMPG() {
    super.DCMPG();
    append(".DCMPG()\n");
    return this;
  }

  @Override
  public HxCodeStream IFEQ(final LABEL label) {
    super.IFEQ(label);
    append(".IFEQ(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFNE(final LABEL label) {
    super.IFNE(label);
    append(".IFNE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFLT(final LABEL label) {
    super.IFLT(label);
    append(".IFLT(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFGE(final LABEL label) {
    super.IFGE(label);
    append(".IFGE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFGT(final LABEL label) {
    super.IFGT(label);
    append(".IFGT(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFLE(final LABEL label) {
    super.IFLE(label);
    append(".IFLE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPEQ(final LABEL label) {
    super.IF_ICMPEQ(label);
    append(".IF_ICMPEQ(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPNE(final LABEL label) {
    super.IF_ICMPNE(label);
    append(".IF_ICMPNE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLT(final LABEL label) {
    super.IF_ICMPLT(label);
    append(".IF_ICMPLT(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGE(final LABEL label) {
    super.IF_ICMPGE(label);
    append(".IF_ICMPGE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPGT(final LABEL label) {
    super.IF_ICMPGT(label);
    append(".IF_ICMPGT(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ICMPLE(final LABEL label) {
    super.IF_ICMPLE(label);
    append(".IF_ICMPLE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPEQ(final LABEL label) {
    super.IF_ACMPEQ(label);
    append(".IF_ACMPEQ(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IF_ACMPNE(final LABEL label) {
    super.IF_ACMPNE(label);
    append(".IF_ACMPNE(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream GOTO(final LABEL label) {
    super.GOTO(label);
    append(".GOTO(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream JSR(final LABEL label) {
    super.JSR(label);
    append(".JSR(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream RET(final int var) {
    super.RET(var);
    append(".RET(").append(var).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream TABLESWITCH(final int min,
                                  final int max,
                                  final LABEL defaultLabel,
                                  final LABEL... labels) {
    super.TABLESWITCH(min, max, defaultLabel, labels);
    append(".TABLESWITCH(")
        .append(min).append(",")
        .append(max).append(",")
        .append(defaultLabel).append(",")
        .append(labels).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LOOKUPSWITCH(final LABEL defaultLabel,
                                   final int[] keys,
                                   final LABEL[] labels) {
    super.LOOKUPSWITCH(defaultLabel, keys, labels);
    append(".TABLESWITCH(")
        .append(defaultLabel).append(",")
        .append(keys).append(",")
        .append(labels).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IRETURN() {
    super.IRETURN();
    append(".IRETURN()\n");
    return this;
  }

  @Override
  public HxCodeStream LRETURN() {
    super.LRETURN();
    append(".LRETURN()\n");
    return this;
  }

  @Override
  public HxCodeStream FRETURN() {
    super.FRETURN();
    append(".FRETURN()\n");
    return this;
  }

  @Override
  public HxCodeStream DRETURN() {
    super.DRETURN();
    append(".DRETURN()\n");
    return this;
  }

  @Override
  public HxCodeStream ARETURN() {
    super.ARETURN();
    append(".ARETURN()\n");
    return this;
  }

  @Override
  public HxCodeStream RETURN() {
    super.RETURN();
    append(".RETURN()\n");
    return this;
  }

  @Override
  public HxCodeStream GETSTATIC(final String owner,
                                final String name,
                                final String desc) {
    super.GETSTATIC(owner, name, desc);
    append(".GETSTATIC(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream PUTSTATIC(final String owner,
                                final String name,
                                final String desc) {
    super.PUTSTATIC(owner, name, desc);
    append(".PUTSTATIC(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream GETFIELD(final String owner,
                               final String name,
                               final String desc) {
    super.GETFIELD(owner, name, desc);
    append(".GETFIELD(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream PUTFIELD(final String owner,
                               final String name,
                               final String desc) {
    super.PUTFIELD(owner, name, desc);
    append(".PUTFIELD(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream INVOKEVIRTUAL(final String owner,
                                    final String name,
                                    final String desc) {
    super.INVOKEVIRTUAL(owner, name, desc);
    append(".INVOKEVIRTUAL(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream INVOKESPECIAL(final String owner,
                                    final String name,
                                    final String desc) {
    super.INVOKESPECIAL(owner, name, desc);
    append(".INVOKESPECIAL(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream INVOKESTATIC(final String owner,
                                   final String name,
                                   final String desc,
                                   final boolean isInterface) {
    super.INVOKESTATIC(owner, name, desc, isInterface);
    append(".INVOKESTATIC(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\",")
        .append(isInterface).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream INVOKEINTERFACE(final String owner,
                                      final String name,
                                      final String desc) {
    super.INVOKEINTERFACE(owner, name, desc);
    append(".INVOKEINTERFACE(\"")
        .append(owner).append("\",\"")
        .append(name).append("\",")
        .append(desc).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream INVOKEDYNAMIC(final String name,
                                    final String desc,
                                    final HxMethodHandle bsm,
                                    final HxArguments bsmArgs) {
    super.INVOKEDYNAMIC(name, desc, bsm, bsmArgs);
    append(".INVOKEDYNAMIC(\"")
        .append(name).append("\",\"")
        .append(desc).append("\",")
        .append(bsm).append(",")
        .append(bsmArgs).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream NEW(final String internalType) {
    super.NEW(internalType);
    append(".NEW(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream NEWARRAY(final HxArrayType type) {
    super.NEWARRAY(type);
    append(".NEWARRAY(HxArrayType.").append(type.name()).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream ANEWARRAY(final String internalType) {
    super.ANEWARRAY(internalType);
    append(".ANEWARRAY(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream ARRAYLENGTH() {
    super.ARRAYLENGTH();
    append(".ARRAYLENGTH()\n");
    return this;
  }

  @Override
  public HxCodeStream ATHROW() {
    super.ATHROW();
    append(".ATHROW()\n");
    return this;
  }

  @Override
  public HxCodeStream CHECKCAST(final String internalType) {
    super.CHECKCAST(internalType);
    append(".CHECKCAST(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream INSTANCEOF(final String internalType) {
    super.INSTANCEOF(internalType);
    append(".INSTANCEOF(\"").append(internalType).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream MONITORENTER() {
    super.MONITORENTER();
    append(".MONITORENTER()\n");
    return this;
  }

  @Override
  public HxCodeStream MONITOREXIT() {
    super.MONITOREXIT();
    append(".MONITOREXIT()\n");
    return this;
  }

  @Override
  public HxCodeStream MULTIANEWARRAY(final String internalType,
                                     final int dims) {
    super.MULTIANEWARRAY(internalType, dims);
    append(".MULTIANEWARRAY(\"").append(internalType).append("\",").append(dims).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFNULL(final LABEL label) {
    super.IFNULL(label);
    append(".IFNULL(").append(label).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream IFNONNULL(final LABEL label) {
    super.IFNONNULL(label);
    append(".IFNONNULL(").append(label).append(")\n");
    return this;
  }



  @Override
  public HxCodeStream LABEL(final LABEL label) {
    super.LABEL(label);
    append(".LABEL(").append(label).append(")\n");
    return this;
  }


  @Override
  public HxCodeStream TRY_CATCH(final LABEL startLabel,
                                final LABEL endLabel,
                                final LABEL handler,
                                final String exceptionTypename) {
    super.TRY_CATCH(startLabel, endLabel, handler, exceptionTypename);
    append(".TRY_CATCH(")
      .append(startLabel).append(",")
      .append(endLabel).append(",")
      .append(handler).append(",\"")
      .append(exceptionTypename).append("\")\n");
    return this;
  }

  @Override
  public HxCodeStream FRAME(final HxFrames type,
                            final int nLocal,
                            final Object[] local,
                            final int nStack,
                            final Object[] stack) {
    super.FRAME(type, nLocal, local, nStack, stack);
    append(".FRAME()\n");
    return this;
  }

  @Override
  public HxCodeStream LOCAL_VARIABLE(final String name,
                                     final int index,
                                     final String desc,
                                     final String signature,
                                     final LABEL start,
                                     final LABEL end) {
    super.LOCAL_VARIABLE(name, index, desc, signature, start, end);
    append(".LOCAL_VARIABLE(\"").append(name)
                                .append("\",\"").append(desc)
                                .append("\",\"").append(signature)
                                .append("\",").append(start)
                                .append(",").append(end)
                                .append(",").append(index)
                                .append(")\n");
    return this;
  }

  @Override
  public HxCodeStream LINE_NUMBER(final int line,
                                  final LABEL start) {
    super.LINE_NUMBER(line, start);
    append(".LINE_NUMBER(").append(line).append(", ").append(start).append(")\n");
    return this;
  }

  @Override
  public HxCodeStream MAXS(final int maxStack,
                           final int maxLocals) {
    super.MAXS(maxStack, maxLocals);
    append(".MAXS(").append(maxStack).append(",").append(maxLocals).append(")\n");
    return this;
  }

  @Override
  public void END() {
    super.END();
    append(".END();\n");

    try {
      output.append(labels)
            .append('\n')
            .append(code);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String toString() {
    return labels.toString() + code.toString();
  }
}
