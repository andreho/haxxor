package net.andreho.utils;

import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 28.05.2015.<br/>
 */
public class ReflectionUtils {
   private final static Class<?>[] EMPTY_CLASS_ARRAY = {};

   private final static Map<Class<?>, Class<?>> primitiveToWrappers;
   private final static Map<Class<?>, Class<?>> wrappersToPrimitives;

   static {
      primitiveToWrappers = new IdentityHashMap<>();
      primitiveToWrappers.put(Boolean.TYPE, Boolean.class);
      primitiveToWrappers.put(Byte.TYPE, Byte.class);
      primitiveToWrappers.put(Character.TYPE, Character.class);
      primitiveToWrappers.put(Short.TYPE, Short.class);
      primitiveToWrappers.put(Integer.TYPE, Integer.class);
      primitiveToWrappers.put(Float.TYPE, Float.class);
      primitiveToWrappers.put(Long.TYPE, Long.class);
      primitiveToWrappers.put(Double.TYPE, Double.class);

      wrappersToPrimitives = new IdentityHashMap<>();
      wrappersToPrimitives.put(Boolean.class, Boolean.TYPE);
      wrappersToPrimitives.put(Byte.class, Byte.TYPE);
      wrappersToPrimitives.put(Character.class, Character.TYPE);
      wrappersToPrimitives.put(Short.class, Short.TYPE);
      wrappersToPrimitives.put(Integer.class, Integer.TYPE);
      wrappersToPrimitives.put(Float.class, Float.TYPE);
      wrappersToPrimitives.put(Long.class, Long.TYPE);
      wrappersToPrimitives.put(Double.class, Double.TYPE);
   }

   /**
    * @param lhsType
    * @param rhsType
    * @return
    */
   public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
      Objects.requireNonNull(lhsType, "Left-hand side type must not be null");
      Objects.requireNonNull(rhsType, "Right-hand side type must not be null");

      if (lhsType.isAssignableFrom(rhsType)) {
         return true;
      }
      if (lhsType.isPrimitive()) {
         Class<?> resolvedPrimitive = wrappersToPrimitives.get(rhsType);
         if (lhsType == resolvedPrimitive) {
            return true;
         }
      } else {
         Class<?> resolvedWrapper = primitiveToWrappers.get(rhsType);
         if (resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper)) {
            return true;
         }
      }
      return false;
   }

   /**
    * @param method
    * @return
    */
   public static boolean isExecutable(Method method) {
      Objects.requireNonNull(method);
      return !Modifier.isAbstract(method.getModifiers()) ||
             (
                !method.getDeclaringClass().isAnnotation() &&
                method.isDefault()
             );
   }

   /**
    * @param o
    * @return {@link Void#TYPE} if the given instance is null otherwise a class of the given instance
    */
   public static Class<?> getNullSafeClass(Object o) {
      return o == null ? void.class : o.getClass();
   }

   /**
    * @param o
    * @return "null" if the given instance is null otherwise a class name of the given instance
    */
   public static String getNullSafeClassName(Object o) {
      return o == null ? "null" : o.getClass()
                                   .getName();
   }

   /**
    * @param aClass to check whether it's a primitive wrapper type or not
    * @return <b>true</b> if the given type is a primitive wrapper such a {@link Integer},{@link Double},
    * {@link Boolean} etc.
    */
   public static boolean isWrapper(Class<?> aClass) {
      return wrappersToPrimitives.containsKey(aClass);
   }

   /**
    * @param primitiveType
    * @return corresponding <b>wrapper type</b> if the given type was a primitive one (int.class, double.class,
    * boolean.class etc.),
    * or the given type itself.
    */
   public static Class<?> findWrapper(Class<?> primitiveType) {
      return primitiveToWrappers.getOrDefault(primitiveType, primitiveType);
   }

   /**
    * @param aWrapperType is a possible wrapper type
    * @return
    */
   public static Class<?> findWrappable(Class<?> aWrapperType) {
      return wrappersToPrimitives.getOrDefault(aWrapperType, aWrapperType);
   }

   /**
    * Tries to load a class by given name
    *
    * @param className
    * @param loader
    * @return appropriate class (or null if not found)
    */
   public static Class<?> locateClass(String className, ClassLoader loader) {
      Class<?> cls = null;

      if (loader != null) {
         cls = locateClassUsing(className, loader);
      }
      if (cls == null && (loader = Thread.currentThread()
                                         .getContextClassLoader()) != null) {
         cls = locateClassUsing(className, loader);
      }
      return cls;
   }


   private static Class<?> locateClassUsing(String className, ClassLoader loader) {
      try {
         return loader.loadClass(className);
      } catch (ClassNotFoundException e) {
         //IGNORE
      }
      return null;
   }

   private static volatile Method defineClassMethod;

   /**
    * Tries to load the given bytecode into
    * the given class loader instance without checking whether it was already loaded or not.
    *
    * @param className   is a binary name (e.g.: <code>java.lang.String</code>) of the defined class in the given
    *                    byte code (may be null)
    * @param byteCode    itself
    * @param classLoader to use for loading
    * @return a loaded class instance associated with provided class
    * @implNote <b>this method is potentially a security breach</b>
    */
   public static Class<?> loadByteCode(String className, byte[] byteCode, ClassLoader classLoader) {
      Objects.requireNonNull(byteCode, "ByteCode is null");
      Objects.requireNonNull(classLoader, "ClassLoader is null");

      if (defineClassMethod == null) {
         try {
            defineClassMethod =
               ClassLoader.class.getDeclaredMethod("defineClass",
                                                   String.class, byte[].class, int.class, int.class);
            defineClassMethod.setAccessible(true);

         } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
         }
      }

      try {
         return (Class<?>) defineClassMethod.invoke(classLoader, className, byteCode, 0, byteCode.length);
      } catch (IllegalAccessException | InvocationTargetException e) {
         throw new IllegalStateException(e);
      }
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * Makes given type conform with binary class naming schema (without leading '[', '[L' or 'class ')
    *
    * @param type to normalize if needed
    * @return a normalized type name
    */
   public static String normalizeClassName(Type type) {
      if (type instanceof Class) {
         Class cls = (Class) type;

         if (cls.isArray()) {
            return normalizeArrayType(cls);
         } else if (cls.isPrimitive()) {
            return normalizePrimitiveType(cls);
         }
         return cls.getName();
      }
      return type.getTypeName();
   }

   private static String normalizeArrayType(Class<?> cls) {
      int dim = 0;

      while (cls.isArray()) {
         dim++;
         cls = cls.getComponentType();
      }

      StringBuilder builder = new StringBuilder();

      if (cls.isPrimitive()) {
         builder.append(normalizePrimitiveType(cls));
      } else {
         builder.append(cls.getName());
      }

      while (dim-- > 0) {
         builder.append("[]");
      }

      return builder.toString();
   }

   private static String normalizePrimitiveType(Class cls) {
      if (cls == Boolean.TYPE) {
         return "boolean";
      }
      if (cls == Byte.TYPE) {
         return "byte";
      }
      if (cls == Character.TYPE) {
         return "char";
      }
      if (cls == Short.TYPE) {
         return "short";
      }
      if (cls == Integer.TYPE) {
         return "int";
      }
      if (cls == Float.TYPE) {
         return "float";
      }
      if (cls == Long.TYPE) {
         return "long";
      }
      if (cls == Double.TYPE) {
         return "double";
      }
      return "void";
   }

   //----------------------------------------------------------------------------------------------------------------

   public static String createSignature(Executable executable) {
      final StringBuilder builder = new StringBuilder();
      if (executable instanceof Method) {
         Method method = (Method) executable;
         builder.append(method.getName());
      } else {
         builder.append("<init>");
      }

      builder.append('(');

      for (Class<?> parameter : executable.getParameterTypes()) {
         builder.append(normalizeClassName(parameter)).append(',');
      }

      if (builder.charAt(builder.length() - 1) == ',') {
         builder.setLength(builder.length() - 1);
      }

      return builder.append(')').toString();
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param type
    * @return
    */
   public static Class extractClass(Type type) {
      if (type instanceof Class) {
         return (Class<?>) type;
      }
      if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType) type;
         return extractClass(parameterizedType.getRawType());
      }
      if (type instanceof GenericArrayType) {
         GenericArrayType genericArrayType = (GenericArrayType) type;
         return Array.newInstance(extractClass(genericArrayType.getGenericComponentType()), 0)
                     .getClass();
      }
      if (type instanceof WildcardType) {
         WildcardType wildcardType = (WildcardType) type;

         final Type[] lowerBounds = wildcardType.getLowerBounds();
         final Type[] upperBounds = wildcardType.getUpperBounds();

         if (lowerBounds.length > 0) {
            Type lowerBound = lowerBounds[0];

            if (lowerBound != Object.class) {
               return extractClass(lowerBound);
            }
         }

         if (upperBounds.length > 0) {
            return extractClass(upperBounds[0]);
         }

         return Object.class;
      }
      throw new IllegalArgumentException(type.getTypeName());
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * Selects a constructor that doesn't expect any arguments.
    *
    * @param aClass declaring a constructors
    * @param <T>
    * @return a constructor reference that expects no arguments
    */
   public static <T> Constructor<T> findConstructor(final Class<? extends T> aClass) {
      return findConstructor(aClass, EMPTY_CLASS_ARRAY);
   }

   /**
    * @param aClass
    * @param argTypes
    * @param <T>
    * @return
    */
   public static <T> Constructor<T> findConstructor(final Class<? extends T> aClass, Class<?>... argTypes) {
      loop:
      for (Constructor<?> constructor : aClass.getDeclaredConstructors()) {
         if (constructor.getParameterCount() == argTypes.length) {
            Class<?>[] paramTypes = constructor.getParameterTypes();

            for (int i = 0; i < argTypes.length; i++) {
               Class<?> a = findWrapper(paramTypes[i]);
               Class<?> b = findWrapper(argTypes[i]);

               if (!a.isAssignableFrom(b)) {
                  continue loop;
               }
            }
            return (Constructor<T>) constructor;
         }
      }

      return null;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param aClass
    * @param selector
    * @return
    */
   public static List<Class<?>> findInterfaces(final Class<?> aClass, final Predicate<Class<?>> selector) {
      return new ArrayList<>(findInterfaces(aClass, selector, new LinkedHashSet<>(16)));
   }

   /**
    * @param aClass
    * @param selector
    * @param interfaces
    * @param <C>
    * @return
    */
   public static <C extends Collection<Class<?>>> C findInterfaces(final Class<?> aClass,
                                                                   final Predicate<Class<?>> selector,
                                                                   final C interfaces) {
      Class<?> current = aClass;

      if (current.isInterface() &&
          selector.test(current)) {
         interfaces.add(current);
      }

      while (current != null) {
         for (Class<?> ifc : current.getInterfaces()) {
            findInterfaces(ifc, selector, interfaces);
         }

         current = current.getSuperclass();
      }

      return interfaces;
   }

   //----------------------------------------------------------------------------------------------------------------


   /**
    * Selects sufficient annotations on given class and its super-classes and also its implemented interfaces using
    * given predicate.<br/>
    * <b>Note:</b> <code>extends</code> path comes before <code>implements</code> path
    *
    * @param aClass
    * @param selector
    * @return
    */
   public static List<Annotation> findAnnotations(final Class<?> aClass, final Predicate<Annotation> selector) {
      return findAnnotations(aClass, selector, new ArrayList<>());
   }

   /**
    * @param aClass
    * @param selector
    * @param annotations
    * @param <C>
    * @return
    */
   public static <C extends Collection<Annotation>> C findAnnotations(final Class<?> aClass,
                                                                      final Predicate<Annotation> selector,
                                                                      final C annotations) {
      Class<?> current = aClass;

      if (current != null) {
         for (Annotation annotation : current.getDeclaredAnnotations()) {
            if (selector.test(annotation)) {
               annotations.add(annotation);
            }
         }

         //Visit super class before any further sub-interfaces
         findAnnotations(current.getSuperclass(), selector, annotations);

         for (Class<?> ifc : current.getInterfaces()) {
            findAnnotations(ifc, selector, annotations);
         }
      }

      return annotations;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param aClass
    * @param selector
    * @return
    */
   public static Method findMethod(final Class<?> aClass, final Predicate<Method> selector) {
      Class<?> current = aClass;

      Method result = null;

      while (current != null) {
         for (Method method : current.getDeclaredMethods()) {
            if (selector.test(method)) {
               return method;
            }
         }

         for (Class<?> ifc : current.getInterfaces()) {
            result = findMethod(ifc, selector);

            if (result != null) {
               return result;
            }
         }

         current = current.getSuperclass();
      }

      return result;
   }

   /**
    * @param aClass
    * @param selector
    * @return
    */
   public static List<Method> findMethods(final Class<?> aClass, final Predicate<Method> selector) {
      return new ArrayList<>(findMethods(aClass, selector, new LinkedHashSet<>(16)));
   }

   /**
    * @param aClass
    * @param selector
    * @param resultList
    * @param <T>
    * @return
    */
   public static <T extends Collection<Method>> T findMethods(
      final Class<?> aClass,
      final Predicate<Method> selector,
      final T resultList) {

      Class<?> current = aClass;

      while (current != null) {
         collectMethods(current, selector, resultList);
         current = current.getSuperclass();
      }

      current = aClass;

      while (current != null) {
         for (Class<?> ifc : current.getInterfaces()) {
            findMethods(ifc, selector, resultList);
         }
         current = current.getSuperclass();
      }

      return resultList;
   }

   static void collectMethods(final Class<?> aClass,
                              final Predicate<Method> selector,
                              final Collection<Method> selected) {

      final Method[] methods = aClass.getDeclaredMethods();

      for (Method method : methods) {
         if (selector.test(method)) {
            selected.add(method);
         }
      }
   }

   //----------------------------------------------------------------------------------------------------------------

   public static Collection<Class<?>> findClasses(final Class<?> aClass, final Predicate<Class<?>> predicate) {
      return findClasses(aClass, predicate, new LinkedHashSet<>(16));
   }

   public static <C extends Collection<Class<?>>> C findClasses(final Class<?> aClass,
                                                                final Predicate<Class<?>> predicate, final C output) {

      Class<?> current = aClass;
      while (current != null) {
         if (predicate.test(current)) {
            output.add(current);
         }
         current = current.getSuperclass();
      }

      current = aClass;
      while (current != null) {
         for (Class<?> itf : current.getInterfaces()) {
            findClasses(itf, predicate, output);
         }
         current = current.getSuperclass();
      }

      return output;
   }

   public static Collection<Class<?>> findSubClasses(final Class<?> aClass, final Predicate<Class<?>> predicate) {
      return findSubClasses(aClass, predicate, new LinkedHashSet<>(16));
   }

   public static <C extends Collection<Class<?>>> C findSubClasses(final Class<?> aClass,
                                                                final Predicate<Class<?>> predicate, final C output) {

      Class<?> current = aClass;
      while (current != null) {
         if (predicate.test(current)) {
            output.add(current);
         }
         current = current.getSuperclass();
      }

      return output;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param aClass
    * @param name
    * @return
    */
   public static Field findField(final Class<?> aClass, final String name) {
      final List<Field> fields = findFields(aClass, (f) -> name.equals(f.getName()));
      return fields.isEmpty() ? null : fields.get(0);
   }

   /**
    * @param aClass
    * @param selector
    * @return
    */
   public static List<Field> findFields(final Class<?> aClass, final Predicate<Field> selector) {
      return new ArrayList<>(findFields(aClass, selector, new LinkedHashSet<>(16)));
   }

   /**
    * @param aClass
    * @param selector
    * @param resultList
    * @param <T>
    * @return
    */
   public static <T extends Collection<Field>> T findFields(
      final Class<?> aClass,
      final Predicate<Field> selector,
      final T resultList) {

      Class<?> current = aClass;

      while (current != null && current != Object.class) {
         for (Field field : current.getDeclaredFields()) {
            if (selector.test(field)) {
               resultList.add(field);
            }
         }

         current = current.getSuperclass();
      }

      return resultList;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param aClass
    * @param selector
    * @return
    */
   public static Method findFirstMethod(final Class<?> aClass, final Predicate<Method> selector) {
      Class<?> current = aClass;
      while (current != null) {
         Method method = peekFirstMethod(current, selector);
         if (method != null) {
            return method;
         }
         current = current.getSuperclass();
      }

      current = aClass;
      while (current != null) {
         for (Class<?> ifc : current.getInterfaces()) {
            Method method = peekFirstMethod(ifc, selector);
            if (method != null) {
               return method;
            }
         }
         current = current.getSuperclass();
      }

      return null;
   }

   static Method peekFirstMethod(final Class<?> aClass, final Predicate<Method> selector) {
      final Method[] methods = aClass.getDeclaredMethods();

      for (Method method : methods) {
         if (selector.test(method)) {
            return method;
         }
      }
      return null;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param declaringClass
    * @param subClass
    * @param genericType
    * @param minimalGenericType
    * @return
    */
   public static Type resolveGenericType(final Class<?> declaringClass,
                                         final Class<?> subClass,
                                         Type genericType,
                                         Type minimalGenericType) {
      if (!declaringClass.isAssignableFrom(subClass)) {
         throw new IllegalArgumentException(
            "Declaring class must be inherited by given not abstract class: " + subClass.getName() + " -> " +
            declaringClass);
      }

      Class<?> current = subClass;

      final List<Map<String, Type>> genericInfo = new ArrayList<>(6);

      while (current != null && current != Object.class) {
         final Class<?> superClass = current.getSuperclass();

         Map<String, Type> genericSpec = new HashMap<>(0);

         genericInfo.add(genericSpec);

         final Type genericSuperclass = current.getGenericSuperclass();

         final TypeVariable<? extends Class<?>>[] typeParameters = superClass.getTypeParameters();

         for (int i = 0; i < typeParameters.length; i++) {
            final TypeVariable<?> typeVariable = typeParameters[i];
            Type type = Object.class;

            if (genericSuperclass instanceof ParameterizedType) {
               ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
               type = parameterizedType.getActualTypeArguments()[i];
            }
            genericSpec.put(typeVariable.getName(), type);
         }

         if (declaringClass.equals(current)) {
            return resolveGenericType(genericType, minimalGenericType, genericInfo, genericInfo.size() - 1);
         }

         current = superClass;
      }

      return genericType;
   }

   /**
    * @param genericType
    * @param minimalExpectation
    * @param specialization
    * @param index
    * @return
    */
   static Type resolveGenericType(Type genericType,
                                  Type minimalExpectation,
                                  List<Map<String, Type>> specialization,
                                  int index) {
      if (genericType instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType) genericType;
         final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

         boolean changed = false;

         for (int i = 0; i < actualTypeArguments.length; i++) {
            Type type = actualTypeArguments[i];

            if (type instanceof TypeVariable) {
               changed = true;
               actualTypeArguments[i] = resolveGenericType(type,
                                                           minimalExpectation,
                                                           specialization,
                                                           index);
            } else if (type instanceof ParameterizedType) {
               Type resolved = resolveGenericType(type,
                                                  type,
                                                  specialization,
                                                  index);
               actualTypeArguments[i] = resolved;
               changed = changed || resolved != type;
            }
         }

         if (changed) {
            return ParameterizedTypeImpl.make(
               (Class<?>) parameterizedType.getRawType(),
               actualTypeArguments,
               parameterizedType.getOwnerType());
         }
      } else if (genericType instanceof GenericArrayType) {
         GenericArrayType genericArrayType = (GenericArrayType) genericType;
         return GenericArrayTypeImpl.make(resolveGenericType(
            genericArrayType.getGenericComponentType(),
            minimalExpectation,
            specialization,
            index));
      } else if (genericType instanceof TypeVariable) {
         TypeVariable typeVariable = (TypeVariable) genericType;
         genericType = specialization.get(index)
                                     .get(typeVariable.getName());

         if ((genericType instanceof TypeVariable || genericType == null) &&
             specialization.size() > 1) {
            for (int i = index - 1; i >= 0; i--) {
               genericType = specialization.get(i)
                                           .get(typeVariable.getName());

               if (genericType instanceof TypeVariable) {
                  typeVariable = (TypeVariable) genericType;
               } else if (genericType != null) {
                  return resolveGenericType(genericType,
                                            minimalExpectation,
                                            specialization,
                                            index);
               }
            }
         }
         return minimalExpectation;
      }
      return genericType;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param type
    * @return
    */
   public static boolean hasTypeVariable(Type type) {
      if (type instanceof TypeVariable) {
         return true;
      } else if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType) type;
         for (Type sub : parameterizedType.getActualTypeArguments()) {
            if (hasTypeVariable(sub)) {
               return true;
            }
         }
      } else if (type instanceof GenericArrayType) {
         GenericArrayType genericArrayType = (GenericArrayType) type;
         return hasTypeVariable(genericArrayType.getGenericComponentType());
      } else if (type instanceof WildcardType) {
         WildcardType wildcardType = (WildcardType) type;
         for (Type sub : wildcardType.getLowerBounds()) {
            if (hasTypeVariable(sub)) {
               return true;
            }
         }
         for (Type sub : wildcardType.getUpperBounds()) {
            if (hasTypeVariable(sub)) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * @param cls to check
    * @return <b>true</b> if the given class may be instantiated (possible access of constructors isn't checked),
    * <b>false</b> otherwise.
    */
   public static boolean isInstantiable(Class<?> cls) {
      return !(cls.isPrimitive() ||
               cls.isEnum() ||
               cls.isArray() ||
               cls.isInterface() ||
               Modifier.isAbstract(cls.getModifiers()));
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * Estimates how far are two types from each other. The 'extends' hops are counted as 1 and 'implements' hops as
    * 1000;
    * For example distance from <code>Integer.class</code> to <code>Number.class</code> is 2 (Integer[1] -> Number[0]).
    *
    * @param lhs is the type left in an assignment (LHS = ...)
    * @param rhs is the type right in an assignment (... = RHS)
    * @return count of jumps from one class to another one or {@link Integer#MAX_VALUE} if not assignable
    * @implSpec if the left type in assignment is an {@link Object} then 1_000_000_000 is returned
    */
   public static int estimateDistance(Class<?> lhs, Class<?> rhs) {
      return estimateDistance(lhs, rhs, 0);
   }

   private static int estimateDistance(final Class<?> lhs, final Class<?> rhs, int count) {
      if (lhs.equals(rhs)) {
         return count;
      }

      //Check whether the given left hand site class is assignable to the right hand part
      if (!lhs.isAssignableFrom(rhs)) {
         return Integer.MAX_VALUE;
      }

      if (rhs.isArray() && lhs.isArray()) {
         return estimateDistance(lhs.getComponentType(), rhs.getComponentType());
      }

      Class<?> current = rhs;

      do {

         if (lhs.isInterface()) {
            //then right hand site must be interface compatible
            final Class<?>[] interfaces = current.getInterfaces();

            int itfPos = 0;
            for (Class<?> itf : interfaces) {
               itfPos++;
               if (lhs.equals(itf)) {
                  return count + (itfPos * 1_000);
               }
            }

            for (Class<?> itf : interfaces) {
               count += 1_000_000;

               if (itf.isAssignableFrom(lhs)) {
                  return estimateDistance(lhs, itf, count);
               }
            }
         }

         count++;
         current = current.getSuperclass();
      }
      while (!current.equals(lhs));

      return current == Object.class ? 1_000_000_000 : count;
   }
}
