package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 23:49.
 */
public class FieldAccessFragment {
  private static int staticField;
  private int memberField;

  public static int getStaticField() {
    return staticField;
  }

  public static void setStaticField(final int staticField) {
    FieldAccessFragment.staticField = staticField;
  }

  public int getMemberField() {
    return memberField;
  }

  public void setMemberField(final int memberField) {
    this.memberField = memberField;
  }
}
