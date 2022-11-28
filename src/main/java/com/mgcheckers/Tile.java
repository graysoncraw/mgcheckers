package com.mgcheckers;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Tile extends Rectangle {
    
    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public Tile(boolean dark, int w, int h) {
        
        //setWidth, setHeight, and relcate, and setFill are all part of javaFX
        setWidth(App.tSize);
        setHeight(App.tSize);

        relocate(w * App.tSize, h * App.tSize);
        
        //short for if mod 2 (dark), make tile black, otherwise make it the other color
        setFill(dark ? Color.valueOf("#000000") : Color.valueOf("#CC5500"));
    }
}
