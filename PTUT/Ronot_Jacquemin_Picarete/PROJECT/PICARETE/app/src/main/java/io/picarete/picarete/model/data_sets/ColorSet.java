package io.picarete.picarete.model.data_sets;

import android.graphics.Color;

import io.picarete.picarete.model.container.ColorCustom;

/**
 * Created by root on 1/9/15.
 */
public class ColorSet {
    public static ColorCustom colorTileBgGeneral = new ColorCustom("#6f7e92");

    public static ColorCustom colorTileBg = new ColorCustom("#fdf8cd");
    public static ColorCustom colorTileBgPlayer1 = new ColorCustom();
    public static ColorCustom colorTileBgPlayer2 = new ColorCustom();
    public static ColorCustom colorTileBgPlayerNone = new ColorCustom("#898A84");

    public static ColorCustom colorEdgePlayer1 = new ColorCustom();
    public static ColorCustom colorEdgePlayer2 = new ColorCustom();
    public static ColorCustom colorEdgePlayerNone = new ColorCustom("#898A84");

    public static final ColorCustom DEBUG = new ColorCustom(Color.GREEN);
}
