package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartItemRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartItemServiceImpl implements CartItemService {

    public static final String BOARD_GAME_NOT_FOUND_EXCEPTION = "This board game not found";

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteAllByCart(Cart cart) {
        cartItemRepository.deleteAllByCart(cart);
    }

    @Override
    public CartItem createNewCartItem(BoardGame boardGame, int quantity) throws ServiceException {
        if (boardGame == null) {
            log.warn(BOARD_GAME_NOT_FOUND_EXCEPTION);
            throw new ServiceException(BOARD_GAME_NOT_FOUND_EXCEPTION);
        }
        CartItem cartItem = new CartItem();
        cartItem.setBoardGame(boardGame);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    @Override
    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }
}
