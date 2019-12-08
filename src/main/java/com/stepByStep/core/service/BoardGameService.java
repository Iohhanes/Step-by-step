package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.BoardGame;

import java.util.List;

public interface BoardGameService {

    void save(BoardGame boardGame);

    void delete(BoardGame boardGame);

    void edit(BoardGame boardGame, BoardGame boardGameForm);

    BoardGame findById(Long boardGameId);

    List<BoardGame> findAll();

    List<BoardGame> findAllOrderByName();

    List<BoardGame> findByTitle(String title);

    List<BoardGame> findByAge(int age);

    List<BoardGame> findByPrice(double price);

    List<BoardGame> findByCountPlayers(int countPlayers);

    List<BoardGame> findByFilters(String title, Double price, Integer countPlayers, Integer age);


}
