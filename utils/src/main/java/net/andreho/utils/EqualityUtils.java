package net.andreho.utils;

/**
 * <br/>Created by andreho on 4/4/16 at 10:06 PM.<br/>
 */
public class EqualityUtils {
   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(boolean a, boolean b) {
      return a == b;
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(byte a, byte b) {
      return a == b;
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(short a, short b) {
      return a == b;
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(char a, char b) {
      return a == b;
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(int a, int b) {
      return a == b;
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(long a, long b) {
      return a == b;
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(float a, float b) {
      return Float.floatToIntBits(a) ==
             Float.floatToIntBits(b);
   }

   /**
    * @param a
    * @param b
    * @return
    */
   public static boolean equal(double a, double b) {
      return Double.doubleToLongBits(a) ==
             Double.doubleToLongBits(b);
   }
}
