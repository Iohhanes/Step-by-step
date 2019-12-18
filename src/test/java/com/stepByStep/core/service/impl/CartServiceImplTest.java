package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_CART_ITEM_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INVALID_ITEM_QUANTITY_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class CartServiceImplTest {

    @Mock
    private CartItemService cartItemService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private BoardGame boardGame;

    private Cart cart;

    private final int quantity = 10;

    private final long id = 1L;

    @BeforeEach
    void setUp() {
        initMocks(this);
        boardGame = new BoardGame("chess", 10.5);
        cart = new Cart();
        cart.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(cartItemService,cartRepository);
        boardGame = null;
        cart = null;
    }

    @Test
    void extendUserCartIfCartItemIsNullThenThrowException() {
        assertThrows(NullParameterException.class, () -> {
            cartService.extendUserCart(cart, null);
        }, INPUT_CART_ITEM_IS_NULL_EXCEPTION);
    }

    @Test
    void extendUserCartIfCartItemContainsThenAddQuantityItem() throws ServiceException, NullParameterException {
        cart.getItems().add(new CartItem(cart, boardGame, quantity));
        CartItem addedCartItem = new CartItem(null, boardGame, quantity);
        cartService.extendUserCart(cart, addedCartItem);
        Cart expectedCart = new Cart();
        int expectedQuantity = 20;
        expectedCart.getItems().add(new CartItem(expectedCart, boardGame, expectedQuantity));
        expectedCart.setTotalCountItems(expectedQuantity);
        expectedCart.setTotalCost(expectedQuantity * boardGame.getPrice());
        expectedCart.setId(id);
        assertEquals(expectedCart, cart);
    }

    @Test
    void extendUserCartIfInvalidQuantityThenThrowException() {
        int invalidQuantity = -20;
        cart.getItems().add(new CartItem(cart, boardGame, quantity));
        CartItem addedCartItem = new CartItem(null, boardGame, invalidQuantity);
        assertThrows(ServiceException.class, () -> {
            cartService.extendUserCart(cart, addedCartItem);
        }, INVALID_ITEM_QUANTITY_EXCEPTION + invalidQuantity);
    }

    @Test
    void extendUserCartIfCartItemDoesNotContainsThenAddCartItem() throws ServiceException, NullParameterException {
        CartItem addedCartItem = new CartItem(null, boardGame, quantity);
        cartService.extendUserCart(cart, addedCartItem);
        Cart expectedCart = new Cart();
        expectedCart.getItems().add(new CartItem(expectedCart, boardGame, quantity));
        expectedCart.setTotalCountItems(quantity);
        expectedCart.setTotalCost(boardGame.getPrice() * quantity);
        expectedCart.setId(id);
        assertEquals(expectedCart, cart);
    }

    @Test
    void reduceUserCartIfCartItemIfNullThenThrowException() {
        assertThrows(NullParameterException.class, () -> {
            cartService.reduceUserCart(cart, null, quantity);
        }, INPUT_CART_ITEM_IS_NULL_EXCEPTION);
    }


    @Test
    void reduceUserCartIfCartItemContainsAndQuantityEqualsCartItemQuantityThenRemoveItem() throws ServiceException,
            NullParameterException {
        cart.getItems().add(new CartItem(cart, boardGame, quantity));
        CartItem removedCartItem = new CartItem(cart, boardGame, quantity);
        cartService.reduceUserCart(cart, removedCartItem, quantity);
        Cart expectedCart = new Cart();
        expectedCart.setId(id);
        assertEquals(expectedCart, cart);
    }

    @Test
    void reduceUserCartIfInvalidQuantityThenThrowException() {
        cart.getItems().add(new CartItem(cart, boardGame, quantity));
        CartItem removedCartItem = new CartItem(cart, boardGame, quantity);
        int invalidQuantity = 20;
        assertThrows(ServiceException.class, () -> {
            cartService.reduceUserCart(cart, removedCartItem, invalidQuantity);
        }, INVALID_ITEM_QUANTITY_EXCEPTION + invalidQuantity);
    }

    @Test
    void reduceUserCartIfCartItemContainsAndQuantityDoesNotEqualsCartItemQuantityThenRemoveItem()
            throws ServiceException, NullParameterException {
        cart.getItems().add(new CartItem(cart, boardGame, quantity));
        CartItem removedCartItem = new CartItem(cart, boardGame, quantity);
        int validQuantity = 5;
        cartService.reduceUserCart(cart, removedCartItem, validQuantity);
        Cart expectedCart = new Cart();
        int newQuantity = quantity - validQuantity;
        expectedCart.getItems().add(new CartItem(expectedCart, boardGame, newQuantity));
        expectedCart.setId(id);
        expectedCart.setTotalCountItems(newQuantity);
        expectedCart.setTotalCost(boardGame.getPrice() * newQuantity);
        assertEquals(expectedCart, cart);
    }

    @Test
    void reduceUserCartIfCartItemDoesNotContains() throws ServiceException, NullParameterException {
        CartItem removedCartItem = new CartItem(null, boardGame, quantity);
        cartService.reduceUserCart(cart, removedCartItem, quantity);
        Cart expectedCart = new Cart();
        expectedCart.setId(id);
        assertEquals(expectedCart, cart);
    }

//    @Test
//    void clearUserCartSuccessful() {
//        CartItem removedCartItem = new CartItem(cart, boardGame, quantity);
//        cart.getItems().add(removedCartItem);
//        cart.setTotalCost(boardGame.getPrice() * quantity);
//        cart.setTotalCountItems(quantity);
//        cartService.clearUserCart(cart);
//        Cart expectedCart = new Cart();
//        expectedCart.setId(id);
//        assertEquals(expectedCart, cart);
//    }

    @Test
    void updateAllIfCartListIsEmptyThenCartListNotUpdated() {
        List<Cart> carts = new ArrayList<>();
        when(cartRepository.findAll()).thenReturn(carts);
        cartService.updateAll();
        List<Cart> expectedCarts = Collections.emptyList();
        assertEquals(expectedCarts, carts);
    }

    @Test
    void updateAllIfCartListIsNotEmptyThenCartListUpdated() {
        List<Cart> carts = new ArrayList<>();
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        int actualQuantityCartItem1 = 4;
        int actualQuantityCartItem2 = 5;
        CartItem cartItem1 = new CartItem(cart1, boardGame, actualQuantityCartItem1);
        CartItem cartItem2 = new CartItem(cart2, boardGame, actualQuantityCartItem2);
        cart1.getItems().add(cartItem1);
        cart2.getItems().add(cartItem2);
        cart1.setTotalCountItems(quantity);
        cart1.setTotalCost(quantity * boardGame.getPrice());
        cart1.setId(1L);
        cart2.setTotalCountItems(quantity);
        cart2.setTotalCost(quantity * boardGame.getPrice());
        cart2.setId(2L);
        carts.add(cart1);
        carts.add(cart2);
        when(cartRepository.findAll()).thenReturn(carts);
        cartService.updateAll();
        List<Cart> expectedCarts = new ArrayList<>();
        Cart expectedCart1 = new Cart();
        Cart expectedCart2 = new Cart();
        CartItem expectedCartItem1 = new CartItem(expectedCart1, boardGame, actualQuantityCartItem1);
        CartItem expectedCartItem2 = new CartItem(expectedCart2, boardGame, actualQuantityCartItem2);
        expectedCart1.getItems().add(expectedCartItem1);
        expectedCart2.getItems().add(expectedCartItem2);
        expectedCart1.setTotalCountItems(actualQuantityCartItem1);
        expectedCart1.setTotalCost(actualQuantityCartItem1 * boardGame.getPrice());
        expectedCart1.setId(1L);
        expectedCart2.setTotalCountItems(actualQuantityCartItem2);
        expectedCart2.setTotalCost(actualQuantityCartItem2 * boardGame.getPrice());
        expectedCart2.setId(2L);
        expectedCarts.add(expectedCart1);
        expectedCarts.add(expectedCart2);
        assertEquals(expectedCarts, carts);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:2147483634", "2147483634:1", "10:50346586", "566870870:78903", "1000000000:1000000000",
            "10:9", "2:1000000000", "12345:54321", "1:1"}, delimiter = ':')
    void extendUserCartCorrectDataAndCartIemExistThenSuccessfulExecute(int currentQuantity, int addedQuantity)
            throws ServiceException, NullParameterException {
        cart.getItems().add(new CartItem(cart, boardGame, currentQuantity));
        CartItem addedCartItem = new CartItem(null, boardGame, addedQuantity);
        cartService.extendUserCart(cart, addedCartItem);
        assertEquals(currentQuantity + addedQuantity, cart.getTotalCountItems());
    }

    @ParameterizedTest
    @CsvSource(value = {"1:2147483645", "2147483646:1", "1:2147483646", "2147483646:1", "459045:2147024603",
            "1000000000:2000000000", "11:2147483646", "2147483645:125", "2147483645:2147483646"}, delimiter = ':')
    void extendUserCartInvalidDataAndCartIemExistThenThrowException(int currentQuantity, int addedQuantity) {
        cart.getItems().add(new CartItem(cart, boardGame, currentQuantity));
        CartItem addedCartItem = new CartItem(null, boardGame, addedQuantity);
        assertThrows(ServiceException.class, () -> {
            cartService.extendUserCart(cart, addedCartItem);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"1:1", "2147483645:1", "2147483645:2147483645", "10:1", "2147483645:78903",
            "1000000000:999999999", "456800:456789", "12345:12345", "999999:999998"}, delimiter = ':')
    void reduceUserCartCorrectDataThenSuccessfulExecute(int currentQuantity, int removedQuantity)
            throws ServiceException, NullParameterException {
        CartItem cartItem = new CartItem(null, boardGame, currentQuantity);
        cart.getItems().add(cartItem);
        cartService.reduceUserCart(cart, cartItem, removedQuantity);
        assertEquals(currentQuantity - removedQuantity, cart.getTotalCountItems());
    }

    @ParameterizedTest
    @CsvSource(value = {"1:2", "1:-1", "2147483645:-2147483645", "1:10", "78903:2147483645", "999999999:1000000000",
            "456789:456800", "2147483645:-12345", "100:-999999999"}, delimiter = ':')
    void reduceUserCartInvalidDataThenThrowException(int currentQuantity, int removedQuantity) {
        CartItem cartItem = new CartItem(null, boardGame, currentQuantity);
        cart.getItems().add(cartItem);
        assertThrows(ServiceException.class, () -> {
            cartService.reduceUserCart(cart, cartItem, removedQuantity);
        });
    }

}