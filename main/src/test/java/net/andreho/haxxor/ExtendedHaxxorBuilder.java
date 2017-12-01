package net.andreho.haxxor;

import net.andreho.haxxor.api.HxType;

import java.util.Map;
import java.util.TreeMap;

public class ExtendedHaxxorBuilder extends HaxxorBuilder {
  @Override
  public Map<String, HxType> createResolvedCache(final Hx haxxor) {
    return new TreeMap<>();
  }
  public static void main(String[] args){
    final Hx hx = new ExtendedHaxxorBuilder().build();
  }
}