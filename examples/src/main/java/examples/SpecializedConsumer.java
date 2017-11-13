package examples;
import java.util.function.Consumer;
public class SpecializedConsumer implements Consumer<String> {
  public static void main(String[] args) {
    Consumer consumer = new SpecializedConsumer();
    consumer.accept(new Integer(1));
  }
  @Override
  public void accept(final String s) {
    System.out.println(s);
  }
}
