package com.GameLogic;

/**
 * King class represents a game piece that has been kinged. Would likely be
 * better to just use a flag in the GamePiece class but I went with this.
 */
public class King extends GamePiece {
    public King(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString() + "K";
    }
}
