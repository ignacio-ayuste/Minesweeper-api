package com.minesweeper.service;

import com.minesweeper.exception.GameOverException;
import com.minesweeper.model.Game;
import com.minesweeper.model.Status;
import com.minesweeper.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public static final Logger LOG = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameRepository gameRepository;

    public Game startGame(int rows, int cols, int minesLimit) {

        Game game = new Game(rows, cols, minesLimit);

        gameRepository.save(game);

        return game;
    }

    public Game select(String gameId, int row, int col) {
        Game game = findGameById(gameId);

        validateGame(game);

        game.select(row, col);

        gameRepository.save(game);

        return game;
    }

    public Game findGameById(String gameId){
        return gameRepository.findOne(gameId);
    }

    public Game flag(String gameId, int row, int col) {
        Game game = findGameById(gameId);

        validateGame(game);

        game.flag(row, col);

        gameRepository.save(game);

        return game;
    }

    private void validateGame(Game game) {
        if(game.getStatus() == Status.LOOSE || game.getStatus() == Status.WIN){
            throw new GameOverException("The game is already over");
        }
    }

}