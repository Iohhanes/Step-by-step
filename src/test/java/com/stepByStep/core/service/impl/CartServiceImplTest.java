package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class CartServiceImplTest {

    @Mock
    private CartItemService cartItemService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @After
    public void cleanUp() {
        reset(cartItemService);
        reset(cartRepository);
    }

    @Test(expected = ServiceException.class)
    public void extendUserCartIfItemNullThenThrowException() throws ServiceException {
        Cart cart = new Cart();
        cartService.extendUserCart(cart, null);
        verify(cartItemService, times(0)).save(any(CartItem.class));
        verify(cartRepository, times(0)).save(any(Cart.class));
        assertEquals(new Cart(), cart);
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

    @Test(expected = ServiceException.class)
    public void reduceUserCartIfItemNullThenThrowException() throws ServiceException {
        Cart cart = new Cart();
        cartService.reduceUserCart(cart, null);
        verify(cartItemService, times(0)).save(any(CartItem.class));
        verify(cartRepository, times(0)).save(cart);
        assertEquals(new Cart(), cart);
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