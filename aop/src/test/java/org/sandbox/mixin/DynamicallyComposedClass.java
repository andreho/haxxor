package org.sandbox.mixin;

/**
 * <br/>Created by a.hofmann on 30.09.2017 at 03:57.
 */
public class DynamicallyComposedClass { //implements Named, Identifiable
//  private static final AtomicLong COUNTER = new AtomicLong(0);
//
//  private String name;
//  private final long id;
//
  public DynamicallyComposedClass() {
    this(false);
  }
  private DynamicallyComposedClass(boolean dummy) {
  }

/*  public SpecificMixin() {
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
  } */

//
//  @Override
//  public String getName() {
//    return name;
//  }
//
//  @Override
//  public void setName(final String name) {
//    this.name = name;
//  }
//
//  @Override
//  public long getId() {
//    return id;
//  }
//
//  @Override
//  public String toString() {
//    return DynamicallyComposedClass.class.getSimpleName() + "{id="+id+", name='" + name + "\'}";
//  }
}
