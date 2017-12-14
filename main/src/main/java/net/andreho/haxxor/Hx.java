package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.ClassWriter;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxClassLoaderHolder;
import net.andreho.haxxor.spi.HxClassResolver;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxDeduplicationCacheAware;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxInitializationAware;
import net.andreho.haxxor.spi.HxProvidable;
import net.andreho.haxxor.spi.HxStubInjector;
import net.andreho.haxxor.spi.HxTypeSerializer;
import net.andreho.haxxor.spi.HxVerificationAware;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 22:31.
 */
public interface Hx extends HxClassnameNormalizer,
                            HxElementFactory,
                            HxClassLoaderHolder,
                            HxInitializationAware,
                            HxTypeSerializer,
                            HxVerificationAware,
                            HxDeduplicationCacheAware,
                            HxStubInjector,
                            HxClassResolver {

  /**
   * @return
   */
  static Hx create() {
    return builder().build();
  }

  /**
   * @return
   */
  static HaxxorBuilder builder() {
    return HaxxorBuilder.newBuilder();
  }

  /**
   * @param classname
   * @return
   */
  boolean hasReference(String classname);

  /**
   * @param classname
   * @return
   */
  HxType reference(String classname);

  /**
   * @param aClass
   * @return
   */
  HxType reference(Class<?> aClass);

  /**
   * @param classnames
   * @return
   */
  HxType[] references(String... classnames);

  /**
   * @param classes
   * @return
   */
  HxType[] references(Class<?>... classes);

  /**
   * @param classnames
   * @return
   */
  List<HxType> referencesAsList(String... classnames);

  /**
   * @param classname
   * @return
   */
  boolean hasResolved(String classname);

  /**
   * @param classname
   * @return
   */
  HxType resolve(String classname);

  /**
   * @param cls
   * @return
   */
  HxType resolve(Class<?> cls);

  /**
   * @param classname
   * @param flags
   * @return
   */
  HxType resolve(String classname, int flags);

  /**
   * @param classname
   * @param byteCode
   * @return
   */
  HxType resolve(String classname, byte[] byteCode);

  /**
   * @param classname
   * @param byteCode
   * @param flags
   * @return
   */
  HxType resolve(String classname,
                 byte[] byteCode,
                 int flags);

  /**
   * @param classname
   * @param typeOrReference
   * @return
   */
  HxType register(String classname,
                  HxType typeOrReference);

  /**
   * @param hxType
   */
  void resolveFields(HxType hxType);

  /**
   * @param hxType
   */
  void resolveMethods(HxType hxType);

  /**
   * @param type
   */
  void resolveFieldsAndMethods(HxType type);

  /**
   * @param providable
   * @param <T>
   * @return
   */
  <T> T providePart(HxProvidable<T> providable);

  /**
   * <br/>Created by a.hofmann on 30.05.2017 at 12:45.
   *
   * @see #SKIP_CODE
   * @see #SKIP_DEBUG
   * @see #SKIP_FRAMES
   * @see #SKIP_FIELDS
   * @see #SKIP_METHODS
   * @see #SKIP_CLASS_INTERNALS
   */
  abstract class Flags {

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

//    /**
//     * Flag to expand the stack map frames. By default stack map frames are
//     * visited in their original format (i.e. "expanded" for classes whose
//     * version is less than V1_6, and "compressed" for the other classes). If
//     * this flag is set, stack map frames are always visited in expanded format
//     * (this option adds a decompression/recompression step in ClassReader and
//     * ClassWriter which degrades performances quite a lot).
//     */
//    public static final int EXPAND_FRAMES = 8;

    /**
     * Don't process and store all properties of a class incl. its annotations.
     * This means that this class must already exist in the managing haxxor-instance.
     */
    public static final int SKIP_CLASS_INTERNALS = 0x1_0000;
    /**
     * Don't process and store declared fields
     */
    public static final int SKIP_FIELDS = HxType.Internals.FIELDS_LOADED;
    /**
     * Don't process and store declared methods and constructors
     */
    public static final int SKIP_METHODS = HxType.Internals.METHODS_LOADED;

    private Flags() {
    }
  }
}
