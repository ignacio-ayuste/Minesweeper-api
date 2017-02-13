package com.minesweeper.exception;

public class GameOverException extends RuntimeException{

    public GameOverException(String message) {
        super(message);
    }
}