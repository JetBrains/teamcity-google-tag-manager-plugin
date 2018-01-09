package org.jetbrains.teamcity;

import jetbrains.buildServer.configuration.FilesWatcher;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.impl.FileWatcherFactory;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.FileUtil;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.concurrent.ThreadSafe;
import java.io.File;
import java.io.IOException;

@ThreadSafe
public class GoogleTagManagerConfig {
    private static final String SETTINGS_FILE = "settings.xml";

    private final File myConfigDir;
    private final File mySettingsFile;
    private String gtmContainerId;

    public GoogleTagManagerConfig(@NotNull EventDispatcher<BuildServerListener> myEvents,
                                  @NotNull ServerPaths serverPaths,
                                  @NotNull FileWatcherFactory fileWatcherFactory) {
        myConfigDir = new File(serverPaths.getConfigDir(), "googleTagManager");
        mySettingsFile = new File(myConfigDir, SETTINGS_FILE);

        int watchInterval = TeamCityProperties.getInteger("teamcity.googleTagManagerPlugin.configWatchInterval", 10000);
        FilesWatcher filesWatcher = fileWatcherFactory.createManyFilesWatcher(
                () -> FileUtil.listFiles(myConfigDir, (dir, name) -> true),
                watchInterval);

        filesWatcher.registerListener((newFiles, modified, removed) -> loadSettings());
        myEvents.addListener(new BuildServerAdapter() {
            @Override
            public void serverStartup() {
                loadSettings();
                filesWatcher.start();
            }

            @Override
            public void serverShutdown() {
                filesWatcher.stop();
            }
        });
    }

    @Nullable
    public synchronized String getGtmContainerId() {
        return gtmContainerId;
    }

    private synchronized void loadSettings() {
        try {
            if (mySettingsFile.exists()) {
                gtmContainerId = FileUtil.parseDocument(mySettingsFile, false).getAttributeValue("container-id");
            }
        } catch (IOException | JDOMException e) {
            Loggers.SERVER.warnAndDebugDetails("Error while loading Google Tag Manager Plugin settings from " + FileUtil.getCanonicalFile(mySettingsFile).getPath(), e);
        }
    }

}
