package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.repository.BoardGameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class BoardGameServiceImplTest {


    @Mock
    private BoardGameRepository boardGameRepository;

    @InjectMocks
    private BoardGameServiceImpl boardGameService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(boardGameRepository);
    }

    @Test
    void editIfFilenameIsNullThenEditCurrentBoardGameWithoutChangingFilename() {

        BoardGame boardGame = BoardGame.builder()
                .title("Chess")
                .price(10)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("chess.jpg")
                .build();
        boardGame.setId(1L);
        BoardGame boardGameForm = BoardGame.builder()
                .title("Chess")
                .price(15)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename(null)
                .build();
        boardGameForm.setId(1L);
        BoardGame boardGameResult = BoardGame.builder()
                .title("Chess")
                .price(15)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("chess.jpg")
                .build();
        boardGameResult.setId(1L);
        boardGameService.edit(boardGame, boardGameForm);
        assertEquals(boardGameResult, boardGame);
    }

    @Test
    public void editIfFilenameNotNullThenEditCurrentBoardGameWithChangingFilename() {
        BoardGame boardGame = BoardGame.builder()
                .title("Chess")
                .price(10)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("chess.jpg")
                .build();
        boardGame.setId(1L);
        BoardGame boardGameForm = BoardGame.builder()
                .title("Chess")
                .price(15)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("dota.jpg")
                .build();
        boardGameForm.setId(1L);
        BoardGame boardGameResult = BoardGame.builder()
                .title("Chess")
                .price(15)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("dota.jpg")
                .build();
        boardGameResult.setId(1L);
        boardGameService.edit(boardGame, boardGameForm);
        assertEquals(boardGameResult, boardGame);
    }

    @Test
    void findByFiltersIfTitleIsEmptyAndPriceIsNullAndAgeIsNullAndCountOfPlayersIsNullThenReturnAllBoardGames() {
        BoardGame boardGame1 = BoardGame.builder()
                .title("Chess")
                .price(10)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("chess.jpg")
                .build();
        boardGame1.setId(1L);
        BoardGame boardGame2 = BoardGame.builder()
                .title("Doka 2")
                .price(15)
                .description(null)
                .countPlayers(4)
                .age(18)
                .filename("dota.jpg")
                .build();
        boardGame2.setId(2L);
        BoardGame boardGame3 = BoardGame.builder()
                .title("Monopoly")
                .price(20)
                .description(null)
                .countPlayers(6)
                .age(5)
                .filename("monopoly.jpg")
                .build();
        boardGame3.setId(3L);
        List<BoardGame> boardGamesExpected = new ArrayList<>();
        boardGamesExpected.add(boardGame1);
        boardGamesExpected.add(boardGame2);
        boardGamesExpected.add(boardGame3);
        when(boardGameRepository.findAll()).thenReturn(boardGamesExpected);
        List<BoardGame> boardGamesActual = boardGameService.findByFilters("", null, null, null);
        assertEquals(boardGamesExpected, boardGamesActual);
    }

    @Test
    void findByFiltersIfTitleIsNotEmptyThenReturnBoardGamesByFilters() {
        BoardGame boardGame1 = BoardGame.builder()
                .title("Chess")
                .price(10)
                .description(null)
                .countPlayers(4)
                .age(10)
                .filename("chess.jpg")
                .build();
        boardGame1.setId(1L);
        List<BoardGame> boardGamesExpected = new ArrayList<>();
        boardGamesExpected.add(boardGame1);
        when(boardGameRepository.findByFilters("Chess", null, null, null))
                .thenReturn(boardGamesExpected);
        List<BoardGame> boardGamesActual = boardGameService
                .findByFilters("Chess", null, null, null);
        assertEquals(boardGamesExpected, boardGamesActual);
    }
}