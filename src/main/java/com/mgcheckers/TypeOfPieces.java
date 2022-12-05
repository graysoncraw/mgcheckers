package com.mgcheckers;

public enum TypeOfPieces {
    ORANGE(-1, 0), BLACK(1, 0), CROWNORANGE(-1, 1), CROWNBLACK(1, -1);

    //creates a constant variable moveDir1
    final int moveDir1;
    final int moveDir2;

    TypeOfPieces(int moveDir1, int moveDir2) {
        this.moveDir1 = moveDir1;
        this.moveDir2 = moveDir2;
    }
}
