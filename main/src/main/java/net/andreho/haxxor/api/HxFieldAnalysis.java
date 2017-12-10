package net.andreho.haxxor.api;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 29.11.2017 at 07:03.
 */
public interface HxFieldAnalysis<O extends HxFieldManager<O>> extends HxFieldManager<O> {
  /**
   * Loads the declared fields if needed and then caches them internally for the faster analysis
   * @return owner instance
   */
  default O cacheFields() {
    return (O) this;
  }

  /**
   * @param predicate for test on each declared field
   * @return a modifiable unbind list with all passing fields
   */
  default List<HxField> fields(Predicate<HxField> predicate) {
    return fields(predicate, false);
  }

  /**
   * @param predicate for test on each declared field
   * @param recursive whether to visit fields of super-types or not (interfaces are not included)
   * @return a modifiable unbind list with all passing fields
   */
  default List<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
    return Collections.emptyList();
  }

  /**
   * @param field prototype of the field to look for
   * @return {@link Optional#empty() empty} or a field with the prototype's name and type
   */
  default Optional<HxField> findField(Field field) {
    return findField(field.getName(), field.getType());
  }

  /**
   * @param field prototype of the field to look for
   * @return {@link Optional#empty() empty} or a field with the prototype's name and type
   */
  default Optional<HxField> findField(HxField field) {
    return findField(field.getName(), field.getType());
  }

  /**
   * Gets the first available field with given name
   *
   * @param name of a field to search
   * @return {@link Optional#empty() empty} or a field with the given name
   */
  default Optional<HxField> findField(String name) {
    for(HxField field : getFields()) {
      if(field.hasName(name)) {
        return Optional.of(field);
      }
    }
    return Optional.empty();
  }

  /**
   * Gets the first available field with given name
   *
   * @param name of a field to search
   * @param type of a field to search
   * @return {@link Optional#empty() empty} or a field with given name
   */
  default Optional<HxField> findField(String name, HxType type) {
    return findField(name, type.getName());
  }

  /**
   * Gets the first available field with given name
   *
   * @param name of a field to search
   * @param type of a field to search
   * @return {@link Optional#empty() empty} or a field with given name
   */
  default Optional<HxField> findField(String name, Class<?> type) {
    return findField(name, type.getName());
  }

  /**
   * Gets the first available field with given name
   *
   * @param name of a field to search
   * @param type of a field to search
   * @return {@link Optional#empty() empty} or a field with given name
   */
  default Optional<HxField> findField(String name, String type) {
    for(HxField field : getFields()) {
      if(field.hasName(name) && field.hasType(type)) {
        return Optional.of(field);
      }
    }
    return Optional.empty();
  }

  /**
   * @param prototype
   * @return
   * @implNote uses only name, type and modifiers of the given field prototype
   */
  default Optional<HxField> findOrCreateField(HxField prototype) {
    final Optional<HxField> optional = findOrCreateField(prototype.getName(), prototype.getType());
    return optional.map(
      field -> field.setModifiers(prototype.getModifiers())
    );
  }

  /**
   * Finds an existing field using given prototype or creates it otherwise for this type
   * @param prototype to use for a field
   * @return
   * @implNote uses only name, type and modifiers of the given field prototype
   */
  default Optional<HxField> findOrCreateField(Field prototype) {
    final Optional<HxField> optional = findOrCreateField(prototype.getName(), prototype.getType());
    return optional.map(
      field -> field.setModifiers(prototype.getModifiers())
    );
  }

  /**
   * Finds an existing field with given parameters or creates it otherwise for this type
   * @param name of the field to look for or to create
   * @param type of the field to look for or to create
   * @return optional with field
   */
  default Optional<HxField> findOrCreateField(String name, String type) {
    final Optional<HxField> optional = findField(name, type);
    if(optional.isPresent()) {
      return optional;
    }
    final HxField field = getHaxxor().createField(type, name);
    addField(field);
    return Optional.of(field);
  }

  /**
   * Finds an existing field with given parameters or creates it otherwise for this type
   * @param name of the field to look for or to create
   * @param type of the field to look for or to create
   * @return optional with field
   */
  default Optional<HxField> findOrCreateField(String name, Class<?> type) {
    return findOrCreateField(name, type.getName());
  }

  /**
   * Finds an existing field with given parameters or creates it otherwise for this type
   * @param name of the field to look for or to create
   * @param type of the field to look for or to create
   * @return optional with field
   */
  default Optional<HxField> findOrCreateField(String name, HxType type) {
    return findOrCreateField(name, type.getName());
  }

  /**
   * Checks whether there is a field with given name or not
   *
   * @param name of a field
   * @return <b>true</b> if there is a field with given name, <b>false</b> otherwise.
   */
  default boolean hasField(String name) {
    return findField(name).isPresent();
  }

  /**
   * Checks whether there is a field with given name and type-descriptor or not
   *
   * @param name of the field to look for
   * @param type of the field to look for
   * @return <b>true</b> if there is a field with given name and type-descriptor, <b>false</b> otherwise.
   */
  default boolean hasField(String name, String type) {
    return findField(name, type).isPresent();
  }

  /**
   * Checks whether there is a field with given name and type or not
   *
   * @param name of the field to look for
   * @param type of the field to look for
   * @return <b>true</b> if there is a field with given name and type, <b>false</b> otherwise.
   */
  default boolean hasField(String name, HxType type) {
    return findField(name, type).isPresent();
  }

  /**
   * Checks whether there is a field with given name and type or not
   *
   * @param name of the field to look for
   * @param type of the field to look for
   * @return <b>true</b> if there is a field with given name and type, <b>false</b> otherwise.
   */
  default boolean hasField(String name, Class<?> type) {
    return findField(name, type).isPresent();
  }
}
