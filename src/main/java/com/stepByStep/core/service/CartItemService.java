package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.util.exceptions.ServiceException;

import java.util.List;

public interface CartItemService {

    void save(CartItem cartItem);

    void delete(CartItem cartItem);

    void deleteAllByCart(Cart cart);

    CartItem createNewCartItem(BoardGame boardGame, int quantity) throws ServiceException;

    CartItem findById(Long cartItemId);

    CartItem findByBoardGame(BoardGame boardGame);

    List<CartItem> findByCart(Cart cart);

    List<CartItem> findAll();

}
