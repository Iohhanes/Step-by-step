package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    public static final int INIT_TOTAL_CART_ITEMS_COUNT_VALUE = 0;
    public static final double INIT_TOTAL_CART_ITEMS_COST_VALUE = 0.0;
    public static final String CART_ITEM_IS_NULL_EXCEPTION = "Input cart item is null";

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
    public void extendUserCart(Cart cart, CartItem cartItem) throws ServiceException {
        checkNullCartItem(cartItem);
        List<CartItem> items = cart.getItems();
        if (items.contains(cartItem)) {
            int index = items.indexOf(cartItem);
            items.get(index).setQuantity(items.get(index).getQuantity() + cartItem.getQuantity());
        } else {
            items.add(cartItem);
            cartItem.setCart(cart);
        }
        cartItemService.save(cartItem);
        revalidateCartMetrics(cart);
    }

    @Override
    public void reduceUserCart(Cart cart, CartItem cartItem) throws ServiceException {
        checkNullCartItem(cartItem);
        List<CartItem> items = cart.getItems();
        if (items.contains(cartItem)) {
            items.remove(cartItem);
            cartItemService.delete(cartItem);
            revalidateCartMetrics(cart);
        }
    }

    @Override
    public void clearUserCart(Cart cart) {
        cart.getItems().clear();
        cartItemService.deleteAllByCart(cart);
        revalidateCartMetrics(cart);
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    private void checkNullCartItem(CartItem cartItem) throws ServiceException {
        if (cartItem == null) {
            log.warn(CART_ITEM_IS_NULL_EXCEPTION);
            throw new ServiceException(CART_ITEM_IS_NULL_EXCEPTION);
        }
    }

    private void revalidateCartMetrics(Cart cart) {
        int totalCountItems = INIT_TOTAL_CART_ITEMS_COUNT_VALUE;
        double totalCost = INIT_TOTAL_CART_ITEMS_COST_VALUE;
        for (CartItem item : cart.getItems()) {
            totalCountItems += item.getQuantity();
            totalCost += item.getQuantity() * item.getBoardGame().getPrice();
        }
        cart.setTotalCost(totalCost);
        cart.setTotalCountItems(totalCountItems);
        cartRepository.save(cart);
    }
}
