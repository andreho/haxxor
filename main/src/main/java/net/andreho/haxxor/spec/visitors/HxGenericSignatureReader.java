package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.signature.SignatureReader;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 16:46.
 */
public class HxGenericSignatureReader
    extends SignatureReader {
  /**
   * Constructs a {@link SignatureReader} for the given signature.
   *
   * @param signature A <i>ClassSignature</i>, <i>MethodTypeSignature</i>, or
   *                  <i>FieldTypeSignature</i>.
   */
  public HxGenericSignatureReader(final String signature) {
    super(signature);
  }
}
