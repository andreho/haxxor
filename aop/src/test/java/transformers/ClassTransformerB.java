package transformers;

import net.andreho.aop.transform.ClassTransformer;
import net.andreho.aop.transform.Transformer;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 18:41.
 */
@Transformer.Order(2)
@Transformer
public class ClassTransformerB implements ClassTransformer {
   @Override
   public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined,
                           final ProtectionDomain protectionDomain, final byte[] classfileBuffer)
      throws IllegalClassFormatException {
      return classfileBuffer;
   }
}
