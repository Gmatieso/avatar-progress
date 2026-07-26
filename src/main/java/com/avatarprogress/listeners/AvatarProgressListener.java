package com.avatarprogress.listeners;

import com.avatarprogress.ui.AvatarProgressBarUi;
import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AvatarProgressListener
        implements LafManagerListener, DynamicPluginListener, StartupActivity {

    private static final String PROGRESS_BAR_UI_KEY = "ProgressBarUI";

    private  static  Object previousUi = null;


    public AvatarProgressListener(){

        swapUi();

    }

    @Override
    public void lookAndFeelChanged(@NotNull LafManager lafManager) {
        swapUi();
    }

    @Override
    public void runActivity(@NotNull Project project) {
        swapUi();
    }


    @Override
    public void pluginLoaded(@NotNull IdeaPluginDescriptor pluginDescriptor) {
        swapUi();
    }

    @Override
    public void beforePluginUnload(@NotNull IdeaPluginDescriptor pluginDescriptor, boolean isUpdate) {

        restoreUi();

    }

    private static synchronized void swapUi() {
        UIDefaults defaults = UIManager.getDefaults();
        Object current = defaults.get(PROGRESS_BAR_UI_KEY);
        if (!AvatarProgressBarUi.class.equals(current)) {
            previousUi = current;
        }

        // Register the Class directly (not its name as a String): Swing resolves
        // String UI class names via Class.forName() using an ambient classloader
        // that can't see into this plugin's isolated PluginClassLoader, which
        // would silently fail and fall back to the default UI.
        defaults.put(PROGRESS_BAR_UI_KEY, AvatarProgressBarUi.class);
    }

    private static synchronized void restoreUi() {
        if (previousUi != null) {
            UIManager.getDefaults().put(PROGRESS_BAR_UI_KEY, previousUi);
        }
    }

}
