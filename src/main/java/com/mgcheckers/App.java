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

        return piece;
    }
            

    public static void main(String[] args) {
        launch();
    }

}