package com.GameLogic;

public class GamePiece {
    public enum Color {
        RED, BLACK;

        /**
         * String will be either R or B for readability
         */
        @Override
        public String toString() {
            return this.name().charAt(0) + "";
        }
    }

    protected Color color;

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
