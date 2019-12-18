package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.repository.UserRepository;
import com.stepByStep.core.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.USERNAME_NOT_FOUND_MESSAGE;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void addUser(String username, String password) {
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(newUser);
        cartRepository.save(newUser.getCart());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn(USERNAME_NOT_FOUND_MESSAGE);
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE);
        }
        return user;
    }
}
