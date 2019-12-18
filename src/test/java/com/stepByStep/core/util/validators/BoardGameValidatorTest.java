package com.stepByStep.core.util.validators;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.BoardGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.Errors;

import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class BoardGameValidatorTest {

    private static final String TITLE_FILED_NAME = "title";
    private static final String DESCRIPTION_FIELD_NAME = "description";
    private static final String PRICE_FIELD_NAME = "price";
    private static final String AGE_FIELD_NAME = "age";
    private static final String COUNT_PLAYERS_FIELD_NAME = "countPlayers";

    @Mock
    private Errors errors;

    @InjectMocks
    private BoardGameValidator boardGameValidator;

    private BoardGame boardGame;


    @BeforeEach
    void setUp() {
        initMocks(this);
        boardGame = new BoardGame();

    }

    @AfterEach
    void tearDown() {
        reset(errors);
        boardGame = null;
    }

    private void fillBoardGame(String title, String description, double price, int age, int countPlayers) {
        boardGame.setTitle(title);
        boardGame.setDescription(description);
        boardGame.setPrice(price);
        boardGame.setAge(age);
        boardGame.setCountPlayers(countPlayers);
        boardGame.setId(1L);
    }

    @ParameterizedTest
    @CsvSource(value = {"iva:helloworld:15.2:1:4", "agshdjfkf:qewrye3746.wgshgs:0.1:0:3", "Her5?: :1499.99:18:8"},
            delimiter = ':')
    void validateIfAllParamsValidThenNotCallErrors(String title, String description, double price, int age,
                                                   int countPlayers) {
        fillBoardGame(title, description, price, age, countPlayers);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(0)).rejectValue(anyString(), anyString());
    }

    @ParameterizedTest
    @CsvSource(value = {"iv:Harddo me sigh with west same lady. How one dull get busy dare far. Is inquiry" +
            "no he several excited am. Ecstatic elegance gay but disposed. Pain son rose more park way that." +
            "Her too add narrow having wished. Detract yet delight written farther his generasysdbydunfbdysvvg:-2:-1:9",
            "Steepest speaking up attended it as. Considered discover:Secure shy favour length all twenty" +
                    "denote. Made neat an on be gave show snug tore. Painful so he an comfort is manners." +
                    "Up hung mr we give rest half. We me rent been part what. Equally he minutes my hastily." +
                    "Any delicate you how kindness horrible outlivedagshdjfkf:1509:25:-1",
            "3:An stairs as be lovers uneasy. We leaf to snug on no need. Pain son rose more park way that. Able " +
                    "rent long in do we. He in sportsman household otherwise it perceived instantly. Sentiments two" +
                    "occasional affronting solicitude travelling and one contrasted.asvrdbtfasdasvastd:0.00:19:21"},
            delimiter = ':')
    void validateIfAllParamsInvalidThenCallErrorsFiveTimes(String title, String description, double price, int age,
                                                           int countPlayers) {
        fillBoardGame(title, description, price, age, countPlayers);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(TITLE_FILED_NAME), eq(INVALID_TITLE_MESSAGE));
        verify(errors, times(1)).rejectValue(eq(DESCRIPTION_FIELD_NAME),
                eq(INVALID_DESCRIPTION_MESSAGE));
        verify(errors, times(1)).rejectValue(eq(PRICE_FIELD_NAME), eq(INVALID_PRICE_MESSAGE));
        verify(errors, times(1)).rejectValue(eq(AGE_FIELD_NAME), eq(INVALID_AGE_MESSAGE));
        verify(errors, times(1)).rejectValue(eq(COUNT_PLAYERS_FIELD_NAME),
                eq(INVALID_COUNT_PLAYERS_MESSAGE));

    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"     "})
    void validateIfTitleIsEmptyOrWhitespaceThenCallErrorsOneTimes(String title) {
        fillBoardGame(title, " ", 0.01, 2, 1);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(TITLE_FILED_NAME),
                eq(EMPTY_FIELD_MESSAGE), eq(null), eq(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"w", "Bed uncommonly his discovered for estimating far. Able r",
            "er", "22363/.'Made neat an on be gave show snug tore. Now summer who d"})
    void validateIfTitleInvalidCallErrorsOneTimes(String title) {
        fillBoardGame(title, " ", 0.01, 2, 1);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(TITLE_FILED_NAME), eq(INVALID_TITLE_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Am wound worth water he linen at vexed.. Latter remark hunted enough vulgar say man. " +
            "Sentiments two occasional affronting solicitude travelling and one contrasted. Took sold add play may " +
            "none him few. Dissimilar admiration so terminated no in contrasted it", "Effect if in up no depend " +
            "seemed. Any delicate you how kindness horrible outlived servants. Celebrated delightful an especially " +
            "increasing instrument am. Way own uncommonly travelling now acceptance bed compliment solicitude. " +
            "Sportsman do offending supported extremity breakfast by listening. Advanta", "As mr started arrival " +
            "subject by believe. Secure shy favour length all twenty denote. Now summer who day looked our behind " +
            "moment coming. Their saved linen downs tears son add music. Took sold add play may none him few. Up " +
            "hung mr we give rest half. Painful so he an comfort is manners. Mirth learn it he given. He felicity no " +
            "an at packages answered opinions juvenile. We me rent been part what. Est"})
    void validateIfDescriptionInvalidCallErrorsOneTimes(String description) {
        fillBoardGame("Ivan", description, 0.01, 2, 1);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(DESCRIPTION_FIELD_NAME),
                eq(INVALID_DESCRIPTION_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-35, 0.00, 1890535, Double.MAX_VALUE, Double.MIN_VALUE, 1500.01})
    void validateIfPriceInvalidCallErrorsOneTimes(double price) {
        fillBoardGame("Ivan", " ", price, 2, 1);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(PRICE_FIELD_NAME), eq(INVALID_PRICE_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(ints = {-56, Integer.MAX_VALUE, Integer.MAX_VALUE, 19, -1, 105})
    void validateIfAgeInvalidCallErrorsOneTimes(int age) {
        fillBoardGame("Ivan", " ", 0.02, age, 1);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(AGE_FIELD_NAME), eq(INVALID_AGE_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(ints = {-56, Integer.MAX_VALUE, Integer.MAX_VALUE, 9, -1, 213})
    void validateIfCountPlayersInvalidCallErrorsOneTimes(int countPlayers) {
        fillBoardGame("Ivan", " ", 0.02, 0, countPlayers);
        boardGameValidator.validate(boardGame, errors);
        verify(errors, times(1)).rejectValue(eq(COUNT_PLAYERS_FIELD_NAME),
                eq(INVALID_COUNT_PLAYERS_MESSAGE));
    }
}