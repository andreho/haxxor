package test;

import net.andreho.resources.Resource;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 23.11.2017 at 17:20.
 */
@State(Scope.Thread)
@Threads(2)
@Timeout(time = 5)
@Fork(value = 1, jvmArgs = "-Xms8g -Xmx8g")
@Warmup(time = 1, timeUnit = TimeUnit.MINUTES)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MINUTES)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
public class AsmTreeApiBenchmark
  extends AbstractBenchmark implements Opcodes {

  static final String LOGGER_DESC = Type.getDescriptor(Logger.class);
  static final String LOGGER_FACTORY_SIGNATURE = "(Ljava/lang/String;)" + LOGGER_DESC;
  static final String LOGGER_GETTER_DESC = "()" + LOGGER_DESC;
  static final String LOGGER_INTERNAL_TYPE = Type.getInternalName(Logger.class);
  static final String LOGGABLE_INTERFACE = Type.getInternalName(LoggableInterface.class);

  public static void main(String[] args)
  throws Exception {
    final long totalMemory = getTotalMemory();
    final Blackhole blackhole = createBlackhole();
    final AsmTreeApiBenchmark benchmark = new AsmTreeApiBenchmark();

    //testRead(blackhole, benchmark);

    testWrite(blackhole, benchmark);

    done(blackhole);
  }

  private static void testRead(final Blackhole blackhole,
                               final AsmTreeApiBenchmark benchmark)
  throws Exception {
    for (int i = 0; i < 100; i++) {
      System.gc();

      long startTime = System.nanoTime();
      long startMemory = getCurrentlyUsedMemory();

      benchmark.read(blackhole);

      long stopTime = System.nanoTime();
      long stopMemory = getCurrentlyUsedMemory();

      System.out.println(i + "\t" + (stopTime - startTime) + "\t" + (stopMemory - startMemory));
    }
  }

  private static void testWrite(final Blackhole blackhole,
                                final AsmTreeApiBenchmark benchmark)
  throws Exception {
    for (int i = 0; i < 100; i++) {
      System.gc();

      long startTime = System.nanoTime();
      long startMemory = getCurrentlyUsedMemory();

      benchmark.write(blackhole);

      long stopTime = System.nanoTime();
      long stopMemory = getCurrentlyUsedMemory();

      System.out.println(i + "\t" + (stopTime - startTime) + "\t" + (stopMemory - startMemory));
    }
  }

  @Override
  @Benchmark
  public void read(final Blackhole bh)
  throws Exception {
    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        //System.out.println(resource.getName());
        final ClassReader cr = new ClassReader(resource.toByteArray());
        final ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        bh.consume(cn);
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  @Override
  @Benchmark
  public void write(final Blackhole bh)
  throws Exception {
    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        //System.out.println(resource.getName());
        final ClassReader cr = new ClassReader(resource.toByteArray());
        final ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        modifyNode(cn);

        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        bh.consume(cw.toByteArray());
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  private void modifyNode(final ClassNode cn) {
    if ((cn.access & Opcodes.ACC_INTERFACE) == 0) {
      cn.interfaces.add(LOGGABLE_INTERFACE);
      createLogField(cn);
      createOrExtendClassInitializer(cn);
      createLogGetter(cn);
    }
  }

  private void createLogField(final ClassNode cn) {
    for (Object node : cn.fields) {
      FieldNode fn = (FieldNode) node;
      if ("$LOG".equals(fn.name) && LOGGER_DESC.equals(fn.desc)) {
        return;
      }
    }
    FieldNode fn = new FieldNode(
      ACC_PRIVATE | ACC_STATIC | ACC_FINAL, "$LOG", LOGGER_DESC, null, null
    );
    cn.fields.add(fn);
  }

  private void createOrExtendClassInitializer(final ClassNode cn) {
    MethodNode clinit = null;
    for (Object node : cn.methods) {
      MethodNode mn = (MethodNode) node;
      if ("<clinit>".equals(mn.name) && "()V".equals(mn.desc)) {
        clinit = mn;
        break;
      }
    }
    if(clinit == null) {
      clinit = new MethodNode(
        ACC_STATIC, "<clinit>", "()V", null, null
      );
      clinit.instructions.add(new InsnNode(RETURN));
      cn.methods.add(clinit);
    }

    final InsnList code = new InsnList();
    code.add(new LdcInsnNode(cn.name.replace('/', '.')));
    code.add(new MethodInsnNode(
      INVOKESTATIC,
      LOGGER_INTERNAL_TYPE,
      "getLogger",
      LOGGER_FACTORY_SIGNATURE,
      false)
    );
    code.add(new FieldInsnNode(PUTSTATIC, cn.name, "$LOG", LOGGER_DESC));
    clinit.instructions.insert(code);
  }

  private void createLogGetter(final ClassNode cn) {
    for (Object node : cn.methods) {
      MethodNode mn = (MethodNode) node;
      if ("log".equals(mn.name) && LOGGER_GETTER_DESC.equals(mn.desc)) {
        return;
      }
    }

    MethodNode logGetter = new MethodNode(
      ACC_PUBLIC, "log", LOGGER_GETTER_DESC, null, null
    );
    logGetter.instructions.add(
      new FieldInsnNode(GETSTATIC, cn.name, "$LOG", LOGGER_DESC));
    logGetter.instructions.add(new InsnNode(ARETURN));
    cn.methods.add(logGetter);
  }
}
