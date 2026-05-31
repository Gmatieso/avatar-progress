package com.avatarprogress.listeners;

import com.avatarprogress.ui.AvatarProgressBarUi;
import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AvatarProgressListener  implements LafManagerListener, DynamicPluginListener {

    private static final String PROGRESS_BAR_UI_KEY = "ProgressBarUI";
    private static final String AVATAR_UI_CLASS =
            AvatarProgressBarUi.class.getName();

    private  static  Object previousUi = null;

    public AvatarProgressListener(){

        swapUi();

    }

    @Override
    public void lookAndFeelChanged(@NotNull LafManager lafManager) {
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
        if (!AVATAR_UI_CLASS.equals(current)) {
            previousUi = current;
        }

        defaults.put(AVATAR_UI_CLASS, AvatarProgressBarUi.class);
        defaults.put(PROGRESS_BAR_UI_KEY, AVATAR_UI_CLASS);
    }

    private static synchronized void restoreUi() {
        if (previousUi != null) {
            UIManager.getDefaults().put(PROGRESS_BAR_UI_KEY, previousUi);
        }
    }

}
