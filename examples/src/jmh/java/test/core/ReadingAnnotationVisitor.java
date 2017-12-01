package test.core;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.openjdk.jmh.infra.Blackhole;

/**
 * <br/>Created by a.hofmann on 24.11.2017 at 01:07.
 */
class ReadingAnnotationVisitor
  extends AnnotationVisitor {

  private final Blackhole blackhole;

  public ReadingAnnotationVisitor(final Blackhole blackhole) {
    super(Opcodes.ASM5);
    this.blackhole = blackhole;
  }

  public ReadingAnnotationVisitor(final Blackhole blackhole,
                                  final AnnotationVisitor av) {
    super(Opcodes.ASM5, av);
    this.blackhole = blackhole;
  }

  @Override
  public void visit(final String name,
                    final Object value) {
    blackhole.consume(name);
    blackhole.consume(value);

    super.visit(name, value);
  }

  @Override
  public void visitEnum(final String name,
                        final String desc,
                        final String value) {
    blackhole.consume(name);
    blackhole.consume(desc);
    blackhole.consume(value);
    super.visitEnum(name, desc, value);
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String name,
                                           final String desc) {
    blackhole.consume(name);
    blackhole.consume(desc);
    return new ReadingAnnotationVisitor(blackhole);
  }

  @Override
  public AnnotationVisitor visitArray(final String name) {
    blackhole.consume(name);
    return new ReadingAnnotationVisitor(blackhole);
  }
}
