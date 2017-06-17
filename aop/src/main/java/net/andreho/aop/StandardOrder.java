package net.andreho.aop;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:29.
 */
public interface StandardOrder {
  int MODIFY_TYPE = 500;
  int MODIFY_FIELD = 600;
  int MODIFY_METHOD = 700;
  int FORWARDING = 1000;
  int FIELD_GET = 1100;
  int FIELD_SET = 1200;
  int FINALLY = 1300;
  int CATCH = 1400;
  int BEFORE = 1500;
  int AFTER = 1600;
}
