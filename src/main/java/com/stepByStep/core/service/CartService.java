package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.util.exceptions.ServiceException;

public interface CartService {

    void save(Cart cart);

    void delete(Cart cart);

    Cart findById(Long cartId);

    void clearUserCart(Cart cart);

    void extendUserCart(Cart cart, CartItem cartItem) throws ServiceException;

    void reduceUserCart(Cart cart, CartItem cartItem) throws ServiceException;

}
