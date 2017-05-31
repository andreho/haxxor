package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public interface Dumpable {

  /**
   * Dumps this instruction to the given {@link CodeStream code stream} using the provided {@link Context context}
   *
   * @param context    to use
   * @param codeStream to dump this instruction to
   */
  void dumpTo(Context context, CodeStream codeStream);
}
