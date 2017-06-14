package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 06:55.
 */
public abstract class HxCodeGenerationUtils {
  /**
   * @param type
   * @return
   */
  public static boolean isTwoSlotsType(String type) {
    return "J".equals(type) || "D".equals(type);
  }

  /**
   * @param type
   * @return
   */
  public static int getTypeSize(String type) {
    return "J".equals(type) || "D".equals(type)? 2 : 1;
  }

  /**
   * Computes the size of the argument's list.
   *
   * @param desc is the signature's descriptor of a method.
   * @return the expected size of the arguments of the referenced method
   */
  public static int getArgumentsSize(final String desc) {
    int i = 1;
    int n = 0;
    while (true) {
      char car = desc.charAt(i++);
      if (car == ')') {
        return n;
      } else if (car == 'L') {
        while (desc.charAt(i++) != ';') {
        }
        n += 1;
      } else if (car == '[') {
        while ((car = desc.charAt(i)) == '[') {
          ++i;
        }
        if (car == 'D' || car == 'J') {
          n -= 1;
        }
      } else if (car == 'D' || car == 'J') {
        n += 2;
      } else {
        n += 1;
      }
    }
  }

  /**
   * Computes the size of the returned type.
   * @param desc is the signature's descriptor of a method.
   * @return the size of the returned type.
   */
  public static int getReturnSize(final String desc) {
    int i = desc.lastIndexOf(')');
    char c = desc.charAt(i + 1);

    if(c == 'V') {
      return 0;
    } else if(c == 'D' || c == 'J') {
      return 2;
    }
    return 1;
  }

  private HxCodeGenerationUtils() {
  }
}
