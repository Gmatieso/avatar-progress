package com.avatarprogress.settings;
import com.avatarprogress.model.Bender;
import com.avatarprogress.model.Benders;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AvatarProgressConfigurable implements Configurable {

    private JCheckBox enabledBox;
    private JCheckBox drawSpritesBox;
    private JCheckBox showTooltipsBox;
    private JSlider speedSlider;
    private Map<String, JCheckBox> benderBoxes;
    private JPanel panel;


    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Avatar Progress";
    }

    @Override
    public @Nullable JComponent createComponent() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // master toggle
        enabledBox = new JCheckBox("Enable Avatar Progress");
        enabledBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(enabledBox);
        panel.add(Box.createVerticalStrut(8));

        // sprite toggle
        drawSpritesBox = new JCheckBox("Draw bender sprites");
        drawSpritesBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(drawSpritesBox);

        // tooltip toggle
        showTooltipsBox = new JCheckBox("Show bender name as tooltip");
        showTooltipsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(showTooltipsBox);
        panel.add(Box.createVerticalStrut(12));

        // speed slider
        JLabel speedLabel = new JLabel("Animation speed:");
        speedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(speedLabel);

        speedSlider = new JSlider(1, 15, 7);
        speedSlider.setMajorTickSpacing(7);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        speedSlider.setMaximumSize(new Dimension(300, 50));
        panel.add(speedSlider);
        panel.add(Box.createVerticalStrut(12));

        // per bender checkboxes
        JLabel bendersLabel = new JLabel("Enabled benders:");
        bendersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(bendersLabel);
        panel.add(Box.createVerticalStrut(4));

        benderBoxes = new HashMap<>();
        for (Bender bender : Benders.ALL) {
            JCheckBox cb = new JCheckBox(bender.getName()
                    + " (" + bender.getElement().name().toLowerCase() + ")");
            cb.setAlignmentX(Component.LEFT_ALIGNMENT);
            benderBoxes.put(bender.getName(), cb);
            panel.add(cb);
        }

        panel.add(Box.createVerticalStrut(12));

        // select all / deselect all buttons
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton selectAll = new JButton("Select all");
        JButton deselectAll = new JButton("Deselect all");

        selectAll.addActionListener(
                e -> benderBoxes.values().forEach(cb -> cb.setSelected(true))
        );
        deselectAll.addActionListener(
                e -> benderBoxes.values().forEach(cb -> cb.setSelected(false))
        );

        buttonRow.add(selectAll);
        buttonRow.add(Box.createHorizontalStrut(8));
        buttonRow.add(deselectAll);
        panel.add(buttonRow);

        reset();
        return panel;
    }


    @Override
    public boolean isModified() {
        AvatarProgressState s = AvatarProgressState.getInstance();
        if (enabledBox.isSelected()      != s.isEnabled())      return true;
        if (drawSpritesBox.isSelected()  != s.isDrawSprites())  return true;
        if (showTooltipsBox.isSelected() != s.isShowTooltips()) return true;
        if (speedSlider.getValue()       != s.getSpeed())       return true;
        for (Bender b : Benders.ALL) {
            if (benderBoxes.get(b.getName()).isSelected()
                    != s.isBenderEnabled(b.getName())) return true;
        }

        return false;
    }

    @Override
    public void apply() {
        AvatarProgressState.State state = AvatarProgressState.getInstance().getState();
        if (state == null) return;

        state.enabled      = enabledBox.isSelected();
        state.drawSprites  = drawSpritesBox.isSelected();
        state.showTooltips = showTooltipsBox.isSelected();
        state.speed        = speedSlider.getValue();

        state.enabledBenders.clear();
        for (Bender b : Benders.ALL) {
            if (benderBoxes.get(b.getName()).isSelected()) {
                state.enabledBenders.add(b.getName());
            }
        }
    }

    @Override
    public void reset() {
        AvatarProgressState s = AvatarProgressState.getInstance();
        enabledBox.setSelected(s.isEnabled());
        drawSpritesBox.setSelected(s.isDrawSprites());
        showTooltipsBox.setSelected(s.isShowTooltips());
        speedSlider.setValue(s.getSpeed());
        for (Bender b : Benders.ALL) {
            JCheckBox cb = benderBoxes.get(b.getName());
            if (cb != null) cb.setSelected(s.isBenderEnabled(b.getName()));
        }
    }
}
