package net.andreho.haxxor.cgen;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 15:13.
 */
class HxOpcodesHelper {
  private static final String[] NAMES;
  private static final int[] OPCODES_BY_NAME;

  static {
    Field[] sortedByName = HxOpcodes.class.getFields().clone();
    Arrays.sort(sortedByName, Comparator.comparing(Field::getName));

    NAMES = new String[sortedByName.length];
    OPCODES_BY_NAME = new int[sortedByName.length];

    try {
      for(int i = 0; i < sortedByName.length; i++) {
        Field byName = sortedByName[i];
        NAMES[i] = byName.getName();
        OPCODES_BY_NAME[i] = byName.getInt(null);
      }
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Unreachable.");
    }
  }

  private static int findWithName(Field[] fields, String name) {
    for (int i = 0; i<fields.length; i++) {
      if(name.equals(fields[i].getName())) {
        return i;
      }
    }
    throw new IllegalStateException();
  }
  private static int compareFieldsByIntValue(Field a, Field b) {
    try {
      int aVal = a.getInt(null);
      int bVal = b.getInt(null);
      return Integer.compare(aVal, bVal);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }

  static int opcodeByName(String name) {
    int idx = Arrays.binarySearch(NAMES, name);
    if(idx < 0) {
      return -1;
    }
    return OPCODES_BY_NAME[idx];
  }
}
