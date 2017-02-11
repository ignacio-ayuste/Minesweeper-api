package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Game")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Game implements Serializable {

    public static final Logger LOG = LoggerFactory.getLogger(Game.class);

    @Id
    private String id;
    private String status;
    private Board board;


    public Game() {
        status = "Start";
        board = new Board();
    }

    public boolean select(int row, int column) {
        LOG.debug("select ");

        boolean finish = board.setPosition(row, column);
        System.out.println("finish: " + finish);
        if(!finish){
            board.openAdjacentSquares(row,column);
            boolean win = board.win();
            System.out.println("Win: " + win);
        }else{
            LOG.info("Finish - Show Mines");
            board.showMines();
            return true;
        }

        return false;
    }

    public void flag(int row, int col) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}