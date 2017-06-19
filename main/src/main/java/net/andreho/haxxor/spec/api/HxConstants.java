package net.andreho.haxxor.spec.api;


/**
 * <br/>Created by a.hofmann on 06.06.2017 at 20:09.
 */
public abstract class HxConstants {
  public static final class EmptyArray {
    private static final EmptyArray[] INSTANCE = new EmptyArray[0];
    private EmptyArray() {}
  }
  public static final String CONSTRUCTOR_METHOD_NAME = "<init>";
  public static final EmptyArray[] EMPTY_ARRAY = EmptyArray.INSTANCE;
  public static final String JAVA_LANG_OBJECT = "java.lang.Object";
  public static final char JAVA_PACKAGE_SEPARATOR_CHAR = '.';
  public static final char INTERNAL_PACKAGE_SEPARATOR_CHAR = '/';
  public static final String UNDEFINED_TYPE = "<undefined>";
  public static final String ARRAY_DIMENSION = "[]";
  public static final char DESC_ARRAY_PREFIX = '[';
  public static final char DESC_PREFIX = 'L';
  public static final char DESC_SUFFIX = ';';


  public enum Primitive {
    VOID('V', "void", Void.class),
    BOOLEAN('Z', "boolean", Boolean.class),
    BYTE('B', "byte", Byte.class),
    SHORT('S', "short", Short.class),
    CHAR('C', "char", Character.class),
    INT('I', "int", Integer.class),
    FLOAT('F', "float", Float.class),
    LONG('J', "long", Long.class),
    DOUBLE('D', "double", Double.class);

    private final String name;
    private final char literal;
    private final Class<?> wrapper;

    Primitive(final char literal,
              final String name,
              final Class<?> wrapper) {
      this.name = name;
      this.literal = literal;
      this.wrapper = wrapper;
    }
  }

  private HxConstants() {
  }
}
