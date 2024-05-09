package com.example;

public class Board {
    /**
     * Dimensions 1 indexed
     */
    private int x; //TODO: remove these they're not needed
    private int y;

    private GamePiece[][] squares; // top left 0,0 row,column
    /**
     * Create standard size checkers board
     */
    public Board() {
        this(8, 8);
    }

    // TODO: Remove this switch to only standard board
    /**
     * Create board of given positive non-zero dimensions.
     * 
     * @param width width of board must be postive. Should be 8
     * @param height height of board must be postive. Should also be 8
     */
    public Board(int xSquares, int ySquares) {
        if (xSquares <= 0 || ySquares <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than 0");
        }

        this.x = xSquares;
        this.y = ySquares;

        this.squares = new GamePiece[x][y];
    }

    /**
     * Initialize game pieces on board. Must be 8x8.
     */
    public void initialize() {
        // Clear board
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = null;
            }
        }

        // Place red pawns
        for (int i = 0; i < 3; i++) {
            for (int j = (i % 2 == 0 ? 1 : 0); j < squares[i].length; j += 2) {
                this.squares[i][j] = new GamePiece(GamePiece.Color.RED);
            }
        }

        // Place black pawns
        for (int i = 5; i < 8; i++) {
            for (int j = (i % 2 == 0 ? 1 : 0); j < squares[i].length; j += 2) {
                this.squares[i][j] = new GamePiece(GamePiece.Color.BLACK);
            }
        }
    }
    
    public boolean move(Player p, int initRow, int initCol, int endRow, int endCol) {
        // TODO: move helper call here so we can save time from rechecking
        // Is King
        if (squares[initRow][initCol] instanceof King) {
            System.out.println("HERE");
            if (isValidKingMove(p, initRow, initCol, endRow, endCol)) {
                squares[endRow][endCol] = squares[initRow][initCol];
                squares[initRow][initCol] = null;
                return true;
            } else if (isValidKingKillMove(p, initRow, initCol, endRow, endCol)) {
                // Seperated because eventually this will alow the peice to move to attack again
                squares[endRow][endCol] = squares[initRow][initCol];
                squares[initRow][initCol] = null;
                return true;
            }
        }

        // Is Pawn or invalid King move FIXME: Figure out instanceof then make this only pawn
        if (isValidPawnMove(p, initRow, initCol, endRow, endCol)) {
            squares[endRow][endCol] = squares[initRow][initCol];
            squares[initRow][initCol] = null;
            isMakeKing(endRow, endCol);
            return true;
        } else if (isValidPawnKillMove(p, initRow, initCol, endRow, endCol)) {
            // Seperated because eventually this will alow the peice to move to attack again
            squares[endRow][endCol] = squares[initRow][initCol];
            squares[initRow][initCol] = null;
            isMakeKing(endRow, endCol);
            return true;
        }

        return false;
    }

    /**
     * Prelim check for all moves to save space. A move validated by this method is not yet valid.
     * 
     * @param p Player attempting the move. Will need to match the game peice they are moving 
     * @param initRow Start row position
     * @param initCol Start column position
     * @param endRow End row position
     * @param endCol End column position
     * @return True if the move is legal and false otherwise
     * @throws IllegalArgumentException if any input value is less than zero
     */
    private boolean isValidMoveHelper(Player p, int initRow, int initCol, int endRow, int endCol) {
        if (initRow < 0 || initCol < 0 || endRow < 0 || endCol < 0) {
            throw new IllegalArgumentException("Values must be postive");
        }

        // Target position must be real and empty. Also does not allow for non moves.
        if (endRow > squares.length || endCol > squares.length || (squares[endRow][endCol] != null)) {
            return false;
        }

        // Piece must exist in given location and be the players color
        if ((squares[initRow][initCol] == null) || (squares[initRow][initCol].getColor() != p.getColor())) {
            return false;
        }

        return true;
    }

    /**
     * Check that standard pawn move is valid. All peices can make pawn moves but this method does
     * not apply to jumps/kills or king only moves.
     * 
     * @param p Player attempting the move. Will need to match the game peice they are moving 
     * @param initRow Start row position
     * @param initCol Start column position
     * @param endRow End row position
     * @param endCol End col position
     * @return True if the move is legal and false otherwise
     * @throws IllegalArgumentException if any input value is less than zero
     */
    private boolean isValidPawnMove(Player p, int initRow, int initCol, int endRow, int endCol) {
        // Move must be real and applied to a pawn
        if (!isValidMoveHelper(p, initRow, initCol, endRow, endCol)) {
            return false;
        }

        // If BLACK, end x must be one to left or right and one up and be black 
        if (((endCol == initCol + 1) || (endCol == initCol - 1))
                && (endRow == initRow - 1)
                && (p.getColor() == GamePiece.Color.BLACK)) {
            return true;
        }
        
        // If RED, end x must be one to left or right and one down and be black 
        if (((endCol == initCol + 1) || (endCol == initCol - 1))
                && (endRow == initRow + 1)
                && (p.getColor() == GamePiece.Color.RED)) {
            return true;
        }

        return false;
    }

    private boolean isValidPawnKillMove(Player p, int initRow, int initCol, int endRow, int endCol) {
        // Move must be real and applied to a pawn
        if (!isValidMoveHelper(p, initRow, initCol, endRow, endCol)) {
            return false;
        }

        // If attempting to move up right as BLACK... else up left as BLACK
        if ((endRow == initRow - 2) && (endCol == initCol + 2) && p.getColor() == GamePiece.Color.BLACK) {
            if (squares[initRow - 1][initCol + 1].getColor() != p.getColor()) {
                squares[initRow - 1][initCol + 1] = null;
                return true;
            }
        } else if ((endRow == initRow - 2) && (endCol == initCol - 2) && p.getColor() == GamePiece.Color.BLACK) {
            if (squares[initRow - 1][initCol - 1].getColor() != p.getColor()) {
                squares[initRow - 1][initCol - 1] = null;
                return true;
            }
        }

        // If attempting to move down right as RED... else down left as RED
        if ((endRow == initRow + 2) && (endCol == initCol + 2) && p.getColor() == GamePiece.Color.RED) {
            if (squares[initRow + 1][initCol + 1].getColor() != p.getColor()) {
                squares[initRow + 1][initCol + 1] = null;
                return true;
            }
        } else if ((endRow == initRow + 2) && (endCol == initCol - 2) && p.getColor() == GamePiece.Color.RED) {
            if (squares[initRow + 1][initCol - 1].getColor() != p.getColor()) {
                squares[initRow + 1][initCol - 1] = null;
                return true;
            }
        }

        return false;
    }

    private boolean isValidKingMove(Player p, int initRow, int initCol, int endRow, int endCol) {
        // Move must be real and applied to a king
        if (!isValidMoveHelper(p, initRow, initCol, endRow, endCol)) {
            return false;
        }
        
        System.out.println(squares[7][2] instanceof King);

        return Math.abs(endRow - initRow) <= 1 && Math.abs(endCol - initCol) <= 1;
    }

    private boolean isValidKingKillMove(Player p, int initRow, int initCol, int endRow, int endCol) {
        // Move must be real and applied to a pawn
        if (!isValidMoveHelper(p, initRow, initCol, endRow, endCol)) {
            return false;
        }

        // If attempting to move up right... else up left
        if ((endRow == initRow - 2) && (endCol == initCol + 2)) {
            if (squares[initRow - 1][initCol + 1].getColor() != p.getColor()) {
                squares[initRow - 1][initCol + 1] = null;
                return true;
            }
        } else if ((endRow == initRow - 2) && (endCol == initCol - 2)) {
            if (squares[initRow - 1][initCol - 1].getColor() != p.getColor()) {
                squares[initRow - 1][initCol - 1] = null;
                return true;
            }
        }

        // If attempting to move down right... else down left
        if ((endRow == initRow + 2) && (endCol == initCol + 2)) {
            if (squares[initRow + 1][initCol + 1].getColor() != p.getColor()) {
                squares[initRow + 1][initCol + 1] = null;
                return true;
            }
        } else if ((endRow == initRow + 2) && (endCol == initCol - 2)) {
            if (squares[initRow + 1][initCol - 1].getColor() != p.getColor()) {
                squares[initRow + 1][initCol - 1] = null;
                return true;
            }
        }

        return false;
    }

    private boolean isMakeKing(int row, int col) {
        if (((row == 0) && (squares[row][col].getColor() == GamePiece.Color.BLACK))
                || ((row == 7) && (squares[row][col].getColor() == GamePiece.Color.RED))) {
            squares[row][col] = new King(squares[row][col].getColor());
            return true;
        }
        return false;
    }

    public int countPieces(GamePiece.Color color) {
        int count = 0;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if ((squares[i][j] != null) && (squares[i][j].getColor() == color)) {
                    count++;
                }
            }
        }
        return count;
    } 

    @Override
    public String toString() {
        String val = "row/col == [row][col]\n  0 1 2 3 4 5 6 7\n";
        for (int i = 0; i < squares.length; i++) {
            val += i + " ";
            for (int j = 0; j < squares[i].length; j++) {
                if (squares[i][j] == null) {
                    val += "- ";
                } else {
                    val += squares[i][j] + " ";
                    // val += i + "," + j;
                }
            }
            val += "\n";
        }
        return val;
    }
}
