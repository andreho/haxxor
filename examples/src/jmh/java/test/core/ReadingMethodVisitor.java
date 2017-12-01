package test.core;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.openjdk.jmh.infra.Blackhole;

/**
 * <br/>Created by a.hofmann on 24.11.2017 at 01:06.
 */
class ReadingMethodVisitor
  extends MethodVisitor {

  private final Blackhole blackhole;

  public ReadingMethodVisitor(final Blackhole blackhole) {
    super(Opcodes.ASM5);
    this.blackhole = blackhole;
  }

  public ReadingMethodVisitor(final Blackhole blackhole,
                              final MethodVisitor mv) {
    super(Opcodes.ASM5, mv);
    this.blackhole = blackhole;
  }

  @Override
  public void visitParameter(final String name,
                             final int access) {
    blackhole.consume(name);
    blackhole.consume(access);
    super.visitParameter(name, access);
  }

  @Override
  public AnnotationVisitor visitAnnotationDefault() {
    return new ReadingAnnotationVisitor(blackhole, super.visitAnnotationDefault());
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc,
                                           final boolean visible) {
    blackhole.consume(desc);
    blackhole.consume(visible);

    return new ReadingAnnotationVisitor(blackhole, super.visitAnnotationDefault());
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(final int typeRef,
                                               final TypePath typePath,
                                               final String desc,
                                               final boolean visible) {
    blackhole.consume(typeRef);
    blackhole.consume(typePath);
    blackhole.consume(desc);
    blackhole.consume(visible);

    return new ReadingAnnotationVisitor(blackhole, super.visitTypeAnnotation(typeRef, typePath, desc, visible));
  }

  @Override
  public AnnotationVisitor visitParameterAnnotation(final int parameter,
                                                    final String desc,
                                                    final boolean visible) {
    blackhole.consume(parameter);
    blackhole.consume(desc);
    blackhole.consume(visible);

    return new ReadingAnnotationVisitor(blackhole, super.visitParameterAnnotation(parameter, desc, visible));
  }

  @Override
  public void visitAttribute(final Attribute attr) {
    blackhole.consume(attr);
    super.visitAttribute(attr);
  }

  @Override
  public void visitCode() {
    super.visitCode();
  }

  @Override
  public void visitFrame(final int type,
                         final int nLocal,
                         final Object[] local,
                         final int nStack,
                         final Object[] stack) {
    blackhole.consume(type);
    blackhole.consume(nLocal);
    blackhole.consume(local);
    blackhole.consume(nStack);
    blackhole.consume(stack);

    super.visitFrame(type, nLocal, local, nStack, stack);
  }

  @Override
  public void visitInsn(final int opcode) {
    blackhole.consume(opcode);
    super.visitInsn(opcode);
  }

  @Override
  public void visitIntInsn(final int opcode,
                           final int operand) {
    blackhole.consume(opcode);
    blackhole.consume(operand);
    super.visitIntInsn(opcode, operand);
  }

  @Override
  public void visitVarInsn(final int opcode,
                           final int var) {
    blackhole.consume(opcode);
    blackhole.consume(var);
    super.visitVarInsn(opcode, var);
  }

  @Override
  public void visitTypeInsn(final int opcode,
                            final String type) {
    blackhole.consume(opcode);
    blackhole.consume(type);
    super.visitTypeInsn(opcode, type);
  }

  @Override
  public void visitFieldInsn(final int opcode,
                             final String owner,
                             final String name,
                             final String desc) {
    blackhole.consume(opcode);
    blackhole.consume(owner);
    blackhole.consume(name);
    blackhole.consume(desc);
    super.visitFieldInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitMethodInsn(final int opcode,
                              final String owner,
                              final String name,
                              final String desc,
                              final boolean itf) {
    blackhole.consume(opcode);
    blackhole.consume(owner);
    blackhole.consume(name);
    blackhole.consume(desc);
    blackhole.consume(itf);
    super.visitMethodInsn(opcode, owner, name, desc, itf);
  }

  @Override
  public void visitInvokeDynamicInsn(final String name,
                                     final String desc,
                                     final Handle bsm,
                                     final Object... bsmArgs) {
    blackhole.consume(name);
    blackhole.consume(desc);
    blackhole.consume(bsm);
    blackhole.consume(bsmArgs);
    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
  }

  @Override
  public void visitJumpInsn(final int opcode,
                            final Label label) {
    blackhole.consume(opcode);
    blackhole.consume(label);
    super.visitJumpInsn(opcode, label);
  }

  @Override
  public void visitLabel(final Label label) {
    blackhole.consume(label);
    super.visitLabel(label);
  }

  @Override
  public void visitLdcInsn(final Object cst) {
    blackhole.consume(cst);
    super.visitLdcInsn(cst);
  }

  @Override
  public void visitIincInsn(final int var,
                            final int increment) {
    blackhole.consume(var);
    blackhole.consume(increment);
    super.visitIincInsn(var, increment);
  }

  @Override
  public void visitTableSwitchInsn(final int min,
                                   final int max,
                                   final Label dflt,
                                   final Label... labels) {
    blackhole.consume(min);
    blackhole.consume(max);
    blackhole.consume(dflt);
    blackhole.consume(labels);
    super.visitTableSwitchInsn(min, max, dflt, labels);
  }

  @Override
  public void visitLookupSwitchInsn(final Label dflt,
                                    final int[] keys,
                                    final Label[] labels) {
    blackhole.consume(dflt);
    blackhole.consume(keys);
    blackhole.consume(labels);
    super.visitLookupSwitchInsn(dflt, keys, labels);
  }

  @Override
  public void visitMultiANewArrayInsn(final String desc,
                                      final int dims) {
    blackhole.consume(desc);
    blackhole.consume(dims);

    super.visitMultiANewArrayInsn(desc, dims);
  }

  @Override
  public AnnotationVisitor visitInsnAnnotation(final int typeRef,
                                               final TypePath typePath,
                                               final String desc,
                                               final boolean visible) {

    blackhole.consume(typeRef);
    blackhole.consume(typePath);
    blackhole.consume(desc);
    blackhole.consume(visible);

    return new ReadingAnnotationVisitor(blackhole, super.visitInsnAnnotation(typeRef, typePath, desc, visible));
  }

  @Override
  public void visitTryCatchBlock(final Label start,
                                 final Label end,
                                 final Label handler,
                                 final String type) {
    super.visitTryCatchBlock(start, end, handler, type);
  }

  @Override
  public AnnotationVisitor visitTryCatchAnnotation(final int typeRef,
                                                   final TypePath typePath,
                                                   final String desc,
                                                   final boolean visible) {
    blackhole.consume(typeRef);
    blackhole.consume(typePath);
    blackhole.consume(desc);
    blackhole.consume(visible);

    return new ReadingAnnotationVisitor(blackhole, super.visitTryCatchAnnotation(typeRef, typePath, desc, visible));
  }

  @Override
  public void visitLocalVariable(final String name,
                                 final String desc,
                                 final String signature,
                                 final Label start,
                                 final Label end,
                                 final int index) {
    blackhole.consume(name);
    blackhole.consume(desc);
    blackhole.consume(signature);
    blackhole.consume(start);
    blackhole.consume(end);
    blackhole.consume(index);

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
    blackhole.consume(typeRef);
    blackhole.consume(typePath);
    blackhole.consume(start);
    blackhole.consume(end);
    blackhole.consume(index);
    blackhole.consume(desc);
    blackhole.consume(visible);

    return new ReadingAnnotationVisitor(
      blackhole,
      super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible)
    );
  }

  @Override
  public void visitLineNumber(final int line,
                              final Label start) {
    blackhole.consume(line);
    blackhole.consume(start);

    super.visitLineNumber(line, start);
  }

  @Override
  public void visitMaxs(final int maxStack,
                        final int maxLocals) {
    blackhole.consume(maxStack);
    blackhole.consume(maxLocals);

    super.visitMaxs(maxStack, maxLocals);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
