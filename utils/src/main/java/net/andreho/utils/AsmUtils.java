package net.andreho.utils;

import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.asm.org.objectweb.asm.signature.SignatureReader;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

import static net.andreho.asm.org.objectweb.asm.Opcodes.ARETURN;
import static net.andreho.asm.org.objectweb.asm.Opcodes.DRETURN;
import static net.andreho.asm.org.objectweb.asm.Opcodes.FRETURN;
import static net.andreho.asm.org.objectweb.asm.Opcodes.IRETURN;
import static net.andreho.asm.org.objectweb.asm.Opcodes.LRETURN;
import static net.andreho.asm.org.objectweb.asm.Opcodes.POP;
import static net.andreho.asm.org.objectweb.asm.Opcodes.POP2;
import static net.andreho.asm.org.objectweb.asm.Opcodes.RETURN;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public class AsmUtils {
   private static final Type EQUALITY_UTILS = Type.getType(EqualityUtils.class);
   private static final Type EQUALITY_UTILS_FLOAT =
         Type.getType(ReflectionUtils.findMethod(EqualityUtils.class,
                                                 (m) -> "equal".equals(m.getName()) &&
                                                          m.getReturnType() == Boolean.TYPE &&
                                                          m.getParameterCount() == 2 &&
                                                          m.getParameterTypes()[0] == m.getParameterTypes()[1] &&
                                                          m.getParameterTypes()[0] == Float.TYPE
         ));
   private static final Type EQUALITY_UTILS_DOUBLE =
         Type.getType(ReflectionUtils.findMethod(EqualityUtils.class,
                                                 (m) -> "equal".equals(m.getName()) &&
                                                          m.getReturnType() == Boolean.TYPE &&
                                                          m.getParameterCount() == 2 &&
                                                          m.getParameterTypes()[0] == m.getParameterTypes()[1] &&
                                                          m.getParameterTypes()[0] == Double.TYPE
         ));

   /**
    * @param type
    * @return
    */
   public static int getReturnCode(Class<?> type) {
      switch (type.getName()) {
         case "boolean":
         case "byte":
         case "char":
         case "short":
         case "int":
            return IRETURN;
         case "float":
            return FRETURN;
         case "long":
            return LRETURN;
         case "double":
            return DRETURN;
         default:
            return type != Void.TYPE? ARETURN : RETURN;
      }
   }

   /**
    * @param type
    * @return
    */
   public static void popStack(MethodVisitor mv, Class<?> type) {
      if(type == Void.TYPE) {
         return;
      }
      switch (type.getName()) {
         case "long":
         case "double":
            mv.visitInsn(POP2);
         break;
         default:
            mv.visitInsn(POP);
      }
   }

   /**
    * @param primitive
    * @return
    */
   public static char getTypeCodeOfPrimitive(Class<?> primitive) {
      switch (primitive.getName()) {
         case "boolean": return 'Z';
         case "byte": return 'B';
         case "char": return 'C';
         case "short": return 'S';
         case "int": return 'I';
         case "float": return 'F';
         case "long": return 'J';
         case "double": return 'D';
         case "void": return 'V';
         default: throw new IllegalArgumentException();
      }
   }

   /**
    * @param method
    * @return
    */
   public static int invokeCodeOf(Method method) {
      return invokeCodeOf(method, method.getDeclaringClass());
   }

   /**
    * @param method
    * @param fromClass
    * @return
    */
   public static int invokeCodeOf(Method method, Class<?> fromClass) {
      if (fromClass.isInterface() ||
          method.getDeclaringClass()
                .isInterface()) {
         return Opcodes.INVOKEINTERFACE;
      }

      if (Modifier.isStatic(method.getModifiers())) {
         return Opcodes.INVOKESTATIC;
      }

      return Modifier.isPrivate(method.getModifiers()) ?
             Opcodes.INVOKESPECIAL :
             Opcodes.INVOKEVIRTUAL;
   }

   /**
    * Compares two primitives values with the same type that lies as last two values on the operand stack
    * and jumps depending of the <code>jumpIfEqual</code> parameter.
    *
    * @param mv
    * @param primitive
    * @param label
    * @param jumpIfEqual
    * @return <b>true</b> if given type is a primitive and was handled, <b>false</b> otherwise.
    */
   public static boolean comparePrimitives(MethodVisitor mv, Class<?> primitive, Label label, boolean jumpIfEqual) {
      if (primitive.isPrimitive()) {
         if (primitive == Boolean.TYPE ||
             primitive == Byte.TYPE ||
             primitive == Short.TYPE ||
             primitive == Character.TYPE ||
             primitive == Integer.TYPE) {
            mv.visitJumpInsn(jumpIfEqual ?
                             Opcodes.IF_ICMPEQ :
                             Opcodes.IF_ICMPNE,
                             label);
            return true;
         }

         if (primitive == Float.TYPE) {
            //mv.visitInsn(Opcodes.FCMPL);
            mv.visitMethodInsn(
                  Opcodes.INVOKESTATIC,
                  EQUALITY_UTILS.getInternalName(),
                  "equal", EQUALITY_UTILS_FLOAT.getDescriptor(),
                  false);
            //if equal => 1, if not then => 0
         } else if (primitive == Double.TYPE) {
            //mv.visitInsn(Opcodes.DCMPL);
            mv.visitMethodInsn(
                  Opcodes.INVOKESTATIC,
                  EQUALITY_UTILS.getInternalName(),
                  "equal", EQUALITY_UTILS_DOUBLE.getDescriptor(),
                  false);
            //if equal => 1, if not then => 0
         } else if (primitive == Long.TYPE) {
            mv.visitInsn(Opcodes.LCMP);
            //if equal => 0, if not then => -1/+1
            jumpIfEqual = !jumpIfEqual;
         }
         mv.visitJumpInsn(jumpIfEqual ?
                          Opcodes.IFNE :
                          Opcodes.IFEQ,
                          label);
         //else - not possible, i hope ;)
         return true;
      }
      return false;
   }

   /**
    * If given class represents one of primitive types (int, long, byte etc.) then its going to be wrapped using
    * default boxing type (Integer, Long, Byte etc.).
    * It's also expected that the value of primitive is already on the operand stack.
    *
    * @param methodVisitor
    * @param primitive
    * @return
    */
   public static boolean wrapPrimitiveType(MethodVisitor methodVisitor, Class<?> primitive) {
      if (primitive.isPrimitive()) {
         String targetClass;
         String methodSignature;

         if (primitive == Boolean.TYPE) {
            targetClass = "java/lang/Boolean";
            methodSignature = "(Z)Ljava/lang/Boolean;";
         } else if (primitive == Byte.TYPE) {
            targetClass = "java/lang/Byte";
            methodSignature = "(B)Ljava/lang/Byte;";
         } else if (primitive == Short.TYPE) {
            targetClass = "java/lang/Short";
            methodSignature = "(S)Ljava/lang/Short;";
         } else if (primitive == Character.TYPE) {
            targetClass = "java/lang/Character";
            methodSignature = "(C)Ljava/lang/Character;";
         } else if (primitive == Integer.TYPE) {
            targetClass = "java/lang/Integer";
            methodSignature = "(I)Ljava/lang/Integer;";
         } else if (primitive == Float.TYPE) {
            targetClass = "java/lang/Float";
            methodSignature = "(F)Ljava/lang/Float;";
         } else if (primitive == Long.TYPE) {
            targetClass = "java/lang/Long";
            methodSignature = "(J)Ljava/lang/Long;";
         } else if (primitive == Double.TYPE) {
            targetClass = "java/lang/Double";
            methodSignature = "(D)Ljava/lang/Double;";
         } else {
            //else - not possible, i hope ;)
            throw new IllegalStateException("Unsupported primitive type: " + primitive);
         }
         methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, targetClass, "valueOf", methodSignature, false);
         return true;
      }
      return false;
   }

   /**
    * @param methodVisitor
    * @param primitive
    * @return
    */
   public static boolean unwrapPrimitiveType(final MethodVisitor methodVisitor, final Class<?> primitive) {
      if (primitive.isPrimitive()) {
         String targetClass = Type.getInternalName(ReflectionUtils.findWrapper(primitive));
         String methodSignature = "()" + getTypeCodeOfPrimitive(primitive);
         methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, targetClass, primitive.getName()+"Value", methodSignature, false);
         return true;
      }
      return false;
   }

   /**
    * @param cls
    * @return
    */
   public static Class<?> getWrapperTypeIfPrimitive(Class<?> cls) {
      if(cls.isPrimitive()) {
         if (cls == Boolean.TYPE) {
            return Boolean.class;
         } else if (cls == Byte.TYPE) {
            return Byte.class;
         } else if (cls == Short.TYPE) {
            return Short.class;
         } else if (cls == Character.TYPE) {
            return Character.class;
         } else if (cls == Integer.TYPE) {
            return Integer.class;
         } else if (cls == Float.TYPE) {
            return Float.class;
         } else if (cls == Long.TYPE) {
            return Long.class;
         } else if (cls == Double.TYPE) {
            return Double.class;
         }
         throw new IllegalArgumentException("Not a primitive type: "+cls);
      }
      return cls;
   }

   //----------------------------------------------------------------------------------------------------------------

   public static String denormalizeClassname(Class<?> cls) {
      return denormalizeClassname(cls.getName());
   }

   public static String denormalizeClassname(String className) {
      return className.replace('.', '/');
   }

   public static String denormalizeSignature(String returnClass, String... signature) {
      StringBuilder builder = new StringBuilder("(");
      for (int i = 0, l = signature.length - 1; i <= l; i++) {
         denormalizeSignature(signature[i], builder);
      }
      return denormalizeSignature(returnClass, builder.append(')')).toString();
   }

   private static StringBuilder denormalizeSignature(String className, StringBuilder builder) {
      switch (className) {
         case "boolean":
            return builder.append('Z');
         case "byte":
            return builder.append('B');
         case "char":
            return builder.append('C');
         case "short":
            return builder.append('S');
         case "int":
            return builder.append('I');
         case "float":
            return builder.append('F');
         case "long":
            return builder.append('J');
         case "double":
            return builder.append('D');
         case "void":
            return builder.append('V');

         default: {
            if (className.endsWith("[]")) {
               int off = className.length();
               while (className.charAt(off - 1) == ']' &&
                      className.charAt(off - 2) == '[') {
                  off -= 2;
                  builder.append('[');
               }
               return denormalizeSignature(className.substring(0, off), builder);
            }

            return builder.append('L')
                          .append(className.replace('.', '/'))
                          .append(';');
         }
      }
   }

   /**
    * @param cls
    * @return
    */
   public static String normalizeClassname(Class<?> cls) {
      if (cls.isArray()) {
         int dim = 0;

         do {
            dim++;
            cls = cls.getComponentType();
         }
         while (cls.isArray());

         StringBuilder builder = new StringBuilder(cls.getName());

         while (dim-- > 0) {
            builder.append("[]");
         }

         return builder.toString();
      }
      return cls.getName();
   }

   /**
    * @param classname
    * @return
    */
   public static String normalizeClassname(String classname) {
      Objects.requireNonNull(classname, "Class name is null");
      return normalizeType(Type.getType(classname.replace('.', '/')));
   }

   /**
    * @param classNames
    * @return
    */
   public static String[] normalizeClassnames(String... classNames) {
      String[] result = classNames.clone();

      for (int i = 0; i < classNames.length; i++) {
         result[i] = normalizeClassname(classNames[i]);
      }

      return result;
   }

   /**
    * @param classname
    * @param signature
    * @return
    */
   public static String normalizeClassname(String classname, String signature) {
      return normalizeClassname(classname);
   }

   /*
    * type: "<T:Ljava/lang/Object;>Ljava/util/AbstractCollection<TT;>;
    * Ljava/lang/Comparable<Lnet/andreho/haxxor/cases/Node<TT;>;>;"
    * field: TT;
    * method: <T:Ljava/lang/Object;>()TT;
    * method: (IZLjava/lang/String;Ljava/util/List<-Ljava/lang/Number;>;)
    * Ljava/util/Map<Ljava/util/Collection<Ljava/lang/String;>;Ljava/util/Map$Entry<TT;+Ljava/lang/Number;>;>;
    *
    */
   public static String normalizeMethodGenericSignature(String signature) {
      SignatureReader reader = new SignatureReader(signature);


      return "";
   }

   /**
    * @param signature
    * @return
    */
   public static String[] normalizeSignature(String signature) {
      Type[] types = Type.getArgumentTypes(signature);
      String[] names = new String[types.length];
      for (int i = 0; i < types.length; i++) {
         names[i] = normalizeType(types[i]);
      }
      return names;
   }

   /**
    * @param signature
    * @return
    */
   public static String normalizeReturnType(String signature) {
      return normalizeType(Type.getReturnType(signature));
   }

   private static String normalizeType(Type type) {
      switch (type.getSort()) {
         case Type.BOOLEAN:
            return "Z";
         case Type.BYTE:
            return "B";
         case Type.CHAR:
            return "C";
         case Type.SHORT:
            return "S";
         case Type.INT:
            return "I";
         case Type.FLOAT:
            return "F";
         case Type.LONG:
            return "J";
         case Type.DOUBLE:
            return "D";
         case Type.VOID:
            return "V";
      }
      return type.getInternalName();
   }
}
