package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.AppConfig;
import com.stepByStep.core.config.PersistenceTestConfig;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {PersistenceTestConfig.class, AppConfig.class})
@Sql(value = {"/sql/create_board_games_before.sql", "/sql/create_users_before.sql","/sql/create_carts_before.sql",
        "/sql/create_cart_items_before.sql", "/sql/update_hibernate_sequence_after.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete_board_games_after.sql", "/sql/delete_cart_items_after.sql", "/sql/delete_carts_after.sql",
        "/sql/delete_orders_after.sql","/sql/delete_users_after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class OrderServiceImplIntegrationTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private CartItemServiceImpl cartItemService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BoardGameServiceImpl boardGameService;

    private User customer;
    private Order order;

    @BeforeEach
    void setUp() {
        customer = userService.findById(2L);
        order = Order.builder()
                .customer(customer)
                .customerName("Ivan")
                .customerEmail("email")
                .customerPhone("3759375")
                .build();
    }

    @AfterEach
    void tearDown() {
        customer = null;
        order = null;
    }

    @Test
    void placeIfOrderIsNullThenThrowException(){
        assertThrows(NullParameterException.class,()->{
            orderService.place(customer,null);
        },INPUT_ORDER_IS_NULL_EXCEPTION);
    }

    @Test
    void placeIfBoardGameIsNullThenThrowException() {
        order.setBoardGame(null);
        assertThrows(NullParameterException.class, () -> {
            orderService.place(customer, order);
        }, INPUT_CART_ITEM_IS_NULL_EXCEPTION);
    }

    @Test
    void findByBoardGameIfBoardGameIsNullThenReturnNull() {
        assertNull(cartItemService.findByBoardGame(null));
    }

    @Test
    void placeIfCartItemQuantityEqualsOrderItemQuantityThenOrderPlaced()
            throws NullParameterException, ServiceException {
        order.setBoardGame(boardGameService.findById(1L));
        order.setQuantity(10);
        orderService.place(customer, order);
        int actual = orderService.findAllByCustomer(customer).size();
        assertEquals(1, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    void findByBoardGameIfBoardGameIsNotNullThenReturnCartItem(long boardGameId) {
        assertNotNull(cartItemService.findByBoardGame(boardGameService.findById(boardGameId)));
    }


    @Test
    void placeIfOrderQuantityOffBoundThenThrowException() {
        order.setBoardGame(boardGameService.findById(1L));
        order.setQuantity(20);
        assertThrows(ServiceException.class, () -> {
            orderService.place(customer, order);
        }, INVALID_ITEM_QUANTITY_EXCEPTION + 20);
    }

    @Test
    void reduceUserCartIfInvalidQuantityThenThrowException() {
        assertThrows(ServiceException.class, () -> {
            cartService.reduceUserCart(cartService.findById(1L),
                    cartItemService.findByBoardGame(boardGameService.findById(1L)), 20);
        }, INVALID_ITEM_QUANTITY_EXCEPTION + 20);
    }

    @Test
    void placeIfCartItemQuantityNotEqualsOrderItemQuantityThenOrderPlaced()
            throws NullParameterException, ServiceException {
        order.setBoardGame(boardGameService.findById(2L));
        order.setQuantity(3);
        orderService.place(customer, order);
        int actual = orderService.findAllByCustomer(customer).size();
        assertEquals(1, actual);
    }

    @Test
    void deleteIfValidCartItemThenDeletedSuccessful() {
        CartItem cartItem = cartItemService.findById(1L);
        cartItemService.delete(cartItem);
        int actualSize = cartItemService.findAll().size();
        assertEquals(1, actualSize);

    }

    @Test
    void reduceUserCartIfValidDataThenCartParametersUpdated() throws NullParameterException, ServiceException {
        Cart cart = cartService.findById(1L);
        cartService.reduceUserCart(cart,
                cartItemService.findByBoardGame(boardGameService.findById(1L)), 10);
        assertArrayEquals(new double[]{20, 246}, new double[]{cart.getTotalCountItems(), cart.getTotalCost()});
    }
}