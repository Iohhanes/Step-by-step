package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.OrderBoardGame;
import com.stepByStep.core.repository.OrderBoardGameRepository;
import com.stepByStep.core.service.OrderBoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBoardGameServiceImpl implements OrderBoardGameService {

    private OrderBoardGameRepository orderBoardGameRepository;

    @Autowired
    public OrderBoardGameServiceImpl(OrderBoardGameRepository orderBoardGameRepository) {
        this.orderBoardGameRepository = orderBoardGameRepository;
    }

    @Override
    public void save(OrderBoardGame orderBoardGame) {
        orderBoardGameRepository.save(orderBoardGame);
    }

    @Override
    public void delete(OrderBoardGame orderBoardGame) {
        orderBoardGameRepository.delete(orderBoardGame);
    }
}
