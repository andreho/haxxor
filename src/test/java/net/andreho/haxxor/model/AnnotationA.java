package net.andreho.haxxor.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 01.06.2017 at 23:26.
 */
@Target({ElementType.CONSTRUCTOR,
         ElementType.TYPE,
         ElementType.METHOD,
         ElementType.FIELD,
         ElementType.ANNOTATION_TYPE,
         ElementType.LOCAL_VARIABLE,
         ElementType.PACKAGE,
         ElementType.PARAMETER,
         ElementType.TYPE_PARAMETER,
         ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationA {
  boolean boolValue();
  byte byteValue();
  char charValue();
  short shortValue();
  int intValue();
  float floatValue();
  long longValue();
  double doubleValue();
  String stringValue();
  Class<?> classValue();
  EnumA enumValue();

  AnnotationB annotationValue();

  boolean[] booleanArray();
  byte[] byteArray();
  char[] charArray();
  short[] shortArray();
  int[] intArray();
  float[] floatArray();
  long[] longArray();
  double[] doubleArray();
  String[] stringArray();
  Class<?>[] classArray();
  EnumA[] enumArray();

  AnnotationB[] annotationArray();
}
