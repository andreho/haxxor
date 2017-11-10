package examples;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE,
         ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE,
         ElementType.LOCAL_VARIABLE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
public @interface Marker {
  String name() default "";
}
