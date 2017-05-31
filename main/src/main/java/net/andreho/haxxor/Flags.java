package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:45.
 */
public abstract class Flags {

  /**
   * Flag to skip method code. If this class is set <code>CODE</code>
   * attribute won't be visited. This can be used, for example, to retrieve
   * annotations for methods and method parameters.
   */
  public static final int SKIP_CODE = 1;

  /**
   * Flag to skip the debug information in the class. If this flag is set the
   * debug information of the class is not visited, i.e. the
   * {@link MethodVisitor#visitLocalVariable visitLocalVariable} and
   * {@link MethodVisitor#visitLineNumber visitLineNumber} methods will not be
   * called.
   */
  public static final int SKIP_DEBUG = 2;

  /**
   * Flag to skip the stack map frames in the class. If this flag is set the
   * stack map frames of the class is not visited, i.e. the
   * {@link MethodVisitor#visitFrame visitFrame} method will not be called.
   * This flag is useful when the {@link ClassWriter#COMPUTE_FRAMES} option is
   * used: it avoids visiting frames that will be ignored and recomputed from
   * scratch in the class writer.
   */
  public static final int SKIP_FRAMES = 4;

  /**
   * Flag to expand the stack map frames. By default stack map frames are
   * visited in their original format (i.e. "expanded" for classes whose
   * version is less than V1_6, and "compressed" for the other classes). If
   * this flag is set, stack map frames are always visited in expanded format
   * (this option adds a decompression/recompression step in ClassReader and
   * ClassWriter which degrades performances quite a lot).
   */
  public static final int EXPAND_FRAMES = 8;

  private Flags() {
  }
}
