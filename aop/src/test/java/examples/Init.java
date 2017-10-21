package examples;
public class Init {
  static { // static <clinit> ()V
    System.out.println("Aufruf des Klasseninitialisierers");
  }
  public Init() { //public <init> ()V
    System.out.println("Aufruf des Konstuktors");
  }
}
