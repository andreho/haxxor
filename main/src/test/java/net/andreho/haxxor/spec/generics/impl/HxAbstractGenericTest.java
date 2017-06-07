package net.andreho.haxxor.spec.generics.impl;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.FieldVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.spec.visitors.HxGenericSignatureReader;
import net.andreho.haxxor.spec.visitors.HxGenericSignatureVisitor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.andreho.haxxor.Utils.normalizeClassname;

/**
 * Created by a.hofmann on 19.07.2016.
 */
public class HxAbstractGenericTest
    extends ClassVisitor {

  private static final String TEST_CLASSES = "testClasses";
  private Class<?> target;
  private String classname;

  public HxAbstractGenericTest() {
    super(Opcodes.ASM5);
  }

  private static Iterable<Class<?>> testClasses() {
    return Arrays.asList(HxAbstractGenericTest.class.getDeclaredClasses());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  void checkSignatureParsing(Class<?> cls)
  throws IOException {
    final ClassReader cr = new ClassReader(cls.getName());
    this.target = cls;
    cr.accept(this, ClassReader.SKIP_CODE);
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
      reader.accept(new HxGenericSignatureVisitor());
      System.out.println();
    }
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

  //-----------------------------------------------------------------------------------------------------------------

  interface INode0 {

  }

  interface INode1<A>
      extends INode0 {

  }

  interface INode2<A, B>
      extends INode1<A> {

  }

  interface INode3<A, B, C>
      extends INode2<A, B> {

  }

  interface INode4<A extends Serializable, B extends Cloneable & Comparable<A>, C extends List<A>, D extends Map<A,
      Collection<?>>>
      extends INode3<A, B, C> {

  }

  //null
  static abstract class AnyNode {

  }

  static abstract class Node
      extends AnyNode {

  }

  //<V::Ljava/io/Serializable;>Ljava/lang/Object;
  static abstract class NodeX<V extends Serializable>
      extends Node implements INode1<List<V>[]> {

  }

  //----------------------------------------------------------------------------------------------------------------

  static abstract class NodeY<V extends Number & Comparable<V>>
      extends Node implements INode1<List<V>[]> {

  }

  //Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node0
      extends NodeX<String> {

  }

  //<A::Ljava/io/Serializable;>Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<TA;>;
  static class Node1<A extends Serializable>
      extends NodeX<A> {

  }

  //<V:Ljava/lang/Object;>Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node2<V>
      extends NodeX<String> {

  }

  //<A:Ljava/lang/Object;B:Ljava/lang/Object;C:Ljava/lang/Object;
  // >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node3<A, B, C>
      extends NodeX<String> {

  }

  //<A::Ljava/util/List<+Ljava/io/Serializable;>;>
  // Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node4<A extends List<? extends Serializable>>
      extends NodeX<String> {

  }

  //<A::Ljava/util/List<+Ljava/io/Serializable;>;B::Ljava/util/Collection<Ljava/lang/String;>;C:Ljava/lang/Number;
  // >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node5<A extends List<? extends Serializable>, B extends Collection<String>, C extends Number>
      extends NodeX<String> {

  }

  //<A:Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$Node6<TA;>;
  // >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node6<A extends Node6<A>>
      extends NodeX<String> {

  }

  //<A::Ljava/io/Serializable;:Ljava/lang/Comparable<TA;>;:Ljava/util/Collection<+TA;>;>
  // Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  static class Node7<A extends Serializable & Comparable<A> & Collection<? extends A>>
      extends NodeX<String> {

  }

  /*
  <A:Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$AnyNode;
  :Ljava/io/Serializable;
  :Ljava/lang/Comparable<TA;>;
  :Ljava/util/Collection<Ljava/util/Set<Ljava/util/List<Ljava/util/Map<TA;*>;>;>;>;>
  Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
  */
  static class Node8<A extends AnyNode & Serializable & Comparable<A> & Collection<Set<List<Map<A, ?>>>>>
      extends NodeX<String> {

  }
}