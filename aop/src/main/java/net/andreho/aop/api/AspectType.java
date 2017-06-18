package net.andreho.aop.api;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:33.
 */
public enum AspectType {
  MODIFY_TYPE,
  MODIFY_FIELD,
  MODIFY_METHOD,
  FORWARDING,
//  PATCH,
  AFTER,
  BEFORE,
  FINALLY,
  CATCH,
  FIELD_GET,
  FIELD_SET
}
