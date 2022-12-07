package com.mgcheckers;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import static com.mgcheckers.App.tSize;

public class Piece extends StackPane {

    private TypeOfPieces type;

    private double oldX, oldY;
    private double mouseX, mouseY;

    Ellipse ec = null;

    //
    public TypeOfPieces CrownBlack() {
        this.type = TypeOfPieces.CROWNBLACK;
        return type;
    }

    public TypeOfPieces CrownOrange(){
        this.type = TypeOfPieces.CROWNORANGE;
        return type;
    }

    public TypeOfPieces getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(TypeOfPieces type, int x, int y) {
        this.type = type;

        move(x, y);

        ec = new Ellipse(tSize * 0.3, tSize * 0.3);
        
        if (type == TypeOfPieces.ORANGE){
            ec.setFill(Color.valueOf("#FFD700"));
        }
        else if(type == TypeOfPieces.BLACK){
            ec.setFill(Color.valueOf("#000000"));
        }

        ec.setStroke(Color.BLACK);
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
