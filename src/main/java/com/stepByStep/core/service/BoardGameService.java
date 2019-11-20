package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardGameService {

    void save(BoardGame boardGame);

    void delete(BoardGame boardGame);

    void post(String name, double price, int averageAge, int countPLayers, String description, MultipartFile file);

    BoardGame findById(Long boardGameId);

    List<BoardGame> findAll();

    List<BoardGame> findAllOrderByName();

    List<BoardGame> findByAverageAge(int averageAge);

    List<BoardGame> findByPrice(double price);

    List<BoardGame> findByCountPlayers(int countPlayers);

}
