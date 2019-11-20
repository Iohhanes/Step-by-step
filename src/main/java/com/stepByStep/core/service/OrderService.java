package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.OrderItem;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.util.exceptions.ServiceException;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void delete(Order order);

    Order findById(Long orderId);

    List<Order> findAllByUser(User user);

    void place(User user, OrderItem orderItem, String name, String email, String phone)
            throws ServiceException;

}
