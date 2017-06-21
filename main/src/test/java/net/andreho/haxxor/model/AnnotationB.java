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
public @interface AnnotationB {
  boolean boolValue() default true;
  byte byteValue() default 1;
  char charValue() default '1';
  short shortValue() default 1;
  int intValue() default 1;
  float floatValue() default 1;
  long longValue() default 1;
  double doubleValue() default 1;

  String stringValue() default "yes";
  Class<?> classValue() default SpecificBean.class;
  EnumA enumValue() default EnumA.YES;
  AnnotationC annotationValue() default @AnnotationC("ok");

  boolean[] booleanArray() default {false, true};
  byte[] byteArray() default {-1,0,1};
  char[] charArray() default {'-','0','1'};
  short[] shortArray() default {-1,0,1};
  int[] intArray() default {-1,0,1};
  float[] floatArray() default {-1.5f,0.5f,1.5f};
  long[] longArray() default {-1,0,1};
  double[] doubleArray() default {-1.5,0.5,1.5};

  String[] stringArray() default {"-1", "0", "1"};
  Class<?>[] classArray() default {AbstractBean.class, SpecificBean.class};
  EnumA[] enumArray() default {EnumA.YES, EnumA.NO, EnumA.MAYBE};
  AnnotationC[] annotationArray() default {@AnnotationC("ok")};
}
