package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.repository.CartItemRepository;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @AfterEach
    void cleanUp() {
        reset(cartItemRepository);
    }

    @Test
    void createNewCartItemIfBoardGameNullThenThrowException() {
        assertThrows(ServiceException.class, () -> {
            cartItemService.createNewCartItem(null, 0);
        });

    }

//    @Test
//    void createNewCartItemSuccessfulThenReturnNewCartItem() throws ServiceException {
//        CartItem expected = new CartItem();
//        expected.setQuantity(10);
//        expected.setBoardGame(new BoardGame("chess", 10.5));
//        assertEquals(expected, cartItemService.
//                createNewCartItem(new BoardGame("chess", 10.5), 10));
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {1, Integer.MAX_VALUE - 2, 2, Integer.MAX_VALUE - 10, 1000000000, 1534678000, 457893210})
//    void createNewCartItemCorrectQuantityThenReturnNewCartItem(int quantity) throws ServiceException {
//        CartItem newCartItem = new CartItem();
//        BoardGame boardGame = new BoardGame("chess", 10.5);
//        newCartItem.setQuantity(quantity);
//        newCartItem.setBoardGame(boardGame);
//        assertEquals(newCartItem, cartItemService.createNewCartItem(boardGame, quantity));
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {0, Integer.MAX_VALUE - 1, Integer.MAX_VALUE, -50, -1000000, -234567890})
//    void createNewCartItemInvalidQuantityThenThrowException(int quantity) {
//        assertThrows(ServiceException.class,
//                () -> {
//                    cartItemService.createNewCartItem(new BoardGame("chess", 10.5), quantity);
//                });
//    }

}