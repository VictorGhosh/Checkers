package com.example;

public class Board {
    
    /**
     * Dimensions 1 indexed
     */
    private int x;
    private int y;

    /**
     * Create standard size checkers board
     */
    public Board() {
        this(8, 8);
    }

    /**
     * Create board of given dimensions
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
    }
}
