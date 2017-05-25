package net.andreho.common.interfaces;

/**
 * Interface to implement different print strategies.
 * <br/>
 * Created by a.hofmann
 * At 21.08.2014 19:44
 */
@FunctionalInterface
public interface Printer<T> {
   /**
    * Prints the given value using this printer
    *
    * @param value to print
    * @return this for method chaining
    */
   Printer<T> print(T value);

   /**
    * @return transforms stored content to a string value
    */
   String toString();
}
