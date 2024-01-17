package com.moxos.uab.util.word;

public class ColSpan {

    private int col;
    private int fromRow;
    private int toRow;

    public ColSpan(int col, int fromRow, int toRow) {
        this.col = col;
        this.fromRow = fromRow;
        this.toRow = toRow;
    }

    public int getCol() {
        return col;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getToRow() {
        return toRow;
    }

}