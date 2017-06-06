package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class HxFieldVisitor
    extends FieldVisitor {

  private final HxType type;
  private final HxField field;

  public HxFieldVisitor(HxType type, HxField field, final FieldVisitor fv) {
    super(Opcodes.ASM5, fv);
    this.type = Objects.requireNonNull(type, "Field's type can't be null.");
    this.field = Objects.requireNonNull(field, "Field is null");
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
    final HxAnnotation hxAnnotation = this.type.getHaxxor()
                                               .createAnnotation(desc, visible);

    return new HxAnnotationVisitor(hxAnnotation, super.visitAnnotation(desc, visible))
        .consumer(this.field::addAnnotation);
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(final int typeRef, final TypePath typePath, final String desc,
                                               final boolean visible) {
    final HxAnnotation hxAnnotation = this.type.getHaxxor()
                                               .createAnnotation(desc, visible);

    return new HxAnnotationVisitor(hxAnnotation, super.visitTypeAnnotation(typeRef, typePath, desc, visible));
  }

  @Override
  public void visitAttribute(final Attribute attr) {
    super.visitAttribute(attr);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    this.type.initialize(HxType.Part.FIELDS)
             .addField(this.field);
  }
}
