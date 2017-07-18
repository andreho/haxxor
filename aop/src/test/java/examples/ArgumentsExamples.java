package examples;

import net.andreho.args.Arguments;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 07:04.
 */
class ArgumentsExamples {

  static {
    Arguments.install();
  }

  @Test
  void loadArguments()
  throws ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
         InstantiationException {
    //long, Object, float, Object, boolean, int, byte, char, short, double => LOFOZIBCSD
    String classname = Arguments.generateFor("JOFOZIBCSD");
    Class<?> argClass = Class.forName(classname);
    Arguments arguments = (Arguments) argClass.getConstructor(String.class).newInstance("JOFOZIBCSD");
    arguments
      .setLong(0, 10L)
      .setObject(1, "Ok")
      .setFloat(2, 10.5f)
      .setObject(3, new ArrayList<>())
      .setBoolean(4, true)
      .setInt(5, 10)
      .setByte(6, (byte) 10)
      .setChar(7, '1')
      .setShort(8, (short) 10)
      .setDouble(9, 10.0);

    Object[] objects = arguments.toArray();
  }
}
