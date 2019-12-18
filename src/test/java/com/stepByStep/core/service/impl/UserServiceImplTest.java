package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.USERNAME_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String username = "Iohhanes";
        String password = "80378037";
        user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(userRepository,mockPasswordEncoder,cartRepository);
        user = null;
    }

    @Test
    void loadUserByUsernameIfUsernameNotFoundThenThrowException() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(user.getUsername());
        }, USERNAME_NOT_FOUND_MESSAGE);
    }

    @Test
    void loadUserByUsernameIfUsernameFoundThenReturnUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        assertEquals(user, userService.loadUserByUsername(user.getUsername()));
    }
}