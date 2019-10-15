package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.Role;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.repository.UserRepository;
import com.stepByStep.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String USER_IS_NULL_WARN_MESSAGE = "Warning! User is null";

    private UserRepository userRepository;
    private CartRepository cartRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository=cartRepository;
    }

    @Override
    public boolean addUser(String username, String password) {
        boolean resultAdding = true;
        User userFromDb = userRepository.findByUsername(username);
        if (userFromDb != null) {
            resultAdding = false;
        } else {
            userFromDb = User.builder()
                    .username(username)
                    .password(password)
                    .active(true)
                    .orders(new HashSet<>())
                    .role(Role.USER)
                    .build();
            userFromDb.setCart(new Cart(userFromDb));
            userRepository.save(userFromDb);
            cartRepository.save(userFromDb.getCart());
        }
        return resultAdding;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
