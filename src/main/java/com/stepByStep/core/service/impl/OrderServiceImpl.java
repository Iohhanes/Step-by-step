package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.OrderBoardGame;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.OrderRepository;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.OrderBoardGameService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    public static final String ORDER_BOARD_GAME_IS_NULL_EXCEPTION = "Input order board game is null";

    private OrderRepository orderRepository;
    private OrderBoardGameService orderBoardGameService;
    private CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderBoardGameService orderBoardGameService,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderBoardGameService = orderBoardGameService;
        this.cartService = cartService;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
        orderBoardGameService.save(order.getOrderBoardGame());
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
        orderBoardGameService.delete(order.getOrderBoardGame());
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
    public void place(User user, OrderBoardGame orderBoardGame, String name, String email, String phone)
            throws ServiceException {
        checkNullOrderBoardGame(orderBoardGame);
        Set<Order> orders = user.getOrders();
        Order newOrder = Order.builder()
                .orderBoardGame(orderBoardGame)
                .name(name)
                .email(email)
                .phone(phone)
                .build();
        if (!orders.contains(newOrder)) {
            newOrder.setUser(user);
            orders.add(newOrder);
            CartItem cartItem = user.getCart().getItems().get(user.getCart().getItems().indexOf(orderBoardGame.getBoardGame()));
            orderBoardGameService.save(orderBoardGame);
            orderRepository.save(newOrder);
        }
    }

    private void checkNullOrderBoardGame(OrderBoardGame orderBoardGame) throws ServiceException {
        if (orderBoardGame == null) {
            log.warn(ORDER_BOARD_GAME_IS_NULL_EXCEPTION);
            throw new ServiceException(ORDER_BOARD_GAME_IS_NULL_EXCEPTION);
        }
    }
}
