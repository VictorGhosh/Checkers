package com.Controller;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;

import com.GameLogic.Game;
import com.GameLogic.GamePiece; // FIXME: This should not be here
import com.GameLogic.King;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final int scale = 100;

    // GUI Elements
    private BorderPane root;
    private Pane boardPane;
    private Label turnLabel;
    private Label p1ScoreLabel;
    private Label p2ScoreLabel;
    private Rectangle activeSquare; // For yellow highlighting
    private String p1Name; // FIXME: Remove these and ask Game instead
    private String p2Name;

    private Game game;
    private int initRow = -1;
    private int initCol = -1;

    @Override
    public void start(Stage primaryStage) {
        this.root = new BorderPane();
        this.boardPane = new Pane();
        this.p1Name = "Player 1";
        this.p2Name = "Player 2";
        root.setLeft(boardPane);

        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);

        initializeBoard();
        initializeSidebar();
        updateBoard();
        updateSidebar();

        primaryStage.show();
    }

    /**
     * Initalizes new gameboard using attribute player names. Creates game object and board
     * including each square and sets action for when squares are clicked. 
     */
    public void initializeBoard() {
        this.game = new Game(p1Name, p2Name);
        game.initialize();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(scale * j, scale * i, scale, scale);
                square.setFill((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                boardPane.getChildren().add(square);

                square.setOnMouseClicked(event -> onSquareClick(event, square));
            }
        }
    }

    /**
     * Initialize sidebar layout. Make design changes here
     */
    private void initializeSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");

        turnLabel = new Label();
        turnLabel.getStyleClass().add("turn-label");
        p1ScoreLabel = new Label();
        p1ScoreLabel.getStyleClass().add("score-label");
        p2ScoreLabel = new Label();
        p2ScoreLabel.getStyleClass().add("score-label");

        sidebar.getChildren().addAll(turnLabel, p1ScoreLabel, p2ScoreLabel);
        root.setRight(sidebar);
    }
    
    /**
     * Update game peice positions on board including kings. Should be run after every move or game state
     * change. TODO: Currently incomplete as it redraws ALL pieces every time, which is not ideal.
     */
    private void updateBoard() {
        GamePiece[][] board = game.getBoard();
        root.getChildren().removeIf(node -> node instanceof Circle);
        root.getChildren().removeIf(node -> node instanceof ImageView);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GamePiece gamePiece = board[i][j];

                if (gamePiece != null) {
                    Circle piece = new Circle((scale * j + scale / 2), (scale * i + scale / 2), (scale / 2 - 10));
                    piece.setFill(gamePiece.getColor() == GamePiece.Color.BLACK ? Color.BLACK : Color.RED);
                    root.getChildren().add(piece);

                    if (gamePiece instanceof King) {
                        Image crownImage = new Image("/crown.png");
                        ImageView crown = new ImageView(crownImage);
                        crown.setFitHeight(60);
                        crown.setFitWidth(60);
                        crown.setX(scale * j + scale / 2 - crown.getFitWidth() / 2);
                        crown.setY(scale * i + scale / 2 - crown.getFitHeight() / 2 - 2);
                        root.getChildren().add(crown);
                    }
                    piece.setOnMouseClicked(event -> onCircleClick(event));
                }
            }
        }
    }
    
    /**
     * Update sidebar values from game object. 
     */
    private void updateSidebar() {
        turnLabel.setText(game.getCurrentPlayer().getName() + "'s Turn");

        p1ScoreLabel.setText(game.getPlayers()[0].getName() + ": "
                + game.countPieces(game.getPlayers()[0].getColor()) + " pieces");
        p2ScoreLabel.setText(game.getPlayers()[1].getName() + ": "
                + game.countPieces(game.getPlayers()[1].getColor()) + " pieces");
    }
    
    /**
     * When a square is clicked first checks if it is the start or end position for the move.
     * If start, highlights the square, if end execute the move and reset. Calls for updates.
     * 
     * @param event mouseclick event to find piece posisiton
     * @param square the square that was clicked on
     */
    private void onSquareClick(javafx.scene.input.MouseEvent event, Rectangle square) {
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
            updateSidebar();
        }
    }
    
    /**
     * When a circle is clicked find the square under it and act like that was clicked instead.
     * 
     * @param event the mouse click event for finding the square
     */
    private void onCircleClick(javafx.scene.input.MouseEvent event) {
        onSquareClick(event, getSquareUnder(event.getSceneX(), event.getSceneY()));
    }
    
    /**
     * Returns the square located under x,y for when the circle is clicked instead. 
     * 
     * @param x coordinate of the target square/location
     * @param y coordinate of the target square/location
     * @return Rectangle object of square under postion x,y or null if no square is found.
     */
    private Rectangle getSquareUnder(double x, double y) {
        int row = (int) x / 100;
        int col = (int) y / 100;
        for (Node node : boardPane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rect = (Rectangle) node;
                if (rect.getX() == row * 100 && rect.getY() == col * 100) {
                    rect.setStroke(Color.GOLD);
                    rect.setStrokeWidth(5);
                    return rect;
                }
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        launch();
    }

}