package org.sandbox.mixin;

import net.andreho.aop.api.mixin.Mixin;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <br/>Created by a.hofmann on 30.09.2017 at 03:27.
 */
@Mixin
public class SpecificMixin
  implements Named,
             Identifiable {
  @Mixin.Ignore
  public static final String PARAMETER_NAME = "name";
  @Mixin.Ignore
  public static final String PARAMETER_ID = "id";

  private static final AtomicLong COUNTER; // = new AtomicLong(0);

  private String name;
  private final long id;

  public SpecificMixin() {
    this.name = "<undefined>";
    this.id = COUNTER.getAndIncrement();
  }

  public SpecificMixin(@Mixin.Parameter(PARAMETER_NAME) final String name) {
    this.name = name;
    this.id = COUNTER.getAndIncrement();
  }

  public SpecificMixin(@Mixin.Parameter(PARAMETER_ID) final long id) {
    this.name = "<undefined>";
    this.id = id;
  }

  public SpecificMixin(@Mixin.Parameter(PARAMETER_NAME) final String name, @Mixin.Parameter(PARAMETER_ID) final long id) {
    this.name = name;
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public String toString() {
    return SpecificMixin.class.getSimpleName() + "{id="+id+", name='" + name + "\'}";
  }

  static {
    System.out.println("");
    COUNTER = new AtomicLong(0);
  }
}
