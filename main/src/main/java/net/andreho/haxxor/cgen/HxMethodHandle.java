package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 16:33.
 */
public class HxMethodHandle {

  private final HxHandleTag tag;
  private final String owner;
  private final String name;
  private final String desc;
  private final boolean itf;

  /**
   * @param tag
   * @param owner
   * @param name
   * @param desc
   * @param itf
   */
  public HxMethodHandle(final HxHandleTag tag,
                        final String owner,
                        final String name,
                        final String desc,
                        final boolean itf) {
    this.tag = tag;
    this.owner = owner;
    this.name = name;
    this.desc = desc;
    this.itf = itf;
  }

  /**
   * The kind of field or method designated by this HxMethodHandle.
   */
  public HxHandleTag getTag() {
    return tag;
  }

  /**
   * The internal name of the class that owns the field or method designated
   * by this handle.
   */
  public String getOwner() {
    return owner;
  }

  /**
   * The name of the field or method designated by this handle.
   */
  public String getName() {
    return name;
  }

  /**
   * The descriptor of the field or method designated by this handle.
   */
  public String getDescriptor() {
    return desc;
  }

  /**
   * Indicate if the owner is an interface or not.
   */
  public boolean isInterface() {
    return itf;
  }

  @Override
  public String toString() {
    return "METHOD_HANDLE["+getTag()+","+getOwner()+","+getName()+","+getDescriptor()+","+isInterface()+"]";
  }
}
