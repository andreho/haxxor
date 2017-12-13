package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.utils.NamingUtils;

/**
 * <br/>Created by a.hofmann on 07.12.2017 at 16:22.
 */
class HxImplTools {

  /**
   * @param type to check
   * @param desc to compare against
   * @return
   */
  static int checkDescriptorsParameters(final HxType type,
                                        final String desc) {
    return checkDescriptorsParameters(type, desc, 0, desc.length());
  }

  static int checkDescriptorsParameters(final HxType type,
                                        final String desc,
                                        int index,
                                        final int length) {
    int dim = 0;
    while (index < length) {
      char c = desc.charAt(index);
      switch (c) {
        case '[':
          dim++;
          break;
        case 'V':
        case 'Z':
        case 'B':
        case 'S':
        case 'C':
        case 'I':
        case 'F':
        case 'J':
        case 'D':
          if (!isPrimitiveTypeWithDimension(type, NamingUtils.primitiveDescriptorToPrimitiveClassname(c), dim)) {
            return -1;
          }
          return index + 1;
        case 'L':
          int sc = desc.indexOf(';', index + 2);
          if (sc < 0) {
            throw new IllegalArgumentException("Invalid descriptor: " + desc);
          }
          if (!isReferenceTypeWithDimension(
            type,
            desc,
            dim,
            index + 1,
            sc)) {
            return -1;
          }
          return sc + 1;
      }
      index++;
    }
    return -1;
  }

  private static boolean isPrimitiveTypeWithDimension(final HxType type,
                                                      final String classname,
                                                      final int dim) {
    int typeDim = 0;
    HxType componentType = type;
    while (componentType.isArray()) {
      componentType = componentType.getComponentType().get();
      typeDim++;
    }
    return typeDim == dim && componentType.hasName(classname);
  }

  private static boolean isReferenceTypeWithDimension(final HxType type,
                                                      final String descriptor,
                                                      final int dim,
                                                      final int from,
                                                      final int to) {

    int typeDim = 0;
    HxType componentType = type;
    while (componentType.isArray()) {
      componentType = componentType.getComponentType().get();
      typeDim++;
    }
    final String classname = componentType.getName();
    if (typeDim != dim || classname.length() != (to - from)) {
      return false;
    }
    for (int i = 0, len = classname.length(); i < len; i++) {
      char a = classname.charAt(i);
      char b = descriptor.charAt(i + from);
      if (a != b) {
        if ((a == '.' || a == '/') &&
            (b == '.' || b == '/')) {
          continue;
        }
        return false;
      }
    }
    return true;
  }

  /**
   * @param lhs
   * @param rhs
   * @param distance
   * @param arrayCost
   * @param extendsCost
   * @param interfaceCost
   * @return
   */
  static int estimateDistance(final HxType lhs,
                              final HxType rhs,
                              int distance,
                              final int arrayCost,
                              final int extendsCost,
                              final int interfaceCost) {
    if (lhs == null || rhs == null) {
      return -1;
    } else if (lhs.equals(rhs)) {
      return distance;
    } else if (lhs.isPrimitive()) {
      return -1;
    } else if (lhs.isArray()) {
      if (lhs.getDimension() != rhs.getDimension()) {
        return -1;
      }
      return estimateDistance(
        lhs.getComponentType().get(),
        rhs.getComponentType().get(),
        distance + arrayCost,
        arrayCost,
        extendsCost,
        interfaceCost
      );
    }

    if (rhs.isInterface()) {
      for (HxType itf : lhs.getInterfaces()) {
        int dist = estimateDistance(
          itf,
          rhs,
          distance += interfaceCost,
          arrayCost,
          extendsCost,
          interfaceCost
        );

        if (dist > -1) {
          return dist;
        }
      }
    }
    if (lhs.hasSuperType()) {
      return estimateDistance(
        lhs.getSuperType().get(),
        rhs,
        distance + extendsCost,
        arrayCost,
        extendsCost,
        interfaceCost
      );
    }
    return -1;
  }
}
