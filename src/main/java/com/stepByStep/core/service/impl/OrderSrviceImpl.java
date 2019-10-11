package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.OrderRepository;
import com.stepByStep.core.service.OrderBoardGameService;
import com.stepByStep.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSrviceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderBoardGameService orderBoardGameService;

    @Autowired
    public OrderSrviceImpl(OrderRepository orderRepository, OrderBoardGameService orderBoardGameService) {
        this.orderRepository = orderRepository;
        this.orderBoardGameService = orderBoardGameService;
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
    public void updateStatus(Order order) {

    }
}
