package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxArguments;
import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxHandleTag;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 04:42.
 */
public class AsmCodeMethodVisitor
  extends MethodVisitor {

  protected final Hx haxxor;
  protected final HxCodeStream codeStream;

  public AsmCodeMethodVisitor(final Hx haxxor,
                              final HxCodeStream codeStream) {
    this(haxxor, codeStream, null);
  }

  public AsmCodeMethodVisitor(final Hx haxxor,
                              final HxCodeStream codeStream,
                              final MethodVisitor mv) {
    super(Opcodes.ASM5, mv);

    this.haxxor = haxxor;
    this.codeStream = codeStream;
  }

  protected Hx getHaxxor() {
    return haxxor;
  }

  @Override
  public void visitAttribute(final Attribute attr) {
    super.visitAttribute(attr);
  }

  @Override
  public void visitCode() {
    super.visitCode();
  }

  protected LABEL[] remap(Label... labels) {
    LABEL[] array = new LABEL[labels.length];
    for (int i = 0; i < labels.length; i++) {
      array[i] = remap(labels[i]);
    }
    return array;
  }

  /**
   * @param label
   * @return
   */
  protected LABEL remap(Label label) {
    Object info = label.info;
    if (info == null) {
      LABEL remapped = new LABEL(label);
      label.info = remapped;
      return remapped;
    }
    return (LABEL) info;
  }

  @Override
  public void visitFrame(int type,
                         int nLocal,
                         Object[] local,
                         int nStack,
                         Object[] stack) {
    super.visitFrame(type, nLocal, local, nStack, stack);
    this.codeStream.FRAME(HxFrames.fromCode(type), nLocal, local, nStack, stack);
  }

  @Override
  public void visitInsn(int opcode) {
    super.visitInsn(opcode);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.NOP: {
        cs.NOP();
      }
      break;
      case Opcodes.ACONST_NULL: {
        cs.ACONST_NULL();
      }
      break;
      case Opcodes.ICONST_M1: {
        cs.ICONST_M1();
      }
      break;
      case Opcodes.ICONST_0: {
        cs.ICONST_0();
      }
      break;
      case Opcodes.ICONST_1: {
        cs.ICONST_1();
      }
      break;
      case Opcodes.ICONST_2: {
        cs.ICONST_2();
      }
      break;
      case Opcodes.ICONST_3: {
        cs.ICONST_3();
      }
      break;
      case Opcodes.ICONST_4: {
        cs.ICONST_4();
      }
      break;
      case Opcodes.ICONST_5: {
        cs.ICONST_5();
      }
      break;
      case Opcodes.LCONST_0: {
        cs.LCONST_0();
      }
      break;
      case Opcodes.LCONST_1: {
        cs.LCONST_1();
      }
      break;
      case Opcodes.FCONST_0: {
        cs.FCONST_0();
      }
      break;
      case Opcodes.FCONST_1: {
        cs.FCONST_1();
      }
      break;
      case Opcodes.FCONST_2: {
        cs.FCONST_2();
      }
      break;
      case Opcodes.DCONST_0: {
        cs.DCONST_0();
      }
      break;
      case Opcodes.DCONST_1: {
        cs.DCONST_1();
      }
      break;
      case Opcodes.IALOAD: {
        cs.IALOAD();
      }
      break;
      case Opcodes.LALOAD: {
        cs.LALOAD();
      }
      break;
      case Opcodes.FALOAD: {
        cs.FALOAD();
      }
      break;
      case Opcodes.DALOAD: {
        cs.DALOAD();
      }
      break;
      case Opcodes.AALOAD: {
        cs.AALOAD();
      }
      break;
      case Opcodes.BALOAD: {
        cs.BALOAD();
      }
      break;
      case Opcodes.CALOAD: {
        cs.CALOAD();
      }
      break;
      case Opcodes.SALOAD: {
        cs.SALOAD();
      }
      break;
      case Opcodes.IASTORE: {
        cs.IASTORE();
      }
      break;
      case Opcodes.LASTORE: {
        cs.LASTORE();
      }
      break;
      case Opcodes.FASTORE: {
        cs.FASTORE();
      }
      break;
      case Opcodes.DASTORE: {
        cs.DASTORE();
      }
      break;
      case Opcodes.AASTORE: {
        cs.AASTORE();
      }
      break;
      case Opcodes.BASTORE: {
        cs.BASTORE();
      }
      break;
      case Opcodes.CASTORE: {
        cs.CASTORE();
      }
      break;
      case Opcodes.SASTORE: {
        cs.SASTORE();
      }
      break;
      case Opcodes.POP: {
        cs.POP();
      }
      break;
      case Opcodes.POP2: {
        cs.POP2();
      }
      break;
      case Opcodes.DUP: {
        cs.DUP();
      }
      break;
      case Opcodes.DUP_X1: {
        cs.DUP_X1();
      }
      break;
      case Opcodes.DUP_X2: {
        cs.DUP_X2();
      }
      break;
      case Opcodes.DUP2: {
        cs.DUP2();
      }
      break;
      case Opcodes.DUP2_X1: {
        cs.DUP2_X1();
      }
      break;
      case Opcodes.DUP2_X2: {
        cs.DUP2_X2();
      }
      break;
      case Opcodes.SWAP: {
        cs.SWAP();
      }
      break;
      case Opcodes.IADD: {
        cs.IADD();
      }
      break;
      case Opcodes.LADD: {
        cs.LADD();
      }
      break;
      case Opcodes.FADD: {
        cs.FADD();
      }
      break;
      case Opcodes.DADD: {
        cs.DADD();
      }
      break;
      case Opcodes.ISUB: {
        cs.ISUB();
      }
      break;
      case Opcodes.LSUB: {
        cs.LSUB();
      }
      break;
      case Opcodes.FSUB: {
        cs.FSUB();
      }
      break;
      case Opcodes.DSUB: {
        cs.DSUB();
      }
      break;
      case Opcodes.IMUL: {
        cs.IMUL();
      }
      break;
      case Opcodes.LMUL: {
        cs.LMUL();
      }
      break;
      case Opcodes.FMUL: {
        cs.FMUL();
      }
      break;
      case Opcodes.DMUL: {
        cs.DMUL();
      }
      break;
      case Opcodes.IDIV: {
        cs.IDIV();
      }
      break;
      case Opcodes.LDIV: {
        cs.LDIV();
      }
      break;
      case Opcodes.FDIV: {
        cs.FDIV();
      }
      break;
      case Opcodes.DDIV: {
        cs.DDIV();
      }
      break;
      case Opcodes.IREM: {
        cs.IREM();
      }
      break;
      case Opcodes.LREM: {
        cs.LREM();
      }
      break;
      case Opcodes.FREM: {
        cs.FREM();
      }
      break;
      case Opcodes.DREM: {
        cs.DREM();
      }
      break;
      case Opcodes.INEG: {
        cs.INEG();
      }
      break;
      case Opcodes.LNEG: {
        cs.LNEG();
      }
      break;
      case Opcodes.FNEG: {
        cs.FNEG();
      }
      break;
      case Opcodes.DNEG: {
        cs.DNEG();
      }
      break;
      case Opcodes.ISHL: {
        cs.ISHL();
      }
      break;
      case Opcodes.LSHL: {
        cs.LSHL();
      }
      break;
      case Opcodes.ISHR: {
        cs.ISHR();
      }
      break;
      case Opcodes.LSHR: {
        cs.LSHR();
      }
      break;
      case Opcodes.IUSHR: {
        cs.IUSHR();
      }
      break;
      case Opcodes.LUSHR: {
        cs.LUSHR();
      }
      break;
      case Opcodes.IAND: {
        cs.IAND();
      }
      break;
      case Opcodes.LAND: {
        cs.LAND();
      }
      break;
      case Opcodes.IOR: {
        cs.IOR();
      }
      break;
      case Opcodes.LOR: {
        cs.LOR();
      }
      break;
      case Opcodes.IXOR: {
        cs.IXOR();
      }
      break;
      case Opcodes.LXOR: {
        cs.LXOR();
      }
      break;
      case Opcodes.I2L: {
        cs.I2L();
      }
      break;
      case Opcodes.I2F: {
        cs.I2F();
      }
      break;
      case Opcodes.I2D: {
        cs.I2D();
      }
      break;
      case Opcodes.L2I: {
        cs.L2I();
      }
      break;
      case Opcodes.L2F: {
        cs.L2F();
      }
      break;
      case Opcodes.L2D: {
        cs.L2D();
      }
      break;
      case Opcodes.F2I: {
        cs.F2I();
      }
      break;
      case Opcodes.F2L: {
        cs.F2L();
      }
      break;
      case Opcodes.F2D: {
        cs.F2D();
      }
      break;
      case Opcodes.D2I: {
        cs.D2I();
      }
      break;
      case Opcodes.D2L: {
        cs.D2L();
      }
      break;
      case Opcodes.D2F: {
        cs.D2F();
      }
      break;
      case Opcodes.I2B: {
        cs.I2B();
      }
      break;
      case Opcodes.I2C: {
        cs.I2C();
      }
      break;
      case Opcodes.I2S: {
        cs.I2S();
      }
      break;
      case Opcodes.LCMP: {
        cs.LCMP();
      }
      break;
      case Opcodes.FCMPL: {
        cs.FCMPL();
      }
      break;
      case Opcodes.FCMPG: {
        cs.FCMPG();
      }
      break;
      case Opcodes.DCMPL: {
        cs.DCMPL();
      }
      break;
      case Opcodes.DCMPG: {
        cs.DCMPG();
      }
      break;
      case Opcodes.IRETURN: {
        cs.IRETURN();
      }
      break;
      case Opcodes.LRETURN: {
        cs.LRETURN();
      }
      break;
      case Opcodes.FRETURN: {
        cs.FRETURN();
      }
      break;
      case Opcodes.DRETURN: {
        cs.DRETURN();
      }
      break;
      case Opcodes.ARETURN: {
        cs.ARETURN();
      }
      break;
      case Opcodes.RETURN: {
        cs.RETURN();
      }
      break;
      case Opcodes.ARRAYLENGTH: {
        cs.ARRAYLENGTH();
      }
      break;
      case Opcodes.ATHROW: {
        cs.ATHROW();
      }
      break;
      case Opcodes.MONITORENTER: {
        cs.MONITORENTER();
      }
      break;
      case Opcodes.MONITOREXIT: {
        cs.MONITOREXIT();
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitIntInsn(int opcode,
                           int operand) {
    super.visitIntInsn(opcode, operand);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.BIPUSH: {
        cs.BIPUSH((byte) operand);
      }
      break;
      case Opcodes.SIPUSH: {
        cs.SIPUSH((short) operand);
      }
      break;
      case Opcodes.NEWARRAY: {
        cs.NEWARRAY(HxArrayType.fromCode(operand));
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitVarInsn(int opcode,
                           int var) {
    super.visitVarInsn(opcode, var);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.ILOAD: {
        cs.ILOAD(var);
      }
      break;
      case Opcodes.LLOAD: {
        cs.LLOAD(var);
      }
      break;
      case Opcodes.FLOAD: {
        cs.FLOAD(var);
      }
      break;
      case Opcodes.DLOAD: {
        cs.DLOAD(var);
      }
      break;
      case Opcodes.ALOAD: {
        cs.ALOAD(var);
      }
      break;
      case Opcodes.ISTORE: {
        cs.ISTORE(var);
      }
      break;
      case Opcodes.LSTORE: {
        cs.LSTORE(var);
      }
      break;
      case Opcodes.FSTORE: {
        cs.FSTORE(var);
      }
      break;
      case Opcodes.DSTORE: {
        cs.DSTORE(var);
      }
      break;
      case Opcodes.ASTORE: {
        cs.ASTORE(var);
      }
      break;
      case Opcodes.RET: {
        cs.RET(var);
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitTypeInsn(int opcode,
                            String type) {
    super.visitTypeInsn(opcode, type);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.NEW: {
        cs.NEW(type);
      }
      break;
      case Opcodes.ANEWARRAY: {
        cs.ANEWARRAY(type);
      }
      break;
      case Opcodes.CHECKCAST: {
        cs.CHECKCAST(type);
      }
      break;
      case Opcodes.INSTANCEOF: {
        cs.INSTANCEOF(type);
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitFieldInsn(int opcode,
                             String owner,
                             String name,
                             String desc) {
    super.visitFieldInsn(opcode, owner, name, desc);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.GETSTATIC: {
        cs.GETSTATIC(owner, name, desc);
      }
      break;
      case Opcodes.PUTSTATIC: {
        cs.PUTSTATIC(owner, name, desc);
      }
      break;
      case Opcodes.GETFIELD: {
        cs.GETFIELD(owner, name, desc);
      }
      break;
      case Opcodes.PUTFIELD: {
        cs.PUTFIELD(owner, name, desc);
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitMethodInsn(int opcode,
                              String owner,
                              String name,
                              String desc) {
    super.visitMethodInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitMethodInsn(int opcode,
                              String owner,
                              String name,
                              String desc,
                              boolean itf) {
    super.visitMethodInsn(opcode, owner, name, desc, itf);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.INVOKEVIRTUAL: {
        cs.INVOKEVIRTUAL(owner, name, desc);
      }
      break;
      case Opcodes.INVOKESPECIAL: {
        cs.INVOKESPECIAL(owner, name, desc);
      }
      break;
      case Opcodes.INVOKESTATIC: {
        cs.INVOKESTATIC(owner, name, desc, itf);
      }
      break;
      case Opcodes.INVOKEINTERFACE: {
        cs.INVOKEINTERFACE(owner, name, desc);
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitInvokeDynamicInsn(String name,
                                     String desc,
                                     Handle bsm,
                                     Object... bsmArgs) {
    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);

    this.codeStream.INVOKEDYNAMIC(name, desc, toHxHandle(bsm), toHxArguments(bsmArgs));
  }

  private HxType toHxType(Type type) {
    return haxxor.reference(type.getDescriptor());
  }

  private HxMethodHandle toHxHandle(Handle h) {
    return new HxMethodHandle(HxHandleTag.fromCode(h.getTag()), h.getOwner(), h.getName(), h.getDesc(),
                              h.isInterface());
  }

  private HxMethodType toHxMethodType(Type methodType) {
    return new HxMethodType(methodType.getDescriptor());
  }

  private HxArguments toHxArguments(Object[] bsmArgs) {
    HxArguments arguments = HxArguments.createArguments();
    for (int i = 0; i < bsmArgs.length; i++) {
      Object arg = bsmArgs[i];
      if (arg instanceof Type) {
        Type type = (Type) arg;
        if (type.getSort() == Type.METHOD) {
          arguments.add(toHxMethodType(type));
        } else {
          arguments.add(toHxType(type));
        }
      } else if (arg instanceof Handle) {
        arguments.add(toHxHandle((Handle) arg));
      } else if (arg instanceof String) {
        arguments.add((String) arg);
      } else if (arg instanceof Number) {
        if (arg instanceof Integer) {
          arguments.add((Integer) arg);
        } else if (arg instanceof Float) {
          arguments.add((Float) arg);
        } else if (arg instanceof Long) {
          arguments.add((Long) arg);
        } else if (arg instanceof Double) {
          arguments.add((Double) arg);
        }
      }
    }
    return arguments;
  }

  @Override
  public void visitJumpInsn(int opcode,
                            Label label) {
    super.visitJumpInsn(opcode, label);
    final HxCodeStream cs = this.codeStream;
    switch (opcode) {
      case Opcodes.IFEQ: {
        cs.IFEQ(remap(label));
      }
      break;
      case Opcodes.IFNE: {
        cs.IFNE(remap(label));
      }
      break;
      case Opcodes.IFLT: {
        cs.IFLT(remap(label));
      }
      break;
      case Opcodes.IFGE: {
        cs.IFGE(remap(label));
      }
      break;
      case Opcodes.IFGT: {
        cs.IFGT(remap(label));
      }
      break;
      case Opcodes.IFLE: {
        cs.IFLE(remap(label));
      }
      break;
      case Opcodes.IF_ICMPEQ: {
        cs.IF_ICMPEQ(remap(label));
      }
      break;
      case Opcodes.IF_ICMPNE: {
        cs.IF_ICMPNE(remap(label));
      }
      break;
      case Opcodes.IF_ICMPLT: {
        cs.IF_ICMPLT(remap(label));
      }
      break;
      case Opcodes.IF_ICMPGE: {
        cs.IF_ICMPGE(remap(label));
      }
      break;
      case Opcodes.IF_ICMPGT: {
        cs.IF_ICMPGT(remap(label));
      }
      break;
      case Opcodes.IF_ICMPLE: {
        cs.IF_ICMPLE(remap(label));
      }
      break;
      case Opcodes.IF_ACMPEQ: {
        cs.IF_ACMPEQ(remap(label));
      }
      break;
      case Opcodes.IF_ACMPNE: {
        cs.IF_ACMPNE(remap(label));
      }
      break;
      case Opcodes.GOTO: {
        cs.GOTO(remap(label));
      }
      break;
      case Opcodes.JSR: {
        cs.JSR(remap(label));
      }
      break;
      case Opcodes.IFNULL: {
        cs.IFNULL(remap(label));
      }
      break;
      case Opcodes.IFNONNULL: {
        cs.IFNONNULL(remap(label));
      }
      break;
      default: {
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }
  }

  @Override
  public void visitLabel(Label label) {
    super.visitLabel(label);
    this.codeStream.LABEL(remap(label));
  }

  @Override
  public void visitLdcInsn(Object cst) {
    super.visitLdcInsn(cst);
    final HxCodeStream cs = this.codeStream;
    if (cst instanceof Integer) {
      cs.LDC((Integer) cst);
    } else if (cst instanceof Float) {
      cs.LDC((Float) cst);
    } else if (cst instanceof Long) {
      cs.LDC((Long) cst);
    } else if (cst instanceof Double) {
      cs.LDC((Double) cst);
    } else if (cst instanceof String) {
      cs.LDC((String) cst);
    } else if (cst instanceof Type) {
      Type type = (Type) cst;
      int sort = type.getSort();
      if (sort == Type.OBJECT || sort == Type.ARRAY) {
//        cs.TYPE(sort == Type.OBJECT ? type.getInternalName() : type.getDescriptor());
        cs.TYPE(type.getDescriptor());
      } else if (sort == Type.METHOD) {
        cs.METHOD(new HxMethodType(type.getDescriptor()));
      } else {
        throw new IllegalArgumentException("Invalid constant: " + cst);
      }
    } else if (cst instanceof Handle) {
      cs.HANDLE(toHxHandle((Handle) cst));
    } else {
      throw new IllegalArgumentException("Invalid constant: " + cst);
    }
  }

  @Override
  public void visitIincInsn(int var,
                            int increment) {
    super.visitIincInsn(var, increment);
    this.codeStream.IINC(var, increment);
  }

  @Override
  public void visitTableSwitchInsn(int min,
                                   int max,
                                   Label dflt,
                                   Label... labels) {
    super.visitTableSwitchInsn(min, max, dflt, labels);
    this.codeStream.TABLESWITCH(min, max, remap(dflt), remap(labels));
  }

  @Override
  public void visitLookupSwitchInsn(Label dflt,
                                    int[] keys,
                                    Label[] labels) {
    super.visitLookupSwitchInsn(dflt, keys, labels);
    this.codeStream.LOOKUPSWITCH(remap(dflt), keys, remap(labels));
  }

  @Override
  public void visitMultiANewArrayInsn(String desc,
                                      int dims) {
    super.visitMultiANewArrayInsn(desc, dims);
    this.codeStream.MULTIANEWARRAY(desc, dims);
  }

  @Override
  public void visitLineNumber(int line,
                              Label start) {
    super.visitLineNumber(line, start);
    this.codeStream.LINE_NUMBER(line, remap(start));
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
