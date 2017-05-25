package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public interface Instructions {
   static InstructionType of(int opcode) {
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

   //----------------------------------------------------------------------------------------------------------------

   enum Kind {
      Access,
      Allocation,
      Arithmetic,
      Array,
      Binary,
      Comparison,
      Conversion,
      Constants,
      Increment,
      Invocation,
      Jump,
      Switches,
      Load,
      Store,
      Stack,
      Misc,
      Synchronization,
      Exit
   }

   enum Access implements InstructionType {
      GETSTATIC(Opcodes.GETSTATIC),
      PUTSTATIC(Opcodes.PUTSTATIC),
      GETFIELD(Opcodes.GETFIELD),
      PUTFIELD(Opcodes.PUTFIELD);

      private final int opcode;

      Access(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Access;
      }

      public boolean isPut() {
         return this.opcode == Opcodes.PUTSTATIC ||
                this.opcode == Opcodes.PUTFIELD;
      }

      public boolean isGet() {
         return !isPut();
      }

      public boolean isStatic() {
         return this.opcode == Opcodes.PUTSTATIC ||
                this.opcode == Opcodes.GETSTATIC;
      }
   }

   enum Allocation implements InstructionType {
      NEW(Opcodes.NEW),

      NEWARRAY(Opcodes.NEWARRAY),
      ANEWARRAY(Opcodes.ANEWARRAY),
      MULTIANEWARRAY(Opcodes.MULTIANEWARRAY);

      private final int opcode;

      Allocation(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Allocation;
      }

      public boolean isArray() {
         return this != NEW;
      }
   }

   enum Arithmetic implements InstructionType {
      IADD(Opcodes.IADD),
      LADD(Opcodes.LADD),
      FADD(Opcodes.FADD),
      DADD(Opcodes.DADD),
      ISUB(Opcodes.ISUB),
      LSUB(Opcodes.LSUB),
      FSUB(Opcodes.FSUB),
      DSUB(Opcodes.DSUB),
      IMUL(Opcodes.IMUL),
      LMUL(Opcodes.LMUL),
      FMUL(Opcodes.FMUL),
      DMUL(Opcodes.DMUL),
      IDIV(Opcodes.IDIV),
      LDIV(Opcodes.LDIV),
      FDIV(Opcodes.FDIV),
      DDIV(Opcodes.DDIV),
      IREM(Opcodes.IREM),
      LREM(Opcodes.LREM),
      FREM(Opcodes.FREM),
      DREM(Opcodes.DREM),
      INEG(Opcodes.INEG),
      LNEG(Opcodes.LNEG),
      FNEG(Opcodes.FNEG),
      DNEG(Opcodes.DNEG);

      private final int opcode;

      Arithmetic(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Arithmetic;
      }
   }

   enum Array implements InstructionType {
      IALOAD(Opcodes.IALOAD),
      LALOAD(Opcodes.LALOAD),
      FALOAD(Opcodes.FALOAD),
      DALOAD(Opcodes.DALOAD),
      AALOAD(Opcodes.AALOAD),
      BALOAD(Opcodes.BALOAD),
      CALOAD(Opcodes.CALOAD),
      SALOAD(Opcodes.SALOAD),

      IASTORE(Opcodes.IASTORE),
      LASTORE(Opcodes.LASTORE),
      FASTORE(Opcodes.FASTORE),
      DASTORE(Opcodes.DASTORE),
      AASTORE(Opcodes.AASTORE),
      BASTORE(Opcodes.BASTORE),
      CASTORE(Opcodes.CASTORE),
      SASTORE(Opcodes.SASTORE),

      ARRAYLENGTH(Opcodes.ARRAYLENGTH);

      private final int opcode;

      Array(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Array;
      }

      public boolean isStore() {
         return this != ARRAYLENGTH && !isLoad();
      }

      public boolean isLoad() {
         return this.opcode >= Opcodes.IALOAD &&
                this.opcode <= Opcodes.SALOAD;
      }
   }

   enum Binary implements InstructionType {
      ISHL(Opcodes.ISHL),
      LSHL(Opcodes.LSHL),
      ISHR(Opcodes.ISHR),
      LSHR(Opcodes.LSHR),
      IUSHR(Opcodes.IUSHR),
      LUSHR(Opcodes.LUSHR),
      IAND(Opcodes.IAND),
      LAND(Opcodes.LAND),
      IOR(Opcodes.IOR),
      LOR(Opcodes.LOR),
      IXOR(Opcodes.IXOR),
      LXOR(Opcodes.LXOR);

      private final int opcode;

      Binary(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Binary;
      }
   }

   enum Comparison implements InstructionType {
      LCMP(Opcodes.LCMP),
      FCMPL(Opcodes.FCMPL),
      FCMPG(Opcodes.FCMPG),
      DCMPL(Opcodes.DCMPL),
      DCMPG(Opcodes.DCMPG);

      private final int opcode;

      Comparison(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Comparison;
      }
   }

   enum Conversion implements InstructionType {
      I2L(Opcodes.I2L),
      I2F(Opcodes.I2F),
      I2D(Opcodes.I2D),
      L2I(Opcodes.L2I),
      L2F(Opcodes.L2F),
      L2D(Opcodes.L2D),
      F2I(Opcodes.F2I),
      F2L(Opcodes.F2L),
      F2D(Opcodes.F2D),
      D2I(Opcodes.D2I),
      D2L(Opcodes.D2L),
      D2F(Opcodes.D2F),
      I2B(Opcodes.I2B),
      I2C(Opcodes.I2C),
      I2S(Opcodes.I2S),

      CHECKCAST(Opcodes.CHECKCAST),
      INSTANCEOF(Opcodes.INSTANCEOF);

      private final int opcode;

      Conversion(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Conversion;
      }
   }

   enum Constants implements InstructionType {
      ACONST_NULL(Opcodes.ACONST_NULL),
      ICONST_M1(Opcodes.ICONST_M1),
      ICONST_0(Opcodes.ICONST_0),
      ICONST_1(Opcodes.ICONST_1),
      ICONST_2(Opcodes.ICONST_2),
      ICONST_3(Opcodes.ICONST_3),
      ICONST_4(Opcodes.ICONST_4),
      ICONST_5(Opcodes.ICONST_5),
      LCONST_0(Opcodes.LCONST_0),
      LCONST_1(Opcodes.LCONST_1),
      FCONST_0(Opcodes.FCONST_0),
      FCONST_1(Opcodes.FCONST_1),
      FCONST_2(Opcodes.FCONST_2),
      DCONST_0(Opcodes.DCONST_0),
      DCONST_1(Opcodes.DCONST_1),
      BIPUSH(Opcodes.BIPUSH),
      SIPUSH(Opcodes.SIPUSH),
      LDC(Opcodes.LDC);

      private final int opcode;

      Constants(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Constants;
      }
   }

   enum Increment implements InstructionType {
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
      public Kind getKind() {
         return Kind.Increment;
      }
   }

   enum Invocation implements InstructionType {
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
      public Kind getKind() {
         return Kind.Invocation;
      }
   }

   enum Jump implements InstructionType {
      IFEQ(Opcodes.IFEQ),
      IFNE(Opcodes.IFNE),
      IFLT(Opcodes.IFLT),
      IFGE(Opcodes.IFGE),
      IFGT(Opcodes.IFGT),
      IFLE(Opcodes.IFLE),

      IF_ICMPEQ(Opcodes.IF_ICMPEQ),
      IF_ICMPNE(Opcodes.IF_ICMPNE),
      IF_ICMPLT(Opcodes.IF_ICMPLT),
      IF_ICMPGE(Opcodes.IF_ICMPGE),
      IF_ICMPGT(Opcodes.IF_ICMPGT),
      IF_ICMPLE(Opcodes.IF_ICMPLE),
      IF_ACMPEQ(Opcodes.IF_ACMPEQ),
      IF_ACMPNE(Opcodes.IF_ACMPNE),

      GOTO(Opcodes.GOTO),

      IFNULL(Opcodes.IFNULL),
      IFNONNULL(Opcodes.IFNONNULL);

      private final int opcode;

      Jump(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Jump;
      }
   }

   enum Switches implements InstructionType {
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
      public Kind getKind() {
         return Kind.Switches;
      }
   }

   enum Load implements InstructionType {
      ILOAD(Opcodes.ILOAD),
      LLOAD(Opcodes.LLOAD),
      FLOAD(Opcodes.FLOAD),
      DLOAD(Opcodes.DLOAD),
      ALOAD(Opcodes.ALOAD);

      private final int opcode;

      Load(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Load;
      }
   }

   enum Store implements InstructionType {
      ISTORE(Opcodes.ISTORE),
      LSTORE(Opcodes.LSTORE),
      FSTORE(Opcodes.FSTORE),
      DSTORE(Opcodes.DSTORE),
      ASTORE(Opcodes.ASTORE);

      private final int opcode;

      Store(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Store;
      }
   }

   enum Misc implements InstructionType {
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
      public Kind getKind() {
         return Kind.Misc;
      }
   }

   enum Stack implements InstructionType {
      POP(Opcodes.POP),
      POP2(Opcodes.POP2),
      DUP(Opcodes.DUP),
      DUP_X1(Opcodes.DUP_X1),
      DUP_X2(Opcodes.DUP_X2),
      DUP2(Opcodes.DUP2),
      DUP2_X1(Opcodes.DUP2_X1),
      DUP2_X2(Opcodes.DUP2_X2),
      SWAP(Opcodes.SWAP);

      private final int opcode;

      Stack(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Stack;
      }
   }

   enum Synchronization implements InstructionType {
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
      public Kind getKind() {
         return Kind.Synchronization;
      }
   }

   //----------------------------------------------------------------------------------------------------------------

   enum Exit implements InstructionType {
      IRETURN(Opcodes.IRETURN),
      LRETURN(Opcodes.LRETURN),
      FRETURN(Opcodes.FRETURN),
      DRETURN(Opcodes.DRETURN),
      ARETURN(Opcodes.ARETURN),
      RETURN(Opcodes.RETURN),
      ATHROW(Opcodes.ATHROW);

      private final int opcode;

      Exit(int opcode) {
         this.opcode = opcode;
      }

      @Override
      public int getOpcode() {
         return this.opcode;
      }

      @Override
      public Kind getKind() {
         return Kind.Exit;
      }
   }
}
