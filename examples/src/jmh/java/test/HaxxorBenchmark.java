package test;

import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.HaxxorBuilder;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxDeduplicationCache;
import net.andreho.haxxor.spi.impl.NoOpDeduplicationCache;
import net.andreho.resources.Resource;
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

import static test.AsmTreeApiBenchmark.LOGGER_DESC;
import static test.AsmTreeApiBenchmark.LOGGER_FACTORY_SIGNATURE;
import static test.AsmTreeApiBenchmark.LOGGER_INTERNAL_TYPE;

/**
 * <br/>Created by a.hofmann on 23.11.2017 at 17:20.
 */
@State(Scope.Thread)
@Threads(1)
@Timeout(time = 5)
@Fork(value = 1, jvmArgs = "-Xms8g -Xmx8g")
@Warmup(time = 3, timeUnit = TimeUnit.MINUTES)
@Measurement(iterations = 100)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class HaxxorBenchmark extends AbstractBenchmark {
  @Override
  @Benchmark
  public void read(final Blackhole bh) throws Exception {
    final Hx hx = new HaxxorBuilder() {
      @Override
      public HxDeduplicationCache createDeduplicationCache(final Hx haxxor) {
//        return new DefaultTrieBasedDeduplicationCache();
        return new NoOpDeduplicationCache();
      }
    }.build();

    Resource current = null;

    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        String className = resource.getName().replace('/', '.');
        className = className.substring(0, className.length() - ".class".length());

        HxType type = hx.resolve(className, resource.toByteArray(), 0);

        if (type.getName().equals("*")) {
          bh.consume(type);
          System.out.println("Unreachable");
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  @Override
  @Benchmark
  public void write(final Blackhole bh) throws Exception {
    final Hx hx = new HaxxorBuilder() {
      @Override
      public HxDeduplicationCache createDeduplicationCache(final Hx haxxor) {
//        return new DefaultTrieBasedDeduplicationCache();
        return new NoOpDeduplicationCache();
      }
    }.build();

    Resource current = null;

    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        String className = resource.getName().replace('/', '.');
        className = className.substring(0, className.length() - ".class".length());

        HxType type = hx.resolve(className, resource.toByteArray(), 0);

        modifyType(hx, type);

        byte[] bytes = type.toByteCode();
        Debugger.trace(bytes);
        bh.consume(bytes);
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  public static void main(String[] args)
  throws Exception {
    final long totalMemory = getTotalMemory();
    final Blackhole blackhole = createBlackhole();
    final HaxxorBenchmark benchmark = new HaxxorBenchmark();

    //testRead(blackhole, benchmark);

    testWrite(blackhole, benchmark);

    done(blackhole);
  }

  private static void testRead(final Blackhole blackhole,
                               final HaxxorBenchmark benchmark)
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
                                final HaxxorBenchmark benchmark)
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

  private void modifyType(final Hx hx, final HxType type) {
    if(!type.isInterface()) {
      type
        .addInterface(LoggableInterface.class)
        .addField(hx.createField(Logger.class, "$LOG")
                    .makePrivate().makeFinal().makeStatic());

      type.findOrCreateClassInitializer().map(clinit -> {
        clinit.getBody().getFirst().asStream()
          .LDC(type.getName())
          .INVOKESTATIC(LOGGER_INTERNAL_TYPE, "getLogger", LOGGER_FACTORY_SIGNATURE, false)
          .PUTSTATIC(type.toInternalName(), "$LOG", LOGGER_DESC);
        return clinit;
      });

      HxMethod log = hx.createMethod(Logger.class, "log");
      log.getBody().build()
         .GETSTATIC(type.toInternalName(), "$LOG", LOGGER_DESC)
         .ARETURN();
      type.addMethod(log);
    }
  }

//  private void modifyTypeWithTemplate(final Hx hx, final HxType type) {
//    if(!type.isInterface()) {
//      type.addTemplate(Template.class);
//    }
//  }
}
//class Template implements LoggableInterface {
//  @Stub.Class static Class<?> __class__(){ return Template.class; }
//  private static final Logger $LOG = Logger.getLogger(__class__().getName());
//  @Override
//  public Logger log() {
//    return $LOG;
//  }
//}
