package com.GameLogic;

public class King extends GamePiece {
    public King(Color color) {
        super(color);
    }

    /**
     * String is BK or RK for black or red king.
     */
    @Override
    public String toString() {
        return super.toString() + "K";
    }
}
