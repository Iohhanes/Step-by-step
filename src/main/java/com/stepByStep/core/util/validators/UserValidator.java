package com.stepByStep.core.util.validators;

import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.*;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private static final String USERNAME_FIELD_NAME = "username";
    private static final String SIGN_FIELD_NAME = "password";

    private UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        validateUsername(user, errors);
        validatePassword(user, errors);
    }

    private void validateUsername(User user, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, USERNAME_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        Pattern patternUserName = Pattern.compile("[a-zA-Z0-9_]{6,20}");
        Pattern nonPatternUsername = Pattern.compile("[0-9_]{6,20}");
        if (nonPatternUsername.matcher(user.getUsername()).matches() ||
                !patternUserName.matcher(user.getUsername()).matches()) {
            errors.rejectValue(USERNAME_FIELD_NAME, INVALID_USERNAME_MESSAGE);
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(USERNAME_FIELD_NAME, DUPLICATE_USERNAME_MESSAGE);
        }
    }

    private void validatePassword(User user, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, SIGN_FIELD_NAME, EMPTY_FIELD_MESSAGE);
        Pattern patternPassword = Pattern.compile("[a-zA-Z0-9]{8,20}");
        if (!patternPassword.matcher(user.getPassword()).matches()) {
            errors.rejectValue(SIGN_FIELD_NAME, INVALID_SIGN_MESSAGE);
        }
    }

}
