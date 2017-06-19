package net.andreho.haxxor.cgen.impl;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.asm.org.objectweb.asm.TypeReference;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.visitors.HxAnnotationDefaultVisitor;
import net.andreho.haxxor.spec.visitors.HxAnnotationVisitor;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 04:42.
 */
public class AsmExecutableMethodVisitor
    extends AsmCodeMethodVisitor {

  protected final HxCode code;
  protected final HxMethod method;
  protected int parameterIndex = 0;

  public AsmExecutableMethodVisitor(final Haxxor haxxor,
                                    final HxMethod method) {
    this(haxxor, method, method.getCode(), method.getCode().build(), null);
  }


  public AsmExecutableMethodVisitor(final Haxxor haxxor,
                                    final HxMethod method,
                                    final MethodVisitor mv) {
    this(haxxor, method, method.getCode(), method.getCode().build(), mv);
  }


  public AsmExecutableMethodVisitor(final Haxxor haxxor,
                                    final HxMethod method,
                                    final HxCode code,
                                    final HxCodeStream codeStream,
                                    final MethodVisitor mv) {
    super(haxxor, codeStream, mv);

    this.code = code;
    this.method = Objects.requireNonNull(method, "Target can't be null.");
  }

  @Override
  public void visitParameter(final String name,
                             final int access) {
    super.visitParameter(name, access);
    this.method.getParameterAt(this.parameterIndex++)
               .setModifiers(access)
               .setName(name);
  }

  @Override
  public AnnotationVisitor visitParameterAnnotation(final int parameter,
                                                    final String desc,
                                                    final boolean visible) {
    final AnnotationVisitor av = super.visitAnnotation(desc, visible);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, visible);
    final Consumer consumer = (Consumer<HxAnnotation>)
        (anno) -> this.method.getParameterAt(parameter).addAnnotation(anno);

    return new HxAnnotationVisitor(annotation, consumer, av);
  }

  @Override
  public AnnotationVisitor visitAnnotationDefault() {
    final AnnotationVisitor av = super.visitAnnotationDefault();
    //available for methods only :)
    final HxMethod hxMethod = this.method;
    final Consumer consumer = (Consumer<Object>)
        (defaultValue) ->
            hxMethod.setDefaultValue(defaultValue);

    return new HxAnnotationDefaultVisitor(haxxor, consumer, av);
  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc,
                                           final boolean visible) {
    final AnnotationVisitor av = super.visitAnnotation(desc, visible);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, visible);
    final Consumer consumer = (Consumer<HxAnnotation>)
        (anno) ->
            this.method.addAnnotation(anno);

    return new HxAnnotationVisitor(annotation, consumer, av);
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(final int typeRef,
                                               final TypePath typePath,
                                               final String desc,
                                               final boolean visible) {
    return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
  }

  @Override
  public AnnotationVisitor visitInsnAnnotation(final int typeRef,
                                               final TypePath typePath,
                                               final String desc,
                                               final boolean visible) {
    final AnnotationVisitor av = super.visitInsnAnnotation(typeRef, typePath, desc, visible);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, visible);
    final Consumer consumer = (Consumer<HxAnnotation>)
        (anno) -> this.code.getCurrent().addAnnotation(anno);

    return new HxAnnotationVisitor(annotation, consumer, av);
  }

  @Override
  public void visitTryCatchBlock(final Label start,
                                 final Label end,
                                 final Label handler,
                                 final String type) {
    super.visitTryCatchBlock(start, end, handler, type);

    this.codeStream.TRY_CATCH(remap(start), remap(end), remap(handler), type);
  }

  @Override
  public AnnotationVisitor visitTryCatchAnnotation(final int typeRef,
                                                   final TypePath typePath,
                                                   final String desc,
                                                   final boolean visible) {
    final AnnotationVisitor av = super.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
    final HxAnnotation annotation = getHaxxor().createAnnotation(desc, visible);
    final Consumer consumer = (Consumer<HxAnnotation>)
        (anno) -> this.code.getLastTryCatch().addAnnotation(anno);

    return new HxAnnotationVisitor(annotation, consumer, av);
  }

  @Override
  public void visitLocalVariable(final String name,
                                 final String desc,
                                 final String signature,
                                 final Label start,
                                 final Label end,
                                 final int index) {
    super.visitLocalVariable(name, desc, signature, start, end, index);
    this.codeStream.LOCAL_VARIABLE(name, desc, signature, remap(start), remap(end), index);
  }

  @Override
  public AnnotationVisitor visitLocalVariableAnnotation(final int typeRef,
                                                        final TypePath typePath,
                                                        final Label[] start,
                                                        final Label[] end,
                                                        final int[] index,
                                                        final String desc,
                                                        final boolean visible) {

    AnnotationVisitor av = super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);

    if (typeRef == TypeReference.LOCAL_VARIABLE && typePath == null) {
      final HxAnnotation annotation = getHaxxor().createAnnotation(desc, visible);
      final Consumer consumer = (Consumer<HxAnnotation>)
          (anno) -> this.code.getLastLocalVariable().addAnnotation(anno);

      return new HxAnnotationVisitor(annotation, consumer, av);
    }

    return av;
  }

  @Override
  public void visitMaxs(final int maxStack,
                        final int maxLocals) {
    super.visitMaxs(maxStack, maxLocals);
    code.setMaxStack(maxStack);
    code.setMaxLocals(maxLocals);
  }
}
