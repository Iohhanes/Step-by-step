package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartItemRepository;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;


    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @After
    public void cleanUp(){
        reset(cartItemRepository);
    }

    @Test(expected = ServiceException.class)
    public void createNewCartItemIfBoardGameNullThenThrowException() throws ServiceException {
        cartItemService.createNewCartItem(null, 0);
    }

    @Test
    public void createNewCartItemSuccessfulThenReturnNewCartItem() throws ServiceException{
        CartItem expected=new CartItem();
        expected.setQuantity(10);
        expected.setBoardGame(new BoardGame("chess",10.5));
        Assert.assertEquals(expected,cartItemService.
                createNewCartItem(new BoardGame("chess",10.5),10));
    }
}