package net.andreho.haxxor.spec.generics.impl;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.impl.HxGenericTypeImpl;
import net.andreho.haxxor.spec.visitors.HxGenericSignatureReader;
import net.andreho.haxxor.spec.visitors.HxGenericSignatureVisitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import static net.andreho.haxxor.Utils.normalizeClassname;
import static net.andreho.haxxor.spec.api.HxTypeTestUtils.checkTypes;

/**
 * Created by a.hofmann on 19.07.2016.
 */
public class HxAbstractGenericTest
    extends ClassVisitor {

  private static final String TEST_CLASSES = "testClasses";
  private static Haxxor haxxor;
  private Class<?> target;
  private String classname;

  public HxAbstractGenericTest() {
    super(Opcodes.ASM5);
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  void checkSignatureParsing(Class<?> cls)
  throws IOException {
    final ClassReader cr = new ClassReader(cls.getName());
    this.target = cls;

    visitAll(cls.getTypeParameters());
    visitAll(cls.getGenericSuperclass());
    visitAll(cls.getGenericInterfaces());

    cr.accept(this, ClassReader.SKIP_CODE);
  }

  @Test
  @Disabled
  void doTest()
  throws IOException {
    checkSignatureParsing(HxGenericTestCasses.NodeXY.class);
  }

  private String printTypeParameters(Class<?> cls) {
    StringBuilder builder = new StringBuilder();
    final TypeVariable<? extends Class<?>>[] typeParameters = cls.getTypeParameters();
    if (typeParameters.length > 0) {
      builder.append("<")
             .append(typeParameters[0].getTypeName());
      for (int i = 1; i < typeParameters.length; i++) {
        builder.append(",")
               .append(typeParameters[i].getTypeName());
      }
      builder.append(">");
    }
    return builder.toString();
  }

  @Override
  public void visit(final int version,
                    final int access,
                    final String name,
                    final String signature,
                    final String superName,
                    final String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    System.out.println(classname = normalizeClassname(name) + ": " + signature);
//    System.out.println(target.getName() + ": " + printTypeParameters(target));
    System.out.println();

    if (signature != null) {
      HxGenericSignatureReader reader = new HxGenericSignatureReader(signature);
      HxGenericTypeImpl genericType = new HxGenericTypeImpl();
      reader.accept(new HxGenericSignatureVisitor(haxxor, genericType));
//      System.out.println(genericType.getTypeVariables());
//      System.out.println(genericType.getSuperType());
//      System.out.println(genericType.getInterfaces());
      System.out.println(genericType);

      checkTypes(target.getTypeParameters(), genericType.getTypeVariables());
      checkTypes(target.getGenericSuperclass(), genericType.getSuperType());
      checkTypes(target.getGenericInterfaces(), genericType.getInterfaces());
    }
    System.out.println();
  }

  @Override
  public MethodVisitor visitMethod(final int access,
                                   final String name,
                                   final String desc,
                                   final String signature,
                                   final String[] exceptions) {
    //System.out.println(classname + "." + name + desc + ": " + signature);
    return super.visitMethod(access, name, desc, signature, exceptions);
  }

  @Override
  public FieldVisitor visitField(final int access,
                                 final String name,
                                 final String desc,
                                 final String signature,
                                 final Object value) {
    //System.out.println(classname + "." + name +"#" + desc + ": " + signature);
    return super.visitField(access, name, desc, signature, value);
  }

  private static Iterable<Class<?>> testClasses() {
    return Arrays.asList(HxGenericTestCasses.class.getDeclaredClasses());
  }

  @BeforeAll
  static void setupGlobalHaxxor() {
    haxxor = new Haxxor();
  }

  //----------------------------------------------------------------------------------------------------------------

  private static void visitAll(Type... types) {
    visitAll(Collections.newSetFromMap(new IdentityHashMap<>()), types);
  }

  private static void visitAll(Set<Type> visited,
                               Type... types) {
    for (Type type : types) {
      visit(visited, type);
    }
  }

  private static void visit(Set<Type> visited,
                            Type type) {
    if (!visited.add(type)) {
      return;
    }

    if (type instanceof TypeVariable) {
      TypeVariable<?> typeVariable = (TypeVariable<?>) type;

      visitAll(visited, typeVariable.getBounds());
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type ownerType = parameterizedType.getOwnerType();
      Type rawType = parameterizedType.getRawType();

      visitAll(visited, parameterizedType.getActualTypeArguments());
    } else if (type instanceof GenericArrayType) {
      GenericArrayType arrayType = (GenericArrayType) type;
      Type componentType = arrayType.getGenericComponentType();

      visit(visited, componentType);
    } else if (type instanceof WildcardType) {
      WildcardType wildcardType = (WildcardType) type;

      visitAll(visited, wildcardType.getLowerBounds());
      visitAll(visited, wildcardType.getUpperBounds());
    }
  }


}