package transformers;

import net.andreho.aop.transform.ClassTransformer;
import net.andreho.aop.transform.Transformer;
import net.andreho.common.anno.Order;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 18:41.
 */
@Order(1)
@Transformer
public class ClassTransformerA implements ClassTransformer {
   @Override
   public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined,
                           final ProtectionDomain protectionDomain, final byte[] classfileBuffer)
      throws IllegalClassFormatException {
      return classfileBuffer;
   }
}
