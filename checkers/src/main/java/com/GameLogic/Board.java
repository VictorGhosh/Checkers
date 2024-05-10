package com.GameLogic;

public class Board {
    // Top left 0,0 [row][column]
    private GamePiece[][] squares;

    /**
     * Constructor only make standard 8x8 board
     */
    public Board() {
        this.squares = new GamePiece[8][8];
    }

    /**
     * Clear this board and set up pieces in standard locations
     */
    public void initialize() {
        // Clear board
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                this.squares[i][j] = null;
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
    
    /**
     * Move player p's piece from init position to end position if legal. Players color must
     * match the piece they are trying to move.
     * TODO: Does not yet allow hop chaining. Can be simplifed after that is added
     * 
     * @param p Player attempting the move
     * @param initRow target piece starting row
     * @param initCol target piece starting column
     * @param endRow target end row for move
     * @param endCol target end column for move
     * @throws IllegalArgumentException if any coordinate is below 0
     * @return true if move is ok and was executed and false otherwise
     */
    public boolean move(Player p, int initRow, int initCol, int endRow, int endCol) {
        // Check legal input
        if (!isValidMoveHelper(p, initRow, initCol, endRow, endCol)) {
            return false;
        }

        // Is King
        if (squares[initRow][initCol] instanceof King) {
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

        // Is Pawn
        if (squares[initRow][initCol].getClass().equals(GamePiece.class)) {
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
     */
    private boolean isValidPawnMove(Player p, int initRow, int initCol, int endRow, int endCol) {
        // Allow to move one to the left or right
        if (Math.abs(endCol - initCol) <= 1) {
            // BLACK moves up
            if ((endRow == initRow - 1) && (p.getColor() == GamePiece.Color.BLACK)) {
                return true;
            }
            // RED moves down
            if ((endRow == initRow + 1) && (p.getColor() == GamePiece.Color.RED)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check that if pawn hop kill is vaid AND remove the piece being hopped. The removal should
     * be seperated but I'm lazy and it would be annoying to imlpement.
     * 
     * @param p Player attempting the hop
     * @param initRow start row of target piece
     * @param initCol start column of target piece
     * @param endRow end row of target piece
     * @param endCol end column of target piece
     * @return true if move is aproved and enemy was removed and false otherwise
     */
    private boolean isValidPawnKillMove(Player p, int initRow, int initCol, int endRow, int endCol) {
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

    /**
     * Check that a king movment is ok
     * @param p
     * @param initRow
     * @param initCol
     * @param endRow
     * @param endCol
     * @return true if kings move is valid and false othewise
     */
    private boolean isValidKingMove(Player p, int initRow, int initCol, int endRow, int endCol) {
        return Math.abs(endRow - initRow) <= 1 && Math.abs(endCol - initCol) <= 1;
    }

    /**
     * See isValidPawnKillMove this is the same thing but for kings so they attack at any diagonal
     * 
     * @param p
     * @param initRow
     * @param initCol
     * @param endRow
     * @param endCol
     * @return true if move is aproved and enemy was removed and false otherwise
     */
    private boolean isValidKingKillMove(Player p, int initRow, int initCol, int endRow, int endCol) {
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

    /**
     * Check if piece at [row][col] should be promoted and make king if so
     *  
     * @param row
     * @param col
     * @return true if piece was made a king and false othewise
     */
    private boolean isMakeKing(int row, int col) {
        if (((row == 0) && (squares[row][col].getColor() == GamePiece.Color.BLACK))
                || ((row == 7) && (squares[row][col].getColor() == GamePiece.Color.RED))) {
            squares[row][col] = new King(squares[row][col].getColor());
            return true;
        }
        return false;
    }

    /**
     * Count the remaining pieces of a specified color. For checking if game end condition is met
     * 
     * @param color color of pieces to count
     * @return number of remaining pieces
     */
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

    /**
     * Should only be used in debugging
     */
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
