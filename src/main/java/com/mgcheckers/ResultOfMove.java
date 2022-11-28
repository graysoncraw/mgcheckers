package com.mgcheckers;

public class ResultOfMove {
    private TypeOfMove type;

    public TypeOfMove getType() {
        return type;
    }

    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public ResultOfMove(TypeOfMove type) {
        this(type, null);
    }

    public ResultOfMove(TypeOfMove type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }
}
