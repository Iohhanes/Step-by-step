package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.*;
import com.stepByStep.core.repository.OrderRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.OrderItemService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.*;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemService orderItemService;
    private CartService cartService;
    private CartItemService cartItemService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService,
                            CartService cartService, CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
        orderItemService.save(order.getOrderItem());
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
        orderItemService.delete(order.getOrderItem());
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
    public void place(User user, OrderItem orderItem, String name, String email, String phone)
            throws ServiceException {
        checkNullOrderBoardGame(orderItem);
        Set<Order> orders = user.getOrders();
        Order newOrder = Order.builder()
                .orderItem(orderItem)
                .name(name)
                .email(email)
                .phone(phone)
                .user(user)
                .build();
        orders.add(newOrder);
        cartService.reduceUserCart(user.getCart(), cartItemService.findByBoardGame(orderItem.getBoardGame()),
                orderItem.getQuantity());
        orderItemService.save(orderItem);
        orderRepository.save(newOrder);
    }

    private void checkNullOrderBoardGame(OrderItem orderItem) throws ServiceException {
        if (orderItem == null) {
            log.warn(ORDER_BOARD_GAME_IS_NULL_EXCEPTION);
            throw new ServiceException(ORDER_BOARD_GAME_IS_NULL_EXCEPTION);
        }
    }
}
