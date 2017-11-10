package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 23:29.
 */
public abstract class HxParameters {

  public static final String HX_BYTECODE_LOADER_MAX_CACHE_SIZE = "hx.bytecode.loader.max_cache_size";
  public static final String HX_DEDUPLICATION_MIN_ENTRY_LENGTH = "hx.deduplication.min_entry_length";
  public static final String HX_DEDUPLICATION_MAX_ENTRY_LENGTH = "hx.deduplication.max_entry_length";

  private HxParameters() {
    throw new UnsupportedOperationException();
  }
}
