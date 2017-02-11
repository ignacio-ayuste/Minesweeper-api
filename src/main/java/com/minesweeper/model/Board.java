package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.minesweeper.model.Square.SQUARE_INITIAL_VALUE;

public class Board implements Serializable {

    public static final Logger LOG = LoggerFactory.getLogger(Board.class);

    @JsonIgnore
    private int[][] mines;
    private List<Square[]> squares;
    private int rows, cols;

    public Board() {
        mines = new int[10][10];
        squares = new ArrayList<>();
        startMines();
        randomMines();
        fillTips();
        populateBoard();
        rows = 10;
        cols = 10;

    }

    public boolean win() {
        int count = 0;
        for (int row = 1; row < 9; row++)
            for (int column = 1; column < 9; column++)
                if (squares.get(row)[column].getValue() == SQUARE_INITIAL_VALUE)
                    count++;
        if (count == 10)
            return true;
        else
            return false;
    }

    public void openAdjacentSquares(int row, int column) {
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if (inNotMine(row+i, column+j) && isExceededLimitBoard(row, column, i, j)){
                    Square square = squares.get(row + i)[column + j];
                    square.setValue(Character.forDigit(mines[row + i][column + j], 10));
                    square.setShow(true);
                }

    }

    public boolean isExceededLimitBoard(int row, int column, int i, int j) {
        return row != 0 && (row + i) != 9 && (column + j) != 0 && (column + j) != 9;
    }

    public boolean inNotMine(int row, int column) {
        return mines[row][column] != -1;
    }

    private boolean isMine(int row, int column) {
        return mines[row][column] == -1;
    }

    public int getPosition(int row, int column) {
        return mines[row][column];
    }

    public boolean setPosition(int row, int column) {

        if ((!squares.get(row)[column].isInitialValue()) && ((row < 9 && row > 0) && (column < 9 && column > 0)))
            LOG.debug("The row {} and the column {} is already Selected", row, column);

        if (isValidRange(row, column))
            System.out.println("Choose a number between 1 and 8");

        if (getPosition(row, column) == -1)
            return true;
        else
            return false;

    }

    private boolean isValidRange(int row, int column) {
        return row < 1 || row > 8 || column < 1 || column > 8;
    }

    //Useful for Debug and Test.
    public void show() {
        System.out.println("\n     Lines");
        for (int row = 8; row > 0; row--) {
            System.out.print("       " + row + " ");

            for (int column = 1; column < 9; column++) {
                System.out.print("   " + squares.get(row)[column].getValue());
            }

            System.out.println();
        }

        System.out.println("\n            1   2   3   4   5   6   7   8");
        System.out.println("                      Columns");

    }

    public void fillTips() {
        for (int row = 1; row < 9; row++)
            for (int column = 1; column < 9; column++) {

                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if (inNotMine(row,column))
                            if (isMine(row + i,column + j))
                                mines[row][column]++;
            }

    }

    public void showMines() {
        for (int i = 1; i < 9; i++)
            for (int j = 1; j < 9; j++)
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
        for (int i = 1; i < mines.length; i++) {
            Square[] squareRow = new Square[mines.length];
            for (int j = 1; j < mines.length; j++) {
                Square s = new Square(i, j);
                squareRow[j] = s;
                if(isMine(i,j)){
                    s.setHasMine(true);
                }
            }
            squares.add(squareRow);
        }

    }

    public void startMines() {
        for (int i = 0; i < mines.length; i++)
            for (int j = 0; j < mines.length; j++)
                mines[i][j] = 0;
    }

    public void randomMines() {
        Random random = new Random();

        boolean raffled;
        int row, column;
        for (int i = 0; i < 10; i++) {

            do {
                row = random.nextInt(8) + 1;
                column = random.nextInt(8) + 1;

                if (isMine(row, column))
                    raffled = true;
                else
                    raffled = false;
            } while (raffled);

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