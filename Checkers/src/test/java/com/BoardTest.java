package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.GameLogic.*;

public class BoardTest {
    @Test
    public void initalSetup() {
        Board board = new Board();
        board.initialize();

        // Black
        assertNotNull(board.getSquare(7, 0));
        assertTrue(board.getSquare(7, 0) instanceof GamePiece);
        assertEquals(board.getSquare(7, 0).getColor(), GamePiece.Color.BLACK);

        // Red
        assertNotNull(board.getSquare(0, 7));
        assertTrue(board.getSquare(0, 7) instanceof GamePiece);
        assertEquals(board.getSquare(0, 7).getColor(), GamePiece.Color.RED);

        // Middle
        assertNull(board.getSquare(3, 3));
    }
    
    @Test
    public void movement() {
        Board board = new Board();
        Player p1 = new Player("p1", GamePiece.Color.BLACK);
        Player p2 = new Player("p2", GamePiece.Color.RED);
        board.initialize();

        // Black left and right
        assertTrue(board.move(p1, 5, 2, 4, 3));
        assertTrue(board.move(p1, 4, 3, 3, 2));

        // Red left and right
        assertTrue(board.move(p2, 2, 5, 3, 4));
        assertTrue(board.move(p2, 3, 4, 4, 5));

        // Black no backwards left or right
        assertFalse(board.move(p1, 3, 2, 4, 1));
        assertFalse(board.move(p1, 3, 2, 4, 3));

        // Red no backwards left or right
        assertFalse(board.move(p1, 4, 5, 3, 4));
        assertFalse(board.move(p1, 4, 5, 3, 6));
    }
}
