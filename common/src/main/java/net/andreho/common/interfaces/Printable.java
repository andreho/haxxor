package net.andreho.common.interfaces;

/**
 * This interface must be implemented of a class that needs to support a custom printing (many <b>toString()</b>
 * methods).
 * <br/>
 * Created by a.hofmann
 * At 21.08.2014 19:43
 */
@FunctionalInterface
public interface Printable<T> {
   /**
    * Prints this object using the given print strategy
    *
    * @param printer a strategy that is used for printing
    * @return given printer to eliminate unwanted string creations
    */
   Printer<T> print(Printer<T> printer);
}
