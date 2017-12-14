package net.andreho.haxxor.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.stub.errors.HxStubException;
import net.andreho.haxxor.utils.ClassLoaderUtils;

import java.util.Optional;
import java.util.Set;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public interface HxType
  extends
    HxInheritanceManager<HxType>,
    HxTypeAnalysis<HxType>,
    HxFieldAnalysis<HxType>,
    HxMethodAnalysis<HxType>,
    HxInnerTypeManager<HxType>,
    HxAnnotated<HxType>,
    HxMember<HxType>,
    HxOwned<HxType>,
    HxGeneric<HxType>,
    HxGenericElement<HxType>,
    HxAccessible<HxType>,
    HxNamed,
    HxProvider {

  int ALLOWED_MODIFIERS =
    Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL |
    Opcodes.ACC_SUPER | Opcodes.ACC_INTERFACE | Opcodes.ACC_ABSTRACT | Opcodes.ACC_ANNOTATION |
    Opcodes.ACC_ENUM | Opcodes.ACC_SYNTHETIC;

  /**
   *
   */
  enum Modifiers
    implements HxModifier {
    // class, field, method
    PUBLIC(Opcodes.ACC_PUBLIC),
    // class, field, method
    PRIVATE(Opcodes.ACC_PRIVATE),
    // class, field, method
    PROTECTED(Opcodes.ACC_PROTECTED),
    // class, field, method
    STATIC(Opcodes.ACC_STATIC),
    // class, field, method, parameter
    FINAL(Opcodes.ACC_FINAL),
    // class
    SUPER(Opcodes.ACC_SUPER),
    // class
    INTERFACE(Opcodes.ACC_INTERFACE),
    // class, method
    ABSTRACT(Opcodes.ACC_ABSTRACT),
    // class, field, method, parameter
    SYNTHETIC(Opcodes.ACC_SYNTHETIC),
    // class
    ANNOTATION(Opcodes.ACC_ANNOTATION),
    // class(?) field inner
    ENUM(Opcodes.ACC_ENUM),
    // class, field, method
    DEPRECATED(Opcodes.ACC_DEPRECATED);

    final int bit;

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given type's modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }

  class Internals {
    public static final int FIELDS_LOADED = 0x4_0000;
    public static final int METHODS_LOADED = 0x8_0000;
  }

  /**
   * @return byte-code version
   */
  default Version getVersion() {
    return Version.V1_8;
  }

  /**
   * @param version of the type to use
   * @return
   * @throws IllegalStateException if this method not allowed
   */
  default HxType setVersion(Version version) {
    throw new IllegalStateException("Version of this type can't be changed: "+getName());
  }

  /**
   * @return
   */
  default Optional<HxSourceInfo> getSourceInfo() {
    return Optional.empty();
  }

  /**
   * @param source
   * @return
   * @throws IllegalStateException if this method not allowed
   */
  default HxType setSourceInfo(HxSourceInfo source) {
    throw new IllegalStateException("This type can't contain any source-info: "+getName());
  }

  /**
   * @return the standard binary Java classname of this type like: <br/>
   * <code>int</code>,
   * <code>byte[]</code>,
   * <code>java.lang.Object</code>,
   * <code>java.lang.String[][]</code> or
   * <code>java.util.Map$Entry</code> etc.)
   */
  String getName();

  /**
   * @return
   */
  default Optional<HxGenericType> getGenericType() {
    return Optional.empty();
  }

  /**
   * @return
   */
  default boolean isReference() {
    return this instanceof HxTypeReference;
  }

  /**
   * @return
   */
  default HxType loadFieldsAndMethods() {
    return this;
  }

  /**
   * Checks whether this class was already loaded by the given classloader (incl. its parents) or not
   * @param classLoader to check
   * @return <b>true</b> if this class was already loaded by the given classloader, <b>false</b> otherwise
   */
  default boolean wasAlreadyLoadedWith(ClassLoader classLoader) {
    if(isPrimitive() || isArray()) {
      return true;
    }
    return ClassLoaderUtils.wasAlreadyLoadedWith(classLoader, getName());
  }

  /**
   * @return uninitialized class instance for this type
   */
  default Class<?> toClass()
  throws ClassNotFoundException {
    return toClass(getHaxxor().getClassLoader());
  }

  /**
   * @param classLoader is the target classloader, where the referenced type prototype must be loaded
   * @return uninitialized class instance
   */
  default Class<?> toClass(ClassLoader classLoader)
  throws ClassNotFoundException {
    final String classname = toInternalName();
    return Class.forName(classname.replace('/', '.'), false, classLoader);
  }

  /**
   * @return a reference to this type
   */
  HxType toReference();

  /**
   * Creates bytecode representation of the actual type's state.
   *
   * @return the actual state of this type as loadable bytecode
   * @throws UnsupportedOperationException if this type can't be serialized
   */
  default byte[] toByteCode() {
    throw new UnsupportedOperationException("This type can't be serialized to bytecode: "+getName());
  }

  /**
   * @param stubClass to wire in
   * @return a list with all added elements that belong to the given stub class
   * @see net.andreho.haxxor.stub.Stub
   * @throws UnsupportedOperationException if this type can't be modified
   */
  default HxType addTemplate(Class<?> stubClass)
  throws HxStubException {
    if(stubClass.isPrimitive() || stubClass.isArray() || stubClass.isInterface()) {
      throw new IllegalArgumentException("Given stub type is invalid: "+stubClass.getName());
    }
    return addTemplate(getHaxxor().resolve(stubClass.getName(), 0));
  }

  /**
   * @param stubType to wire into this type
   * @return a list with all added elements that belong to the given stub class
   * @see net.andreho.haxxor.stub.Stub
   * @throws UnsupportedOperationException if this type can't be modified
   */
  default HxType addTemplate(HxType stubType)
  throws HxStubException {
    getHaxxor().injectStub(this, stubType);
    return this;
  }
}
