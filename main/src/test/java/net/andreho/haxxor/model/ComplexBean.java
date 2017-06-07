package net.andreho.haxxor.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 17:40.
 */
public class ComplexBean
    extends AbstractBean
    implements InterfaceD {

  private boolean boolValue;
  public byte byteValue;
  protected char charValue;
  short shortValue;
  volatile int intValue;
  private float floatValue;
  private long longValue;
  private double doubleValue;
  private String stringValue;
  private Class<?> classValue;
  private EnumA enumValue;

  private boolean[] booleanArray;
  public byte[] byteArray;
  protected char[] charArray;
  volatile short[] shortArray;
  private int[] intArray;
  private float[] floatArray;
  private long[] longArray;
  private double[] doubleArray;
  private String[] stringArray;
  private Class<?>[] classArray;
  private EnumA[] enumArray;

  @Override
  void someAbstractMethod() {

  }

  @Override
  protected String methodToOverride(final String a,
                                    final int b,
                                    final byte... array) {
    super.methodToOverride(a, b, array);
    return null;
  }

  public static String staticFunction() {
    return "ok";
  }

  public ComplexBean(final String name) {
    super(name);
  }

  private boolean isBoolValue() {
    return boolValue;
  }

  private void setBoolValue(final boolean boolValue) {
    this.boolValue = boolValue;
  }

  public byte getByteValue() {
    return byteValue;
  }

  public void setByteValue(final byte byteValue) {
    this.byteValue = byteValue;
  }

  protected char getCharValue() {
    return charValue;
  }

  protected void setCharValue(final char charValue) {
    this.charValue = charValue;
  }

  short getShortValue() {
    return shortValue;
  }

  void setShortValue(final short shortValue) {
    this.shortValue = shortValue;
  }

  public synchronized int getIntValue() {
    return intValue;
  }

  public synchronized void setIntValue(final int intValue) {
    this.intValue = intValue;
  }

  public strictfp float getFloatValue() {
    return floatValue;
  }

  public strictfp void setFloatValue(final float floatValue) {
    this.floatValue = floatValue;
  }

  public long getLongValue() {
    return longValue;
  }

  public void setLongValue(final long longValue) {
    this.longValue = longValue;
  }

  public double getDoubleValue() {
    return doubleValue;
  }

  public void setDoubleValue(final double doubleValue) {
    this.doubleValue = doubleValue;
  }

  public String getStringValue() {
    return stringValue;
  }

  public void setStringValue(final String stringValue) {
    this.stringValue = stringValue;
  }

  public Class<?> getClassValue() {
    return classValue;
  }

  public void setClassValue(final Class<?> classValue) {
    this.classValue = classValue;
  }

  public EnumA getEnumValue() {
    return enumValue;
  }

  public void setEnumValue(final EnumA enumValue) {
    this.enumValue = enumValue;
  }

  public boolean[] getBooleanArray() {
    return booleanArray;
  }

  public void setBooleanArray(final boolean ... booleanArray) {
    this.booleanArray = booleanArray;
  }

  public byte[] getByteArray() {
    return byteArray;
  }

  public void setByteArray(final byte[] byteArray) {
    this.byteArray = byteArray;
  }

  public char[] getCharArray() {
    return charArray;
  }

  public void setCharArray(final char[] charArray) {
    this.charArray = charArray;
  }

  public short[] getShortArray() {
    return shortArray;
  }

  public void setShortArray(final short[] shortArray) {
    this.shortArray = shortArray;
  }

  public int[] getIntArray() {
    return intArray;
  }

  public void setIntArray(final int[] intArray) {
    this.intArray = intArray;
  }

  public float[] getFloatArray() {
    return floatArray;
  }

  public void setFloatArray(final float[] floatArray) {
    this.floatArray = floatArray;
  }

  public long[] getLongArray() {
    return longArray;
  }

  public void setLongArray(final long[] longArray) {
    this.longArray = longArray;
  }

  public double[] getDoubleArray() {
    return doubleArray;
  }

  public void setDoubleArray(final double... doubleArray) {
    this.doubleArray = doubleArray;
  }

  public String[] getStringArray() {
    return stringArray;
  }

  public void setStringArray(final String[] stringArray) {
    this.stringArray = stringArray;
  }

  public Class<?>[] getClassArray() {
    return classArray;
  }

  public void setClassArray(final Class<?>[] classArray) {
    this.classArray = classArray;
  }

  public EnumA[] getEnumArray() {
    return enumArray;
  }

  public void setEnumArray(final EnumA ... enumArray) {
    this.enumArray = enumArray;
  }

  public Map<String, Integer> aMethod(String name) {
    return null;
  }

  public Map<String, Integer> aMethod(String name, int index) {
    return null;
  }

  public Map<String, Integer> aMethod(String name, long index) {
    return null;
  }

  public List<?> aMethod(String name, long ... index) {
    return null;
  }

  public Collection<Integer> aMethod(String name, int index, List<InterfaceA> list) {
    return null;
  }

  public Map<String, Integer> aMethod(String name, int index, Collection<InterfaceA> collection) {
    return null;
  }

  public Map<String, Integer> aMethod(String name, int index, List<InterfaceA> list, EnumC enumC) {
    return null;
  }
}
