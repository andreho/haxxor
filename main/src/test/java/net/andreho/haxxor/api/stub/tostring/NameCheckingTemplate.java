package net.andreho.haxxor.api.stub.tostring;
import net.andreho.haxxor.api.stub.Stub;

interface NameProvider {
  String getName();
  void setName(String name);
}

class NamedClass implements NameProvider {
  protected String name;
  @Override
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String toString() {
    return name;
  }
}

@Stub(requires = {NameProvider.class})
public abstract class NameCheckingTemplate implements NameProvider {
  @Stub.Method
  @Override public abstract String getName();
  @Stub.Method("original_")
  abstract void original_setName(String name);

  @Stub.Override
  @Override public void setName(String name) {
    if(name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Invalid name");
    }
    String oldName = getName();
    original_setName(name);
    System.out.println("Name was changed from '"+oldName+"' to '"+name+"'");
  }
}
