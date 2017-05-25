package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxEnum;
import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.impl.HxAnnotationImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 10.11.2015.<br/>
 */
public class HxAnnotationVisitor extends AnnotationVisitor {
   //----------------------------------------------------------------------------------------------------------------

   private final HxAnnotation annotation;
   private Consumer<HxAnnotation> consumer;

   //----------------------------------------------------------------------------------------------------------------

   public HxAnnotationVisitor(HxAnnotation annotation) {
      this(annotation, null);
   }

   public HxAnnotationVisitor(HxAnnotation annotation, AnnotationVisitor av) {
      super(Opcodes.ASM5, av);
      this.annotation = Objects.requireNonNull(annotation, "Annotation is null");
   }

   //----------------------------------------------------------------------------------------------------------------

   public HxAnnotationVisitor consumer(Consumer<HxAnnotation> consumer) {
      this.consumer = consumer;
      return this;
   }

   protected HxAnnotationVisitor createVisitor(final AnnotationVisitor superAnnotationVisitor, final String desc,
                                               final boolean visible) {
      final HxType annotationType = this.annotation.getType()
                                                   .getHaxxor()
                                                   .reference(desc);
      return new HxAnnotationVisitor(new HxAnnotationImpl(annotationType, visible), superAnnotationVisitor);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public void visit(String name, Object value) {
      super.visit(name, value);

      if (name != null) {
         if (value.getClass().isArray()) {
            if (value instanceof int[]) {
               annotation.attribute(name, (int[]) value);
            } else if (value instanceof double[]) {
               annotation.attribute(name, (double[]) value);
            } else if (value instanceof long[]) {
               annotation.attribute(name, (long[]) value);
            } else if (value instanceof float[]) {
               annotation.attribute(name, (float[]) value);
            } else if (value instanceof byte[]) {
               annotation.attribute(name, (byte[]) value);
            } else if (value instanceof char[]) {
               annotation.attribute(name, (char[]) value);
            } else if (value instanceof short[]) {
               annotation.attribute(name, (short[]) value);
            } else {
               //if(value instanceof boolean[])
               annotation.attribute(name, (boolean[]) value);
            }
         } else if (value instanceof Number) {
            if (value instanceof Byte) {
               annotation.attribute(name, (Byte) value);
            } else if (value instanceof Short) {
               annotation.attribute(name, (Short) value);
            } else if (value instanceof Integer) {
               annotation.attribute(name, (Integer) value);
            } else if (value instanceof Float) {
               annotation.attribute(name, (Float) value);
            } else if (value instanceof Long) {
               annotation.attribute(name, (Long) value);
            } else {
               //if(value instanceof Double)
               annotation.attribute(name, (Double) value);
            }
         } else if (value instanceof Boolean) {
            annotation.attribute(name, (Boolean) value);
         } else if (value instanceof Character) {
            annotation.attribute(name, (Character) value);
         } else if (value instanceof String) {
            annotation.attribute(name, (String) value);
         } else {
            //if(value instanceof Type)
            Type type = (Type) value;
            if (type.getSort() != Type.OBJECT && type.getSort() != Type.ARRAY) {
               switch (type.getSort()) {
                  case Type.BOOLEAN:
               }
            } else {
               annotation.attribute(name, type.getInternalName());
            }
         }
      }
   }

   @Override
   public void visitEnum(String name, String desc, String value) {
      super.visitEnum(name, desc, value);

      if (name != null) {
         final HxType attributeType = this.annotation.getType()
                                                     .getHaxxor()
                                                     .reference(desc);

         this.annotation.attribute(name, new HxEnum(attributeType, value));
      }
   }

   @Override
   public AnnotationVisitor visitArray(final String name) {
      final HxAnnotation annotation = this.annotation;

      return new AnnotationVisitor(Opcodes.ASM5, super.visitArray(name)) {
         Object[] array = new Object[0];
         int length;

         void push(Class elementType, Object value) {
            if (!elementType.isInstance(array.getClass())) {
               array = Arrays.copyOf(array,
                                     Math.max(2,
                                              (length >= array.length) ? length + (int) (length * 0.75) : array.length),
                                     elementType);
            } else if (length >= array.length) {
               array = Arrays.copyOf(array, Math.max(2, length + (int) (length * 0.75)));
            }
            array[length++] = value;
         }

         @Override
         public void visit(String name, Object value) {
            super.visit(name, value);
            if (value instanceof Type) {
               push(HxType[].class, annotation.getType()
                                              .getHaxxor()
                                              .reference(((Type) value).getInternalName()));
            } else {
               push(Object.class, value);
            }
         }

         @Override
         public void visitEnum(String name, String desc, String value) {
            super.visitEnum(name, desc, value);
            push(HxEnum[].class, new HxEnum(annotation.getType()
                                                      .getHaxxor()
                                                      .reference(desc), value));
         }

         @Override
         public AnnotationVisitor visitAnnotation(String name, String desc) {
            return createVisitor(super.visitAnnotation(name, desc), desc, true)
                  .consumer((anno) -> push(HxAnnotation[].class, anno));
         }

         @Override
         public void visitEnd() {
            super.visitEnd();
            throw new UnsupportedOperationException("TODO");
//            annotation.set(name, (array.length != length) ? Arrays.copyOf(array, length) : array);
         }
      };
   }

   @Override
   public AnnotationVisitor visitAnnotation(final String name, final String desc) {
      final HxAnnotationVisitor annotationVisitor = createVisitor(super.visitAnnotation(name, desc), desc, true);

      return new AnnotationVisitor(Opcodes.ASM5, annotationVisitor) {
         @Override
         public void visitEnd() {
            super.visitEnd();
            throw new UnsupportedOperationException("TODO");
//            if (name != null) {
//               annotation.set(name, annotationVisitor.annotation);
//            }
         }
      };
   }

   @Override
   public void visitEnd() {
      super.visitEnd();

      if (this.consumer != null) {
         this.consumer.accept(this.annotation);
      }
   }
}
