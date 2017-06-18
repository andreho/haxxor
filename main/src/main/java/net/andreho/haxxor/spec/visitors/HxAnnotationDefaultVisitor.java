package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstants;
import net.andreho.haxxor.spec.api.HxEnum;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 10.11.2015.<br/>
 */
public class HxAnnotationDefaultVisitor
    extends AnnotationVisitor {

  private final Haxxor haxxor;
  private final Consumer consumer;
  private Object value = HxConstants.EMPTY_ARRAY;

  public HxAnnotationDefaultVisitor(final Haxxor haxxor,
                                    final Consumer consumer,
                                    final AnnotationVisitor av) {
    super(Opcodes.ASM5, av);
    this.haxxor = haxxor;
    this.consumer = consumer;
  }

  private Haxxor getHaxxor() {
    return this.haxxor;
  }

  private void setValue(final String name, final Object value) {
    this.value = value;
  }

  private void setEnum(final String name, final HxEnum value) {
    this.value = value;
  }

  private void setArray(final String name, final Object array) {
    this.value = array;
  }

  private void setAnnotation(final String name, final HxAnnotation annotation) {
    this.value = annotation;
  }

  @Override
  public void visit(String name, Object value) {
    super.visit(name, value);

    if (value.getClass().isArray()) {
      setArray(name, value);
    } else if (value instanceof Number) {
      setValue(name, value);
    } else if (value instanceof Boolean) {
      setValue(name, value);
    } else if (value instanceof Character) {
      setValue(name, value);
    } else if (value instanceof String) {
      setValue(name, value);
    } else {
      Type type = (Type) value;
      setValue(name, getHaxxor().reference(type.getClassName()));
    }
  }

  @Override
  public void visitEnum(String name, String desc, String value) {
    super.visitEnum(name, desc, value);
    setEnum(name, new HxEnum(getHaxxor().reference(desc), value));
  }

  @Override
  public AnnotationVisitor visitArray(final String name) {
    final AnnotationVisitor av = super.visitArray(name);
    return new HxAnnotationArrayVisitor(getHaxxor(), new ArrayList<>(), (array) -> setArray(name, array), av);
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String name, final String desc) {
    final AnnotationVisitor av = super.visitAnnotation(name, desc);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, true);
    return new HxAnnotationVisitor(annotation, (anno) -> setAnnotation(name, (HxAnnotation) anno), av);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();

    if (this.consumer != null) {
      this.consumer.accept(this.value);
    }
  }
}
