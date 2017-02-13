package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Serializable {

    public static final Logger LOG = LoggerFactory.getLogger(Board.class);

    @JsonIgnore
    private int[][] mines;
    @JsonIgnore
    private  int minesLimit;
    private List<Square[]> squares;
    private int rows, cols;


    public Board(int rows, int cols, int minesLimit) {
        this.rows = rows;
        this.cols = cols;
        this.minesLimit = minesLimit;
        mines = new int[rows][cols];
        squares = new ArrayList<>();
        randomMines();
        fillTips();
        populateBoard();

    }

    public boolean win() {
        int count = 0;
        for (int row = 0; row < rows; row++){
            for (int column = 0; column < cols; column++){
                if (squares.get(row)[column].isInitialValue()){
                    count++;
                }
            }
        }
        return count == minesLimit;
    }

    public void openAdjacentSquares(int row, int column) {
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if (isExceededLimitBoard(row + i, column + j, i, j) && isNotMine(row+i, column+j)){
                    Square square = squares.get(row + i)[column + j];
                    square.setValue(Character.forDigit(mines[row + i][column + j], 10));
                    square.setShow(true);
                }

    }

    public boolean isExceededLimitBoard(int row, int column, int i, int j) {
//        return row != -1 && row != (rows - 1) && column != -1 && column != (cols - 1);
        return row != -1 && row != rows && column != -1 && column != cols;
        //return row != (rows - 1)  && column != (cols - 1);
    }

    public boolean isNotMine(int row, int column) {
        return getPosition(row, column) != -1;
    }

    private boolean isMine(int row, int column) {
        return getPosition(row, column) == -1;
    }

    public int getPosition(int row, int column) {
        return mines[row][column];
    }

    public boolean setPosition(int row, int column) {

        if ((!squares.get(row)[column].isInitialValue()) && ((row < rows && row >= 0) && (column < cols && column >= 0)))
            LOG.debug("The row {} and the column {} is already Selected", row, column);

        if (!isValidRange(row, column))
            LOG.debug("Choose a number between 0 and {}", column);

        return isMine(row, column);
    }

    private boolean isValidRange(int row, int column) {
        //return row  1 || row > 8 || column < 1 || column > 8;
        return (row >= 0) && (row < rows) &&  (column >= 0) && (column < cols);
    }

    //Useful for Debug and Test.
    public void show() {
        System.out.println("\n     Lines");
        for (int row = (rows - 1); row >= 0; row--) {
            System.out.print("       " + row + " ");

            for (int column = 0; column < cols; column++) {
                System.out.print("   " + squares.get(row)[column].getValue());
            }

            System.out.println();
        }

        System.out.println("\n            0   1   2  ");
        System.out.println("                      Columns");

    }

    public void fillTips() {
        for (int row = 0; row < mines.length; row++){
            for (int column = 0; column < mines.length; column++) {

                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if(isValidRange(row + i,column + j)){
                            if (isNotMine(row,column))
                                if (isMine(row + i,column + j))
                                    mines[row][column]++;
                        }
            }
        }
    }

    public void showMines() {
        for (int i = 0; i < mines.length; i++)
            for (int j = 0; j < mines.length; j++)
                if (isMine(i,j))
                    squares.get(i)[j].setValue('*');
        show();
    }

    public void cleanMines() {
        for (int i = 1; i < 9; i++)
            for (int j = 1; j < 9; j++)
                if (isMine(i,j))
                    squares.get(i)[j].setValue('0');
        show();
    }

    public void populateBoard() {
        for (int i = 0; i < rows; i++) {
            Square[] squareRow = new Square[cols];
            for (int j = 0; j < cols; j++) {
                Square s = new Square(i, j);
                squareRow[j] = s;
                if(isMine(i,j)){
                    s.setHasMine(true);
                }
            }
            squares.add(squareRow);
        }

    }

    public void randomMines() {
        Random random = new Random();

        int row, column;
        for (int i = 0; i < minesLimit; i++) {

            do {
                row = random.nextInt(rows);
                column = random.nextInt(cols);

            } while (isMine(row, column));

            mines[row][column] = -1;
        }
    }

    public int[][] getMines() {
        return mines;
    }

    public void setMines(int[][] mines) {
        this.mines = mines;
    }

    public List<Square[]> getSquares() {
        return squares;
    }

    public void setSquares(List<Square[]> squares) {
        this.squares = squares;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}