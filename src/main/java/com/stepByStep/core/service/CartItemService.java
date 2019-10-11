package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;

import java.util.List;

public interface CartItemService {

    void save(CartItem cartItem);

    void delete(CartItem cartItem);

    void deleteAllByCart(Cart cart);

    List<CartItem> findByCart(Cart cart);

    List<CartItem> findAll();

}
