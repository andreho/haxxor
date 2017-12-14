package test;

import net.andreho.agent.ClassFileTransformerService;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.utils.ClassLoaderUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicReference;

import static test.HaxxorBenchmark.modifyType;

/**
 * -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * -Dimpl=haxxor
 * <br/>Created by a.hofmann on 11.12.2017 at 17:49.
 */
public class Agent implements ClassFileTransformerService {
  private static final AtomicReference<Hx> HX_ATOMIC_REFERENCE = new AtomicReference<>();
  private static final ClassFileTransformer TRANSFORMER;
  private static final Method LOADED_CLASS;

  static {
    TRANSFORMER = Agent::modifyWithHaxxor;
    try {
      LOADED_CLASS = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
      LOADED_CLASS.setAccessible(true);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  static boolean checkAlreadyLoaded(ClassLoader classLoader, String classname) {
    return ClassLoaderUtils.wasAlreadyLoadedWith(classLoader, classname);
  }

  @Override
  public int compareTo(final ClassFileTransformerService o) {
    return 0;
  }

  @Override
  public byte[] transform(final ClassLoader loader,
                          final String className,
                          final Class<?> classBeingRedefined,
                          final ProtectionDomain protectionDomain,
                          final byte[] classfileBuffer)
  throws IllegalClassFormatException {
    if(className != null && className.startsWith("org/hibernate/")) {
      //Don't know why!?
      if("org/hibernate/loader/BatchFetchStyle".equals(className) ||
        "org/hibernate/persister/entity/SingleTableEntityPersister".equals(className)) {
        return null;
      }
      //System.out.println("Target: " + className);
      if(TRANSFORMER != null) {
        return TRANSFORMER.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
      }
    }
    return null;
  }

  protected static byte[] modifyWithHaxxor(final ClassLoader loader,
                                           final String className,
                                           final Class<?> classBeingRedefined,
                                           final ProtectionDomain protectionDomain,
                                           final byte[] classfileBuffer) {
    synchronized (loader) {
      final String binaryClassname = className.replace('/', '.');
      if(checkAlreadyLoaded(loader, binaryClassname)) {
        return null;
      }

      final Hx hx = initializeHaxxor();
      final HxType type = hx.resolve(binaryClassname, classfileBuffer);
      modifyType(hx, type);
      byte[] byteCode = type.toByteCode();
      return byteCode;
    }
  }

  private static Hx initializeHaxxor() {
    Hx hx;
    if((hx = HX_ATOMIC_REFERENCE.get()) == null) {
      if(!HX_ATOMIC_REFERENCE.compareAndSet(null, hx = Hx.create())) {
        hx = HX_ATOMIC_REFERENCE.get();
      }
    }
    return hx;
  }
}
