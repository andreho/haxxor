package net.andreho.aop.spi.impl.steps.injectors;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class AttributeInjector
  extends AbstractMultipleInjector {
  public static final AttributeInjector INSTANCE = new AttributeInjector();

  public AttributeInjector() {
    super(
      LocalAttributeInjector.INSTANCE,
      FieldAttributeInjector.INSTANCE
    );
  }
}
