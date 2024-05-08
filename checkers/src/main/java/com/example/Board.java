package com.example;

import java.util.Arrays;

public class Board {
    /**
     * Dimensions 1 indexed
     */
    private int x;
    private int y;

    private GamePiece[][] squares;

    /**
     * Create standard size checkers board
     */
    public Board() {
        this(8, 8);
        initialize();
        debugDisplay();
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
     * Initialize game pieces on board. Must be 8x8 and assumes empty
     */
    private void initialize() {
        if (x != 8 || y != 8) {
            throw new UnsupportedOperationException("Board must be standard 8x8 to initialize");
        }

        // Place red pawns
        for (int i = 0; i < 3; i++) {
            for (int j = (i % 2 == 0 ? 1 : 0); j < squares[i].length; j+=2) {
                this.squares[i][j] = new GamePiece(); //TODO: SET COLOR!
            }
        }

        // Place black pawns
        for (int i = 5; i < 8; i++) {
            for (int j = (i % 2 == 0 ? 1 : 0); j < squares[i].length; j+=2) {
                this.squares[i][j] = new GamePiece();
            }
        }
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
        return Arrays.deepToString(this.squares);
    }
}
