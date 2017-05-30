package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.util.ASMifier;
import net.andreho.asm.org.objectweb.asm.util.Printer;
import net.andreho.asm.org.objectweb.asm.util.TraceClassVisitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by a.hofmann on 11.04.2015.
 */
public class Debugger {
   /**
    * Flag to skip method code. If this class is set <code>CODE</code>
    * attribute won't be visited. This can be used, for example, to retrieve
    * annotations for methods and method parameters.
    */
   public static final int SKIP_CODE = 1;
   /**
    * Flag to skip the debug information in the class. If this flag is set the
    * debug information of the class is not visited, i.e. the
    * {@link MethodVisitor#visitLocalVariable visitLocalVariable} and
    * {@link MethodVisitor#visitLineNumber visitLineNumber} methods will not be
    * called.
    */
   public static final int SKIP_DEBUG = 2;
   /**
    * Flag to skip the stack map frames in the class. If this flag is set the
    * stack map frames of the class is not visited, i.e. the
    * {@link MethodVisitor#visitFrame visitFrame} method will not be called.
    * This flag is useful when the {@link ClassWriter#COMPUTE_FRAMES} option is
    * used: it avoids visiting frames that will be ignored and recomputed from
    * scratch in the class writer.
    */
   public static final int SKIP_FRAMES = 4;
   /**
    * Flag to expand the stack map frames. By default stack map frames are
    * visited in their original format (i.e. "expanded" for classes whose
    * version is less than V1_6, and "compressed" for the other classes). If
    * this flag is set, stack map frames are always visited in expanded format
    * (this option adds a decompression/recompression step in ClassReader and
    * ClassWriter which degrades performances quite a lot).
    */
   public static final int EXPAND_FRAMES = 8;

   /**
    * Reads the content of given stream into a byte array
    * <b>ATTENTION:</b> input still must be closed by caller.
    *
    * @param inputStream
    * @return
    */
   public static byte[] toByteArray(InputStream inputStream) throws IOException {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      int count;
      byte[] buffer = new byte[4096];
      while ((count = inputStream.read(buffer)) > -1) {
         outputStream.write(buffer, 0, count);
      }
      return outputStream.toByteArray();
   }

   /**
    * Prints byte code of the given class
    *
    * @param className of class whose bytecode to trace
    */
   public static void trace(String className) {
      trace(className, new ASMifier());
   }

   /**
    * Prints byte code of the given class
    * @param cls whose bytecode to trace
    */
   public static void trace(Class<?> cls) {
      trace(cls.getName(), new ASMifier());
   }

   /**
    * Prints byte code of the given class using selected printer
    *
    * @param className of class whose bytecode to trace
    * @param flags to decide what to trace
    * @see Debugger#SKIP_CODE
    * @see Debugger#SKIP_DEBUG
    * @see Debugger#SKIP_FRAMES
    */
   public static void trace(String className, int flags) {
      trace(className, new ASMifier(), new PrintWriter(System.out), flags);
   }

   /**
    * Prints byte code of the given class using selected printer
    *
    * @param cls   whose bytecode to trace
    * @param flags to decide what to trace
    * @see Debugger#SKIP_CODE
    * @see Debugger#SKIP_DEBUG
    * @see Debugger#SKIP_FRAMES
    */
   public static void trace(Class<?> cls, int flags) {
      trace(cls.getName(), new ASMifier(), new PrintWriter(System.out), flags);
   }

   /**
    * Prints byte code of the given class using selected printer
    *
    * @param className to print
    * @param printer   to output to
    */
   public static void trace(String className, Printer printer) {
      trace(className, printer, new PrintWriter(System.out));
   }

   /**
    * Prints byte code of the given class using selected printer
    *
    * @param className to print
    * @param printer   to output to
    * @param flags
    */
   public static void trace(String className, Printer printer, int flags) {
      trace(className, printer, new PrintWriter(System.out), flags);
   }

   /**
    * Prints byte code of the given class using selected printer
    *
    * @param className   to print
    * @param printer     to output to
    * @param printWriter
    */
   public static void trace(String className, Printer printer, PrintWriter printWriter) {
      trace(className, printer, printWriter, 0);
   }

   /**
    * @param className
    * @param printer
    * @param printWriter
    * @param flags
    */
   public static void trace(String className, Printer printer, PrintWriter printWriter, int flags) {
      className = className.replace('.', '/') + ".class";

      try (final InputStream stream = ClassLoader.getSystemResourceAsStream(className)) {
         if (stream == null) {
            throw new NullPointerException("Unable to reference: " + className);
         }

         trace(toByteArray(stream), printer, printWriter, flags);
      } catch (Exception e) {
         throw new IllegalStateException("Unable to process stream or given class: " + className);
      }
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param byteCode
    */
   public static void trace(byte[] byteCode) {
      trace(byteCode, new ASMifier(), new PrintWriter(System.out));
   }

   /**
    * @param byteCode
    * @param printer
    */
   public static void trace(byte[] byteCode, Printer printer) {
      trace(byteCode, printer, new PrintWriter(System.out));
   }

   /**
    * @param byteCode
    * @param printer
    * @param printWriter
    */
   public static void trace(byte[] byteCode, Printer printer, PrintWriter printWriter) {
      trace(byteCode, printer, printWriter, 0);
   }

   /**
    * @param byteCode
    * @param printer
    * @param printWriter
    * @param flags
    */
   public static void trace(byte[] byteCode, Printer printer, PrintWriter printWriter, int flags) {
      final ClassReader classReader = new ClassReader(byteCode);
      final TraceClassVisitor visitor = new TraceClassVisitor(null, printer, printWriter);
      classReader.accept(visitor, flags);
   }
}
