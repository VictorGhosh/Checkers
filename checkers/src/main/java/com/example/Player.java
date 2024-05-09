package com.example;

public class Player {
    private String name;
    private GamePiece.Color color; //FIXME: Should Color enum be seperate from GamePiece?

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

    /**
     * String debug is player name + their color
     */
    @Override
    public String toString() {
        return name + "-" + color.toString();
    }
}
