package net.andreho.haxxor.stub.identifiable;

import net.andreho.haxxor.stub.Stub;

public class IdentifiableTargetClass extends OtherClass {
  private final String name;
  public IdentifiableTargetClass(String name) {
    this.name = name;
  }
  public IdentifiableTargetClass(String name, @Stub.Named long id) {
    this(name);
  }
  public String getName() {
    return name;
  }
}
