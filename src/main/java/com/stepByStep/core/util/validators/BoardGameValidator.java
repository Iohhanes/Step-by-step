package com.stepByStep.core.util.validators;

import com.stepByStep.core.model.entity.BoardGame;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BoardGameValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return BoardGame.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
