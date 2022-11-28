package com.mgcheckers;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

//import java.io.IOException;

public class App extends Application {

    //initializing tile size, and number of tiles vertically and horizontally
    public static final int tSize = 100;
    public static final int width = 8;
    public static final int height = 8;

    //creates a board with the number of tiles vertically and horizontally
    private Tile[][] board = new Tile[width][height];

    //creates a javaFX group for the tiles
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();


    private Parent createWindow() {
        Pane root = new Pane();
        root.setPrefSize(width * tSize, height * tSize);
        root.getChildren().addAll(tileGroup, pieceGroup);

        //a nested for loop to create a Tile object
        //this will create the board
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                Tile tile = new Tile((w + h) % 2 == 0, w, h);
                board[w][h] = tile;
                tileGroup.getChildren().add(tile);
            
            Piece piece = null;

                if (h <= 2 && (w + h) % 2 != 0) {
                    piece = makePiece(Pieces.BLACK, w, h);
                }

                if (h >= 5 && (w + h) % 2 != 0) {
                    piece = makePiece(Pieces.ORANGE, w, h);
                }

                if (piece != null) {
                    piece.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    private ResultOfMove tryMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new ResultOfMove(TypeOfMove.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
            return new ResultOfMove(TypeOfMove.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new ResultOfMove(TypeOfMove.HOP, board[x1][y1].getPiece());
            }
        }

        return new ResultOfMove(TypeOfMove.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + tSize / 2) / tSize;
    }
            
    //uses what was just created to fill it into a scene/window
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createWindow());
        primaryStage.setTitle("MG Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(Pieces type, int x, int y) {
        Piece piece = new Piece(type, x, y);
        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            ResultOfMove result;

            if (newX < 0 || newY < 0 || newX >= width || newY >= height) {
                result = new ResultOfMove(TypeOfMove.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case HOP:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
        });
        return piece;
    }
    
            

    public static void main(String[] args) {
        launch();
    }

}