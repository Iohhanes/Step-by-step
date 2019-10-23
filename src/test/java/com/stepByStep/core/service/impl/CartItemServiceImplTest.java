package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartItemRepository;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
public class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;


    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @AfterEach
    public void cleanUp() {
        reset(cartItemRepository);
    }

    @Test
    public void createNewCartItemIfBoardGameNullThenThrowException() throws ServiceException {
        Assertions.assertThrows(ServiceException.class, () -> {
            cartItemService.createNewCartItem(null, 0);
        });

    }

    @Test
    public void createNewCartItemSuccessfulThenReturnNewCartItem() throws ServiceException {
        CartItem expected = new CartItem();
        expected.setQuantity(10);
        expected.setBoardGame(new BoardGame("chess", 10.5));
        Assertions.assertEquals(expected, cartItemService.
                createNewCartItem(new BoardGame("chess", 10.5), 10));
    }
}