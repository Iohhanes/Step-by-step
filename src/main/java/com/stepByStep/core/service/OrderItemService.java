package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.OrderItem;
import com.stepByStep.core.util.exceptions.ServiceException;

public interface OrderItemService {

    void save(OrderItem orderItem);

    void delete(OrderItem orderItem);

    OrderItem createNewOrderItem(CartItem cartItem, int quantity) throws ServiceException;

}
