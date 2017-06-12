package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.spec.api.HxCode;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 05:01.
 */
public class CodeStreamMatcher extends MethodVisitor {

  private final HxCode code;
  private final Iterator<HxInstruction> iterator;

  public CodeStreamMatcher(final HxCode code) {
    this(code, null);
  }

  public CodeStreamMatcher(final HxCode code, final MethodVisitor mv) {
    super(Opcodes.ASM5, mv);
    this.code = code;
    this.iterator = code.iterator();
  }

  private HxInstruction visitSingleInstruction() {
    return iterator.next();
  }

  @Override
  public void visitCode() {
    super.visitCode();
    HxInstruction hxInstruction = visitSingleInstruction();
    assertTrue(hxInstruction.isBegin());
    assertTrue(hxInstruction.getOpcode() == -1);
  }

  @Override
  public void visitFrame(final int type,
                         final int nLocal,
                         final Object[] local,
                         final int nStack,
                         final Object[] stack) {
    super.visitFrame(type, nLocal, local, nStack, stack);
  }

  @Override
  public void visitInsn(final int opcode) {
    super.visitInsn(opcode);

    switch (opcode) {
      case Opcodes.NOP:
      case Opcodes.ACONST_NULL:
      case Opcodes.ICONST_M1:
      case Opcodes.ICONST_0:
      case Opcodes.ICONST_1:
      case Opcodes.ICONST_2:
      case Opcodes.ICONST_3:
      case Opcodes.ICONST_4:
      case Opcodes.ICONST_5:
      case Opcodes.LCONST_0:
      case Opcodes.LCONST_1:
      case Opcodes.FCONST_0:
      case Opcodes.FCONST_1:
      case Opcodes.FCONST_2:
      case Opcodes.DCONST_0:
      case Opcodes.DCONST_1:
      case Opcodes.IALOAD:
      case Opcodes.LALOAD:
      case Opcodes.FALOAD:
      case Opcodes.DALOAD:
      case Opcodes.AALOAD:
      case Opcodes.BALOAD:
      case Opcodes.CALOAD:
      case Opcodes.SALOAD:
      case Opcodes.IASTORE:
      case Opcodes.LASTORE:
      case Opcodes.FASTORE:
      case Opcodes.DASTORE:
      case Opcodes.AASTORE:
      case Opcodes.BASTORE:
      case Opcodes.CASTORE:
      case Opcodes.SASTORE:
      case Opcodes.POP:
      case Opcodes.POP2:
      case Opcodes.DUP:
      case Opcodes.DUP_X1:
      case Opcodes.DUP_X2:
      case Opcodes.DUP2:
      case Opcodes.DUP2_X1:
      case Opcodes.DUP2_X2:
      case Opcodes.SWAP:
      case Opcodes.IADD:
      case Opcodes.LADD:
      case Opcodes.FADD:
      case Opcodes.DADD:
      case Opcodes.ISUB:
      case Opcodes.LSUB:
      case Opcodes.FSUB:
      case Opcodes.DSUB:
      case Opcodes.IMUL:
      case Opcodes.LMUL:
      case Opcodes.FMUL:
      case Opcodes.DMUL:
      case Opcodes.IDIV:
      case Opcodes.LDIV:
      case Opcodes.FDIV:
      case Opcodes.DDIV:
      case Opcodes.IREM:
      case Opcodes.LREM:
      case Opcodes.FREM:
      case Opcodes.DREM:
      case Opcodes.INEG:
      case Opcodes.LNEG:
      case Opcodes.FNEG:
      case Opcodes.DNEG:
      case Opcodes.ISHL:
      case Opcodes.LSHL:
      case Opcodes.ISHR:
      case Opcodes.LSHR:
      case Opcodes.IUSHR:
      case Opcodes.LUSHR:
      case Opcodes.IAND:
      case Opcodes.LAND:
      case Opcodes.IOR:
      case Opcodes.LOR:
      case Opcodes.IXOR:
      case Opcodes.LXOR:
      case Opcodes.I2L:
      case Opcodes.I2F:
      case Opcodes.I2D:
      case Opcodes.L2I:
      case Opcodes.L2F:
      case Opcodes.L2D:
      case Opcodes.F2I:
      case Opcodes.F2L:
      case Opcodes.F2D:
      case Opcodes.D2I:
      case Opcodes.D2L:
      case Opcodes.D2F:
      case Opcodes.I2B:
      case Opcodes.I2C:
      case Opcodes.I2S:
      case Opcodes.LCMP:
      case Opcodes.FCMPL:
      case Opcodes.FCMPG:
      case Opcodes.DCMPL:
      case Opcodes.DCMPG:
      case Opcodes.IRETURN:
      case Opcodes.LRETURN:
      case Opcodes.FRETURN:
      case Opcodes.DRETURN:
      case Opcodes.ARETURN:
      case Opcodes.RETURN:
      case Opcodes.ARRAYLENGTH:
      case Opcodes.ATHROW:
      case Opcodes.MONITORENTER:
      case Opcodes.MONITOREXIT:
        HxInstruction last = visitSingleInstruction();
        if(last.getOpcode() != opcode) {
          System.out.println();
        }

        assertEquals(opcode, last.getOpcode(),
                     "Last instruction has unexpected opcode: " + last + "#" + last.getOpcode() + "; " + HxInstructions.fromOpcode(opcode));
        assertEquals(HxInstructions.fromOpcode(opcode), last.getInstructionType());

        break;
        default: throw new IllegalStateException();
    }
  }

  @Override
  public void visitIntInsn(final int opcode,
                           final int operand) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitIntInsn(opcode, operand);
  }

  @Override
  public void visitVarInsn(final int opcode,
                           final int var) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitVarInsn(opcode, var);
  }

  @Override
  public void visitTypeInsn(final int opcode,
                            final String type) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitTypeInsn(opcode, type);
  }

  @Override
  public void visitFieldInsn(final int opcode,
                             final String owner,
                             final String name,
                             final String desc) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitFieldInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitMethodInsn(final int opcode,
                              final String owner,
                              final String name,
                              final String desc) {
    super.visitMethodInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitMethodInsn(final int opcode,
                              final String owner,
                              final String name,
                              final String desc,
                              final boolean itf) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitMethodInsn(opcode, owner, name, desc, itf);
  }

  @Override
  public void visitInvokeDynamicInsn(final String name,
                                     final String desc,
                                     final net.andreho.asm.org.objectweb.asm.Handle bsm,
                                     final Object... bsmArgs) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
  }

  @Override
  public void visitJumpInsn(final int opcode,
                            final Label label) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitJumpInsn(opcode, label);
  }

  @Override
  public void visitLabel(final Label label) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitLabel(label);
  }

  @Override
  public void visitLdcInsn(final Object cst) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitLdcInsn(cst);
  }

  @Override
  public void visitIincInsn(final int var,
                            final int increment) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitIincInsn(var, increment);
  }

  @Override
  public void visitTableSwitchInsn(final int min,
                                   final int max,
                                   final Label dflt,
                                   final Label... labels) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitTableSwitchInsn(min, max, dflt, labels);
  }

  @Override
  public void visitLookupSwitchInsn(final Label dflt,
                                    final int[] keys,
                                    final Label[] labels) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitLookupSwitchInsn(dflt, keys, labels);
  }

  @Override
  public void visitMultiANewArrayInsn(final String desc,
                                      final int dims) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitMultiANewArrayInsn(desc, dims);
  }

  @Override
  public AnnotationVisitor visitInsnAnnotation(final int typeRef,
                                               final TypePath typePath,
                                               final String desc,
                                               final boolean visible) {
    return super.visitInsnAnnotation(typeRef, typePath, desc, visible);
  }

  @Override
  public void visitTryCatchBlock(final Label start,
                                 final Label end,
                                 final Label handler,
                                 final String type) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitTryCatchBlock(start, end, handler, type);
  }

  @Override
  public AnnotationVisitor visitTryCatchAnnotation(final int typeRef,
                                                   final TypePath typePath,
                                                   final String desc,
                                                   final boolean visible) {
    return super.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
  }

  @Override
  public void visitLocalVariable(final String name,
                                 final String desc,
                                 final String signature,
                                 final Label start,
                                 final Label end,
                                 final int index) {
    super.visitLocalVariable(name, desc, signature, start, end, index);
  }

  @Override
  public AnnotationVisitor visitLocalVariableAnnotation(final int typeRef,
                                                        final TypePath typePath,
                                                        final Label[] start,
                                                        final Label[] end,
                                                        final int[] index,
                                                        final String desc,
                                                        final boolean visible) {
    return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
  }

  @Override
  public void visitLineNumber(final int line,
                              final Label start) {
    super.visitLineNumber(line, start);
    HxInstruction hxInstruction = visitSingleInstruction();
    assertTrue(hxInstruction instanceof LINE_NUMBER);
    LINE_NUMBER lineNumber = (LINE_NUMBER) hxInstruction;
    assertEquals(line, lineNumber.getLine());
  }

  @Override
  public void visitMaxs(final int maxStack,
                        final int maxLocals) {
    super.visitMaxs(maxStack, maxLocals);
    assertEquals(maxStack, code.getMaxStack());
    assertEquals(maxLocals, code.getMaxLocals());
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    HxInstruction hxInstruction = visitSingleInstruction();
    assertTrue(hxInstruction.isEnd());
    assertTrue(hxInstruction.getOpcode() == -1);
  }
}
