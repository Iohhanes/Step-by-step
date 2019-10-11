package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.BoardGame;

import java.util.List;

public interface BoardGameService {

    void save(BoardGame boardGame);

    void delete(BoardGame boardGame);

    List<BoardGame> findAll();

    List<BoardGame> findAllOrderByName();

    List<BoardGame> findByAverageAge(int averageAge);

    List<BoardGame> findByPrice(double price);

    List<BoardGame> findByCountPlayers(int countPlayers);

}
