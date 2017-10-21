package examples;

import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 05.10.2017 at 15:59.
 */
public class WideningExample {
//  public void print(boolean val) {
//  }
//  public void print(byte val) {
//  }
  public void print(short val) {
  }
  public void print(char val) {
  }
  public void print(int val) {
  }
//  public void print(long val) {
//  }

  public void print(float val) {
  }
//  public void print(double val) {
//  }

  @Test
  void test() {
    print((byte)1);
  }
}
