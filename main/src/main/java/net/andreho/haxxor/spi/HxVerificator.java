package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxMember;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 16:28.
 */
@FunctionalInterface
public interface HxVerificator<E extends HxMember<E>> {

  /**
   * Merges the given verification list into one verification step
   * @param list with verification to apply
   * @param <M>
   * @return a merged verificator
   */
  static <M extends HxMember<M>> HxVerificator<M> merge(HxVerificator<M> ... list) {
    if (list == null || list.length == 0) {
      throw new IllegalArgumentException("Given verificators' array can't be null or empty.");
    }
    for (int i = 0; i < list.length; i++) {
      HxVerificator<M> verificator = list[i];
      if (verificator == null) {
        throw new IllegalArgumentException("Verificator at index " + i + " is null.");
      }
    }

    final HxVerificator<M>[] array = list.clone();

    return element -> {
      HxVerificationResult first = HxVerificationResult.ok();
      HxVerificationResult current = first;

      for (HxVerificator<M> initializer : array) {
        HxVerificationResult result = initializer.verify(element);

        if(result.isFailed()) {
          if(current.isPassed()) {
            first = current = result;
          } else {
            current = current.append(result);
          }
        }
      }
      return first;
    };
  }

  /**
   * @param element to check
   * @return result of this verification
   * @see HxVerificationResult
   */
  HxVerificationResult verify(E element);
}
