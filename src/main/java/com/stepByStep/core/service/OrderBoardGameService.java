package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.OrderBoardGame;
import com.stepByStep.core.util.exceptions.ServiceException;

public interface OrderBoardGameService {

    void save(OrderBoardGame orderBoardGame);

    void delete(OrderBoardGame orderBoardGame);

    OrderBoardGame createNewOrderBoardGame(CartItem cartItem, int quantity) throws ServiceException;

}
