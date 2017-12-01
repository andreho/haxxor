package test.core;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.openjdk.jmh.infra.Blackhole;

/**
 * <br/>Created by a.hofmann on 24.11.2017 at 01:06.
 */
public class ReadingClassVisitor
  extends ClassVisitor {

  private final Blackhole blackhole;

  public int access;
  public String name;
  public String superName;
  public String[] interfaces;

  public ReadingClassVisitor(Blackhole blackhole) {
    super(Opcodes.ASM5);
    this.blackhole = blackhole;
  }

  public ReadingClassVisitor(final Blackhole blackhole,
                             final ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
    this.blackhole = blackhole;
  }

  @Override
  public void visit(final int version,
                    final int access,
                    final String name,
                    final String signature,
                    final String superName,
                    final String[] interfaces) {
    this.access = access;
    this.name = name;
    this.superName = superName;
    this.interfaces = interfaces;

    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public void visitSource(final String source,
                          final String debug) {
    blackhole.consume(source);
    blackhole.consume(debug);

    super.visitSource(source, debug);
  }

  @Override
  public void visitOuterClass(final String owner,
                              final String name,
                              final String desc) {
    blackhole.consume(owner);
    blackhole.consume(name);
    blackhole.consume(desc);
    super.visitOuterClass(owner, name, desc);
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc,
                                           final boolean visible) {
    blackhole.consume(desc);
    blackhole.consume(visible);
    return new ReadingAnnotationVisitor(blackhole);
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
  public void visitAttribute(final Attribute attr) {
    blackhole.consume(attr);
    super.visitAttribute(attr);
  }

  @Override
  public void visitInnerClass(final String name,
                              final String outerName,
                              final String innerName,
                              final int access) {
    blackhole.consume(name);
    blackhole.consume(outerName);
    blackhole.consume(innerName);
    blackhole.consume(access);
    super.visitInnerClass(name, outerName, innerName, access);
  }

  @Override
  public FieldVisitor visitField(final int access,
                                 final String name,
                                 final String desc,
                                 final String signature,
                                 final Object value) {
    blackhole.consume(access);
    blackhole.consume(name);
    blackhole.consume(desc);
    blackhole.consume(signature);
    blackhole.consume(value);

    return new ReadingFieldVisitor(blackhole, super.visitField(access, name, desc, signature, value));
  }

  @Override
  public MethodVisitor visitMethod(final int access,
                                   final String name,
                                   final String desc,
                                   final String signature,
                                   final String[] exceptions) {

    blackhole.consume(access);
    blackhole.consume(name);
    blackhole.consume(desc);
    blackhole.consume(signature);
    blackhole.consume(exceptions);

    return new ReadingMethodVisitor(blackhole, super.visitMethod(access, name, desc, signature, exceptions));
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
