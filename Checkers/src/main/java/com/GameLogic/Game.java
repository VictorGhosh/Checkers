package com.GameLogic;

public class Game {
    private Board board;
    private final Player p1; // Black
    private final Player p2; // Red
    private Player currentPlayer;

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        this.board = new Board();
    }

    public Game(String p1Name, String p2Name) {
        this(new Player(p1Name, GamePiece.Color.BLACK), new Player(p2Name, GamePiece.Color.RED));
    }
    
    public void initialize() {
        board.initialize();
        currentPlayer = p1;
    }

    public boolean takeTurn(int initRow, int initCol, int endRow, int endCol) {
        System.out.println("From Game: " + initRow + ", " + initCol + " -> " + endRow + ", " + endCol);
        boolean wasMoved = board.move(currentPlayer, initRow, initCol, endRow, endCol);
        if (wasMoved) {
            switchPlayer();
        }
        return wasMoved;
    }

    private void switchPlayer() {
        this.currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }

    public boolean isOver() {
        if (board.countPieces(GamePiece.Color.BLACK) == 0) {
            System.out.println("Black wins.");
            return true;
        } else if (board.countPieces(GamePiece.Color.RED) == 0) {
            System.out.println("Red wins.");
            return true;
        }
        return false;
    }

    public GamePiece[][] getBoard() {
        return board.getBoard();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player[] getPlayers() {
        return new Player[]{p1, p2};
    }

    public int countPieces(GamePiece.Color color) {
        return board.countPieces(color);
    }

    @Override
    public String toString() {
        return p1 + " vs " + p2 + "\n" + board;
    }
}
