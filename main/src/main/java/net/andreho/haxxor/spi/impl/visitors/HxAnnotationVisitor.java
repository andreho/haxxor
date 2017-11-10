package net.andreho.haxxor.spi.impl.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxEnum;
import net.andreho.haxxor.api.HxType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 10.11.2015.<br/>
 */
public class HxAnnotationVisitor
    extends AnnotationVisitor {
  private static final Consumer<Object> BLACK_HOLE = (any) -> {};

  private final HxAnnotation annotation;
  private final Consumer consumer;

  public HxAnnotationVisitor(final HxAnnotation annotation) {
    this(annotation, BLACK_HOLE, null);
  }

  public HxAnnotationVisitor(final HxAnnotation annotation,
                             final Consumer consumer) {
    this(annotation, consumer, null);
  }

  public HxAnnotationVisitor(final HxAnnotation annotation,
                             final Consumer consumer,
                             final AnnotationVisitor av) {
    super(Opcodes.ASM5, av);
    this.annotation = Objects.requireNonNull(annotation, "Annotation is null");
    this.consumer = consumer;
  }

  private void checkAnnotationSemantic(final String name) {
    if(name == null) {
      throw new IllegalStateException("Annotation-Visitor must have named attributes: "+name+", annotation: "+annotation.getType());
    }
  }

  private Hx getHaxxor() {
    return this.annotation.getHaxxor();
  }

  private void addArray(final String name, final Object array) {
    if(array == HxConstants.EMPTY_ARRAY) {
      annotation.setAttribute(name, (HxConstants.EmptyArray[]) array);
      return;
    }

    if(!array.getClass().isArray()) {
      throw new IllegalStateException("Expected an array: "+array);
    }

    if(array instanceof boolean[]) {
      annotation.setAttribute(name, (boolean[]) array);
    } else if(array instanceof byte[]) {
      annotation.setAttribute(name, (byte[]) array);
    } else if(array instanceof short[]) {
      annotation.setAttribute(name, (short[]) array);
    } else if(array instanceof char[]) {
      annotation.setAttribute(name, (char[]) array);
    } else if(array instanceof int[]) {
      annotation.setAttribute(name, (int[]) array);
    } else if(array instanceof float[]) {
      annotation.setAttribute(name, (float[]) array);
    } else if(array instanceof double[]) {
      annotation.setAttribute(name, (double[]) array);
    } else if(array instanceof long[]) {
      annotation.setAttribute(name, (long[]) array);
    } else if(array instanceof String[]) {
      annotation.setAttribute(name, (String[]) array);
    } else if(array instanceof HxType[]) {
      annotation.setAttribute(name, (HxType[]) array);
    } else if(array instanceof HxEnum[]) {
      annotation.setAttribute(name, (HxEnum[]) array);
    } else if(array instanceof HxAnnotation[]) {
      annotation.setAttribute(name, (HxAnnotation[]) array);
    } else {
      throw new IllegalStateException("Unsupported array-type: "+array.getClass());
    }
  }

  private void addAnnotation(final String name, final HxAnnotation annotation) {
    this.annotation.setAttribute(name, annotation);
  }


  @Override
  public void visit(String name, Object value) {
    super.visit(name, value);
    checkAnnotationSemantic(name);

    if (value.getClass().isArray()) {
      if (value instanceof int[]) {
        annotation.setAttribute(name, (int[]) value);
      } else if (value instanceof double[]) {
        annotation.setAttribute(name, (double[]) value);
      } else if (value instanceof long[]) {
        annotation.setAttribute(name, (long[]) value);
      } else if (value instanceof float[]) {
        annotation.setAttribute(name, (float[]) value);
      } else if (value instanceof byte[]) {
        annotation.setAttribute(name, (byte[]) value);
      } else if (value instanceof char[]) {
        annotation.setAttribute(name, (char[]) value);
      } else if (value instanceof short[]) {
        annotation.setAttribute(name, (short[]) value);
      } else {
        //if(value instanceof boolean[])
        annotation.setAttribute(name, (boolean[]) value);
      }
    } else if (value instanceof Number) {
      if (value instanceof Byte) {
        annotation.setAttribute(name, (Byte) value);
      } else if (value instanceof Short) {
        annotation.setAttribute(name, (Short) value);
      } else if (value instanceof Integer) {
        annotation.setAttribute(name, (Integer) value);
      } else if (value instanceof Float) {
        annotation.setAttribute(name, (Float) value);
      } else if (value instanceof Long) {
        annotation.setAttribute(name, (Long) value);
      } else {
        //if(value instanceof Double)
        annotation.setAttribute(name, (Double) value);
      }
    } else if (value instanceof Boolean) {
      annotation.setAttribute(name, (Boolean) value);
    } else if (value instanceof Character) {
      annotation.setAttribute(name, (Character) value);
    } else if (value instanceof String) {
      annotation.setAttribute(name, (String) value);
    } else {
      Type type = (Type) value;
      annotation.setAttribute(name, getHaxxor().reference(type.getClassName()));
    }
  }

  @Override
  public void visitEnum(String name, String desc, String value) {
    super.visitEnum(name, desc, value);
    checkAnnotationSemantic(name);

    final HxType enumType = getHaxxor().reference(desc);
    this.annotation.setAttribute(name, new HxEnum(enumType, value));
  }

  @Override
  public AnnotationVisitor visitArray(final String name) {
    checkAnnotationSemantic(name);
    final AnnotationVisitor av = super.visitArray(name);
    return new HxAnnotationArrayVisitor(getHaxxor(), new ArrayList<>(), (array) -> addArray(name, array), av);
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String name, final String desc) {
    checkAnnotationSemantic(name);
    final AnnotationVisitor av = super.visitAnnotation(name, desc);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, true);
    return new HxAnnotationVisitor(annotation, (anno) -> addAnnotation(name, (HxAnnotation) anno), av);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();

    if (this.consumer != null) {
      this.consumer.accept(this.annotation);
    }
  }
}
