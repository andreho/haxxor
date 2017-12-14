package net.andreho.haxxor;

import net.andreho.haxxor.model.AbstractBean;
import net.andreho.haxxor.model.AnnotationA;
import net.andreho.haxxor.model.AnnotationB;
import net.andreho.haxxor.model.AnnotationBRepeatable;
import net.andreho.haxxor.model.AnnotationC;
import net.andreho.haxxor.model.CodeAnnotationBean;
import net.andreho.haxxor.model.ComplexBean;
import net.andreho.haxxor.model.EmbeddingClassesBean;
import net.andreho.haxxor.model.EnumA;
import net.andreho.haxxor.model.EnumB;
import net.andreho.haxxor.model.EnumC;
import net.andreho.haxxor.model.GenericBean;
import net.andreho.haxxor.model.InterfaceA;
import net.andreho.haxxor.model.InterfaceB;
import net.andreho.haxxor.model.InterfaceC;
import net.andreho.haxxor.model.InterfaceD;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.OverAnnotatedValueBean;
import net.andreho.haxxor.model.SupportedSpecBean;
import net.andreho.haxxor.model.ValueBean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 18:55.
 */
public class TestCases {

  public static final String PRIMITIVES = "primitives";
  public static final String ARRAYS = "arrays";
  public static final String INTERFACES = "interfaces";
  public static final String CLASSES = "classes";
  public static final String JAVA8_CLASSES = "java8Classes";

  public static List<Class<?>> primitives() {
    return Arrays.asList(
      void.class,
      boolean.class,
      byte.class,
      char.class,
      short.class,
      int.class,
      float.class,
      long.class,
      double.class
    );
  }

  public static List<Class<?>> arrays() {
    return Arrays.asList(
      boolean[].class,
      byte[].class,
      char[].class,
      short[].class,
      int[].class,
      float[].class,
      long[].class,
      double[].class,
      boolean[][].class,
      byte[][].class,
      char[][].class,
      short[][].class,
      int[][].class,
      float[][].class,
      long[][].class,
      double[][].class,
      boolean[][][].class,
      byte[][][].class,
      char[][][].class,
      short[][][].class,
      int[][][].class,
      float[][][].class,
      long[][][].class,
      double[][][].class,

      Serializable[].class,
      Serializable[][].class,
      Serializable[][][].class,
      String[].class,
      String[][].class,
      String[][][].class
    );
  }

  public static List<Class<?>> interfaces() {
    return Arrays.asList(
      Serializable.class,
      Comparable.class,
      Function.class,
      InterfaceA.class,
      InterfaceB.class,
      InterfaceC.class,
      InterfaceD.class,
      AnnotationA.class,
      AnnotationB.class,
      AnnotationBRepeatable.class,
      AnnotationC.class
    );
  }

  public static List<Class<?>> classes() {
    return Arrays.asList(
      String.class,
      StringBuilder.class,
      AbstractBean.class,
      MinimalBean.class,
      ValueBean.class,
      ComplexBean.class,
      GenericBean.class,
      SupportedSpecBean.class,
      EnumA.class,
      EnumB.class,
      EnumC.class,
      EmbeddingClassesBean.class
    );
  }

  public static List<Class<?>> java8Classes() {
    return Arrays.asList(
      OverAnnotatedValueBean.class,
      CodeAnnotationBean.class
    );
  }
}
