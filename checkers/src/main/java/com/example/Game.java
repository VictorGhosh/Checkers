package com.example;

public class Game {
    private Board board;
    private Player p1; // Black
    private Player p2; // Red
    private Player currentPlayer;

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        this.board = new Board();
    }
    
    public void initialize() {
        board.initialize();
        currentPlayer = p1;
        // play();
    }

    private void play() {
        while (!isOver()) {
            // takeTurn(currentPlayer);
            switchPlayer();
        }
    }

    private void takeTurn() {
        System.out.println(board.move(p1, 5, 2, 4, 3));
        System.out.println(board.move(p2, 2, 5, 3, 4));
        System.out.println(board.move(p2, 3, 4, 5, 2));
        System.out.println();
    }

    private void switchPlayer() {
        this.currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }

    private boolean isOver() {
        return false;
    }

    @Override
    public String toString() {
        return p1 + " vs " + p2 + "\n" + board;
    }

    public static void main(String[] args) {
        Game game = new Game(new Player("Player 1", GamePiece.Color.BLACK),new Player("Player 2", GamePiece.Color.RED));
        game.initialize();
        System.out.println(game);
        game.takeTurn();
        System.out.println(game);
    }
}
