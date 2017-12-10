package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 29.11.2017 at 07:03.
 */
public interface HxFieldManager<O extends HxFieldManager<O>>
  extends HxInitializable<O>,
          HxNamed,
          HxProvider {
  /**
   * Loads all declared fields of this class if not already done
   * @return owner instance
   */
  default O loadFields() {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any fields.");
  }

  /**
   * Removes any defined fields in this class and also allows to reuse {@link #loadFields()}
   * @return owner instance
   */
  default O clearFields() {
    return setFields(Collections.emptyList());
  }

  /**
   * Gets the list with all fiends. This may resolve and load the declared fields of holding type
   * @return a list with all fields
   * @implNote never change this collection directly - use helper methods instead
   */
  default List<HxField> getFields() {
    return Collections.emptyList();
  }

  /**
   * @param list is the new list with all fields
   * @return owner instance
   */
  default O setFields(List<HxField> list) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any fields.");
  }

  /**
   * @param array is the list with all fields
   * @return owner instance
   */
  default O setFields(HxField ... array) {
    return setFields(Arrays.asList(array));
  }

  /**
   * Adds given field to this type at specific position
   *
   * @param field to add
   * @return owner instance
   * @throws IllegalArgumentException if given field is already present in this type
   * @implNote adding any field yourself doesn't lead to deactivation of {@link #loadFields()}
   */
  default O addField(HxField field) {
    return addFieldAt(getFields().size(), field);
  }

  /**
   * Adds given field to this type at specific position
   *
   * @param index where to insert given field
   * @param field to add
   * @return owner instance
   * @throws IllegalArgumentException if given field is already present in this type
   * @implNote adding any field yourself doesn't lead to deactivation of {@link #loadFields()}
   */
  default O addFieldAt(int index, HxField field) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any fields.");
  }

  /**
   * Removes the given field from this container
   * @param field to remove
   * @return owner instance
   * @throws IllegalArgumentException if given field doesn't belong to this type
   */
  default O removeField(HxField field) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any fields.");
  }


  /**
   * @param field to search for
   * @return <b>-1</b> if given field doesn't belong to this type,
   * otherwise zero-based position of the given field in the {@link #getFields()} list
   */
  default int indexOfField(HxField field) {
    if (!equals(field.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxField hxField : getFields()) {
      if (field == hxField || field.equals(hxField)) {
        return idx;
      }
      idx++;
    }
    return -1;
  }
}
