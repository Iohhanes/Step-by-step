package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    public static final int INIT_CART_ITEMS_VALUE = 0;
    public static final int MIN_QUANTITY = 0;
    public static final String CART_OR_CART_ITE_IS_NULL_WARNING_MESSAGE = "Impossible to add board game to cart. " +
            "Cart or cart item is null.";
    private static Logger LOGGER = Logger.getLogger(CartService.class);


    private CartRepository cartRepository;
    private CartItemService cartItemService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
        cartItemService.deleteAllByCart(cart);
    }

    @Override
    public void clear(Long cartId) {
        Cart cart = findById(cartId);
        if (cart != null) {
            cart.getItems().clear();
            cartItemService.deleteAllByCart(cart);
            revalidateCartMetrics(cart);
        }
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    @Override
    public void update(Cart cart, CartItem cartItem) {
        if (cart != null && cartItem != null) {
            List<CartItem> items = cart.getItems();
            if (items.contains(cartItem)) {
                int index = items.indexOf(cartItem);
                if (cartItem.getQuantity() > MIN_QUANTITY) {
                    items.get(index).setQuantity(cartItem.getQuantity());
                } else {
                    items.remove(cartItem);
                }
            } else {
                items.add(cartItem);
            }
            cartItemService.save(cartItem);
            revalidateCartMetrics(cart);
        } else {
            LOGGER.warn(CART_OR_CART_ITE_IS_NULL_WARNING_MESSAGE);
        }
    }


    private void revalidateCartMetrics(Cart cart) {
        int totalCountItems = INIT_CART_ITEMS_VALUE;
        int totalCost = INIT_CART_ITEMS_VALUE;
        for (CartItem item : cart.getItems()) {
            totalCountItems += item.getQuantity();
            totalCost += item.getQuantity() * item.getBoardGame().getPrice();
        }
        cart.setTotalCost(totalCost);
        cart.setTotalCountItems(totalCountItems);
    }
}
