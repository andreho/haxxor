package test;

import net.andreho.resources.Resource;
import org.apache.bcel.Const;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.PUTSTATIC;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;
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
public class BCELBenchmark
  extends AbstractBenchmark {

  public static void main(String[] args)
  throws Exception {
    loadResources();
    final long totalMemory = getTotalMemory();
    final Blackhole blackhole = createBlackhole();
    final BCELBenchmark benchmark = new BCELBenchmark();

    //testRead(blackhole, benchmark);

    testWrite(blackhole, benchmark);

    done(blackhole);
  }

  private static void testRead(final Blackhole blackhole,
                               final BCELBenchmark benchmark)
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
                                final BCELBenchmark benchmark)
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

  private static InstructionList createClassInitCode(
    final ClassGen classGen,
    final ConstantPoolGen cp) {
    return createClassInitCode(classGen, cp, true);
  }

  private static InstructionList createClassInitCode(
    final ClassGen classGen,
    final ConstantPoolGen cp,
    final boolean withReturn) {

    final InstructionList il = new InstructionList();
    il.append(
      new LDC(cp.addString(classGen.getClassName()))
    );
    il.append(
      new INVOKESTATIC(
        cp.addMethodref(Logger.class.getName(), "getLogger", AsmTreeApiBenchmark.LOGGER_FACTORY_SIGNATURE))
    );
    il.append(
      new PUTSTATIC(cp.addFieldref(classGen.getClassName(), "$LOG", AsmTreeApiBenchmark.LOGGER_DESC))
    );
    if (withReturn) {
      il.append(new RETURN());
    }
    return il;
  }

  private static InstructionList createLogGetter(final ClassGen classGen,
                                                 final ConstantPoolGen cp) {
    final InstructionList il = new InstructionList();
    il.append(
      new GETSTATIC(cp.addFieldref(classGen.getClassName(), "$LOG", AsmTreeApiBenchmark.LOGGER_DESC))
    );
    il.append(new ARETURN());
    return il;
  }

  @Override
  @Benchmark
  public void read(final Blackhole bh)
  throws Exception {
    org.apache.bcel.util.Repository repository = Repository.getRepository();
    repository.clear();

    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        String className = resource.getName().replace('/', '.');
        className = className.substring(0, className.length() - ".class".length());

        final JavaClass javaClass = repository.loadClass(className);
        if ("*".equals(javaClass.getClassName())) {
          bh.consume(javaClass);
          System.out.println("Unreachable");
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  @Override
  public void write(final Blackhole bh)
  throws Exception {
    org.apache.bcel.util.Repository repository = Repository.getRepository();
    repository.clear();

    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        String className = resource.getName().replace('/', '.');
        className = className.substring(0, className.length() - ".class".length());

        JavaClass javaClass = repository.loadClass(className);
        if (!javaClass.isInterface()) {
          final ClassGen classGen = new ClassGen(javaClass);
          modifyClass(javaClass, classGen);
          javaClass = classGen.getJavaClass();
        }
        byte[] bytes = javaClass.getBytes();
        bh.consume(bytes);
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  private void modifyClass(final JavaClass javaClass,
                           final ClassGen classGen) {
    if (javaClass.isInterface()) {
      return;
    }
    final ConstantPoolGen cp = classGen.getConstantPool();
    classGen.addInterface(LoggableInterface.class.getName());

    final FieldGen $LOG = new FieldGen(
      Const.ACC_PRIVATE | Const.ACC_STATIC | Const.ACC_FINAL,
      Type.getType(Logger.class),
      "$LOG",
      cp
    );

    classGen.addField($LOG.getField());

    Method clinit = null;
    for (Method method : classGen.getMethods()) {
      if ("<clinit>".equals(method.getName()) && method.isStatic()) {
        clinit = method;
        break;
      }
    }

    if (clinit == null) {
      MethodGen methodGen = new MethodGen(
        Const.ACC_STATIC, Type.VOID,
        new Type[0],
        new String[0], "<clinit>",
        javaClass.getClassName(),
        createClassInitCode(classGen, cp),
        cp);
      methodGen.setMaxLocals();
      methodGen.setMaxStack();
      classGen.addMethod(methodGen.getMethod());
    } else {
      MethodGen methodGen = new MethodGen(clinit, javaClass.getClassName(), cp);
      methodGen.getInstructionList()
               .insert(
                 createClassInitCode(classGen, cp, false)
               );

      methodGen.setMaxLocals();
      methodGen.setMaxStack();
      classGen.replaceMethod(clinit, methodGen.getMethod());
    }

    MethodGen logGetter = new MethodGen(
      Const.ACC_PUBLIC,
      Type.getType(Logger.class),
      new Type[0],
      new String[0], "log",
      javaClass.getClassName(),
      createLogGetter(classGen, cp),
      cp);
    logGetter.setMaxLocals();
    logGetter.setMaxStack();

    classGen.addMethod(logGetter.getMethod());
  }
}
