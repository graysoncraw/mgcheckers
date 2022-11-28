package com.mgcheckers;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import static com.mgcheckers.App.tSize;

public class Piece extends StackPane {

    private Pieces type;

    private double oldX, oldY;
    private double mouseX, mouseY;


    //
    public Pieces getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(Pieces type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse ec = new Ellipse(tSize * 0.3, tSize * 0.3);
        
        ec.setFill(type == Pieces.ORANGE
                ? Color.valueOf("#CC5500") : Color.valueOf("#000000"));

        ec.setStroke(Color.ORANGE);
        ec.setStrokeWidth(tSize * 0.03);
        ec.setTranslateX((tSize - tSize * 0.3 * 2) / 2);
        ec.setTranslateY((tSize - tSize * 0.3 * 2) / 2);

        getChildren().addAll(ec);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });

    }
    public void move(int x, int y) {
        oldX = x * tSize;
        oldY = y * tSize;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
