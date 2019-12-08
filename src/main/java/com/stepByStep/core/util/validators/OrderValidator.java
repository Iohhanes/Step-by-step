package com.stepByStep.core.util.validators;

import com.stepByStep.core.model.entity.Order;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.*;

@Component
public class OrderValidator implements Validator {

    private static final String NAME_IN_ORDER_FIELD_NAME = "name";
    private static final String EMAIL_IN_ORDER_FIELD_NAME = "email";
    private static final String PHONE_IN_ORDER_FIELD_NAME = "phone";


    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Order order = (Order) obj;
        validateName(order, errors);
        validateEmail(order, errors);
        validatePhone(order, errors);
    }

    public void validateQuantity(){

    }

    private void validateName(Order order, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, NAME_IN_ORDER_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        Pattern patternName = Pattern.compile("[a-zA-Z]{3,20}");
        if (!patternName.matcher(order.getName()).matches()) {
            errors.rejectValue(NAME_IN_ORDER_FIELD_NAME, INVALID_NAME_IN_ORDER_MESSAGE);
        }
    }

    private void validateEmail(Order order, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL_IN_ORDER_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        if (!EmailValidator.getInstance(true).isValid(order.getEmail())) {
            errors.rejectValue(EMAIL_IN_ORDER_FIELD_NAME, INVALID_EMAIL_IN_ORDER_MESSAGE);
        }
    }

    private void validatePhone(Order order, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PHONE_IN_ORDER_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        Pattern patternPhone = Pattern.compile("[0-9]{7}");
        if (!patternPhone.matcher(order.getPhone()).matches()) {
            errors.rejectValue(PHONE_IN_ORDER_FIELD_NAME, INVALID_PHONE_IN_ORDER_MESSAGE);
        }
    }
}
