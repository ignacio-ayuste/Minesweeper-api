package com.minesweeper.repository;


import com.minesweeper.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {



}