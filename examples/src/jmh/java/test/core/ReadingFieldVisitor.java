package test.core;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.openjdk.jmh.infra.Blackhole;

/**
 * <br/>Created by a.hofmann on 24.11.2017 at 01:07.
 */
class ReadingFieldVisitor
  extends FieldVisitor {

  private final Blackhole blackhole;

  public ReadingFieldVisitor(final Blackhole blackhole) {
    super(Opcodes.ASM5);
    this.blackhole = blackhole;
  }

  public ReadingFieldVisitor(final Blackhole blackhole,
                             final FieldVisitor fv) {
    super(Opcodes.ASM5, fv);
    this.blackhole = blackhole;
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc,
                                           final boolean visible) {
    blackhole.consume(desc);
    blackhole.consume(visible);
    return new ReadingAnnotationVisitor(blackhole, super.visitAnnotation(desc, visible));
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
  public void visitEnd() {
    super.visitEnd();
  }
}
