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
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameterizable;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxType.Part;

import java.util.Objects;

import static net.andreho.haxxor.Utils.normalizeClassname;
import static net.andreho.haxxor.Utils.normalizeReturnType;
import static net.andreho.haxxor.Utils.normalizeSignature;

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

  public HxTypeVisitor(Haxxor haxxor, ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
    this.haxxor = haxxor;
  }

  public HxType getType() {
    return type;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);

    this.name = name;
    this.type = this.haxxor.createType(name);

    this.type
        .setVersion(HxType.Version.of(version))
        .setModifiers(access)
        .setSuperType(superName)
        .setGenericSignature(signature);

    if (interfaces != null) {
      this.type.setInterfaces(this.haxxor.referencesAsList(interfaces));
    }
  }

  @Override
  public void visitSource(String source, String debug) {
    super.visitSource(source, debug);
  }

  @Override
  public void visitInnerClass(String name, String outerName, String innerName, int access) {
    super.visitInnerClass(name, outerName, innerName, access);

    if(Objects.equals(this.name, name)) { //isn't it redundant?
      if(outerName != null) {
        type.setDeclaringMember(haxxor.reference(outerName));
      }
    } else if(Objects.equals(this.name, outerName)) {
      type.initialize(Part.DECLARED_TYPES).getDeclaredTypes().add(haxxor.reference(name));
    }
  }

  @Override
  public void visitOuterClass(String owner, String name, String desc) {
    super.visitOuterClass(owner, name, desc);

    if (name != null && desc != null) {
      if ("<init>".equals(name)) {
        HxConstructor constructorReference = haxxor.createConstructorReference(owner, normalizeSignature(desc));
        this.type.setDeclaringMember(constructorReference);
      } else {
        final String returnType = desc.substring(desc.lastIndexOf(')') + 1);
        HxMethod methodReference = haxxor.createMethodReference(owner, name, returnType, normalizeSignature(desc));
        this.type.setDeclaringMember(methodReference);
      }
    } else {
      this.type.setDeclaringMember(this.type.getHaxxor()
                                            .reference(owner));
    }
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    final HxAnnotation hxAnnotation =
        this.haxxor.createAnnotation(desc, visible);
    this.type.initialize(Part.ANNOTATIONS);
    return new HxAnnotationVisitor(hxAnnotation, super.visitAnnotation(desc, visible))
        .consumer(this.type::addAnnotation);
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
    return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
  }

  @Override
  public void visitAttribute(Attribute attr) {
    super.visitAttribute(attr);
  }


  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
    this.type.initialize(Part.FIELDS);
    HxField hxField = this.haxxor
        .createField(name, normalizeClassname(desc))
        .setModifiers(access)
        .setGenericSignature(signature)
        .setDefaultValue(value);

    return new HxFieldVisitor(this.type, hxField, super.visitField(access, name, desc, signature, value));
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    final String[] parameterTypes = normalizeSignature(desc);
    HxParameterizable parameterizable;

    if ("<init>".equals(name)) {
      parameterizable = this.haxxor.createConstructor(parameterTypes);
    } else {
      String returnType = normalizeReturnType(desc);
      parameterizable = this.haxxor.createMethod(name, returnType, parameterTypes);
    }

    parameterizable.setGenericSignature(signature);

    if (exceptions != null) {
      parameterizable.setExceptionTypes(this.haxxor.referencesAsArray(exceptions));
    }

    return new HxParameterizableVisitor(
        type,
        parameterizable,
        super.visitMethod(access, name, desc, signature, exceptions)
    );
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
