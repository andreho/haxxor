package net.andreho.aop.api;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:29.
 */
public interface StandardOrder {

  int MODIFY_TYPE = 10_000;
  int MODIFY_FIELD = 11_000;
  int MODIFY_METHOD = 12_000;
  int FORWARDING = 13_000;
  int FIELD_GET = 14_000;
  int FIELD_SET = 15_000;
  int FINALLY = 16_000;
  int CATCH = 17_000;
  int BEFORE = 18_000;
  int AFTER = 19_000;
}
