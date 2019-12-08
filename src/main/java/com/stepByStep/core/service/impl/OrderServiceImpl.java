package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.OrderRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_ORDER_IS_NULL_EXCEPTION;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private CartService cartService;
    private CartItemService cartItemService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService,
                            CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public void place(User user, Order order) throws NullParameterException, ServiceException {
        ShopElementIsNullChecker.checkNull(order, new NullParameterException(INPUT_ORDER_IS_NULL_EXCEPTION));
        Set<Order> orders = user.getOrders();
        orders.add(order);
        CartItem cartItem = cartItemService.findByBoardGame(order.getBoardGame());
        cartService.reduceUserCart(user.getCart(), cartItem, order.getQuantity());
        orderRepository.save(order);
    }

}
