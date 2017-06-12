package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.NOP;
import net.andreho.haxxor.cgen.instr.arithmetic.DADD;
import net.andreho.haxxor.cgen.instr.arithmetic.DDIV;
import net.andreho.haxxor.cgen.instr.arithmetic.DMUL;
import net.andreho.haxxor.cgen.instr.arithmetic.DNEG;
import net.andreho.haxxor.cgen.instr.arithmetic.DREM;
import net.andreho.haxxor.cgen.instr.arithmetic.DSUB;
import net.andreho.haxxor.cgen.instr.arithmetic.FADD;
import net.andreho.haxxor.cgen.instr.arithmetic.FDIV;
import net.andreho.haxxor.cgen.instr.arithmetic.FMUL;
import net.andreho.haxxor.cgen.instr.arithmetic.FNEG;
import net.andreho.haxxor.cgen.instr.arithmetic.FREM;
import net.andreho.haxxor.cgen.instr.arithmetic.FSUB;
import net.andreho.haxxor.cgen.instr.arithmetic.IADD;
import net.andreho.haxxor.cgen.instr.arithmetic.IDIV;
import net.andreho.haxxor.cgen.instr.arithmetic.IMUL;
import net.andreho.haxxor.cgen.instr.arithmetic.INEG;
import net.andreho.haxxor.cgen.instr.arithmetic.IREM;
import net.andreho.haxxor.cgen.instr.arithmetic.ISUB;
import net.andreho.haxxor.cgen.instr.arithmetic.LADD;
import net.andreho.haxxor.cgen.instr.arithmetic.LDIV;
import net.andreho.haxxor.cgen.instr.arithmetic.LMUL;
import net.andreho.haxxor.cgen.instr.arithmetic.LNEG;
import net.andreho.haxxor.cgen.instr.arithmetic.LREM;
import net.andreho.haxxor.cgen.instr.arithmetic.LSUB;
import net.andreho.haxxor.cgen.instr.array.ARRAYLENGTH;
import net.andreho.haxxor.cgen.instr.array.load.AALOAD;
import net.andreho.haxxor.cgen.instr.array.load.BALOAD;
import net.andreho.haxxor.cgen.instr.array.load.CALOAD;
import net.andreho.haxxor.cgen.instr.array.load.DALOAD;
import net.andreho.haxxor.cgen.instr.array.load.FALOAD;
import net.andreho.haxxor.cgen.instr.array.load.IALOAD;
import net.andreho.haxxor.cgen.instr.array.load.LALOAD;
import net.andreho.haxxor.cgen.instr.array.load.SALOAD;
import net.andreho.haxxor.cgen.instr.array.store.AASTORE;
import net.andreho.haxxor.cgen.instr.array.store.BASTORE;
import net.andreho.haxxor.cgen.instr.array.store.CASTORE;
import net.andreho.haxxor.cgen.instr.array.store.DASTORE;
import net.andreho.haxxor.cgen.instr.array.store.FASTORE;
import net.andreho.haxxor.cgen.instr.array.store.IASTORE;
import net.andreho.haxxor.cgen.instr.array.store.LASTORE;
import net.andreho.haxxor.cgen.instr.array.store.SASTORE;
import net.andreho.haxxor.cgen.instr.binary.IAND;
import net.andreho.haxxor.cgen.instr.binary.IOR;
import net.andreho.haxxor.cgen.instr.binary.ISHL;
import net.andreho.haxxor.cgen.instr.binary.ISHR;
import net.andreho.haxxor.cgen.instr.binary.IUSHR;
import net.andreho.haxxor.cgen.instr.binary.IXOR;
import net.andreho.haxxor.cgen.instr.binary.LAND;
import net.andreho.haxxor.cgen.instr.binary.LOR;
import net.andreho.haxxor.cgen.instr.binary.LSHL;
import net.andreho.haxxor.cgen.instr.binary.LSHR;
import net.andreho.haxxor.cgen.instr.binary.LUSHR;
import net.andreho.haxxor.cgen.instr.binary.LXOR;
import net.andreho.haxxor.cgen.instr.cflow.ATHROW;
import net.andreho.haxxor.cgen.instr.cflow.FRAME;
import net.andreho.haxxor.cgen.instr.cflow.exits.ARETURN;
import net.andreho.haxxor.cgen.instr.cflow.exits.DRETURN;
import net.andreho.haxxor.cgen.instr.cflow.exits.FRETURN;
import net.andreho.haxxor.cgen.instr.cflow.exits.IRETURN;
import net.andreho.haxxor.cgen.instr.cflow.exits.LRETURN;
import net.andreho.haxxor.cgen.instr.cflow.exits.RETURN;
import net.andreho.haxxor.cgen.instr.compare.DCMPG;
import net.andreho.haxxor.cgen.instr.compare.DCMPL;
import net.andreho.haxxor.cgen.instr.compare.FCMPG;
import net.andreho.haxxor.cgen.instr.compare.FCMPL;
import net.andreho.haxxor.cgen.instr.compare.LCMP;
import net.andreho.haxxor.cgen.instr.constants.ACONST_NULL;
import net.andreho.haxxor.cgen.instr.constants.BIPUSH;
import net.andreho.haxxor.cgen.instr.constants.DCONST_0;
import net.andreho.haxxor.cgen.instr.constants.DCONST_1;
import net.andreho.haxxor.cgen.instr.constants.FCONST_0;
import net.andreho.haxxor.cgen.instr.constants.FCONST_1;
import net.andreho.haxxor.cgen.instr.constants.FCONST_2;
import net.andreho.haxxor.cgen.instr.constants.ICONST_0;
import net.andreho.haxxor.cgen.instr.constants.ICONST_1;
import net.andreho.haxxor.cgen.instr.constants.ICONST_2;
import net.andreho.haxxor.cgen.instr.constants.ICONST_3;
import net.andreho.haxxor.cgen.instr.constants.ICONST_4;
import net.andreho.haxxor.cgen.instr.constants.ICONST_5;
import net.andreho.haxxor.cgen.instr.constants.ICONST_M1;
import net.andreho.haxxor.cgen.instr.constants.LCONST_0;
import net.andreho.haxxor.cgen.instr.constants.LCONST_1;
import net.andreho.haxxor.cgen.instr.constants.LDC;
import net.andreho.haxxor.cgen.instr.constants.SIPUSH;
import net.andreho.haxxor.cgen.instr.conversion.CHECKCAST;
import net.andreho.haxxor.cgen.instr.conversion.INSTANCEOF;
import net.andreho.haxxor.cgen.instr.conversion.primitive.D2F;
import net.andreho.haxxor.cgen.instr.conversion.primitive.D2I;
import net.andreho.haxxor.cgen.instr.conversion.primitive.D2L;
import net.andreho.haxxor.cgen.instr.conversion.primitive.F2D;
import net.andreho.haxxor.cgen.instr.conversion.primitive.F2I;
import net.andreho.haxxor.cgen.instr.conversion.primitive.F2L;
import net.andreho.haxxor.cgen.instr.conversion.primitive.I2B;
import net.andreho.haxxor.cgen.instr.conversion.primitive.I2C;
import net.andreho.haxxor.cgen.instr.conversion.primitive.I2D;
import net.andreho.haxxor.cgen.instr.conversion.primitive.I2F;
import net.andreho.haxxor.cgen.instr.conversion.primitive.I2L;
import net.andreho.haxxor.cgen.instr.conversion.primitive.I2S;
import net.andreho.haxxor.cgen.instr.conversion.primitive.L2D;
import net.andreho.haxxor.cgen.instr.conversion.primitive.L2F;
import net.andreho.haxxor.cgen.instr.conversion.primitive.L2I;
import net.andreho.haxxor.cgen.instr.create.ANEWARRAY;
import net.andreho.haxxor.cgen.instr.create.MULTIANEWARRAY;
import net.andreho.haxxor.cgen.instr.create.NEW;
import net.andreho.haxxor.cgen.instr.create.NEWARRAY;
import net.andreho.haxxor.cgen.instr.fields.GETFIELD;
import net.andreho.haxxor.cgen.instr.fields.GETSTATIC;
import net.andreho.haxxor.cgen.instr.fields.PUTFIELD;
import net.andreho.haxxor.cgen.instr.fields.PUTSTATIC;
import net.andreho.haxxor.cgen.instr.invokes.INVOKEDYNAMIC;
import net.andreho.haxxor.cgen.instr.invokes.INVOKEINTERFACE;
import net.andreho.haxxor.cgen.instr.invokes.INVOKESPECIAL;
import net.andreho.haxxor.cgen.instr.invokes.INVOKESTATIC;
import net.andreho.haxxor.cgen.instr.invokes.INVOKEVIRTUAL;
import net.andreho.haxxor.cgen.instr.jumps.GOTO;
import net.andreho.haxxor.cgen.instr.jumps.JSR;
import net.andreho.haxxor.cgen.instr.jumps.RET;
import net.andreho.haxxor.cgen.instr.jumps.cond.integer.IF_ICMPEQ;
import net.andreho.haxxor.cgen.instr.jumps.cond.integer.IF_ICMPGE;
import net.andreho.haxxor.cgen.instr.jumps.cond.integer.IF_ICMPGT;
import net.andreho.haxxor.cgen.instr.jumps.cond.integer.IF_ICMPLE;
import net.andreho.haxxor.cgen.instr.jumps.cond.integer.IF_ICMPLT;
import net.andreho.haxxor.cgen.instr.jumps.cond.integer.IF_ICMPNE;
import net.andreho.haxxor.cgen.instr.jumps.cond.refs.IFNONNULL;
import net.andreho.haxxor.cgen.instr.jumps.cond.refs.IFNULL;
import net.andreho.haxxor.cgen.instr.jumps.cond.refs.IF_ACMPEQ;
import net.andreho.haxxor.cgen.instr.jumps.cond.refs.IF_ACMPNE;
import net.andreho.haxxor.cgen.instr.jumps.cond.zero.IFEQ;
import net.andreho.haxxor.cgen.instr.jumps.cond.zero.IFGE;
import net.andreho.haxxor.cgen.instr.jumps.cond.zero.IFGT;
import net.andreho.haxxor.cgen.instr.jumps.cond.zero.IFLE;
import net.andreho.haxxor.cgen.instr.jumps.cond.zero.IFLT;
import net.andreho.haxxor.cgen.instr.jumps.cond.zero.IFNE;
import net.andreho.haxxor.cgen.instr.local.IINC;
import net.andreho.haxxor.cgen.instr.local.load.ALOAD;
import net.andreho.haxxor.cgen.instr.local.load.DLOAD;
import net.andreho.haxxor.cgen.instr.local.load.FLOAD;
import net.andreho.haxxor.cgen.instr.local.load.ILOAD;
import net.andreho.haxxor.cgen.instr.local.load.LLOAD;
import net.andreho.haxxor.cgen.instr.local.store.ASTORE;
import net.andreho.haxxor.cgen.instr.local.store.DSTORE;
import net.andreho.haxxor.cgen.instr.local.store.FSTORE;
import net.andreho.haxxor.cgen.instr.local.store.ISTORE;
import net.andreho.haxxor.cgen.instr.local.store.LSTORE;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.cgen.instr.stack.DUP;
import net.andreho.haxxor.cgen.instr.stack.DUP2;
import net.andreho.haxxor.cgen.instr.stack.DUP2_X1;
import net.andreho.haxxor.cgen.instr.stack.DUP2_X2;
import net.andreho.haxxor.cgen.instr.stack.DUP_X1;
import net.andreho.haxxor.cgen.instr.stack.DUP_X2;
import net.andreho.haxxor.cgen.instr.stack.POP;
import net.andreho.haxxor.cgen.instr.stack.POP2;
import net.andreho.haxxor.cgen.instr.stack.SWAP;
import net.andreho.haxxor.cgen.instr.switches.LOOKUPSWITCH;
import net.andreho.haxxor.cgen.instr.switches.TABLESWITCH;
import net.andreho.haxxor.cgen.instr.sync.MONITORENTER;
import net.andreho.haxxor.cgen.instr.sync.MONITOREXIT;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public interface HxInstructionFactory {
  int FLOAT_ONE = Float.floatToIntBits(1f);
  int FLOAT_TWO = Float.floatToIntBits(2f);
  long DOUBLE_ONE = Double.doubleToLongBits(1d);

  default HxInstruction BEGIN() {
    return new BEGIN();
  }

  default HxInstruction END() {
    return new END();
  }

  default HxInstruction NOP() {
    return new NOP();
  }

  default HxInstruction ACONST_NULL() {
    return new ACONST_NULL();
  }

  default HxInstruction ICONST(int value) {
    switch (value) {
      case -1:
        return new ICONST_M1();
      case 0:
        return new ICONST_0();
      case 1:
        return new ICONST_1();
      case 2:
        return new ICONST_2();
      case 3:
        return new ICONST_3();
      case 4:
        return new ICONST_4();
      case 5:
        return new ICONST_5();
      default: {
        throw new IllegalArgumentException("Unsupported parameter: " + value);
      }
    }
  }

  default HxInstruction LCONST(long value) {
    if (value == 0L) {
      return new LCONST_0();
    } else if (value == 1L) {
      return new LCONST_1();
    }
    throw new IllegalArgumentException("Unsupported parameter: " + value);
  }

  default HxInstruction FCONST(float value) {
    if (value == 0f) {
      return new FCONST_0();
    } else if (Float.floatToIntBits(value) == FLOAT_ONE) {
      return new FCONST_1();
    } else if (Float.floatToIntBits(value) == FLOAT_TWO) {
      return new FCONST_2();
    }
    throw new IllegalArgumentException("Unsupported parameter: " + value);
  }

  default HxInstruction DCONST(double value) {
    if (value == 0d) {
      return new DCONST_0();
    } else if (Double.doubleToLongBits(value) == DOUBLE_ONE) {
      return new DCONST_1();
    }
    throw new IllegalArgumentException("Unsupported parameter: " + value);
  }

  default HxInstruction BIPUSH(byte value) {
    return new BIPUSH(value);
  }

  default HxInstruction SIPUSH(short value) {
    return new SIPUSH(value);
  }

  default HxInstruction LDC(int value) {
    return LDC.of(value);
  }

  default HxInstruction LDC(float value) {
    return LDC.of(value);
  }

  default HxInstruction LDC(long value) {
    return LDC.of(value);
  }

  default HxInstruction LDC(double value) {
    return LDC.of(value);
  }

  default HxInstruction LDC(String value) {
    return LDC.of(value);
  }

  default HxInstruction HANDLE(HxHandle value) {
    return LDC.ofMethodHandle(value);
  }

  default HxInstruction METHOD(String value) {
    return LDC.ofMethodType(value);
  }

  default HxInstruction TYPE(String value) {
    return LDC.ofType(value);
  }

  default HxInstruction TYPE(HxType value) {
    return TYPE(value.toInternalName());
  }

  default HxInstruction ILOAD(int var) {
    return new ILOAD(var);
  }

  default HxInstruction LLOAD(int var) {
    return new LLOAD(var);
  }

  default HxInstruction FLOAD(int var) {
    return new FLOAD(var);
  }

  default HxInstruction DLOAD(int var) {
    return new DLOAD(var);
  }

  default HxInstruction ALOAD(int var) {
    return new ALOAD(var);
  }

  default HxInstruction IALOAD() {
    return new IALOAD();
  }

  default HxInstruction LALOAD() {
    return new LALOAD();
  }

  default HxInstruction FALOAD() {
    return new FALOAD();
  }

  default HxInstruction DALOAD() {
    return new DALOAD();
  }

  default HxInstruction AALOAD() {
    return new AALOAD();
  }

  default HxInstruction BALOAD() {
    return new BALOAD();
  }

  default HxInstruction CALOAD() {
    return new CALOAD();
  }

  default HxInstruction SALOAD() {
    return new SALOAD();
  }

  default HxInstruction ISTORE(int var) {
    return new ISTORE(var);
  }

  default HxInstruction LSTORE(int var) {
    return new LSTORE(var);
  }

  default HxInstruction FSTORE(int var) {
    return new FSTORE(var);
  }

  default HxInstruction DSTORE(int var) {
    return new DSTORE(var);
  }

  default HxInstruction ASTORE(int var) {
    return new ASTORE(var);
  }

  default HxInstruction IASTORE() {
    return new IASTORE();
  }

  default HxInstruction LASTORE() {
    return new LASTORE();
  }

  default HxInstruction FASTORE() {
    return new FASTORE();
  }

  default HxInstruction DASTORE() {
    return new DASTORE();
  }

  default HxInstruction AASTORE() {
    return new AASTORE();
  }

  default HxInstruction BASTORE() {
    return new BASTORE();
  }

  default HxInstruction CASTORE() {
    return new CASTORE();
  }

  default HxInstruction SASTORE() {
    return new SASTORE();
  }

  default HxInstruction POP() {
    return new POP();
  }

  default HxInstruction POP2() {
    return new POP2();
  }

  default HxInstruction DUP() {
    return new DUP();
  }

  default HxInstruction DUP_X1() {
    return new DUP_X1();
  }

  default HxInstruction DUP_X2() {
    return new DUP_X2();
  }

  default HxInstruction DUP2() {
    return new DUP2();
  }

  default HxInstruction DUP2_X1() {
    return new DUP2_X1();
  }

  default HxInstruction DUP2_X2() {
    return new DUP2_X2();
  }

  default HxInstruction SWAP() {
    return new SWAP();
  }

  default HxInstruction IADD() {
    return new IADD();
  }

  default HxInstruction LADD() {
    return new LADD();
  }

  default HxInstruction FADD() {
    return new FADD();
  }

  default HxInstruction DADD() {
    return new DADD();
  }

  default HxInstruction ISUB() {
    return new ISUB();
  }

  default HxInstruction LSUB() {
    return new LSUB();
  }

  default HxInstruction FSUB() {
    return new FSUB();
  }

  default HxInstruction DSUB() {
    return new DSUB();
  }

  default HxInstruction IMUL() {
    return new IMUL();
  }

  default HxInstruction LMUL() {
    return new LMUL();
  }

  default HxInstruction FMUL() {
    return new FMUL();
  }

  default HxInstruction DMUL() {
    return new DMUL();
  }

  default HxInstruction IDIV() {
    return new IDIV();
  }

  default HxInstruction LDIV() {
    return new LDIV();
  }

  default HxInstruction FDIV() {
    return new FDIV();
  }

  default HxInstruction DDIV() {
    return new DDIV();
  }

  default HxInstruction IREM() {
    return new IREM();
  }

  default HxInstruction LREM() {
    return new LREM();
  }

  default HxInstruction FREM() {
    return new FREM();
  }

  default HxInstruction DREM() {
    return new DREM();
  }

  default HxInstruction INEG() {
    return new INEG();
  }

  default HxInstruction LNEG() {
    return new LNEG();
  }

  default HxInstruction FNEG() {
    return new FNEG();
  }

  default HxInstruction DNEG() {
    return new DNEG();
  }

  default HxInstruction ISHL() {
    return new ISHL();
  }

  default HxInstruction LSHL() {
    return new LSHL();
  }

  default HxInstruction ISHR() {
    return new ISHR();
  }

  default HxInstruction LSHR() {
    return new LSHR();
  }

  default HxInstruction IUSHR() {
    return new IUSHR();
  }

  default HxInstruction LUSHR() {
    return new LUSHR();
  }

  default HxInstruction IAND() {
    return new IAND();
  }

  default HxInstruction LAND() {
    return new LAND();
  }

  default HxInstruction IOR() {
    return new IOR();
  }

  default HxInstruction LOR() {
    return new LOR();
  }

  default HxInstruction IXOR() {
    return new IXOR();
  }

  default HxInstruction LXOR() {
    return new LXOR();
  }

  default HxInstruction IINC(int var, int increment) {
    return new IINC(var, increment);
  }

  default HxInstruction I2L() {
    return new I2L();
  }

  default HxInstruction I2F() {
    return new I2F();
  }

  default HxInstruction I2D() {
    return new I2D();
  }

  default HxInstruction L2I() {
    return new L2I();
  }

  default HxInstruction L2F() {
    return new L2F();
  }

  default HxInstruction L2D() {
    return new L2D();
  }

  default HxInstruction F2I() {
    return new F2I();
  }

  default HxInstruction F2L() {
    return new F2L();
  }

  default HxInstruction F2D() {
    return new F2D();
  }

  default HxInstruction D2I() {
    return new D2I();
  }

  default HxInstruction D2L() {
    return new D2L();
  }

  default HxInstruction D2F() {
    return new D2F();
  }

  default HxInstruction I2B() {
    return new I2B();
  }

  default HxInstruction I2C() {
    return new I2C();
  }

  default HxInstruction I2S() {
    return new I2S();
  }

  default HxInstruction LCMP() {
    return new LCMP();
  }

  default HxInstruction FCMPL() {
    return new FCMPL();
  }

  default HxInstruction FCMPG() {
    return new FCMPG();
  }

  default HxInstruction DCMPL() {
    return new DCMPL();
  }

  default HxInstruction DCMPG() {
    return new DCMPG();
  }

  default HxInstruction IFEQ(LABEL label) {
    return new IFEQ(label);
  }

  default HxInstruction IFNE(LABEL label) {
    return new IFNE(label);
  }

  default HxInstruction IFLT(LABEL label) {
    return new IFLT(label);
  }

  default HxInstruction IFGE(LABEL label) {
    return new IFGE(label);
  }

  default HxInstruction IFGT(LABEL label) {
    return new IFGT(label);
  }

  default HxInstruction IFLE(LABEL label) {
    return new IFLE(label);
  }

  default HxInstruction IF_ICMPEQ(LABEL label) {
    return new IF_ICMPEQ(label);
  }

  default HxInstruction IF_ICMPNE(LABEL label) {
    return new IF_ICMPNE(label);
  }

  default HxInstruction IF_ICMPLT(LABEL label) {
    return new IF_ICMPLT(label);
  }

  default HxInstruction IF_ICMPGE(LABEL label) {
    return new IF_ICMPGE(label);
  }

  default HxInstruction IF_ICMPGT(LABEL label) {
    return new IF_ICMPGT(label);
  }

  default HxInstruction IF_ICMPLE(LABEL label) {
    return new IF_ICMPLE(label);
  }

  default HxInstruction IF_ACMPEQ(LABEL label) {
    return new IF_ACMPEQ(label);
  }

  default HxInstruction IF_ACMPNE(LABEL label) {
    return new IF_ACMPNE(label);
  }

  default HxInstruction GOTO(LABEL label) {
    return new GOTO(label);
  }

  default HxInstruction JSR(LABEL label) {
    return new JSR(label);
  }

  default HxInstruction RET(int var) {
    return new RET(var);
  }

  default HxInstruction TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels) {
    return new TABLESWITCH(min, max, defaultLabel, labels);
  }

  default HxInstruction LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL... labels) {
    return new LOOKUPSWITCH(defaultLabel, keys, labels);
  }

  default HxInstruction IRETURN() {
    return new IRETURN();
  }

  default HxInstruction LRETURN() {
    return new LRETURN();
  }

  default HxInstruction FRETURN() {
    return new FRETURN();
  }

  default HxInstruction DRETURN() {
    return new DRETURN();
  }

  default HxInstruction ARETURN() {
    return new ARETURN();
  }

  default HxInstruction RETURN() {
    return new RETURN();
  }

  default HxInstruction GETSTATIC(String type, String name, String fieldDesc) {
    return new GETSTATIC(type, name, fieldDesc);
  }

  default HxInstruction PUTSTATIC(String type, String name, String fieldDesc) {
    return new PUTSTATIC(type, name, fieldDesc);
  }

  default HxInstruction GETFIELD(String type, String name, String fieldDesc) {
    return new GETFIELD(type, name, fieldDesc);
  }

  default HxInstruction PUTFIELD(String type, String name, String fieldDesc) {
    return new PUTFIELD(type, name, fieldDesc);
  }

  default HxInstruction INVOKEVIRTUAL(String type, String name, String methodDesc) {
    return new INVOKEVIRTUAL(type, name, methodDesc);
  }

  default HxInstruction INVOKESPECIAL(String type, String name, String methodDesc) {
    return new INVOKESPECIAL(type, name, methodDesc);
  }

  default HxInstruction INVOKESTATIC(String type, String name, String methodDesc, boolean isInterface) {
    return new INVOKESTATIC(type, name, methodDesc, isInterface);
  }

  default HxInstruction INVOKEINTERFACE(String type, String name, String methodDesc) {
    return new INVOKEINTERFACE(type, name, methodDesc);
  }

  default HxInstruction INVOKEDYNAMIC(String name, String methodDesc, HxHandle bootstrapMethod, Object... bsmArgs) {
    return new INVOKEDYNAMIC(name, methodDesc, bootstrapMethod, bsmArgs);
  }

  default HxInstruction NEW(String type) {
    return new NEW(type);
  }

  default HxInstruction NEWARRAY(HxArrayType type) {
    return new NEWARRAY(type);
  }

  default HxInstruction ANEWARRAY(String type) {
    return new ANEWARRAY(type);
  }

  default HxInstruction ARRAYLENGTH() {
    return new ARRAYLENGTH();
  }

  default HxInstruction ATHROW() {
    return new ATHROW();
  }

  default HxInstruction CHECKCAST(String type) {
    return new CHECKCAST(type);
  }

  default HxInstruction INSTANCEOF(String type) {
    return new INSTANCEOF(type);
  }

  default HxInstruction MONITORENTER() {
    return new MONITORENTER();
  }

  default HxInstruction MONITOREXIT() {
    return new MONITOREXIT();
  }

  default HxInstruction MULTIANEWARRAY(String type, int dims) {
    return new MULTIANEWARRAY(type, dims);
  }

  default HxInstruction IFNULL(LABEL label) {
    return new IFNULL(label);
  }

  default HxInstruction IFNONNULL(LABEL label) {
    return new IFNONNULL(label);
  }

  default HxInstruction FRAME(final HxFrames type, final int nLocal, final Object[] local, final int nStack,
                              final Object[] stack) {
    return new FRAME(type, nLocal, local, nStack, stack);
  }

  default HxInstruction LINE_NUMBER(int line, LABEL label) {
    return new LINE_NUMBER(line, label);
  }

  default HxInstruction LABEL(LABEL label) {
    return new LABEL(label.getAsmLabel());
  }
}