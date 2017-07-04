package examples.mutation;

/**
 * Created by 666 on 24.03.2017.
 */
public interface ExampleInterface {
   default String someMethod(String value) {
      return "Failed with " + value;
   }
}
