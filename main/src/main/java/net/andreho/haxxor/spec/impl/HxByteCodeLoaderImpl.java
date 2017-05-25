package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.loading.HxByteCodeLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class HxByteCodeLoaderImpl implements HxByteCodeLoader {
   private final Haxxor haxxor;
   //----------------------------------------------------------------------------------------------------------------

   public HxByteCodeLoaderImpl(final Haxxor haxxor) {
      this.haxxor = Objects.requireNonNull(haxxor);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public Haxxor getHaxxor() {
      return haxxor;
   }

   @Override
   @SuppressWarnings("Duplicates")
   public byte[] load(final String className) {
      final ClassLoader classLoader = getHaxxor().getClassLoader();

      try (InputStream inputStream = classLoader.getResourceAsStream(className.replace('.', '/') + ".class")) {
         return Debugger.toByteArray(inputStream);
      } catch (IOException e) {
         throw new IllegalArgumentException(
               "Class '" + className + "' not found by current class loader: " + classLoader, e);
      }
   }
}
