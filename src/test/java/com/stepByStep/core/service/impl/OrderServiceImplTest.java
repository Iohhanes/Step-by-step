package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.OrderStatus;
import com.stepByStep.core.repository.OrderRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.util.exceptions.NullParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class OrderServiceImplTest {

    @Mock
    private CartItemService cartItemService;

    @Mock
    private CartService cartService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        initMocks(this);
        order = Order.builder()
                .boardGame(new BoardGame("chess", 10.5))
                .customerName("Ivan")
                .customerPhone("3753759")
                .customerEmail("qwerty@mail.ru")
                .quantity(20)
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(cartItemService,cartService,orderRepository);
        order = null;
    }

    @Test
    void changeStatusIfOrderIsNullThenThrowException() {
        assertThrows(NullParameterException.class, () -> {
            orderService.changeStatus(null);
        }, "This order is null");
    }

    @Test
    void changeStatusIfOrderStatusIsDeliveredThenOrderStatusHasNotBeenChanged() throws NullParameterException {
        order.setStatus(OrderStatus.DELIVERED);
        orderService.changeStatus(order);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    void changeStatusIfOrderStatusIsDeliveredThenOrderStatusHasBeenChanged() throws NullParameterException {
        orderService.changeStatus(order);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }
}