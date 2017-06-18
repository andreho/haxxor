package net.andreho.aop.spi.impl;

import net.andreho.aop.api.spec.query.Operator;
import net.andreho.aop.spi.AspectMatcher;
import net.andreho.aop.spi.AspectTypeMatcherFactory;
import net.andreho.aop.spi.impl.matchers.AnnotatedMatcher;
import net.andreho.aop.spi.impl.matchers.ClassWithMatcher;
import net.andreho.aop.spi.impl.matchers.ClassesMatcher;
import net.andreho.aop.spi.impl.matchers.ConditionMatcher;
import net.andreho.aop.spi.impl.matchers.DirectlyNamedMatcher;
import net.andreho.aop.spi.impl.matchers.MethodsMatcher;
import net.andreho.aop.spi.impl.matchers.ModifiersMatcher;
import net.andreho.aop.spi.impl.matchers.NamedMatcher;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxEnum;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.List;


/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:24.
 */
public class AspectTypeMatcherFactoryImpl
    implements AspectTypeMatcherFactory, Constants {
//  public static final String ASPECTS_ANNOTATION_TYPE = "net.andreho.aop.api.Aspects";


  @Override
  public AspectMatcher<HxType> create(final HxType aspectType) {
    if(aspectType.isAnnotationPresent(ASPECT_ANNOTATION_TYPE)) {
      HxAnnotation aspect = aspectType.getAnnotation(ASPECT_ANNOTATION_TYPE).get();
      HxAnnotation[] classes = aspect.getAttribute("classes", HxAnnotation[].class);
      return createClassesMatcher(classes);
    }
    return AspectMatcher.none();
  }

  protected AspectMatcher<HxType> createClassesMatcher(HxAnnotation[] classes) {
    final List<AspectMatcher<HxType>> classesMatchers = new ArrayList<>(classes.length);

    final List<AspectMatcher<HxType>> valuesMatchers = new ArrayList<>();
    final List<AspectMatcher<HxType>> extendingMatchers = new ArrayList<>();
    final List<AspectMatcher<HxType>> implementingMatchers = new ArrayList<>();

    for(HxAnnotation classesAnnotation : classes) {
      final HxAnnotation[] values = classesAnnotation.getAttribute("value", HxAnnotation[].class);
      final HxAnnotation[] extending = classesAnnotation.getAttribute("extending", HxAnnotation[].class);
      final HxAnnotation[] implementing = classesAnnotation.getAttribute("implementing", HxAnnotation[].class);

      for(HxAnnotation classWithAnnotation : values) {
        valuesMatchers.add(createClassWithMatcher(classWithAnnotation));
      }
      for(HxAnnotation classWithAnnotation : extending) {
        extendingMatchers.add(createClassWithMatcher(classWithAnnotation));
      }
      for(HxAnnotation classWithAnnotation : implementing) {
        implementingMatchers.add(createClassWithMatcher(classWithAnnotation));
      }

      classesMatchers.add(
          new ClassesMatcher(
            AspectMatcher.or(valuesMatchers),
            AspectMatcher.or(extendingMatchers),
            AspectMatcher.or(implementingMatchers)
        )
      );
    }

    return AspectMatcher.or(classesMatchers);
  }

  protected AspectMatcher<HxType> createClassWithMatcher(final HxAnnotation classWithAnnotation) {
    final HxAnnotation[] modifiers = classWithAnnotation.getAttribute("modifiers", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] named = classWithAnnotation.getAttribute("named", EMPTY_ANNOTATION_ARRAY);
    final HxType[] value = classWithAnnotation.getAttribute("value", EMPTY_TYPE_ARRAY);
    final HxAnnotation[] annotated = classWithAnnotation.getAttribute("annotated", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = classWithAnnotation.getAttribute("negate", false);


    final AspectMatcher<HxType> modifiersMatchers = createModifiersMatcher(modifiers);
    final AspectMatcher<HxType> namedMatchers = createNamedMatcher(named);
    final AspectMatcher<HxType> valuesMatchers = createDirectlyNamedMatcher(value);
    final AspectMatcher<HxType> annotatedMatchers = createAnnotatedMatcher(annotated);

    return new ClassWithMatcher(
        modifiersMatchers,
        valuesMatchers,
        namedMatchers,
        annotatedMatchers
    ).negateIf(negate);
  }

  private <A extends HxAnnotated<A>> AspectMatcher<A> createAnnotatedMatcher(final HxAnnotation[] annotated) {
    final List<AspectMatcher<A>> annotatedMatchers = new ArrayList<>();
    for(HxAnnotation annotatedAnnotation : annotated) {
      annotatedMatchers.add(createAnnotatedMatcher(annotatedAnnotation));
    }
    return AspectMatcher.or(annotatedMatchers);
  }

  private <N extends HxNamed> AspectMatcher<N> createDirectlyNamedMatcher(final N[] value) {
    final List<AspectMatcher<N>> valuesMatchers = new ArrayList<>();
    for(N named : value) {
      valuesMatchers.add(createDirectlyNamedMatcher(named));
    }
    return AspectMatcher.or(valuesMatchers);
  }

  private <N extends HxNamed> AspectMatcher<N> createNamedMatcher(final HxAnnotation[] named) {
    final List<AspectMatcher<N>> namedMatchers = new ArrayList<>();
    for(HxAnnotation namedAnnotation : named) {
      namedMatchers.add(createPatternMatcher(namedAnnotation));
    }
    return AspectMatcher.or(namedMatchers);
  }

  private <M extends HxMember<M>> AspectMatcher<M> createModifiersMatcher(final HxAnnotation[] modifiers) {
    final List<AspectMatcher<M>> modifiersMatchers = new ArrayList<>();
    for(HxAnnotation modifierAnnotation : modifiers) {
      modifiersMatchers.add(createModifiersMatcher(modifierAnnotation));
    }
    return AspectMatcher.or(modifiersMatchers);
  }

  protected <M extends HxMember<M>> AspectMatcher<M> createModifiersMatcher(final HxAnnotation modifierAnnotation) {
    if(modifierAnnotation == null) {
      return AspectMatcher.any();
    }

    final int modifiers = modifierAnnotation.getAttribute("value", -1);
    final boolean negate = modifierAnnotation.getAttribute("negate", false);

    return modifiers == -1?
           AspectMatcher.any() :
           new ModifiersMatcher<M>(modifiers).negateIf(negate);
  }

  protected <N extends HxNamed> AspectMatcher<N> createPatternMatcher(final HxAnnotation namedAnnotation) {
    if(namedAnnotation == null) {
      return AspectMatcher.any();
    }

    final String pattern = namedAnnotation.getAttribute("value", "");
    final boolean asRegExp = namedAnnotation.getAttribute("asRegExp", false);
    final boolean negate = namedAnnotation.getAttribute("negate", false);

    return pattern.isEmpty()?
           AspectMatcher.any() :
           new NamedMatcher<N>(pattern, asRegExp).negateIf(negate);
  }

  protected <N extends HxNamed> AspectMatcher<N> createDirectlyNamedMatcher(final N named) {
    if(named == null) {
      return AspectMatcher.any();
    }
    return new DirectlyNamedMatcher<>(named.getName());
  }

  protected <A extends HxAnnotated<A>>
  AspectMatcher<A> createAnnotatedMatcher(final HxAnnotation annotatedAnnotation) {

    final HxType value = annotatedAnnotation.getAttribute("value", (HxType) null);
    final HxAnnotation named = annotatedAnnotation.getAttribute("named", (HxAnnotation) null);
    final HxAnnotation[] where = annotatedAnnotation.getAttribute("criteria", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = annotatedAnnotation.getAttribute("negate", false);

    return (value == null && named == null)?
           AspectMatcher.any() :
           new AnnotatedMatcher(
             createDirectlyNamedMatcher(value),
             createPatternMatcher(named),
             createCriteriaMatcher(where)
           ).negateIf(negate);
  }

  protected AspectMatcher<HxAnnotation> createCriteriaMatcher(final HxAnnotation[] criteriaList) {
    if(criteriaList == null || criteriaList.length == 0) {
      return AspectMatcher.any();
    }
    final List<AspectMatcher<HxAnnotation>> whereMatchers = new ArrayList<>();

    for(HxAnnotation whereAnnotation : criteriaList) {
      whereMatchers.add(createConditionsMatcher(whereAnnotation.getAttribute("value", EMPTY_ANNOTATION_ARRAY)));
    }

    return AspectMatcher.or(whereMatchers);
  }

  private AspectMatcher<HxAnnotation> createConditionsMatcher(final HxAnnotation[] conditionsList) {
    if(conditionsList == null || conditionsList.length == 0) {
      return AspectMatcher.any();
    }

    final List<AspectMatcher<HxAnnotation>> conditionMatchers = new ArrayList<>();

    for(HxAnnotation conditionAnnotation : conditionsList) {
      conditionMatchers.add(createConditionMatcher(conditionAnnotation));
    }

    return AspectMatcher.and(conditionMatchers);
  }

  private AspectMatcher<HxAnnotation> createConditionMatcher(final HxAnnotation conditionAnnotation) {
    if(conditionAnnotation == null) {
      return AspectMatcher.any();
    }

    final String name = conditionAnnotation.getAttribute("name");
    final String[] value = conditionAnnotation.getAttribute("value", EMPTY_STRING_ARRAY);
    final HxEnum operator = conditionAnnotation.getAttribute("operator", (HxEnum) null);
    final boolean negate = conditionAnnotation.getAttribute("negate", false);
    final boolean undefinedValue = conditionAnnotation.getAttribute("undefinedValue", false);

    return
        new ConditionMatcher(name,
                             value,
                             operator == null? Operator.EQ : operator.loadEnum(this.getClass().getClassLoader()),
                             undefinedValue)
        .negateIf(negate);
  }

  @Override
  public AspectMatcher<HxMethod> create(final HxMethod aspectMethod, final HxAnnotation aspectAnnotation) {
    if(aspectAnnotation.getType().hasName(BEFORE_ANNOTATION_TYPE)) {
      return createInterceptorMethodMatcher(aspectMethod, aspectAnnotation);
    }
    if(aspectAnnotation.getType().hasName(AFTER_ANNOTATION_TYPE)) {
      return createInterceptorMethodMatcher(aspectMethod, aspectAnnotation);
    }
    return AspectMatcher.none();
  }

  private <E extends HxExecutable<E>> AspectMatcher<E>
  createInterceptorMethodMatcher(final E executable, final HxAnnotation aspectAnnotation) {
    final HxAnnotation[] methods = aspectAnnotation.getAttribute("methods", EMPTY_ANNOTATION_ARRAY);
    final List<AspectMatcher<E>> methodMatchers = new ArrayList<>();

    for(HxAnnotation methodsAnnotation : methods) {
      methodMatchers.add(createMethodMatcher(methodsAnnotation));
    }

    return AspectMatcher.or(methodMatchers);
  }

  private <E extends HxExecutable<E>> AspectMatcher<E> createMethodMatcher(final HxAnnotation methodsAnnotation) {
    final HxAnnotation[] declaredBy = methodsAnnotation.getAttribute("declaredBy", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] modifiers = methodsAnnotation.getAttribute("modifiers", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] named = methodsAnnotation.getAttribute("named", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] returning = methodsAnnotation.getAttribute("returning", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] annotated = methodsAnnotation.getAttribute("annotated", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] throwing = methodsAnnotation.getAttribute("throwing", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] signatures = methodsAnnotation.getAttribute("signatures", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] parameters = methodsAnnotation.getAttribute("parameters", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = methodsAnnotation.getAttribute("negate", false);

    final AspectMatcher<HxType> declaredByMatcher = createClassesMatcher(declaredBy);
    final AspectMatcher<E> modifiersMatcher = createModifiersMatcher(modifiers);
    final AspectMatcher<HxNamed> namedMatcher = createNamedMatcher(named);
    final AspectMatcher<HxType> returningMatcher = createClassesMatcher(returning);
    final AspectMatcher<HxAnnotated> annotatedMatcher = createAnnotatedMatcher(annotated);
    final AspectMatcher<HxType> throwingMatcher = createClassesMatcher(throwing);
    final AspectMatcher<E> signaturesMatcher = AspectMatcher.any(); //createClassesMatcher(throwing);
    final AspectMatcher<E> parametersMatcher = AspectMatcher.any(); //createClassesMatcher(throwing);

    return new MethodsMatcher<>(
      declaredByMatcher,
      modifiersMatcher,
      namedMatcher,
      returningMatcher,
      annotatedMatcher,
      throwingMatcher,
      signaturesMatcher,
      parametersMatcher
    ).negateIf(negate);
  }

  @Override
  public AspectMatcher<HxConstructor> create(final HxConstructor aspectConstructor, final HxAnnotation aspectAnnotation) {
    return AspectMatcher.none();
  }
}
