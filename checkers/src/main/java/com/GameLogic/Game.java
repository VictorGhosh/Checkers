package com.GameLogic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Board board;
    private Player p1; // Black
    private Player p2; // Red
    private Player currentPlayer;

    private Scanner scanner; // For Debugging only

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        this.board = new Board();
    }
    
    public void initialize() {
        board.initialize();
        currentPlayer = p1;
        play();
    }

    private void play() {
        while (!isOver()) {
            System.out.println(this);
            takeTurn(currentPlayer);
            switchPlayer();
        }
    }

    private void takeTurn(Player p) {
        this.scanner = new Scanner(System.in);

        int initRow = -1, initCol = -1, endRow = -1, endCol = -1;
        System.out.println(p.getName() + ", enter move (format: startRow startCol endRow endCol): ");
        try {
            initRow = scanner.nextInt();
            initCol = scanner.nextInt();
            endRow = scanner.nextInt();
            endCol = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter only integers.");
            scanner.next();
            takeTurn(p);
        }
        boolean attempt = board.move(p, initRow, initCol, endRow, endCol);
        if (attempt == false) {
            System.out.println("Invalid move");
            takeTurn(p);
        }
    }

    private void switchPlayer() {
        this.currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }

    private boolean isOver() {
        if (board.countPieces(GamePiece.Color.BLACK) == 0) {
            System.out.println("Black wins.");
            return true;
        } else if (board.countPieces(GamePiece.Color.RED) == 0) {
            System.out.println("Red wins.");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return p1 + " vs " + p2 + "\n" + board;
    }
    
    public static void main(String[] args) {
        Game game = new Game(new Player("Player 1", GamePiece.Color.BLACK),new Player("Player 2", GamePiece.Color.RED));
        game.initialize();
        game.play();
    }
}
