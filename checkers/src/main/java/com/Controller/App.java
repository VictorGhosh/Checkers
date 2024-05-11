package com.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import com.GameLogic.Game;
import com.GameLogic.GamePiece; // FIXME: This should not be here
import com.GameLogic.King;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final int scale = 100;

    private Pane root;

    private Game game;
    private Rectangle activeSquare;

    private String p1Name;
    private String p2Name;

    private int initRow = -1;
    private int initCol = -1;

    @Override
    public void start(Stage primaryStage) {
        this.root = new Pane();

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("Checkers Game");
        primaryStage.setScene(scene);

        initializeBoard();
        updateBoard();

        primaryStage.show();
    }

    public void initializeBoard() {
        this.p1Name = "Player 1";
        this.p2Name = "Player 2";
        this.game = new Game(p1Name, p2Name);
        game.initialize();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(scale * j, scale * i, scale, scale);
                square.setFill((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                root.getChildren().add(square);

                square.setOnMouseClicked(event -> squareClicked(event, square));
            }
        }
    }

    /**
     * FIXME: Redrawing all game pieces every update is not good but I'm lazy
     */
    private void updateBoard() {
        GamePiece[][] board = game.getBoard();
        root.getChildren().removeIf(node -> node instanceof Circle);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GamePiece gamePiece = board[i][j];

                if (gamePiece != null) {
                    Circle piece = new Circle((scale * j + scale / 2), (scale * i + scale / 2), (scale / 2 - 10));
                    piece.setFill(gamePiece.getColor() == GamePiece.Color.BLACK ? Color.BLACK : Color.RED);
                    root.getChildren().add(piece);

                    if (gamePiece instanceof King) {
                        int pal[][] = new int[][] { { 20, 1 }, { -20, 1 }, { 1, 20 }, { 1, -20 } };
                        for (int k = 0; k < pal.length; k++) {
                            Circle palImage = new Circle((scale * j + scale / 2) + pal[k][0], (scale * i + scale / 2) + pal[k][1], 10);
                            palImage.setFill(Color.GOLD);
                            root.getChildren().add((palImage));
                        }
                        Circle base = new Circle((scale * j + scale / 2), (scale * i + scale / 2), 20);
                        base.setFill(Color.GOLD);
                        root.getChildren().add(base);

                        Circle hole = new Circle((scale * j + scale / 2), (scale * i + scale / 2), 15);
                        hole.setFill(gamePiece.getColor() == GamePiece.Color.BLACK ? Color.BLACK : Color.RED);
                        root.getChildren().add(hole);
                    }
                }
            }
        }
    }

    private void squareClicked(javafx.scene.input.MouseEvent event, Rectangle square) {
        if (initRow < 0 && initCol < 0) {
            this.initRow = (int) Math.round(event.getSceneY()) / 100;
            this.initCol = (int) Math.round(event.getSceneX()) / 100;

            this.activeSquare = square;
            square.setStroke(Color.GOLD);
            square.setStrokeWidth(5);
        } else {
            int endRow = (int) Math.round(event.getSceneY()) / 100;
            int endCol = (int) Math.round(event.getSceneX()) / 100;

            System.out.println("(" + initRow + ", " + initCol + ") -> (" + endRow + ", " + endCol
                + ") " + game.takeTurn(initRow, initCol, endRow, endCol));
            activeSquare.setStrokeWidth(0);
            this.initRow = -1;
            this.initCol = -1;
            updateBoard();
        }
    }
    
    public static void main(String[] args) {
        launch();
    }

}