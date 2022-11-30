/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.poi;

/**
 *
 * @author FNZABALETAA
 */
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
