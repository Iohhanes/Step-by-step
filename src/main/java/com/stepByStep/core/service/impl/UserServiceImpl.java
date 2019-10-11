package com.stepByStep.core.service.impl;

import com.stepByStep.core.model.entity.Role;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.repository.UserRepository;
import com.stepByStep.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String USER_IS_NULL_WARN_MESSAGE = "Warning! User is null";

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean addUser(User user) {
        boolean resultAdding = true;
        if (user == null) {
            log.warn(USER_IS_NULL_WARN_MESSAGE);
            resultAdding = false;
        } else {
            User userFromDb = userRepository.findByUsername(user.getUsername());
            if (userFromDb != null) {
                resultAdding = false;
            }
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
        }
        return resultAdding;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
