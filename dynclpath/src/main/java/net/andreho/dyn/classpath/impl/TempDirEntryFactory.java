package net.andreho.dyn.classpath.impl;

import net.andreho.dyn.classpath.Entry;
import net.andreho.dyn.classpath.EntryFactory;

import java.net.URL;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 13:18.
 */
public class TempDirEntryFactory
  implements EntryFactory {

  @Override
  public Entry createFor(final String id,
                         final ClassLoader classLoader,
                         final URL classPathUrl) {
    if(!"file".equals(classPathUrl.getProtocol())) {
      return null;
    } 

    try {
      return new TempDirEntry(id, classLoader, classPathUrl);
    } catch (Exception e) {
      throw new IllegalStateException("Unable to create dynamic classpath-entry with id: "+id, e);
    }
  }
}
