package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxSourceInfo;
import net.andreho.haxxor.spec.api.HxType;

import java.util.function.Consumer;

import static net.andreho.haxxor.Utils.normalizeReturnType;
import static net.andreho.haxxor.Utils.normalizeSignature;
import static net.andreho.haxxor.Utils.toPrimitiveClassname;

/**
 * <br/>Created by a.hofmann on 10.11.2015.<br/>
 */
public class HxTypeVisitor
    extends ClassVisitor {

  private final Haxxor haxxor;
  private String name;
  private HxType type;

  public HxTypeVisitor(Haxxor haxxor) {
    this(haxxor, null);
  }

  public HxTypeVisitor(Haxxor haxxor,
                       ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
    this.haxxor = haxxor;
  }

  public HxType getType() {
    return type;
  }

  @Override
  public void visit(int version,
                    int access,
                    String name,
                    String signature,
                    String superName,
                    String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);

    this.name = name;
    this.type = this.haxxor.createType(name);

    this.type
        .setVersion(HxType.Version.of(version))
        .setModifiers(access)
        .setSuperType(superName)
        .setGenericSignature(signature);

    if (interfaces != null && interfaces.length > 0) {
      this.type.setInterfaces(this.haxxor.referencesAsList(interfaces));
    }
  }

  @Override
  public void visitSource(String source,
                          String debug) {
    super.visitSource(source, debug);
    this.type.setSourceInfo(new HxSourceInfo(source, debug));
  }

  @Override
  public void visitInnerClass(String name,
                              String outerName,
                              String innerName,
                              int access) {
    super.visitInnerClass(name, outerName, innerName, access);
//    System.out.println(HxType.Modifiers.toSet(access));
    if(!this.type.hasName(name)) {
      this.type.addInnerType(haxxor.reference(name).setModifiers(access));
    } else {
      System.out.println(HxType.Modifiers.toSet(this.type.getModifiers()) + name);
    }
  }

  @Override
  public void visitOuterClass(String owner,
                              String name,
                              String desc) {
    super.visitOuterClass(owner, name, desc);

    if (name != null && desc != null) {
      final HxMethod methodReference =
        this.haxxor.createMethodReference(owner, normalizeReturnType(desc), name, normalizeSignature(desc));
      this.type.setDeclaringMember(methodReference);
    } else {
      this.type.setDeclaringMember(this.haxxor.reference(owner));
    }
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc,
                                           boolean visible) {
    final HxAnnotation hxAnnotation =
        this.haxxor.createAnnotation(desc, visible);
    this.type.initialize(HxType.Part.ANNOTATIONS);
    final Consumer consumer = (Consumer<HxAnnotation>) this.type::addAnnotation;
    return new HxAnnotationVisitor(hxAnnotation, consumer, super.visitAnnotation(desc, visible));
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(int typeRef,
                                               TypePath typePath,
                                               String desc,
                                               boolean visible) {
    AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    return av;
  }

  @Override
  public void visitAttribute(Attribute attr) {
    super.visitAttribute(attr);
  }

  @Override
  public FieldVisitor visitField(int access,
                                 String name,
                                 String desc,
                                 String signature,
                                 Object value) {
    final FieldVisitor fv = super.visitField(access, name, desc, signature, value);

    this.type.initialize(HxType.Part.FIELDS);
    HxField hxField = this.haxxor
        .createField(toPrimitiveClassname(desc), name)
        .setModifiers(access)
        .setGenericSignature(signature)
        .setDefaultValue(value);

    return new HxFieldVisitor(this.type, hxField, fv);
  }

  @Override
  public MethodVisitor visitMethod(int access,
                                   String name,
                                   String desc,
                                   String signature,
                                   String[] exceptions) {
    final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

    final String[] parameterTypes = normalizeSignature(desc);
    final String returnType = normalizeReturnType(desc);
    final HxMethod parameterizable =
      this.haxxor
        .createMethod(returnType, name, parameterTypes)
        .setModifiers(access)
        .setGenericSignature(signature);

    if (exceptions != null && exceptions.length > 0) {
      parameterizable.setExceptionTypes(this.haxxor.referencesAsArray(exceptions));
    }

    return new HxExecutableVisitor(haxxor, type, parameterizable, mv);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
