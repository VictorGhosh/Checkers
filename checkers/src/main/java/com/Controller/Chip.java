package com.Controller;

public class Chip {
    private int row;
    private int col;

    Chip(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chip chip = (Chip) o;
        return row == chip.getRow() && col == chip.getCol();
    }

    @Override
    public int hashCode() {
        return 31 * row * col;
    }
}
