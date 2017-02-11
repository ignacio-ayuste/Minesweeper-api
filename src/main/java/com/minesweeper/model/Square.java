package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Square implements Serializable {

    public static final char SQUARE_INITIAL_VALUE = 'X';
    private int row, col;
    private boolean show;
    private boolean hasMine;
    private char value = SQUARE_INITIAL_VALUE;

    public Square() {
    }

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @JsonIgnore
    public boolean isInitialValue(){
        return value == SQUARE_INITIAL_VALUE;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isHasMine() {
        return hasMine;
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
}