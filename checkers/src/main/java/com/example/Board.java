package com.example;

import java.util.Arrays;

public class Board {
    /**
     * Dimensions 1 indexed
     */
    private int x;
    private int y;

    private GamePiece[][] squares; // top left 0,0 y,x TODO: swap x and y or rename to row and column

    /**
     * Create standard size checkers board
     */
    public Board() {
        this(8, 8);
    }

    /**
     * Create board of given positive non-zero dimensions.
     * 
     * @param width width of board must be postive. Should be 8
     * @param height height of board must be postive. Should also be 8
     */
    public Board(int xSquares, int ySquares) {
        if (xSquares <= 0 || ySquares <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than 0");
        }

        this.x = xSquares;
        this.y = ySquares;

        this.squares = new GamePiece[x][y];
    }

    /**
     * Initialize game pieces on board. Must be 8x8.
     */
    public void initialize() {
        if (x != 8 || y != 8) {
            throw new UnsupportedOperationException("Board must be standard 8x8 to initialize");
        }

        // Clear board
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = null;
            }
        }

        // Place red pawns
        for (int i = 0; i < 3; i++) {
            for (int j = (i % 2 == 0 ? 1 : 0); j < squares[i].length; j += 2) {
                this.squares[i][j] = new GamePiece(GamePiece.Color.RED);
            }
        }

        // Place black pawns
        for (int i = 5; i < 8; i++) {
            for (int j = (i % 2 == 0 ? 1 : 0); j < squares[i].length; j += 2) {
                this.squares[i][j] = new GamePiece(GamePiece.Color.BLACK);
            }
        }
    }

    private GamePiece getSquareState(int x, int y) {
        if (squares[x][y] == null) {
            return null;
        }
        return new GamePiece(squares[x][y]);
    }

    public boolean move(Player p, int initX, int initY, int endX, int endY) {
        if (isValidMove(p, initX, initY, endX, endY)) {
            squares[endX][endY] = squares[initX][initY];
            squares[initX][initY] = null;
            return true;
        }
        return false;
    }

    // TODO: Incomplete. Just starting with basic rules. No kills or king movement
    /**
     * 
     * @param p Player attempting the move. Will need to match the game peice they are moving 
     * @param initX Start x position
     * @param initY Start y position
     * @param endX End x position
     * @param endY End y position
     * @return True if the move is legal and false otherwise
     * @throws IllegalArgumentException if any input value is less than zero
     */
    private boolean isValidMove(Player p, int initX, int initY, int endX, int endY) {
        if (initX < 0 || initY < 0 || endX < 0 || endY < 0) {
            throw new IllegalArgumentException("Values must be postive");
        }

        // Target position must be real and empty 
        if (endX > squares.length || endY > squares.length || (squares[endX][endY] != null)) {
            System.out.println("HELLO");
            return false;
        }

        // Piece must exist in given location and be the players color
        if ((getSquareState(initX, initY) == null) || (squares[initX][initY].getColor() != p.getColor())) {
            return false;
        }

        // End x must be +1 to either the left or right and end Y must be +1 above.
        if (!((endX == initX + 1) || (endX == initX - 1)) || (endY != initY + 1)) {
            return false;
        }

        return true;
    }

    private void debugDisplay() {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (squares[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(squares[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String val = "";
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (squares[i][j] == null) {
                    val += "- ";
                } else {
                    val += squares[i][j] + " ";
                }
            }
            val += "\n";
        }
        return val;
    }
}
