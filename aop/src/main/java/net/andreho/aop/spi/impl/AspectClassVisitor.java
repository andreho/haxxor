package net.andreho.aop.spi.impl;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Aspects;
import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 05:49.
 */
public class AspectClassVisitor
    extends ClassVisitor {

  private static final int ALLOWED_MODIFIERS = Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE | Opcodes.ACC_PUBLIC;
  private static final String ASPECT_ANNOTATION_DESCRIPTOR = Type.getDescriptor(Aspect.class);
  private static final String ASPECTS_ANNOTATION_DESCRIPTOR = Type.getDescriptor(Aspects.class);

  private boolean aspect;
  private int access;

  public AspectClassVisitor() {
    super(Opcodes.ASM5);
  }

  public boolean isAspect() {
    return aspect;
  }

  @Override
  public void visit(final int version,
                    final int access,
                    final String name,
                    final String signature,
                    final String superName,
                    final String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    this.access = access;
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc,
                                           final boolean visible) {
    final AnnotationVisitor av = super.visitAnnotation(desc, visible);
    if ((access & (ALLOWED_MODIFIERS)) == Opcodes.ACC_PUBLIC &&
        (desc.equals(ASPECT_ANNOTATION_DESCRIPTOR) || desc.equals(ASPECTS_ANNOTATION_DESCRIPTOR))) {
      this.aspect = true;
    }
    return av;
  }
}
