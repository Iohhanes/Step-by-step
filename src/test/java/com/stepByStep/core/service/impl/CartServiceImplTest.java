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
public class CartServiceImplTest {

    @Mock
    private CartItemService cartItemService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @AfterEach
    public void cleanUp() {
        reset(cartItemService);
        reset(cartRepository);
    }

    @Test
    public void extendUserCartIfItemNullThenThrowException() throws ServiceException {
        assertThrows(ServiceException.class, () -> {
            cartService.extendUserCart(new Cart(), null);
        });
        verify(cartItemService, times(0)).save(any(CartItem.class));
        verify(cartRepository, times(0)).save(any(Cart.class));
    }

    @Test
    public void extendUserCartIfItemContainsThenAddQuantityItem() throws ServiceException {
        Cart cart = new Cart();
        cart.getItems().add(new CartItem(cart, new BoardGame("chess", 10.5), 10));
        CartItem addedCartItem = new CartItem(null, new BoardGame("chess", 10.5), 10);
        cartService.extendUserCart(cart, addedCartItem);
        verify(cartItemService, times(1)).save(addedCartItem);
        verify(cartRepository, times(1)).save(cart);
        Cart expected = new Cart();
        expected.getItems().add(new CartItem(expected, new BoardGame("chess", 10.5), 20));
        expected.setTotalCountItems(20);
        expected.setTotalCost(210);
        assertEquals(expected, cart);
    }

    @Test
    public void extendUserCartIfItemNotContainsThenAddCartItem() throws ServiceException {
        Cart cart = new Cart();
        CartItem addedCartItem = new CartItem(null, new BoardGame("chess", 10.5), 10);
        cartService.extendUserCart(cart, addedCartItem);
        verify(cartItemService, times(1)).save(addedCartItem);
        verify(cartRepository, times(1)).save(cart);
        Cart expected = new Cart();
        expected.getItems().add(new CartItem(expected, new BoardGame("chess", 10.5), 10));
        expected.setTotalCountItems(10);
        expected.setTotalCost(105);
        assertEquals(expected, cart);
    }

    @Test
    public void reduceUserCartIfItemNullThenThrowException() throws ServiceException {
        Cart cart=new Cart();
        assertThrows(ServiceException.class, () -> {
            cartService.reduceUserCart(cart, null);
        });
        verify(cartItemService, times(0)).save(any(CartItem.class));
        verify(cartRepository, times(0)).save(cart);
    }

    @Test
    public void reduceUserCartIfItemContainsThenRemoveItem() throws ServiceException {
        Cart cart = new Cart();
        cart.getItems().add(new CartItem(cart, new BoardGame("chess", 10.5), 10));
        CartItem removedCartItem = new CartItem(cart, new BoardGame("chess", 10.5), 10);
        cartService.reduceUserCart(cart, removedCartItem);
        verify(cartItemService, times(1)).delete(removedCartItem);
        verify(cartRepository, times(1)).save(cart);
        assertEquals(new Cart(), cart);
    }

    @Test
    public void reduceUserCartIfItemNotContains() throws ServiceException {
        Cart cart = new Cart();
        CartItem removedCartItem = new CartItem(null, new BoardGame("chess", 10.5), 10);
        cartService.reduceUserCart(cart, removedCartItem);
        verify(cartItemService, times(0)).delete(any(CartItem.class));
        verify(cartRepository, times(0)).save(cart);
        assertEquals(new Cart(), cart);
    }

}