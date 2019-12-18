package com.stepByStep.core.util.validators;

import com.stepByStep.core.model.entity.Order;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

import static com.stepByStep.core.util.constants.DataPermissibleConstant.MAX_PERMISSIBLE_LENGTH_ORDER_CUSTOMER_EMAIL;
import static com.stepByStep.core.util.constants.DataPermissibleConstant.MIN_PERMISSIBLE_LENGTH_ORDER_CUSTOMER_EMAIL;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.*;

@Component
public class OrderValidator implements Validator {

    private static final String CUSTOMER_NAME_IN_ORDER_FIELD_NAME = "customerName";
    private static final String CUSTOMER_EMAIL_IN_ORDER_FIELD_NAME = "customerEmail";
    private static final String CUSTOMER_PHONE_IN_ORDER_FIELD_NAME = "customerPhone";

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Order order = (Order) obj;
        validateCustomerName(order, errors);
        validateCustomerEmail(order, errors);
        validateCustomerPhone(order, errors);
    }

    private void validateCustomerName(Order order, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CUSTOMER_NAME_IN_ORDER_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        Pattern patternName = Pattern.compile("[a-zA-Z]{3,20}");
        if (!patternName.matcher(order.getCustomerName()).matches()) {
            errors.rejectValue(CUSTOMER_NAME_IN_ORDER_FIELD_NAME, INVALID_CUSTOMER_NAME_IN_ORDER_MESSAGE);
        }
    }

    private void validateCustomerEmail(Order order, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CUSTOMER_EMAIL_IN_ORDER_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        if (!EmailValidator.getInstance(true).isValid(order.getCustomerEmail()) ||
                order.getCustomerEmail().length() < MIN_PERMISSIBLE_LENGTH_ORDER_CUSTOMER_EMAIL ||
                order.getCustomerEmail().length() > MAX_PERMISSIBLE_LENGTH_ORDER_CUSTOMER_EMAIL) {
            errors.rejectValue(CUSTOMER_EMAIL_IN_ORDER_FIELD_NAME, INVALID_CUSTOMER_EMAIL_IN_ORDER_MESSAGE);
        }
    }

    private void validateCustomerPhone(Order order, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CUSTOMER_PHONE_IN_ORDER_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        Pattern patternPhone = Pattern.compile("[0-9]{7}");
        if (!patternPhone.matcher(order.getCustomerPhone()).matches()) {
            errors.rejectValue(CUSTOMER_PHONE_IN_ORDER_FIELD_NAME, INVALID_CUSTOMER_PHONE_IN_ORDER_MESSAGE);
        }
    }
}
