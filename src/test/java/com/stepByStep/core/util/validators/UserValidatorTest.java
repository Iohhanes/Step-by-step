package com.stepByStep.core.util.validators;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.UserRepository;
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
class UserValidatorTest {

    private static final String USERNAME_FIELD_NAME = "username";
    private static final String SIGN_FIELD_NAME = "password";

    @Mock
    private UserRepository userRepository;

    @Mock
    private Errors errors;

    @InjectMocks
    private UserValidator userValidator;

    private User user;

    @BeforeEach
    void setUp() {
        initMocks(this);
        user = new User();
    }

    @AfterEach
    void tearDown() {
        reset(userRepository, errors);
        user = null;
    }

    @ParameterizedTest
    @CsvSource(value = {"ivanMA:6114516v", "qwertyQWERTYqwertyAB:abs9absHe5absHello10", "iohhanes:IVAN90ivan",
            "12IvanMA:helloWorld1999", "ivan_MA:IvAnMAZURENKA", "_i_v_a_n_1999:qwertyASDFGHJK", "12345I78:12345IV9",
            "IllBeBack2000:61145169"}, delimiter = ':')
    void validateCorrectUsernameAndCorrectPasswordThenNotCallErrors(String username,String password){
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(0)).rejectValue(eq(USERNAME_FIELD_NAME), anyString());
        verify(errors, times(0)).rejectValue(eq(SIGN_FIELD_NAME), anyString());
    }


    @ParameterizedTest
    @ValueSource(strings = {"ivanMA", "qwertyQWERTYqwertyAB", "iohhanes", "12IvanMA", "ivan_MA", "_i_v_a_n_1999",
            "12345I78", "IllBeBack2000"})
    void validateCorrectUsernameThenNotCallErrors(String username) {
        user.setUsername(username);
        user.setPassword("6114516v");
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(0)).rejectValue(eq(USERNAME_FIELD_NAME), anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"6114516v", "abs9absHe5absHello10", "IVAN90ivan", "helloWorld1999", "IvAnMAZURENKA",
            "qwertyASDFGHJK", "12345IV9", "61145169"})
    void validateCorrectPasswordThenNotCallErrors(String password) {
        user.setUsername("ivanMa");
        user.setPassword(password);
        when(userRepository.findByUsername("ivanMa")).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(0)).rejectValue(eq(SIGN_FIELD_NAME), anyString());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"ivan7", "qWertgh7Ivan9prtfK90I", "123456", "_/.^7@#cy", "________", "      "})
    void validateInvalidUsernameThenCallErrorsOneTimes(String username) {
        user.setUsername(username);
        user.setPassword("6114516v");
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq(USERNAME_FIELD_NAME),
                eq(INVALID_USERNAME_MESSAGE));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"iohhan9", "_ivan1999", "qWertgh7Ivan9prtfK90I", "        ", "_/.^%$3ivkl", "@iohhanes"})
    void validateInvalidPasswordThenCallErrorsOneTimes(String password) {
        String username="ivanMa";
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq(SIGN_FIELD_NAME),
                eq(INVALID_SIGN_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ivanMA", "qwertyQWERTYqwertyAB", "iohhanes", "12IvanMA", "ivan_MA", "_i_v_a_n_1999",
            "12345I78"})
    void validateDuplicateUsernameThenCallErrorsOneTimes(String username) {
        user.setUsername(username);
        user.setPassword("6114516v");
        User existedUser = new User();
        existedUser.setPassword("helloWorld99");
        existedUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(existedUser);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq(USERNAME_FIELD_NAME),
                eq(DUPLICATE_USERNAME_MESSAGE));
    }

    @ParameterizedTest
    @CsvSource(value = {"ivan7:iohhan9", "qWertgh7Ivan9prtfK90I:_ivan1999", "123456:qWertgh7Ivan9prtfK90I",
            "_/.^7@#cy:3", "________:_/.^%$3ivkl", "hi:@iohhanes"}, delimiter = ':')
    void validateIfUsernameInvalidAndPasswordInvalidThenCallErrorsTwoTimes(String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq(USERNAME_FIELD_NAME),
                eq(INVALID_USERNAME_MESSAGE));
        verify(errors, times(1)).rejectValue(eq(SIGN_FIELD_NAME),
                eq(INVALID_SIGN_MESSAGE));
    }
}