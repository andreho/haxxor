package net.andreho.haxxor.api;

/**
 * <br/>Created by a.hofmann on 30.11.2017 at 07:43.
 */
public interface HxInitializablePart {
  HxInitializablePart ANNOTATIONS = InitializablePart.ANNOTATIONS;
  HxInitializablePart FIELDS = InitializablePart.FIELDS;
  HxInitializablePart METHODS = InitializablePart.METHODS;
  HxInitializablePart INTERFACES = InitializablePart.INTERFACES;
  HxInitializablePart INNER_TYPES = InitializablePart.INNER_TYPES;
  /**
   * @return name of the initializable part
   */
  String name();
}
