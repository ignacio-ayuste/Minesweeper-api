package com.minesweeper.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Game")
public class Game implements Serializable {

    public static final Logger LOG = LoggerFactory.getLogger(Game.class);

    @Id
    private String id;
    private Status status;
    private Board board;

    public Game() {
    }

    public Game(int rows, int cols, int minesLimit) {
        status = Status.NEW;
        board = new Board(rows,cols,minesLimit);
    }

    public boolean select(int row, int column) {
        LOG.debug("Select Square row: {}, col: {}", row, column);
        Status.SELECT.setValue(String.format(Status.SELECT.getValue(), row, column));
        status = Status.SELECT;

        boolean finish = board.setPosition(row, column);
        LOG.debug("finish: {}", finish);
        if(!finish){
            board.openAdjacentSquares(row,column);
            if(board.win()){
                status = Status.WIN;
                LOG.info("Finish - You Win!!!!!!");
            }
        }else{
            LOG.info("Finish - Show Mines");
            status = Status.LOOSE;
            return true;
        }

        return false;
    }

    public void flag(int row, int col) {
        board.getSquares().get(row)[col].setValue('F');

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}