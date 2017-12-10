package net.andreho.haxxor.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

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
    ENUM(Opcodes.ACC_ENUM);

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

  /**
   * @return byte-code version
   */
  default Version getVersion() {
    return Version.V1_8;
  }

  /**
   * @param version of the type to use
   * @return
   */
  default HxType setVersion(Version version) {
    return this;
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
   */
  default HxType setSourceInfo(HxSourceInfo source) {
    return this;
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
    getHaxxor().resolveFieldsAndMethods(this);
    return this;
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
   * @see net.andreho.haxxor.api.stub.Stub
   * @throws UnsupportedOperationException if this type can't be modified
   */
  default HxType addTemplate(Class<?> stubClass) {
    return addTemplate(getHaxxor().resolve(stubClass));
  }

  /**
   * @param stubType to wire into this type
   * @return a list with all added elements that belong to the given stub class
   * @see net.andreho.haxxor.api.stub.Stub
   * @throws UnsupportedOperationException if this type can't be modified
   */
  default HxType addTemplate(HxType stubType) {
    return this;
  }
}
