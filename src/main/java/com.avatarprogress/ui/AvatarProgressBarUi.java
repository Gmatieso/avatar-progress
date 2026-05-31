package com.avatarprogress.ui;

import com.avatarprogress.model.Bender;
import com.avatarprogress.model.Benders;
import com.avatarprogress.settings.AvatarProgressState;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AvatarProgressBarUi extends BasicProgressBarUI {
    private final Bender bender;

    public AvatarProgressBarUi() {
        AvatarProgressState settings = AvatarProgressState.getInstance();
        List<Bender> enabled = Benders.ALL.stream()
                .filter(b -> settings.isBenderEnabled(b.getName()))
                .collect(Collectors.toList());

        this.bender = Benders.random(enabled);
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c){
        AvatarProgressState settings = AvatarProgressState.getInstance();
        if (!settings.isEnabled()) {
            super.paintDeterminate(g, c);
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        try {
            Insets insets = progressBar.getInsets();
            int barW = progressBar.getWidth()  - insets.left - insets.right;
            int barH = progressBar.getHeight() - insets.top  - insets.bottom;

            if (barW <= 0 || barH <= 0) return;

            double progress   = progressBar.getPercentComplete();
            int    fillWidth  = (int)(barW * progress);

            // paint background
            g2.setColor(bender.getSecondaryColor());
            g2.fillRect(insets.left, insets.top, barW, barH);

            // paint fill
            if (fillWidth > 0) {
                g2.setColor(bender.getPrimaryColor());
                g2.fillRect(insets.left, insets.top, fillWidth, barH);
            }

            // paint sprite at leading edge
            if (settings.isDrawSprites() && bender.getIcon() != null) {
                paintSprite(g2, insets.left + fillWidth, insets.top, barH);
            }

            // tooltip
            if (settings.isShowTooltips()) {
                progressBar.setToolTipText(bender.getTooltip());
            }

        } finally {
            g2.dispose();
        }
    }
}
