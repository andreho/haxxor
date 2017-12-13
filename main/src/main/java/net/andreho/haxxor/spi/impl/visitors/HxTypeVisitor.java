package net.andreho.haxxor.spi.impl.visitors;

import net.andreho.asm.org.objectweb.asm.AnnotationVisitor;
import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.TypePath;
import net.andreho.asm.org.objectweb.asm.TypeReference;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxInitializablePart;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxSourceInfo;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.Version;
import net.andreho.haxxor.api.impl.HxAnnotatedImpl;
import net.andreho.haxxor.utils.NamingUtils;

import java.util.Collections;
import java.util.function.Consumer;

import static net.andreho.haxxor.utils.NamingUtils.normalizeReturnType;
import static net.andreho.haxxor.utils.NamingUtils.normalizeSignature;

/**
 * <br/>Created by a.hofmann on 10.11.2015.<br/>
 */
public class HxTypeVisitor
    extends ClassVisitor {

  private final Hx haxxor;
  private HxType type;
  private final boolean resolveClass;
  private final boolean resolveFields;
  private final boolean resolveMethods;

  public HxTypeVisitor(final Hx haxxor,
                       final boolean resolveClass,
                       final boolean resolveFields,
                       final boolean resolveMethods) {
    this(haxxor, null, resolveClass, resolveFields, resolveMethods);
  }

  public HxTypeVisitor(final Hx haxxor,
                       final ClassVisitor cv,
                       final boolean resolveClass,
                       final boolean resolveFields,
                       final boolean resolveMethods) {
    super(Opcodes.ASM5, cv);
    this.haxxor = haxxor;
    this.resolveClass = resolveClass;
    this.resolveFields = resolveFields;
    this.resolveMethods = resolveMethods;
  }

  public HxType getType() {
    return type;
  }

  public HxTypeVisitor setType(final HxType type) {
    this.type = type;
    return this;
  }

  @Override
  public void visit(int version,
                    int access,
                    String name,
                    String signature,
                    String superName,
                    String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);

    if(!resolveClass) {
      return;
    }

    this.type = this.type != null? this.type : this.haxxor.createType(name);

    this.type
        .setVersion(Version.of(version))
        .setModifiers(access)
        .setSuperType(superName)
        .setGenericSignature(signature);

    if (interfaces != null && interfaces.length > 0) {
      this.type.setInterfaces(this.haxxor.referencesAsList(interfaces));
    } else {
      this.type.setInterfaces(Collections.emptyList());
    }
  }

  @Override
  public void visitSource(String source,
                          String debug) {
    super.visitSource(source, debug);

    if(!resolveClass) {
      return;
    }

    this.type.setSourceInfo(new HxSourceInfo(source, debug));
  }

  @Override
  public void visitInnerClass(String name,
                              String outerName,
                              String innerName,
                              int access) {
    super.visitInnerClass(name, outerName, innerName, access);
    if(!resolveClass) {
      return;
    }
    if(!this.type.hasName(name)) {
      this.type.addInnerType(haxxor.reference(name).setModifiers(access));
    } else {
//      System.out.println(HxType.Modifiers.toSet(this.type.getModifiers()) + " " + name);
    }
  }

  @Override
  public void visitOuterClass(String owner,
                              String name,
                              String desc) {
    super.visitOuterClass(owner, name, desc);
    if(!resolveClass) {
      return;
    }

    if (name != null && desc != null) {
      final HxMethod methodReference =
        this.haxxor.createMethodReference(owner, normalizeReturnType(desc), name, normalizeSignature(desc));
      this.type.setDeclaringMember(methodReference);
    } else {
      this.type.setDeclaringMember(this.haxxor.reference(owner));
    }
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    final AnnotationVisitor av = super.visitAnnotation(desc, visible);
    if(!resolveClass) {
      return av;
    }

    final HxAnnotation hxAnnotation =
        this.haxxor.createAnnotation(desc, visible);
    this.type.initialize(HxInitializablePart.ANNOTATIONS);
    final Consumer consumer = (Consumer<HxAnnotation>) this.type::addAnnotation;
    return new HxAnnotationVisitor(hxAnnotation, consumer, av);
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(int typeRef,
                                               TypePath typePath,
                                               String desc,
                                               boolean visible) {
    AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    if(!resolveClass) {
      return av;
    }

    if(typeRef != 0) {
      final HxType type = this.type;
      final TypeReference ref = new TypeReference(typeRef);
      final int index = ref.getSuperTypeIndex();

      if(ref.getSort() == TypeReference.CLASS_EXTENDS) {
        HxAnnotated<?> annotated;

        if(index < 0) {
          if(type.getAnnotatedSuperType().isPresent()) {
            annotated = type.getAnnotatedSuperType().get();
          } else {
            annotated = new HxAnnotatedImpl<>().setDeclaringMember(type);
            type.setAnnotatedSuperType(annotated);
          }
        } else {
          if(type.getAnnotatedInterface(index).isPresent()) {
            annotated = type.getAnnotatedInterface(index).get();
          } else {
            annotated = new HxAnnotatedImpl<>().setDeclaringMember(type);
            type.setAnnotatedInterface(index, annotated);
          }
        }

        final HxAnnotation hxAnnotation = this.haxxor.createAnnotation(desc, visible);
        final Consumer consumer = (Consumer<HxAnnotation>) annotated::addAnnotation;
        return new HxAnnotationVisitor(hxAnnotation, consumer, av);
      }
    }
    return av;
  }

  @Override
  public void visitAttribute(Attribute attr) {
    if(resolveClass) {
      super.visitAttribute(attr);
    }
  }

  @Override
  public FieldVisitor visitField(int access,
                                 String name,
                                 String desc,
                                 String signature,
                                 Object value) {
    final FieldVisitor fv = super.visitField(access, name, desc, signature, value);
    if(!resolveFields) {
      return fv;
    }

    this.type.initialize(HxInitializablePart.FIELDS);
    HxField hxField = this.haxxor
        .createField(NamingUtils.primitiveDescriptorToPrimitiveClassname(desc), name)
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
    if(!resolveMethods) {
      return mv;
    }

    final String[] parameterTypes = normalizeSignature(desc);
    final String returnType = normalizeReturnType(desc);
    final HxMethod method =
      this.haxxor
        .createMethod(returnType, name, parameterTypes)
        .setModifiers(access)
        .setGenericSignature(signature);

    if (exceptions != null && exceptions.length > 0) {
      method.setExceptionTypes(this.haxxor.references(exceptions));
    }

    return new HxMethodVisitor(haxxor, type, method, mv);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
