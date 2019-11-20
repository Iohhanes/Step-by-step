package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.OrderItem;
import com.stepByStep.core.repository.OrderItemRepository;
import com.stepByStep.core.service.OrderItemService;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.stepByStep.core.util.constants.DataPermissibleConstant.*;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.*;

@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    @Override
    public OrderItem createNewOrderItem(CartItem cartItem, int quantity) throws ServiceException {
        if (quantity <= MIN_PERMISSIBLE_QUANTITY_ITEM || quantity >= MAX_PERMISSIBLE_QUANTITY_ITEM) {
            log.warn(INVALID_ITEM_QUANTITY_EXCEPTION + quantity);
            throw new ServiceException(INVALID_ITEM_QUANTITY_EXCEPTION + quantity);
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setBoardGame(cartItem.getBoardGame());
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}
