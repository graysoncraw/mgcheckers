package com.mgcheckers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;


import java.sql.*;

public class App extends Application {

    private Button resetButton;
    private Button loginButton;
    private Label blackCounter;
    private Label orangeCounter;
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField blackVal;
    private TextField orangeVal;
    private TextField usernameField;
    private TextField passwordField;
    private Label victoryLabel;
    public int blackPoint = 0;
    public int orangePoint = 0;
    public boolean successcheck = false;

    //initializing tile size, and number of tiles vertically and horizontally
    public static final int tSize = 80;
    public static final int width = 8;
    public static final int height = 8;

    //creates a board with the number of tiles vertically and horizontally
    private Tile[][] board = new Tile[width][height];

    //creates a javaFX group for the tiles
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();


    private Parent createWindow() {
        BorderPane border = new BorderPane();
        border.setCenter(addGridPane());
        border.setRight(addVBox());
        border.setPrefSize(790, 640);
        border.getChildren().addAll(tileGroup, pieceGroup);
        return border;
    }
        //a nested for loop to create a Tile object
        //this will create the board
        public GridPane addGridPane() {
            GridPane grid = new GridPane();
            grid.setHgap(0);
            grid.setVgap(0);
            grid.setPadding(new Insets(0, 0, 0, 0));
        
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                Tile tile = new Tile((w + h) % 2 == 0, w, h);
                board[w][h] = tile;
                tileGroup.getChildren().add(tile);
        
            }
        }
        return grid;
    }

    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0));
        vbox.setSpacing(4);
        blackCounter = new Label("Black Score:");
        blackVal = new TextField();
        blackVal.setEditable(false);
        blackVal.setText("0");
        orangeCounter = new Label("Orange Score:");
        orangeVal = new TextField();
        orangeVal.setEditable(false);
        orangeVal.setText("0");

        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setEditable(true);
        passwordLabel = new Label("Password:");
        passwordField = new TextField();
        passwordField.setEditable(true);
        loginButton = new Button("Login");
        resetButton = new Button("Reset Game");
        victoryLabel = new Label("");
        VBox.setMargin(usernameLabel, new Insets(350, 0, 0, 0));

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String userName = usernameField.getText();
                String password = passwordField.getText();
                String dbUserName;
                String dbPassword;
                try {
                    String url = "jdbc:mysql://localhost:3306/logininfo";
                    String uname = "root";
                    String pword = "Hsjmieemkgiea612&";
                    Connection conn = DriverManager.getConnection(url, uname, pword);
                    Statement stmt = conn.createStatement();
                    //selects all usernames and passwords
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PASSWORDS;");
                //will retrieve usernames and passwords by column one at a time, and 
                //check to see if they are equal with what the user has entered
                while (rs.next()) {
                    dbUserName = rs.getString(1);
                    dbPassword = rs.getString(2);
                    if (userName.equals(dbUserName)){
                        if (password.equals(dbPassword)){
                            successcheck = true;
                            System.out.println("success");
                            for (int h = 0; h < 8; h++){
                                for (int w = 0; w < 8; w++){
                                    Piece piece = null;

                                    if (h <= 2 && (w + h) % 2 != 0) {
                                        piece = makePiece(TypeOfPieces.BLACK, w, h);
                                    }
    
                                    if (h >= 5 && (w + h) % 2 != 0) {
                                        piece = makePiece(TypeOfPieces.ORANGE, w, h);
                                    }
    
                                    if (piece != null) {
                                        board[w][h].setPiece(piece);
                                        pieceGroup.getChildren().add(piece);
                                    }
                                    usernameLabel.setVisible(false);
                                    usernameField.setVisible(false);
                                    passwordField.setVisible(false);
                                    passwordLabel.setVisible(false);
                                    loginButton.setVisible(false);

                                }
                            }

                        }
                    }
                    if(successcheck == false){
                        System.out.println("fail");
                    }
                }
                conn.close();
                stmt.close();
                
                } catch (SQLException sqlexc) {
                sqlexc.printStackTrace();
                } 
            }
        });
        

        resetButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
                if(successcheck == true){
                blackVal.setText("0");
                blackPoint = 0;
                orangeVal.setText("0");
                orangePoint = 0;
                victoryLabel.setText("");
				for(int x=0; x<8; x++){
                    for(int y=0; y<8; y++){
                        if(board[x][y].hasPiece()){
                            Piece piece = board[x][y].getPiece();
                            board[x][y].setPiece(null);
                            pieceGroup.getChildren().remove(piece);
                        }
                    }
                }
                for(int h = 0; h<8; h++){
                    for(int w=0;w<8;w++){
                        Piece piece = null;

                if (h <= 2 && (w + h) % 2 != 0) {
                    piece = makePiece(TypeOfPieces.BLACK, w, h);
                }

                if (h >= 5 && (w + h) % 2 != 0) {
                    piece = makePiece(TypeOfPieces.ORANGE, w, h);
                }

                if (piece != null) {
                    board[w][h].setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
                    }
                }
			}
        }
		});
        vbox.getChildren().addAll(resetButton, blackCounter, blackVal, orangeCounter, orangeVal, 
        victoryLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        return vbox;

    }

    private ResultOfMove tryMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new ResultOfMove(TypeOfMove.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir1 || Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir2) {
            return new ResultOfMove(TypeOfMove.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir1 * 2 || Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir2 * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && ((board[x1][y1].getPiece().getType() == TypeOfPieces.ORANGE || board[x1][y1].getPiece().getType() == TypeOfPieces.CROWNORANGE) && (piece.getType() == TypeOfPieces.BLACK || piece.getType() == TypeOfPieces.CROWNBLACK)) || 
            ((board[x1][y1].getPiece().getType() == TypeOfPieces.BLACK || board[x1][y1].getPiece().getType() == TypeOfPieces.CROWNBLACK) && (piece.getType() == TypeOfPieces.ORANGE || piece.getType() == TypeOfPieces.CROWNORANGE))) {
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

    private Piece makePiece(TypeOfPieces type, int x, int y) {
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
                    if(piece.getType() == TypeOfPieces.BLACK){
                        if(newY == 7){
                            piece.CrownBlack();
                            piece.ec.setFill(Color.valueOf("#808080"));
                        }
                    }
                    else if(piece.getType() == TypeOfPieces.ORANGE){
                        if(newY == 0){
                            piece.CrownOrange();
                            piece.ec.setFill(Color.valueOf("#FF0000"));

                        }
                    }
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case HOP:
                    if(type == TypeOfPieces.BLACK){
                        if(newY == 7){
                            piece.CrownBlack();
                            piece.ec.setFill(Color.valueOf("#808080"));
                        }
                        blackPoint++;
                        blackVal.setText(Integer.toString(blackPoint));
                        if(blackPoint == 12){
                            victoryLabel.setText("Black Player has won!");}
                    }
                    else if(type == TypeOfPieces.ORANGE){
                        if(newY == 0){
                            piece.CrownOrange();
                            piece.ec.setFill(Color.valueOf("#FF0000"));
                        }
                        
                        orangePoint++;
                        orangeVal.setText(Integer.toString(orangePoint));
                        if(orangePoint == 12){
                            victoryLabel.setText("Orange player has won!");
                        }
                    }
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