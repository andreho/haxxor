package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.spi.ParameterInjector;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 05.10.2017 at 13:29.
 */
public abstract class AbstractParameterInjector implements ParameterInjector {

  protected static final int PRIMITIVE_TO_SUITABLE_WRAPPER = MAX_SUITABLE - (100 * SCALE_STEP);
  protected static final int PRIMITIVE_NUMBER_TO_NUMBER_WRAPPER = PRIMITIVE_TO_SUITABLE_WRAPPER / 2;
  protected static final int PRIMITIVE_TO_SERIALIZABLE = PRIMITIVE_TO_SUITABLE_WRAPPER / 3;
  protected static final int PRIMITIVE_TO_ANY_WRAPPER = PRIMITIVE_TO_SUITABLE_WRAPPER / 4;

  protected static final int OBJECT_PASSING_BEGIN = PRIMITIVE_TO_ANY_WRAPPER - 100;
  protected static final int EQUAL = MAX_SUITABLE;

  protected int scoreTypes(final HxType given,
                           final HxType expected) {
    if(given.equals(expected)) {
      return EQUAL;
    }

    final HxSort givenSort = given.getSort();
    final HxSort expectedSort = expected.getSort();

    int score;

    if(givenSort.isPrimitive()) {
      if(expectedSort.isPrimitive()) {
        score = scoreFromPrimitiveToPrimitive(givenSort, expectedSort);
      } else {
        score = scoreFromPrimitiveToObject(expected, givenSort);
      }
    } else { //!givenSort.isPrimitive()
      if(expectedSort.isPrimitive()) {
        score = scoreFromObjectToPrimitive(given, expectedSort);
      } else { //!expectedSort.isPrimitive()
        score = scoreFromObjectToObject(given, expected);
      }
    }

    return score;
  }

  private int scoreFromObjectToObject(final HxType given,
                                      final HxType expected) {
    int distance = expected.distanceTo(given);
    return distance < 0? NOT_INJECTABLE : OBJECT_PASSING_BEGIN - distance;
  }

  private int scoreFromPrimitiveToPrimitive(final HxSort givenSort,
                                            final HxSort expectedSort) {
    //IS WIDENING POSSIBLE?
    int distance = givenSort.wideningDistance(expectedSort);
    if(distance < EQUAL) {
      return NOT_INJECTABLE;
    }
    return MAX_SUITABLE - (distance * SCALE_STEP);
  }

  private int scoreFromPrimitiveToObject(final HxType expected,
                                         final HxSort givenSort) {
    switch (givenSort) {
      case BOOLEAN: {
        if(expected.hasName("java.lang.Boolean")) {
          return PRIMITIVE_TO_SUITABLE_WRAPPER;
        }
      } break;
      case CHAR: {
        if(expected.hasName("java.lang.Character")) {
          return PRIMITIVE_TO_SUITABLE_WRAPPER;
        }
      } break;
      case BYTE:
      case SHORT:
      case INT:
      case FLOAT:
      case LONG:
      case DOUBLE: {
        if(expected.hasName(givenSort.getWrapper())) {
          return PRIMITIVE_TO_SUITABLE_WRAPPER;
        } else if(expected.hasName("java.lang.Number")) {
          return PRIMITIVE_NUMBER_TO_NUMBER_WRAPPER;
        }
      } break;
    }
    if(expected.hasName("java.io.Serializable")) {
      return PRIMITIVE_TO_SERIALIZABLE;
    }
    return expected.hasName("java.lang.Object") ? PRIMITIVE_TO_ANY_WRAPPER : NOT_INJECTABLE;
  }

  private int scoreFromObjectToPrimitive(final HxType given,
                                         final HxSort expectedSort) {
//    switch (expectedSort) {
//      case BOOLEAN: {
//        if(given.hasName("java.lang.Boolean")) {
//          return PRIMITIVE_WRAPPER;
//        }
//      } break;
//      case CHAR: {
//        if(given.hasName("java.lang.Character")) {
//          return PRIMITIVE_WRAPPER;
//        }
//      } break;
//      case BYTE:
//      case SHORT:
//      case INT:
//      case FLOAT:
//      case LONG:
//      case DOUBLE: {
//        if(given.hasName(expectedSort.getWrapper())) {
//          return PRIMITIVE_WRAPPER;
//        }
//      } break;
//    }
    return NOT_INJECTABLE;
  }
}
