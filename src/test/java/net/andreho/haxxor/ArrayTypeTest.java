package net.andreho.haxxor;

import junit.framework.AssertionFailedError;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.model.AnnotationB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 08.12.2017 at 14:57.
 */
class ArrayTypeTest {
  private Hx hx;
  @BeforeEach
  void prepareHaxxor() {
    hx = Hx.create();
  }

  @Test
  void testArrays() {
    HxType type = hx.resolve(String[][][].class);
    testArrayType(type, 3, String[][][].class, String.class);
    type = hx.resolve(int[].class);
    testArrayType(type, 1, int[].class, int.class);
  }

  private void testArrayType(HxType type,
                             int dims,
                             Class<?> arrayType,
                             Class<?> component) {
    while(arrayType.getComponentType() != null) {

      assertEquals(dims, type.getDimension());
      assertTrue((dims > 0) == type.isArray());
      String name = append(component.getName(), "[]", dims);
      assertEquals(name, type.getName());
      assertEquals(Type.getDescriptor(arrayType), type.toDescriptor());
      assertTrue(type.hasName(name));
      assertTrue(type.hasDescriptor(Type.getDescriptor(arrayType)));
      assertEquals(type.toInternalName(), Type.getInternalName(arrayType));

      type = type.getComponentType().orElseThrow(AssertionFailedError::new);
      arrayType = arrayType.getComponentType();
      dims--;
    }
  }

  private String append(final String str,
                        final String suffix,
                        final int count) {
    StringBuilder builder = new StringBuilder(str);
    for(int i = 0; i<count; i++) {
      builder.append(suffix);
    }
    return builder.toString();
  }
}

class AnnotatedType extends @AnnotationB() Object implements @AnnotationB() Serializable {
  private int value;
  private int[] intValues;
  private String[] stringValues;
  public AnnotatedType(final int value) {
    this.value = value;
  }
  public int getValue() {
    return value;
  }
  public void setValue(final int value) {
    this.value = value;
  }
}