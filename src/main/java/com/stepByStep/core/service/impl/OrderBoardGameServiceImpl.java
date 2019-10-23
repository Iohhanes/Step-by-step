package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.OrderBoardGame;
import com.stepByStep.core.repository.OrderBoardGameRepository;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.OrderBoardGameService;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderBoardGameServiceImpl implements OrderBoardGameService {


    public static final String CART_ITEM_NOT_FOUND_EXCEPTION = "This cart item not found";

    private OrderBoardGameRepository orderBoardGameRepository;
    private CartService cartService;

    @Autowired
    public OrderBoardGameServiceImpl(OrderBoardGameRepository orderBoardGameRepository, CartService cartService) {
        this.orderBoardGameRepository = orderBoardGameRepository;
        this.cartService = cartService;
    }

    @Override
    public void save(OrderBoardGame orderBoardGame) {
        orderBoardGameRepository.save(orderBoardGame);
    }

    @Override
    public void delete(OrderBoardGame orderBoardGame) {
        orderBoardGameRepository.delete(orderBoardGame);
    }

    @Override
    public OrderBoardGame createNewOrderBoardGame(CartItem cartItem, int quantity) throws ServiceException {
        if (cartItem == null) {
            log.warn(CART_ITEM_NOT_FOUND_EXCEPTION);
            throw new ServiceException(CART_ITEM_NOT_FOUND_EXCEPTION);
        }
        OrderBoardGame orderBoardGame = new OrderBoardGame();
        orderBoardGame.setBoardGame(cartItem.getBoardGame());
        orderBoardGame.setQuantity(quantity);
        return orderBoardGame;
    }
}
