package net.andreho.haxxor.spi.impl.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Constants;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxEnum;
import net.andreho.haxxor.api.HxType;

import java.lang.reflect.Array;
import java.util.List;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 10.11.2015.<br/>
 */
public class HxAnnotationArrayVisitor
    extends AnnotationVisitor {
  private final Hx haxxor;
  private final List<Object> array;
  private final Consumer<Object> consumer;

  public HxAnnotationArrayVisitor(final Hx haxxor,
                                  final List<Object> array,
                                  final Consumer<Object> consumer) {
    this(haxxor, array, consumer, null);
  }

  public HxAnnotationArrayVisitor(final Hx haxxor,
                                  final List<Object> array,
                                  final Consumer<Object> consumer,
                                  final AnnotationVisitor av) {
    super(Opcodes.ASM5, av);
    this.haxxor = haxxor;
    this.array = array;
    this.consumer = consumer;
  }

  private void push(Object o) {
    array.add(o);
  }

  @Override
  public void visit(String name, Object value) {
    super.visit(name, value);
    checkArraySemantic(name);

    if (value instanceof Type) {
      Type type = (Type) value;
      value = getHaxxor().reference(type.getClassName());
    }

    push(value);
  }

  private Hx getHaxxor() {
    return haxxor;
  }

  private void checkArraySemantic(final String name) {
    if(name != null) {
      throw new IllegalStateException("Array-visitor can't have named attributes: "+name);
    }
  }

  @Override
  public void visitEnum(String name, String desc, String value) {
    super.visitEnum(name, desc, value);
    checkArraySemantic(name);
    push(new HxEnum(getHaxxor().reference(desc), value));
  }

  @Override
  public AnnotationVisitor visitArray(final String name) {
    throw new IllegalStateException("Array-visitor can't create sub-arrays: "+name);
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String name, final String desc) {
    checkArraySemantic(name);

    final AnnotationVisitor av = super.visitAnnotation(name, desc);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, true);
    final HxAnnotationVisitor annotationVisitor = new HxAnnotationVisitor(annotation, this::push, av);

    return annotationVisitor;
  }

  @Override
  public void visitEnd() {
    super.visitEnd();

    final Consumer<Object> consumer = this.consumer;
    if (consumer == null) {
      return;
    }

    final List<Object> list = this.array;
    if (list.isEmpty()) {
      return;
    }

    Object first = list.get(0);
    Object array = null;

    if (first instanceof Number) {
      if (first instanceof Byte) {
        array = new byte[list.size()];
      } else if (first instanceof Short) {
        array = new short[list.size()];
      } else if (first instanceof Integer) {
        array = new int[list.size()];
      } else if (first instanceof Float) {
        array = new float[list.size()];
      } else if (first instanceof Long) {
        array = new long[list.size()];
      } else {
        array = new double[list.size()];
      }
    } else if (first instanceof Boolean) {
      array = new boolean[list.size()];
    } else if (first instanceof Character) {
      array = new char[list.size()];
    } else if (first instanceof String) {
      array = new String[list.size()];
    }

    if (array != null) {
      for (int i = 0; i < list.size(); i++) {
        Object o = list.get(i);
        Array.set(array, i, o);
      }
    }

    if (first instanceof HxType) {
      array = list.toArray(Constants.EMPTY_HX_TYPE_ARRAY);
    } else if (first instanceof HxEnum) {
      array = list.toArray(Constants.EMPTY_HX_ENUM_ARRAY);
    } else if (first instanceof HxAnnotation) {
      array = list.toArray(Constants.EMPTY_HX_ANNOTATION_ARRAY);
    }

    consumer.accept(array == null? HxConstants.EMPTY_ARRAY : array);
  }
}
