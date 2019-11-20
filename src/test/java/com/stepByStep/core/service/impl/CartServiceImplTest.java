package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.service.CartItemService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @AfterEach
    void cleanUp() {
        reset(cartItemService);
        reset(cartRepository);
    }

//    @Test
//    void extendUserCartIfItemNullThenThrowException() {
//        assertThrows(ServiceException.class, () -> {
//            cartService.extendUserCart(new Cart(), null);
//        });
//        verify(cartItemService, times(0)).save(any(CartItem.class));
//        verify(cartRepository, times(0)).save(any(Cart.class));
//    }
//
//    @Test
//    void extendUserCartIfItemContainsThenAddQuantityItem() throws ServiceException {
//        Cart cart = new Cart();
//        cart.getItems().add(new CartItem(cart, new BoardGame("chess", 10.5), 10));
//        CartItem addedCartItem = new CartItem(null, new BoardGame("chess", 10.5), 10);
//        cartService.extendUserCart(cart, addedCartItem);
//        verify(cartItemService, times(1)).save(addedCartItem);
//        verify(cartRepository, times(1)).save(cart);
//        Cart expected = new Cart();
//        expected.getItems().add(new CartItem(expected, new BoardGame("chess", 10.5), 20));
//        expected.setTotalCountItems(20);
//        expected.setTotalCost(210);
//        assertEquals(expected, cart);
//    }
//
//    @Test
//    void extendUserCartIfItemNotContainsThenAddCartItem() throws ServiceException {
//        Cart cart = new Cart();
//        CartItem addedCartItem = new CartItem(null, new BoardGame("chess", 10.5), 10);
//        cartService.extendUserCart(cart, addedCartItem);
//        verify(cartItemService, times(1)).save(addedCartItem);
//        verify(cartRepository, times(1)).save(cart);
//        Cart expected = new Cart();
//        expected.getItems().add(new CartItem(expected, new BoardGame("chess", 10.5), 10));
//        expected.setTotalCountItems(10);
//        expected.setTotalCost(105);
//        assertEquals(expected, cart);
//    }
//
//    @Test
//    void reduceUserCartIfItemNullThenThrowException() {
//        Cart cart = new Cart();
//        assertThrows(ServiceException.class, () -> {
//            cartService.reduceUserCart(cart, null, 0);
//        });
//        verify(cartItemService, times(0)).save(any(CartItem.class));
//        verify(cartRepository, times(0)).save(cart);
//    }
//
//    @Test
//    void reduceUserCartIfItemContainsThenRemoveItem() throws ServiceException {
//        Cart cart = new Cart();
//        cart.getItems().add(new CartItem(cart, new BoardGame("chess", 10.5), 10));
//        CartItem removedCartItem = new CartItem(cart, new BoardGame("chess", 10.5), 10);
//        cartService.reduceUserCart(cart, removedCartItem, 10);
//        verify(cartItemService, times(1)).delete(removedCartItem);
//        verify(cartRepository, times(1)).save(cart);
//        assertEquals(new Cart(), cart);
//    }
//
//    @Test
//    void reduceUserCartIfItemNotContains() throws ServiceException {
//        Cart cart = new Cart();
//        CartItem removedCartItem = new CartItem(null, new BoardGame("chess", 10.5), 10);
//        cartService.reduceUserCart(cart, removedCartItem, 10);
//        verify(cartItemService, times(0)).delete(any(CartItem.class));
//        verify(cartRepository, times(0)).save(cart);
//        assertEquals(new Cart(), cart);
//    }

    @ParameterizedTest
    @CsvSource(value = {"1:2147483634", "2147483634:1", "10:50346586", "566870870:78903", "1000000000:1000000000",
            "10:9", "2:1000000000", "12345:54321", "1:1"}, delimiter = ':')
    void extendUserCartCorrectDataAndCartIemExistThenSuccessfulExecute(int currentQuantity, int addedQuantity)
            throws ServiceException {
        Cart cart = new Cart();
        cart.getItems().add(new CartItem(cart, new BoardGame("chess", 10.5), currentQuantity));
        CartItem addedCartItem = new CartItem(null, new BoardGame("chess", 10.5), addedQuantity);
        cartService.extendUserCart(cart, addedCartItem);
        assertEquals(currentQuantity + addedQuantity, cart.getTotalCountItems());
    }

    @ParameterizedTest
    @CsvSource(value = {"1:2147483645", "2147483646:1", "1:2147483646", "2147483646:1", "459045:2147024603",
            "1000000000:2000000000", "11:2147483646", "2147483645:125", "2147483645:2147483646"}, delimiter = ':')
    void extendUserCartInvalidDataAndCartIemExistThenThrowException(int currentQuantity, int addedQuantity) {
        Cart cart = new Cart();
        cart.getItems().add(new CartItem(cart, new BoardGame("chess", 10.5), currentQuantity));
        CartItem addedCartItem = new CartItem(null, new BoardGame("chess", 10.5), addedQuantity);
        assertThrows(ServiceException.class, () -> {
            cartService.extendUserCart(cart, addedCartItem);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"1:1", "2147483645:1", "2147483645:2147483645", "10:1", "2147483645:78903",
            "1000000000:999999999", "456800:456789", "12345:12345", "999999:999998"}, delimiter = ':')
    void reduceUserCartCorrectDataThenSuccessfulExecute(int currentQuantity, int removedQuantity)
            throws ServiceException {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(null, new BoardGame("chess", 10.5), currentQuantity);
        cart.getItems().add(cartItem);
        cartService.reduceUserCart(cart, cartItem, removedQuantity);
        assertEquals(currentQuantity - removedQuantity, cart.getTotalCountItems());
    }

    @ParameterizedTest
    @CsvSource(value = {"1:2","1:-1", "2147483645:-2147483645", "1:10", "78903:2147483645", "999999999:1000000000",
            "456789:456800","2147483645:-12345","100:-999999999"}, delimiter = ':')
    void reduceUserCartInvalidDataThenThrowException(int currentQuantity, int removedQuantity) {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(null, new BoardGame("chess", 10.5), currentQuantity);
        cart.getItems().add(cartItem);
        assertThrows(ServiceException.class, () -> {
            cartService.reduceUserCart(cart, cartItem, removedQuantity);
        });
    }

}