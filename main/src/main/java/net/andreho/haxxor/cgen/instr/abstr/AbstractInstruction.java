package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.spec.HxAnnotated;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.impl.HxAnnotatedImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractInstruction implements Instruction {
   public static final List<Object> NO_STACK_PUSH = Collections.emptyList();
   //----------------------------------------------------------------------------------------------------------------
   protected static final List<Object> PUSH_NULL = Collections.singletonList(Opcodes.NULL);
   protected static final List<Object> PUSH_UNINITIALIZED_THIS = Collections.singletonList(Opcodes.UNINITIALIZED_THIS);
   protected static final List<Object> PUSH_STRING = Collections.singletonList("java/lang/String");
   protected static final List<Object> PUSH_METHOD = Collections.singletonList("java/lang/invoke/MethodType");
   protected static final List<Object> PUSH_HANDLE = Collections.singletonList("java/lang/invoke/MethodHandle");
   protected static final List<Object> PUSH_TYPE = Collections.singletonList("java/lang/Class");
   protected static final List<Object> PUSH_ADDRESS = Collections.singletonList(Opcodes.INTEGER);
   protected static final List<Object> PUSH_INT = PUSH_ADDRESS;
   protected static final List<Object> PUSH_FLOAT = Collections.singletonList(Opcodes.FLOAT);
   protected static final List<Object> PUSH_LONG = Collections.unmodifiableList(
         Arrays.asList(Opcodes.LONG, Opcodes.TOP));
   protected static final List<Object> PUSH_DOUBLE = Collections.unmodifiableList(
         Arrays.asList(Opcodes.DOUBLE, Opcodes.TOP));

   //----------------------------------------------------------------------------------------------------------------

   protected final int opcode;
   protected Instruction next;
   protected Instruction previous;
   protected HxAnnotated annotated;
   private int index = -1;

   //----------------------------------------------------------------------------------------------------------------

   public AbstractInstruction(int opcode) {
      this.opcode = opcode;
   }

   @Override
   public boolean hasAnnotations() {
      return annotated != null &&
             annotated.getAnnotations().isEmpty();
   }

   @Override
   public int getOpcode() {
      return opcode;
   }

   @Override
   public int getIndex() {
      return index;
   }

   @Override
   public void setIndex(final int index) {
      this.index = index;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public Instruction getNext() {
      return next;
   }

   @Override
   public void setNext(Instruction next) {
      this.next = next;
   }

   @Override
   public Instruction getPrevious() {
      return previous;
   }

   @Override
   public void setPrevious(Instruction previous) {
      this.previous = previous;
   }

   @Override
   public String toString() {
      return "   " + getClass().getSimpleName();
   }

   private HxAnnotated initAnnotated() {
      if (this.annotated == null) {
         this.annotated = new HxAnnotatedImpl();
      }
      return this.annotated;
   }

   @Override
   public Instruction setAnnotations(final Collection<HxAnnotation> annotations) {
      initAnnotated().setAnnotations(annotations);
      return this;
   }

   @Override
   public Collection<HxAnnotation> getAnnotations() {
      return initAnnotated().getAnnotations();
   }

   @Override
   public Collection<HxAnnotated> getSuperAnnotated() {
      return initAnnotated().getSuperAnnotated();
   }

   @Override
   public Collection<HxAnnotation> getAnnotationsByType(final String type) {
      return initAnnotated().getAnnotationsByType(type);
   }

   @Override
   public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, final boolean recursive) {
      return initAnnotated().annotations(predicate, recursive);
   }

   protected static class Utils {
      /**
       * Checks given method name depending on provided opcode
       *
       * @param opcode to test
       * @param name   to test
       */
      public static void checkMethodName(int opcode, String name) {
         if ("<clinit>".equals(name) || (opcode != Opcodes.INVOKESPECIAL && "<init>".equals(name))) {
            throw new IllegalArgumentException("Invalid method name for an interface method call: " + name);
         }
      }

      /**
       * Transforms a type descriptor to an internal name
       *
       * @param desc to transform
       * @return an internal type name
       */
      private static String transformDesc(String desc) {
         //for example: [Ljava/lang/String;
         if (desc.endsWith(";")) {
            String value = desc;
            int index = value.indexOf('L');
            if (index > -1) {
               //array case with, e.g.: [[[L...
               value = desc.substring(0, index);
               //add rest of class name without last character
               value += desc.substring(index + 1, desc.length() - 1);
            }
            desc = value;
         }
         return desc;
      }

      /**
       * Retrieves a type of given element descriptor (descriptor is either of a method or of a field)
       *
       * @param context to use
       * @param desc    to analyse (either of a method or a field)
       * @return a type name in an internal type form (or special constant)
       */
      public static List<Object> retrieveType(Context context, String desc) {
         int off = desc.charAt(0) == '(' ? desc.lastIndexOf(')') + 1 : 0;

         if (off < -1) {
            throw new IllegalArgumentException("Invalid descriptor: " + desc);
         }

         switch (desc.charAt(off)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
               return PUSH_INT;
            case 'F':
               return PUSH_FLOAT;
            case 'J':
               return PUSH_LONG;
            case 'D':
               return PUSH_DOUBLE;
            case 'V':
               return NO_STACK_PUSH;
            default: {
               return context.getPush().prepare().push(Utils.transformDesc(desc.substring(off))).get();
            }
         }
      }
   }
}
