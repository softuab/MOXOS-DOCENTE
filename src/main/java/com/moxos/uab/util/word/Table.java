package com.moxos.uab.util.word;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private int row;
    private int col;
    private DataCell[][] data;
    private List<RowSpan> RowSpan;
    private List<ColSpan> ColSpan;
    private List<Long> columnsSize;

    public List<Long> getColumnsSize() {
        return columnsSize;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public DataCell[][] getData() {
        return data;
    }

    public DataCell getData(int row, int col) {
        return data[row][col];
    }

    public Table(int row, int col) {
        data = new DataCell[row][col];
        this.row = row;
        this.col = col;
        RowSpan = new ArrayList<>();
        ColSpan = new ArrayList<>();
        columnsSize = new ArrayList<>();
    }

    public void AddRowspan(int row, int fromCol, int toCol) {
        RowSpan.add(new RowSpan(row, fromCol, toCol));
    }

    public void AddColspan(int col, int fromRow, int toRow) {
        ColSpan.add(new ColSpan(col, fromRow, toRow));
    }

    public void Add(int index, DataCell... arguments) {
        if (arguments.length <= col) {
            for (int i = 0; i < arguments.length; i++) {
                data[index][i] = arguments[i];
            }
        }
    }

    public void AddColumnsSize(long... arguments) {
        for (int i = 0; i < arguments.length; i++) {
            this.columnsSize.add(arguments[i]);
        }
    }

    public List<RowSpan> getRowSpan() {
        return this.RowSpan;
    }

    public List<ColSpan> getColSpan() {
        return this.ColSpan;
    }
}
