package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import static net.andreho.haxxor.cgen.HxCodeGenerationUtils.getArgumentsSize;
import static net.andreho.haxxor.cgen.HxCodeGenerationUtils.getReturnSize;
import static net.andreho.haxxor.cgen.HxCodeGenerationUtils.getTypeSize;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public interface HxInstructions {

  enum Access
      implements HxInstructionType {
    GETSTATIC(Opcodes.GETSTATIC),
    PUTSTATIC(Opcodes.PUTSTATIC),
    GETFIELD(Opcodes.GETFIELD),
    PUTFIELD(Opcodes.PUTFIELD);

    private final int opcode;
    private final boolean isStatic;

    Access(int opcode) {
      this.opcode = opcode;
      this.isStatic = this.opcode == Opcodes.PUTSTATIC || this.opcode == Opcodes.GETSTATIC;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Access;
    }

    @Override
    public int getPopSize() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getPushSize() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getPopSize(final String desc) {
      return (isStatic ? 0 : 1) + getTypeSize(desc);
    }

    @Override
    public int getPushSize(final String desc) {
      return isGet() ? getTypeSize(desc) : 0;
    }

    public boolean isPut() {
      return this.opcode == Opcodes.PUTSTATIC || this.opcode == Opcodes.PUTFIELD;
    }

    public boolean isGet() {
      return !isPut();
    }

    public boolean isStatic() {
      return this.opcode == Opcodes.PUTSTATIC || this.opcode == Opcodes.GETSTATIC;
    }
  }

  //----------------------------------------------------------------------------------------------------------------

  enum Allocation
      implements HxInstructionType {
    NEW(Opcodes.NEW, 0, 1),

    NEWARRAY(Opcodes.NEWARRAY, 1, 1),
    ANEWARRAY(Opcodes.ANEWARRAY, 1, 1),
    MULTIANEWARRAY(Opcodes.MULTIANEWARRAY, -1, 1);

    private final int opcode;
    private final int pop;
    private final int push;

    Allocation(int opcode,
               final int pop,
               final int push) {
      this.opcode = opcode;
      this.pop = pop;
      this.push = push;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Allocation;
    }

    public boolean isArray() {
      return this != NEW;
    }
  }

  enum Arithmetic
      implements HxInstructionType {
    IADD(Opcodes.IADD, 1 + 1, 1),
    LADD(Opcodes.LADD, 2 + 2, 2),
    FADD(Opcodes.FADD, 1 + 1, 1),
    DADD(Opcodes.DADD, 2 + 2, 2),
    ISUB(Opcodes.ISUB, 1 + 1, 1),
    LSUB(Opcodes.LSUB, 2 + 2, 2),
    FSUB(Opcodes.FSUB, 1 + 1, 1),
    DSUB(Opcodes.DSUB, 2 + 2, 2),
    IMUL(Opcodes.IMUL, 1 + 1, 1),
    LMUL(Opcodes.LMUL, 2 + 2, 2),
    FMUL(Opcodes.FMUL, 1 + 1, 1),
    DMUL(Opcodes.DMUL, 2 + 2, 2),
    IDIV(Opcodes.IDIV, 1 + 1, 1),
    LDIV(Opcodes.LDIV, 2 + 2, 2),
    FDIV(Opcodes.FDIV, 1 + 1, 1),
    DDIV(Opcodes.DDIV, 2 + 2, 2),
    IREM(Opcodes.IREM, 1 + 1, 1),
    LREM(Opcodes.LREM, 2 + 2, 1),
    FREM(Opcodes.FREM, 1 + 1, 1),
    DREM(Opcodes.DREM, 2 + 2, 1),
    INEG(Opcodes.INEG, 1 + 1, 1),
    LNEG(Opcodes.LNEG, 2 + 0, 2),
    FNEG(Opcodes.FNEG, 1 + 1, 1),
    DNEG(Opcodes.DNEG, 2 + 0, 2);

    private final int opcode;
    private final int pop;
    private final int push;

    Arithmetic(int opcode,
               final int pop,
               final int push) {
      this.opcode = opcode;
      this.pop = pop;
      this.push = push;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Arithmetic;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }
  }

  enum Array
      implements HxInstructionType {
    IALOAD(Opcodes.IALOAD, 1 + 1, 1),
    LALOAD(Opcodes.LALOAD, 1 + 1, 2),
    FALOAD(Opcodes.FALOAD, 1 + 1, 1),
    DALOAD(Opcodes.DALOAD, 1 + 1, 2),
    AALOAD(Opcodes.AALOAD, 1 + 1, 1),
    BALOAD(Opcodes.BALOAD, 1 + 1, 1),
    CALOAD(Opcodes.CALOAD, 1 + 1, 1),
    SALOAD(Opcodes.SALOAD, 1 + 1, 1),

    IASTORE(Opcodes.IASTORE, 1 + 1 + 1, 0),
    LASTORE(Opcodes.LASTORE, 1 + 1 + 2, 0),
    FASTORE(Opcodes.FASTORE, 1 + 1 + 1, 0),
    DASTORE(Opcodes.DASTORE, 1 + 1 + 2, 0),
    AASTORE(Opcodes.AASTORE, 1 + 1 + 1, 0),
    BASTORE(Opcodes.BASTORE, 1 + 1 + 1, 0),
    CASTORE(Opcodes.CASTORE, 1 + 1 + 1, 0),
    SASTORE(Opcodes.SASTORE, 1 + 1 + 1, 0),

    ARRAYLENGTH(Opcodes.ARRAYLENGTH, 1, 1);

    private final int opcode;
    private final int push;
    private final int pop;

    Array(int opcode,
          int pop,
          int push) {
      this.opcode = opcode;
      this.push = push;
      this.pop = pop;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Array;
    }

    public boolean isStore() {
      return this != ARRAYLENGTH && !isLoad();
    }

    public boolean isLoad() {
      return this.opcode >= Opcodes.IALOAD && this.opcode <= Opcodes.SALOAD;
    }
  }

  enum Binary
      implements HxInstructionType {
    ISHL(Opcodes.ISHL, 1 + 1, 1),
    LSHL(Opcodes.LSHL, 2 + 1, 2),
    ISHR(Opcodes.ISHR, 1 + 1, 1),
    LSHR(Opcodes.LSHR, 2 + 1, 2),
    IUSHR(Opcodes.IUSHR, 1 + 1, 1),
    LUSHR(Opcodes.LUSHR, 2 + 1, 2),
    IAND(Opcodes.IAND, 1 + 1, 1),
    LAND(Opcodes.LAND, 2 + 2, 2),
    IOR(Opcodes.IOR, 1 + 1, 1),
    LOR(Opcodes.LOR, 2 + 2, 2),
    IXOR(Opcodes.IXOR, 1 + 1, 1),
    LXOR(Opcodes.LXOR, 2 + 2, 2);

    private final int opcode;
    private final int push;
    private final int pop;

    Binary(int opcode,
           int pop,
           int push) {
      this.opcode = opcode;
      this.push = push;
      this.pop = pop;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Binary;
    }

    public boolean is32Bit() {
      return !is64Bit();
    }

    public boolean is64Bit() {
      return 'L' == name().charAt(0);
    }
  }

  enum Comparison
      implements HxInstructionType {
    LCMP(Opcodes.LCMP, 2 + 2, 1),
    FCMPL(Opcodes.FCMPL, 1 + 1, 1),
    FCMPG(Opcodes.FCMPG, 1 + 1, 1),
    DCMPL(Opcodes.DCMPL, 2 + 2, 1),
    DCMPG(Opcodes.DCMPG, 2 + 2, 1);

    private final int opcode;
    private final int push;
    private final int pop;

    Comparison(int opcode,
               final int pop,
               final int push) {
      this.opcode = opcode;
      this.push = push;
      this.pop = pop;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Comparison;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }
  }

  enum Conversion
      implements HxInstructionType {
    I2L(Opcodes.I2L, 1, 2),
    I2F(Opcodes.I2F, 1, 1),
    I2D(Opcodes.I2D, 1, 2),
    L2I(Opcodes.L2I, 2, 1),
    L2F(Opcodes.L2F, 2, 1),
    L2D(Opcodes.L2D, 2, 2),
    F2I(Opcodes.F2I, 1, 1),
    F2L(Opcodes.F2L, 1, 2),
    F2D(Opcodes.F2D, 1, 2),
    D2I(Opcodes.D2I, 2, 1),
    D2L(Opcodes.D2L, 2, 2),
    D2F(Opcodes.D2F, 2, 1),
    I2B(Opcodes.I2B, 1, 1),
    I2C(Opcodes.I2C, 1, 1),
    I2S(Opcodes.I2S, 1, 1),

    CHECKCAST(Opcodes.CHECKCAST, 1, 1),
    INSTANCEOF(Opcodes.INSTANCEOF, 1, 1);

    private final int opcode;
    private final int pop;
    private final int push;

    Conversion(int opcode,
               final int pop,
               final int push) {
      this.opcode = opcode;
      this.pop = pop;
      this.push = push;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Conversion;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }
  }

  enum Constants
      implements HxInstructionType {
    ACONST_NULL(Opcodes.ACONST_NULL, 1),
    ICONST_M1(Opcodes.ICONST_M1, 1),
    ICONST_0(Opcodes.ICONST_0, 1),
    ICONST_1(Opcodes.ICONST_1, 1),
    ICONST_2(Opcodes.ICONST_2, 1),
    ICONST_3(Opcodes.ICONST_3, 1),
    ICONST_4(Opcodes.ICONST_4, 1),
    ICONST_5(Opcodes.ICONST_5, 1),
    LCONST_0(Opcodes.LCONST_0, 2),
    LCONST_1(Opcodes.LCONST_1, 2),
    FCONST_0(Opcodes.FCONST_0, 1),
    FCONST_1(Opcodes.FCONST_1, 1),
    FCONST_2(Opcodes.FCONST_2, 1),
    DCONST_0(Opcodes.DCONST_0, 2),
    DCONST_1(Opcodes.DCONST_1, 2),
    BIPUSH(Opcodes.BIPUSH, 1),
    SIPUSH(Opcodes.SIPUSH, 1),
    LDC(Opcodes.LDC, 1);

    private final int opcode;
    private final int push;

    Constants(int opcode,
              final int push) {
      this.opcode = opcode;
      this.push = push;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Constants;
    }

    @Override
    public int getPopSize() {
      return 0;
    }

    @Override
    public int getPushSize() {
      return push;
    }
  }

  enum Increment
      implements HxInstructionType {
    IINC(Opcodes.IINC);

    private final int opcode;

    Increment(int opcode) {
      this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Increment;
    }

    @Override
    public int getPopSize() {
      return 0;
    }

    @Override
    public int getPushSize() {
      return 0;
    }
  }

  enum Invocation
      implements HxInstructionType {
    INVOKEVIRTUAL(Opcodes.INVOKEVIRTUAL),
    INVOKESPECIAL(Opcodes.INVOKESPECIAL),
    INVOKESTATIC(Opcodes.INVOKESTATIC),
    INVOKEINTERFACE(Opcodes.INVOKEINTERFACE),
    INVOKEDYNAMIC(Opcodes.INVOKEDYNAMIC);

    private final int opcode;

    Invocation(int opcode) {
      this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getPushSize() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getPopSize(final String desc) {
      return (this == INVOKESTATIC? 0 : 1) + getArgumentsSize(desc);
    }

    @Override
    public int getPushSize(final String desc) {
      return getReturnSize(desc);
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Invocation;
    }
  }

  enum Jump
      implements HxInstructionType {
    IFEQ(Opcodes.IFEQ, 1),
    IFNE(Opcodes.IFNE, 1),
    IFLT(Opcodes.IFLT, 1),
    IFGE(Opcodes.IFGE, 1),
    IFGT(Opcodes.IFGT, 1),
    IFLE(Opcodes.IFLE, 1),

    IF_ICMPEQ(Opcodes.IF_ICMPEQ, 1+1),
    IF_ICMPNE(Opcodes.IF_ICMPNE, 1+1),
    IF_ICMPLT(Opcodes.IF_ICMPLT, 1+1),
    IF_ICMPGE(Opcodes.IF_ICMPGE, 1+1),
    IF_ICMPGT(Opcodes.IF_ICMPGT, 1+1),
    IF_ICMPLE(Opcodes.IF_ICMPLE, 1+1),
    IF_ACMPEQ(Opcodes.IF_ACMPEQ, 1+1),
    IF_ACMPNE(Opcodes.IF_ACMPNE, 1+1),

    GOTO(Opcodes.GOTO, 0),

    IFNULL(Opcodes.IFNULL, 1),
    IFNONNULL(Opcodes.IFNONNULL, 1);

    private final int opcode;
    private final int pop;

    Jump(int opcode,
         final int pop) {
      this.opcode = opcode;
      this.pop = pop;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return 0;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Jump;
    }
  }

  enum Switches
      implements HxInstructionType {
    TABLESWITCH(Opcodes.TABLESWITCH),
    LOOKUPSWITCH(Opcodes.LOOKUPSWITCH);

    private final int opcode;

    Switches(int opcode) {
      this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return 1;
    }

    @Override
    public int getPushSize() {
      return 0;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Switches;
    }
  }

  enum Load
      implements HxInstructionType {
    ILOAD(Opcodes.ILOAD, 1),
    LLOAD(Opcodes.LLOAD, 2),
    FLOAD(Opcodes.FLOAD, 1),
    DLOAD(Opcodes.DLOAD, 2),
    ALOAD(Opcodes.ALOAD, 1);

    private final int opcode;
    private final int push;

    Load(int opcode,
         final int push) {
      this.opcode = opcode;
      this.push = push;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return 0;
    }

    @Override
    public int getPushSize() {
      return push;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Load;
    }
  }

  enum Store
      implements HxInstructionType {
    ISTORE(Opcodes.ISTORE, 1),
    LSTORE(Opcodes.LSTORE, 2),
    FSTORE(Opcodes.FSTORE, 1),
    DSTORE(Opcodes.DSTORE, 2),
    ASTORE(Opcodes.ASTORE, 1);

    private final int opcode;
    private final int pop;

    Store(int opcode,
          final int pop) {
      this.opcode = opcode;
      this.pop = pop;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return 0;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Store;
    }
  }

  enum Misc
      implements HxInstructionType {
    NOP(Opcodes.NOP),
    JSR(Opcodes.JSR),
    RET(Opcodes.RET);

    private final int opcode;

    Misc(int opcode) {
      this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return 0;
    }

    @Override
    public int getPushSize() {
      return this == JSR? 1 : 0;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Misc;
    }
  }

  enum Stack
      implements HxInstructionType {
    POP(Opcodes.POP, 1, 0),
    POP2(Opcodes.POP2, 2, 0),
    DUP(Opcodes.DUP, 1, 2),
    DUP_X1(Opcodes.DUP_X1, 2, 3),
    DUP_X2(Opcodes.DUP_X2, 3, 4),
    DUP2(Opcodes.DUP2, 2, 4),
    DUP2_X1(Opcodes.DUP2_X1, 3, 5),
    DUP2_X2(Opcodes.DUP2_X2, 4, 6),
    SWAP(Opcodes.SWAP, 0, 0);

    private final int opcode;
    private final int pop;
    private final int push;

    Stack(int opcode,
          final int pop,
          final int push) {
      this.opcode = opcode;
      this.pop = pop;
      this.push = push;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return push;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Stack;
    }
  }

  enum Synchronization
      implements HxInstructionType {
    MONITORENTER(Opcodes.MONITORENTER),
    MONITOREXIT(Opcodes.MONITOREXIT);

    private final int opcode;

    Synchronization(int opcode) {
      this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return 1;
    }

    @Override
    public int getPushSize() {
      return 0;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Synchronization;
    }
  }

  enum Exit
      implements HxInstructionType {
    IRETURN(Opcodes.IRETURN, 1),
    LRETURN(Opcodes.LRETURN, 2),
    FRETURN(Opcodes.FRETURN, 1),
    DRETURN(Opcodes.DRETURN, 2),
    ARETURN(Opcodes.ARETURN, 1),
    RETURN(Opcodes.RETURN, 0),
    ATHROW(Opcodes.ATHROW, 1);

    private final int opcode;
    private final int pop;

    Exit(int opcode,
         final int pop) {
      this.opcode = opcode;
      this.pop = pop;
    }

    @Override
    public int getOpcode() {
      return this.opcode;
    }

    @Override
    public int getPopSize() {
      return pop;
    }

    @Override
    public int getPushSize() {
      return 0;
    }

    @Override
    public HxInstructionKind getKind() {
      return HxInstructionKind.Exit;
    }
  }

  //----------------------------------------------------------------------------------------------------------------

  static HxInstructionType fromOpcode(int opcode) {
    //TODO: evaluate what is faster in that case a SWITCH or an ARRAY?
    switch (opcode) {
      case Opcodes.NOP:
        return Misc.NOP;
      case Opcodes.ACONST_NULL:
        return Constants.ACONST_NULL;
      case Opcodes.ICONST_M1:
        return Constants.ICONST_M1;
      case Opcodes.ICONST_0:
        return Constants.ICONST_0;
      case Opcodes.ICONST_1:
        return Constants.ICONST_1;
      case Opcodes.ICONST_2:
        return Constants.ICONST_2;
      case Opcodes.ICONST_3:
        return Constants.ICONST_3;
      case Opcodes.ICONST_4:
        return Constants.ICONST_4;
      case Opcodes.ICONST_5:
        return Constants.ICONST_5;
      case Opcodes.LCONST_0:
        return Constants.LCONST_0;
      case Opcodes.LCONST_1:
        return Constants.LCONST_1;
      case Opcodes.FCONST_0:
        return Constants.FCONST_0;
      case Opcodes.FCONST_1:
        return Constants.FCONST_1;
      case Opcodes.FCONST_2:
        return Constants.FCONST_2;
      case Opcodes.DCONST_0:
        return Constants.DCONST_0;
      case Opcodes.DCONST_1:
        return Constants.DCONST_1;
      case Opcodes.BIPUSH:
        return Constants.BIPUSH;
      case Opcodes.SIPUSH:
        return Constants.SIPUSH;
      case Opcodes.LDC:
        return Constants.LDC;
      case Opcodes.ILOAD:
        return Load.ILOAD;
      case Opcodes.LLOAD:
        return Load.LLOAD;
      case Opcodes.FLOAD:
        return Load.FLOAD;
      case Opcodes.DLOAD:
        return Load.DLOAD;
      case Opcodes.ALOAD:
        return Load.ALOAD;
      case Opcodes.IALOAD:
        return Array.IALOAD;
      case Opcodes.LALOAD:
        return Array.LALOAD;
      case Opcodes.FALOAD:
        return Array.FALOAD;
      case Opcodes.DALOAD:
        return Array.DALOAD;
      case Opcodes.AALOAD:
        return Array.AALOAD;
      case Opcodes.BALOAD:
        return Array.BALOAD;
      case Opcodes.CALOAD:
        return Array.CALOAD;
      case Opcodes.SALOAD:
        return Array.SALOAD;
      case Opcodes.ISTORE:
        return Store.ISTORE;
      case Opcodes.LSTORE:
        return Store.LSTORE;
      case Opcodes.FSTORE:
        return Store.FSTORE;
      case Opcodes.DSTORE:
        return Store.DSTORE;
      case Opcodes.ASTORE:
        return Store.ASTORE;
      case Opcodes.IASTORE:
        return Array.IASTORE;
      case Opcodes.LASTORE:
        return Array.LASTORE;
      case Opcodes.FASTORE:
        return Array.FASTORE;
      case Opcodes.DASTORE:
        return Array.DASTORE;
      case Opcodes.AASTORE:
        return Array.AASTORE;
      case Opcodes.BASTORE:
        return Array.BASTORE;
      case Opcodes.CASTORE:
        return Array.CASTORE;
      case Opcodes.SASTORE:
        return Array.SASTORE;
      case Opcodes.POP:
        return Stack.POP;
      case Opcodes.POP2:
        return Stack.POP2;
      case Opcodes.DUP:
        return Stack.DUP;
      case Opcodes.DUP_X1:
        return Stack.DUP_X1;
      case Opcodes.DUP_X2:
        return Stack.DUP_X2;
      case Opcodes.DUP2:
        return Stack.DUP2;
      case Opcodes.DUP2_X1:
        return Stack.DUP2_X1;
      case Opcodes.DUP2_X2:
        return Stack.DUP2_X2;
      case Opcodes.SWAP:
        return Stack.SWAP;
      case Opcodes.IADD:
        return Arithmetic.IADD;
      case Opcodes.LADD:
        return Arithmetic.LADD;
      case Opcodes.FADD:
        return Arithmetic.FADD;
      case Opcodes.DADD:
        return Arithmetic.DADD;
      case Opcodes.ISUB:
        return Arithmetic.ISUB;
      case Opcodes.LSUB:
        return Arithmetic.LSUB;
      case Opcodes.FSUB:
        return Arithmetic.FSUB;
      case Opcodes.DSUB:
        return Arithmetic.DSUB;
      case Opcodes.IMUL:
        return Arithmetic.IMUL;
      case Opcodes.LMUL:
        return Arithmetic.LMUL;
      case Opcodes.FMUL:
        return Arithmetic.FMUL;
      case Opcodes.DMUL:
        return Arithmetic.DMUL;
      case Opcodes.IDIV:
        return Arithmetic.IDIV;
      case Opcodes.LDIV:
        return Arithmetic.LDIV;
      case Opcodes.FDIV:
        return Arithmetic.FDIV;
      case Opcodes.DDIV:
        return Arithmetic.DDIV;
      case Opcodes.IREM:
        return Arithmetic.IREM;
      case Opcodes.LREM:
        return Arithmetic.LREM;
      case Opcodes.FREM:
        return Arithmetic.FREM;
      case Opcodes.DREM:
        return Arithmetic.DREM;
      case Opcodes.INEG:
        return Arithmetic.INEG;
      case Opcodes.LNEG:
        return Arithmetic.LNEG;
      case Opcodes.FNEG:
        return Arithmetic.FNEG;
      case Opcodes.DNEG:
        return Arithmetic.DNEG;
      case Opcodes.ISHL:
        return Binary.ISHL;
      case Opcodes.LSHL:
        return Binary.LSHL;
      case Opcodes.ISHR:
        return Binary.ISHR;
      case Opcodes.LSHR:
        return Binary.LSHR;
      case Opcodes.IUSHR:
        return Binary.IUSHR;
      case Opcodes.LUSHR:
        return Binary.LUSHR;
      case Opcodes.IAND:
        return Binary.IAND;
      case Opcodes.LAND:
        return Binary.LAND;
      case Opcodes.IOR:
        return Binary.IOR;
      case Opcodes.LOR:
        return Binary.LOR;
      case Opcodes.IXOR:
        return Binary.IXOR;
      case Opcodes.LXOR:
        return Binary.LXOR;
      case Opcodes.IINC:
        return Increment.IINC;
      case Opcodes.I2L:
        return Conversion.I2L;
      case Opcodes.I2F:
        return Conversion.I2F;
      case Opcodes.I2D:
        return Conversion.I2D;
      case Opcodes.L2I:
        return Conversion.L2I;
      case Opcodes.L2F:
        return Conversion.L2F;
      case Opcodes.L2D:
        return Conversion.L2D;
      case Opcodes.F2I:
        return Conversion.F2I;
      case Opcodes.F2L:
        return Conversion.F2L;
      case Opcodes.F2D:
        return Conversion.F2D;
      case Opcodes.D2I:
        return Conversion.D2I;
      case Opcodes.D2L:
        return Conversion.D2L;
      case Opcodes.D2F:
        return Conversion.D2F;
      case Opcodes.I2B:
        return Conversion.I2B;
      case Opcodes.I2C:
        return Conversion.I2C;
      case Opcodes.I2S:
        return Conversion.I2S;
      case Opcodes.LCMP:
        return Comparison.LCMP;
      case Opcodes.FCMPL:
        return Comparison.FCMPL;
      case Opcodes.FCMPG:
        return Comparison.FCMPG;
      case Opcodes.DCMPL:
        return Comparison.DCMPL;
      case Opcodes.DCMPG:
        return Comparison.DCMPG;
      case Opcodes.IFEQ:
        return Jump.IFEQ;
      case Opcodes.IFNE:
        return Jump.IFNE;
      case Opcodes.IFLT:
        return Jump.IFLT;
      case Opcodes.IFGE:
        return Jump.IFGE;
      case Opcodes.IFGT:
        return Jump.IFGT;
      case Opcodes.IFLE:
        return Jump.IFLE;
      case Opcodes.IF_ICMPEQ:
        return Jump.IF_ICMPEQ;
      case Opcodes.IF_ICMPNE:
        return Jump.IF_ICMPNE;
      case Opcodes.IF_ICMPLT:
        return Jump.IF_ICMPLT;
      case Opcodes.IF_ICMPGE:
        return Jump.IF_ICMPGE;
      case Opcodes.IF_ICMPGT:
        return Jump.IF_ICMPGT;
      case Opcodes.IF_ICMPLE:
        return Jump.IF_ICMPLE;
      case Opcodes.IF_ACMPEQ:
        return Jump.IF_ACMPEQ;
      case Opcodes.IF_ACMPNE:
        return Jump.IF_ACMPNE;
      case Opcodes.GOTO:
        return Jump.GOTO;
      case Opcodes.JSR:
        return Misc.JSR;
      case Opcodes.RET:
        return Misc.RET;
      case Opcodes.TABLESWITCH:
        return Switches.TABLESWITCH;
      case Opcodes.LOOKUPSWITCH:
        return Switches.LOOKUPSWITCH;
      case Opcodes.IRETURN:
        return Exit.IRETURN;
      case Opcodes.LRETURN:
        return Exit.LRETURN;
      case Opcodes.FRETURN:
        return Exit.FRETURN;
      case Opcodes.DRETURN:
        return Exit.DRETURN;
      case Opcodes.ARETURN:
        return Exit.ARETURN;
      case Opcodes.RETURN:
        return Exit.RETURN;
      case Opcodes.GETSTATIC:
        return Access.GETSTATIC;
      case Opcodes.PUTSTATIC:
        return Access.PUTSTATIC;
      case Opcodes.GETFIELD:
        return Access.GETFIELD;
      case Opcodes.PUTFIELD:
        return Access.PUTFIELD;
      case Opcodes.INVOKEVIRTUAL:
        return Invocation.INVOKEVIRTUAL;
      case Opcodes.INVOKESPECIAL:
        return Invocation.INVOKESPECIAL;
      case Opcodes.INVOKESTATIC:
        return Invocation.INVOKESTATIC;
      case Opcodes.INVOKEINTERFACE:
        return Invocation.INVOKEINTERFACE;
      case Opcodes.INVOKEDYNAMIC:
        return Invocation.INVOKEDYNAMIC;
      case Opcodes.NEW:
        return Allocation.NEW;
      case Opcodes.NEWARRAY:
        return Allocation.NEWARRAY;
      case Opcodes.ANEWARRAY:
        return Allocation.ANEWARRAY;
      case Opcodes.ARRAYLENGTH:
        return Array.ARRAYLENGTH;
      case Opcodes.ATHROW:
        return Exit.ATHROW;
      case Opcodes.CHECKCAST:
        return Conversion.CHECKCAST;
      case Opcodes.INSTANCEOF:
        return Conversion.INSTANCEOF;
      case Opcodes.MONITORENTER:
        return Synchronization.MONITORENTER;
      case Opcodes.MONITOREXIT:
        return Synchronization.MONITOREXIT;
      case Opcodes.MULTIANEWARRAY:
        return Allocation.MULTIANEWARRAY;
      case Opcodes.IFNULL:
        return Jump.IFNULL;
      case Opcodes.IFNONNULL:
        return Jump.IFNONNULL;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }
}
