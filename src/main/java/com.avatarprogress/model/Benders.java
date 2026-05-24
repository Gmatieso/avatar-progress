package com.avatarprogress.model;

import java.awt.*;
import java.util.ArrayList;

public class Benders {
    public static final Bender AANG = new Bender(
            "Aang",
            Element.AIR,
            "/sprite/aang/walk.gif",
            new Color(0xE65100),
            new Color(0xFFCC80)
    );


    public static final Bender KATARA = new Bender(
            "Katara",
            Element.WATER,
            "/sprite/katara/walk.gif",
            new Color(0x1565C0),
            new Color(0x90CAF9)
    );

    public static  final Bender TOPH = new Bender(
            "Toph",
            Element.EARTH,
                "/sprite/toph/walk.gif",
            new Color(0x2E7D32),
            new Color(0xA5D6A7)
    );

    public static final Bender ZUKO  = new Bender(
            "ZUKO",
            Element.FIRE,
            "/sprite/zuko/walk.gif",
            new Color(0xB71C1C),
            new Color(0xFF8A65)
    );

    public static final List<Bender> ALL = new ArrayList<>();


    }

