package net.andreho.haxxor.spec.generics.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <br/>Created by a.hofmann on 09.06.2017 at 02:38.
 */
public class HxGenericTestCasses {

  //null
  static abstract class AnyNode {

  }

  static abstract class Node
      extends AnyNode {

  }

  //<V::Ljava/io/Serializable;>Ljava/lang/Object;
  static abstract class NodeX<V extends Serializable>
      extends Node
      implements INode1<List<V>[]> {

  }

  static abstract class NodeY<V extends Number>
      extends Node
      implements INode1<List<V>[]> {

  }

  static abstract class NodeXY<V extends Number & Comparable<V>>
      extends NodeX<V>
      implements INode0,
                 INode1<List<V>[]>,
                 INode2<List<V>[], byte[]> {

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

  interface INode0 {

  }

  interface INode1<A>
      extends INode0 {

  }

  //-----------------------------------------------------------------------------------------------------------------

  interface INode2<A, B>
      extends INode1<A> {

  }

  interface INode3<A, B, C>
      extends INode2<A[][], byte[]> {

  }

  interface INode4<A extends Serializable, B extends Cloneable & Comparable<A>, C extends List<A>, D extends Map<A,
      Collection<?>>>
      extends INode3<Map<?, ?>, Serializable[], C> {

  }
}
