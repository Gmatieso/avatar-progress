package com.avatarprogress.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TestProgressBarAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JDialog dialog = new JDialog((Frame) null, "Avatar Progress Diagnostic", false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JProgressBar determinate = new JProgressBar(0, 100);
        determinate.setValue(40);
        determinate.setPreferredSize(new Dimension(300, 24));
        determinate.setAlignmentX(Component.LEFT_ALIGNMENT);

        JProgressBar indeterminate = new JProgressBar();
        indeterminate.setIndeterminate(true);
        indeterminate.setPreferredSize(new Dimension(300, 24));
        indeterminate.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(new JLabel("Determinate raw JProgressBar (40%):"));
        panel.add(determinate);
        panel.add(Box.createVerticalStrut(16));
        panel.add(new JLabel("Indeterminate raw JProgressBar:"));
        panel.add(indeterminate);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        Timer timer = new Timer(500, ev -> indeterminate.repaint());
        timer.start();
    }
}
