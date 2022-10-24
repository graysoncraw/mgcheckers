package com.mgcheckers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public Tile(boolean light, int x, int y) {
        setWidth(App.TILE_SIZE);
        setHeight(App.TILE_SIZE);

        relocate(x * App.TILE_SIZE, y * App.TILE_SIZE);

        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
}
}
    