package com.GameLogic;

/**
 * All game pieces. Kings are inhereted from this/
 */
public class GamePiece {
    public static enum Color {
        RED, BLACK;

        @Override
        public String toString() {
            return this.name().charAt(0) + "";
        }
    }

    // Accessible for Kings class 
    protected final Color color;

    public GamePiece(Color color) {
        this.color = color;
    }

    public GamePiece(GamePiece piece) {
        this.color = piece.getColor();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
