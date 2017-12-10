package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.api.Access;
import net.andreho.aop.api.ArgPassing;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.locals.ConstructorLocal;
import net.andreho.aop.api.injectable.locals.FieldLocal;
import net.andreho.aop.api.injectable.locals.MethodLocal;
import net.andreho.aop.api.injectable.locals.ParameterLocal;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class InterceptedParameterInjector
  extends AbstractParameterInjector {

  private static final String HELPERS_CLASS = "net/andreho/aop/spi/Helpers";
  private static final Class<Intercepted> INTERCEPTED_ANNOTATION_TYPE = Intercepted.class;

  private static final Class<Parameter> PARAMETER_TYPE = Parameter.class;
  private static final Class<ParameterLocal> PARAMETER_LOCAL_TYPE = ParameterLocal.class;

  private static final Class<Field> FIELD_TYPE = Field.class;
  private static final Class<FieldLocal> FIELD_LOCAL_TYPE = FieldLocal.class;

  private static final Class<Method> METHOD_TYPE = Method.class;
  private static final Class<MethodLocal> METHOD_LOCAL_TYPE = MethodLocal.class;

  private static final Class<Constructor> CONSTRUCTOR_TYPE = Constructor.class;
  private static final Class<ConstructorLocal> CONSTRUCTOR_LOCAL_TYPE = ConstructorLocal.class;
  private static final Class<ArgPassing> ARG_PASSING_ANNOTATION_TYPE = ArgPassing.class;

  public static final InterceptedParameterInjector INSTANCE = new InterceptedParameterInjector();

  public InterceptedParameterInjector() {
  }

  @Override
  public int score(final AspectContext context,
                   final HxMethod original,
                   final HxMethod interceptor,
                   final HxParameter parameter) {
    if(!parameter.isAnnotationPresent(INTERCEPTED_ANNOTATION_TYPE)) {
      return NOT_INJECTABLE;
    }

    final AspectAdviceType aspectAdviceType = context.getAspectAdvice().getType();
    final Hx haxxor = original.getHaxxor();

    int score;
    if(aspectAdviceType.isActivatedThrough(ARG_PASSING_ANNOTATION_TYPE)) {
      score = calcDistance(haxxor, parameter, PARAMETER_LOCAL_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
      score = calcDistance(haxxor, parameter, PARAMETER_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
    } else if(aspectAdviceType.isActivatedThrough(Access.Get.class) ||
              aspectAdviceType.isActivatedThrough(Access.Set.class)) {
      score = calcDistance(haxxor, parameter, FIELD_LOCAL_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
      score = calcDistance(haxxor, parameter, FIELD_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
    } else if(original.isConstructor()) {
      score = calcDistance(haxxor, parameter, CONSTRUCTOR_LOCAL_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
      score = calcDistance(haxxor, parameter, CONSTRUCTOR_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
    } else {
      score = calcDistance(haxxor, parameter, METHOD_LOCAL_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
      score = calcDistance(haxxor, parameter, METHOD_TYPE);
      if(score > NOT_INJECTABLE) {
        return score;
      }
    }

    return parameter.hasType(Object.class)? MIN_SUITABLE : NOT_INJECTABLE;
  }

  private int calcDistance(final Hx haxxor,
                           final HxParameter parameter,
                           final Class<?> type) {
    HxType parameterType = haxxor.reference(type);
    return parameterType.distanceTo(parameter.getType());
  }

  @Override
  public void inject(final AspectAdvice<?> aspectAdvice,
                     final AspectContext context,
                     final HxMethod original,
                     final HxMethod shadow,
                     final HxMethod interceptor,
                     final HxParameter parameter,
                     final HxInstruction anchor) {
    String key;
    final HxType declaringType = shadow.getDeclaringType();
    final HxMethod classInitializer = declaringType.findOrCreateClassInitializer().get();
    final HxExtendedCodeStream stream = anchor.asAnchoredStream();

    final HxInstruction returnInst =
      classInitializer.getBody().getLast().findFirstWithType(HxInstructionTypes.Exit.RETURN).get();

    final AspectAdviceType aspectAdviceType = context.getAspectAdvice().getType();

    if(aspectAdviceType.isActivatedThrough(ARG_PASSING_ANNOTATION_TYPE)) {

    } else if(aspectAdviceType.isActivatedThrough(Access.Get.class) || aspectAdviceType.isActivatedThrough(Access.Set.class)) {

    } else {
      if (original.isConstructor()) {
        key = original.toDescriptor();
        stream
          .LDC(key)
          .INVOKESTATIC(HELPERS_CLASS,
                        "getConstructorOf",
                        "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Constructor;", false);
      } else {
        key = original.toDescriptor(new StringBuilder(original.getName())).toString();
        stream
          .LDC(key)
          .INVOKESTATIC(HELPERS_CLASS,
                        "getMethodOf",
                        "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Method;", false);
      }
    }
  }
}
