package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void delete(Order order);

    Order findById(Long orderId);

    List<Order> findAll();

    List<Order> findAllByCustomer(User customer);

    void place(User user, Order order) throws NullParameterException, ServiceException;

    void changeStatus(Order order) throws NullParameterException;

}
