package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.Role;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.CartRepository;
import com.stepByStep.core.repository.UserRepository;
import com.stepByStep.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
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
    public boolean addUser(String username, String password) {
        boolean resultAdding = true;
        User userFromDb = userRepository.findByUsername(username);
        if (userFromDb != null) {
            resultAdding = false;
        } else {
            userFromDb = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
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
