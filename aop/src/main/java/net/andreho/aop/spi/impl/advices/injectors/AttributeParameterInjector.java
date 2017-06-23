package net.andreho.aop.spi.impl.advices.injectors;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class AttributeParameterInjector
  extends AbstractMultipleParameterInjector {
  public static final AttributeParameterInjector INSTANCE = new AttributeParameterInjector();

  public AttributeParameterInjector() {
    super(
      LocalAttributeParameterInjector.INSTANCE,
      FieldAttributeParameterInjector.INSTANCE
    );
  }
}
