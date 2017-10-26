package net.andreho.haxxor.spi.impl.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxType;

import java.util.Objects;
import java.util.function.Consumer;

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
    final Consumer consumer = (Consumer<HxAnnotation>) this.field::addAnnotation;
    final HxAnnotation hxAnnotation = this.type.getHaxxor().createAnnotation(desc, visible);
    return new HxAnnotationVisitor(hxAnnotation, consumer, super.visitAnnotation(desc, visible));
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(final int typeRef, final TypePath typePath, final String desc,
                                               final boolean visible) {
//    final Consumer consumer = (Consumer<HxAnnotation>) this.field::addAnnotation;
//    final HxAnnotation hxAnnotation = this.type.getHaxxor().createAnnotation(desc, visible);
//    return new HxAnnotationVisitor(hxAnnotation, consumer, super.visitTypeAnnotation(typeRef, typePath, desc, visible));
    return super.visitTypeAnnotation(typeRef, typePath, desc, false);
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
