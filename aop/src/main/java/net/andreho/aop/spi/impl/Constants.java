package net.andreho.aop.spi.impl;

import net.andreho.aop.api.After;
import net.andreho.aop.api.ArgPassing;
import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.Catch;
import net.andreho.aop.api.Finally;
import net.andreho.aop.api.Modify;
import net.andreho.aop.api.Order;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.api.mixin.Mixin;
import net.andreho.aop.api.spec.Disable;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxEnum;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:47.
 */
public interface Constants {
  HxAnnotation[] EMPTY_ANNOTATION_ARRAY = new HxAnnotation[0];
  HxType[] EMPTY_TYPE_ARRAY = new HxType[0];
  HxEnum[] EMPTY_ENUM_ARRAY = new HxEnum[0];
  HxMethod[] EMPTY_METHOD_ARRAY = new HxMethod[0];
  HxField[] EMPTY_FIELD_ARRAY = new HxField[0];

  String[] EMPTY_STRING_ARRAY = new String[0];
  int[] EMPTY_INT_ARRAY = new int[0];

  String ANY_SELECTION_PROFILE_NAME = "*";
  String ASPECT_ANNOTATION_TYPE = Aspect.class.getName();
  String ASPECT_FACTORY_ANNOTATION_TYPE = Aspect.Factory.class.getName();

  String ARG_PASSING_ANNOTATION_TYPE = ArgPassing.class.getName();
  String ORDER_ANNOTATION_TYPE = Order.class.getName();
  String BEFORE_ANNOTATION_TYPE = Before.class.getName();
  String AFTER_ANNOTATION_TYPE = After.class.getName();
  String CATCH_ANNOTATION_TYPE = Catch.class.getName();
  String FINALLY_ANNOTATION_TYPE = Finally.class.getName();
  String MIXIN_APPLICATION_ANNOTATION = Mixin.Application.class.getName();
  String MIXIN_APPLICATIONS_ANNOTATION = Mixin.Applications.class.getName();
  String MODIFY_TYPE_ANNOTATION_TYPE = Modify.Type.class.getName();
  String MODIFY_METHOD_ANNOTATION_TYPE = Modify.Method.class.getName();
  String MODIFY_FIELD_ANNOTATION_TYPE = Modify.Field.class.getName();
  String INJECT_RESULT_ANNOTATION_TYPE = Result.class.getName();
  String CAUGHT_RESULT_ANNOTATION_TYPE = Caught.class.getName();
  String DISABLE_ANNOTATION_TYPE = Disable.class.getName();

  String INTERCEPTED_FIELD_NAME_PREFIX = "$$__";
  String INTERCEPTED_FIELD_NAME_SUFFIX = "__$$";

  String VOID_PARAMETER_NAME =
    "$$_void_";
  String RESULT_ATTRIBUTES_NAME =
    "$$_result_";
  String DEFAULT_CAUGHT_EXCEPTION_ATTRIBUTE_NAME_PREFIX =
    "$$_e_";
  String DEFAULT_ASPECT_ATTRIBUTE_NAME_PREFIX =
    "$$_caught_";
  int UNORDERED_ELEMENT_INDEX = 1_000_000;
}
