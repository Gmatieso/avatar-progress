package com.avatarprogress.model;

import javax.swing.*;
import java.awt.*;

public class Bender {

    private  final String name;
    private  final Element element;
    private final String spritePath;
    private final Color primaryColor;
    private final Color secondaryColor;
    private  Icon cachedIcon;

    public Bender(String name, Element element, String spritePath, Color primaryColor, Color secondaryColor) {
        this.name = name;
        this.element = element;
        this.spritePath = spritePath;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public String getName() {return name;}
    public Element getElement() {return element;}
    public Color getPrimaryColor() {return primaryColor;}
    public Color getSecondaryColor() {return secondaryColor;}


    public Icon getIcon() {
        if(cachedIcon == null) {
            java.net.URL url = Bender.class.getResource(spritePath);
            if(url != null) {
                cachedIcon = new ImageIcon(url);
            }
        }
        return cachedIcon;
    }

    public String getTooltip(){
        return  name + " - " + element.name().toLowerCase() + "bender";
    }
}
