package net.andreho.aop.spi.impl.arguments;

import net.andreho.aop.spi.Arguments;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 10:22.
 */
public class ArgumentsFactory {
  private final Map<String, Class<? extends Arguments>> cached;
  private final ReentrantReadWriteLock readWriteLock;

  public ArgumentsFactory() {
    this(new HashMap<>());
  }

  public ArgumentsFactory(final Map<String, Class<? extends Arguments>> cached) {
    this.cached = cached;
    this.readWriteLock = new ReentrantReadWriteLock();

    throw new UnsupportedOperationException("WIP");
  }
}
