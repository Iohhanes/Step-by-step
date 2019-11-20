package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.repository.BoardGameRepository;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BoardGameServiceImpl implements BoardGameService {

    private BoardGameRepository boardGameRepository;
    private StorageService storageService;

    @Autowired
    public BoardGameServiceImpl(BoardGameRepository boardGameRepository, StorageService storageService) {
        this.boardGameRepository = boardGameRepository;
        this.storageService = storageService;
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
    public void post(String name, double price, int averageAge, int countPLayers, String description,
                     MultipartFile file) {
        BoardGame boardGame = new BoardGame(name, price);
        boardGame.setAverageAge(averageAge);
        boardGame.setCountPlayers(countPLayers);
        boardGame.setDescription(description);
        boardGame.setFilename(storageService.loadImage(file));
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
        return boardGameRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public List<BoardGame> findByAverageAge(int averageAge) {
        return boardGameRepository.findByAverageAge(averageAge);
    }

    @Override
    public List<BoardGame> findByPrice(double price) {
        return boardGameRepository.findByPrice(price);
    }

    @Override
    public List<BoardGame> findByCountPlayers(int countPlayers) {
        return boardGameRepository.findByCountPlayers(countPlayers);
    }
}
