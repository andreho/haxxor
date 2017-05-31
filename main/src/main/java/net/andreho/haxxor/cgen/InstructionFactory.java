package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.CodeStream.ArrayType;
import net.andreho.haxxor.cgen.CodeStream.Frames;
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

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public interface InstructionFactory {

  default Instruction BEGIN() {
    return new BEGIN();
  }

  default Instruction END() {
    return new END();
  }

  default Instruction NOP() {
    return new NOP();
  }

  default Instruction ACONST_NULL() {
    return new ACONST_NULL();
  }

  default Instruction ICONST(int value) {
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

  default Instruction LCONST(long value) {
    if (value == 0L) {
      return new LCONST_0();
    } else if (value == 1L) {
      return new LCONST_1();
    }
    throw new IllegalArgumentException("Unsupported parameter: " + value);
  }

  default Instruction FCONST(float value) {
    if (value == 0f) {
      return new FCONST_0();
    } else if (value == 1f) {
      return new FCONST_1();
    } else if (value == 2f) {
      return new FCONST_2();
    }
    throw new IllegalArgumentException("Unsupported parameter: " + value);
  }

  default Instruction DCONST(double value) {
    if (value == 0d) {
      return new DCONST_0();
    } else if (value == 1d) {
      return new DCONST_1();
    }
    throw new IllegalArgumentException("Unsupported parameter: " + value);
  }

  default Instruction BIPUSH(byte value) {
    return new BIPUSH(value);
  }

  default Instruction SIPUSH(short value) {
    return new SIPUSH(value);
  }

  default Instruction LDC(int value) {
    return new LDC(value);
  }

  default Instruction LDC(float value) {
    return new LDC(value);
  }

  default Instruction LDC(long value) {
    return new LDC(value);
  }

  default Instruction LDC(double value) {
    return new LDC(value);
  }

  default Instruction LDC(String value) {
    return new LDC(value);
  }

  default Instruction HANDLE(Handle value) {
    return new LDC(value);
  }

  default Instruction METHOD(String value) {
    return new LDC(Type.getType(value), LDC.METHOD);
  }

  default Instruction TYPE(String value) {
    return new LDC(Type.getType(value), LDC.TYPE);
  }

  default Instruction ILOAD(int var) {
    return new ILOAD(var);
  }

  default Instruction LLOAD(int var) {
    return new LLOAD(var);
  }

  default Instruction FLOAD(int var) {
    return new FLOAD(var);
  }

  default Instruction DLOAD(int var) {
    return new DLOAD(var);
  }

  default Instruction ALOAD(int var) {
    return new ALOAD(var);
  }

  default Instruction IALOAD() {
    return new IALOAD();
  }

  default Instruction LALOAD() {
    return new LALOAD();
  }

  default Instruction FALOAD() {
    return new FALOAD();
  }

  default Instruction DALOAD() {
    return new DALOAD();
  }

  default Instruction AALOAD() {
    return new AALOAD();
  }

  default Instruction BALOAD() {
    return new BALOAD();
  }

  default Instruction CALOAD() {
    return new CALOAD();
  }

  default Instruction SALOAD() {
    return new SALOAD();
  }

  default Instruction ISTORE(int var) {
    return new ISTORE(var);
  }

  default Instruction LSTORE(int var) {
    return new LSTORE(var);
  }

  default Instruction FSTORE(int var) {
    return new FSTORE(var);
  }

  default Instruction DSTORE(int var) {
    return new DSTORE(var);
  }

  default Instruction ASTORE(int var) {
    return new ASTORE(var);
  }

  default Instruction IASTORE() {
    return new IASTORE();
  }

  default Instruction LASTORE() {
    return new LASTORE();
  }

  default Instruction FASTORE() {
    return new FASTORE();
  }

  default Instruction DASTORE() {
    return new DASTORE();
  }

  default Instruction AASTORE() {
    return new AASTORE();
  }

  default Instruction BASTORE() {
    return new BASTORE();
  }

  default Instruction CASTORE() {
    return new CASTORE();
  }

  default Instruction SASTORE() {
    return new SASTORE();
  }

  default Instruction POP() {
    return new POP();
  }

  default Instruction POP2() {
    return new POP2();
  }

  default Instruction DUP() {
    return new DUP();
  }

  default Instruction DUP_X1() {
    return new DUP_X1();
  }

  default Instruction DUP_X2() {
    return new DUP_X2();
  }

  default Instruction DUP2() {
    return new DUP2();
  }

  default Instruction DUP2_X1() {
    return new DUP2_X1();
  }

  default Instruction DUP2_X2() {
    return new DUP2_X2();
  }

  default Instruction SWAP() {
    return new SWAP();
  }

  default Instruction IADD() {
    return new IADD();
  }

  default Instruction LADD() {
    return new LADD();
  }

  default Instruction FADD() {
    return new FADD();
  }

  default Instruction DADD() {
    return new DADD();
  }

  default Instruction ISUB() {
    return new ISUB();
  }

  default Instruction LSUB() {
    return new LSUB();
  }

  default Instruction FSUB() {
    return new FSUB();
  }

  default Instruction DSUB() {
    return new DSUB();
  }

  default Instruction IMUL() {
    return new IMUL();
  }

  default Instruction LMUL() {
    return new LMUL();
  }

  default Instruction FMUL() {
    return new FMUL();
  }

  default Instruction DMUL() {
    return new DMUL();
  }

  default Instruction IDIV() {
    return new IDIV();
  }

  default Instruction LDIV() {
    return new LDIV();
  }

  default Instruction FDIV() {
    return new FDIV();
  }

  default Instruction DDIV() {
    return new DDIV();
  }

  default Instruction IREM() {
    return new IREM();
  }

  default Instruction LREM() {
    return new LREM();
  }

  default Instruction FREM() {
    return new FREM();
  }

  default Instruction DREM() {
    return new DREM();
  }

  default Instruction INEG() {
    return new INEG();
  }

  default Instruction LNEG() {
    return new LNEG();
  }

  default Instruction FNEG() {
    return new FNEG();
  }

  default Instruction DNEG() {
    return new DNEG();
  }

  default Instruction ISHL() {
    return new ISHL();
  }

  default Instruction LSHL() {
    return new LSHL();
  }

  default Instruction ISHR() {
    return new ISHR();
  }

  default Instruction LSHR() {
    return new LSHR();
  }

  default Instruction IUSHR() {
    return new IUSHR();
  }

  default Instruction LUSHR() {
    return new LUSHR();
  }

  default Instruction IAND() {
    return new IAND();
  }

  default Instruction LAND() {
    return new LAND();
  }

  default Instruction IOR() {
    return new IOR();
  }

  default Instruction LOR() {
    return new LOR();
  }

  default Instruction IXOR() {
    return new IXOR();
  }

  default Instruction LXOR() {
    return new LXOR();
  }

  default Instruction IINC(int var, int increment) {
    return new IINC(var, increment);
  }

  default Instruction I2L() {
    return new I2L();
  }

  default Instruction I2F() {
    return new I2F();
  }

  default Instruction I2D() {
    return new I2D();
  }

  default Instruction L2I() {
    return new L2I();
  }

  default Instruction L2F() {
    return new L2F();
  }

  default Instruction L2D() {
    return new L2D();
  }

  default Instruction F2I() {
    return new F2I();
  }

  default Instruction F2L() {
    return new F2L();
  }

  default Instruction F2D() {
    return new F2D();
  }

  default Instruction D2I() {
    return new D2I();
  }

  default Instruction D2L() {
    return new D2L();
  }

  default Instruction D2F() {
    return new D2F();
  }

  default Instruction I2B() {
    return new I2B();
  }

  default Instruction I2C() {
    return new I2C();
  }

  default Instruction I2S() {
    return new I2S();
  }

  default Instruction LCMP() {
    return new LCMP();
  }

  default Instruction FCMPL() {
    return new FCMPL();
  }

  default Instruction FCMPG() {
    return new FCMPG();
  }

  default Instruction DCMPL() {
    return new DCMPL();
  }

  default Instruction DCMPG() {
    return new DCMPG();
  }

  default Instruction IFEQ(LABEL label) {
    return new IFEQ(label);
  }

  default Instruction IFNE(LABEL label) {
    return new IFNE(label);
  }

  default Instruction IFLT(LABEL label) {
    return new IFLT(label);
  }

  default Instruction IFGE(LABEL label) {
    return new IFGE(label);
  }

  default Instruction IFGT(LABEL label) {
    return new IFGT(label);
  }

  default Instruction IFLE(LABEL label) {
    return new IFLE(label);
  }

  default Instruction IF_ICMPEQ(LABEL label) {
    return new IF_ICMPEQ(label);
  }

  default Instruction IF_ICMPNE(LABEL label) {
    return new IF_ICMPNE(label);
  }

  default Instruction IF_ICMPLT(LABEL label) {
    return new IF_ICMPLT(label);
  }

  default Instruction IF_ICMPGE(LABEL label) {
    return new IF_ICMPGE(label);
  }

  default Instruction IF_ICMPGT(LABEL label) {
    return new IF_ICMPGT(label);
  }

  default Instruction IF_ICMPLE(LABEL label) {
    return new IF_ICMPLE(label);
  }

  default Instruction IF_ACMPEQ(LABEL label) {
    return new IF_ACMPEQ(label);
  }

  default Instruction IF_ACMPNE(LABEL label) {
    return new IF_ACMPNE(label);
  }

  default Instruction GOTO(LABEL label) {
    return new GOTO(label);
  }

  default Instruction JSR(LABEL label) {
    return new JSR(label);
  }

  default Instruction RET(int var) {
    return new RET(var);
  }

  default Instruction TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels) {
    return new TABLESWITCH(min, max, defaultLabel, labels);
  }

  default Instruction LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL... labels) {
    return new LOOKUPSWITCH(defaultLabel, keys, labels);
  }

  default Instruction IRETURN() {
    return new IRETURN();
  }

  default Instruction LRETURN() {
    return new LRETURN();
  }

  default Instruction FRETURN() {
    return new FRETURN();
  }

  default Instruction DRETURN() {
    return new DRETURN();
  }

  default Instruction ARETURN() {
    return new ARETURN();
  }

  default Instruction RETURN() {
    return new RETURN();
  }

  default Instruction GETSTATIC(String type, String name, String fieldDesc) {
    return new GETSTATIC(type, name, fieldDesc);
  }

  default Instruction PUTSTATIC(String type, String name, String fieldDesc) {
    return new PUTSTATIC(type, name, fieldDesc);
  }

  default Instruction GETFIELD(String type, String name, String fieldDesc) {
    return new GETFIELD(type, name, fieldDesc);
  }

  default Instruction PUTFIELD(String type, String name, String fieldDesc) {
    return new PUTFIELD(type, name, fieldDesc);
  }

  default Instruction INVOKEVIRTUAL(String type, String name, String methodDesc) {
    return new INVOKEVIRTUAL(type, name, methodDesc);
  }

  default Instruction INVOKESPECIAL(String type, String name, String methodDesc) {
    return new INVOKESPECIAL(type, name, methodDesc);
  }

  default Instruction INVOKESTATIC(String type, String name, String methodDesc, boolean isInterface) {
    return new INVOKESTATIC(type, name, methodDesc, isInterface);
  }

  default Instruction INVOKEINTERFACE(String type, String name, String methodDesc) {
    return new INVOKEINTERFACE(type, name, methodDesc);
  }

  default Instruction INVOKEDYNAMIC(String name, String methodDesc, Handle bootstrapMethod, Object... bsmArgs) {
    return new INVOKEDYNAMIC(name, methodDesc, bootstrapMethod, bsmArgs);
  }

  default Instruction NEW(String type) {
    return new NEW(type);
  }

  default Instruction NEWARRAY(ArrayType type) {
    return new NEWARRAY(type);
  }

  default Instruction ANEWARRAY(String type) {
    return new ANEWARRAY(type);
  }

  default Instruction ARRAYLENGTH() {
    return new ARRAYLENGTH();
  }

  default Instruction ATHROW() {
    return new ATHROW();
  }

  default Instruction CHECKCAST(String type) {
    return new CHECKCAST(type);
  }

  default Instruction INSTANCEOF(String type) {
    return new INSTANCEOF(type);
  }

  default Instruction MONITORENTER() {
    return new MONITORENTER();
  }

  default Instruction MONITOREXIT() {
    return new MONITOREXIT();
  }

  default Instruction MULTIANEWARRAY(String type, int dims) {
    return new MULTIANEWARRAY(type, dims);
  }

  default Instruction IFNULL(LABEL label) {
    return new IFNULL(label);
  }

  default Instruction IFNONNULL(LABEL label) {
    return new IFNONNULL(label);
  }

  default Instruction FRAME(final Frames type, final int nLocal, final Object[] local, final int nStack,
                            final Object[] stack) {
    return new FRAME(type, nLocal, local, nStack, stack);
  }

  default Instruction LINE_NUMBER(int line, LABEL label) {
    return new LINE_NUMBER(line, label);
  }

  default Instruction LABEL(LABEL label) {
    return new LABEL(label.getAsmLabel());
  }
}