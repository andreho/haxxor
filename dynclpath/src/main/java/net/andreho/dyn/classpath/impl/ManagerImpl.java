package net.andreho.dyn.classpath.impl;

import net.andreho.dyn.classpath.Entry;
import net.andreho.dyn.classpath.EntryFactory;
import net.andreho.dyn.classpath.EntryInstaller;
import net.andreho.dyn.classpath.InstallationResult;
import net.andreho.dyn.classpath.Manager;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Collections.unmodifiableList;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 11:28.
 */
public class ManagerImpl
  implements Manager {

  private final Collection<EntryFactory> availableEntryFactories;
  private final Collection<EntryInstaller<?>> availableEntryInstallers;
  private final ConcurrentMap<String, Entry> installedEntries = new ConcurrentHashMap<>();


  public ManagerImpl() {
    this(Services.PROVIDED_INSTALLERS, Services.PROVIDED_FACTORIES);
  }

  public ManagerImpl(final List<EntryInstaller<?>> availableEntryInstallers,
                     final List<EntryFactory> availableEntryFactories) {
    this.availableEntryInstallers = unmodifiableList(availableEntryInstallers);
    this.availableEntryFactories = unmodifiableList(availableEntryFactories);
  }

  @Override
  public Collection<EntryInstaller<?>> getAvailableEntryInstallers() {
    return availableEntryInstallers;
  }

  @Override
  public Collection<EntryFactory> getAvailableEntryFactories() {
    return availableEntryFactories;
  }

  @Override
  public InstallationResult install(final String id,
                                    final ClassLoader classLoader,
                                    final URL classPathUrl) {
    for (EntryFactory factory : getAvailableEntryFactories()) {
      final Entry entry = factory.createFor(id, classLoader, classPathUrl);
      if (entry != null) {
        return installCreatedEntry(entry, classLoader);
      }
    }
    return InstallationResult.UNSUPPORTED;
  }

  protected InstallationResult installCreatedEntry(final Entry entry,
                                                   final ClassLoader classLoader) {
    final InstallationResult result = proceedInstallation(classLoader, entry);

    if (result == InstallationResult.SUCCESSFUL) {
      this.installedEntries.merge(
        entry.getId(), entry, (current, newEntry) -> newEntry.merge(current)
      );
    }
    return result;
  }

  protected InstallationResult proceedInstallation(final ClassLoader classLoader,
                                                   final Entry entry) {
    for (EntryInstaller entryInstaller : getAvailableEntryInstallers()) {
      if (entryInstaller.isCompatibleWith(classLoader)) {
        return entryInstaller.installOn(classLoader, entry);
      }
    }
    return InstallationResult.UNSUPPORTED;
  }

  @Override
  public Iterable<Entry> getEntries() {
    return installedEntries.values();
  }

  @Override
  public Optional<Entry> getEntryById(final String id) {
    final Entry entry = installedEntries.get(id);
    return entry == null ? Optional.empty() : Optional.of(entry);
  }
}
