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

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Errors errors;

    @InjectMocks
    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(userRepository, errors);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ivanMA", "qwertyQWERTYqwertyAB", "iohhanes", "12IvanMA", "ivan_MA", "_i_v_a_n_1999",
            "12345I78", "IllBeBack2000"})
    void validateCorrectUsernameThenNotCallErrors(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("6114516v");
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(0)).rejectValue(eq("username"), anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"6114516v", "abs9absHe5absHello10", "IVAN90ivan", "helloWorld1999", "IvAnMAZURENKA",
            "qwertyASDFGHJK", "12345IV9", "61145169"})
    void validateCorrectPasswordThenNotCallErrors(String password) {
        User user = new User();
        user.setUsername("ivanMa");
        user.setPassword(password);
        when(userRepository.findByUsername("ivanMa")).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(0)).rejectValue(eq("password"), anyString());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"ivan7", "qWertgh7Ivan9prtfK90I", "123456", "_/.^7@#cy", "________", "      "})
    void validateInvalidUsernameThenCallErrors(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("6114516v");
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq("username"),
                eq("Please use between 6 and 20, latin characters, numbers and underscores"));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"iohhan9", "_ivan1999", "qWertgh7Ivan9prtfK90I", "        ", "_/.^%$3ivkl", "@iohhanes"})
    void validateInvalidPasswordThenCallErrors(String password) {
        User user = new User();
        user.setUsername("ivanMa");
        user.setPassword(password);
        when(userRepository.findByUsername("ivanMa")).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq("password"),
                eq("Try one with at least 8 latin characters or(and) numbers"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ivanMA", "qwertyQWERTYqwertyAB", "iohhanes", "12IvanMA", "ivan_MA", "_i_v_a_n_1999",
            "12345I78"})
    void validateDuplicateUsernameThenCallErrors(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("6114516v");
        User existedUser = new User();
        existedUser.setPassword("helloWorld99");
        existedUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(existedUser);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq("username"),
                eq("Someone already has that username"));
    }

    @ParameterizedTest
    @CsvSource(value = {"ivan:23", "123456:iohan7"}, delimiter = ':')
    void validateTest(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(null);
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq("username"),
                eq("Please use between 6 and 20, latin characters, numbers and underscores"));
        verify(errors, times(1)).rejectValue(eq("password"),
                eq("Try one with at least 8 latin characters or(and) numbers"));
    }
}