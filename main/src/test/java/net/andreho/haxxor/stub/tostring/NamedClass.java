package net.andreho.haxxor.stub.tostring;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 01:18.
 */
class NamedClass implements NameProvider {
  private String name;

  @Override
  public String getName() {
    return name;
  }
  @Override
  public void setName(String name) {
    this.name = name;
  }
  @Override
  public String toString() {
    return name;
  }
}
