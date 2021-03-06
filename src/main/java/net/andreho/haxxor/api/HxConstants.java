package net.andreho.haxxor.api;


/**
 * <br/>Created by a.hofmann on 06.06.2017 at 20:09.
 */
public abstract class HxConstants {
  public static final class EmptyArray {
    private static final EmptyArray[] INSTANCE = new EmptyArray[0];
    private EmptyArray() {}
  }
  public static final String CLASS_INITIALIZER_METHOD_NAME = "<clinit>";
  public static final String CONSTRUCTOR_METHOD_NAME = "<init>";
  public static final EmptyArray[] EMPTY_ARRAY = EmptyArray.INSTANCE;
  public static final String JAVA_LANG_OBJECT = "java.lang.Object";
  public static final char JAVA_PACKAGE_SEPARATOR_CHAR = '.';
  public static final char INTERNAL_PACKAGE_SEPARATOR_CHAR = '/';
  public static final String UNDEFINED_TYPE = "<undefined>";
  public static final String ARRAY_DIMENSION = "[]";
  public static final String DESC_ARRAY_PREFIX_STR = "[";
  public static final char DESC_ARRAY_PREFIX = '[';
  public static final char DESC_PREFIX = 'L';
  public static final char DESC_SUFFIX = ';';

  public static final HxType[] EMPTY_TYPE_ARRAY = new HxType[0];
  public static final HxEnum[] EMPTY_ENUM_ARRAY = new HxEnum[0];
  public static final HxField[] EMPTY_FIELD_ARRAY = new HxField[0];
  public static final HxMethod[] EMPTY_METHOD_ARRAY = new HxMethod[0];
  public static final HxAnnotated<?>[] EMPTY_ANNOTATED_ARRAY = new HxAnnotated[0];
  public static final HxAnnotation[] EMPTY_ANNOTATION_ARRAY = new HxAnnotation[0];

  private HxConstants() {
  }
}
