package com.GameLogic;

public class Player {
    private final String name;
    private final GamePiece.Color color;

    public Player(String name, GamePiece.Color color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return name;
    }

    public GamePiece.Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return name + "-" + color.toString();
    }
}
