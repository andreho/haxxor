package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.cgen.instr.abstr.FieldInstruction;
import net.andreho.haxxor.cgen.instr.abstr.InvokeInstruction;
import net.andreho.haxxor.cgen.instr.abstr.SingleOperandInstruction;
import net.andreho.haxxor.cgen.instr.abstr.StringOperandInstruction;
import net.andreho.haxxor.cgen.instr.invokes.INVOKEDYNAMIC;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 05:01.
 */
public class CodeStreamMatcher extends MethodVisitor {

  private final HxMethodBody code;
  private final Iterator<HxInstruction> iterator;

  public CodeStreamMatcher(final HxMethodBody code) {
    this(code, null);
  }

  public CodeStreamMatcher(final HxMethodBody code, final MethodVisitor mv) {
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
      checkOpcode(opcode, visitSingleInstruction());
  }

  private void checkOpcode(final int opcode,
                           final HxInstruction hxInstruction) {
    assertEquals(opcode, hxInstruction.getOpcode(),
                 "Current instruction has an unexpected opcode: " + hxInstruction + "#" + hxInstruction.getOpcode() + "; " + HxInstructionTypes

                   .fromOpcode(opcode));
    assertEquals(HxInstructionTypes.fromOpcode(opcode), hxInstruction.getInstructionType());
    assertTrue(hxInstruction.hasOpcode(opcode));
  }

  @Override
  public void visitIntInsn(final int opcode,
                           final int operand) {
    super.visitIntInsn(opcode, operand);

    HxInstruction hxInstruction = visitSingleInstruction();
    checkOpcode(opcode, hxInstruction);

    SingleOperandInstruction instruction = (SingleOperandInstruction) hxInstruction;
    assertEquals(operand, instruction.getOperand());
  }

  @Override
  public void visitVarInsn(final int opcode,
                           final int var) {
    super.visitVarInsn(opcode, var);
    HxInstruction hxInstruction = visitSingleInstruction();
    checkOpcode(opcode, hxInstruction);

    SingleOperandInstruction instruction = (SingleOperandInstruction) hxInstruction;
    assertEquals(var, instruction.getOperand());
  }

  @Override
  public void visitTypeInsn(final int opcode,
                            final String type) {
    super.visitTypeInsn(opcode, type);

    HxInstruction hxInstruction = visitSingleInstruction();
    checkOpcode(opcode, hxInstruction);

    StringOperandInstruction instruction = (StringOperandInstruction) hxInstruction;
    assertEquals(type, instruction.getOperand());
  }

  @Override
  public void visitFieldInsn(final int opcode,
                             final String owner,
                             final String name,
                             final String desc) {
    super.visitFieldInsn(opcode, owner, name, desc);

    HxInstruction hxInstruction = visitSingleInstruction();
    checkOpcode(opcode, hxInstruction);

    FieldInstruction instruction = (FieldInstruction) hxInstruction;
    assertEquals(owner, instruction.getOwner());
    assertEquals(name, instruction.getName());
    assertEquals(desc, instruction.getDescriptor());
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
    super.visitMethodInsn(opcode, owner, name, desc, itf);
    HxInstruction hxInstruction = visitSingleInstruction();
    checkOpcode(opcode, hxInstruction);

    InvokeInstruction instruction = (InvokeInstruction) hxInstruction;
    assertEquals(owner, instruction.getOwner());
    assertEquals(name, instruction.getName());
    assertEquals(desc, instruction.getDescriptor());
    assertEquals(itf, instruction.isInterface());
  }

  @Override
  public void visitInvokeDynamicInsn(final String name,
                                     final String desc,
                                     final Handle bsm,
                                     final Object... bsmArgs) {
    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
    HxInstruction hxInstruction = visitSingleInstruction();
    checkOpcode(Opcodes.INVOKEDYNAMIC, hxInstruction);

    INVOKEDYNAMIC instruction = (INVOKEDYNAMIC) hxInstruction;
    assertEquals(name, instruction.getName());
    assertEquals(desc, instruction.getDescriptor());
    assertEquals(bsm, instruction.getBootstrapMethod());
  }

  @Override
  public void visitJumpInsn(final int opcode,
                            final Label label) {
    HxInstruction hxInstruction = visitSingleInstruction();
    super.visitJumpInsn(opcode, label);
    checkOpcode(opcode, hxInstruction);
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
