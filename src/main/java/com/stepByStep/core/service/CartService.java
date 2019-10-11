package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;

public interface CartService {

    void save(Cart cart);

    void delete(Cart cart);

    Cart findById(Long cartId);

    void clear(Long cartId);

    void update(Cart cart, CartItem cartItem);
}
