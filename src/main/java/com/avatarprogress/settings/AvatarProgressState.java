package com.avatarprogress.settings;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@State(
        name = "AvatarProgressSettings",
        storages = @Storage("AvatarProgress.xml")
)

@Service
public final class AvatarProgressState implements PersistentStateComponent<AvatarProgressState.State> {


    public static class State {
        public boolean enabled = true;
        public boolean drawSprites = true;
        public boolean showTooltips = true;
        public int speed = 7;
        public Set<String> enabledBenders = new HashSet<>();
    }

    private State myState = new State();


    public static AvatarProgressState getInstance() {
        return ApplicationManager.getApplication()
                                .getService(AvatarProgressState.class);
    }


    @Override
    public @Nullable State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    public boolean isEnabled() { return myState.enabled; }
    public boolean isDrawSprites() { return myState.drawSprites; }
    public boolean isShowTooltips() { return myState.showTooltips; }
    public int getSpeed() { return myState.speed; }

    public boolean isBenderEnabled(String benderName) {
        return myState.enabledBenders.contains(benderName)
        || myState.enabledBenders.isEmpty();
    }
}
