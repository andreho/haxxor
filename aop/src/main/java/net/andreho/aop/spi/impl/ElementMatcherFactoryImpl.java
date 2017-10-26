package net.andreho.aop.spi.impl;

import net.andreho.aop.api.spec.MethodsType;
import net.andreho.aop.api.spec.query.Operator;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.aop.spi.impl.matchers.AnnotatedMatcher;
import net.andreho.aop.spi.impl.matchers.ClassWithMatcher;
import net.andreho.aop.spi.impl.matchers.ClassesMatcher;
import net.andreho.aop.spi.impl.matchers.ConditionMatcher;
import net.andreho.aop.spi.impl.matchers.DirectlyNamedMatcher;
import net.andreho.aop.spi.impl.matchers.MethodParametersMatcher;
import net.andreho.aop.spi.impl.matchers.MethodsMatcher;
import net.andreho.aop.spi.impl.matchers.ModifiersMatcher;
import net.andreho.aop.spi.impl.matchers.NamedMatcher;
import net.andreho.aop.spi.impl.matchers.ParametersMatcher;
import net.andreho.aop.spi.impl.matchers.PositionedMatcher;
import net.andreho.aop.spi.impl.matchers.SignatureMatcher;
import net.andreho.aop.spi.impl.matchers.TypedMatcher;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxEnum;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxNamed;
import net.andreho.haxxor.api.HxOrdered;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTyped;

import java.util.ArrayList;
import java.util.List;


/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:24.
 */
public class ElementMatcherFactoryImpl
  implements ElementMatcherFactory, Constants {
//  public static final String ASPECTS_ANNOTATION_TYPE = "net.andreho.aop.api.Aspects";


  @Override
  public ElementMatcher<HxType> createClassesFilter(final HxType aspectType) {
    if(aspectType.isAnnotationPresent(ASPECT_ANNOTATION_TYPE)) {
      HxAnnotation aspect = aspectType.getAnnotation(ASPECT_ANNOTATION_TYPE).get();
      HxAnnotation[] classes = aspect.getAttribute("classes", EMPTY_ANNOTATION_ARRAY);
      return createClassesFilter(classes);
    }
    return ElementMatcher.none();
  }

  @Override
  public ElementMatcher<HxType> createClassesFilter(HxAnnotation[] classesAnnotations) {
    final List<ElementMatcher<HxType>> classesMatchers = new ArrayList<>(classesAnnotations.length);

    final List<ElementMatcher<HxType>> valuesMatchers = new ArrayList<>();
    final List<ElementMatcher<HxType>> extendingMatchers = new ArrayList<>();
    final List<ElementMatcher<HxType>> implementingMatchers = new ArrayList<>();

    for(HxAnnotation classesAnnotation : classesAnnotations) {
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
            ElementMatcher.or(valuesMatchers),
            ElementMatcher.or(extendingMatchers),
            ElementMatcher.or(implementingMatchers)
        )
      );
    }

    return ElementMatcher.or(classesMatchers);
  }

  protected <T extends HxTyped> ElementMatcher<T> createTypedMatcher(HxAnnotation[] classes) {
    return new TypedMatcher<>(createClassesFilter(classes));
  }

  protected ElementMatcher<HxType> createClassWithMatcher(final HxAnnotation classWithAnnotation) {
    final HxAnnotation[] modifiers = classWithAnnotation.getAttribute("modifiers", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] named = classWithAnnotation.getAttribute("named", EMPTY_ANNOTATION_ARRAY);
    final HxType[] value = classWithAnnotation.getAttribute("value", EMPTY_TYPE_ARRAY);
    final HxAnnotation[] annotated = classWithAnnotation.getAttribute("annotated", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = classWithAnnotation.getAttribute("negate", false);


    final ElementMatcher<HxType> modifiersMatchers = createModifiersMatcher(modifiers);
    final ElementMatcher<HxType> namedMatchers = createNamedMatcher(named);
    final ElementMatcher<HxType> valuesMatchers = createDirectlyNamedMatcher(value);
    final ElementMatcher<HxType> annotatedMatchers = createAnnotatedMatcher(annotated);

    return new ClassWithMatcher(
        modifiersMatchers,
        valuesMatchers,
        namedMatchers,
        annotatedMatchers
    ).negateIf(negate);
  }

  private <A extends HxAnnotated<A>> ElementMatcher<A> createAnnotatedMatcher(final HxAnnotation[] annotated) {
    final List<ElementMatcher<A>> annotatedMatchers = new ArrayList<>();
    for(HxAnnotation annotatedAnnotation : annotated) {
      annotatedMatchers.add(createAnnotatedMatcher(annotatedAnnotation));
    }
    return ElementMatcher.or(annotatedMatchers);
  }

  private <N extends HxNamed> ElementMatcher<N> createDirectlyNamedMatcher(final N[] value) {
    final List<ElementMatcher<N>> valuesMatchers = new ArrayList<>();
    for(N named : value) {
      valuesMatchers.add(createDirectlyNamedMatcher(named));
    }
    return ElementMatcher.or(valuesMatchers);
  }

  private <N extends HxNamed> ElementMatcher<N> createNamedMatcher(final HxAnnotation[] named) {
    final List<ElementMatcher<N>> namedMatchers = new ArrayList<>();
    for(HxAnnotation namedAnnotation : named) {
      namedMatchers.add(createPatternMatcher(namedAnnotation));
    }
    return ElementMatcher.or(namedMatchers);
  }

  private <M extends HxMember<M>> ElementMatcher<M> createModifiersMatcher(final HxAnnotation[] modifiers) {
    final List<ElementMatcher<M>> modifiersMatchers = new ArrayList<>();
    for(HxAnnotation modifierAnnotation : modifiers) {
      modifiersMatchers.add(createModifiersMatcher(modifierAnnotation));
    }
    return ElementMatcher.or(modifiersMatchers);
  }

  protected <M extends HxMember<M>> ElementMatcher<M> createModifiersMatcher(final HxAnnotation modifierAnnotation) {
    if(modifierAnnotation == null) {
      return ElementMatcher.any();
    }

    final int modifiers = modifierAnnotation.getAttribute("value", -1);
    final boolean negate = modifierAnnotation.getAttribute("negate", false);

    return modifiers == -1 ?
           ElementMatcher.any() :
           new ModifiersMatcher<M>(modifiers).negateIf(negate);
  }

  protected <N extends HxNamed> ElementMatcher<N> createPatternMatcher(final HxAnnotation namedAnnotation) {
    if(namedAnnotation == null) {
      return ElementMatcher.any();
    }

    final String pattern = namedAnnotation.getAttribute("value", "");
    final boolean asRegExp = namedAnnotation.getAttribute("asRegExp", false);
    final boolean negate = namedAnnotation.getAttribute("negate", false);

    return pattern.isEmpty() ?
           ElementMatcher.any() :
           new NamedMatcher<N>(pattern, asRegExp).negateIf(negate);
  }

  protected <N extends HxNamed> ElementMatcher<N> createDirectlyNamedMatcher(final N named) {
    if(named == null) {
      return ElementMatcher.any();
    }
    return new DirectlyNamedMatcher<>(named.getName());
  }

  protected <A extends HxAnnotated<A>>
  ElementMatcher<A> createAnnotatedMatcher(final HxAnnotation annotatedAnnotation) {

    final HxType value = annotatedAnnotation.getAttribute("value", (HxType) null);
    final HxAnnotation named = annotatedAnnotation.getAttribute("named", (HxAnnotation) null);
    final HxAnnotation[] where = annotatedAnnotation.getAttribute("criteria", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = annotatedAnnotation.getAttribute("negate", false);

    return (value == null && named == null) ?
           ElementMatcher.any() :
           new AnnotatedMatcher(
             createDirectlyNamedMatcher(value),
             createPatternMatcher(named),
             createCriteriaMatcher(where)
           ).negateIf(negate);
  }

  protected ElementMatcher<HxAnnotation> createCriteriaMatcher(final HxAnnotation[] criteriaList) {
    if(criteriaList == null || criteriaList.length == 0) {
      return ElementMatcher.any();
    }
    final List<ElementMatcher<HxAnnotation>> whereMatchers = new ArrayList<>();

    for(HxAnnotation whereAnnotation : criteriaList) {
      whereMatchers.add(createConditionsMatcher(whereAnnotation.getAttribute("value", EMPTY_ANNOTATION_ARRAY)));
    }

    return ElementMatcher.or(whereMatchers);
  }

  private ElementMatcher<HxAnnotation> createConditionsMatcher(final HxAnnotation[] conditionsList) {
    if(conditionsList == null || conditionsList.length == 0) {
      return ElementMatcher.any();
    }

    final List<ElementMatcher<HxAnnotation>> conditionMatchers = new ArrayList<>();

    for(HxAnnotation conditionAnnotation : conditionsList) {
      conditionMatchers.add(createConditionMatcher(conditionAnnotation));
    }

    return ElementMatcher.and(conditionMatchers);
  }

  private ElementMatcher<HxAnnotation> createConditionMatcher(final HxAnnotation conditionAnnotation) {
    if(conditionAnnotation == null) {
      return ElementMatcher.any();
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
  public ElementMatcher<HxMethod> createMethodsFilter(final HxAnnotation[] methodsAnnotations) {
    if(methodsAnnotations == null || methodsAnnotations.length == 0) {
      return ElementMatcher.any();
    }

    final List<ElementMatcher<HxMethod>> methodMatchers = new ArrayList<>();
    for(HxAnnotation methodsAnnotation : methodsAnnotations) {
      methodMatchers.add(createMethodMatcher(methodsAnnotation));
    }

    return ElementMatcher.or(methodMatchers);
  }

  private ElementMatcher<HxMethod> createMethodMatcher(final HxAnnotation methodsAnnotation) {
    final HxEnum type = methodsAnnotation.getAttribute("type", (HxEnum) null);
    final HxAnnotation[] declaredBy = methodsAnnotation.getAttribute("declaredBy", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] modifiers = methodsAnnotation.getAttribute("modifiers", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] named = methodsAnnotation.getAttribute("named", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] returning = methodsAnnotation.getAttribute("returning", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] annotated = methodsAnnotation.getAttribute("annotated", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] throwing = methodsAnnotation.getAttribute("throwing", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] signatures = methodsAnnotation.getAttribute("signatures", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] parameters = methodsAnnotation.getAttribute("parameters", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = methodsAnnotation.getAttribute("negate", false);

    final ElementMatcher<HxType> declaredByMatcher = createClassesFilter(declaredBy);
    final ElementMatcher<HxMethod> modifiersMatcher = createModifiersMatcher(modifiers);
    final ElementMatcher<HxNamed> namedMatcher = createNamedMatcher(named);
    final ElementMatcher<HxType> returningMatcher = createClassesFilter(returning);
    final ElementMatcher<HxAnnotated> annotatedMatcher = createAnnotatedMatcher(annotated);
    final ElementMatcher<HxType> throwingMatcher = createClassesFilter(throwing);
    final ElementMatcher<HxMethod> signaturesMatcher = createSignatureMatcher(signatures);
    final ElementMatcher<HxMethod> parametersMatcher = createMethodParametersMatcher(parameters);

    return new MethodsMatcher(
      getMethodType(type),
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

  private MethodsType getMethodType(final HxEnum type) {
    return type == null ? MethodsType.METHODS : (MethodsType) type.loadEnum(getClass().getClassLoader());
  }

  private ElementMatcher<HxMethod> createSignatureMatcher(final HxAnnotation[] signaturesAnnotations) {
    final List<ElementMatcher<HxMethod>> signaturesList = new ArrayList<>();
    for (HxAnnotation signature : signaturesAnnotations) {
      signaturesList.add(createSignatureMatcher(signature));
    }
    return ElementMatcher.or(signaturesList);
  }

  private ElementMatcher<HxMethod> createSignatureMatcher(final HxAnnotation signatureAnnotation) {
    if(signatureAnnotation == null) {
      return ElementMatcher.any();
    }

    final String[] classes = signatureAnnotation.getAttribute("classes", EMPTY_STRING_ARRAY);
    final HxType[] values = signatureAnnotation.getAttribute("value", EMPTY_TYPE_ARRAY);
    final boolean negate = signatureAnnotation.getAttribute("negate", false);

    if(classes.length != 0) {
      return new SignatureMatcher(classes).negateIf(negate);
    }
    if(values.length != 0) {
      return new SignatureMatcher(values).negateIf(negate);
    }

    return ElementMatcher.any();
  }

  private <I extends HxOrdered> ElementMatcher<I> createPositionedMatcher(final HxAnnotation[] positionedAnnotations) {
    final List<ElementMatcher<I>> positionedList = new ArrayList<>();
    for (HxAnnotation positionedAnnotation : positionedAnnotations) {
      positionedList.add(createPositionedMatcher(positionedAnnotation));
    }
    return ElementMatcher.or(positionedList);
  }

  private <I extends HxOrdered> ElementMatcher<I> createPositionedMatcher(final HxAnnotation positionedAnnotation) {
    if(positionedAnnotation == null) {
      return ElementMatcher.any();
    }

    final int[] positions = positionedAnnotation.getAttribute("value", EMPTY_INT_ARRAY);
    final boolean asRange = positionedAnnotation.getAttribute("asRange", false);
    final boolean negate = positionedAnnotation.getAttribute("negate", false);

    if(positions.length != 0) {
      return new PositionedMatcher<I>(positions, asRange).negateIf(negate);
    }

    return ElementMatcher.any();
  }

  private ElementMatcher<HxMethod> createMethodParametersMatcher(final HxAnnotation[] parametersAnnotations) {
    return new MethodParametersMatcher(createParametersFilter(parametersAnnotations));
  }

  @Override
  public ElementMatcher<HxField> createFieldsFilter(final HxAnnotation[] fieldsAnnotations) {
    if(fieldsAnnotations.length > 0) {
      throw new UnsupportedOperationException();
    }
    return ElementMatcher.any();
  }

  @Override
  public ElementMatcher<HxParameter> createParametersFilter(final HxAnnotation[] parameters) {
    if(parameters == null || parameters.length == 0) {
      return ElementMatcher.any();
    }

    final List<ElementMatcher<HxParameter>> parametersMatchers = new ArrayList<>();
    for (HxAnnotation parameterAnnotation : parameters) {
      parametersMatchers.add(createParameterFilter(parameterAnnotation));
    }

    return ElementMatcher.or(parametersMatchers);
  }

  private ElementMatcher<HxParameter> createParameterFilter(final HxAnnotation parameterAnnotation) {
    if(parameterAnnotation == null) {
      return ElementMatcher.any();
    }

    final HxAnnotation[] positioned = parameterAnnotation.getAttribute("positioned", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] typed = parameterAnnotation.getAttribute("typed", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] annotated = parameterAnnotation.getAttribute("annotated", EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] named = parameterAnnotation.getAttribute("named", EMPTY_ANNOTATION_ARRAY);
    final boolean negate = parameterAnnotation.getAttribute("negate", false);

    final ElementMatcher<HxOrdered> positionedMatcher = createPositionedMatcher(positioned);
    final ElementMatcher<HxTyped> typedMatcher = createTypedMatcher(typed);
    final ElementMatcher<HxAnnotated> annotatedMatcher = createAnnotatedMatcher(annotated);
    final ElementMatcher<HxNamed> namedMatcher = createNamedMatcher(named);

    return new ParametersMatcher(
      positionedMatcher,
      typedMatcher,
      annotatedMatcher,
      namedMatcher
    ).negateIf(negate);
  }
}
