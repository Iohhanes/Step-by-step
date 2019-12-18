package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stepByStep.core.util.constants.DataPermissibleConstant.MAX_PERMISSIBLE_QUANTITY_ITEM;
import static com.stepByStep.core.util.constants.DataPermissibleConstant.MIN_PERMISSIBLE_QUANTITY_ITEM;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_CART_ITEM_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INVALID_ITEM_QUANTITY_EXCEPTION;

@Log4j2
@Service
@Transactional
public class CartServiceImpl implements CartService {

    private static final int INIT_TOTAL_CART_ITEMS_COUNT_VALUE = 0;
    private static final double INIT_TOTAL_CART_ITEMS_COST_VALUE = 0.0;

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

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public void extendUserCart(Cart cart, CartItem cartItem) throws NullParameterException, ServiceException {
        ShopElementIsNullChecker.checkNull(cartItem, new NullParameterException(INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        List<CartItem> items = cart.getItems();
        if (items.contains(cartItem)) {
            CartItem currentCartItem = items.get(items.indexOf(cartItem));
            int newCartItemQuantity = cartItem.getQuantity() + currentCartItem.getQuantity();
            if (newCartItemQuantity <= MIN_PERMISSIBLE_QUANTITY_ITEM ||
                    newCartItemQuantity >= MAX_PERMISSIBLE_QUANTITY_ITEM) {
                log.warn(INVALID_ITEM_QUANTITY_EXCEPTION + cartItem.getQuantity());
                throw new ServiceException(INVALID_ITEM_QUANTITY_EXCEPTION + cartItem.getQuantity());
            }
            currentCartItem.setQuantity(newCartItemQuantity);
            cartItemService.save(currentCartItem);
        } else {
            items.add(cartItem);
            cartItem.setCart(cart);
            cartItemService.save(cartItem);
        }
        revalidateCartMetrics(cart);
    }

    @Override
    public void reduceUserCart(Cart cart, CartItem cartItem, int quantity)
            throws NullParameterException, ServiceException {
        ShopElementIsNullChecker.checkNull(cartItem, new NullParameterException(INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        List<CartItem> items = cart.getItems();
        if (items.contains(cartItem)) {
            if (quantity == cartItem.getQuantity()) {
                items.remove(cartItem);
                cartItemService.delete(cartItem);
            } else {
                CartItem currentCartItem = items.get(items.indexOf(cartItem));
                if (quantity <= MIN_PERMISSIBLE_QUANTITY_ITEM || quantity > currentCartItem.getQuantity()) {
                    log.warn(INVALID_ITEM_QUANTITY_EXCEPTION + quantity);
                    throw new ServiceException(INVALID_ITEM_QUANTITY_EXCEPTION + quantity);
                }
                currentCartItem.setQuantity(currentCartItem.getQuantity() - quantity);
                cartItemService.save(currentCartItem);
            }
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
    public void updateAll() {
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            revalidateCartMetrics(cart);
        }
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
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
        save(cart);
    }

}
