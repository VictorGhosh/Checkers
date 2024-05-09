package com.example;

public class King extends GamePiece {
    public King(Color color) {
        super(color);
    }

    /**
     * String is BK or RK for black or red king.
     */
    @Override
    public String toString() {
        return super.color.toString().charAt(0) + "K";
    }
}
