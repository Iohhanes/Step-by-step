package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.repository.BoardGameRepository;
import com.stepByStep.core.service.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGameServiceImpl implements BoardGameService {

    private static final int EMPTY_TITLE_LENGTH_VALUE = 0;

    private BoardGameRepository boardGameRepository;

    @Autowired
    public BoardGameServiceImpl(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    @Override
    public void save(BoardGame boardGame) {
        boardGameRepository.save(boardGame);
    }

    @Override
    public void delete(BoardGame boardGame) {
        boardGameRepository.delete(boardGame);
    }

    @Override
    public void edit(BoardGame boardGame, BoardGame boardGameForm) {
        boardGame.setTitle(boardGameForm.getTitle());
        boardGame.setPrice(boardGameForm.getPrice());
        boardGame.setAge(boardGameForm.getAge());
        boardGame.setCountPlayers(boardGameForm.getCountPlayers());
        boardGame.setDescription(boardGameForm.getDescription());
        if (boardGameForm.getFilename() != null) {
            boardGame.setFilename(boardGameForm.getFilename());
        }
        boardGameRepository.save(boardGame);
    }

    @Override
    public BoardGame findById(Long boardGameId) {
        return boardGameRepository.findById(boardGameId).orElse(null);
    }

    @Override
    public List<BoardGame> findAll() {
        return boardGameRepository.findAll();
    }

    @Override
    public List<BoardGame> findAllOrderByName() {
        return boardGameRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }

    @Override
    public List<BoardGame> findByTitle(String title) {
        return boardGameRepository.findByTitle(title);
    }

    @Override
    public List<BoardGame> findByAge(int age) {
        return boardGameRepository.findByAge(age);
    }

    @Override
    public List<BoardGame> findByPrice(double price) {
        return boardGameRepository.findByPrice(price);
    }

    @Override
    public List<BoardGame> findByCountPlayers(int countPlayers) {
        return boardGameRepository.findByCountPlayers(countPlayers);
    }

    @Override
    public List<BoardGame> findByFilters(String title, Double price, Integer countPlayers, Integer age) {
        return (title.length() == EMPTY_TITLE_LENGTH_VALUE && price == null && countPlayers == null && age == null) ?
                boardGameRepository.findAll() : boardGameRepository.findByFilters(title, price, countPlayers, age);

    }
}
