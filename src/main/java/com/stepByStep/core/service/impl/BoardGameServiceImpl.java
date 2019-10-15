package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.repository.BoardGameRepository;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardGameServiceImpl implements BoardGameService {

    @Value("${upload.path}")
    private String uploadPath;
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
    public void post(String name, double price, int averageAge, int countPLayers, String description,
                     MultipartFile file) throws ServiceException{
        BoardGame boardGame = new BoardGame(name, price);
        boardGame.setAverageAge(averageAge);
        boardGame.setCountPlayers(countPLayers);
        boardGame.setDescription(description);
        boardGame.setFilename(addBoardGameImage(file));
        boardGameRepository.save(boardGame);
    }

    private String addBoardGameImage(MultipartFile file)throws ServiceException{
        String filename=null;
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                throw new ServiceException(e.getMessage());
            }
            filename=resultFilename;
        }
        return filename;
    }

    @Override
    public void edit(BoardGame boardGame) {

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
        return boardGameRepository.findAll(new Sort(Sort.Direction.ASC,"name"));
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
