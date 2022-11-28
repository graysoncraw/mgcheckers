package com.mgcheckers;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import static com.mgcheckers.App.tSize;

public class Piece extends StackPane {

    private Pieces type;
    private double oldX, oldY;
    private Piece piece;
    private double mouseX, mouseY;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
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

        // Ellipse eOrange = new Ellipse(tSize * 0.3, tSize * 0.3);
        // Ellipse eBlack = new Ellipse(tSize * 0.3, tSize * 0.3);
        Ellipse ec = new Ellipse(tSize * 0.3, tSize * 0.3);


        // if (type == Pieces.ORANGE){
        //     eOrange.setFill(Color.valueOf("#CC5500"));
        //     eOrange.setStroke(Color.BLACK);
        //     eOrange.setStrokeWidth(tSize * 0.035);
        //     eOrange.setTranslateX((tSize - tSize * 0.3 * 2) / 2);
        //     eOrange.setTranslateY((tSize - tSize * 0.3 * 2) / 2);
        // }
        // else if (type == Pieces.BLACK){
        //     eBlack.setFill(Color.valueOf("#000000"));
        //     eBlack.setStroke(Color.ORANGE);
        //     eBlack.setStrokeWidth(tSize * 0.035);
        //     eBlack.setTranslateX((tSize - tSize * 0.3 * 2) / 2);
        //     eBlack.setTranslateY((tSize - tSize * 0.3 * 2) / 2);
        // }
        
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
