package net.andreho.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 * Created by a.hofmann on 20.05.2016.
 */
public class Agent {
   private static final Logger LOG = Logger.getLogger(Agent.class.getName());
   private static final ServiceLoader<ClassFileTransformer> TRANSFORMERS =
      ServiceLoader.load(ClassFileTransformer.class);

   private static volatile Instrumentation INSTRUMENTATION;
   private static volatile String ARGS;

   private Agent() {}

   /**
    * Main entry point for a premain javaagent implementation
    * (activated with the following JVM parameter: <code>-javaagent:jarpath[=options]</code>
    * and additional Manifest information)
    *
    * @param args that were passed to javaagent
    * @param inst central instrumentation instance for further use
    * @see
    * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/package-summary.html">Instrumentation</a>
    */
   public static void premain(String args, Instrumentation inst) {
      agentmain(args, inst);
   }

   /**
    * Main entry point for an ad-hoc javaagent implementation
    * (activated with the following JVM parameter: <code>-javaagent:jarpath[=options]</code>
    * and additional Manifest information)
    *
    * @param args that were passed to javaagent
    * @param inst central instrumentation instance for further use
    * @see
    * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/package-summary.html">Instrumentation</a>
    */
   public static void agentmain(String args, Instrumentation inst) {
      Agent.ARGS = Objects.requireNonNull(args, "Arguments are null.");
      Agent.INSTRUMENTATION = Objects.requireNonNull(inst, "Instrumentation is null.");

      LOG.fine("Agent was successfully installed.");
      installClassFileTransformers(args, inst);
   }

   private static void installClassFileTransformers(final String args, final Instrumentation inst) {

      for(final ClassFileTransformer transformer : TRANSFORMERS) {
         Objects.requireNonNull(transformer,"ClassFileTransformer can't be null.");
         LOG.fine("ClassFileTransformer located and added: "+transformer.getClass().getName());
         inst.addTransformer(transformer, true);
      }
   }

   /**
    * @return an instrumentation instance that was gave to this javaagent.
    * @apiNote potential security breach
    */
   public static Instrumentation getInstrumentation() {
      return INSTRUMENTATION;
   }

   /**
    * @return plain argument list that was gave to this javaagent.
    */
   public static String getArgs() {
      return ARGS;
   }

   /**
    * Calculates size of given instance
    *
    * @param o any not-null instance
    * @return an implementation-specific approximation of the amount of storage consumed by the specified object
    * @throws IllegalStateException if this javaagent wasn't attached to system yet.
    */
   public static long getSizeOf(Object o) {
      if (INSTRUMENTATION == null) {
         throw new IllegalStateException("Please attach javaagent first.");
      }
      return INSTRUMENTATION.getObjectSize(o);
   }
}
