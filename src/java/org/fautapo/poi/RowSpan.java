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
