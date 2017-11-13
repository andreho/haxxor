package examples;
public class EnclosingParentClass {
  public static Object enclosingMethod() {
    class LocalClass {
      @Override public String toString() {
        return "LocalClass";
      }
    }
    return new LocalClass();
  }
}
