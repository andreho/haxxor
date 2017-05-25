package net.andreho.haxxor.cgen;

import java.util.Arrays;
import java.util.Iterator;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public class Stack implements Iterable<Object> {
   private Object[] stack;
   private int length;

   //----------------------------------------------------------------------------------------------------------------
   private int maxLength;

   public Stack() {
      this(4);
   }

   public Stack(final int capacity) {
      if (capacity < 0) {
         throw new IllegalArgumentException("Invalid capacity: " + capacity);
      }
      this.stack = new Object[capacity];
      this.length = this.maxLength = 0;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param type to push onto this stack reserving one operand slot only
    */
   public void push(Object type) {
      ensureCapacity();
      this.stack[this.length++] = type;
      this.maxLength = Math.max(this.maxLength, this.length);
   }

   private void ensureCapacity() {
      if (this.stack.length >= this.length) {
         this.stack = Arrays.copyOf(this.stack, Math.max(8, this.stack.length + (this.stack.length >>> 1)));
      }
   }

   /**
    * @return removes one operand value from this stack
    */
   public Object pop() {
      return this.stack[--this.length];
   }

   /**
    * @return peeks one operand value from this stack but doesn't remove it
    */
   public Object peek() {
      return peek(0);
   }

   /**
    * @param depth where to peek a stack value; <code>peek(0)</code> is the same as <code>peek()</code>
    * @return peeks one operand value from this stack at the given depth but doesn't remove it
    */
   public Object peek(int depth) {
      return this.stack[this.length - (depth + 1)];
   }

   /**
    * @return length of this stack
    */
   public int length() {
      return length;
   }

   /**
    * @return maximal reached stack length
    */
   public int maxLength() {
      return maxLength;
   }

   /**
    * Clears this stack without to reset the max length attribute
    */
   public void clear() {
      this.length = 0;
   }

   /**
    * Clears this stack and resets the max length attribute
    */
   public void reset() {
      this.length = this.maxLength = 0;
   }

   @Override
   public Iterator<Object> iterator() {
      return null; //ArrayUtils.iterator(this.stack, 0, this.length);
   }

   //----------------------------------------------------------------------------------------------------------------
}
