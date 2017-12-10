package net.andreho.haxxor.api;

/**
 * Created by a.hofmann on 30.11.2017.
 */
enum InitializablePart
  implements HxInitializablePart {
  ANNOTATIONS,
  INTERFACES,
  INNER_TYPES,
  FIELDS,
  METHODS
}
