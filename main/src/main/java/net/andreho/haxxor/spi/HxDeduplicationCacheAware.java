package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 18:28.
 */
public interface HxDeduplicationCacheAware {

  /**
   * @return the associated deduplication cache
   */
  HxDeduplicationCache getDeduplicationCache();
}
