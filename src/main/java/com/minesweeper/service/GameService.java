package com.minesweeper.service;

import com.minesweeper.model.Game;
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

    public Game startGame() {

        Game game = new Game();

        gameRepository.save(game);

        return game;
    }

    public Game move(String gameId, int row, int col) {
        Game game = findGameById(gameId);
        game.select(row, col);

        gameRepository.save(game);

        return game;
    }

    public Game findGameById(String gameId){
        return gameRepository.findOne(gameId);
    }

    public Game flag(String gameId, int row, int col) {
        Game game = findGameById(gameId);
        game.flag(row, col);

        return game;
    }

}