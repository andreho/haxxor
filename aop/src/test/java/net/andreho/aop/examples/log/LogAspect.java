package net.andreho.aop.examples.log;

import net.andreho.aop.Aspect;
import net.andreho.aop.Get;
import net.andreho.aop.Redefine;
import net.andreho.aop.injectable.Current;
import net.andreho.aop.injectable.Declaring;
import net.andreho.aop.injectable.Intercepted;
import net.andreho.aop.spec.Annotated;
import net.andreho.aop.spec.FieldRef;
import net.andreho.aop.spec.Modifier;
import net.andreho.aop.spec.Scope;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import static net.andreho.aop.spec.Modifiers.STATIC;
import static net.andreho.aop.spec.Modifiers.VOLATILE;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 19:34.
 */
@Aspect(
   value = Log.class,
   scope = {Scope.FIELD}
)
public class LogAspect {

   @Get(
      fields = @FieldRef(
         modifiers = @Modifier(STATIC | VOLATILE),
         annotated = @Annotated(Log.class)
      )
   )
   public static
   @Redefine Logger prepareLogger(
      @Declaring Class<?> type,
      @Intercepted Field field,
      @Current Logger current) {

      if (current != null) {
         return current;
      }

      return initializeLogger(type, field);
   }

   private static Logger initializeLogger(final Class<?> type, final Field field) {
      synchronized (type) {
         final Logger logger = Logger.getLogger(type.getName());
         try {
            field.set(type, logger);
         } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
         }
         return logger;
      }
   }
}
