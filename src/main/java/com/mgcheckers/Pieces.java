package com.mgcheckers;

public enum Pieces {
    ORANGE(1), BLACK(-1);

    //creates a constant variable moveDir
    final int moveDir;

    Pieces(int moveDir) {
        this.moveDir = moveDir;
    }
}
