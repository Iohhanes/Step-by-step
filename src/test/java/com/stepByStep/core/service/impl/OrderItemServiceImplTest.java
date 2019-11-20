package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.OrderItem;
import com.stepByStep.core.repository.OrderItemRepository;
import com.stepByStep.core.util.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(orderItemRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, Integer.MAX_VALUE - 2, 2, Integer.MAX_VALUE - 10, 1000000000, 1534678000, 457893210, 50,
            1255})
    void createNewOrderItemCorrectQuantityThenReturnNewOrderItem(int quantity) throws ServiceException {
        BoardGame boardGame = new BoardGame("chess", 10.5);
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setQuantity(quantity);
        newOrderItem.setBoardGame(boardGame);
        assertEquals(newOrderItem.getQuantity(),
                orderItemService.
                        createNewOrderItem(new CartItem(null, boardGame, quantity), quantity).getQuantity());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, Integer.MAX_VALUE - 1, Integer.MAX_VALUE, -50, -1000000, -234567890, -1500, -1,
            -Integer.MAX_VALUE})
    void createNewOrderBoardGameInvalidQuantityThenThrowException(int quantity) {
        assertThrows(ServiceException.class,
                () -> {
                    orderItemService.createNewOrderItem(
                            new CartItem(null, new BoardGame("chess", 10.5), 10), quantity);
                });
    }
}