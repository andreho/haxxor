package net.andreho.aop.spi.impl;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.Order;
import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.api.spec.Disable;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxEnum;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:47.
 */
public interface Constants {
  HxAnnotation[] EMPTY_ANNOTATION_ARRAY = new HxAnnotation[0];
  HxType[] EMPTY_TYPE_ARRAY = new HxType[0];
  HxEnum[] EMPTY_ENUM_ARRAY = new HxEnum[0];
  String[] EMPTY_STRING_ARRAY = new String[0];
  int[] EMPTY_INT_ARRAY = new int[0];

  String ASPECT_ANNOTATION_TYPE = Aspect.class.getName();
  String ASPECT_FACTORY_ANNOTATION_TYPE = Aspect.Factory.class.getName();

  String ORDER_ANNOTATION_TYPE = Order.class.getName();
  String BEFORE_ANNOTATION_TYPE = Before.class.getName();
  String AFTER_ANNOTATION_TYPE = After.class.getName();
  String INJECT_RESULT_ANNOTATION_TYPE = Result.class.getName();
  String DISABLE_ANNOTATION_TYPE = Disable.class.getName();

  String RESULT_ATTRIBUTES_NAME =
    "__$result";
  int UNORDERED_ELEMENT_INDEX = 1_000_000;
}
