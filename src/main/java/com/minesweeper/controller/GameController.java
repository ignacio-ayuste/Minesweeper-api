package com.minesweeper.controller;

import com.minesweeper.model.Game;
import com.minesweeper.model.RestError;
import com.minesweeper.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "game")
public class GameController {

    public static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @RequestMapping( value= "/create", method = RequestMethod.GET)
    @ResponseBody
    public Game create(@RequestParam(value = "rows", required = false, defaultValue = "3") int rows,
                       @RequestParam(value = "cols", required = false, defaultValue = "3") int cols,
                       @RequestParam(value = "minesLimit", required = false, defaultValue = "2") int minesLimit) {
        LOG.info("Creating Game...");

        return gameService.startGame(rows, cols, minesLimit);
    }

    @RequestMapping( value= "{gameId}/select", method = RequestMethod.GET)
    @ResponseBody
    public Game select(@PathVariable String gameId,
                       @RequestParam("row") int row,
                       @RequestParam("col") int col) {
        LOG.debug("Select Square row {}, col {}", row, col);

        return gameService.select(gameId, row, col);
    }

    @RequestMapping( value= "{gameId}/flag", method = RequestMethod.GET)
    @ResponseBody
    public Game flag(@PathVariable String gameId,
                       @RequestParam("row") int row,
                       @RequestParam("col") int col) {
        LOG.debug("Flag Square row {}, col {}", row, col);

        return gameService.flag(gameId, row, col);
    }

    @ExceptionHandler
    public ResponseEntity<RestError> exceptionHandler(Exception ex) {
        RestError error = new RestError(HttpStatus.PRECONDITION_FAILED.name(),ex.getMessage());
        return new ResponseEntity<RestError>(error, HttpStatus.OK);
    }


}