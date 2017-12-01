package net.andreho.haxxor.api;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 29.11.2017 at 07:03.
 */
public interface HxFieldOwner<O extends HxFieldOwner<O> & HxInitializable<InitializablePart, HxFieldOwner<O>>>
  extends HxInitializable<InitializablePart, HxFieldOwner<O>>,
          HxNamed,
          HxProvider {
  /**
   * Loads the declared fields if needed
   * @return owner instance
   */
  default O loadFields() {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any fields.");
  }

  /**
   * Gets the list with all fiends. This may resolve and load the declared fields of holding type
   * @return a list with all fields
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
   * Adds given field to this type at specific position
   *
   * @param field to add
   * @return owner instance
   * @throws IllegalArgumentException if given field is already present in this type
   */
  default O addField(HxField field) {
    return loadFields().addFieldAt(getFields().size(), field);
  }

  /**
   * Adds given field to this type at specific position
   *
   * @param index where to insert given field
   * @param field to add
   * @return owner instance
   * @throws IllegalArgumentException if given field is already present in this type
   */
  default O addFieldAt(int index, HxField field) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any fields.");
  }

  /**
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
  default int indexOf(HxField field) {
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
