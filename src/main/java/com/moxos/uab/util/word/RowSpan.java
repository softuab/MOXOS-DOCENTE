package com.moxos.uab.util.word;

public class RowSpan {

    private int row;
    private int fromCol;
    private int toCol;

    public RowSpan(int row, int fromCol, int toCol) {
        this.row = row;
        this.fromCol = fromCol;
        this.toCol = toCol;
    }

    public int getRow() {
        return row;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToCol() {
        return toCol;
    }

}
