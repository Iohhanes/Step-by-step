package com.stepByStep.core.util.validators;

import com.stepByStep.core.model.entity.BoardGame;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.stepByStep.core.util.constants.DataPermissibleConstant.*;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.*;

@Component
public class BoardGameValidator implements Validator {

    private static final String TITLE_FILED_NAME = "title";
    private static final String DESCRIPTION_FIELD_NAME = "description";
    private static final String PRICE_FIELD_NAME = "price";
    private static final String AGE_FIELD_NAME = "age";
    private static final String COUNT_PLAYERS_FIELD_NAME = "countPlayers";

    @Override
    public boolean supports(Class<?> aClass) {
        return BoardGame.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        BoardGame boardGame = (BoardGame) obj;
        validateTitle(boardGame, errors);
        validateDescription(boardGame,errors);
        validatePrice(boardGame,errors);
        validateAge(boardGame,errors);
        validateCountPlayers(boardGame,errors);

    }

    private void validateTitle(BoardGame boardGame, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, TITLE_FILED_NAME, EMPTY_FIELD_MESSAGE);
        if (boardGame.getTitle().length() < MIN_PERMISSIBLE_LENGTH_BOARD_GAME_TITLE &&
                boardGame.getTitle().length() > MAX_PERMISSIBLE_LENGTH_BOARD_GAME_TITLE) {
            errors.rejectValue(TITLE_FILED_NAME, INVALID_TITLE_MESSAGE);
        }
    }

    private void validateDescription(BoardGame boardGame, Errors errors) {
        if (boardGame.getDescription().length() > MAX_PERMISSIBLE_LENGTH_BOARD_GAME_DESCRIPTION) {
            errors.rejectValue(DESCRIPTION_FIELD_NAME, INVALID_DESCRIPTION_MESSAGE);
        }
    }

    private void validatePrice(BoardGame boardGame, Errors errors) {
        if (boardGame.getPrice() < MIN_PERMISSIBLE_BOARD_GAME_PRICE &&
                boardGame.getPrice() > MAX_PERMISSIBLE_BOARD_GAME_PRICE) {
            errors.rejectValue(PRICE_FIELD_NAME, INVALID_PRICE_MESSAGE);
        }
    }

    private void validateAge(BoardGame boardGame, Errors errors) {
        if (boardGame.getAge() < MIN_PERMISSIBLE_BOARD_GAME_AGE &&
                boardGame.getAge() > MAX_PERMISSIBLE_BOARD_GAME_AGE) {
            errors.rejectValue(AGE_FIELD_NAME, INVALID_AGE_MESSAGE);
        }
    }

    private void validateCountPlayers(BoardGame boardGame, Errors errors) {
        if (boardGame.getCountPlayers() < MIN_PERMISSIBLE_BOARD_GAME_COUNT_PLAYERS &&
                boardGame.getCountPlayers() > MAX_PERMISSIBLE_BOARD_GAME_COUNT_PLAYERS) {
            errors.rejectValue(COUNT_PLAYERS_FIELD_NAME, INVALID_COUNT_PLAYERS_MESSAGE);
        }
    }
}
