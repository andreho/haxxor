package examples;
import java.util.Collections;
import java.util.List;
public class GenericsExample {
  public static void main(String[] args) {
    List<String> list = Collections.singletonList("hallo");
    System.out.println(/* (String) */ list.get(0)); //Hier findet eine Object zu String Umwandlung statt
  }
}
