package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.OrderStatus;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.OrderRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_ORDER_IS_NULL_EXCEPTION;

@Log4j2
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
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByCustomer(User customer) {
        return orderRepository.findAllByCustomer(customer);
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

    @Override
    public void changeStatus(Order order) throws NullParameterException {
        ShopElementIsNullChecker.checkNull(order, new NullParameterException(INPUT_ORDER_IS_NULL_EXCEPTION));
        if (order.getStatus() == OrderStatus.WAITED_DELIVERY) {
            order.setStatus(OrderStatus.DELIVERED);
        }
        save(order);
    }
}
