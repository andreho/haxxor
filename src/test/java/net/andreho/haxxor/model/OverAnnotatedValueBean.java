package net.andreho.haxxor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 13:45.
 */
@AnnotationB(
    boolValue = false,
    intArray = {1, 2, 3},
    floatArray = {1.25f, 2.25f, 3.25f},
    stringArray = {"a", "b", "c"},
    enumArray = {EnumA.YES, EnumA.NO},
    classArray = {MinimalBean.class}
)
@AnnotationC("class")
public class OverAnnotatedValueBean
    extends @AnnotationC("superClass") MinimalBean
    implements @AnnotationC("interface") InterfaceA,
               GenericInterfaceB<@AnnotationC("GenericInterfaceB<*>")
                   List<@AnnotationC("GenericInterfaceB<<*>>") EnumA>> {

  @AnnotationC("field")
  private final String value;

  @AnnotationC("list")
  private List<@AnnotationC("List<*>>") Integer> list;

  @AnnotationC("map")
  private Map<String, List<@AnnotationC("Map<String, List<*>>") Integer>> map;

  @AnnotationC("<init>")
  public OverAnnotatedValueBean(@AnnotationC("parameter") final String value) {
    this.value =
        (@AnnotationC("interface") String)
            Objects.requireNonNull(value);
    this.list = new @AnnotationC("new") ArrayList<>();
  }

  @AnnotationC("getter")
  public String getValue() {
    return value;
  }

  @Override
  @AnnotationC("equals")
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof @AnnotationC("instanceof") OverAnnotatedValueBean)) {
      return false;
    }
    try {
      @AnnotationC("that") final OverAnnotatedValueBean that =
          (@AnnotationC("cast") OverAnnotatedValueBean) o;
      return value != null ? value.equals(that.value) : that.value == null;
    } catch (@AnnotationC("exception") Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  @AnnotationC("methodA")
  public GenericInterfaceB<List<EnumA>> methodA() {
    return null;
  }

  @Override
  @AnnotationC("methodB")
  public void methodB(@AnnotationC("methodB-enumA") final List<EnumA> enumA) {

  }

  @Override
  @AnnotationC("methodC")
  public List<@AnnotationC("methodC-List<*>") EnumA> methodC(@AnnotationC("methodC-s") final String s) {
    return null;
  }

  @Override
  @AnnotationC("methodD")
  public List<String> methodD(@AnnotationC("methodD-map") final Map<List<EnumA>, String> map) {
    return null;
  }

  @Override
  @AnnotationC("methodX")
  public List<List<EnumA>> methodX(@AnnotationC("methodX-map") final Map<@AnnotationC("1") List<@AnnotationC("2")
      EnumA>, @AnnotationC("3") List<@AnnotationC("4") EnumA>>
                                       map) {
    return null;
  }

  @Override
  @AnnotationC("hashCode")
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  @AnnotationC("toString")
  public String toString() {
    return "OverAnnotatedSimpleBean{" +
           "value='" + value + '\'' +
           '}';
  }

}
