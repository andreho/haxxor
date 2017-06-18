package net.andreho.aop.spi.impl;

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

  String ASPECT_ANNOTATION_TYPE = "net.andreho.aop.api.Aspect";
  String ASPECT_FACTORY_ANNOTATION_TYPE = "net.andreho.aop.api.Aspect$Factory";

  String BEFORE_ANNOTATION_TYPE = "net.andreho.aop.api.Before";
  String AFTER_ANNOTATION_TYPE = "net.andreho.aop.api.After";
}
