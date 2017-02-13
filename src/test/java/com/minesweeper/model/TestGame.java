package com.minesweeper.model;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGame {

    public static final Logger LOG = LoggerFactory.getLogger(TestGame.class);

    private Game game;

    @Before
    public void setup() {
        game = new Game(3,3,1);
    }

    @Test
    public void testPlay(){

        Random rn = new Random();
        int row = rn.nextInt(game.getBoard().getRows());
        LOG.debug("row: {}", row);

        boolean finish = false;
        for(int i = 0; i < game.getBoard().getRows(); i++){
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
                if (board.isExceededLimitBoard(row + i, column + j, i, j) && board.isNotMine(row+i, column+j)){
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