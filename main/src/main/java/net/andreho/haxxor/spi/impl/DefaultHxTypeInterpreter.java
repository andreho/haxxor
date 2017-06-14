package net.andreho.haxxor.spi.impl;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.impl.AsmCodeStream;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxEnum;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spi.HxTypeInterpreter;

import java.util.Collection;
import java.util.function.BiFunction;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 20:42.
 */
public class DefaultHxTypeInterpreter
    implements HxTypeInterpreter {

  /*
  A visitor to visit a Java class.
  The methods of this class must be called in the following order:
    visit
    [ visitSource ]
    [ visitOuterClass ]
    ( visitAnnotation | visitTypeAnnotation | visitAttribute )*
    ( visitInnerClass | visitField | visitMethod )*
    visitEnd.
  */
  @Override
  public byte[] interpret(final HxType type,
                          final boolean computeFrames) {
    final ClassWriter writer = new ClassWriter(computeFrames ? ClassWriter.COMPUTE_FRAMES : 0);

    visitClassHeader(type, writer);
    visitSource(type, writer);
    visitOuterClass(type, writer);
    visitAnnotations(type, writer::visitAnnotation);
    visitTypeAnnotations(type, writer);
    visitAttributes(type, writer);
    visitInnerClasses(type, writer);
    visitFields(type, writer);
    HxMethod clinit = visitMethods(type, writer);
    visitConstructors(type, writer);
    if(clinit != null) {
      visitMethod(writer, clinit);
    }

    writer.visitEnd();

    return writer.toByteArray();
  }

  protected void visitClassHeader(final HxType type,
                                  final ClassWriter writer) {
    writer.visit(type.getVersion().getCode(),
                 type.getModifiers(),
                 type.toInternalName(),
                 type.getGenericSignature().orElse(null),
                 type.getSuperType().get().toInternalName(),
                 type.getInterfaces().stream().map(HxType::toInternalName).toArray(String[]::new)
    );
  }

  protected void visitSource(final HxType type,
                             final ClassWriter writer) {
    //NO OP
  }

  protected void visitOuterClass(final HxType type,
                                 final ClassWriter writer) {
    if(type.getDeclaringMember() == null) {
      return;
    }

    //Outer type
    if (type.getDeclaringMember() instanceof HxType) {
      HxType owner = (HxType) type.getDeclaringMember();
      writer.visitOuterClass(owner.getName(), null, null);
    } else {
      if (type.getDeclaringMember() instanceof HxMethod) {
        HxMethod owner = (HxMethod) type.getDeclaringMember();
        writer.visitOuterClass((owner.getDeclaringMember()).getName(), owner.getName(),
                               owner.toDescriptor());
      } else {
        HxConstructor owner = (HxConstructor) type.getDeclaringMember();
        writer.visitOuterClass((owner.getDeclaringMember()).getName(), "<init>", owner.toDescriptor());
      }
    }
  }

  protected void visitAnnotations(final HxAnnotated<?> annotated,
                                  final BiFunction<String, Boolean, AnnotationVisitor> visitAnnotation) {
    for (HxAnnotation annotation : annotated.getAnnotations()) {
      visitAnnotation(annotation,
                      visitAnnotation.apply(
                          annotation.getType().toDescriptor(),
                          annotation.isVisible()
                      )
      ).visitEnd();
    }
  }

  protected AnnotationVisitor visitAnnotation(final HxAnnotation annotation,
                                              final AnnotationVisitor av) {
    final Collection<HxAnnotationAttribute<?, ?>> attributes = annotation.getAttributeMap().values();

    for (HxAnnotationAttribute<?, ?> attribute : attributes) {
      String name = attribute.getName();
      Object value = attribute.getValue();

      if(value instanceof HxEnum) {

        HxEnum hxEnum = (HxEnum) value;
        av.visitEnum(name, hxEnum.getType().toDescriptor(), hxEnum.getName());
      } else if(value instanceof HxEnum[]) {

        HxEnum[] hxEnums = (HxEnum[]) value;
        AnnotationVisitor arrayVisitor = av.visitArray(name);
        for(HxEnum hxEnum : hxEnums) {
          arrayVisitor.visitEnum(null, hxEnum.getType().toDescriptor(), hxEnum.getName());
        }
        arrayVisitor.visitEnd();
      } else if(value instanceof String[]) {

        String[] array = (String[]) value;
        AnnotationVisitor arrayVisitor = av.visitArray(name);
        for(String str : array) {
          arrayVisitor.visit(null, str);
        }
        arrayVisitor.visitEnd();
      } else if(value instanceof HxType) {

        HxType type = (HxType) value;
        av.visit(name, Type.getType(type.toDescriptor()));
      } else if(value instanceof HxType[]) {

        HxType[] types = (HxType[]) value;
        AnnotationVisitor arrayVisitor = av.visitArray(name);
        for(HxType type : types) {
          arrayVisitor.visit(null, Type.getType(type.toDescriptor()));
        }
        arrayVisitor.visitEnd();
      } else if(value instanceof HxAnnotation) {

        HxAnnotation subAnnotation = (HxAnnotation) value;
        AnnotationVisitor subAnnotationVisitor = av.visitAnnotation(name, subAnnotation.getType().toDescriptor());
        visitAnnotation(subAnnotation, subAnnotationVisitor).visitEnd();
      } else if(value instanceof HxAnnotation[]) {

        HxAnnotation[] subAnnotations = (HxAnnotation[]) value;
        AnnotationVisitor arrayVisitor = av.visitArray(name);
        for(HxAnnotation subAnnotation : subAnnotations) {
          visitAnnotation(
              subAnnotation,
              arrayVisitor.visitAnnotation(null, subAnnotation.getType().toDescriptor())).visitEnd();
        }
        arrayVisitor.visitEnd();
      } else {
        av.visit(name, value);
      }
    }
    return av;
  }

  protected void visitTypeAnnotations(final HxType type,
                                      final ClassWriter cw) {
    //NO OP
  }

  protected void visitAttributes(final HxType type,
                                 final ClassWriter cw) {
    //NO OP
  }

  protected void visitInnerClasses(final HxType type,
                                   final ClassWriter cw) {
    //Inner types
    for (HxType innerType : type.getDeclaredTypes()) {
      visitInnerClass(innerType, cw);
    }
  }

  protected void visitInnerClass(final HxType declared,
                                 final ClassWriter cw) {
    final String name = declared.toInternalName();

    String simpleName = declared.getSimpleName();
    if (simpleName != null && simpleName.isEmpty()) {
      simpleName = null;
    }

    if (declared.isMemberType() && declared != null) {
      cw.visitInnerClass(name, declared.toInternalName(), simpleName, declared.getModifiers());
    } else {
      cw.visitInnerClass(name, null, simpleName, declared.getModifiers());
    }
  }

  protected void visitFields(final HxType type,
                             final ClassWriter cw) {
    for (HxField field : type.getFields()) {
      //( visitAnnotation | visitTypeAnnotation | visitAttribute )* visitEnd.
      final FieldVisitor fv = cw.visitField(
          field.getModifiers(),
          field.getName(),
          field.getType().toDescriptor(),
          field.getGenericSignature().orElse(null),
          field.getDefaultValue()
      );

      visitAnnotations(field, fv::visitAnnotation);
      visitTypeAnnotations(field, fv);
      visitAttributes(field, fv);

      fv.visitEnd();
    }
  }

  protected void visitTypeAnnotations(final HxField field,
                                      final FieldVisitor fv) {
    if (!field.isGeneric()) {
      return;
    }

//    final HxAnnotation annotation = ...;
//    final TypeReference typeReference = TypeReference.newTypeReference(TypeReference.FIELD);
//    final TypePath typePath = TypePath.fromString();
//    final AnnotationVisitor av = fv.visitTypeAnnotation(typeReference.getValue(), typePath,
//                                                        annotation.getType().toDescriptor(),
//                                                        annotation.isVisible());
  }

  protected void visitAttributes(final HxField field,
                                 final FieldVisitor fv) {
    //NO OP
  }

  /*
 ( visitParameter )*
 [ visitAnnotationDefault ]
 ( visitAnnotation | visitParameterAnnotation visitTypeAnnotation | visitAttribute )*
 [ visitCode ( visitFrame | visitXInsn | visitLabel | visitInsnAnnotation | visitTryCatchBlock |
 visitTryCatchAnnotation | visitLocalVariable | visitLocalVariableAnnotation | visitLineNumber )* visitMaxs ]
 visitEnd.
   */
  protected void visitConstructors(final HxType type,
                                   final ClassWriter cw) {
    for (HxConstructor constructor : type.getConstructors()) {
      visitConstructor(cw, constructor).visitEnd();
    }
  }

  protected MethodVisitor visitConstructor(final ClassWriter cw,
                                           final HxConstructor constructor) {
    final MethodVisitor mv = cw.visitMethod(
        constructor.getModifiers(),
        "<init>",
        constructor.toDescriptor(),
        constructor.getGenericSignature().orElse(null),
        constructor.getExceptionTypes().stream().map(HxType::toInternalName).toArray(String[]::new)
    );

    visitParameters(constructor, mv);
    visitAnnotations(constructor, mv::visitAnnotation);
    visitParameterAnnotations(constructor, mv);
    visitTypeAnnotations(constructor, mv);
    visitAttributes(constructor, mv);
    visitCode(constructor, mv);

    mv.visitEnd();

    return mv;
  }

  protected HxMethod visitMethods(final HxType type,
                              final ClassWriter cw) {
    HxMethod clinit = null;
    for (HxMethod method : type.getMethods()) {
      if("<clinit>".equals(method.getName()) && clinit == null) {
        clinit = method;
        continue;
      }
      visitMethod(cw, method);
    }
    return clinit;
  }

  protected MethodVisitor visitMethod(final ClassWriter cw,
                                      final HxMethod method) {
    final MethodVisitor mv = cw.visitMethod(
        method.getModifiers(),
        method.getName(),
        method.toDescriptor(),
        method.getGenericSignature().orElse(null),
        method.getExceptionTypes().stream().map(HxType::toInternalName).toArray(String[]::new)
    );

    visitParameters(method, mv);
    if(method.getDeclaringMember().isAnnotation()) {
      visitAnnotationDefault(method, mv);
    }
    visitAnnotations(method, mv::visitAnnotation);
    visitParameterAnnotations(method, mv);
    visitTypeAnnotations(method, mv);
    visitAttributes(method, mv);
    visitCode(method, mv);

    mv.visitEnd();

    return mv;
  }

  protected void visitParameters(final HxExecutable<?> executable,
                                 final MethodVisitor mv) {
    for (HxParameter<?> parameter : executable.getParameters()) {
      visitParameter(mv, parameter);
    }
  }

  private void visitParameter(final MethodVisitor mv,
                              final HxParameter<?> parameter) {
    String name = parameter.isNamePresent() ? parameter.getName() : null;
    mv.visitParameter(name, parameter.getModifiers());
  }

  protected void visitAnnotationDefault(final HxMethod method,
                                        final MethodVisitor mv) {
    if(!method.isAnnotationAttribute()) {
      return;
    }



  }


  protected void visitParameterAnnotations(final HxParameter<?> parameter,
                                           final int index,
                                           final MethodVisitor mv) {
    visitAnnotations(parameter, (desc, visible) -> mv.visitParameterAnnotation(index, desc, visible));
  }

  protected void visitParameterAnnotations(final HxExecutable<?> executable,
                                           final MethodVisitor mv) {
    int index = 0;
    for (final HxParameter<?> parameter : executable.getParameters()) {
      visitParameterAnnotations(parameter, index++, mv);
    }
  }

  protected void visitTypeAnnotations(final HxExecutable<?> executable,
                                      final MethodVisitor mv) {


    if(!executable.isGeneric()) {
      //TODO: this is still not a filter condition ...
      // because of 'throws' and possible annotations on checked exceptions
    }

    //Annotations of an executable that lie outside of the code itself
//    mv.visitTypeAnnotation()
  }

  protected void visitAttributes(final HxExecutable<?> executable,
                                 final MethodVisitor mv) {
    //NO OP
  }

  protected void visitCode(final HxExecutable<?> executable,
                           final MethodVisitor mv) {
    if (!executable.hasCode()) {
      return;
    }

    visitCode(executable.getCode(), new AsmCodeStream(executable, mv), mv);
  }

  protected void visitCode(final HxCode code,
                           final HxCodeStream codeStream,
                           final MethodVisitor mv) {
    assert code.getFirst().isBegin();
    assert code.getLast().isEnd();

    codeStream.BEGIN();
    visitTryCatchBlocks(code, codeStream, mv);

    for (HxInstruction instruction : code.getFirst().getNext()) {
      if (instruction.isEnd()) {
        visitLocalVariables(code, codeStream, mv);
        codeStream.MAXS(code.getMaxStack(), code.getMaxLocals());
//        codeStream.END();
        break;
      }

      instruction.visit(codeStream);

//      if(instruction.hasAnnotations()) {
//        visitAnnotations(instruction, mv::visitInsnAnnotation);
//      }
    }
  }

  protected void visitLocalVariables(final HxCode code,
                                     final HxCodeStream codeStream,
                                     final MethodVisitor mv) {
    for (HxLocalVariable var : code.getLocalVariables()) {
      codeStream.LOCAL_VARIABLE(
          var.getName(),
          var.getDescriptor(),
          var.getSignature(),
          var.getStart(),
          var.getEnd(),
          var.getIndex()
      );
//      visitAnnotations(var, mv::visitLocalVariableAnnotation);
    }
  }

  protected void visitTryCatchBlocks(final HxCode code,
                                     final HxCodeStream codeStream,
                                     final MethodVisitor mv) {
    for (HxTryCatch tryCatch : code.getTryCatches()) {
      codeStream.TRY_CATCH(
          tryCatch.getStartLabel(),
          tryCatch.getEndLabel(),
          tryCatch.getHandler(),
          tryCatch.getType()
      );
//      visitAnnotations(tryCatch, mv::visitTryCatchAnnotation);
    }
  }
}
