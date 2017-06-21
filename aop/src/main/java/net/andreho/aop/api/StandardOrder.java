package net.andreho.aop.api;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:29.
 */
public interface StandardOrder {

  int MODIFY_TYPE = 100_000_000;
  int MODIFY_FIELD = 110_000_000;
  int MODIFY_METHOD = 120_000_000;
  int FORWARDING = 130_000_000;
  int FIELD_GET = 140_000_000;
  int FIELD_SET = 150_000_000;
  int FINALLY = 160_000_000;
  int CATCH = 170_000_000;
  int BEFORE = 180_000_000;
  int AFTER = 190_000_000;
}
