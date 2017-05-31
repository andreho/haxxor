package net.andreho.haxxor.spec.generics.impl;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.signature.SignatureReader;
import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.generics.HxTypeVariable;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by a.hofmann on 19.07.2016.
 */
public class HxAbstractGenericTest {
   static final int FORMAL_TYPE = 0;
   static final int CLASS_BOUND = 1;
   static final int INTERFACE_BOUND = 2;

   //----------------------------------------------------------------------------------------------------------------
   static final int SUPER_CLASS = 3;
   static final int INTERFACE = 4;

   @Test
   public void asd() {
      Debugger.trace(Node8 .class);
   }

   @Test
   public void test() {
      TypeVariable<Class<Node5>>[] typeVariables = Node5.class.getTypeParameters();
      for (TypeVariable t :
            typeVariables) {
         t.getBounds();
         t.getGenericDeclaration();
      }

      String s =
//            "<A::Ljava/io/Serializable;>Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<TA;>;";
//            "<A::Ljava/util/List<+Ljava/io/Serializable;>;
// >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;";
//            "<A::Ljava/util/List<+Ljava/io/Serializable;>;B::Ljava/util/Collection<Ljava/lang/String;>;" +
//            "C:Ljava/lang/Number;>" +
//            "Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;";
//      "<A::Ljava/io/Serializable;" +
//      ":Ljava/lang/Comparable<TA;>;" +
//      ":Ljava/util/Collection<+TA;>;>" +
//      "Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;";
      "<A:Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$AnyNode;" +
      ":Ljava/io/Serializable;" +
      ":Ljava/lang/Comparable<TA;>;" +
      ":Ljava/util/Collection<Ljava/util/Set<Ljava/util/List<Ljava/util/Map<TA;*>;>;>;>;>" +
      "Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;";

      SignatureReader sr = new SignatureReader(s);
      sr.accept(new ClassSignatureReader());
      System.out.println();
   }

   @Test
   public void test0() {
      String s =
//            "<A::Ljava/io/Serializable;>Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<TA;>;";
//            "<A::Ljava/util/List<+Ljava/io/Serializable;>;
// >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;";
      "<A::Ljava/io/Serializable;" +
      ":Ljava/lang/Comparable<TA;>;" +
      ":Ljava/util/Collection<+TA;>;>"+
      "Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;";
   }

   //null
   static abstract class AnyNode {

   }

   //-----------------------------------------------------------------------------------------------------------------

   //<V::Ljava/io/Serializable;>Ljava/lang/Object;
   static abstract class NodeX<V extends Serializable> {

   }

   //Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node0 extends NodeX<String> {

   }

   //<A::Ljava/io/Serializable;>Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<TA;>;
   static class Node1<A extends Serializable> extends NodeX<A> {

   }

   //<V:Ljava/lang/Object;>Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node2<V> extends NodeX<String> {

   }

   //<A:Ljava/lang/Object;B:Ljava/lang/Object;C:Ljava/lang/Object;
   // >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node3<A, B, C> extends NodeX<String> {

   }
   //----------------------------------------------------------------------------------------------------------------

   //<A::Ljava/util/List<+Ljava/io/Serializable;>;>
   // Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node4<A extends List<? extends Serializable>> extends NodeX<String> {

   }

   //<A::Ljava/util/List<+Ljava/io/Serializable;>;B::Ljava/util/Collection<Ljava/lang/String;>;C:Ljava/lang/Number;
   // >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node5<A extends List<? extends Serializable>, B extends Collection<String>, C extends Number>
         extends NodeX<String> {

   }

   //<A:Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$Node6<TA;>;
   // >Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node6<A extends Node6<A>> extends NodeX<String> {

   }

   //<A::Ljava/io/Serializable;:Ljava/lang/Comparable<TA;>;:Ljava/util/Collection<+TA;>;>
   // Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   static class Node7<A extends Serializable & Comparable<A> & Collection<? extends A>> extends NodeX<String> {

   }

   /*
   <A:Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$AnyNode;
   :Ljava/io/Serializable;
   :Ljava/lang/Comparable<TA;>;
   :Ljava/util/Collection<Ljava/util/Set<Ljava/util/List<Ljava/util/Map<TA;*>;>;>;>;>
   Lnet/andreho/haxxor/spec/generics/impl/HxAbstractGenericTest$NodeX<Ljava/lang/String;>;
   */
   static class Node8<A extends AnyNode & Serializable & Comparable<A> & Collection<Set<List<Map<A,?>>>>>
         extends NodeX<String> {

   }

   static abstract class AbstractSignatureReader extends SignatureVisitor {
      protected HxGeneric parent;

      public AbstractSignatureReader(){
         super(Opcodes.ASM5);
      }

      public AbstractSignatureReader(HxGeneric parent){
         this();
         this.parent = parent;
      }
   }

   static class ClassSignatureReader extends AbstractSignatureReader {

      private List<HxTypeVariable> typeVariables = new ArrayList<>();
      private HxGeneric superClass;
      private List<HxGeneric> interfaces = new ArrayList<>();
      private int step;
      private int depth;

      private void print(String s) {
         StringBuilder stb = new StringBuilder();
         int indent = this.depth;
         while(indent-- > 0){
            stb.append('|');
         }
         stb.append(s);
         System.out.println(stb);
      }

      @Override
      public void visitFormalTypeParameter(String name) {
         print("visitFormalTypeParameter(\""+name+"\");");
         this.step = FORMAL_TYPE;
         super.visitFormalTypeParameter(name);
         this.typeVariables.add(new HxTypeVariableImpl().setName(name));
      }

      /*
      ClassSignature =
      ( visitFormalTypeParameter visitClassBound? visitInterfaceBound* )* ( visitSuperclass visitInterface* )
       */
      @Override
      public SignatureVisitor visitClassBound() {
         print("visitClassBound(); ->");
         this.step = CLASS_BOUND;
         this.depth++;
         return super.visitClassBound();
      }

      @Override
      public SignatureVisitor visitInterfaceBound() {
         print("visitInterfaceBound(); ->");
         this.step = INTERFACE_BOUND;
         this.depth++;
         return super.visitInterfaceBound();
      }

      @Override
      public SignatureVisitor visitSuperclass() {
         print("visitSuperclass(); ->");
         this.depth++;
         return super.visitSuperclass();
      }

      @Override
      public SignatureVisitor visitInterface() {
         print("visitInterface(); ->");
         this.depth++;
         return super.visitInterface();
      }

      @Override
      public SignatureVisitor visitArrayType() {
         print("visitArrayType(); ->");
         this.depth++;
         return super.visitArrayType();
      }

      @Override
      public void visitBaseType(char descriptor) {
         print("visitBaseType('"+descriptor+"');");
         super.visitBaseType(descriptor);
      }

      @Override
      public void visitClassType(String name) {
         print("visitClassType(\""+name+"\");");
         super.visitClassType(name);
      }

      @Override
      public void visitTypeVariable(String name) {
         print("visitTypeVariable(\""+name+"\");");
         super.visitTypeVariable(name);
         visitEnd();
      }

      @Override
      public void visitInnerClassType(String name) {
         print("visitInnerClassType(\""+name+"\");");
         super.visitInnerClassType(name);
      }

      @Override
      public void visitTypeArgument() {
         print("visitTypeArgument();");
         super.visitTypeArgument();
      }

      @Override
      public SignatureVisitor visitTypeArgument(char wildcard) {
         print("visitTypeArgument('"+wildcard+"'); ->");
         this.depth++;
         return super.visitTypeArgument(wildcard);
      }

      @Override
      public void visitEnd() {
         this.depth--;
         print("visitEnd();");
         super.visitEnd();
      }
   }
}