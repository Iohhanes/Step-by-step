package com.stepByStep.core.util.validators;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.Order;
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
class OrderValidatorTest {

    @Mock
    private Errors errors;

    @InjectMocks
    private OrderValidator orderValidator;

    private Order order;


    @BeforeEach
    void setUp() {
        initMocks(this);
        order = new Order();
    }

    @AfterEach
    void tearDown() {
        order = null;
        reset(errors);
    }

    private void fillOrder(String name, String email, String phone) {
        order.setName(name);
        order.setEmail(email);
        order.setPhone(phone);
    }

    @ParameterizedTest
    @CsvSource(value = {"userDefault:helloworld@mail.ru:3756923", "Pavel:tanki@gmail.com:1234567",
            "Iva:mamkin.user1017@inbox.ru:7777789"}, delimiter = ':')
    void validateCorrectNameAndCorrectEmailAndCorrectPhoneThenNotCallErrors(String name, String email, String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors,times(0)).rejectValue(anyString(),anyString());
    }

    @ParameterizedTest
    @CsvSource(value = {"i9:qwert.5:45", "_567ty:1234@localhos_t:asdb765", "^&IVan:%^**%@8.@:^%$p#&9"}, delimiter = ':')
    void validateInvalidNameAndInvalidEmailAndInvalidPhoneThenCallErrorsThreeTimes(String name, String email,
                                                                                   String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("name"),
                eq("Please use between 3 and 20 characters"));
        verify(errors, times(1)).rejectValue(eq("email"), eq("Incorrect email"));
        verify(errors, times(1)).rejectValue(eq("phone"),
                eq("The phone number cannot be less than 7 characters and must contain only numbers"));
    }

    @ParameterizedTest
    @CsvSource(value = {"qwertyQWERTYabsrtyIvan:helloworld@mail.ru:3756923", "i9:tanki@gmail.com:1234567",
            "^&%*%PI_Ivan:mamkin.user1017@inbox.ru:7777789"}, delimiter = ':')
    void validateInvalidNameAndCorrectEmailAndCorrectPhoneThenCallErrorsOneTimes(String name, String email,
                                                                                 String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("name"),
                eq("Please use between 3 and 20 characters"));
    }

    @ParameterizedTest
    @CsvSource(value = {"userDefault:1234@localhost_4:3756923", "Pavel:qwert.5:1234567",
            "Iva:%^**%@8.a:7777789"}, delimiter = ':')
    void validateCorrectNameAndInvalidEmailAndCorrectPhoneThenCallErrorsOneTimes(String name, String email,
                                                                                 String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("email"),
                eq("Incorrect email"));
    }

    @ParameterizedTest
    @CsvSource(value = {"userDefault:helloworld@mail.ru:asdb765", "Pavel:tanki@gmail.com:45",
            "Iva:mamkin.user1017@inbox.ru:^%$p#&9"}, delimiter = ':')
    void validateCorrectNameAndCorrectEmailAndInvalidPhoneThenCallErrorsOneTimes(String name, String email,
                                                                                 String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("phone"),
                eq("The phone number cannot be less than 7 characters and must contain only numbers"));
    }

    @ParameterizedTest
    @CsvSource(value = {"iv:234@mail_ru:3756923", "qwertyQWERTYabsrtyIvan:@$#^$^:1234567",
            "$%&%$IVAN_:Y@#536:7777789"}, delimiter = ':')
    void validateInvalidNameAndInvalidEmailAndCorrectPhoneThenCallErrorsTwoTimes(String name, String email,
                                                                                 String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("name"),
                eq("Please use between 3 and 20 characters"));
        verify(errors, times(1)).rejectValue(eq("email"), eq("Incorrect email"));
    }

    @ParameterizedTest
    @CsvSource(value = {"i9:helloworld@mail.ru:$%68891", "$%I:tanki@gmail.com:1234567890",
            "wteuriqrqTyugvvtmmowgfiwg:mamkin.user1017@inbox.ru:45"}, delimiter = ':')
    void validateInvalidNameAndCorrectEmailAndInvalidPhoneThenCallErrorsTwoTimes(String name, String email,
                                                                                 String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("name"),
                eq("Please use between 3 and 20 characters"));
        verify(errors, times(1)).rejectValue(eq("phone"),
                eq("The phone number cannot be less than 7 characters and must contain only numbers"));
    }

    @ParameterizedTest
    @CsvSource(value = {"userDefault:@com:123IVA", "Pavel:@ivan@mail.ru:Rtel@com",
            "Iva:qewte1244:12345p7"}, delimiter = ':')
    void validateCorrectNameAndInvalidEmailAndInvalidPhoneThenCallErrorsTwoTimes(String name, String email,
                                                                                 String phone) {
        fillOrder(name, email, phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("email"),
                eq("Incorrect email"));
        verify(errors, times(1)).rejectValue(eq("phone"),
                eq("The phone number cannot be less than 7 characters and must contain only numbers"));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"          "})
    void validateEmptyOrWhitespaceNameAndCorrectEmailAndCorrectPhoneThenErrorsHasOneFieldError(String name) {
        order.setName(name);
        order.setEmail("helloworld@mail.ru");
        order.setPhone("3753999");
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("name"),
                eq("This field is required"), eq(null), eq(null));
        verify(errors, times(1)).rejectValue(eq("name"),
                eq("Please use between 3 and 20 characters"));
    }


    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"      "})
    void validateCorrectNameAndEmptyOrWhitespaceEmailAndCorrectPhoneThenErrorsHasOneFieldError(String email) {
        order.setName("ivan");
        order.setEmail(email);
        order.setPhone("37533999");
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("email"),
                eq("This field is required"), eq(null), eq(null));
        verify(errors, times(1)).rejectValue(eq("email"), eq("Incorrect email"));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"       "})
    void validateCorrectNameAndCorrectEmailAndEmptyOrWhitespacePhoneThenErrorsHasOneFieldError(String phone) {
        order.setName("ivan");
        order.setEmail("helloworld@mail.ru");
        order.setPhone(phone);
        orderValidator.validate(order, errors);
        verify(errors, times(1)).rejectValue(eq("phone"),
                eq("This field is required"), eq(null), eq(null));
        verify(errors, times(1)).rejectValue(eq("phone"),
                eq("The phone number cannot be less than 7 characters and must contain only numbers"));
    }
}