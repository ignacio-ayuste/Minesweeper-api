package com.minesweeper.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGame {

    private Game game;

    @Before
    public void setup() {
        game = new Game(3,3,1);
    }

    @Test
    public void testPlay(){

        Random rn = new Random();
        int row = rn.nextInt(8) + 1;
        System.out.println(row);

        boolean finish = false;
        for(int i = 1; i < 9; i++){
            if(!finish){
                finish = game.select(row,i);
                if(!finish){
                    assertThat(validateAdjacent(game.getBoard(), row, i), equalTo(true));
                }
            }
        }

    }

    private boolean validateAdjacent(Board board, int row, int column){
        boolean invalidSquareValue = true;
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (board.isNotMine(row+i, column+j) && board.isExceededLimitBoard(row, column, i, j)){
                    System.out.println(board.getSquares().get(row + i)[column + j].getValue());
                    invalidSquareValue = board.getSquares().get(row + i)[column + j].isInitialValue();
                    if(invalidSquareValue){
                        return false;
                    }
                }
            }
        }

        return true;
    }

}